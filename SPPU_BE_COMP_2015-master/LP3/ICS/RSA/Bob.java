import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Scanner;

public class Bob {
	public static final String SERVER = "127.0.0.1";
	public static final int PORT = 8088;

	public static void main(String[] args) throws Exception, IOException {
		System.out.println("====Client Side====(BOB)");
		Socket soc = new Socket(SERVER, PORT);
		System.out.println("Connected to " + soc.getRemoteSocketAddress());
		DataInputStream dis = new DataInputStream(soc.getInputStream());
		DataOutputStream dos = new DataOutputStream(soc.getOutputStream());

		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Prime number (P):");
		int p = sc.nextInt();
		System.out.print("Enter Prime number (Q):");
		int q = sc.nextInt();

		int n = p * q;
		int phi = (p - 1) * (q - 1);
		System.out.println("phi = " + phi + "\nn=" + n);
		int e = getPublicKey(phi);

		int d = getDecryptionKey(phi, e);

		dos.writeInt(n);
		dos.writeInt(e);
		System.out.println("Public key {e,n} = { " + e + ", " + n + " }");
		System.out.println("Private key {d,n} = { " + d + ", " + n + " }");

		System.out.println("Waiting for CIpher Text from Alice....:)");
		int CT = dis.readInt();
		System.out.println("Received CT: " + CT);
		BigInteger PT = BigInteger.valueOf(CT).pow(d).mod(BigInteger.valueOf(n));

		System.out.println("Decrypted Plain Text: " + PT);
		soc.close();
		sc.close();
		// sending data to ALice
	}

	public static int gcd(int a, int b) {
		int temp;
		while (true) {
			temp = a % b;
			if (temp == 0)
				return b;
			a = b;
			b = temp;
		}
	}

	public static int getPublicKey(int phi) {
		int e = 2;
		for (e = 2; e < phi; e++) {
			/*if (gcd(phi, e) == 1) {
				break;
			}*/
			//Use above cooomented or below library function for calculating gcd
			if (BigInteger.valueOf(phi).gcd(BigInteger.valueOf(e)).intValue() == 1)
				break;
		}

		return e;
	}

	public static int getDecryptionKey(int phi, int e) {
		int k = 1;
		double d = (double) (k * phi * 1) / (double) e;
		while (Math.floor(d) != d) {
			k = k + 1;
			d = (double) (k * phi + 1) / (double) e;
		}
		return (int) d;
	}
}
