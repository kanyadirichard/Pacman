import javax.swing.ImageIcon;

public class Inky extends Ghost{
	
	// Az alap �rt�keket be�ll�tja �s bet�lti az ir�nyokhoz tartoz� gifeket
	Inky() {
		dir = baseDir = Direction.Right; 
		//posx = spawnPositionx = 20*14-10;
		//posy = spawnPositiony = 80 + 10*20;
		targetx = baseTargetx = 26*20;
		targety = baseTargety = 32*20;
		rightpic = new ImageIcon("i_right.gif").getImage();
		leftpic = new ImageIcon("i_left.gif").getImage();
		uppic = new ImageIcon("i_up.gif").getImage();
		downpic = new ImageIcon("i_down.gif").getImage();
	}
	
	// Param�terk�nt �tveszi a p�ly�t, blinkyt �s pacmant
	// Megh�vja a saj�t setTarget f�ggv�ny�t (a megfelel� param�terez�sen kereszt�l) amely be�ll�tja a megfelel� poz�ci�t
	// A findDirection seg�ts�g�vel megtudja merre indulva a legr�videbb a t�vols�g a target poz�ci� fel�
	// Az �soszt�ly move f�ggv�nye seg�ts�g�vel poz�ci�t v�ltoztat a megfelel� ir�nyba
	public void move(Maze maze, Entity blinky, Entity pacman) {
		setTarget(blinky, pacman);
		findDirection(maze);
		super.move(maze);
	}
	
	// Param�terk�nt �tveszi blinkyt �s pacmant
	// Inky a pacman el�tt 2-vel l�v� mez�re t�kr�zi Blinky poz�ci�j�t �s azt a mez�t veszi c�lba
	// A t�kr�z�st az eltolt poz�ci� 2-szeres�b�l Blinky poz�ci�ja kivon�s�val sz�molom
	public void setTarget(Entity blinky, Entity pacman) {
		int tempx = pacman.getPosx();
		int tempy = pacman.getPosy();
		
		if(pacman.getDir() == Direction.Up)
			tempy -= 2*20;
		else if(pacman.getDir() == Direction.Right)
			tempx += 2*20;
		else if(pacman.getDir() == Direction.Down)
			tempy += 2*20;
		else 
			tempx -= 2*20;
		
		setTarget(2*tempx - blinky.getPosx(), 2*tempy - blinky.getPosy());
		
	}
}
