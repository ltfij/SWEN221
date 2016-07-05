package circuits.wiring;

import java.util.List;

import circuits.components.Component;

/**
 * A Wire connects two pins together, and propagates the value from the input
 * pin along to the output pin when the wire is clocked.
 * 
 * @author David J. Pearce
 * 
 */
public class Wire {
	private final Pin input;
	private final Pin output;
	private final Joint[] joints;

	/**
	 * Create a Wire that connects an input Pin to an output Pin.
	 * 
	 * @param input
	 * @param output
	 */
	public Wire(Pin input, Pin output, Joint[] joints) {
		this.input = input;
		this.output = output;
		this.joints = joints;
	}

	/**
	 * Get the input pin that this wire is connected to
	 * @return
	 */	
	public Pin getInputPin() {
		return input;
	}

	/**
	 * Get the output pin that this wire is connected to
	 * @return
	 */
	public Pin getOutputPin() {
		return output;
	}
	
	/**
	 * Get the internal joints which make up this wire.
	 * 
	 * @return
	 */
	public Joint[] getJoints() {
		return joints;
	}
		
	/**
	 * Propagate the value of the input pin to the
	 * output pin.
	 */
	public void propagate() {
		// First, read value from input pin
		boolean value = input.read();

		// Second, write value to output pin
		output.write(value);
	}
}
