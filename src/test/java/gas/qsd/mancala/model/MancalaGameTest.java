package gas.qsd.mancala.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

public class MancalaGameTest {

	Player playerSouth;
	Player playerNorth;
	
	@Before
	public void setUp(){
		playerSouth = new Player(1, true, false);
		playerNorth = new Player(2, false, false);
	}
	
	@Test
	public void testCreate(){
		int[] shortHandBoardState = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14};
		MancalaGame game = new MancalaGame(shortHandBoardState, playerSouth, playerNorth);
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
		int[] shortHandBoardState = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13};
		new MancalaGame(shortHandBoardState, playerSouth, playerNorth);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testStartFromEmptyPotThrowsError(){
		int[] shortHandBoardState = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		new MancalaGame(shortHandBoardState, playerSouth, playerNorth).move(5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testStartFromPotNotOnBoard(){
		int[] shortHandBoardState = new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		new MancalaGame(shortHandBoardState, playerSouth, playerNorth).move(8);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testStartFromMancala(){
		int[] shortHandBoardState = new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1};
		new MancalaGame(shortHandBoardState, playerSouth, playerNorth).move(7);
	}
	
	@Test
	public void testSimpleMove(){
		int[] shortHandBoardState = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14};
		MancalaGame game = new MancalaGame(shortHandBoardState, playerSouth, playerNorth);
		
		assertEquals(1, game.getSouthSidePots()[0].stoneCount());
		assertEquals(2, game.getSouthSidePots()[1].stoneCount());
		assertEquals(3, game.getSouthSidePots()[2].stoneCount());
		assertEquals(4, game.getSouthSidePots()[3].stoneCount());
		assertEquals(5, game.getSouthSidePots()[4].stoneCount());
		
		assertTrue(playerSouth.hasTurn());
		assertFalse(playerNorth.hasTurn());
		
		game.move(2);
		
		assertEquals(1, game.getSouthSidePots()[0].stoneCount());
		assertEquals(0, game.getSouthSidePots()[1].stoneCount());
		assertEquals(4, game.getSouthSidePots()[2].stoneCount());
		assertEquals(5, game.getSouthSidePots()[3].stoneCount());
		assertEquals(5, game.getSouthSidePots()[4].stoneCount());
		
		assertFalse(playerSouth.hasTurn());
		assertTrue(playerNorth.hasTurn());
	}
	
	@Test
	public void testMoveLastStoneInOwnMancalaPlayerKeepsTurn(){
		int[] shortHandBoardState = new int[]{0,1,4,0,0,0,0,0,0,1,0,0,0,0};
		MancalaGame game = new MancalaGame(shortHandBoardState, playerSouth, playerNorth);
		
		assertTrue(playerSouth.hasTurn());
		assertFalse(playerNorth.hasTurn());
		
		game.move(3);
		
		assertTrue(playerSouth.hasTurn());
		assertFalse(playerNorth.hasTurn());
		assertFalse(playerNorth.hasWon());
		assertFalse(playerNorth.hasWon());
	}
	
	@Test
	public void testPlayerWins(){
		int[] shortHandBoardState = new int[]{0,0,0,0,0,0,0,0,0,1,0,0,0,0};
		MancalaGame game = new MancalaGame(shortHandBoardState, playerSouth, playerNorth);
		
		playerNorth.setHasTurn(true);
		playerSouth.setHasTurn(false);
		
		game.move(3);
		
		assertFalse(playerSouth.hasWon());
		assertTrue(playerNorth.hasWon());
		assertFalse(playerSouth.hasTurn());
		assertFalse(playerNorth.hasTurn());
	}
	
	@Test
	public void testMoveLastStoneInOwnMancalaPlayerLoses(){
		int[] shortHandBoardState = new int[]{0,0,1,2,0,0,2,0,0,0,0,0,1,3};
		MancalaGame game = new MancalaGame(shortHandBoardState, playerSouth, playerNorth);
		
		playerNorth.setHasTurn(true);
		playerSouth.setHasTurn(false);
		
		game.move(6);
		
		assertTrue(playerSouth.hasWon());
		assertEquals(5, playerSouth.getMancala().stoneCount());
		assertFalse(playerNorth.hasWon());
		assertEquals(4, playerNorth.getMancala().stoneCount());
		assertFalse(playerSouth.hasTurn());
		assertFalse(playerNorth.hasTurn());
		
	}
	
	@Test
	public void testPlayerWinsBecauseOfCapture(){
		int[] shortHandBoardState = new int[]{1,0,10,0,0,0,0,0,0,0,0,20,0,7};
		MancalaGame game = new MancalaGame(shortHandBoardState, playerSouth, playerNorth);
		
		playerSouth.setHasTurn(true);
		playerNorth.setHasTurn(false);
		
		game.move(1);
		
		assertTrue(playerSouth.hasWon());
		assertEquals(31, playerSouth.getMancala().stoneCount());
		assertFalse(playerNorth.hasWon());
		assertEquals(7, playerNorth.getMancala().stoneCount());
		assertFalse(playerSouth.hasTurn());
		assertFalse(playerNorth.hasTurn());
	}	
	
	@Test
	public void testMoveArroundTheBoardEndInEmptyPot(){
		//14 stones in the first south side pot, one for each pot skipping the opponents mancala, 
		//and 1 extra for the pot we started the move from. The rest of the pots on the board are empty.
		//Last stone goes into the starting pots next pot which is now empty
		//The pot across from the starting pot now contains 1 stone from sowing, which is taken and added to the moving players mancala
		//The pot the move started from also has 1 stone which is taken and added to the moving players mancala.
		//The mancala already had 1 stone from sowing so at the end of the move holds 3 stones.
		
		int[] shortHandBoardState = new int[]{0,13,0,0,0,0,0,0,0,0,0,0,0,0};
		MancalaGame game = new MancalaGame(shortHandBoardState, playerSouth, playerNorth);
		
		assertEquals(0, game.getSouthSidePots()[0].stoneCount());
		assertEquals(13, game.getSouthSidePots()[1].stoneCount());
				
		assertEquals(0, playerSouth.getMancala().stoneCount());
		assertEquals(0, playerNorth.getMancala().stoneCount());
		
		assertTrue(playerSouth.hasTurn());
		assertFalse(playerNorth.hasTurn());
		
		game.move(2);
		
		assertEquals(1, game.getSouthSidePots()[0].stoneCount());
		assertEquals(0, game.getSouthSidePots()[1].stoneCount());
		assertEquals(1, game.getSouthSidePots()[2].stoneCount());
		assertEquals(1, game.getNorthSidePots()[3].stoneCount());//holds 1 stone from sowing
		assertEquals(0, game.getNorthSidePots()[4].stoneCount());//held 1 stone from sowing, but that has been placed in the mancala
		assertEquals(1, game.getNorthSidePots()[5].stoneCount());//holds 1 stone from sowing
		
		assertEquals(3, playerSouth.getMancala().stoneCount());
		assertEquals(0, playerNorth.getMancala().stoneCount());
		
		assertTrue(playerNorth.hasTurn());
		assertFalse(playerSouth.hasTurn());
	}
	
	
	@Test
	public void testWinningMoveMovingPlayerWins(){		
		int[] shortHandBoardState = new int[]{1,2,3,4,5,6,7,0,0,0,0,0,0,27};
		MancalaGame game = new MancalaGame(shortHandBoardState, playerSouth, playerNorth);
		
		assertFalse(playerSouth.hasWon());
		assertFalse(playerNorth.hasWon());
		assertEquals(7, playerSouth.getMancala().stoneCount());
		
		game.move(2);
		
		assertEquals(28, playerSouth.getMancala().stoneCount());
		assertEquals(27, playerNorth.getMancala().stoneCount());
		
		assertTrue(playerSouth.hasWon());
		assertFalse(playerNorth.hasWon());
	}
	
	@Test
	public void testWinningMoveNotMovingPlayerWins(){		
		int[] shortHandBoardState = new int[]{1,2,3,4,5,6,7,0,0,0,0,0,0,29};
		MancalaGame game = new MancalaGame(shortHandBoardState, playerSouth, playerNorth);
		
		assertFalse(playerSouth.hasWon());
		assertFalse(playerNorth.hasWon());
		assertEquals(7, playerSouth.getMancala().stoneCount());
		
		game.move(2);
		
		assertEquals(28, playerSouth.getMancala().stoneCount());
		assertEquals(29, playerNorth.getMancala().stoneCount());
		
		assertFalse(playerSouth.hasWon());
		assertTrue(playerNorth.hasWon());
		
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
