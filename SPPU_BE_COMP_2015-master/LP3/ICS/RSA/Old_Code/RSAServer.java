import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class RSAServer {
	public static long getDecryptionKey(long phiOfN, long e) {
		double num=0.0;
		for(int i=1;i<100;i++) {
			num=(double)(i*phiOfN+1)/e;  //Note:typecasting is imp here..Otherwise it will typecast to long value and below if condition will be true for first run
			if(Math.floor(num)==num)  //num%1==0
			{
				System.out.println("i = "+i+" num = "+num);
				break;
			}
		}
		return (long)num;
	}
	public static void main(String[] args) throws IOException {
		System.out.println("==============Server Side====================");

		ServerSocket server=new ServerSocket(8088);
		System.out.println("waitinng for connection on port  "+server.getLocalPort());
		Socket soc=server.accept();
		System.out.println("Accepted connection from "+soc.getRemoteSocketAddress());
		DataInputStream dis=new DataInputStream(soc.getInputStream());
		DataOutputStream dos=new DataOutputStream(soc.getOutputStream());
		
		long phiOfN=dis.readLong();
		long n=dis.readLong();
		long e=dis.readLong();
		int CT=dis.readInt();
		
		System.out.println("N = "+n);
		System.out.println("Public key (e) = "+e);
		System.out.println("phi(n) = "+phiOfN);
		System.out.println("Cipher text : "+CT);
		
		long d=getDecryptionKey(phiOfN, e);
		System.out.println("Private key (D):  "+d);
		
		BigInteger PT=(BigInteger.valueOf(CT).pow((int)d)).mod(BigInteger.valueOf(n));
		
		System.out.println("Decypted Plain text: "+PT.intValue());
				
		soc.close();

	}

}
/*
 * RSA SERVER OUTPUT
waitinng for connection on port  8088
Accepted connection from /127.0.0.1:60598
N = 143
Public key (e) = 7
phi(n) = 120
Cipher text : 115
i = 6 num = 103.0
Private key (D):  103
Decypted Plain text: 15
 
 */
