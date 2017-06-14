package gas.qsd.mancala.model;

public class Player {
	private final int id;
	private boolean hasTurn;
	private boolean hasWon;
	private Pot[] pots;
	
	public Player(final int id, boolean hasTurn, boolean hasWon) {
		this.id = id;
		this.hasTurn = hasTurn;
		this.hasWon = hasWon;
	}
	
	public boolean hasTurn() {
		return hasTurn;
	}
	
	public void setHasTurn(boolean hasTurn) {
		this.hasTurn = hasTurn;
	}

	public boolean hasWon() {
		return hasWon;
	}
	
	public void setHasWon(boolean hasWon) {
		this.hasWon = hasWon;
		this.hasTurn = false;
	}
	
	public int getId(){
		return this.id;
	}

	public void setPots(Pot[] pots) {
		this.pots = pots;
	}
	
	public Pot[] getPots(){
		return this.pots;
	}
	
	public Pot getMancala(){
		return this.pots[pots.length-1];
	}

	/**
	 * Determines if the player can make a move by determining if there is a 
	 * stone in any of the pots on his side of the board.
	 * @return	Indication if the player is allowed to make any moves.
	 */
	public boolean hasMoves() {
		for(int i=0;i<pots.length-1;i++){
			if(pots[i].stoneCount()>0){
				return true;
			}
		}
		return false;
	}

	/**
	 * Takes all the stones from all the pots on the players side of the board leaving all of them empty.
	 * @return	Number of stones that were in all the pots on the players side of the board.
	 */
	public int takeAllStones() {
		int allStones = 0;
		for(int i=0;i<pots.length-1;i++){
			allStones = allStones + pots[i].takeAllStones();
		}
		return allStones;
	}
	
	@Override
	public boolean equals(Object playerIn){
		return playerIn instanceof Player && this.id == ((Player)playerIn).id;
	}
	
	@Override
	public int hashCode() { 
		return this.id; 
	}

	
}
