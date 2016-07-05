package minesweeper;

/**
 * Represents an (x,y) position on the game board.
 * 
 * @author David J. Pearce
 * 
 */
public final class Position {
	private int x;
	private int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
