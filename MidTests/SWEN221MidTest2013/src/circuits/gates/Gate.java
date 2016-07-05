package circuits.gates;

/**
 * Represents a logic gate in a circuit. A logic gate can be, for example, an
 * AND gate, an OR gate, or an XOR gate. Each gate has two inputs, and one
 * output. The value of output is determine by the value of the input, and the
 * type of the gate (e.g. AND, OR, XOR, etc).
 * 
 * @author David J. Pearce
 * 
 */
public interface Gate {
	
	/**
	 * Computer the output value for this gate for the given input values.
	 * 
	 * @param inputA
	 *            --- value of first input.
	 * @param inputB
	 *            --- value of second input.
	 * @return
	 */
	public boolean getOutput(boolean inputA, boolean inputB);
}
