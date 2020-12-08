import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Alice {
	/*
	 * 	
	 *   YOUTUBE LINK for REFERENCE: https://youtu.be/u8puACVWPZA
	 * 	 Length: 11 mins
	 */
	
public static void main(String[] args) throws Exception {
	System.out.println("==============Server Side(ALICE)====================");
	ServerSocket server = new ServerSocket(8088);
	System.out.println("waitinng for connection on port  " + server.getLocalPort());
	Socket soc = server.accept();
	System.out.println("Accepted connection from " + soc.getRemoteSocketAddress());
	DataInputStream dis = new DataInputStream(soc.getInputStream());
	DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
	Point g=new Point(13, 16);//Read using scanner x=sc.nextLong();y=sc.nextLong(); g=new Point(x,y)
	dos.writeLong(g.x);
	dos.writeLong(g.y);
	long privA=10;  //take input from user
	
	Point pubA=g.multiply(privA);  
	
	System.out.println("Alice:\nPublic key = "+pubA+" \nPrivate key: "+privA);
	//sending public key key
	dos.writeLong(pubA.x);
	dos.writeLong(pubA.y);
	
	long x=dis.readLong();
	long y=dis.readLong();
	Point pubB=new Point(x, y);
	System.out.println("Bob's Public key = "+pubB);

	Point key1=pubB.multiply(privA);
	System.out.println("Secret key "+key1);
	
	System.out.println("Input MSG:");
	//Read using scanner x=sc.nextLong();y=sc.nextLong(); M=new Point(x,y)
	Point M=new Point(45,46);
	Point k=new Point(key1.x,key1.y);
	
	Point CT1=k.multiply(g);
	Point CT2=k.multiply(pubB).add(M);
	
	dos.writeLong(CT1.x);
	dos.writeLong(CT1.y);
	dos.writeLong(CT2.x);
	dos.writeLong(CT2.y);
	System.out.println("Message: "+M);
	System.out.println("CT1 = "+CT1+" \nCT2 = "+CT2);
}
}
/*
 * ==============Server Side(ALICE)====================
waitinng for connection on port  8088
Accepted connection from /127.0.0.1:40354
Alice:
Public key = [130 ,160] 
Private key: 10
Bob's Public key = [156 ,192]
Secret key [1560 ,1920]
Input MSG:
Message: [45 ,46]
CT1 = [20280 ,30720] 
CT2 = [243405 ,368686]
*/

