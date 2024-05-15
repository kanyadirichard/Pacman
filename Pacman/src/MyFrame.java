import javax.swing.*;

public class MyFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;	
	
	private Game panel;	
	 
	MyFrame(Player player) {
		panel = new Game(player, this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(panel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("Pacman");
		addKeyListener(panel);
		setFocusable(true);
		requestFocus();
	 }  
}
