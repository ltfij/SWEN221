package circuits.components;

import circuits.gates.Gate;
import circuits.wiring.Pin;

/**
 * Represents a single logic gate on the circuit board. Every logic gate has
 * three pins 0,1 and 2. Pins 0 and 1 are the input pins, whilst pin 2 is the
 * output pin.
 * 
 * @author David J. Pearce
 * 
 */
public class LogicGate implements Component {
	private Gate gate;
	private Pin inputA;
	private Pin inputB;
	private Pin output;
	
	public LogicGate(Gate gate, Pin inputA, Pin inputB, Pin output) {
		this.gate = gate;
		this.inputA = inputA;
		this.inputB = inputB;
		this.output = output;
	}


	@Override
	public int getNumberOfPins() {
		return 3;
	}
	
	@Override
	public int findPin(Pin pin) {
		if(pin == inputA) {
			return 0;
		} else if(pin == inputB) {
			return 1;
		} else if(pin == output) {
			return 2;
		} else {
			return -1; // not contained
		}
	}

	@Override
	public Pin getPin(int pin) {
		switch (pin) {
		case 0:
			return inputA;
		case 1:
			return inputB;
		case 2:
			return output;
		}
		throw new IllegalArgumentException("invalid pin requested");
	}

	@Override
	public boolean isInputPin(int pin) {
		return pin == 0 || pin == 1;
	}

	@Override
	public boolean isOutputPin(int pin) {
		return pin == 2;
	}
	
	public Gate getGate() {
	    return this.gate;
	}

	@Override
	public void clock() {
		// Read the current value of the input pins, and compute the output for
		// the gate based on this.
		boolean value = gate.getOutput(inputA.read(), inputB.read());

		// Write the update value to the output pin.
		output.write(value);
	}
}
