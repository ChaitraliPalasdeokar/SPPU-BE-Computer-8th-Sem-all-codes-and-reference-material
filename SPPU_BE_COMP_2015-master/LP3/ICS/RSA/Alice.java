import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
/*
 * IN RSA, Public and Private Keys are used.
 * Alice wants to send msg to bob.So ALice will use Bob's Public key for encryption.
 * Bob Will generate both public {e,n} and private keys{d,n}. 
 * He will send its public key {e,n} to alice.
 * Alice will send CipherText to bob.
 * Bob will decrypt msg using his own private keys {d,n}
 * 
 */
public class Alice {

	public static void main(String[] args) throws Exception {
		System.out.println("==============Server Side(ALICE)====================");
		ServerSocket server = new ServerSocket(8088);
		System.out.println("waitinng for connection on port  " + server.getLocalPort());
		Socket soc = server.accept();
		System.out.println("Accepted connection from " + soc.getRemoteSocketAddress());
		DataInputStream dis = new DataInputStream(soc.getInputStream());
		DataOutputStream dos = new DataOutputStream(soc.getOutputStream());

		Scanner sc = new Scanner(System.in);
		int n = dis.readInt();
		int e = dis.readInt();

		System.out.println(" Public Key of Bob(Used for encryption): ");
		System.out.println("Public key {e,n} = { " + e + ", " + n + " }");

		System.out.println("ENter Plain Text (integer) to be encypted: PT: ");
		int pt = sc.nextInt();

		BigInteger CT = BigInteger.valueOf(pt).pow(e).mod(BigInteger.valueOf(n));
		System.out.println("Generated Cipher Text CT: " + CT);

		dos.writeInt(CT.intValue());

		soc.close();
		server.close();
		sc.close();
	}
}
