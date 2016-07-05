package swen221.lab6.connect.core;

import swen221.lab6.connect.Game;
import swen221.lab6.connect.Game.Status;

/**
 * Represents a generic rule for checking some aspect of the game.
 * 
 * @author David J. Pearce
 *
 */
public interface Rule {	
	/**
	 * Apply a given rule to the game. This may update the board, for example by
	 * removing tokens after a capture. It may also determine that the game is
	 * over in some way (e.g. stalemate).
	 * 
	 * @param g
	 * @return
	 */
	public Status apply(Game g);
}
