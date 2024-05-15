import java.util.HashMap;
import java.util.ArrayList;

public class Maze {
	
	private final int X_BLOCKS = 28;
	private final int Y_BLOCKS = 31;
	
	// 2 dimenziós tömb a mezõk típusával 0-fal, 1-mezõ kisbogyóval, 2-üres mezõ, 3-mezõ nagy bogyóval
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
	
	// Két ciklus segítségével feltölti a mapet (ArrayList) mezõkkel és beállítja a mezõk szomszédait
	public Maze() {
		
		for(int i = 0; i < Y_BLOCKS; i++) // Ahány oszlop van
			for(int j= 0; j < X_BLOCKS; j++) // Ahány sor van
				// Létrehoz és hozzáad egy mezõt a listához, a mezõhöz rendel egy ID-t, ha a mezõhöz tartozó szám a fieldType tömbben 0 akkor fal
				// ha 1-es akkor léphetõ mezõ bogyóval, ha 2-es akkor léphetõ, de nincs rajta bogyó, ha 3-as akkor léphetõ és nagy bogyó van rajta
				// Emellett minden mezõhöz koordinátát is rendel
 				map.add(new Field(i*X_BLOCKS + j, (fieldType[i][j] != 0), j*20, (i+3)*20, (fieldType[i][j] == 1), (fieldType[i][j] == 3)));
		
		// Végig iterál a pályán és a léphetõ mezõk szomszédait beállítja
		for(Field j : map) {
			if(j.isCanStepOn()) {
				j.addNeighbor(Direction.Up, map.get(j.getid()-28));
				j.addNeighbor(Direction.Right, map.get(j.getid()+1));
				j.addNeighbor(Direction.Down, map.get(j.getid()+28));
				j.addNeighbor(Direction.Left, map .get(j.getid()-1));
			}
		}
	}
	
	// A paraméterben átvett entitás pozícióját összehasonlítja a pálya mezõivel sorra és ha egyezést talál visszatér a mezõvel
	// Ha nincs egyezés nullal tér vissza
	public Field getEntityPosition(Entity ent) {
		for(Field  j: map) 
			if(j.getx() == ent.getPosx() && j.gety() == ent.getPosy())
				return j;
		return null;
	}
	
	// Visszaadja a mezõket tároló ArrayListet
	public ArrayList<Field> getMap() {
		return map;
	}
	
	
}
