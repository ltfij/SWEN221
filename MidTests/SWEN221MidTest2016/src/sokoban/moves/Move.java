package sokoban.moves;

import sokoban.Game;
import sokoban.io.GameError;

/**
 * Represents a move taken during a game, which is either a walking move or a
 * pushing move.
 * 
 * @author David J. Pearce
 * 
 */
public interface Move {	
	/**
	 * Apply this move to the board, whilst checking that it is valid. If the
	 * move is invalid, then a game error should be thrown.
	 * 
	 * @param game
	 *            --- game to validate this move against.
	 */
	public void apply(Game game) throws GameError;		
}
