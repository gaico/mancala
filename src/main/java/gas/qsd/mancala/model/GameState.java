package gas.qsd.mancala.model;

import java.util.Arrays;

public class GameState {
	
	private final static int playerSouthId = 1;
	private final static int playerNorthId = 2;
	
	private final Player playerSouth;
	private final Player playerNorth;
		
	private final MancalaGame game;
	
	/**
	 * Creates a game state based on the a notation and then changes this state by applying the move that the player made.
	 * @param shortHandGameState	A short notation for the game state. The first number indicates the player that has the turn,
	 * 								the second indicates the winning player. Where 1 stands for the the player on the south 
	 * 								(bottom) side of the board, 2 for his opponent, 0 if neither has won or has the turn..
	 * 								The other numbers indicate the number of of stones in their consecutive pots.
	 * @param move					The number of the pot, belonging to the player that has the turn, that has been picked to make 
	 * 								the move from.
	 */
	public GameState(int[] shortHandGameState, int move){
		this.playerSouth = new Player(playerSouthId, shortHandGameState[0] == playerSouthId, shortHandGameState[1] == playerSouthId);
		this.playerNorth = new Player(playerNorthId, shortHandGameState[0] == playerNorthId, shortHandGameState[1] == playerNorthId);
		this.game = new MancalaGame(Arrays.copyOfRange(shortHandGameState, 2, shortHandGameState.length), playerSouth, playerNorth);
		this.game.move(move);
	}
	
	public GameState(MancalaGame game, Player playerSouth, Player playerNorth) {
		super();
		this.playerSouth = playerSouth;
		this.playerNorth = playerNorth;
		this.game = game;
	}
	
	public Player getPlayerSouth(){
		return this.playerSouth;
	}
	
	public Player getPlayerNorth(){
		return this.playerNorth;
	}
	
	public MancalaGame getGame(){
		return this.game;
	}

	

	
	
}
