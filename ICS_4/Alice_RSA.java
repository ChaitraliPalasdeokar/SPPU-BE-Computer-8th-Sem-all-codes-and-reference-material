import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Alice_RSA
{	
	public static void main(String[] args) throws IOException
	{
		ServerSocket server = new ServerSocket(8088);
		System.out.println("Waiting for connection on port " + server.getLocalPort());
		Socket soc = server.accept();
		System.out.println("Accepted connection from " + soc.getRemoteSocketAddress());
		DataInputStream dis = new DataInputStream(soc.getInputStream());
		DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
		
		Scanner sc = new Scanner(System.in);
		int n = dis.readInt();
		int e = dis.readInt();
		System.out.println("n = " + n + " \ne = " + e);
		
		System.out.println("Public key {e,n} = { " + e + ", " + n + " }");
		
		System.out.print("Enter Plaintext to be encrypted : ");
		int plaintext = sc.nextInt();
		
		BigInteger ciphertext = BigInteger.valueOf(plaintext).pow(e).mod(BigInteger.valueOf(n));
		System.out.println("Generated Ciphertext : " + ciphertext);
		dos.writeInt(ciphertext.intValue());

		soc.close();
		server.close();
		sc.close();
	}
}
