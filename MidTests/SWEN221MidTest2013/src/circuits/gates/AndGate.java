package circuits.gates;

/**
 * Represents an AND gate, which has the following truth table:
 * 
 * <pre>
 * A B | C
 * --------
 * T T | T
 * T F | F
 * F T | F
 * F F | F
 * </pre>
 * 
 * Here, A and B are the two inputs, whilst C is the output value. Furthermore,
 * T = true, and F = false.
 * 
 * @author David J. Pearce
 * 
 */
public class AndGate implements Gate {

	@Override
	public boolean getOutput(boolean inputA, boolean inputB) {
		return inputA && inputB;
	}

}
