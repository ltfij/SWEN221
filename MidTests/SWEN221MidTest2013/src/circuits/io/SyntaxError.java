package circuits.io;

import java.util.ArrayList;

/**
 * A syntax error is a simple kind of error message which is used to signal a
 * problem occurred during parsing.
 * 
 * @author David J. Pearce
 * 
 */
public class SyntaxError extends Exception {
	public SyntaxError(String msg) {
		super(msg);		
	}	
}
