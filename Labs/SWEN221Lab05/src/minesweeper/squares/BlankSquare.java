package minesweeper.squares;

/**
 * Represents a blank square on the game board. Every blank square knows how
 * many bombs are around it.
 * 
 * @author David J. Pearce
 * 
 */
public class BlankSquare extends Square {
	private int numberOfBombsAround; // number of bombs in surrounding squares

	/**
	 * Create a blank square initially set with zero bombs in surrounding area.
	 */
	public BlankSquare(int numberOfBombsAround) {
		this.numberOfBombsAround = numberOfBombsAround;
	}
	
	/**
	 * Get the number of bombs in the surrounding area.
	 * 
	 * @return
	 */
	public int getNumberOfBombsAround() {
		return numberOfBombsAround;
	}	
}
