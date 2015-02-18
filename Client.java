import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class Client implements Runnable{
	private DatagramSocket datagramSocket;
	private InetAddress server;
	private byte[] message;
	private int port;
	private String username;
	private JButton sendButton;
	private JTextField input;
	private JFrame frame;
	private JButton openButton;
	private ChatPanel self;
	
	public Client(String hostname, int p, String uname, JButton s, JTextField f, JFrame frm, JButton open, ChatPanel sf){
		frame = frm;
		sendButton = s;
		input = f;
		username = uname;
		openButton = open;
		self = sf;
		try {
			
			server = InetAddress.getByName(hostname);
			datagramSocket = new DatagramSocket();
			port = p;
		} catch (UnknownHostException e) {
			System.out.println("UNKNOWN HOST!!!");
			e.printStackTrace();
		}catch (SocketException e) {
			System.out.println("UNABLE TO START DATASOCKET!!!");
			e.printStackTrace();
		}
		
		//Send Username
		try {
			message = username.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("UNABLE TO CONVERT TO UTF-8!!!");
			e.printStackTrace();
		}
		send();
	}
	
	public Client(InetAddress ip, int p, String username){
		try {
			server = ip;
			datagramSocket = new DatagramSocket();
			port = p;
		} catch (SocketException e) {
			System.out.println("UNABLE TO START DATASOCKET!!!");
			e.printStackTrace();
		}
	}
	
	
	public void setMessage(String msg){
		msg = username + ": " + msg;
		try {
			message = msg.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("UNABLE TO CONVERT TO UTF-8!!!");
			e.printStackTrace();
		}
	}
	
	public boolean valid(String msg){
		byte[] temp = msg.getBytes();
		if(temp.length < 65508){
			return true;
		}
		return false;
		
	}
	public void setFile(byte[] a){
		message = a; 
	}
	
	public void setPort(int p){
		port = p;
	}
	
	public int getPort(){
		return port;
	}
	
	public void send(){
		DatagramPacket packet = new DatagramPacket(
		        message, message.length, server, port);
		try {
			datagramSocket.send(packet);
		} catch (IOException e) {
			System.out.println("UNABLE TO SEND PACKET!!!");
			e.printStackTrace();
		}
	}
	public void run(){
		sendButton.addActionListener(new Send());
		input.addKeyListener(new Key());
		openButton.addActionListener(new Browse());
	}
	
	private class Send implements ActionListener
    {	 	
           
       public void actionPerformed(ActionEvent e)
       {
    	   if(!input.getText().equals(null) && !input.getText().equals("")){
    		   setMessage(input.getText());
    		   send();
    		   input.setText(null);
    	   }
    	   else{
    		   JOptionPane.showMessageDialog(frame,
					    "Please type a message",
					    "ERROR",
					    JOptionPane.ERROR_MESSAGE);
    	   }
    	   
       }
    }
	private class Key extends KeyAdapter
    {
       public void keyPressed(KeyEvent e)
       {
    	   if(e.getKeyCode() == KeyEvent.VK_ENTER)
           {
    		   if(!input.getText().equals(null) && !input.getText().equals("")){
        		   setMessage(input.getText());
        		   send();
        		   input.setText(null);
        	   }
        	   else{
        		   JOptionPane.showMessageDialog(frame,
    					    "Please type a message",
    					    "ERROR",
    					    JOptionPane.ERROR_MESSAGE);
        	   }
           }
       }
    }
	private class Browse implements ActionListener
    {	 	
	   JFileChooser fc = new JFileChooser();
	   FileInputStream fileInputStream = null;
       byte[] bFile;
	   public void actionPerformed(ActionEvent e)
       
       {
    	   int returnVal = fc.showOpenDialog(self);

           if (returnVal == JFileChooser.APPROVE_OPTION) {
               File file = fc.getSelectedFile();
               bFile= new byte[(int) file.length()];
               
      
                   //convert file into array of bytes
       	    try {
				fileInputStream = new FileInputStream(file);
	       	    fileInputStream.read(bFile);
	       	    fileInputStream.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
       	 for (int i = 0; i < bFile.length; i++) {
 	       	System.out.print((char)bFile[i]);
             }
       	 	System.out.println("");

               //This is where a real application would open the file.
               System.out.println("Opening: " + file.getName() + ".\n");
           } else {
        	   System.out.println("Open command cancelled by user.\n");
           }
           setFile(bFile);
           send();

       }
    }
}
