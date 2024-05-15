import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.FlowView.FlowStrategy;

public class Start extends JFrame implements ActionListener{
	
	private JTextField text;
	private JButton startButton;
	private JButton leaderboardButton;
	private JButton exitButton;
	
	Start() {
		//JPanel startmenu = new JPanel();
		//startmenu.setLayout(new BoxLayout(startmenu, BoxLayout.PAGE_AXIS));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		//setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		setPreferredSize(new Dimension(560,560));
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setBackground(Color.BLACK); 
		setTitle("Pacman");
		setFocusable(true);
		requestFocus();
		
		//JPanel start = new JPanel(new FlowLayout());
		//start.setBackground(Color.BLACK);
		//start.setPreferredSize(new Dimension(560 , 100));
		/*
		JLabel name = new JLabel("Please give your name below");
		name.setBounds(200, 10, 50, 30);
		name.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		name.setForeground(Color.YELLOW);
		*/
		startButton = new JButton("Start");
		leaderboardButton = new JButton("Leaderboard");
		exitButton = new JButton("Exit");
		text = new JTextField();	
		text.setBounds(210, 50, 140, 25);
		text.setBackground(Color.YELLOW);
		startButton.addActionListener(this);
		startButton.setBackground(Color.YELLOW);
		startButton.setFont(new Font("TimesRoman", Font.BOLD, 30));
		startButton.setBounds(180, 100, 200, 70);
		leaderboardButton.setBackground(Color.YELLOW);
		leaderboardButton.setFont(new Font("TimesRoman", Font.BOLD, 25));
		leaderboardButton.setBounds(180, 250, 200, 70);
		leaderboardButton.addActionListener(this);
		exitButton.setBackground(Color.YELLOW);
		exitButton.setFont(new Font("TimesRoman", Font.BOLD, 30));
		exitButton.setBounds(180, 400, 200, 70);
		exitButton.addActionListener(this);
		text.setPreferredSize(new Dimension(250, 40));	
		text.setText("Your name");
		text.setFont(new Font("TimesRoman", Font.ITALIC, 10));
		
		//leaderboard.setLayout();
		//add(name);
		add(startButton);
		add(text);
		
		//pack();
		//add(startmenu);
		//add(start);
		add(leaderboardButton);
		add(exitButton);
		pack();
		
		setVisible(true);	
	}

	// Ha a játékos megnyomja a Start gombot, elindul a játék a megadott névvel
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == startButton) {
			MyFrame f = new MyFrame(new Player(text.getText(), 0));
			dispose();
		}
		
		if(e.getSource() == leaderboardButton) {
			PlayerList list = new PlayerList();
			try {
				list.load();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			Menu leaderboard = new Menu(list);
		}
		
		if(e.getSource() == exitButton) {
			dispose();
		}
	}
}
