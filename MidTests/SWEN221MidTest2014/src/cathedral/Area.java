package cathedral;

import cathedral.util.Position;
import cathedral.util.Rectangle;

/**
 * Represents an enclosed area on the board which is captured by one player or
 * another.
 * 
 * @author David J. Pearce
 * 
 */
public class Area {
	private Rectangle[] components;
	
	public Area(Rectangle... components) {
		this.components = components;
	}
	
	/**
	 * Check whether a given position is contained in this area or not.
	 * 
	 * @param position
	 * @return
	 */
	public boolean contains(Position position) {
		for(Rectangle r : components) {
			if(r.contains(position)) {
				return true;
			}
		}
		return false;
	}
		
	/**
	 * Check whether a given position is internal to this area or not. That is,
	 * whether or not the position is considered to be completely enclosed
	 * within the area and not touching any external positions.
	 * 
	 * @param position
	 * @return
	 */
	public boolean isInternal(Position position) {
		Position up = new Position(position.getX(), position.getY() - 1);
		Position down = new Position(position.getX(), position.getY() + 1);
		Position left = new Position(position.getX(), position.getX() - 1);
		Position right = new Position(position.getX(), position.getX() + 1);
		return contains(up) && contains(down) && contains(left)
				&& contains(right);
	}
}
