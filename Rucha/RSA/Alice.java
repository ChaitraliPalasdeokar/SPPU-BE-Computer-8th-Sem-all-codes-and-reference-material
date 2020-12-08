import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Alice {
	private  long e;
	//private double d;
	private long p,q;
	private long n,phiN;
	Socket socket=null;
	ArrayList<Integer> primes;

	public Alice() {
		primes=new ArrayList<Integer>();
	}
	public void startConversation(String address,int port) throws UnknownHostException, IOException {
		socket=new Socket(address,port);
		DataInputStream in=new DataInputStream(socket.getInputStream());
		DataOutputStream out=new DataOutputStream(socket.getOutputStream());
		
		generatePQ();
//		out.writeLongint(p);
//		out.flush();
//		out.writeLongint(q);
		EulerTotient();
		out.writeLong(phiN);
		System.out.println("phIN "+phiN);
		out.flush();
		out.writeLong(n);
		out.flush();
		//finding e
		generateEncryptionKey();
		
		out.writeLong(e);
		out.flush();
		System.out.println("E "+e);
		System.out.println("Enter the message to be encrypted");
		Scanner sc=new Scanner(System.in);
		long m=sc.nextInt();
		long cipher=(long) ((Math.pow(m, e))%n);
		System.out.println("Cipher "+cipher);
		out.writeLong(cipher);
		out.flush();
	}
	private void generateEncryptionKey() {
		e=2;
		while(e<phiN) {
			if(gcd(e,phiN)==1) {
				break;
			}
			else {
				e++;
			}
		}
		//e=13; // delete this
		//EulerTotient();
//		ArrayList<Integer> listofprimes=new ArrayList<Integer>();
//		
//		for(int i=2;i<100;i++) {
//			if(isPrime(i)) {
//				
//				listofprimes.add(i);
//
//			}
//		}
//		for(Integer prime:listofprimes) {
//			System.out.println(prime);
//			if(gcd(prime,phiN)==1) {
//				System.out.println("in if");
//				e=prime;
//				break;
//			}
//		}
	}
	private long gcd(long a, long b) {
		if(b==0)
			return a;
		return gcd(b,a%b);
	}
	private void EulerTotient() {
		phiN=(p-1)*(q-1);
	}
	private void generatePQ() {
		long cnt=0;
		for(int i=1000;i<2000;i++) {
			if(isPrime(i)) {
				cnt++;
				primes.add(i);

			}
			
		}
		System.out.println("cnt"+cnt);
		int n1=(int) (Math.random()*(cnt-10)+1);
		int n2=(int) (Math.random()*(cnt-10)+2);
		p=13;		//primes.get(n1);
		q=11;			//primes.get(n2);
		System.out.println("p "+p+" q "+q);
		n=p*q;

	}
	private boolean isPrime(long i) {
		for(int j=2;j<Math.sqrt(i);j++) {
			if(i%j==0)
				return false;
		}
		return true;
	}
	public static void main(String[] args) throws UnknownHostException, IOException {
		Alice alice=new Alice();
		alice.startConversation("127.0.0.1", 5002);
	}
}
