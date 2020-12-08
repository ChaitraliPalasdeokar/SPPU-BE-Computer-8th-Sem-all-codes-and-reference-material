import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Bob_RSA
{	
	public static int gcd(int a, int h) 
	{ 
	    int temp; 
	    while(true)
	    { 
		temp = a % h; 
		if (temp == 0) 
		  return h; 
		a = h; 
		h = temp; 
	    }
	}
	
	public static int getPublicKey(int phi)
	{
		int e = 2;
		for (e = 2; e < phi; e++)
		{
			if (BigInteger.valueOf(phi).gcd(BigInteger.valueOf(e)).intValue() == 1)
				break;
		}
		return e;
	}
	
	public static int getDecryptionKey(int phi, int e)
	{
		int k = 1;
		double d = (double) (k * phi * 1) / (double) e;
		
		while (Math.floor(d) != d)
		{
			k = k + 1;
			d = (double) (k * phi + 1) / (double) e;
		}
		return (int)d;
	}	

	public static void main(String[] args) throws UnknownHostException, IOException
	{		
		Socket soc = new Socket("127.0.0.1",8088);
		System.out.println("Connected to "+soc.getRemoteSocketAddress());
		DataInputStream dis = new DataInputStream(soc.getInputStream());
		DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter first prime Number(p) : ");
		int p = sc.nextInt();
		System.out.print("Enter second prime number (q) : ");
		int q = sc.nextInt();
		int n = p * q;
		System.out.println("n = " + n);
		int phi = (p - 1) * (q - 1);
		System.out.println("Phi = " + phi);
		
		int e = getPublicKey(phi);
		int d = getDecryptionKey(phi, e);
		
		dos.writeInt(n);
		dos.writeInt(e);
		
		System.out.println("Public key {e,n} = { " + e + ", " + n + " }");
		System.out.println("Private key {d,n} = { " + d + ", " + n + " }");

		System.out.println("Waiting for Ciphertext from Alice....");
		int ciphertext = dis.readInt();
		System.out.println("Received Ciphertext : " + ciphertext);
		BigInteger plaintext = BigInteger.valueOf(ciphertext).pow(d).mod(BigInteger.valueOf(n));

		System.out.println("Decrypted Plain Text: " + plaintext);
		
		soc.close();
		sc.close();		
		
	}
}
