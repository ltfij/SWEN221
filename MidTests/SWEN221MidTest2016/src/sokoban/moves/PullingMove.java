package sokoban.moves;

import sokoban.Game;
import sokoban.Game.Direction;
import sokoban.io.GameError;
import sokoban.pieces.CratePiece;
import sokoban.pieces.StoragePiece;
import sokoban.util.Position;

public class PullingMove extends AbstractMove {

	public PullingMove(Direction direction, int numberOfSteps) {
		super(direction, numberOfSteps);

	}

	@Override
	public void apply(Game game) throws GameError {

		Position workerPosition = findPlayer(game);

		// HINT: to update position of crate, use AbstractMove.movePiece()
		Position cratePosition = workerPosition.move(this.direction, -1);

		// not pulling air or other stuff
		if (game.getPiece(cratePosition) == null || !(game.getPiece(cratePosition) instanceof CratePiece)) {
			throw new GameError("   ");
		}

		// any obstacles on the path?
		for (int i = 1; i <= this.numberOfSteps; i++) {
			Position newCratePos = workerPosition.move(direction, i);

			if (game.getPiece(newCratePos) != null) {
				if (game.getPiece(newCratePos) instanceof StoragePiece) {

				} else {
					throw new GameError("   ");
				}
			}
		}

		// Physically move the player on the board
		movePiece(workerPosition, game);
		movePiece(cratePosition, game);
	}

}
