import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Alice {
	Socket socket=null;
	private int privateKeyA,publicKeyA;
	private int P,G,secretKey;
	
	
	public void startConversation(String address, int port) throws UnknownHostException, IOException {
		socket=new Socket(address,port);
		System.out.println("Connected");
		DataInputStream in=new DataInputStream(socket.getInputStream());
		DataOutputStream out=new DataOutputStream(socket.getOutputStream());
		
		generatePublicNumbers();
		out.write(P);
		out.flush();
		out.write(G);
		out.flush();
		
		generateKeyA();
		System.out.println("Alice's private key "+privateKeyA);
		publicKeyA=(int)Math.pow(G, privateKeyA)%P;
		System.out.println("Alice's public key "+publicKeyA);
		out.write(publicKeyA);
		int publicKeyB=in.read();
		System.out.println("Received publicKeyB "+publicKeyB);
		secretKey=(int)Math.pow(publicKeyB, privateKeyA)%P;
		System.out.println("SecretKey "+secretKey);
		
	}

	private void generatePublicNumbers() {
		int n=100;
		ArrayList<Integer>list=new ArrayList<Integer>();
		
		for(int i=0;i<n;i++) {
			if(isPrime(i)) {
				list.add(i);
			}
		}
		int index=(int)Math.random()*40+1;
		int ind=(int)Math.random()*30+2;
		P=list.get(index);
		G=list.get(ind);
		
		
	}

	private boolean isPrime(int n) {
		for(int j=2;j<Math.sqrt(n);j++) {
			if(n%j==0)
				return false;
		}
		return true;
	}

	private void generateKeyA() {
		privateKeyA=(int)Math.random()*100+1;		
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Alice alice=new Alice();
		alice.startConversation("127.0.0.1", 5002);
	}
}
