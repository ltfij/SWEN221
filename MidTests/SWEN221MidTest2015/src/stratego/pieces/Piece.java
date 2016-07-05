package stratego.pieces;

import stratego.Game;
import stratego.util.Position;

/**
 * Represents an arbitrary piece on the board. This include pieces which can be
 * moved by the plaer, as well as those pieces which are immovable or represent
 * terrain.
 *
 * @author David J. Pearce
 *
 */
public interface Piece {
	/**
	 * Get the player which owns this piece, or null if no player does. The
	 * latter occurs only for instances of ImpassableTerrain.
	 *
	 * @return
	 */
	public Game.Player getPlayer();

	/**
	 * Check whether or not this piece can move in a given direction for a given
	 * number of steps.
	 *
	 * @param position
	 *            --- position of this piece before move.
	 * @param direction
	 *            --- direction in which to move
	 * @param steps
	 *            --- number of steps to take
	 * @param game
	 *            --- game state on which the validity of this move is being
	 *            checked.
	 * @return
	 */
	public boolean isValidMove(Position position, Game.Direction direction, int steps, Game game);
	
	public boolean canMove(Position position, Game game);
}
