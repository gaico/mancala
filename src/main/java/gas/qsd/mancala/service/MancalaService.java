package gas.qsd.mancala.service;

import java.util.Arrays;

import gas.qsd.mancala.model.GameState;
import gas.qsd.mancala.model.MancalaGame;
import gas.qsd.mancala.model.Player;
import gas.qsd.mancala.model.Pot;

public class MancalaService {

	public String getNewShortHandGameStateFrom(String shortHandGameState, String move) {
		return gameStateToShortHandString(new GameState(shortHandStringToShortHand(shortHandGameState), Integer.parseInt(move)));
	}
	
	private String gameStateToShortHandString(GameState gameState){
		int out[] = new int[(MancalaGame.POTS_PER_PLAYER * 2) + 2];
		
		Player playerSouth = gameState.getPlayerSouth();
		Player playerNorth = gameState.getPlayerNorth();
		
		out[0] = 0;
		if(playerSouth.hasTurn()){
			out[0] = playerSouth.getId();
		}
		if(gameState.getPlayerNorth().hasTurn()){
			out[0] = playerNorth.getId();
		}
		
		out[1] = 0;
		if(playerSouth.hasWon()){
			out[1] = playerSouth.getId();
		}
		if(playerNorth.hasWon()){
			out[1] = playerNorth.getId();
		}
		
		Pot[] southSidePots = gameState.getGame().getSouthSidePots();
		for(int i=0; i < southSidePots.length; i++){
			out[i+2] = southSidePots[i].stoneCount();
		}
		
		Pot[] northSidePots = gameState.getGame().getNorthSidePots();
		for(int i=0; i < northSidePots.length; i++){
			out[i+2+MancalaGame.POTS_PER_PLAYER] = northSidePots[i].stoneCount();
		}
		String shortHand = Arrays.toString(out);
		return shortHand.substring(1, shortHand.length()-1).replaceAll("\\s+","");
	}
	
	private int[] shortHandStringToShortHand(String input){
		String[] tokens = input.split(",");
		int[] shorthandGameState = new int[tokens.length];
		for(int i=0; i<tokens.length; i++){
			shorthandGameState[i] = Integer.parseInt(tokens[i].trim());
		}
		return shorthandGameState;
	}

}
