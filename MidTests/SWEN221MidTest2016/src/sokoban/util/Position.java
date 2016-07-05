package sokoban.util;

import sokoban.Game.Direction;

/**
 * Represents an (x,y) position on the game board.
 * 
 * @author David J. Pearce
 * 
 */
public final class Position {
	private final int x;
	private final int y;
	
	/**
	 * Construct a position on the board
	 * 
	 * @param x
	 * @param y
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Calculate a relative position after moving a number of steps in a given
	 * direction from a starting point.
	 * 
	 * @param direction
	 *            --- Direction to move in
	 * @param steps
	 *            --- Number of steps to take
	 * @return
	 */
	public Position move(Direction direction, int steps) {
		int x = this.x;
		int y = this.y; 
		switch (direction) {
		case NORTH:
			y += steps;
			break;
		case SOUTH:
			y -= steps;
			break;
		case EAST:
			x += steps;
			break;
		case WEST:
			x -= steps;
			break;
		}
		return new Position(x,y);
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
