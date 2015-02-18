import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Main {
	public static void main(String[] args){

		Client c = null;
		try {
			c = new Client(InetAddress.getByName("224.2.2.3"), 87, "Taylor H.");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		Scanner kbreader = new Scanner(System.in);
		
		String msg = "New user";
		
		while(true){
			msg = kbreader.nextLine();
			c.setMessage(msg);
			c.send();
		}
	}
}
