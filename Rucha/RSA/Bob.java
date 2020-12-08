import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class Bob {
	private long n,phiN;
	private long d,e;
	Socket socket=null;
	ServerSocket server=null;
	
	public Bob(int port) throws IOException {
		n=phiN=d=e=0;
		server= new ServerSocket(port);
		socket=server.accept();
		DataInputStream in=new DataInputStream(socket.getInputStream());
		//DataOutputStream out=new DataOutputStream(socket.getOutputStream());
		
		phiN=in.readLong();
		System.out.println("phiN "+phiN);
		n=in.readLong();
		System.out.println("N "+n);
		e=in.readLong();
		System.out.println("e "+e);
		long cipher=in.readLong();
		System.out.println("Cipher "+cipher);
		generateDecryptionKey();
		System.out.println("D "+d);
		long plaintext=(long)Math.pow(cipher, d)%n;
		
		BigInteger PT=BigInteger.valueOf(cipher).pow((int)d).mod(BigInteger.valueOf(n)); //important to find 																						// out 
		System.out.println("PlainText "+PT.doubleValue());
	}
	private void generateDecryptionKey() {
		double num=0.00;
		for(int i=1;i<100;i++) {
			num=(double)(i*phiN+1)/e;
			if(num%1==0) {
				d=(long) num;
				break;
			}
		}
		//long k = 3;  // A constant value 
	   // d = (1 + (k*phiN))/e; 
		
	}
	public static void main(String[] args) throws IOException {
		Bob bob=new Bob(5002);
		
	}
	
}
