import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class DiffieHellmanServer {
public static void main(String[] args) throws IOException {
	//Socket connection code (Server)
	ServerSocket server=new ServerSocket(8088);
	System.out.println("waitinng for connection on port  "+server.getLocalPort());
	Socket soc=server.accept();
	System.out.println("Accepted connection from "+soc.getRemoteSocketAddress());
	DataInputStream dis=new DataInputStream(soc.getInputStream());
	DataOutputStream dos=new DataOutputStream(soc.getOutputStream());
	
	//Read data sent by User A
	int p=dis.readInt();
	int g=dis.readInt();
	int x=dis.readInt(); //received public key of A
	System.out.println("Received Values: \nP = "+p+" \nG = "+g+"\nPublic Key of A (X) = "+x);
	
	//Generate private key  b 
	int b=(int)Math.random()+1; 
	System.out.println("Private Ket of B (b) = "+b);
	
	//y=(g^b)mod(p)
	BigInteger y=(BigInteger.valueOf(g).pow(b)).mod(BigInteger.valueOf(p));
	System.out.println("Public key of B(y) = "+y);
	dos.writeInt(y.intValue());
	
	BigInteger secretKey=(BigInteger.valueOf(x).pow(b)).mod(BigInteger.valueOf(p));
	
	System.out.println("Secret Key : "+secretKey.intValue());
	
//If Secret key at A & B are same then communication can be performed
}
}
