import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Alice_DH
{
	public static void main(String[] args) throws IOException
	{
		ServerSocket server = new ServerSocket(8088);
		System.out.println("Waiting for connection on port " + server.getLocalPort());
		Socket soc = server.accept();
		System.out.println("Accepted connection from " + soc.getRemoteSocketAddress());
		DataInputStream dis = new DataInputStream(soc.getInputStream());
		DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
		
		int P = dis.readInt();
		int G = dis.readInt();
		int Yb = dis.readInt();
		System.out.println("P = " + P + " \nG = " + G + "\nBob's Public key(Yb) = " + Yb);
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Private key : ");
		int Xa = sc.nextInt();
		
		//Ya = (G ^ Xa) mod P
		BigInteger Ya = (BigInteger.valueOf(G).pow(Xa)).mod(BigInteger.valueOf(P));
		System.out.println("Alice's Public key(Ya) = " + Ya);
		dos.writeInt(Ya.intValue());
		
		//Sk = (Yb ^ Xa) mod P
		BigInteger Sk=(BigInteger.valueOf(Yb).pow(Xa)).mod(BigInteger.valueOf(P));
		System.out.println("Shared Key : "+ Sk.intValue());
	}
}

/*Output:
Waiting for connection on port 8088
Accepted connection from /127.0.0.1:38786
P = 5 
G = 11
Bob's Public key(Yb) = 1
Enter Private key : 2
Alice's Public key(Ya) = 1
Shared Key : 1
*/

