package sokoban.moves;

import sokoban.Game;
import sokoban.io.GameError;
import sokoban.pieces.CratePiece;
import sokoban.pieces.Piece;
import sokoban.pieces.StoragePiece;
import sokoban.pieces.WallPiece;
import sokoban.util.Position;

/**
 * Represents a push move, where the player pushes a one create for a number of
 * steps in a given direction.
 *
 * @author David J. Pearce
 *
 */
public class PushingMove extends AbstractMove {

	public PushingMove(Game.Direction direction, int numberOfSteps) {
		super(direction, numberOfSteps);
	}

	@Override
	public void apply(Game game) throws GameError {
		Position workerPosition = findPlayer(game);

		// HINT: to update position of crate, use AbstractMove.movePiece()
		Position cratePosition = workerPosition.move(this.direction, 1);

		// not pushing air or other stuff
		if (game.getPiece(cratePosition) == null || !(game.getPiece(cratePosition) instanceof CratePiece)) {
			throw new GameError("   ");
		}

		// check along the path, if it is obstacled
		for (int i = 1; i <= this.numberOfSteps; i++) {
			Position newCratePos = cratePosition.move(direction, i);

			if (game.getPiece(newCratePos) != null) {
				if (game.getPiece(newCratePos) instanceof StoragePiece) {

				} else {
					throw new GameError("   ");
				}
			}
		}

		// Physically move the player on the board
		movePiece(cratePosition, game);
		movePiece(workerPosition, game);
	}
}
