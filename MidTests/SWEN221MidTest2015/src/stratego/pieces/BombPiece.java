package stratego.pieces;

import stratego.Game;
import stratego.Game.Direction;
import stratego.Game.Player;
import stratego.util.Position;

/**
 * Represents a bomb piece on the game board.
 *
 * @author David J. Pearce
 *
 */
public class BombPiece implements Piece {

    private final Game.Player player;

	public BombPiece(Game.Player player) {
	    this.player = player;
	}

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isValidMove(Position position, Direction direction, int steps, Game game) {
        
        return false;

    }

    @Override
    public boolean canMove(Position position, Game game) {

        return false;
    }
}
