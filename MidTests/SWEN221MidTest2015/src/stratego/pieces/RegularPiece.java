package stratego.pieces;

import java.util.ArrayList;

import stratego.Game;
import stratego.Game.Direction;
import stratego.Game.Player;
import stratego.util.Position;

/**
 * Represents a "regular" piece on the board. That is, a piece which has a rank,
 * but no other special abilities.
 *
 * @author David J. Pearce
 *
 */
public class RegularPiece implements Piece {
	// Some constants to help
	public static final int MARSHAL = 9;
	public static final int GENERAL = 8;
	public static final int COLONEL = 7;
	public static final int MAJOR = 6;
	public static final int CAPTAIN = 5;
	public static final int LIEUTENANT = 4;
	public static final int SERGENT = 3;
	public static final int MINER = 2;
	public static final int SCOUT = 1;
	public static final int SPY = 0;

	/**
	 * Represents which side this piece is on
	 */
	private final Game.Player player;

	/**
	 * Represents the rank of this piece.
	 */
	private final int rank;

	/**
	 * Construct a regular piece with a given rank.
	 *
	 * @param rank
	 */
	public RegularPiece(Game.Player player, int rank) {
		this.player = player;
		this.rank = rank;
	}

	/**
	 * Get the rank of this piece, which is an integer from 1 to 10 (inclusive).
	 *
	 * @return
	 */
	public int getRank() {
		return rank;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public boolean isValidMove(Position startPosition, Direction direction,
			int steps, Game game) {		
	    
	    if (rank != 1 && steps > 1) {
	        return false;
	    }

		return true;
	}

    @Override
    public boolean canMove(Position position, Game game) {
        
        ArrayList<Position> possibleMovePositions = possibleMovePositions(position, game);
        for (Position p : possibleMovePositions) {
            if (game.getPiece(p) == null) {
                return true;
            }
        }
        
        return false;
    }

    private ArrayList<Position> possibleMovePositions(Position position, Game game) {
        
        ArrayList<Position> possibleMovePositions = new ArrayList<>();
        Position up = new Position(position, Direction.NORTH, 1);
        Position down = new Position(position, Direction.SOUTH, 1);
        Position left = new Position(position, Direction.WEST, 1);
        Position right = new Position(position, Direction.EAST, 1);
        
        if (up.isValidPosition(game)) {
            possibleMovePositions.add(up);
        }
        if (down.isValidPosition(game)) {
            possibleMovePositions.add(down);
        }
        if (left.isValidPosition(game)) {
            possibleMovePositions.add(left);
        }
        if (right.isValidPosition(game)) {
            possibleMovePositions.add(right);
        }
        
        return possibleMovePositions;
        
    }
}
