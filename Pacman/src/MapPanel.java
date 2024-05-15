import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;


public class MapPanel extends JPanel implements KeyListener{
	
	private static final long serialVersionUID = 1L;
	
	private Image map;
	private Pacman pacman;
	private Clyde clyde;
	private Inky inky;
	private Blinky blinky;
	private Pinky pinky;
	private Timer timer;
	private Maze maze;
	
	// L�trehozza Pacmant, Clydeot, Blinkyt, Pinkyt, Inkyt �s a p�ly�t, bet�lti a h�tt�rk�pet �s elind�tja a Timert, ami
	// ami tickenk�nt mozgatja az entit�sokat, �jra rajzolja a k�perny�t, megvizsg�lja, hogy Pacman �tk�z�tt-e sz�rnnyel 
	// �s, hogy v�get �rt-e a j�t�k
	MapPanel() {
		
		//sleep = false;
		pacman = new Pacman();
		clyde = new Clyde();
		blinky = new Blinky();
		pinky = new Pinky();
		inky = new Inky();
		maze = new Maze();
		map = new ImageIcon("PacMan_background.png").getImage();
		this.setPreferredSize(new Dimension(560,720));		
		timer = new Timer(5, action->{
				pacman.move(maze);
				clyde.move(maze); 
				blinky.move(maze, pacman); 
				pinky.move(maze, pacman); 
				inky.move(maze, blinky, pacman); 
				repaint();
				pacmanDies();
				endGame();
				}
		);
		timer.start();
	}

	// Az entit�sokat, a h�tt�rk�pet, a j�t�kos pontjait, �let�t �s a bogy�kat kirajzolja a k�perny�re
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.drawImage(map, 0, 0, null);
		g2D.setFont(g2D.getFont().deriveFont(18.0f));
		g2D.setColor(Color.WHITE);
		g2D.drawString("Points: " + pacman.getPoints(), 20, 35);
		g2D.drawString("Lives: " + pacman.getLife(), 480, 35);
		g2D.drawImage(pacman.getDirPic(), pacman.getPosx(), pacman.getPosy(), this);
		g2D.drawImage(clyde.getDirPic(), clyde.getPosx(), clyde.getPosy(), this);
		g2D.drawImage(blinky.getDirPic(), blinky.getPosx(), blinky.getPosy(), this);
		g2D.drawImage(pinky.getDirPic(), pinky.getPosx(), pinky.getPosy(), this);
		g2D.drawImage(inky.getDirPic(), inky.getPosx(), inky.getPosy(), this);
		
		/*// A szellemek c�lpont mez�inek kirajzol�sa 
		g2D.setColor(Color.ORANGE);
		g2D.fillOval(clyde.getTargetx(), clyde.getTargety(), 15, 15);
		
		g2D.setColor(Color.RED);
		g2D.fillOval(blinky.getTargetx(), blinky.getTargety(), 15, 15);
		
		g2D.setColor(Color.BLUE);
		g2D.fillOval(inky.getTargetx(), inky.getTargety(), 15, 15);
		
		g2D.setColor(Color.PINK);
		g2D.fillOval(pinky.getTargetx(), pinky.getTargety(), 15, 15);
		*/
		
		for(int i = 0; i < 31*28; i++) {
			Field temp = maze.getMap().get(i);
			if(temp.isHasPellet()) {
				g2D.setColor(Color.YELLOW);
				g2D.fillOval(temp.getx()+7, temp.gety()+7, 5, 5);
			}
			if(temp.isHasBigPellet()) {
				g2D.setColor(Color.YELLOW);
				g2D.fillOval(temp.getx()+3, temp.gety()+3, 15, 15);
			}
		}
	}

	// Ha a j�t�kos lenyom egy �rv�nyes billenty�t a megfelel� ir�nyhoz kapcsol�d� nextDirection met�dust h�vja
	// w - felfele, d - jobbra, s -lefele, a - balra
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyChar()) {
			case 'a': nextDirection(Direction.Left);
				break;
			
			case 'd': nextDirection(Direction.Right);
				break;
				
			case 'w': nextDirection(Direction.Up);
				break;
				
			case 's': nextDirection(Direction.Down);
				break;
		}	
	}
	
	// Aktiv�lja �s a param�terk�nt kapott ir�nyt be�ll�tja Pacman k�vetkez� ir�nyak�nt
	public void nextDirection(Direction newd) {
			pacman.setActive(true);
			pacman.setNextdir(newd);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	
	public void endGame() {
		if(pacman.getPoints() == 26000) {
			timer.stop();
		}
	}
	
	// Ha Pacman a szellemek 20-as sugar� k�rnyezet�be �r vesz�t egy �letet �s minden entit�s alap �llapotba ker�l
	public void pacmanDies() {
		int tempx = pacman.getPosx();
		int tempy = pacman.getPosy();
		
		// A t�vols�got pacman �s a szellem koordin�t�i k�l�nbs�g�nek n�gyzet�sszeg�nek gy�k al� von�s�val sz�molja
		// Ha b�rmelyik szellem 20-n�l kisebb t�vols�gba ker�l Pacmant�l, Pacman meghal
		if((Math.sqrt(Math.pow(tempx - clyde.getPosx(), 2) + Math.pow(tempy - clyde.getPosy(), 2)) < 20) ||
		   (Math.sqrt(Math.pow(tempx - pinky.getPosx(), 2) + Math.pow(tempy - pinky.getPosy(), 2)) < 20) ||
		   (Math.sqrt(Math.pow(tempx - inky.getPosx(), 2) + Math.pow(tempy - inky.getPosy(), 2)) < 20) ||
		   (Math.sqrt(Math.pow(tempx - blinky.getPosx(), 2) + Math.pow(tempy - blinky.getPosy(), 2)) < 20)) {

			resetEntities();
			pacman.loseLife();
		}
	}
	
	// Minden entit�s alaphelyzetbe ker�l
	public void resetEntities() {
		pacman.reset();
		pinky.reset();
		inky.reset();
		blinky.reset();
		clyde.reset();
	}
}
