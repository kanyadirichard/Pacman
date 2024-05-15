import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

class PacmanTest {
	
	Maze maze;
	Pacman pacman;
	Blinky blinky;
	
	@Test
	public void pelletTest() {
		assertTrue(maze.getMap().get(30).isHasPellet() == true, "No pellet on field.");
	}
	
	@Test
	public void bigPelletTest() {
		assertTrue(maze.getMap().get(80).isHasBigPellet() == true, "No big pellet on field.");
	}
	
	@Test
	public void wallTest() {
		assertTrue(maze.getMap().get(310).isCanStepOn() == false, "The wall is steppable.");
	}
	
	@Test
	public void pacmanPositionTest() {
		assertEquals("Not the same position.", ( pacman.getPosx() == pacman.getSpawnPosx() && pacman.getPosy() == pacman.getSpawnPosy())); 
	}
	
	@Test
	public void pacmanSpeedTest() {
		assertEquals("Not the same speed.", pacman.getSpeed() == blinky.getSpeed());
	}
	
	@Test
	public void targetTest() {
		assertTrue(blinky.getTargetx() == blinky.getBaseTargetx() && blinky.getTargety() == blinky.getBaseTargetx(), "Blinky has wrong target."); 
	}
	
	@Test
	public void neighborTest() {
		assertTrue(maze.getMap().get(30).gety() == maze.getMap().get(30).getNeighbor(Direction.Right).gety(), "Wrong neighbor.");
	}

	
}
