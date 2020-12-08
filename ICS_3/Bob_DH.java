import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Bob_DH
{
	private static Socket soc;
	private static Scanner sc;

	public static void main(String[] args) throws UnknownHostException, IOException
	{
		int P,G;
		int Xb;
		
		soc = new Socket("127.0.0.1",8088);
		System.out.println("Connected to "+soc.getRemoteSocketAddress());
		DataInputStream dis = new DataInputStream(soc.getInputStream());
		DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
		sc = new Scanner(System.in);
		
		System.out.print("Enter first prime Number(P) : ");
		P = sc.nextInt();
		System.out.print("Enter second prime number (G) : ");
		G = sc.nextInt();
		
		dos.writeInt(P);
		dos.writeInt(G);
		
		System.out.print("Enter Private key : ");
		Xb = sc.nextInt();
		
		//Yb = (G ^ Xb) mod P
		BigInteger Yb = (BigInteger.valueOf(G).pow(Xb)).mod(BigInteger.valueOf(P));
		System.out.println("Bob's Public key(Yb)= " + Yb.intValue());
		dos.writeInt(Yb.intValue());
		
		//Sk = (Ya ^ Xb) mod P
		int Ya = dis.readInt();
		System.out.println("Alice's Public key(Ya) = " + Ya);
		BigInteger Sk = (BigInteger.valueOf(Ya).pow(Xb)).mod(BigInteger.valueOf(P));
		
		System.out.println("Shared Key: " + Sk.intValue());
		soc.close();
		
	}
}

/*Output:
Connected to /127.0.0.1:8088
Enter first prime Number(P) : 5
Enter second prime number (G) : 11
Enter Private key : 3
Bob's Public key(Yb)= 1
Alice's Public key(Ya) = 1
Shared Key: 1
*/


