package gas.qsd.mancala.translator;

import java.util.Arrays;

import gas.qsd.mancala.model.MancalaGame;
import gas.qsd.mancala.model.Player;
import gas.qsd.mancala.model.Pot;

public class MancalaTranslator {
	
	private final static int playerSouthId = 1;
	private final static int playerNorthId = 2;
	
	public MancalaGame gameFromShortHandGameState(final String shortHandGameStateString){
		int[] shortHandGameState = shortHandStringToShortHand(shortHandGameStateString);
		int[] shortHandBoardState = Arrays.copyOfRange(shortHandGameState, 2, shortHandGameState.length);
		
		Player southPlayer = new Player(playerSouthId, shortHandGameState[0] == playerSouthId, shortHandGameState[1] == playerSouthId);
		Player northPlayer = new Player(playerNorthId, shortHandGameState[0] == playerNorthId, shortHandGameState[1] == playerNorthId);

		Pot[] pots = new Pot[MancalaGame.POTS_PER_PLAYER * 2];
		
		if(shortHandBoardState.length < pots.length){
			throw new IllegalArgumentException();
		}
				
		for(int i=0; i < pots.length; i++){
			Player owner = i < MancalaGame.POTS_PER_PLAYER ? southPlayer : northPlayer;
			pots[i] = new Pot(shortHandBoardState[i], (i+1)%MancalaGame.POTS_PER_PLAYER==0, owner);
		}
		
		for(int i=0; i < pots.length; i++){
			if(i == pots.length-1){
				pots[i].setNextPot(pots[0]);
				pots[i].setPotAccross(pots[MancalaGame.POTS_PER_PLAYER-1]);
			}else{
				pots[i].setNextPot(pots[i+1]);
				if(i == MancalaGame.POTS_PER_PLAYER-1){
					pots[i].setPotAccross(pots[pots.length-1]);
				}else{
					pots[i].setPotAccross(pots[pots.length-i-2]);
				}
			}
		}
		southPlayer.setPots(Arrays.copyOf(pots, MancalaGame.POTS_PER_PLAYER));
		northPlayer.setPots(Arrays.copyOfRange(pots, MancalaGame.POTS_PER_PLAYER, pots.length));
		
		return new MancalaGame(pots, southPlayer, northPlayer);
	}
	
	public String shortHandStringFromGame(MancalaGame game){
		int out[] = new int[(MancalaGame.POTS_PER_PLAYER * 2) + 2];
		
		Player playerSouth = game.getPlayerSouth();
		Player playerNorth = game.getPlayerNorth();
		
		out[0] = 0;
		if(playerSouth.hasTurn()){
			out[0] = playerSouth.getId();
		}
		if(playerNorth.hasTurn()){
			out[0] = playerNorth.getId();
		}
		
		out[1] = 0;
		if(playerSouth.hasWon()){
			out[1] = playerSouth.getId();
		}
		if(playerNorth.hasWon()){
			out[1] = playerNorth.getId();
		}
		
		Pot[] southSidePots = game.getSouthSidePots();
		for(int i=0; i < southSidePots.length; i++){
			out[i+2] = southSidePots[i].stoneCount();
		}
		
		Pot[] northSidePots = game.getNorthSidePots();
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
