package cathedral.io;

import java.util.ArrayList;

/**
 * A game error is a simple kind of error message which is used to signal a
 * problem occurred during parsing.
 * 
 * @author David J. Pearce
 * 
 */
public class GameError extends Exception {
	public GameError(String msg) {
		super(msg);		
	}	
}
