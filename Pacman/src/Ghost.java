import javax.swing.ImageIcon;
import java.util.*;

public class Ghost extends Entity{
	protected int targetx;
	protected int targety;
	protected State state;
	protected int baseTargetx;
	protected int baseTargety;
	private int counter;
	
	// Beállítja az alapértékeket
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
	
	// Átveszi a pályát paraméterben és megnézi, hogy a szellem keresztezõdésbe van-e
	// Akkor áll keresztezõdésben, ha képes az eredeti irányától eltérõ irányba továbbhaladni
	// A 180 fokos fordulat nem számít érvényes irányváltoztatásnak 
	// (pl.: Ha felfele megy, akkor, számít egy mezõ keresztezõdésnek, ha továbbmehet jobbra és/vagy balra is)
	public boolean isCrossroads(Maze m) {
		Field pos = m.getEntityPosition(this); // a pozíció ahol a szellem áll, ha épp 2 mezõ között van nullot kap
		
		if(isOnField(pos)) { // megnézi hogy mezõn áll-e
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
	
	// Meghívja a startChase() metódust, majd a paraméterként kapott x és y értéket, ha a szellem Chase állapotban van
	// beállítja a célpont értékeknek
	public void setTarget(int newx, int newy) {
		startChase();
		
		if(state.equals(State.Chase)) {
			targetx = newx;
			targety = newy;
		}
	}
	
	// Ha a szellem a saját sarkába tartózkodik növeli a counter számlálót, ha 2-szer átmegy ezen a mezõn átvált Chase állapotba
	public void startChase() {
		if(posx == baseTargetx && posy == baseTargety) {
			counter++;
			if(2 <= counter)
				state = State.Chase;
		}
	}
	
	
	// A szellem keresztezõdésben választható irányai közül kiválasztja azt, amerre haladva a célpont mezõhöz a legközelebb lesz
	// Egyenlõség esetén a prioritás(fel, balra, le, jobbra)
	// Akkor vesz figyelembe egy irányt, ha léphet arrafele és elõtte nem az ellenkezõ irányba haladt
	// Az érvényes irányok közül a minimálisat veszi és átállítja Pacman következõ irányát az ennek megfelelõ irányra
	public void findDirection(Maze m) {
		Double distance = Double.POSITIVE_INFINITY;
		Field tempField = m.getEntityPosition(this);
		
		// Ha nem keresztezõdésben áll nem tud irányt változtatni, ezért nem történik semmi
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
	
	// Visszatér a szellem alap célpont mezõjének x koordinátájával
	public int getBaseTargetx() {
		return targetx;
	}
	
	// Visszatér a szellem alap célpont mezõjének y koordinátájával
	public int getBaseTargety() {
		return targety;
	}
	
	// Visszatér a szellem célpont mezõjének x koordinátájával
	public int getTargetx() {
		return targetx;
	}
	
	// Visszatér a szellem célpont mezõjének y koordinátájával
	public int getTargety() {
		return targety;
	}
	
	// Beállítja a paramáterként kapott állapotot a szellem állapotának
	public void setState(State s) {
		state = s;
	}
	
	// Az õsosztály reset függvényét kiegészíti az állapot, célpont és a számláló visszaállításával
	public void reset() {
		super.reset();
		state = State.Scatter;
		targetx = baseTargetx;
		targety = baseTargety;
		counter = 0;
	}
}
