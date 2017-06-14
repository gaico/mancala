package gas.qsd.mancala.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
	
	Player p1; 
	Player p2; 
	Player p3;
	
	@Before
	public void setUp(){
		p1 = new Player(1, false, false);
		p2 = new Player(2, false, false);
		p3 = new Player(1, true, true);
		
		Pot p1pot4 = new Pot(5, true, p1);
		Pot p1pot3 = new Pot(5, false, p1);
		p1pot3.setNextPot(p1pot4);
		Pot p1pot2 = new Pot(5, false, p1);
		p1pot2.setNextPot(p1pot3);
		Pot p1pot1 = new Pot(5, false, p1);
		p1pot1.setNextPot(p1pot2);
		
		p1.setPots(new Pot[]{p1pot1, p1pot2, p1pot3, p1pot4});
	}
		
	@Test
	public void testEquals(){
		assertFalse(p1.equals(p2));
		assertTrue(p1.equals(p3));
	}
	
	@Test
	public void takeAllStonesTest(){
		assertEquals(15, p1.takeAllStones());
		assertEquals(0, p1.takeAllStones());
	}
	
	@Test
	public void hasMovesTest(){
		assertTrue(p1.hasMoves());
		p1.takeAllStones();
		assertFalse(p1.hasMoves());
	}
	
	

}
