import java.util.ArrayList;
import java.io.*;
import java.util.Collections;
import java.util.Comparator;

public class PlayerList {
	public ArrayList<Player> list = new ArrayList<Player>();
	
	// Hozzáad a paraméterként adatokkal egy játékost a listához
	public void add(String name, int score) {
		list.add(new Player(name, score));
	}
	
	// Fájlba menti a játékosok listáját
	public void save() {
		try {
			File file = new File(System.getProperty("user.dir"));
			File outputFile = new File(file, "leaderboard.txt");
			outputFile.createNewFile();
			FileOutputStream fileOS = new FileOutputStream(outputFile);
			ObjectOutputStream objectOS = new ObjectOutputStream(fileOS);
			objectOS.writeObject(list);
			objectOS.close();
			fileOS.close();
		} catch(IOException e) {
			System.out.println("Could not save.");
		}
		
	}

	// Betölti a megadott fájlból a játékosok listáját
	public void load() throws FileNotFoundException {
		File source = null;
		File file = new File(System.getProperty("user.dir"));
		
		for(File temp : file.listFiles()) {
			if (temp.getName().equals("leaderboard.txt"))
				source = temp;
		}
		if (source == null)
			throw new FileNotFoundException();
		try {
			FileInputStream fileIS = new FileInputStream(source);
			ObjectInputStream objectIS = new ObjectInputStream(fileIS);
			ArrayList<Player> temp = (ArrayList<Player>) objectIS.readObject();
			list = temp;
			objectIS.close();
			fileIS.close();
		} catch(IOException | ClassNotFoundException e) {
			System.out.println("Could not load players.");
		}
	}
	
	// Kitörli a listából a paraméterként kapott indexhez tartozó játékost
	public void delete(int index) {
		list.remove(index);
	}
	
	// Visszatér a játékosok listájával
	public ArrayList<Player> getPlayers() {
		return list;
	}
}
