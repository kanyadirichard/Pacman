import java.awt.Image;

public class Entity {
	protected int speed;
	protected int spawnPositionx;
	protected int spawnPositiony;
	protected int posx;
	protected int posy;
	protected final int leftTeleportx = 0;
	protected final int leftTeleporty = 17*20;
	protected final int rightTeleportx = 27*20;
	protected final int rightTeleporty = 17*20;
	protected Direction baseDir;
	protected Direction dir;
	protected Direction nextdir;
	protected boolean nextdirActive;
	
	protected Image leftpic, rightpic, uppic, downpic;
	
	Entity() {
		speed = 3;
		setActive(false);
	}
	
	// Visszatér az entitás kezdeti x pozíciójával
	public int getSpawnPosx() {
		return spawnPositionx;
	}
	
	// Visszatér az entitás kezdeti y pozíciójával
	public int getSpawnPosy() {
		return spawnPositiony;
	}
	
	// Visszatér az entitás aktuális x pozíciójával
	public int getPosx() {
		return posx;
	}
	
	// Visszatér az entitás aktuális y pozíciójával
	public int getPosy() {
		return posy;
	}
	
	// Átállítja a az aktuális x pozíciót a paraméterben kapottra
	public void setPosx(int newpos) {
		posx = newpos;
	}
	
	// Átállítja a az aktuális y pozíciót a paraméterben kapottra
	public void setPosy(int newpos) {
		posy = newpos;
	}
	
	// Visszatér az entitás sebességének értékével
	public int getSpeed() {
		return speed;
	}

	// Átállítja az aktuális irányt a paraméterben kapott irányra
	public void changeDirection(Direction newdir) {
		dir = newdir;
	}
	
	// Megmondja, hogy a paraméterbe kapott mezõ felsõ szomszédja léphetõ-e
	public boolean canGoUp(Field position) {
		// A feltétel rövidzár alapon mûködik, vagyis ha a szomszéd mezõ értéket null nem vizsgálja tovább és hamissal tér vissza
		return (position.getNeighbor(Direction.Up) != null && position.getNeighbor(Direction.Up).isCanStepOn());
	}
	
	// Megmondja, hogy a paraméterbe kapott mezõ jobb szomszédja léphetõ-e
	public boolean canGoRight(Field position) {
		// A feltétel rövidzár alapon mûködik, vagyis ha a szomszéd mezõ értéket null nem vizsgálja tovább és hamissal tér vissza
		return (position.getNeighbor(Direction.Right) != null && position.getNeighbor(Direction.Right).isCanStepOn());
	}
	
	// Megmondja, hogy a paraméterbe kapott mezõ alsó szomszédja léphetõ-e
	public boolean canGoDown(Field position) {
		// A feltétel rövidzár alapon mûködik, vagyis ha a szomszéd mezõ értéket null nem vizsgálja tovább és hamissal tér vissza
		return (position.getNeighbor(Direction.Down) != null && position.getNeighbor(Direction.Down).isCanStepOn());
	}
	
	// Megmondja, hogy a paraméterbe kapott mezõ bal szomszédja léphetõ-e
	public boolean canGoLeft(Field position) {
		// A feltétel rövidzár alapon mûködik, vagyis ha a szomszéd mezõ értéket null nem vizsgálja tovább és hamissal tér vissza
		return (position.getNeighbor(Direction.Left) != null && position.getNeighbor(Direction.Left).isCanStepOn());
	}
	
	// Paraméterként átveszi a pályát
	// Az alap mozgás metódus, ezt használja az összes leszármazott is valamilyen formában
	// Ha mezõn áll irányt változtathat, ha nem, akkor csak halad tovább az eredeti irányba
	public void move(Maze maze) {
		Field p = null;
		Field d = maze.getEntityPosition(this); // Az entitás pozíciója
		if(isOnField(d)) { // Ha mezõn áll irányt változtathat
			
			// Ha van elmentett irányváltoztatás megvizsgálja, hogy végrehajtható-e és ha igen akkor végrahajtja
			if(nextdirActive && d.getNeighbor(nextdir) != null && d.getNeighbor(nextdir).isCanStepOn()) {
				p = d.getNeighbor(nextdir); // A megadott irányba lévõ szomszéd mezõ
				dir = nextdir; // Változik az irány
				nextdirActive = false; // Többé már nem lesz aktív a következõ irány, mert megtörtént az irányváltás
			}
			else { // Ha nem lehet iránytváltoztatni a következõ mezõ az entitás elõtti mezõ lesz
				p = d.getNeighbor(dir);
			}
			if(p != null && p.isCanStepOn()) { // Az elõzõ feltétel else ága miatt fontos vizsgálni, hogy a következõ mezõ léphetõ-e
				if(dir.equals(Direction.Right)) { 
						posx += speed;
				}
				else if(dir.equals(Direction.Left)) {
							posx -= speed;
					}
					else if(dir.equals(Direction.Up)) {
								posy -= speed;
						}
						else if(p.isCanStepOn())
								posy += speed;
			}
		}
		
		// Ha 2 mezõ között van akkor halad tovább az eredeti irányba
		// Attól függõen, hogy milyen irányba néz a megfelelõ pozíció koordinátát állítja speed egységnyivel
		// hiszen egy tick alatt ennyit mozog
		else {
			if(dir.equals(Direction.Right)) 
				posx += speed; 
			if(dir.equals(Direction.Left))
				posx -= speed;
			if(dir.equals(Direction.Up))
				posy -= speed;
			if(dir.equals(Direction.Down))
				posy += speed;
		}
		
		// Ha a baloldali teleport mezõre lép átteleportál a jobboldali teleport mezõre
		// vagyis a pozíciója a jobboldali teleport mezõ pozíciója lesz
		if(posx == leftTeleportx && posy == leftTeleporty) {
			posx = rightTeleportx;
			posy = rightTeleporty;
			dir = Direction.Left;
		}
		
		// Ha a jobboldali teleport mezõre lép átteleportál a baloldali teleport mezõre
		// vagyis a pozíciója a baloldali teleport mezõ pozíciója lesz
		else if(posx == rightTeleportx && posy == rightTeleporty) {
			posx = leftTeleportx;
			posy = leftTeleporty;
			dir = Direction.Right;
		}
	}
	
	// Az entitás irányához tartozó giffel tér vissza
	public Image getDirPic() {
		if(dir.equals(Direction.Right))
			return rightpic;
		else if(dir.equals(Direction.Left))
			return leftpic;
			else if(dir.equals(Direction.Up))
				return uppic;
				else
					return downpic;
	}

	// A következõ iránnyal tér vissza
	public Direction getNextdir() {
		return nextdir;
	}

	// A paraméterben kapott irány lesz a következõ irány
	public void setNextdir(Direction nextd) {
		nextdir = nextd;
	}
	
	// Az entitás irányával tér vissza
	public Direction getDir() {
		return dir;
	}

	// Megvizsgálja, hogy van-e következõ irány
	public boolean isActive() {
		return nextdirActive;
	}

	// Aktiválja/deaktiválja a következõ irányt
	public void setActive(boolean active) {
		nextdirActive = active;
	}
	
	// A kapott mezõ ha nem null igazzal tér vissza, vagyis a pálya egy mezõjérõl beszélünk 
	public boolean isOnField(Field f) {
		return (f != null);
	}
	
	// Visszaállítja az entitás pozícióját és irányát a kezdõértékekre
	public void reset() {
		posx = spawnPositionx;
		posy = spawnPositiony;
		dir = baseDir;
	}
}
