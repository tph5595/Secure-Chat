import javax.swing.JFrame;

public class Driver {

	public static void main(String[] args) {
		JFrame frame = new JFrame("SBHS Chat");
		frame.setSize(700,450);
		frame.setLocation(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new ChatPanel(frame));
		frame.setVisible(true);

	}

}
