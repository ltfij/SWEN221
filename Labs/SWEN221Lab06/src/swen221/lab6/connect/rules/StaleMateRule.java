package swen221.lab6.connect.rules;

import swen221.lab6.connect.Game;
import swen221.lab6.connect.Game.Status;
import swen221.lab6.connect.core.Rule;

/**
 * Checks whether or not a stale mate has been reached.
 *
 * @author David J. Pearce
 *
 */
public class StaleMateRule implements Rule {

	@Override
	public Status apply(Game g) {
		// Here, we need to check how many tokens have been played so far. Since
		// each player starts with exactly eight tokens, there can be at most
		// eight tokens played by each player. After that point, we have reached
		// a stalemate. When this happens, we need to return the appropriate
		// status signal. And, yes, it is possible to reach stalemate.
	    
	    if (g.getMovesPlayed() >= 16 && g.getStatus() != Status.STALEMATE) {
	        return Status.STALEMATE;
	    } else {
	        return g.getStatus();
	    }
	}
}
