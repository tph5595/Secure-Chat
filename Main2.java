
import java.util.ArrayList;

public class Main2 {
	public static void main(String[] args){
		ArrayList<String> messages = new ArrayList<String>();
		Server s = new Server(9765,"224.2.2.3", null);
		while(true){
			messages.add(s.receive());
			for(String msg : messages){
				System.out.println(msg);		
			}
		}
	}
}
