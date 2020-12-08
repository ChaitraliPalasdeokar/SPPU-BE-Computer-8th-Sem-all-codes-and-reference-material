import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class RSAClient {

	public static final String SERVER="127.0.0.1";
	public static final int PORT=8088;
	
	static long gcd(long phiOfN, long e) { 
		long temp; 
	    while (true) 
	    { 
	        temp = phiOfN%e; 
	        if (temp == 0) 
	          return e; 
	        phiOfN = e; 
	        e = temp; 
	    }
	}
	
	public static long getEncyptionkey(long phiOfN) {
		long e=2;
		for(e=2;e<phiOfN;e++) {
			if(gcd(phiOfN,e)==1) {
				//e=13;
				return e;
			}
		}
		//e=13;
		
		return e;
	}
	
	public static long getDecryptionKey(long phiOfN, long e) {
		double num=0.0;
		for(int i=0;i<100;i++) {
			num=(i*phiOfN+1)/e;
			if(num%1==0)
				break;
		}
		return (long)num;
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("==============Client Side====================");
		Socket soc=new Socket(SERVER, PORT);
		System.out.println("Connected to "+soc.getRemoteSocketAddress());
		DataInputStream dis=new DataInputStream(soc.getInputStream());
		DataOutputStream dos=new DataOutputStream(soc.getOutputStream());
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter Prime number (P):");
		long p=sc.nextLong();
		System.out.print("Enter Prime number (Q):");
		long q=sc.nextLong();
		
		long n=p*q;
		
		long phiOfN=(p-1)*(q-1);
		
		int e=(int)getEncyptionkey(phiOfN);
		
		//long d=getDecryptionKey(phiOfN, e);
		
		System.out.println("P = "+p+"\nQ = "+q);
		System.out.println("N = "+n);
		System.out.println("Phi(n) = "+phiOfN);
		System.out.println("Public key (e) = "+e);
		//System.out.println("Private Key (d) = "+d);
		
		System.out.println("Enter message(number) to be encrypted");
		long PT=sc.nextLong();
		 System.out.println("Plain Text : "+PT);
BigInteger CT=(BigInteger.valueOf(PT).pow(e)).mod(BigInteger.valueOf(n));		
		System.out.println("Cipher Text: "+CT.intValue());
		
		
		//sending data to server for Decryption of CT
		//dos.writeLong(d);
		dos.writeLong(phiOfN);
		dos.writeLong(n);
		dos.writeLong(e);
		dos.writeInt(CT.intValue());
		
		soc.close();
		
	}
	
	
	
	//Extra Code if you want to check if entered no is  prime number or not
	
	public static boolean isPrime(long num) {
		if(num==1||num==2)
			return true;
		for(int i=2;i<Math.sqrt(num);i++) {
			if(num%i==0)
				return false;
		}
		return true;
	}

}

/*   OUTPUT
 * 
Enter Prime number (P):13
Enter Prime number (Q):11
P = 13
Q = 11
N = 143
Phi(n) = 120
Public key (e) = 7
Enter message(number) to be encrypted
15
Plain Text : 15
Cipher Text: 115

*/
