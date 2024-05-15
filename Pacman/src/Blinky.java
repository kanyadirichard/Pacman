import javax.swing.ImageIcon;
import java.lang.Math;

public class Blinky extends Ghost{
	
	// Az alap �rt�keket be�ll�tja �s bet�lti az ir�nyokhoz tartoz� gifeket
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
	
	// A param�terben �tvett target x �s y koordin�t�j�t be�ll�tja saj�t c�lpont koordin�t�ik�nt
	// A findDirection seg�ts�g�vel megtudja merre indulva a legr�videbb a t�vols�g a target poz�ci� fel�
	// Az �soszt�ly move f�ggv�nye seg�ts�g�vel poz�ci�t v�ltoztat a megfelel� ir�nyba
	public void move(Maze maze, Entity target) {
		setTarget(target.getPosx(), target.getPosy());
		findDirection(maze);
		super.move(maze);
	}
}
