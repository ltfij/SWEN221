package stratego.moves;

import stratego.Game;
import stratego.Game.Direction;
import stratego.Game.Player;
import stratego.io.GameError;
import stratego.pieces.Piece;
import stratego.pieces.RegularPiece;
import stratego.util.Position;

/**
 * Represents a basic move on the board, where one player's piece moves to
 * another position on the board but does not attempt to take another piece.
 *
 * @author David J. Pearce
 *
 */
public class BasicMove extends Move {

	/**
	 * The number of steps taken
	 */
	protected final int steps;

	/**
	 * Construct a basic move for a given player going in one direction for a
	 * number of steps.
	 *
	 * @param player
	 *            --- Player making the move
	 * @param position
	 *            --- Starting position of piece to be moved
	 * @param direction
	 *            --- Direction into which to move
	 * @param steps
	 *            --- Number of steps to move
	 */
	public BasicMove(Player player, Position position, Direction direction, int steps) {
		super(player, position, direction);
		this.steps = steps;
	}

	@Override
	public void apply(Game game) throws GameError {
		Position newPosition = new Position(position,direction,steps);
		Piece opponent = game.getPiece(newPosition);
		Piece p = game.getPiece(position);
		
		// First, check that piece exists at given position, and is owned by
		// this player and that destination is empty.
		if (opponent != null || p == null) {
		    throw new GameError("Invalid move for piece");
		}
		
		if (player != p.getPlayer()) {
		    throw new GameError("Invalid move for piece");
		}
		
		// Second, check that piece is permitted to move a given number of steps
		// in the given direction.
		if(!p.isValidMove(position, direction, steps, game)) {
			throw new GameError("Invalid move for piece");
		}
		
		// scout cannot move through an occupied position
		if (p instanceof RegularPiece) {
		    RegularPiece rp = (RegularPiece)p;
		    if (rp.getRank() == 1) {
		        for (int step=1; step <= steps; step++) {
		            Position passPosition = new Position(position,direction,step);
		            if (game.getPiece(passPosition) != null) {
		                throw new GameError("Invalid move for piece");
		            }
		        }
		    }
		}
		
		if (game.isEnded()) {
		    throw new GameError("Invalid move for piece");
		}
		

		// Third, apply the move!
		game.setPiece(newPosition,p);
		game.setPiece(position,null);
		
		Player another;
		if (player == Player.BLUE) {
		    another = Player.RED;
		} else {
		    another = Player.BLUE;
		}
		
		if (game.noPossibleMoves(player)) {
		    game.setCannotMove(player);
		}
		
		if (game.noPossibleMoves(another)) {
            game.setCannotMove(another);
        }
		
	}


	/**
	 * Returns the number of steps to move
	 *
	 * @return
	 */
	public int getSteps() {
		return steps;
	}
}
