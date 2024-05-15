import javax.swing.ImageIcon;

public class Pinky extends Ghost{
	
	// Az alap értékeket beállítja és betölti az irányokhoz tartozó gifeket
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
	
	// Paraméterként átveszi a pályát és a célpont entitást
	// Meghívja a saját setTarget függvényét (a megfelelõ paraméterezésen keresztül) amely beállítja a megfelelõ pozíciót
	// A findDirection segítségével megtudja merre indulva a legrövidebb a távolság a target pozíció felé
	// Az õsosztály move függvénye segítségével pozíciót változtat a megfelelõ irányba
	public void move(Maze maze, Entity target) {
		setTarget(target);
		findDirection(maze);
		super.move(maze);
	}
	
	// Paraméterként átveszi a célpont entitást
	// Az iránytól függõen a megfelelõ paraméterrekl beállítja a célpont pozíciót
	// Mivel Pinky a Pacman elõtt lévõ 4. mezõt veszi célba, ezért amelyik irányba néz, ha fel vagy le, akkor az y koordinátát
	// ha jobbra vagy balra néz akkor az x koordinátát tolja el 4*20(a 20 a blokk mérete)-al
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
