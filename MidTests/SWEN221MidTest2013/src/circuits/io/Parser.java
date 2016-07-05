package circuits.io;

import java.util.*;

import circuits.CircuitBoard;
import circuits.components.*;
import circuits.gates.*;
import circuits.wiring.*;

public class Parser {
	private String text; // text to be processed
	private int index;  // position into text
	
	public Parser(String text) {
		this.text = text;
		this.index = 0;
	}

	public CircuitBoard parse() throws SyntaxError {
		// First, get the width and height of the board
		int width = parseNumber();
		

		match(','); 
		
		int height = parseNumber();

		// Second, parse all the pins
		int numberOfPins = parseNumber();
		
		Pin[] pins = new Pin[numberOfPins];
		for(int i=0;i!=numberOfPins;++i) {
			pins[i] = parsePin();
		}
		
		// Third, parse all the wires
		int numberOfWires = parseNumber();

		Wire[] wires = new Wire[numberOfWires];
		for(int i=0;i!=numberOfWires;++i) {
			wires[i] = parseWire(pins);
		}
		
		// Fourth, parse all the components
		int numberOfComponents = parseNumber();

		Component[] components = new Component[numberOfComponents];
		for(int i=0;i!=numberOfComponents;++i) {
			components[i] = parseComponent(pins);
		}
		
		CircuitBoard board = new CircuitBoard(width,height,pins,wires,components);
		
		checkValidBoard(board);
		
		return board;
	}
	
	/**
	 * Checks that a circuit board is valid with respect to the given
	 * requirements.
	 * 
	 * @param board
	 */
	public void checkValidBoard(CircuitBoard board) throws SyntaxError {
	    
	    // check (1) every pin is within the dimensions of the circuit board
        for (int i = 0; i != board.getNumberOfPins(); i++) {
            Pin pin = board.getPin(i);
            int x = pin.getX();
            int y = pin.getY();
            if (x < 0 || x >= board.getWidth() || y < 0 || y >= board.getHeight()) {
                throw new SyntaxError("pin is not within the dimensionsof the board");
            }
        }
	    
        // check (2) that no two pins occupy the same position on the circuit board     
        for (int i = 0; i != board.getNumberOfPins(); i++) {
            Pin pinI = board.getPin(i);
            for (int j = i+1; j != board.getNumberOfPins(); j++) {
                Pin pinJ = board.getPin(j);
                if (pinI.getX() == pinJ.getX() && pinI.getY() == pinJ.getY()) {
                    throw new SyntaxError("two pins occupy the same position");
                }
            }
        }
        
        // check (3) that no pin is used more than once by any component
        for (int i = 0; i != board.getNumberOfComponents(); i++) {
            Component component = board.getComponent(i);
            for (int j = 0; j != component.getNumberOfPins(); j++) {
                Pin pinJ = component.getPin(j);
                for (int k = j+1; k != component.getNumberOfPins(); k++) {
                    Pin pinK = component.getPin(k);
                    if (pinJ.equals(pinK)) {
                        throw new SyntaxError("pin is used more than once by a component");
                    }
                }
            }
        }
        
        // check (4) no pin is used by more than one component
        for(int i=0;i<board.getNumberOfComponents();++i) {
            Component c1 = board.getComponent(i);
            for(int j=i+1;j<board.getNumberOfComponents();++j) {
                Component c2 = board.getComponent(j);
                for(int x=0;x<c1.getNumberOfPins();++x) {
                    Pin p1 = c1.getPin(x);
                    for(int y=0;y<c2.getNumberOfPins();++y) {
                        Pin p2 = c2.getPin(y);
                        if(p1 == p2) {
                            throw new SyntaxError("same pin used in different components!");
                        }
                    }
                }
            }
        }
        
        // Check (5)  The input pin for a wire may not be the input pin of a component.
        // Check (6)  The output pin for a wire may not be the output pin of a component.
        for (int i=0; i<board.getNumberOfWires();++i) {
            Wire wire = board.getWire(i);
            Pin inputW = wire.getInputPin();
            Pin outputW = wire.getOutputPin();
            for(int j=0;j<board.getNumberOfComponents();++j) {
                Component component = board.getComponent(i);
                for (int k=0;k<component.getNumberOfPins();++k) {
                    Pin pinC = component.getPin(k);
                    if (component.isInputPin(k) && inputW.equals(pinC)) {
                        throw new SyntaxError("The input pin for a wire is the input pin of a component");
                    }
                    if (component.isOutputPin(k) && outputW.equals(pinC)) {
                        throw new SyntaxError("The output pin for a wire is the output pin of a component");
                    }
                }
            }
        }
	}
		
	public Pin parsePin() throws SyntaxError {

		match('('); 
		
		int x = parseNumber();
		match(',');
		int y = parseNumber();
		match(':');
		skipWhiteSpace();
		boolean value;
		char lookahead = text.charAt(index++);
		if(lookahead == 'T') {
			value = true;
		} else if(lookahead == 'F') {			 
			value = false;
		} else {
			syntaxError("invalid pin value: " + lookahead);
			return null;
		}
		match(')');
		return new Pin(x,y,value);
	}
	
	public Wire parseWire(Pin[] pins) throws SyntaxError {
		// First, we parse the pin identifiers for both the input and output
		// pins.
		int inputPin = parseNumber();
		match('-');
		match('>');
		int outputPin = parseNumber();
		
		// Second, parse the joints that this wire contains
		match('[');
		ArrayList<Joint> joints = new ArrayList<Joint>();
		while(index < text.length() && text.charAt(index) != ']') {
			int x = parseNumber();
			match(',');
			int y = parseNumber();
			joints.add(new Joint(x,y));
			skipWhiteSpace();
		}
		match(']');
		
		// Second, check these pin identifiers are valid.
		if(inputPin >= pins.length) {
			syntaxError("invalid pin identifier: " + inputPin);
		}
		if(outputPin >= pins.length) {
			syntaxError("invalid pin identifier: " + outputPin);
		}
		// Third, we get the pins that these identifiers correspond to.
		Pin input = pins[inputPin];
		Pin output = pins[outputPin];
		
		// Finally, construct the wire
		return new Wire(input, output, joints.toArray(new Joint[joints.size()]));
	}
	
	public int[] parsePinId() throws SyntaxError {		
		int component = parseNumber();
		match('[');
		int pin = parseNumber();
		match(']');
		return new int[]{component,pin};
	}
	
	public Component parseComponent(Pin[] pins) throws SyntaxError {
		skipWhiteSpace();
		
		char componentCharacter = text.charAt(index++);
		
		switch(componentCharacter) {
		case 'G':
			return parseLogicGate(pins);
		case 'I':
			return parseLogicChip(pins);
		case 'R':
			return parseRomChip(pins);
		}
		
		// if we get here, then an error has occurred.
		syntaxError("invalid component character: " + componentCharacter);		
		return null;
	}	
	
	public LogicGate parseLogicGate(Pin[] pins) throws SyntaxError {
		match('(');
		Gate gate;
		char gateChar = text.charAt(index++);
		switch(gateChar) {
		case 'A':
			gate = new AndGate();
			break;
		case 'O':
			gate = new OrGate();
			break;
		case 'X':
			gate = new XorGate();
			break;
		default:
			// if we get here, then an error has occurred.
			syntaxError("invalid gate character: " + gateChar);		
			return null;
		}
		match(':');
		Pin[] legs = new Pin[3];
		for(int i=0;i!=legs.length;++i) {
			if(i!=0) {
				match(',');
			}
			int legPin = parseNumber();
			if(legPin >= pins.length) {
				syntaxError("invalid pin identifier: " + legPin);
			}
			legs[i] = pins[legPin];
		}
		match(')');
		
		return new LogicGate(gate,legs[0],legs[1],legs[2]);
	}
	
	public Component parseLogicChip(Pin[] pins) throws SyntaxError {
		match('(');
		int numberOfGates = parseNumber();
		
		if (numberOfGates % 2 != 0) {
		    throw new SyntaxError("The number of Gates is not right");
		}
		
		LogicGate[] gates = new LogicGate[numberOfGates];
		match(':');
		for(int i=0;i!=gates.length;++i) {
			if(i!=0) {
				match(',');
			}
			gates[i] = parseLogicGate(pins);
		}
		match(')');	

		// Uncomment the following line when you get to Part 7 (Logic
		// Chips II). Note, at this point, gate[0] corresponds to Gate 0,
		// gate[1] to Gate 1, etc.
		
		return new LogicChip(gates);
	}
	
	public Component parseRomChip(Pin[] pins) throws SyntaxError {
		match('(');
		Pin[] legs = new Pin[6];
		for(int i=0;i!=6;++i) {
			if(i!=0) {
				match(',');
			}
			int legPin = parseNumber();
			if(legPin >= pins.length) {
				syntaxError("invalid pin identifier: " + legPin);
			}
			legs[i] = pins[legPin];
		}
		match(':');
		// the memory of a ROM chip is 8 x 3bit words.
		boolean[][] memory = new boolean[8][];
		for(int i=0;i<8;++i) {
			if(i!=0) {
				match(',');
			}
			skipWhiteSpace();
			int value = text.charAt(index++) - '0';
			memory[i] = CircuitBoard.decode(value);
		}
		match(')');
		
		return new RomChip(pins,memory);

	}
	
	
	/**
	 * This method parses an integer from a string consisting of one or more
	 * digits.
	 * 
	 * @return
	 */
	private int parseNumber() throws SyntaxError {
		skipWhiteSpace();
		// first, determine all digits which make up the number
		int start = index;
		while (index < text.length() && Character.isDigit(text.charAt(index))) {
			index = index + 1;
		}

		// second, check that we found at least one digit
		if (index == start) {
			syntaxError("expecting a number");
		}

		// finally, convert the string into an actual integer.
		return Integer.parseInt(text.substring(start, index));
	}

	/**
	 * This method checks whether the given character is at the current position
	 * in the text. The method will first skip any whitespace at this point.
	 * 
	 * @param text
	 */
	private void match(char c) throws SyntaxError {
		skipWhiteSpace();
		if (index < text.length() && text.charAt(index) == c) {
			index = index + 1;
		} else if (index < text.length()) {
			syntaxError("expecting '" + c + "', found '" + text.charAt(index)
					+ "'");
		} else {
			syntaxError("unexpected end-of-file");
		}
	}
	
	/**
	 * Move the current position past any "white space" characters. White space
	 * characters include spaces (i.e. ' '), tabs (i.e. '\t') and newlines (i.e.
	 * '\n').
	 */
	private void skipWhiteSpace() {
		while (index < text.length() && isWhitespace(text.charAt(index))) {
			index = index + 1;
		}
	}

	/**
	 * Check whether a given character is whitespace or not.
	 * 
	 * @param c
	 *            --- the character to test.
	 * @return
	 */
	private boolean isWhitespace(char c) {
		return c == ' ' || c == '\t' || c == '\n';
	}

	/**
	 * Print out useful debugging output, and throw a SyntaxError exception
	 * 
	 * @param msg
	 */
	private void syntaxError(String msg) throws SyntaxError {
		ArrayList<String> lines = new ArrayList<String>();
		int i = 0;
		int start = 0;
		int linenum = 0;
		int col = 0;
		while(i < text.length()) {			
			if(text.charAt(i) == '\n') {
				String line = text.substring(start,i);
				lines.add(line);
				start = i+1;
			}
			if(i == index) { 
				linenum = lines.size();
				col = i - start;
			}
			i = i + 1;
		}
		// finally, process last line
		String line = text.substring(start,i);
		lines.add(line);
		System.err.println(msg);
		System.err.println(lines.get(linenum));					
		for(i=0;i<col;++i) {
			System.err.print(" ");
		}
		System.err.println("^\n");
		throw new SyntaxError(msg);
	}
}
