package gas.qsd.mancala.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MancelaServiceTest {
	
	@Test
	public void testMancelaService(){
		MancalaService service = new MancalaService();
		String newGameState = service.getNewShortHandGameStateFrom("1,0,12,100,200,300,400,500,600,700,800,900,1000,1100,1200,1300", "1");
		
		String[] gameStateStrings = newGameState.split(",");
		
		assertEquals("2", gameStateStrings[0]);
		assertEquals("0", gameStateStrings[1]);
		
		assertEquals("0", gameStateStrings[2]);
		assertEquals("101", gameStateStrings[3]);
		assertEquals("201", gameStateStrings[4]);
		assertEquals("301", gameStateStrings[5]);
		assertEquals("401", gameStateStrings[6]);
		assertEquals("501", gameStateStrings[7]);
		assertEquals("601", gameStateStrings[8]);
		assertEquals("701", gameStateStrings[9]);
		assertEquals("801", gameStateStrings[10]);
		assertEquals("901", gameStateStrings[11]);
		assertEquals("1001", gameStateStrings[12]);
		assertEquals("1101", gameStateStrings[13]);
		assertEquals("1201", gameStateStrings[14]);
		assertEquals("1300", gameStateStrings[15]);
	}


}
