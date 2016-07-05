package cathedral.util;

import cathedral.pieces.Building;

/**
 * Represents a rectangle on the board, consisting of a start position and an
 * end position. 
 * 
 * @author David J. Pearce
 * 
 */
public final class Rectangle {
	private final Position start;
	private final Position end;
	
	public Rectangle(Position start, Position end) {
		this.start = start;
		this.end = end;
	}
	
	/**
	 * Get the start position of this rectangle on the board.
	 * 
	 * @return
	 */
	public Position getStart() {
		return start;
	}
	
	/**
	 * Get the end position of this rectangle on the board. 
	 * 
	 * @return
	 */
	public Position getEnd() {
		return end;
	}
	
	/**
	 * Check whether or not a given position is contained within this rectangle.
	 * 
	 * @param position
	 * @return
	 */
	public boolean contains(Position p) {
		int min_x = Math.min(start.getX(),end.getX());
		int max_x = Math.max(start.getX(),end.getX());
		int min_y = Math.min(start.getY(),end.getY());
		int max_y = Math.max(start.getY(),end.getY());
		
		return min_x <= p.getX() && p.getX() <= max_x 
				&& min_y <= p.getY() && p.getY() <= max_y;
	}
		
}
