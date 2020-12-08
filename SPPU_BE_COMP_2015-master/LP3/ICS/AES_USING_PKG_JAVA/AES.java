import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
public class AES {
 public final static String SERVER="127.0.0.1";
 public static final int PORT=8088;
 // Note: This Program uses java.crypto pkg. It uses readymade AES implementation. 
//		In exam, some externals may not allow such implementation
 public static void main(String[] args) throws NoSuchAlgorithmException {
	 
	 String plainText="Vaibhav Navnath Kumbhar";
	try {
		Socket soc=new Socket(SERVER, PORT);
		 System.out.println("Just connected to " + soc.getRemoteSocketAddress());
		 
		 DataOutputStream out=new DataOutputStream(soc.getOutputStream());
		 DataInputStream in=new DataInputStream(soc.getInputStream());
		 //GENERATING KEYS
		 KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");
		 keyGenerator.init(128);
		 
		 SecretKey key=keyGenerator.generateKey();
		 
		 // Generating IV.(INitializaion vector)/We are using CBC
	     byte[] IV = new byte[16];
	     SecureRandom random = new SecureRandom();
	     random.nextBytes(IV);
	     
	        byte[] cipherText = encrypt(plainText.getBytes(),key, IV);
		System.out.println("Plain Text: :"+plainText);
		String cipherString=Base64.getEncoder().encodeToString(cipherText);

	        System.out.println("Encrypted Text : "+cipherString );
	        
	        System.out.println("Symmetric Key "+Base64.getEncoder().encodeToString(key.getEncoded()));
	        out.writeUTF(Base64.getEncoder().encodeToString(key.getEncoded())); //key
	        out.write(IV);
	        out.writeUTF(cipherString);
	        
		
		
	} catch (UnknownHostException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
 
 
 public static byte[] encrypt(byte[] plaintext, SecretKey key, byte[] IV) throws Exception{

     //Get Cipher Instance
     Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
     
     //Create SecretKeySpec
     SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
     
     //Create IvParameterSpec
     IvParameterSpec ivSpec = new IvParameterSpec(IV);
     
     //Initialize Cipher for ENCRYPT_MODE
     cipher.init(Cipher.ENCRYPT_MODE, keySpec,ivSpec 	);
     
     //Perform Encryption
     byte[] cipherText = cipher.doFinal(plaintext);
     
     return cipherText;
	 
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
