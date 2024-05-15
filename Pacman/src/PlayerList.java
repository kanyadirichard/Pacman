import java.util.ArrayList;
import java.io.*;
import java.util.Collections;
import java.util.Comparator;

public class PlayerList {
	public ArrayList<Player> list = new ArrayList<Player>();
	
	// Hozz�ad a param�terk�nt adatokkal egy j�t�kost a list�hoz
	public void add(String name, int score) {
		list.add(new Player(name, score));
	}
	
	// F�jlba menti a j�t�kosok list�j�t
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

	// Bet�lti a megadott f�jlb�l a j�t�kosok list�j�t
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
	
	// Kit�rli a list�b�l a param�terk�nt kapott indexhez tartoz� j�t�kost
	public void delete(int index) {
		list.remove(index);
	}
	
	// Visszat�r a j�t�kosok list�j�val
	public ArrayList<Player> getPlayers() {
		return list;
	}
}
