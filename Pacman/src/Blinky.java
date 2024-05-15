import javax.swing.ImageIcon;
import java.lang.Math;

public class Blinky extends Ghost{
	
	// Az alap értékeket beállítja és betölti az irányokhoz tartozó gifeket
	Blinky() {
		dir = baseDir = Direction.Right;
		//posx = spawnPositionx = 20;
		//posy = spawnPositiony = 80;
		targetx = baseTargetx = 26*20;
		targety = baseTargety = 4*20;
		rightpic = new ImageIcon("b_right.gif").getImage();
		leftpic = new ImageIcon("b_left.gif").getImage();
		uppic = new ImageIcon("b_up.gif").getImage();
		downpic = new ImageIcon("b_down.gif").getImage();
	}
	
	// A paraméterben átvett target x és y koordinátáját beállítja saját célpont koordinátáiként
	// A findDirection segítségével megtudja merre indulva a legrövidebb a távolság a target pozíció felé
	// Az õsosztály move függvénye segítségével pozíciót változtat a megfelelõ irányba
	public void move(Maze maze, Entity target) {
		setTarget(target.getPosx(), target.getPosy());
		findDirection(maze);
		super.move(maze);
	}
}
