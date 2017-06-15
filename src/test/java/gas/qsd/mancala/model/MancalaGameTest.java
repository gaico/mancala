package gas.qsd.mancala.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import gas.qsd.mancala.translator.MancalaTranslator;

public class MancalaGameTest {

	MancalaTranslator translator;
	
	@Before
	public void setUp(){
		translator = new MancalaTranslator();
	}
	
	@Test
	public void testSimpleMove(){
		MancalaGame game = translator.gameFromShortHandGameState("1,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14");
		
		assertEquals(1, game.getSouthSidePots()[0].stoneCount());
		assertEquals(2, game.getSouthSidePots()[1].stoneCount());
		assertEquals(3, game.getSouthSidePots()[2].stoneCount());
		assertEquals(4, game.getSouthSidePots()[3].stoneCount());
		assertEquals(5, game.getSouthSidePots()[4].stoneCount());
		
		assertTrue(game.getPlayerSouth().hasTurn());
		assertFalse(game.getPlayerNorth().hasTurn());
		
		game.move(2);
		
		assertEquals(1, game.getSouthSidePots()[0].stoneCount());
		assertEquals(0, game.getSouthSidePots()[1].stoneCount());
		assertEquals(4, game.getSouthSidePots()[2].stoneCount());
		assertEquals(5, game.getSouthSidePots()[3].stoneCount());
		assertEquals(5, game.getSouthSidePots()[4].stoneCount());
		
		assertFalse(game.getPlayerSouth().hasTurn());
		assertTrue(game.getPlayerNorth().hasTurn());
	}
	
	@Test
	public void testMoveLastStoneInOwnMancalaPlayerKeepsTurn(){
		MancalaGame game = translator.gameFromShortHandGameState("1,0,0,1,4,0,0,0,0,0,0,1,0,0,0,0");
		
		assertTrue(game.getPlayerSouth().hasTurn());
		assertFalse(game.getPlayerNorth().hasTurn());
		
		game.move(3);
		
		assertTrue(game.getPlayerSouth().hasTurn());
		assertFalse(game.getPlayerNorth().hasTurn());
		assertFalse(game.getPlayerNorth().hasWon());
		assertFalse(game.getPlayerNorth().hasWon());
	}
	
	@Test
	public void testPlayerWins(){
		MancalaGame game = translator.gameFromShortHandGameState("1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0");
		
		game.getPlayerNorth().setHasTurn(true);
		game.getPlayerSouth().setHasTurn(false);
		
		game.move(3);
		
		assertFalse(game.getPlayerSouth().hasWon());
		assertTrue(game.getPlayerNorth().hasWon());
		assertFalse(game.getPlayerSouth().hasTurn());
		assertFalse(game.getPlayerNorth().hasTurn());
	}
	
	@Test
	public void testMoveLastStoneInOwnMancalaPlayerLoses(){
		MancalaGame game = translator.gameFromShortHandGameState("1,0,0,0,1,2,0,0,2,0,0,0,0,0,1,3");
		
		game.getPlayerNorth().setHasTurn(true);
		game.getPlayerSouth().setHasTurn(false);
		
		game.move(6);
		
		assertTrue(game.getPlayerSouth().hasWon());
		assertEquals(5, game.getPlayerSouth().getMancala().stoneCount()); 
		assertFalse(game.getPlayerNorth().hasWon());
		assertEquals(4, game.getPlayerNorth().getMancala().stoneCount());
		assertFalse(game.getPlayerSouth().hasTurn());
		assertFalse(game.getPlayerNorth().hasTurn());
		
	}
	
	@Test
	public void testEndingInEmptyPotOnOtherSide(){
		MancalaGame game = translator.gameFromShortHandGameState("2,0,4,0,1,6,6,6,1,4,4,0,5,5,5,1");
		
		assertFalse(game.getPlayerSouth().hasTurn());
		assertTrue(game.getPlayerNorth().hasTurn());
		
		game.move(4);
		
		assertEquals(2, game.getPlayerNorth().getMancala().stoneCount()); 
	}
	
	@Test
	public void testPlayerWinsBecauseOfCapture(){
		MancalaGame game = translator.gameFromShortHandGameState("1,0,1,0,10,0,0,0,0,0,0,0,0,20,0,7");
		
		assertTrue(game.getPlayerSouth().hasTurn());
		assertFalse(game.getPlayerNorth().hasTurn());
		
		game.move(1);
		
		assertTrue(game.getPlayerSouth().hasWon());
		assertEquals(31, game.getPlayerSouth().getMancala().stoneCount());
		assertFalse(game.getPlayerNorth().hasWon());
		assertEquals(7, game.getPlayerNorth().getMancala().stoneCount());
		assertFalse(game.getPlayerSouth().hasTurn());
		assertFalse(game.getPlayerNorth().hasTurn());
	}	
	
	@Test
	public void testMoveArroundTheBoardEndInEmptyPot(){
		//14 stones in the first south side pot, one for each pot skipping the opponents mancala, 
		//and 1 extra for the pot we started the move from. The rest of the pots on the board are empty.
		//Last stone goes into the starting pots next pot which is now empty
		//The pot across from the starting pot now contains 1 stone from sowing, which is taken and added to the moving players mancala
		//The pot the move started from also has 1 stone which is taken and added to the moving players mancala.
		//The mancala already had 1 stone from sowing so at the end of the move holds 3 stones.
		//After the move nobody has won and the turn has shifted to the other player.

		MancalaGame game = translator.gameFromShortHandGameState("1,0,0,13,0,0,0,0,0,0,0,0,0,0,0,0");
		
		assertEquals(0, game.getSouthSidePots()[0].stoneCount());
		assertEquals(13, game.getSouthSidePots()[1].stoneCount());
				
		assertEquals(0, game.getPlayerSouth().getMancala().stoneCount());
		assertEquals(0, game.getPlayerNorth().getMancala().stoneCount());
		
		assertTrue(game.getPlayerSouth().hasTurn());
		assertFalse(game.getPlayerNorth().hasTurn());
		
		game.move(2);
		
		assertEquals(1, game.getSouthSidePots()[0].stoneCount());
		assertEquals(0, game.getSouthSidePots()[1].stoneCount());
		assertEquals(1, game.getSouthSidePots()[2].stoneCount());
		assertEquals(1, game.getNorthSidePots()[3].stoneCount());//holds 1 stone from sowing
		assertEquals(0, game.getNorthSidePots()[4].stoneCount());//held 1 stone from sowing, but that has been placed in the mancala
		assertEquals(1, game.getNorthSidePots()[5].stoneCount());//holds 1 stone from sowing
		
		assertEquals(3, game.getPlayerSouth().getMancala().stoneCount());
		assertEquals(0, game.getPlayerNorth().getMancala().stoneCount());
		
		assertFalse(game.getPlayerSouth().hasWon());
		assertFalse(game.getPlayerNorth().hasWon());
		
		assertFalse(game.getPlayerSouth().hasTurn());
		assertTrue(game.getPlayerNorth().hasTurn());
	}
	
	
	@Test
	public void testWinningMoveMovingPlayerWins(){		
		MancalaGame game = translator.gameFromShortHandGameState("1,0,1,2,3,4,5,6,7,0,0,0,0,0,0,27");
		
		assertFalse(game.getPlayerSouth().hasWon());
		assertFalse(game.getPlayerNorth().hasWon());
		assertEquals(7, game.getPlayerSouth().getMancala().stoneCount());
		
		game.move(2);
		
		assertEquals(28, game.getPlayerSouth().getMancala().stoneCount());
		assertEquals(27, game.getPlayerNorth().getMancala().stoneCount());
		
		assertTrue(game.getPlayerSouth().hasWon());
		assertFalse(game.getPlayerNorth().hasWon());
	}
	
	@Test
	public void testWinningMoveNotMovingPlayerWins(){		
		MancalaGame game = translator.gameFromShortHandGameState("1,0,1,2,3,4,5,6,7,0,0,0,0,0,0,29");
		
		assertFalse(game.getPlayerSouth().hasWon());
		assertFalse(game.getPlayerNorth().hasWon());
		assertEquals(7, game.getPlayerSouth().getMancala().stoneCount());
		
		game.move(2);
		
		assertEquals(28, game.getPlayerSouth().getMancala().stoneCount());
		assertEquals(29, game.getPlayerNorth().getMancala().stoneCount());
		
		assertFalse(game.getPlayerSouth().hasWon());
		assertTrue(game.getPlayerNorth().hasWon());
		
	}

}
