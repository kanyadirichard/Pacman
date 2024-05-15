import javax.swing.ImageIcon;
import javax.swing.*;

public class Pacman extends Entity{
	private int lives;
	private int points;
	
	// Az alap �rt�keket be�ll�tja �s bet�lti az ir�nyokhoz tartoz� gifeket
	Pacman() {
		speed = 2;
		points = 0;
		lives = 3;
		dir = baseDir = Direction.Left;
		posx = spawnPositionx = 20*14-10;
		posy = spawnPositiony = 80 + 22*20;
		rightpic = new ImageIcon("right.gif").getImage();
		leftpic = new ImageIcon("left.gif").getImage();
		uppic = new ImageIcon("up.gif").getImage();
		downpic = new ImageIcon("down.gif").getImage();		
	}
	
	// Az �soszt�ly move met�dus�t haszn�lja a mozg�shoz
	// A mozg�s ut�n megn�zi hogy az �j poz�ci�n tal�lhat�-e kis vagy nagy bogy�, 
	// ha igen akkor megeszi �s a megfelel� �rt�kkel n� a pontja
	public void move(Maze maze) {
		super.move(maze);
		Field d = maze.getEntityPosition(this);
		
		if(d != null && d.isHasPellet()) {
			d.removePellet();
			addPoints(100); // A kisbogy� 100 pontot �r
		}
		
		if(d != null && d.isHasBigPellet()) {
			d.removeBigPellet();
			addPoints(500); // A nagybogy� 500 pontot �r
		}
		
	}
	
	// Pacman pointjait a kapott �rt�kkel megn�veli
	public void addPoints(int points) {
		this.points += points;
	}
	
	// Visszat�r Pacman pontsz�m�val
	public int getPoints() {
		return points;
	}
	
	// Pacman vesz�t egy �letet
	public void loseLife() {
		lives--;
	}
	
	// Visszat�r Pacman �let�nek �rt�k�vel
	public int getLife() {
		return lives;
	}
}
