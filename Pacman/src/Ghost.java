import javax.swing.ImageIcon;
import java.util.*;

public class Ghost extends Entity{
	protected int targetx;
	protected int targety;
	protected State state;
	protected int baseTargetx;
	protected int baseTargety;
	private int counter;
	
	// Be�ll�tja az alap�rt�keket
	Ghost() {
		posx = spawnPositionx = 20*14-10;
		posy = spawnPositiony = 80 + 10*20;
		speed = 2;
		state = State.Scatter;
		counter = 0;
	}
	
	/*
	public void move(Maze maze, Entity target) {
		setTarget(target.getPosx(), target.getPosy());
		findDirection(maze);
		super.move(maze);
		
	}*/
	
	// �tveszi a p�ly�t param�terben �s megn�zi, hogy a szellem keresztez�d�sbe van-e
	// Akkor �ll keresztez�d�sben, ha k�pes az eredeti ir�ny�t�l elt�r� ir�nyba tov�bbhaladni
	// A 180 fokos fordulat nem sz�m�t �rv�nyes ir�nyv�ltoztat�snak 
	// (pl.: Ha felfele megy, akkor, sz�m�t egy mez� keresztez�d�snek, ha tov�bbmehet jobbra �s/vagy balra is)
	public boolean isCrossroads(Maze m) {
		Field pos = m.getEntityPosition(this); // a poz�ci� ahol a szellem �ll, ha �pp 2 mez� k�z�tt van nullot kap
		
		if(isOnField(pos)) { // megn�zi hogy mez�n �ll-e
			switch(getDir()) {
				case Up:
					return(canGoLeft(pos) || canGoRight(pos));
							
				case Right:
					return(canGoUp(pos) || canGoDown(pos));
					
				case Down:
					return(canGoLeft(pos) || canGoRight(pos));
					
				case Left:
					return(canGoUp(pos) || canGoDown(pos));
			}
		}
		
		return false;
	}
	
	// Megh�vja a startChase() met�dust, majd a param�terk�nt kapott x �s y �rt�ket, ha a szellem Chase �llapotban van
	// be�ll�tja a c�lpont �rt�keknek
	public void setTarget(int newx, int newy) {
		startChase();
		
		if(state.equals(State.Chase)) {
			targetx = newx;
			targety = newy;
		}
	}
	
	// Ha a szellem a saj�t sark�ba tart�zkodik n�veli a counter sz�ml�l�t, ha 2-szer �tmegy ezen a mez�n �tv�lt Chase �llapotba
	public void startChase() {
		if(posx == baseTargetx && posy == baseTargety) {
			counter++;
			if(2 <= counter)
				state = State.Chase;
		}
	}
	
	
	// A szellem keresztez�d�sben v�laszthat� ir�nyai k�z�l kiv�lasztja azt, amerre haladva a c�lpont mez�h�z a legk�zelebb lesz
	// Egyenl�s�g eset�n a priorit�s(fel, balra, le, jobbra)
	// Akkor vesz figyelembe egy ir�nyt, ha l�phet arrafele �s el�tte nem az ellenkez� ir�nyba haladt
	// Az �rv�nyes ir�nyok k�z�l a minim�lisat veszi �s �t�ll�tja Pacman k�vetkez� ir�ny�t az ennek megfelel� ir�nyra
	public void findDirection(Maze m) {
		Double distance = Double.POSITIVE_INFINITY;
		Field tempField = m.getEntityPosition(this);
		
		// Ha nem keresztez�d�sben �ll nem tud ir�nyt v�ltoztatni, ez�rt nem t�rt�nik semmi
		if(isCrossroads(m)) {
			if(dir != Direction.Down && canGoUp(tempField)) {
				distance = Math.sqrt(Math.pow(targetx - posx, 2) + Math.pow(targety+speed - posy, 2));
				nextdir = Direction.Up;
				nextdirActive = true;
			}
			if(dir != Direction.Right && canGoLeft(tempField)) { 
				Double tempDistance = Math.sqrt(Math.pow(targetx+speed - posx, 2) + Math.pow(targety - posy, 2));
				if(tempDistance <= distance) {
					if(tempDistance != distance) {
						distance = tempDistance;
						nextdir = Direction.Left;
						nextdirActive = true;
					}
				}
			}
			if(dir != Direction.Up && canGoDown(tempField)) {
				Double tempDistance = Math.sqrt(Math.pow(targetx - posx, 2) + Math.pow(targety-speed - posy, 2));
				if(tempDistance <= distance) {
					if(tempDistance != distance) {
						distance = tempDistance;
						nextdir = Direction.Down;
						nextdirActive = true;
					}
				}
			}
			if(dir != Direction.Left && canGoRight(tempField)) {
				Double tempDistance = Math.sqrt(Math.pow(targetx-speed - posx, 2) + Math.pow(targety - posy, 2));	
				if(tempDistance <= distance) {
					if(tempDistance != distance) {
						distance = tempDistance;
						nextdir = Direction.Right;
						nextdirActive = true;
					}
				}
			}
		}
	}
	
	// Visszat�r a szellem alap c�lpont mez�j�nek x koordin�t�j�val
	public int getBaseTargetx() {
		return targetx;
	}
	
	// Visszat�r a szellem alap c�lpont mez�j�nek y koordin�t�j�val
	public int getBaseTargety() {
		return targety;
	}
	
	// Visszat�r a szellem c�lpont mez�j�nek x koordin�t�j�val
	public int getTargetx() {
		return targetx;
	}
	
	// Visszat�r a szellem c�lpont mez�j�nek y koordin�t�j�val
	public int getTargety() {
		return targety;
	}
	
	// Be�ll�tja a param�terk�nt kapott �llapotot a szellem �llapot�nak
	public void setState(State s) {
		state = s;
	}
	
	// Az �soszt�ly reset f�ggv�ny�t kieg�sz�ti az �llapot, c�lpont �s a sz�ml�l� vissza�ll�t�s�val
	public void reset() {
		super.reset();
		state = State.Scatter;
		targetx = baseTargetx;
		targety = baseTargety;
		counter = 0;
	}
}
