import java.util.Random;

import javax.swing.ImageIcon;

public class Clyde extends Ghost{
	
	// Az alap �rt�keket be�ll�tja �s bet�lti az ir�nyokhoz tartoz� gifeket
	Clyde() {
		dir = baseDir = Direction.Left;
		//posx = spawnPositionx = 20;
		//posy = spawnPositiony = 80;
		targetx = baseTargetx = 20;
		targety = baseTargety = 32*20;
		rightpic = new ImageIcon("c_right.gif").getImage();
		leftpic = new ImageIcon("c_left.gif").getImage();
		uppic = new ImageIcon("c_up.gif").getImage();
		downpic = new ImageIcon("c_down.gif").getImage();	
	}
	
	// Param�terk�nt csak a maze-t veszi �t, mert mozg�sa nem f�gg semelyik m�sik entit�st�l
	// Megh�vja a saj�t setTarget f�ggv�ny�t (a megfelel� param�terez�sen kereszt�l) amely be�ll�tja a megfelel� poz�ci�t
	// A findDirection seg�ts�g�vel megtudja merre indulva a legr�videbb a t�vols�g a target poz�ci� fel�
	// Az �soszt�ly move f�ggv�nye seg�ts�g�vel poz�ci�t v�ltoztat a megfelel� ir�nyba
	public void move(Maze maze) {
		setTarget(maze);
		findDirection(maze);
		super.move(maze);
	}
	
	// Param�terk�nt csak a maze-t veszi �t, mert mozg�sa nem f�gg semelyik m�sik entit�st�l
	// Ha keresztez�d�sbe �r sorsol egy ir�nyt �s az abba az ir�nyba l�v� szomsz�d lesz a c�lpont
	public void setTarget(Maze maze) {
		if(isCrossroads(maze)) {
			Field d = maze.getEntityPosition(this); // Clyde poz�ci�ja
			boolean invalid = true;
			while(invalid) { // am�g nem tal�l �rv�nyes ir�nyt addig �jat sorsol
				Random randnumber = new Random();
				int randomdir = randnumber.nextInt(4); // 0 �s 3 k�z�tti random sz�m sorsol�sa
				
				switch(randomdir) {
					case 0: nextdir = Direction.Up;
							setTarget(d.getNeighbor(Direction.Up).getx(), d.getNeighbor(Direction.Up).gety());
						break;
						
					case 1: nextdir = Direction.Right; 
							setTarget(d.getNeighbor(Direction.Right).getx(), d.getNeighbor(Direction.Right).gety());
						break;
						
					case 2: nextdir = Direction.Down;
							setTarget(d.getNeighbor(Direction.Down).getx(), d.getNeighbor(Direction.Down).gety());
						break;
						
					case 3: nextdir = Direction.Left;
							setTarget(d.getNeighbor(Direction.Left).getx(), d.getNeighbor(Direction.Left).gety());
						break;
				}
				//nextdirActive = true;
				if(d.getNeighbor(nextdir) != null && d.getNeighbor(nextdir).isCanStepOn()) 
					invalid = false; // Ha a sorsolt mez� l�phet� megszak�tja a ciklust
			}
		}
	}
}
