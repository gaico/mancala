package gas.qsd.mancala.model;

import java.util.Arrays;

public class MancalaGame {
	public static final int POTS_PER_PLAYER = 7;
	
	private final Pot[] pots;
	private final Player southPlayer;
	private final Player northPlayer;
	
	public MancalaGame(final Pot[] pots, final Player southPlayer, final Player northPlayer) {
		this.pots = pots;
		this.southPlayer = southPlayer;
		this.northPlayer = northPlayer;
	}
		
	public Pot[] getSouthSidePots(){
		return Arrays.copyOf(this.pots, POTS_PER_PLAYER);
	}
	
	public Pot[] getNorthSidePots(){
		return Arrays.copyOfRange(this.pots, POTS_PER_PLAYER, pots.length);
	}
	
	public Player getPlayerSouth() {
		return this.southPlayer;
	}

	public Player getPlayerNorth() {
		return this.northPlayer;
	}
	
	private Player getPlayerThatHasTurn(){
		return southPlayer.hasTurn()? southPlayer : northPlayer;
	}
	
	private Player getOpponent(Player playerIn){
		if(playerIn.equals(southPlayer)){
			return northPlayer;
		}
		return southPlayer;
	}

	public MancalaGame move(final int movePotNumber) {
		final Player playerCurrentTurn = getPlayerThatHasTurn();
		if(movePotNumber > playerCurrentTurn.getPots().length){
			throw new IllegalArgumentException("Startingpot not on board");
		}
		final Pot potToStartMoveFrom = playerCurrentTurn.getPots()[movePotNumber-1];
		if(potToStartMoveFrom.isMancala()){
			throw new IllegalArgumentException("Startingpot is a mancala, this is not allowed");
		}
		if(potToStartMoveFrom.stoneCount()<1){
			throw new IllegalArgumentException("Pot to move from may not be empty");
		}
		
		final Pot potThatReceivedLastStone = potToStartMoveFrom.move(playerCurrentTurn); 
		
		//Rule: If the player's last stone ends in an empty pit on his or her own side, 
		//      the player captures all of the stones in the pit directly across the board from where the last stone was placed
		if(!potThatReceivedLastStone.isMancala()
				&& potThatReceivedLastStone.stoneCount() == 1 
				&& potThatReceivedLastStone.getPotAccross().stoneCount() > 0
				&& potThatReceivedLastStone.getOwner().equals(playerCurrentTurn)){
			playerCurrentTurn.getMancala().addStones(potThatReceivedLastStone.getPotAccross().takeAllStones());
			playerCurrentTurn.getMancala().addStones(potThatReceivedLastStone.takeAllStones());
		}
		
		//Rule: If the player's last stone ends in his or her own Mancala, the player gets another turn.
		Player playerNextTurn;
		if(potThatReceivedLastStone.isMancala()){
			playerNextTurn = new Player(playerCurrentTurn.getId(), true, false);
			playerNextTurn.setPots(playerCurrentTurn.getPots());
		}else{
			playerNextTurn = new Player(getOpponent(playerCurrentTurn).getId(), true, false);
			playerNextTurn.setPots(getOpponent(playerCurrentTurn).getPots());
			playerCurrentTurn.setHasTurn(false);
			getOpponent(playerCurrentTurn).setHasTurn(true);
		}
		
		//Rule: The game ends when one player cannot move on his or her turn, 
		//      at which time the other player captures all of the stones remaining on his or her side of the board. 
		if(!playerNextTurn.hasMoves()){
			playerCurrentTurn.getMancala().addStones(playerCurrentTurn.takeAllStones());
			getOpponent(playerCurrentTurn).getMancala().addStones(getOpponent(playerCurrentTurn).takeAllStones());
			
			playerCurrentTurn.setHasWon(playerCurrentTurn.getMancala().stoneCount() > getOpponent(playerCurrentTurn).getMancala().stoneCount());
			getOpponent(playerCurrentTurn).setHasWon(getOpponent(playerCurrentTurn).getMancala().stoneCount() > playerCurrentTurn.getMancala().stoneCount());
		}
				
		return new MancalaGame(pots, getOpponent(playerCurrentTurn), playerCurrentTurn);
	}
}
