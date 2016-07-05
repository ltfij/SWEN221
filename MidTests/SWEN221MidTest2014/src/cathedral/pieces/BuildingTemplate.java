package cathedral.pieces;

import java.util.Arrays;

import cathedral.Game;
import cathedral.util.Position;

/**
 * A building template gives the shape of the building, and is used to place a
 * building onto the game board. To place a building we need to give it an
 * origin and a direction (North, South, East, West)
 * 
 * @author David J. Pearce
 * 
 */
public abstract class BuildingTemplate {
	private Position[] squares;
	
	/**
	 * Create a template from a given array of squares
	 * 
	 * @param squares
	 */
	public BuildingTemplate(Position... squares) {		
		this.squares = squares;
	}
	
	/**
	 * Get the name of this building type. For example, "CornerBuilding",
	 * "LargeBuilding", etc.
	 * 
	 * @return
	 */
	public abstract String getName();

	/**
	 * Place a given building onto the board with a given origin and direction.
	 * 
	 * @param origin The origin where the building is placed 
	 * @param direction The direction in which the building will be facing
	 * @param player The player placing the building
	 */
	public Building create(Position origin, Game.Direction direction,
			Game.Player player) {
		Position[] oriented = rotate(squares, direction);
		Position[] placed = translate(oriented, origin);
		return new Building(player, this, placed);
	}
	
	/**
	 * Rotate an array of squares to face in a given direction. The squares are
	 * assumed to be facing initially in the NORTH direction.
	 * 
	 * @param squares
	 * @param direction
	 */
	private static Position[] rotate(Position[] squares, Game.Direction direction) {
		switch(direction) {
		case NORTH:
			return squares;
		case EAST:
			return rotateClockwise(squares);
		case SOUTH:
			// should rotate clockwise twice
			return rotateClockwise(rotateClockwise(squares));			
		case WEST:
			// should rotate clockwise three times
			return rotateClockwise(rotateClockwise(rotateClockwise(squares)));
		default:
			return null; // should be unreachable
		}
	}
	
	/**
	 * Rotate the squares in a clockwise direction by 90 degrees. For example:
	 * 
	 * <pre>
	 *  1 |_|X|_|
	 *  0 |X|X|X|
	 * -1 |_|_|_|
	 *    -1 0 1
	 * </pre>
	 * 
	 * becomes:
	 * 
	 * <pre>
	 *  1 |_|X|_|
	 *  0 |_|X|X|
	 * -1 |_|X|_|
	 *    -1 0 1
	 * </pre>
	 * 
	 * @param squares
	 * @return
	 */
	private static Position[] rotateClockwise(Position[] squares) {
		Position[] nSquares = new Position[squares.length];
		for(int i=0;i!=squares.length;++i) {
			Position square = squares[i];
			nSquares[i] = new Position(square.getY(),-square.getX());
		}
		return nSquares;
	}
	
	/**
	 * Translate (or shift) the squares by a given x and y component. For
	 * example:
	 * 
	 * <pre>
	 *  1 |_|X|_|_|
	 *  0 |X|X|X|_|
	 * -1 |_|_|_|_|
	 *    -1 0 1 2
	 * </pre>
	 * 
	 * becomes:
	 * 
	 * <pre>
	 *  1 |_|_|_|_|
	 *  0 |_|_|X|_|
	 * -1 |_|X|X|X|
	 *    -1 0 1 2
	 * </pre>
	 * 
	 * If we translate by (1,1), which corresponds to one step in the X
	 * direction and one step in the Y direction.
	 * 
	 * @param squares
	 * @return
	 */
	private static Position[] translate(Position[] squares, Position translation) {
		Position[] nSquares = new Position[squares.length];
		for (int i = 0; i != squares.length; ++i) {
			Position square = squares[i];
			nSquares[i] = new Position(square.getX() + translation.getX(),
					square.getY() + translation.getY());
		}
		return nSquares;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(squares);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BuildingTemplate other = (BuildingTemplate) obj;
        if (!Arrays.equals(squares, other.squares))
            return false;
        return true;
    }
	
	
	
	
}
