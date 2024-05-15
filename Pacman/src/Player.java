import java.io.*;


public class Player implements Comparable, Serializable{
	private String name;
	private int score;
	
	Player(String n, int s){
		name = n;
		score = s;
	}
	
	// Visszaadja a játékos nevét
	public String getName() {
		return name;
	}
	
	/*
	public void setName(String name) {
		this.name = name;
	}*/

	// Visszaadja a játékos pontszámát
	public int getScore() {
		return score;
	}

	/*
	public void setScore(int score) {
		this.score = score;
	}*/

	// Stringet készít a játékos nevébõl és pontszámából
	public String toString() {
		return "Name: " + name + "                  Score: " + score;
	}

	// A Comparator megfelelõ mûködéséhez szükséges, visszaadja 2 játékos pontszámának különbségét
	public int compareTo(Object o) {
		Player temp = (Player) o;
		return temp.score - score;
	}
}
