import java.io.*;


public class Player implements Comparable, Serializable{
	private String name;
	private int score;
	
	Player(String n, int s){
		name = n;
		score = s;
	}
	
	// Visszaadja a j�t�kos nev�t
	public String getName() {
		return name;
	}
	
	/*
	public void setName(String name) {
		this.name = name;
	}*/

	// Visszaadja a j�t�kos pontsz�m�t
	public int getScore() {
		return score;
	}

	/*
	public void setScore(int score) {
		this.score = score;
	}*/

	// Stringet k�sz�t a j�t�kos nev�b�l �s pontsz�m�b�l
	public String toString() {
		return "Name: " + name + "                  Score: " + score;
	}

	// A Comparator megfelel� m�k�d�s�hez sz�ks�ges, visszaadja 2 j�t�kos pontsz�m�nak k�l�nbs�g�t
	public int compareTo(Object o) {
		Player temp = (Player) o;
		return temp.score - score;
	}
}
