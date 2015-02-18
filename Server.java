import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JLabel;

public class Server implements Runnable{
	private static final int MAX_MESSAGES = 20;
	private MulticastSocket multicastSocket;
	private int port;
	private byte[] buffer = new byte[65508];
	private DatagramPacket packet;
	private ArrayList<String> messages = new ArrayList<String>();
	private JLabel stream;
	private Hashtable<String, String> knownUsers = new Hashtable<String, String >();
	
	public Server(int p, String ip, JLabel m){
		port = p;
		stream = m;
			try {
				multicastSocket = new MulticastSocket(port);
			} catch (IOException e) {
				System.out.println("UNABLE TO OPEN MULTICASTSOCKET!!!");
				e.printStackTrace();
			}
			InetAddress address = null;
			try {
				address = InetAddress.getByName(ip);
			} catch (UnknownHostException e) {
				System.out.println("UNABLE TO SET ADDRESS!!!");
				e.printStackTrace();
			}
			try {
				multicastSocket.joinGroup(address);
			} catch (IOException e) {
				System.out.println("UNABLE TO JOIN GROUP!!!");
				e.printStackTrace();
			}
	}
	public String receive(){
		buffer = new byte[65508];
		packet = null;
		packet = new DatagramPacket(buffer, buffer.length);
		try {
			multicastSocket.receive(packet);
			
		} catch (IOException e) {
			System.out.println("UNABLE TO RECIEVE DATA!!!");
			e.printStackTrace();
		}
		byte[] buffer = packet.getData();
		String s = null;
		try {
			s = new String(buffer, "US-ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		System.out.println("Hi: " + s);
		String found = knownUsers.get(packet.getAddress().getHostAddress());
		if(found == null){
			knownUsers.put(packet.getAddress().getHostAddress(), s.trim());
			s +=" joined the server";
			System.out.println(s);
			return s;
		}
		return s.trim();
		
	}
	public void run(){
		String temp = "";
		while(true){
			temp = receive();
			messages.add(temp);
			truncateList();
			String s = "<html>";
			for(String message: messages){
				s+=message+"<br>";
			}
			s+="</html>";
			System.out.println("after"+ s);
			stream.setText(s);
 	    }
	}
	public void truncateList(){
		if(messages.size()>MAX_MESSAGES){
			messages.remove(0);
		}
	}
}
