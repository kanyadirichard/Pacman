import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;

public class Game extends JPanel implements KeyListener{
	
	private static final long serialVersionUID = 1L;
	
	private Image map; 
	private Pacman pacman;
	private Clyde clyde;
	private Inky inky;
	private Blinky blinky;
	private Pinky pinky;
	private Timer timer;
	private Maze maze;
	
	private PlayerList players;
	
	// Bet�lti a j�t�kosok adatait
	// L�trehozza Pacmant, Clydeot, Blinkyt, Pinkyt, Inkyt �s a p�ly�t, bet�lti a h�tt�rk�pet �s elind�tja a Timert, ami
	// ami tickenk�nt mozgatja az entit�sokat, �jra rajzolja a k�perny�t, megvizsg�lja, hogy Pacman �tk�z�tt-e sz�rnnyel 
	// �s, hogy v�get �rt-e a j�t�k
	Game(Player player, MyFrame frame) {
		
		players = new PlayerList();
		
		try {
			players.load();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for(Player p : players.getPlayers())
			System.out.println(p.toString());
		
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
				endGame(player, frame); 
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
		/*
		for(Field i : maze.getMap())
        {
            g2D.setPaint(Color.yellow);
            g2D.fillOval(i.getx(), i.gety(), 16, 16);
        }*/
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
	
	
	// Param�terk�nt kapja az aktu�lis j�t�kost
	// Ha a j�t�kos el�rte a maxim�lis pontsz�mot vagy elfogyott minden �lete v�ge a j�t�knak
	// Ekkor az id�z�t� le�ll �s ha a j�t�kos pontsz�ma a 10 legjobb k�z�tt van hozz�adja (ha kevesebb eredm�ny van mindig hozz�adja)
	// Ha m�r volt 10 eredm�ny, de az �j pontsz�m jobb valamelyikn�l, akkor a legalacsonyabb pontsz�mhoz tartoz� j�t�kost t�rli a list�b�l
	// V�g�l rendezi a list�t pontsz�m szerint �s f�jlba ment, ezut�n egy f�ggv�nyh�v�son kereszt�l megjelen�ti a ranglist�t
	public void endGame(Player player, MyFrame frame) { 
		if(pacman.getPoints() == 26000 || pacman.getLife() == 0) {
			timer.stop();
			players.getPlayers().sort(null);
			if(players.getPlayers().size() < 10 || players.getPlayers().get(9).getScore() < pacman.getPoints()) {
				//System.out.println(players.getPlayers().get(0).getScore());
				if(10 <= players.getPlayers().size())
					players.delete(9);
				players.add(player.getName(), pacman.getPoints());
				//PlayerName playerName = new PlayerName(players, pacman.getPoints()); 
				//playerName.closeWindow();
			}
			players.getPlayers().sort(null);
			
			players.save();
			frame.dispose();
			Menu leaderboard = new Menu(players);
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
