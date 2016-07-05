package stratego.pieces;

import stratego.Game;
import stratego.util.Position;

/**
 * Represents a region on the board which cannot be entered by another piece.
 *
 * @author David J. Pearce
 *
 */
public class ImpassableTerrain implements Piece {

	@Override
	public Game.Player getPlayer() {
		return null;
	}

	@Override
	public boolean isValidMove(Position position, Game.Direction direction,
			int steps, Game game) {
		// For this kind of piece, it is never valid to move it.
		return true;
	}


    @Override
    public boolean canMove(Position position, Game game) {
        return false;
    }
}
