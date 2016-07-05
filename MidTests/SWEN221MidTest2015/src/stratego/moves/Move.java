package stratego.moves;

import stratego.Game;
import stratego.io.GameError;
import stratego.util.Position;

/**
 * Represents a move taken during a game. Since every move has a
 * position that is being affected, it is contained here.
 * 
 * @author David J. Pearce
 * 
 */
public abstract class Move {
	
	/**
	 * The player making this move
	 */
	protected final Game.Player player;
	
	/**
	 * The position on the board of the piece being moved.
	 */
	protected final Position position;
	
	/**
	 * The direction in which this move goes
	 */
	protected final Game.Direction direction;
	
	/**
	 * Construct this abstract move for a given player.
	 * 
	 * @param player
	 */
	public Move(Game.Player player, Position position,
			Game.Direction direction) {
		this.player = player;
		this.position = position;
		this.direction = direction;
	}
	
	/**
	 * Returns the player making this move.
	 * 
	 * @return
	 */
	public Game.Player getPlayer() {
		return player;
	}
	
	/**
	 * Returns the direction in which to move
	 * 
	 * @return
	 */
	public Game.Direction getDirection() {
		return direction;
	}
	
	/**
	 * Apply this move to the board, whilst checking that it is valid. If the
	 * move is invalid, then a syntax error should be thrown.
	 * 
	 * @param game
	 *            --- game to validate this move against.
	 */
	public abstract void apply(Game game) throws GameError;		
}
