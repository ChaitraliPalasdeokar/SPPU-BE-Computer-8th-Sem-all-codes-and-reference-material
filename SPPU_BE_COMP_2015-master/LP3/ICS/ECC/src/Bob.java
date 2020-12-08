import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Bob {
	public static final String SERVER = "127.0.0.1";
	public static final int PORT = 8088;
public static void main(String[] args) throws Exception, IOException {
	System.out.println("====Client Side====(BOB)");
	Socket soc = new Socket(SERVER, PORT);
	System.out.println("Connected to " + soc.getRemoteSocketAddress());
	DataInputStream dis = new DataInputStream(soc.getInputStream());
	DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
	
	long x=dis.readLong();
	long y=dis.readLong();
	Point g=new Point(x, y);
	
	long privB=12; //Take input from user
	Point pubB=g.multiply(privB);
	
	
	System.out.println("Bob:\nPublic key = "+pubB+" \nPrivate key: "+privB);

	x=dis.readLong();
	y=dis.readLong();
	Point pubA=new Point(x, y);
	System.out.println("Alice's Public key = "+pubA);
	
	//sending public key key
		dos.writeLong(pubB.x);
		dos.writeLong(pubB.y);
	
		Point key2=pubA.multiply(privB);
		Point key=new Point(key2.x,key2.y);
		System.out.println("Secret key "+key2);
		System.out.println("Secret keys are same then we can perform encryption and decryption");
		x=dis.readLong();
		y=dis.readLong();
		Point CT1=new Point(x, y);
		
		x=dis.readLong();
		y=dis.readLong();
		Point CT2=new Point(x, y);
		System.out.println("Received \nCT1 = "+CT1+" \nCT2 = "+CT2);
		
		Point dec1=CT1.multiply(privB);
		Point PT=CT2.sub(dec1);
		
		System.out.println("Decrypted PT: "+PT);
}
}

/*
 * ====Client Side====(BOB)
Connected to /127.0.0.1:8088
Bob:
Public key = [156 ,192] 
Private key: 12
Alice's Public key = [130 ,160]
Secret key [1560 ,1920]
Secret keys are same then we can perform encryption and decryption
Received 
CT1 = [20280 ,30720] 
CT2 = [243405 ,368686]
Decrypted PT: [45 ,46]

*/
