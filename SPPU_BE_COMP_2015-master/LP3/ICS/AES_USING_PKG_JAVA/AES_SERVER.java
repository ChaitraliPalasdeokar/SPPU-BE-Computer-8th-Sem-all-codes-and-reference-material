import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
// Note: This Program uses java.crypto pkg. It uses readymade AES implementation. 
//		In exam, some externals may not allow such implementation

public class AES_SERVER {
public static void main(String[] args) {
	 final int PORT=8088;

	try{
		ServerSocket serverSocket = new ServerSocket(PORT); 
System.out.println("Started server on port "+PORT+"\nWaiting for client connection");
	Socket server = serverSocket.accept(); 
    System.out.println("Just connected to " + server.getRemoteSocketAddress()); 
 // Accepts the data from client 
    DataInputStream in = new DataInputStream(server.getInputStream()); 
    DataOutputStream out = new DataOutputStream(server.getOutputStream());
  
    String encodedkey = in.readUTF();
    
    byte[] decodedKey = Base64.getDecoder().decode(encodedkey);
 // rebuild key using SecretKeySpec
 SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
 byte[]IV=new byte[16];
 int noBytes=in.read(IV);
 
 String cipherText=in.readUTF();
  System.out.println("Symmetric Key :"+encodedkey);
  System.out.println("Received CipherText: "+cipherText);
 String decryptedText=decrypt(Base64.getDecoder().decode(cipherText),key,IV);//todo
 System.out.println("Decrypted text: "+decryptedText);
	}
	catch(Exception e){
		
	}
}



public static String decrypt (byte[] cipherText, SecretKey key,byte[] IV) throws Exception
{
    //Get Cipher Instance
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    
    //Create SecretKeySpec
    SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
    
    //Create IvParameterSpec
    IvParameterSpec ivSpec = new IvParameterSpec(IV);
    
    //Initialize Cipher for DECRYPT_MODE
    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
    
    //Perform Decryption
    byte[] decryptedText = cipher.doFinal(cipherText);
    
    return new String(decryptedText);
}
}
