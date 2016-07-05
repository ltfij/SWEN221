package sokoban.moves;

import sokoban.Game;
import sokoban.io.GameError;
import sokoban.pieces.Piece;
import sokoban.pieces.PlayerPiece;
import sokoban.pieces.StoragePiece;
import sokoban.util.Position;

/**
 * Implements a basic walking move in the game, where the play moves some number
 * of steps in a given direction. For the move to be considered valid, the
 * places the player moves must be empty.
 *
 * @author David J. Pearce
 *
 */
public class WalkingMove extends AbstractMove {

	public WalkingMove(Game.Direction direction, int numberOfSteps) {
		super(direction, numberOfSteps);
	}

	@Override
	public void apply(Game game) throws GameError {
		Position position = findPlayer(game);
		// Check that it is safe to apply this move
		// HINT: use AbstractMove.checkMovementValid() here
		for (int i = 1; i <= this.numberOfSteps; i++) {
			Position newP = position.move(direction, i);

			if (game.getPiece(newP) != null) {
				if (game.getPiece(newP) instanceof StoragePiece) {

				} else {
					throw new GameError("   ");
				}
			}
		}

		// Physically move the play on the board
		movePiece(position, game);
	}

}
