package gas.qsd.mancala.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PotTest {
	
	private final static int playerSouthId = 1;
	private final static int playerNorthId = 2;
	
	Pot pot5;
	Pot pot4;
	Pot pot3;
	Pot pot2;
	Pot pot1;
	
	Player playerSouth;
	Player playerNorth;
	
	@Before
	public void setUp(){
		playerSouth = new Player(playerSouthId, true, false);
		playerNorth = new Player(playerNorthId, true, false);
		pot5 = new Pot(2, false, playerSouth);
		pot4 = new Pot(11, true, playerNorth);
		pot4.setNextPot(pot5);
		pot3 = new Pot(0, true, playerSouth);
		pot3.setNextPot(pot4);
		pot2 = new Pot(3, false, playerSouth);
		pot2.setNextPot(pot3);
		pot1 = new Pot(3, false, playerSouth);
		pot1.setNextPot(pot2);
	}
		
		
	@Test
	public void testMove(){
		pot1.move(playerSouth);
		
		assertEquals(0, pot1.stoneCount());
		assertEquals(4, pot2.stoneCount());
		assertEquals(1, pot3.stoneCount());
		assertEquals(11, pot4.stoneCount());
		assertEquals(3, pot5.stoneCount());
	}
	
	@Test
	public void testTakeAllStones(){
		assertEquals(11, pot4.stoneCount());
		assertEquals(11, pot4.takeAllStones());
		assertEquals(0, pot4.stoneCount());
	}
	
	@Test
	public void testAddStones(){
		assertEquals(11, pot4.stoneCount());
		pot4.addStones(11);
		assertEquals(22, pot4.stoneCount());
	}

}
