import javax.swing.ImageIcon;

public class Inky extends Ghost{
	
	// Az alap értékeket beállítja és betölti az irányokhoz tartozó gifeket
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
	
	// Paraméterként átveszi a pályát, blinkyt és pacmant
	// Meghívja a saját setTarget függvényét (a megfelelõ paraméterezésen keresztül) amely beállítja a megfelelõ pozíciót
	// A findDirection segítségével megtudja merre indulva a legrövidebb a távolság a target pozíció felé
	// Az õsosztály move függvénye segítségével pozíciót változtat a megfelelõ irányba
	public void move(Maze maze, Entity blinky, Entity pacman) {
		setTarget(blinky, pacman);
		findDirection(maze);
		super.move(maze);
	}
	
	// Paraméterként átveszi blinkyt és pacmant
	// Inky a pacman elõtt 2-vel lévõ mezõre tükrözi Blinky pozícióját és azt a mezõt veszi célba
	// A tükrözést az eltolt pozíció 2-szeresébõl Blinky pozíciója kivonásával számolom
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
