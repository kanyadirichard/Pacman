import java.util.HashMap;

public class Field {
	
	private int id;
	private boolean canStepOn;
	private int posx;
	private int posy;
	private boolean hasPellet;
	private boolean hasBigPellet;
	
	private HashMap<Direction, Field> neighbors = new HashMap<Direction, Field>();
	
	Field(int nextid, boolean isStep, int x, int y, boolean pellet, boolean bigPellet) {
		id = nextid;
		canStepOn = isStep;
		posx = x;
		posy = y;
		hasPellet = pellet;
		hasBigPellet = bigPellet;
	}
	
	
	// A mez� ID-val t�r vissza
	public int getid() {
		return id;
	}
	
	// A mez� x koordin�t�j�val t�r vissza
	public int getx() {
		return posx;
	}
	
	// A mez� y koordin�t�j�val t�r vissza
	public int gety() {
		return posy;
	}
	
	// Ha a mez� l�phet� igazzal t�r vissza
	public boolean isCanStepOn() {
		return canStepOn;
	}
	
	// Ha a mez�n van bogy� igazzal t�r vissza
	public boolean isHasPellet() {
		return hasPellet;
	}
	
	// Ha a mez�n van bogy� elt�vol�tja r�la
	public void removePellet() {
		hasPellet = false;
	}
	
	// Ha a mez�n van nagy bogy� igazzal t�r vissza
	public boolean isHasBigPellet() {
		return hasBigPellet;
	}
	
	// Ha a mez�n van nagy bogy� elt�vol�tja r�la
	public void removeBigPellet() {
		hasBigPellet = false;
	}
	
//	public void setCanStepOn(boolean canStepOn) {
//		this.canStepOn = canStepOn;
//	}
	
	// A mez� hash mapj�ben a param�terk�nt kapott kulcshoz tartoz� mez�t be�ll�tja a param�terk�nt �tvettre
	public void addNeighbor(Direction d, Field f) {
		neighbors.put(d, f);
	}
	
//	public Field getNeighbor(Direction d) {
//		return neighbors.get(d);
//		
//	}
	
	// A mez� param�terben �tvett ir�nyhoz tartoz� szomsz�dj�t lek�rdezi a Hash mapb�l majd visszat�r vele
	public Field getNeighbor(Direction d) {
        if (neighbors.containsKey(d))
            return neighbors.get(d);
      //	  System.out.println("No neighbor found");
        return null;
    }
}
