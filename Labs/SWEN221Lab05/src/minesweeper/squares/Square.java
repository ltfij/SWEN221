package minesweeper.squares;

/**
 * Represents a square on the game board. Every square can be either hidden or
 * not, and flagged or not.
 * 
 * @author David J. Pearce
 * 
 */
public abstract class Square {
	private boolean isHidden;
	private boolean isFlagged;
	
	/**
	 * Construct a square which initially hidden and unflagged.
	 */
	public Square() {
		this.isFlagged = false;
		this.isHidden = true;
	}
	
	/**
	 * Check whether this square is flagged or not.
	 * 
	 * @return
	 */
	public boolean isFlagged() {
		return isFlagged;
	}
	
	/**
	 * Set the flag status of this square.
	 * 
	 * @param flag
	 *            --- true if being flagged; false if being unflagged.
	 */
	public void setFlagged(boolean flag) {
		this.isFlagged = flag;
	}
	
	/**
	 * Check whether this square is hidden or not.
	 * 
	 * @return
	 */
	public boolean isHidden() {
		return isHidden;
	}
	
	/**
	 * Set the hidden status of this square.
	 * 
	 * @param hidden
	 *            --- true if being hidden; false if being unhidden.
	 */
	public void setHidden(boolean hidden) {
		this.isHidden = hidden;
	}	
}
