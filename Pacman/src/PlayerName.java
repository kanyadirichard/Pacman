import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class PlayerName extends JFrame implements ActionListener{
	
	private JTextField text;
	private JButton button;	
	
	PlayerName() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		//setSize(560,720);
		//pack();
		setResizable(false);
		setLocationRelativeTo(null);
		//getContentPane().setBackground(new Color(0, 0, 0)); 
		setTitle("Pacman");
		//setFocusable(true);
		//requestFocus();
		
		button = new JButton("Start");
		text = new JTextField();	
		button.addActionListener(this);
		text.setPreferredSize(new Dimension(250, 40));	
		text.setText("Your name");
		
		add(button);
		add(text);
		pack();
		setVisible(true);	
	}

	// Ha a játékos megnyomja a Start gombot, elindul a játék a megadott névvel
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button) {
			MyFrame f = new MyFrame(new Player(text.getText(), 0));
			dispose();
		}
	}
}
