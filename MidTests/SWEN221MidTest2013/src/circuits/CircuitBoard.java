package circuits;

import circuits.components.*;
import circuits.wiring.Joint;
import circuits.wiring.Pin;
import circuits.wiring.Wire;

/**
 * Represents a single-sided circuit board which is made up of a number of pins,
 * wires and components. Wires connect pins together, and may be jointed in
 * order to route around items on the board. Components are connected to pins,
 * and implement different functionality (e.g. Logic Gates).
 * 
 * @author David J. Pearce
 * 
 */
public class CircuitBoard {
	private final int width;
	private final int height;
	private final Pin[] pins;
	private final Wire[] wires;
	private final Component[] components;

	/**
	 * Construct a circuit board of a given width and height from arrays of
	 * pins, wires and components.
	 * 
	 * @param width --- width of the board.
	 * @param height --- height of the board.
	 * @param pins --- pins on the board.
	 * @param wires --- wires on the board.
	 * @param components --- components on the board.
	 */
	public CircuitBoard(int width, int height, Pin[] pins, Wire[] wires,
			Component[] components) {
		this.width = width;
		this.height = height;
		this.pins = pins;
		this.wires = wires;
		this.components = components;
	}

	/**
	 * Get the width of the board.
	 * 
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Get the height of the board.
	 * 
	 * @return
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Get the number of pins in this circuit.
	 * 
	 * @return
	 */
	public int getNumberOfPins() {
		return pins.length;
	}

	/**
	 * Get a given pin in this circuit.
	 * 
	 * @return
	 */
	public Pin getPin(int pin) {
		return pins[pin];
	}
	
	/**
	 * Get the number of wires in this circuit.
	 * 
	 * @return
	 */
	public int getNumberOfWires() {
		return wires.length;
	}

	/**
	 * Get a given wire in this circuit
	 * 
	 * @return
	 */
	public Wire getWire(int wire) {
		return wires[wire];
	}
	
	/**
	 * Get the number of components in this circuit.
	 * 
	 * @return
	 */
	public int getNumberOfComponents() {
		return components.length;
	}

	/**
	 * Get a given component in this circuit
	 * 
	 * @return
	 */
	public Component getComponent(int component) {
		return components[component];
	}
	
	/**
	 * Run the circuit for a given number of clock signals. This will propagate
	 * pin values through components they connect to, thereby generating updated
	 * values for output pins, and so on.
	 * 
	 * @param numberOfClocks
	 */
	public void run(int numberOfClocks) {
		for (int i = 0; i != numberOfClocks; ++i) {
			// First, clock all the components
			for (Component c : components) {
				c.clock();
			}

			// Second, propagate across all wires
			for (Wire w : wires) {
				w.propagate();
			}
		}
	}
	
	/**
	 * Construct a textual representation of the circuit board which is useful
	 * for debugging.
	 */
	public String toString() {
		int canvasWidth = width * 3;
		int canvasHeight = height * 3;
		char[] canvas = new char[canvasWidth * canvasHeight];

		// Initially, set all positions as blanks
		for(int i=0;i!=canvas.length;++i) {
			canvas[i] = ' ';
		}
		// Then, draw all the wires
		for(int i=0;i!=wires.length;++i) {
			drawWire(wires[i],canvas);
		}
		// Then, draw all the pins
		for(int i=0;i!=pins.length;++i) {
			drawPin(i,pins[i],canvas);
		}		
		
		// Now, generate a string from the canvas 
		String r = "";
		for(int y=0;y!=canvasHeight;++y) {
			for(int x=0;x!=canvasWidth;++x) {
				r = r + canvas[(y*canvasWidth)+x];
			}	
			r = r + "\n";
		}
		
		// Finally, add the value of each pin
		r = r + "\n";
		for(int i=0;i!=pins.length;++i) {
			r = r + "pin #" + i + " = " + pins[i].read() + "\n";
		}
		return r;
	}
	
	/**
	 * Draw a single wire onto the canvas.
	 * 
	 * @param wire
	 * @param canvas
	 */
	private void drawWire(Wire wire, char[] canvas) {
		Joint last = wire.getInputPin();
		for(Joint j : wire.getJoints()) {
			drawSegment(last,j,canvas);
			last = j;
		}
		drawSegment(last,wire.getOutputPin(),canvas);
	}

	/**
	 * Draw a wire segment onto the canvas.
	 * 
	 * @param from
	 * @param to
	 * @param canvas
	 */
	private void drawSegment(Joint from, Joint to, char[] canvas) {
		int fromX = from.getX() * 3;
		int fromY = from.getY() * 3;
		int toX = to.getX() * 3;
		int toY = to.getY() * 3;
		if(fromX == toX) {
			// vertical segment
			int start = Math.min(fromY, toY);
			int end = Math.max(fromY, toY);
			for(int y=start;y<=end;++y) {
				draw(fromX+1,y+1,"*",canvas);
			}
		} else if(fromY == toY) {
			// horizontal segment
			int start = Math.min(fromX, toX);
			int end = Math.max(fromX, toX);
			for(int x=start;x<=end;++x) {
				draw(x+1,fromY+1,"*",canvas);
			}
		} else {
			throw new IllegalArgumentException("cannot draw segment: " + from + "--" + to);
		}
	}	
	
	/**
	 * Draw a Pin onto the canvas.
	 * 
	 * @param id
	 * @param pin
	 * @param canvas
	 */
	private void drawPin(int id, Pin pin, char[] canvas) {
		String[] digits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"A", "B", "C", "D", "E", "F" };
		int topLeftX = pin.getX() * 3;
		int topLeftY = pin.getY() * 3;
		draw(topLeftX, topLeftY, "+-+", canvas);
		draw(topLeftX, topLeftY+1, "| |", canvas);
		draw(topLeftX, topLeftY+2, "+-+", canvas);
		draw(topLeftX+1, topLeftY+1, digits[id], canvas);
	}
	
	private void draw(int x, int y, String s, char[] canvas) {
		int canvasWidth = width*3;
		for(int i=0;i!=s.length();++i,++x) {
			canvas[(y*canvasWidth)+x] = s.charAt(i);
		}
	}
	
	/**
	 * Convert a given number into its 4-bit binary representation, where the
	 * least-significant bit has the lowest index in the array.
	 * 
	 * @param number
	 * @return
	 */
	public static boolean[] decode(int number) {		
		return decodeTable[number];
	}
	
	/**
	 * <p>
	 * The following decode table provides the bit arrangements for representing
	 * the numbers 0 .. 7 using boolean arrays. Observe that the least
	 * significant bit is located on the left, since this corresponds to the
	 * lowest index in the array.
	 * </p>
	 * 
	 * SWEN221: You don't really need to understand this works to build a
	 * RomChip!
	 */
	private static final boolean[][] decodeTable = {
		    // 0  |  1  |  2  
			{false,false,false},  // 0
			{true, false,false},  // 1
			{false,true, false},  // 2
			{true, true, false},  // 3
			{false,false,true },  // 4
			{true, false,true },  // 5
			{false,true, true },  // 6
			{true, true, true }   // 7
	};
	
	/**
	 * Convert an n-bit binary array into the corresponding number.
	 * 
	 * SWEN221: You don't really need to understand how this works to build a
	 * RomChip!
	 * 
	 * @param number
	 * @return
	 */
	public static int encode(boolean[] bits) {		
		int mask = 1;
		int value = 0;
		for(int i=0;i!=bits.length;++i) {
			if(bits[i]) {
				value = value | mask;
			}
			mask = mask << 1;
		}
		return value;
	}
}
