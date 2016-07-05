package circuits.components;

import circuits.wiring.Pin;

/**
 * Represents a component on the circuit board. Each component uses a given
 * number pins which are consecutively numbered from 0. For example, logic gates
 * use exactly three pins numbered 0,1,2. Likewise, logic chips have 2*n gates
 * which give 6*n pins, numbered 0..(6*n)-1.
 * 
 * @author David J. Pearce
 * 
 */
public interface Component {

	/**
	 * Return the total number of pins used by this component.
	 * 
	 * @return
	 */	
	public int getNumberOfPins();
	
	/**
	 * Get the pin for a given pin number in this component.  
	 * 
	 * @param pin --- where 0 <= pin < getNumberOfPins() holds.
	 * @return
	 */
	public Pin getPin(int pin);
	
	/**
	 * Determine whether a given pin is contained within this component or not.
	 * If it is, the pin number should be returned. Otherwise, -1.
	 * 
	 * @param pin
	 * @return
	 */
	public int findPin(Pin pin);
	
	/**
	 * Check whether the given pin is an "input pin". For example, every logic
	 * gate has two input pins (numbered 0 and 1).
	 * 
	 * @param pin
	 * @return
	 */
	public boolean isInputPin(int pin);
	
	/**
	 * Check whether the given pin is an "output pin". For example, every logic
	 * gate has one output pin (numbered 2).
	 * 
	 * @param pin
	 * @return
	 */
	public boolean isOutputPin(int pin);
	
	/**
	 * Clock this component, which forces it to assign new values to the output
	 * pins based on current values of its input pins.
	 */
	public void clock();
}
