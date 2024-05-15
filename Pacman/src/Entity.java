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
	
	// Visszat�r az entit�s kezdeti x poz�ci�j�val
	public int getSpawnPosx() {
		return spawnPositionx;
	}
	
	// Visszat�r az entit�s kezdeti y poz�ci�j�val
	public int getSpawnPosy() {
		return spawnPositiony;
	}
	
	// Visszat�r az entit�s aktu�lis x poz�ci�j�val
	public int getPosx() {
		return posx;
	}
	
	// Visszat�r az entit�s aktu�lis y poz�ci�j�val
	public int getPosy() {
		return posy;
	}
	
	// �t�ll�tja a az aktu�lis x poz�ci�t a param�terben kapottra
	public void setPosx(int newpos) {
		posx = newpos;
	}
	
	// �t�ll�tja a az aktu�lis y poz�ci�t a param�terben kapottra
	public void setPosy(int newpos) {
		posy = newpos;
	}
	
	// Visszat�r az entit�s sebess�g�nek �rt�k�vel
	public int getSpeed() {
		return speed;
	}

	// �t�ll�tja az aktu�lis ir�nyt a param�terben kapott ir�nyra
	public void changeDirection(Direction newdir) {
		dir = newdir;
	}
	
	// Megmondja, hogy a param�terbe kapott mez� fels� szomsz�dja l�phet�-e
	public boolean canGoUp(Field position) {
		// A felt�tel r�vidz�r alapon m�k�dik, vagyis ha a szomsz�d mez� �rt�ket null nem vizsg�lja tov�bb �s hamissal t�r vissza
		return (position.getNeighbor(Direction.Up) != null && position.getNeighbor(Direction.Up).isCanStepOn());
	}
	
	// Megmondja, hogy a param�terbe kapott mez� jobb szomsz�dja l�phet�-e
	public boolean canGoRight(Field position) {
		// A felt�tel r�vidz�r alapon m�k�dik, vagyis ha a szomsz�d mez� �rt�ket null nem vizsg�lja tov�bb �s hamissal t�r vissza
		return (position.getNeighbor(Direction.Right) != null && position.getNeighbor(Direction.Right).isCanStepOn());
	}
	
	// Megmondja, hogy a param�terbe kapott mez� als� szomsz�dja l�phet�-e
	public boolean canGoDown(Field position) {
		// A felt�tel r�vidz�r alapon m�k�dik, vagyis ha a szomsz�d mez� �rt�ket null nem vizsg�lja tov�bb �s hamissal t�r vissza
		return (position.getNeighbor(Direction.Down) != null && position.getNeighbor(Direction.Down).isCanStepOn());
	}
	
	// Megmondja, hogy a param�terbe kapott mez� bal szomsz�dja l�phet�-e
	public boolean canGoLeft(Field position) {
		// A felt�tel r�vidz�r alapon m�k�dik, vagyis ha a szomsz�d mez� �rt�ket null nem vizsg�lja tov�bb �s hamissal t�r vissza
		return (position.getNeighbor(Direction.Left) != null && position.getNeighbor(Direction.Left).isCanStepOn());
	}
	
	// Param�terk�nt �tveszi a p�ly�t
	// Az alap mozg�s met�dus, ezt haszn�lja az �sszes lesz�rmazott is valamilyen form�ban
	// Ha mez�n �ll ir�nyt v�ltoztathat, ha nem, akkor csak halad tov�bb az eredeti ir�nyba
	public void move(Maze maze) {
		Field p = null;
		Field d = maze.getEntityPosition(this); // Az entit�s poz�ci�ja
		if(isOnField(d)) { // Ha mez�n �ll ir�nyt v�ltoztathat
			
			// Ha van elmentett ir�nyv�ltoztat�s megvizsg�lja, hogy v�grehajthat�-e �s ha igen akkor v�grahajtja
			if(nextdirActive && d.getNeighbor(nextdir) != null && d.getNeighbor(nextdir).isCanStepOn()) {
				p = d.getNeighbor(nextdir); // A megadott ir�nyba l�v� szomsz�d mez�
				dir = nextdir; // V�ltozik az ir�ny
				nextdirActive = false; // T�bb� m�r nem lesz akt�v a k�vetkez� ir�ny, mert megt�rt�nt az ir�nyv�lt�s
			}
			else { // Ha nem lehet ir�nytv�ltoztatni a k�vetkez� mez� az entit�s el�tti mez� lesz
				p = d.getNeighbor(dir);
			}
			if(p != null && p.isCanStepOn()) { // Az el�z� felt�tel else �ga miatt fontos vizsg�lni, hogy a k�vetkez� mez� l�phet�-e
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
		
		// Ha 2 mez� k�z�tt van akkor halad tov�bb az eredeti ir�nyba
		// Att�l f�gg�en, hogy milyen ir�nyba n�z a megfelel� poz�ci� koordin�t�t �ll�tja speed egys�gnyivel
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
		
		// Ha a baloldali teleport mez�re l�p �tteleport�l a jobboldali teleport mez�re
		// vagyis a poz�ci�ja a jobboldali teleport mez� poz�ci�ja lesz
		if(posx == leftTeleportx && posy == leftTeleporty) {
			posx = rightTeleportx;
			posy = rightTeleporty;
			dir = Direction.Left;
		}
		
		// Ha a jobboldali teleport mez�re l�p �tteleport�l a baloldali teleport mez�re
		// vagyis a poz�ci�ja a baloldali teleport mez� poz�ci�ja lesz
		else if(posx == rightTeleportx && posy == rightTeleporty) {
			posx = leftTeleportx;
			posy = leftTeleporty;
			dir = Direction.Right;
		}
	}
	
	// Az entit�s ir�ny�hoz tartoz� giffel t�r vissza
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

	// A k�vetkez� ir�nnyal t�r vissza
	public Direction getNextdir() {
		return nextdir;
	}

	// A param�terben kapott ir�ny lesz a k�vetkez� ir�ny
	public void setNextdir(Direction nextd) {
		nextdir = nextd;
	}
	
	// Az entit�s ir�ny�val t�r vissza
	public Direction getDir() {
		return dir;
	}

	// Megvizsg�lja, hogy van-e k�vetkez� ir�ny
	public boolean isActive() {
		return nextdirActive;
	}

	// Aktiv�lja/deaktiv�lja a k�vetkez� ir�nyt
	public void setActive(boolean active) {
		nextdirActive = active;
	}
	
	// A kapott mez� ha nem null igazzal t�r vissza, vagyis a p�lya egy mez�j�r�l besz�l�nk 
	public boolean isOnField(Field f) {
		return (f != null);
	}
	
	// Vissza�ll�tja az entit�s poz�ci�j�t �s ir�ny�t a kezd��rt�kekre
	public void reset() {
		posx = spawnPositionx;
		posy = spawnPositiony;
		dir = baseDir;
	}
}
