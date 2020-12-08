import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class DiffiehellmanClient {

	
	
	private static Socket soc;
	private static Scanner sc;

	public static void main(String[] args) throws UnknownHostException, IOException {
		int p,g;//Two large prime numbers(public keys)
		int a;//selected private key of user A
		
		//Socket connection code
		soc = new Socket("127.0.0.1",8088);
		System.out.println("Connected to "+soc.getRemoteSocketAddress());
		DataInputStream dis=new DataInputStream(soc.getInputStream());
		DataOutputStream dos=new DataOutputStream(soc.getOutputStream());
		sc = new Scanner(System.in);
		
		//Take 2 prime numbers as input
		System.out.println("Enter first prime Number(P):");
		p=sc.nextInt();
		System.out.println("Enter second prime number (G):");
		g=sc.nextInt();
		
		//send public keys(2 large prime nos) to another user B
		dos.writeInt(p);
		dos.writeInt(g);
		
		//Select random private key of user A
		a=(int)Math.random()+1;
		System.out.println("Private Key (A): "+a);
		
		BigInteger x=(BigInteger.valueOf(g).pow(a)).mod(BigInteger.valueOf(p)); //x=(g^a)mod(p)
		System.out.println("Generated key (X)= "+x.intValue());
		dos.writeInt(x.intValue());
		
		int y=dis.readInt(); //received public key from B
		System.out.println("Received data from server (y) = "+y);
		BigInteger secretKey=(BigInteger.valueOf(y).pow(a)).mod(BigInteger.valueOf(p));
		
		System.out.println(" Secret Key: "+secretKey.intValue());
		//if secret keys at User A & B are same then communication can be performed.
		
		soc.close();
		
	}
}
