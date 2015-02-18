import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class ChatPanel extends JPanel {
	private JLabel message;
	private JTextField input;
	private JButton sendButton;
	private Client c = null;
	private Server s;
	private JFrame frame;
	private JPanel InputPanel;
	private JPanel UserPanel;
	private JPanel StreamPanel;
	private JButton openButton;
	
	public ChatPanel(JFrame f){
		frame = f;
		//basic stoof
		setLayout(new BorderLayout());
		
		InputPanel = new JPanel();
		InputPanel.setLayout(new FlowLayout());
		
		UserPanel = new JPanel();
		UserPanel.setLayout(new GridLayout());
		
		StreamPanel = new JPanel();
		StreamPanel.setLayout(new BorderLayout());
		
		message = new JLabel("Connecting to the Server...");
		message.setForeground(Color.BLUE);
		StreamPanel.add(message, BorderLayout.CENTER);
		
		input = new JTextField(30);
		sendButton = new JButton("Send!");
		openButton = new JButton("Open file");
		InputPanel.add(input, BorderLayout.LINE_START);
		InputPanel.add(sendButton);
		InputPanel.add(openButton, BorderLayout.LINE_END);
		
		add(InputPanel, BorderLayout.SOUTH);
		add(StreamPanel, BorderLayout.CENTER);
		add(UserPanel, BorderLayout.WEST);
		
		
		//start the threads
		s = new Server(87,"224.2.2.3", message);
		c = new Client("224.2.2.3", 87, "Taylor", sendButton, input, frame, openButton, this);
		
		Thread client = new Thread(c);
		Thread server = new Thread(s);
		server.start();
		client.start();
		
		
		
	}
}
