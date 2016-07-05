package sokoban.moves;

import sokoban.Game;
import sokoban.pieces.CratePiece;
import sokoban.pieces.Piece;
import sokoban.pieces.PlayerPiece;
import sokoban.pieces.StoragePiece;
import sokoban.util.Position;

/**
 * Provides a common implementation for the walking and pushing moves.
 *
 * @author David J. Pearce
 *
 */
public abstract class AbstractMove implements Move {
	/**
	 * The direction on the board that the player is pushing in.
	 */
	protected Game.Direction direction;

	/**
	 * The number of steps taken
	 */
	protected int numberOfSteps;

	public AbstractMove(Game.Direction direction, int numberOfSteps) {
		this.direction = direction;
		this.numberOfSteps = numberOfSteps;
	}

	/**
	 * Responsible for determining the players position on the game board.
	 *
	 * @param game
	 * @return
	 */
	protected Position findPlayer(Game game) {
		for (int y = 0; y != game.getHeight(); ++y) {
			for (int x = 0; x != game.getWidth(); ++x) {
				Piece piece = game.getPiece(new Position(x, y));
				if (piece instanceof PlayerPiece) {
					return new Position(x, y);
				}
			}
		}
		throw new IllegalArgumentException("Player not present on board!");
	}

	/**
	 * Check whether the given movement proposed in this move is valid or not.
	 * This is done by checking in turn each position in a path from a given
	 * starting position is empty.
	 *
	 * @param position
	 *            The starting position of the player
	 * @param game
	 *            The game board the move is being applied to
	 * @return True if the move is valid, false otherwise
	 */
	protected boolean isMovementValid(Position position, Game game) {
		// Move one step at a time, constantly checking that it is safe to do
		for (int i = 0; i != numberOfSteps; ++i) {
			position = position.move(direction, 1);
			if (!isEmptyLocation(position, game)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check whether a give position on the board constitutes an "empty"
	 * position. That is, one which we can move through or push crates through.
	 *
	 * @param position
	 * @param game
	 * @return
	 */
	private boolean isEmptyLocation(Position position, Game game) {
		return game.getPiece(position) == null;
	}

	/**
	 * Physically move a given piece (e.g. player or crate) on the board from
	 * the given position to the calculated new position.
	 *
	 * @param position
	 *            The starting position of the piece
	 * @param game
	 *            The game board being updated
	 */
	protected void movePiece(Position position, Game game) {

		Piece piece = game.getPiece(position);

		if (piece instanceof PlayerPiece) {
			PlayerPiece pPiece = (PlayerPiece) piece;
			if (pPiece.isStored() == true) {
				pPiece.setStored(false);
				game.setPiece(position, new StoragePiece());
			} else {
				game.setPiece(position, null);
			}

		} else if (piece instanceof CratePiece) {
			CratePiece cPiece = (CratePiece) piece;
			if (cPiece.isStored() == true) {
				cPiece.setStored(false);
				game.setPiece(position, new StoragePiece());
			} else {
				game.setPiece(position, null);
			}
		} else {
			game.setPiece(position, null);

		}

		Position newPosition = position.move(direction, numberOfSteps);

		if (game.getPiece(newPosition) instanceof StoragePiece) {

			if (piece instanceof PlayerPiece) {
				PlayerPiece pPiece = (PlayerPiece) piece;
				pPiece.setStored(true);
				game.setPiece(newPosition, pPiece);
			} else if (piece instanceof CratePiece) {
				CratePiece cPiece = (CratePiece) piece;
				cPiece.setStored(true);
				game.setPiece(newPosition, cPiece);
			}

		} else {
			game.setPiece(newPosition, piece);
		}

	}

}
