package stratego.pieces;

import stratego.Game;
import stratego.Game.Direction;
import stratego.Game.Player;
import stratego.util.Position;

public class FlagPiece implements Piece {

	/**
	 * Represents which side this piece is on
	 */
	private final Game.Player player;

	public FlagPiece(Game.Player player) {
		this.player = player;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public boolean isValidMove(Position position, Direction direction,
			int steps, Game game) {
		// Flags cannot move
		return false;
	}

    @Override
    public boolean canMove(Position position, Game game) {
        return false;
    }
}
