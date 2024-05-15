import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

public class Menu extends JFrame implements ActionListener{
	
	JButton closeButton;
	
	Menu(PlayerList players) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(560,720);
		//pack();
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(0, 0, 0)); 
		setVisible(true);
		setTitle("Pacman");
		setFocusable(true);
		requestFocus();
		setLayout(new GridLayout(11, 1));
		listLeaderboard(players);
		
		closeButton = new JButton("Close");
		closeButton.setBackground(Color.YELLOW);
		closeButton.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		closeButton.setBounds(0, 80, 10, 10);
		closeButton.addActionListener(this);
		
		add(closeButton);
	 } 
	
	
	public void listLeaderboard(PlayerList players) {
		for(int i = 0; i < players.getPlayers().size(); i++) {
			JLabel temp = new JLabel(players.getPlayers().get(i).toString());
			temp.setForeground(Color.YELLOW);
			temp.setFont(new Font("TimesRoman", Font.PLAIN, 30));
			temp.setHorizontalTextPosition(JLabel.CENTER);
			add(temp);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == closeButton) 
			dispose();
		
	}
}
