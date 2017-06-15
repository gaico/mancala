package gas.qsd.mancala.translator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import gas.qsd.mancala.model.MancalaGame;
import gas.qsd.mancala.model.Player;
import gas.qsd.mancala.model.Pot;
import gas.qsd.mancala.translator.MancalaTranslator;

public class MancalaTranslatorTest {

	Player playerSouth;
	Player playerNorth;
	MancalaTranslator translator;
	
	@Before
	public void setUp(){
		playerSouth = new Player(1, true, false);
		playerNorth = new Player(2, false, false);
		translator = new MancalaTranslator();
	}
	
	@Test
	public void testCreate(){
		MancalaGame game = translator.gameFromShortHandGameState("1,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14");
		Pot[] northSide = game.getNorthSidePots();
		Pot[] southSide = game.getSouthSidePots();
		
		assertEquals(7, northSide.length);
		assertEquals(7, southSide.length);
		
		assertPot(southSide[0], 1, false, playerSouth, southSide[1].stoneCount(), northSide[5].stoneCount());
		assertPot(southSide[1], 2, false, playerSouth, southSide[2].stoneCount(), northSide[4].stoneCount());
		assertPot(southSide[2], 3, false, playerSouth, southSide[3].stoneCount(), northSide[3].stoneCount());
		assertPot(southSide[3], 4, false, playerSouth, southSide[4].stoneCount(), northSide[2].stoneCount());
		assertPot(southSide[4], 5, false, playerSouth, southSide[5].stoneCount(), northSide[1].stoneCount());
		assertPot(southSide[5], 6, false, playerSouth, southSide[6].stoneCount(), northSide[0].stoneCount());
		
		assertPot(southSide[6], 7, true, playerSouth, northSide[0].stoneCount(), northSide[6].stoneCount());
		
		assertPot(northSide[0], 8, false, playerNorth, northSide[1].stoneCount(), southSide[5].stoneCount());
		assertPot(northSide[1], 9, false, playerNorth, northSide[2].stoneCount(), southSide[4].stoneCount());
		assertPot(northSide[2], 10, false, playerNorth, northSide[3].stoneCount(), southSide[3].stoneCount());
		assertPot(northSide[3], 11, false, playerNorth, northSide[4].stoneCount(), southSide[2].stoneCount());
		assertPot(northSide[4], 12, false, playerNorth, northSide[5].stoneCount(), southSide[1].stoneCount());
		assertPot(northSide[5], 13, false, playerNorth, northSide[6].stoneCount(), southSide[0].stoneCount());
		
		assertPot(northSide[6], 14, true, playerNorth, southSide[0].stoneCount(), southSide[6].stoneCount());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateInputToShortThrowsError(){
		translator.gameFromShortHandGameState("1,0,1,2,3,4,5,6,7,8,9,10,11,12,13");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testStartFromEmptyPotThrowsError(){
		translator.gameFromShortHandGameState("1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0").move(5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testStartFromPotNotOnBoard(){
		//int[] shortHandBoardState = new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		//new MancalaGame(shortHandBoardState, playerSouth, playerNorth).move(8);
		translator.gameFromShortHandGameState("1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1").move(8);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testStartFromMancala(){
		//int[] shortHandBoardState = new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		//new MancalaGame(shortHandBoardState, playerSouth, playerNorth).move(7);
		translator.gameFromShortHandGameState("1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1").move(7);
	}
	
	private void assertPot(Pot pot, 
			int numberfStones, 
			boolean isMancala, 
			Player owner, 
			int numberfStonesInNextPot,
			int numberOfStonesInPotAcross) {
		assertEquals("numberfStones", numberfStones, pot.stoneCount());
		assertEquals("isMancala", isMancala, pot.isMancala());
		assertEquals("owner", owner, pot.getOwner());
		assertEquals("numberfStonesInNextPot", numberfStonesInNextPot, pot.getNextPot().stoneCount());
		assertEquals("numberOfStonesInPotAcross", numberOfStonesInPotAcross, pot.getPotAccross().stoneCount());
		
	}
}
