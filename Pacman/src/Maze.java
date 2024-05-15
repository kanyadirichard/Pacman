import java.util.HashMap;
import java.util.ArrayList;

public class Maze {
	
	private final int X_BLOCKS = 28;
	private final int Y_BLOCKS = 31;
	
	// 2 dimenzi�s t�mb a mez�k t�pus�val 0-fal, 1-mez� kisbogy�val, 2-�res mez�, 3-mez� nagy bogy�val
	private final int fieldType[][] = 
		{
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0},
			{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
			{0,3,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,3,0},
			{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
			{0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
			{0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0},
			{0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0},
			{0,1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1,0},
			{0,0,0,0,0,0,1,0,0,0,0,0,2,0,0,2,0,0,0,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,0,0,0,2,0,0,2,0,0,0,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,2,2,2,2,2,2,2,2,2,2,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,2,0,0,0,0,0,0,0,0,2,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,2,0,2,2,2,2,2,2,0,2,0,0,1,0,0,0,0,0,0},
			{2,2,2,2,2,2,1,2,2,2,0,2,2,2,2,2,2,0,2,2,2,1,2,2,2,2,2,2},
			{0,0,0,0,0,0,1,0,0,2,0,2,2,2,2,2,2,0,2,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,2,0,0,0,0,0,0,0,0,2,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,2,2,2,2,2,2,2,2,2,2,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,2,0,0,0,0,0,0,0,0,2,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,2,0,0,0,0,0,0,0,0,2,0,0,1,0,0,0,0,0,0},
			{0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0},
			{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
			{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
			{0,3,1,1,0,0,1,1,1,1,1,1,1,2,2,1,1,1,1,1,1,1,0,0,1,1,3,0},
			{0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0},
			{0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0},
			{0,1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1,0},
			{0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0},
			{0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0},
			{0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
	};
	
	private ArrayList<Field> map = new ArrayList<Field>(); 
	
	// K�t ciklus seg�ts�g�vel felt�lti a mapet (ArrayList) mez�kkel �s be�ll�tja a mez�k szomsz�dait
	public Maze() {
		
		for(int i = 0; i < Y_BLOCKS; i++) // Ah�ny oszlop van
			for(int j= 0; j < X_BLOCKS; j++) // Ah�ny sor van
				// L�trehoz �s hozz�ad egy mez�t a list�hoz, a mez�h�z rendel egy ID-t, ha a mez�h�z tartoz� sz�m a fieldType t�mbben 0 akkor fal
				// ha 1-es akkor l�phet� mez� bogy�val, ha 2-es akkor l�phet�, de nincs rajta bogy�, ha 3-as akkor l�phet� �s nagy bogy� van rajta
				// Emellett minden mez�h�z koordin�t�t is rendel
 				map.add(new Field(i*X_BLOCKS + j, (fieldType[i][j] != 0), j*20, (i+3)*20, (fieldType[i][j] == 1), (fieldType[i][j] == 3)));
		
		// V�gig iter�l a p�ly�n �s a l�phet� mez�k szomsz�dait be�ll�tja
		for(Field j : map) {
			if(j.isCanStepOn()) {
				j.addNeighbor(Direction.Up, map.get(j.getid()-28));
				j.addNeighbor(Direction.Right, map.get(j.getid()+1));
				j.addNeighbor(Direction.Down, map.get(j.getid()+28));
				j.addNeighbor(Direction.Left, map .get(j.getid()-1));
			}
		}
	}
	
	// A param�terben �tvett entit�s poz�ci�j�t �sszehasonl�tja a p�lya mez�ivel sorra �s ha egyez�st tal�l visszat�r a mez�vel
	// Ha nincs egyez�s nullal t�r vissza
	public Field getEntityPosition(Entity ent) {
		for(Field  j: map) 
			if(j.getx() == ent.getPosx() && j.gety() == ent.getPosy())
				return j;
		return null;
	}
	
	// Visszaadja a mez�ket t�rol� ArrayListet
	public ArrayList<Field> getMap() {
		return map;
	}
	
	
}
