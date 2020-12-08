import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Bob {
	ServerSocket server=null;
	Socket socket=null;
	private int privateKeyB,publicKeyB;
	private int P,G;
	private int secretKey;

	public Bob(int port) throws IOException {
		server=new ServerSocket(port);
		System.out.println("Server Active");
		System.out.println("Waiting for connection");
		socket=server.accept();
		System.out.println("Connected..");
		DataInputStream in = null;
		DataOutputStream out = null;
		try {
			 in=new DataInputStream(socket.getInputStream());
			 out= new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
		P=in.read();
		G=in.read();
		System.out.println("Received P "+P+" G "+G);
		generateBKey();
		System.out.println("Bob's Private Key "+privateKeyB);
		int publicKeyA=in.read();
		publicKeyB=((int)Math.pow(G, privateKeyB))%P;
		System.out.println("Bob's public Key "+publicKeyB);
		out.write(publicKeyB);
		out.flush();
		
		secretKey=(int)Math.pow(publicKeyA, privateKeyB)%P;
		System.out.println("SecretKey "+secretKey);
	}

	private void generateBKey() {
		privateKeyB=(int)Math.random()*100+2;
		
		
	}
	public static void main(String[] args) throws IOException {
		Bob b=new Bob(5002);
	}
}
