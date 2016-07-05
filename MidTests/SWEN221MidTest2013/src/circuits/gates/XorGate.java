package circuits.gates;

/**
 * Represents an XOR gate, which has the following truth table:
 * 
 * <pre>
 * A B | C
 * --------
 * T T | F
 * T F | T
 * F T | T
 * F F | F
 * </pre>
 * 
 * Here, A and B are the two inputs, whilst C is the output value. Furthermore,
 * T = true, and F = false.
 * 
 * @author David J. Pearce
 * 
 */
public class XorGate implements Gate {

	@Override
	public boolean getOutput(boolean inputA, boolean inputB) {
	    
	    if (inputA == inputB) {
	        return false;

	    } else {
	        return true;
	    }
	    
	    

	}
}
