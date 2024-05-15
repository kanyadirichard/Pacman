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
	
	
	// A mezõ ID-val tér vissza
	public int getid() {
		return id;
	}
	
	// A mezõ x koordinátájával tér vissza
	public int getx() {
		return posx;
	}
	
	// A mezõ y koordinátájával tér vissza
	public int gety() {
		return posy;
	}
	
	// Ha a mezõ léphetõ igazzal tér vissza
	public boolean isCanStepOn() {
		return canStepOn;
	}
	
	// Ha a mezõn van bogyó igazzal tér vissza
	public boolean isHasPellet() {
		return hasPellet;
	}
	
	// Ha a mezõn van bogyó eltávolítja róla
	public void removePellet() {
		hasPellet = false;
	}
	
	// Ha a mezõn van nagy bogyó igazzal tér vissza
	public boolean isHasBigPellet() {
		return hasBigPellet;
	}
	
	// Ha a mezõn van nagy bogyó eltávolítja róla
	public void removeBigPellet() {
		hasBigPellet = false;
	}
	
//	public void setCanStepOn(boolean canStepOn) {
//		this.canStepOn = canStepOn;
//	}
	
	// A mezõ hash mapjében a paraméterként kapott kulcshoz tartozó mezõt beállítja a paraméterként átvettre
	public void addNeighbor(Direction d, Field f) {
		neighbors.put(d, f);
	}
	
//	public Field getNeighbor(Direction d) {
//		return neighbors.get(d);
//		
//	}
	
	// A mezõ paraméterben átvett irányhoz tartozó szomszédját lekérdezi a Hash mapbõl majd visszatér vele
	public Field getNeighbor(Direction d) {
        if (neighbors.containsKey(d))
            return neighbors.get(d);
      //	  System.out.println("No neighbor found");
        return null;
    }
}
