import javax.swing.ImageIcon;

public class Pinky extends Ghost{
	
	// Az alap �rt�keket be�ll�tja �s bet�lti az ir�nyokhoz tartoz� gifeket
	Pinky() {
		dir = baseDir = Direction.Left;
		//posx = spawnPositionx = 20*14;
		//posy = spawnPositiony = 300;
		targetx = baseTargetx = 20;
		targety = baseTargety = 4*20;
		rightpic = new ImageIcon("p_right.gif").getImage();
		leftpic = new ImageIcon("p_left.gif").getImage();
		uppic = new ImageIcon("p_up.gif").getImage();
		downpic = new ImageIcon("p_down.gif").getImage();
	}
	
	// Param�terk�nt �tveszi a p�ly�t �s a c�lpont entit�st
	// Megh�vja a saj�t setTarget f�ggv�ny�t (a megfelel� param�terez�sen kereszt�l) amely be�ll�tja a megfelel� poz�ci�t
	// A findDirection seg�ts�g�vel megtudja merre indulva a legr�videbb a t�vols�g a target poz�ci� fel�
	// Az �soszt�ly move f�ggv�nye seg�ts�g�vel poz�ci�t v�ltoztat a megfelel� ir�nyba
	public void move(Maze maze, Entity target) {
		setTarget(target);
		findDirection(maze);
		super.move(maze);
	}
	
	// Param�terk�nt �tveszi a c�lpont entit�st
	// Az ir�nyt�l f�gg�en a megfelel� param�terrekl be�ll�tja a c�lpont poz�ci�t
	// Mivel Pinky a Pacman el�tt l�v� 4. mez�t veszi c�lba, ez�rt amelyik ir�nyba n�z, ha fel vagy le, akkor az y koordin�t�t
	// ha jobbra vagy balra n�z akkor az x koordin�t�t tolja el 4*20(a 20 a blokk m�rete)-al
	public void setTarget(Entity target) {
		if(target.getDir() == Direction.Up)
			setTarget(target.getPosx(), target.getPosy() - 4*20);
		else if(target.getDir() == Direction.Right)
			setTarget(target.getPosx() + 4*20, target.getPosy());
		else if(target.getDir() == Direction.Down)
			setTarget(target.getPosx(), target.getPosy() + 4*20);
		else
			setTarget(target.getPosx()- 4*20, target.getPosy());
	}
}
