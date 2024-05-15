import java.util.Random;

import javax.swing.ImageIcon;

public class Clyde extends Ghost{
	
	// Az alap értékeket beállítja és betölti az irányokhoz tartozó gifeket
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
	
	// Paraméterként csak a maze-t veszi át, mert mozgása nem függ semelyik másik entitástól
	// Meghívja a saját setTarget függvényét (a megfelelõ paraméterezésen keresztül) amely beállítja a megfelelõ pozíciót
	// A findDirection segítségével megtudja merre indulva a legrövidebb a távolság a target pozíció felé
	// Az õsosztály move függvénye segítségével pozíciót változtat a megfelelõ irányba
	public void move(Maze maze) {
		setTarget(maze);
		findDirection(maze);
		super.move(maze);
	}
	
	// Paraméterként csak a maze-t veszi át, mert mozgása nem függ semelyik másik entitástól
	// Ha keresztezõdésbe ér sorsol egy irányt és az abba az irányba lévõ szomszéd lesz a célpont
	public void setTarget(Maze maze) {
		if(isCrossroads(maze)) {
			Field d = maze.getEntityPosition(this); // Clyde pozíciója
			boolean invalid = true;
			while(invalid) { // amíg nem talál érvényes irányt addig újat sorsol
				Random randnumber = new Random();
				int randomdir = randnumber.nextInt(4); // 0 és 3 közötti random szám sorsolása
				
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
					invalid = false; // Ha a sorsolt mezõ léphetõ megszakítja a ciklust
			}
		}
	}
}
