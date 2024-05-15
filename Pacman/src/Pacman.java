import javax.swing.ImageIcon;
import javax.swing.*;

public class Pacman extends Entity{
	private int lives;
	private int points;
	
	// Az alap értékeket beállítja és betölti az irányokhoz tartozó gifeket
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
	
	// Az õsosztály move metódusát használja a mozgáshoz
	// A mozgás után megnézi hogy az új pozíción található-e kis vagy nagy bogyó, 
	// ha igen akkor megeszi és a megfelelõ értékkel nõ a pontja
	public void move(Maze maze) {
		super.move(maze);
		Field d = maze.getEntityPosition(this);
		
		if(d != null && d.isHasPellet()) {
			d.removePellet();
			addPoints(100); // A kisbogyó 100 pontot ér
		}
		
		if(d != null && d.isHasBigPellet()) {
			d.removeBigPellet();
			addPoints(500); // A nagybogyó 500 pontot ér
		}
		
	}
	
	// Pacman pointjait a kapott értékkel megnöveli
	public void addPoints(int points) {
		this.points += points;
	}
	
	// Visszatér Pacman pontszámával
	public int getPoints() {
		return points;
	}
	
	// Pacman veszít egy életet
	public void loseLife() {
		lives--;
	}
	
	// Visszatér Pacman életének értékével
	public int getLife() {
		return lives;
	}
}
