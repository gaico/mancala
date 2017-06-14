package gas.qsd.mancala.controllers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameStateControllerTest {
	
	GameStateController controller;
	
	@Before
	public void setUp(){
		controller = new GameStateController();
	}
	
	@Test
	public void testValidateGameInputMoveMustBeADigit(){
		assertEquals("Input Henk is not a digit",
				getExceptionText("1,0,1,2,3,Henk,5,6,0,1,2,3,4,5,6,0", "1"));
	}	
	
	@Test
	public void testValidateGameInputPlayerTurnMustBeADigit1Or2(){
		assertEquals("First digit must be from 0 to 2, as it is the id of the player that has the turn",
				getExceptionText("7,0,1,2,3,4,5,6,0,1,2,3,4,5,6,0", "1"));
	}
	
	@Test
	public void testValidateGameInputMoveMustBeADigitFrom1to6(){
		assertEquals("Move must be a digit from 1 to 6",
				getExceptionText("1,0,1,2,3,4,5,6,0,1,2,3,4,5,6,0", "7"));
	}
	
	@Test
	public void testValidateGameInputWinnerSet(){
		assertEquals("Second digit must 0, as it is the id of the player that won the game",
				getExceptionText("1,1,1,2,3,4,5,6,0,1,2,3,4,5,6,0", "1"));
	}
	
	@Test
	public void testValidateGameInputWrongLength(){
		assertEquals("Number of digits must be 16",
				getExceptionText("1,0,1,2,3,4,5,6,0,1,2,3,4,5,6", "1"));
		assertEquals("Number of digits must be 16",
				getExceptionText("1,0,1,2,3,4,5,6,0,1,2,3,4,5,6,0,1", "1"));
	}	
	
	private String getExceptionText(String shortHandGameState, String move){
		try{
			controller.move(shortHandGameState, move);
		}catch(Exception e){
			return e.getMessage();
		}
		return null;
	}

}
