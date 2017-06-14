package gas.qsd.mancala.model;

public class Pot {
	private Pot nextPot;
	private Pot potAccross;
	private int numberOfStones;
	private final boolean isMancala;
	private final Player owner;
	
	public Pot(final int numberOfStones, final boolean isMancala, final Player owner) {
		this.numberOfStones = numberOfStones;
		this.isMancala = isMancala;
		this.owner = owner;
	}
	
	public Player getOwner(){
		return this.owner;
	}
	
	public boolean isMancala(){
		return this.isMancala;
	}
	
	public Pot getPotAccross() {
		return this.potAccross;
	}
	
	public void setPotAccross(Pot potAccross) {
		this.potAccross = potAccross;
		
	}

	public Pot getNextPot() {
		return this.nextPot;
	}
	
	public void setNextPot(Pot nextPot) {
		this.nextPot = nextPot;
	}
	
	public int stoneCount() {
		return this.numberOfStones;
	}
	
	/**
	 * Picks up all stones in a pot, leaving it empty, and distributes the stones across the board
	 * dropping 1 stone in every consecutive pot skipping the opponents mancala.
	 * @param  playerThatMakesMove	The player that has the turn
	 * @return Pot that the last stone was dropped in
	 */
	public Pot move(final Player playerThatMakesMove){
		Pot out = nextPot.sow(playerThatMakesMove, this.takeAllStones());
		return out;
	}
	
	/**
	 * Distributes stones across the board by keeping on stone in this pot and calling the sow method
	 * on the next pot on the board skipping the opponents mancala.
	 * dropping 1 stone in every consecutive pot.
	 * @param 	playerThatMakesMove 	The player that has the turn
	 * @param 	numberOfStonesIn		The number of stones being distributed across the board
	 * @return	Pot that the last stone was dropped in
	 */
	private Pot sow(final Player playerThatMakesMove, int numberOfStonesIn){
		//Rule: Skip opponents Mancala
		if(this.isMancala && !this.owner.equals(playerThatMakesMove)){
			return nextPot.sow(playerThatMakesMove, numberOfStonesIn);
		}
		numberOfStonesIn--;
		this.numberOfStones++;
		if(numberOfStonesIn == 0){
			return this;
		}
		return nextPot.sow(playerThatMakesMove, numberOfStonesIn);
	}
	
	/**
	 * Takes all stones from this pot leaving it empty
	 * @return The number of stones that were in this pot
	 */
	public int takeAllStones() {
		int out = this.numberOfStones;
		this.numberOfStones = 0;
		return out;
	}

	public void addStones(int numberOStonesToAdd) {
		this.numberOfStones = this.numberOfStones + numberOStonesToAdd;
	}

	
	
}
