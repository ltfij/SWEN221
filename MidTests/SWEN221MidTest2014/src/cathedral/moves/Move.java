package cathedral.moves;

import cathedral.*;
import cathedral.io.GameError;

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
	protected Game.Player player;
	
	public Move(Game.Player player) {
		this.player = player;
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

