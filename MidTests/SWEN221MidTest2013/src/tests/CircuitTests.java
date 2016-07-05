package tests;

import org.junit.*;
import static org.junit.Assert.fail;

import circuits.*;
import circuits.io.Parser;
import circuits.io.SyntaxError;


public class CircuitTests {

	/**
	 * ============================================================
	 * Part 1 --- Parsing Wires and Pins
	 * ============================================================
	 */
	
	@Test
	public void validFile_1() {
		String dimension = "3,3\n";
		String pins = "2 (0,0:T) (2,0:F)\n";
		String wires = "1 0->1[]\n";
		String components = "0\n";
		String input = dimension + pins + wires + components; 
		checkValid(input, 1);
	}
	
	@Test
	public void validFile_2() {
		String dimension = "3,3\n";
		String pins = "2 (0,0:T) (2,2:F)\n";
		String wires = "1 0->1[0,2]\n";
		String components = "0\n";
		String input = dimension + pins + wires + components; 
		checkValid(input, 2);
	}
	
	@Test
	public void validFile_3() {
		String dimension = "3,3\n";
		String pins = "2 (0,0:T) (2,0:F)\n";
		String wires = "1 0->1[0,2 2,2]\n";
		String components = "0\n";
		String input = dimension + pins + wires + components; 
		checkValid(input, 3);
	}
	
	@Test
	public void validFile_4() {
		String dimension = "3,3\n";
		String pins = "4 (0,0:T) (2,0:F) (0,2:T) (2,2:F)\n";
		String wires = "2 0->1[] 2->3[]\n";
		String components = "0\n";
		String input = dimension + pins + wires + components; 
		checkValid(input, 4);
	}
	
	@Test
	public void validFile_5() {
		String dimension = "3,3\n";
		String pins = "2 (0,0:T) (1,0:F)\n";
		String wires = "1 0->1[0,2 2,2 2,0]\n";
		String components = "0\n";
		String input = dimension + pins + wires + components; 
		checkValid(input, 5);
	}
	
	/**
	 * ============================================================
	 * Part 2 --- Logic Gates
	 * ============================================================
	 */

	@Test
	public void validFile_6() {
		// tests AND gate
		String dimension = "3,3\n";
		String pins = "3 (0,0:T) (2,0:T) (0,2:F)\n";
		String wires = "0\n";
		String components = "1 G(A:0,1,2)\n";
		String input = dimension + pins + wires + components;
		boolean[] expectedPinOuts = {true,true,true};
		checkValidAndRun(input, expectedPinOuts, 6);
	}
	
	@Test
	public void validFile_7() {
		// tests AND gate
		String dimension = "3,3\n";
		String pins = "3 (0,0:T) (2,0:F) (0,2:F)\n";
		String wires = "0\n";
		String components = "1 G(A:0,1,2)\n";
		String input = dimension + pins + wires + components;
		boolean[] expectedPinOuts = {true,false,false};
		checkValidAndRun(input, expectedPinOuts, 7);
	}
	
	@Test
	public void validFile_8() {
		// tests OR gate
		String dimension = "3,3\n";
		String pins = "3 (0,0:T) (2,0:F) (0,2:F)\n";
		String wires = "0\n";
		String components = "1 G(O:0,1,2)\n";
		String input = dimension + pins + wires + components;
		boolean[] expectedPinOuts = {true,false,true};
		checkValidAndRun(input, expectedPinOuts, 8);
	}
	
	@Test
	public void validFile_9() {
		// tests OR gate
		String dimension = "3,3\n";
		String pins = "3 (0,0:F) (2,0:F) (0,2:F)\n";
		String wires = "0\n";
		String components = "1 G(O:0,1,2)\n";
		String input = dimension + pins + wires + components;
		boolean[] expectedPinOuts = {false,false,false};
		checkValidAndRun(input, expectedPinOuts, 9);
	}
	
	@Test
	public void validFile_10() {
		// tests OR gate
		String dimension = "3,3\n";
		String pins = "3 (0,0:T) (2,0:T) (0,2:F)\n";
		String wires = "0\n";
		String components = "1 G(O:0,1,2)\n";
		String input = dimension + pins + wires + components;
		boolean[] expectedPinOuts = {true,true,true};
		checkValidAndRun(input, expectedPinOuts, 10);
	}
	
	@Test
	public void validFile_11() {
		// tests XOR gate
		String dimension = "3,3\n";
		String pins = "3 (0,0:T) (2,0:T) (0,2:F)\n";
		String wires = "0\n";
		String components = "1 G(X:0,1,2)\n";
		String input = dimension + pins + wires + components;
		boolean[] expectedPinOuts = {true,true,false};
		checkValidAndRun(input, expectedPinOuts, 11);
	}
	
	@Test
	public void validFile_12() {
		// tests XOR gate
		String dimension = "3,3\n";
		String pins = "3 (0,0:T) (2,0:F) (0,2:F)\n";
		String wires = "0\n";
		String components = "1 G(X:0,1,2)\n";
		String input = dimension + pins + wires + components;
		boolean[] expectedPinOuts = {true,false,true};
		checkValidAndRun(input, expectedPinOuts, 12);
	}
	
	/**
	 * ============================================================
	 * Part 3 --- Pin Positions
	 * ============================================================
	 */
	
	@Test
	public void invalidFile_13() {
		// fails because pin placed outside dimensions of board
		String dimension = "3,3\n";
		String pins = "1 (4,4:T)\n";
		String wires = "0\n";
		String components = "0\n";
		String input = dimension + pins + wires + components;		
		checkInvalid(input, 13);
	}
	
	@Test
	public void invalidFile_14() {
		// fails because pin placed outside dimensions of board
		String dimension = "3,3\n";
		String pins = "2 (2,2:T) (4,4:T)\n";
		String wires = "0\n";
		String components = "0\n";
		String input = dimension + pins + wires + components;		
		checkInvalid(input, 14);
	}
	
	@Test
	public void invalidFile_15() {
		// fails because two pins have same position on board
		String dimension = "1,1\n";
		String pins = "2 (0,0:T) (0,0:T)\n";
		String wires = "0\n";
		String components = "0\n";
		String input = dimension + pins + wires + components;		
		checkInvalid(input, 15);
	}
	
	@Test
	public void invalidFile_16() {
		// fails because two pins have same position on board
		String dimension = "3,3\n";
		String pins = "3 (2,2:T) (0,0:T) (2,2:T)\n";
		String wires = "0\n";
		String components = "0\n";
		String input = dimension + pins + wires + components;		
		checkInvalid(input, 16);
	}

	/**
	 * ============================================================
	 * Part 4 --- Pin Usage
	 * ============================================================
	 */
	
	@Test
	public void invalidFile_17() {
		// fails because one pin used twice by same component
		String dimension = "3,3\n";
		String pins = "2 (2,2:T) (0,0:T)\n";
		String wires = "0\n";
		String components = "1 G(A:0,1,1)\n";
		String input = dimension + pins + wires + components;		
		checkInvalid(input, 17);
	}
	
	@Test
	public void invalidFile_18() {
		// fails because one pin used twice by same component
		String dimension = "3,3\n";
		String pins = "3 (0,0:T) (2,2:T) (2,0:T)\n";
		String wires = "0\n";
		String components = "1 G(A:0,0,2)\n";
		String input = dimension + pins + wires + components;		
		checkInvalid(input, 18);
	}
	
	@Test
	public void invalidFile_19() {
		// fails because pin used by two components
		String dimension = "5,3\n";
		String pins = "5 (0,0:T) (2,0:T) (0,2:T) (2,2:T) (4,0:T)\n";
		String wires = "0\n";
		String components = "2 G(A:0,1,2) G(A:3,4,4)\n";
		String input = dimension + pins + wires + components;		
		checkInvalid(input, 19);
	}
	
	@Test
	public void invalidFile_20() {
		// fails because pin used by two components
		String dimension = "5,3\n";
		String pins = "5 (0,0:T) (2,0:T) (0,2:T) (2,2:T) (4,0:T)\n";
		String wires = "0\n";
		String components = "2 G(A:0,1,2) G(A:3,3,4)\n";
		String input = dimension + pins + wires + components;		
		checkInvalid(input, 20);
	}
	
	/**
	 * ============================================================
	 * Part 5 --- Valid Wiring
	 * ============================================================
	 */
	
	@Test
	public void invalidFile_21() {
		// fails because output of wire is output of AND gate
		String dimension = "3,3\n";
		String pins = "4 (0,0:T) (2,0:T) (0,2:F) (2,2:F)\n";
		String wires = "1  3->2[0,2 2,2]\n";
		String components = "1 G(A:0,1,2)\n";
		String input = dimension + pins + wires + components;
		checkInvalid(input, 21);	
	}
	
	@Test
	public void invalidFile_22() {
		// fails because input of wire is input of AND gate
		String dimension = "3,3\n";
		String pins = "4 (0,0:T) (2,0:T) (0,2:F) (2,2:F)\n";
		String wires = "1  2->3[0,2 2,2]\n";
		String components = "1 G(A:2,1,0)\n";
		String input = dimension + pins + wires + components;
		checkInvalid(input, 22);	
	}
	
	/**
	 * ============================================================
	 * Part 6 --- Logic Chips I
	 * ============================================================
	 */	
	
	@Test
	public void invalidFile_23() {
		// fails because number of gates not divisible by two.
		String dimension = "3,3\n";
		String pins = "3 (0,0:T) (2,0:T) (2,2:T)\n";
		String wires = "0\n";
		String components = "1 I(1:(A:0,1,2))\n";
		String input = dimension + pins + wires + components;		
		checkInvalid(input, 23);
	}	
	
	@Test
	public void invalidFile_24() {
		// fails because number of gates not divisible by two.
		String dimension = "7,3\n";
		String pins = "9 (0,0:T) (2,0:T) (4,0:T) (6,0:T) (0,2:T) (2,2:T) (4,2:T) (6,2:T) (2,1:T)\n";
		String wires = "0\n";
		String components = "1 I(3:(A:0,1,2),(A:3,4,5),(A:6,7,8))\n";
		String input = dimension + pins + wires + components;		
		checkInvalid(input, 24);
	}	
	
	/**
	 * ============================================================
	 * Part 7 --- Logic Chips II
	 * ============================================================
	 */	
		
	@Test
	public void invalidFile_25() {
		// fails because one pin used twice by same logic chip
		String dimension = "5,3\n";
		String pins = "6 (0,0:T) (2,0:T) (4,0:T) (0,2:T) (2,2:T) (4,2:T)\n";
		String wires = "0\n";
		String components = "1 I(2:(A:0,1,0),(A:3,4,5))\n";
		String input = dimension + pins + wires + components;		
		checkInvalid(input, 25);
	}	
	
	@Test
	public void invalidFile_26() {
		// fails because one pin used twice by same logic chip
		String dimension = "5,3\n";
		String pins = "6 (0,0:T) (2,0:T) (4,0:T) (0,2:T) (2,2:T) (4,2:T)\n";
		String wires = "0\n";
		String components = "1 I(2:(A:0,1,2),(A:2,3,0))\n";
		String input = dimension + pins + wires + components;		
		checkInvalid(input, 26);
	}
	
	@Test
	public void invalidFile_27() {
		// fails because one pin used by two logic chips
		String dimension = "5,3\n";
		String pins = "6 (0,0:T) (2,0:T) (4,0:T) (0,2:T) (2,2:T) (4,2:T)\n";
		String wires = "0\n";
		String components = "2 I(2:(A:0,1,2),(A:3,4,5)) I(2:(A:0,1,2),(A:3,4,5))\n";
		String input = dimension + pins + wires + components;		
		checkInvalid(input, 27);
	}
	

	@Test
	public void invalidFile_28() {
		// fails because input of wire is input of logic chip
		String dimension = "5,3\n";
		String pins = "6 (0,0:T) (2,0:F) (4,0:F) (0,2:T) (2,2:F) (4,2:F)\n";
		String wires = "1  1->0[2,1 0,1]\n";
		String components = "1 I(2:(A:0,1,2),(O:3,4,5))\n";
		String input = dimension + pins + wires + components;
		checkInvalid(input, 28);
	}
	
	@Test
	public void invalidFile_29() {
		// fails because output of wire is output of logic chip
		String dimension = "6,3\n";
		String pins = "6 (0,0:T) (2,0:F) (4,0:F) (0,2:T) (2,2:F) (4,2:F)\n";
		String wires = "1  2->5[5,0 5,2]\n";
		String components = "1 I(2:(A:0,1,2),(O:3,4,5))\n";
		String input = dimension + pins + wires + components;
		checkInvalid(input, 29);
	}
	
	/**
	 * ============================================================
	 * Part 8 --- Logic Chips III
	 * ============================================================
	 */	
		
	@Test
	public void validFile_30() {
		// tests chip with two AND gates
		String dimension = "5,3\n";
		String pins = "6 (0,0:T) (2,0:T) (4,0:F) (0,2:T) (2,2:T) (4,2:F)\n";
		String wires = "0\n";
		String components = "1 I(2:(A:0,1,2),(A:3,4,5))\n";
		String input = dimension + pins + wires + components;
		boolean[] expectedPinOuts = {true,true,true,true,true,true};
		checkValidAndRun(input, expectedPinOuts, 30);
	}
	
	@Test
	public void validFile_31() {
		// tests chip with two AND gates
		String dimension = "5,3\n";
		String pins = "6 (0,0:T) (2,0:F) (4,0:T) (0,2:T) (2,2:T) (4,2:T)\n";
		String wires = "0\n";
		String components = "1 I(2:(A:0,1,2),(A:3,4,5))\n";
		String input = dimension + pins + wires + components;
		boolean[] expectedPinOuts = {true,false,false,true,true,true};
		checkValidAndRun(input, expectedPinOuts, 31);
	}
	
	@Test
	public void validFile_32() {
		// tests chip with AND and OR gates
		String dimension = "5,3\n";
		String pins = "6 (0,0:F) (2,0:F) (4,0:T) (0,2:T) (2,2:F) (4,2:T)\n";
		String wires = "0\n";
		String components = "1 I(2:(A:0,1,2),(O:3,4,5))\n";
		String input = dimension + pins + wires + components;
		boolean[] expectedPinOuts = {false,false,false,true,false,true};
		checkValidAndRun(input, expectedPinOuts, 32);
	}
	
	@Test
	public void validFile_33() {
		// tests chip with AND and OR gates
		String dimension = "5,3\n";
		String pins = "6 (0,0:T) (2,0:F) (4,0:F) (0,2:T) (2,2:F) (4,2:F)\n";
		String wires = "0\n";
		String components = "1 I(2:(A:0,1,2),(O:3,4,5))\n";
		String input = dimension + pins + wires + components;
		boolean[] expectedPinOuts = {true,false,false,true,false,true};
		checkValidAndRun(input, expectedPinOuts, 33);
	}
		
	/**
	 * ============================================================
	 * Part 9 --- ROM Chips
	 * ============================================================
	 */
	
	@Test
	public void validFile_34() {
		// tests ROM chip with input 101 = 5
		String dimension = "5,3\n";		
		String pins = "6 (0,0:T) (2,0:F) (4,0:T) (0,2:F) (2,2:F) (4,2:F)\n";
		String wires = "0\n";
		String components = "1 R(0,1,2,3,4,5:0,0,0,0, 0,7,0,0)\n";
		String input = dimension + pins + wires + components;
		boolean[] expectedPinOuts = {true,false,true, true,true,true};
		checkValidAndRun(input, expectedPinOuts, 34);
	}
	
	@Test
	public void validFile_35() {
		// tests ROM chip with input 011 = 3
		String dimension = "5,3\n";		
		String pins = "6 (0,0:T) (2,0:T) (4,0:F) (0,2:F) (2,2:F) (4,2:F)\n";
		String wires = "0\n";
		String components = "1 R(0,1,2,3,4,5:0,0,0,6, 0,0,0,0)\n";
		String input = dimension + pins + wires + components;
		boolean[] expectedPinOuts = {true,true,false, false,true,true};
		checkValidAndRun(input, expectedPinOuts, 35);
	}
			
	/**
	 * This method checks whether or not the given input string is valid.
	 * 
	 * ** DO NOT MODIFY THIS FUNCTION **
	 * 
	 * @param input
	 */
	public void checkValid(String input, int testNumber) {
		CircuitBoard circuit;
		
		// first, parse the circuit
		try {			
			circuit = new Parser(input).parse();
			
		} catch (SyntaxError e) {
			System.out.println("===================================================");
			System.out.println("TEST " + testNumber + ": PARSE ERROR");
			System.out.println("===================================================");
			System.out.println(e.getMessage());			
			throw new RuntimeException(e);
		}
	}

	/**
	 * This method checks whether or not the given input string is valid. It
	 * also runs the circuit and checks that the expected outputs match.
	 * 
	 * ** DO NOT MODIFY THIS FUNCTION **
	 * 
	 * @param input
	 */
	public void checkValidAndRun(String input, boolean[] expectedPinOuts, int testNumber) {
		CircuitBoard circuit;
		
		// first, parse the circuit
		try {			
			circuit = new Parser(input).parse();
			
		} catch (SyntaxError e) {
			System.out.println("===================================================");
			System.out.println("TEST " + testNumber + ": PARSE ERROR");
			System.out.println("===================================================");
			System.out.println(e.getMessage());			
			throw new RuntimeException(e);
		}

		// Second, run circuits for 100 cycles, and check expected outputs match actual outputs. 
		try {

			circuit.run(100);			

			for(int i=0;i!=expectedPinOuts.length;++i) {
				boolean expected = expectedPinOuts[i];
				boolean actual = circuit.getPin(i).read();
				if(expected != actual) {
					System.out.println("===================================================");
					System.out.println("TEST " + testNumber + ": INCORRECT OUTPUT");
					System.out.println("===================================================");
					System.out.println(circuit.toString());
					System.out.println("(expected pin #" + i + " = " + expected + ")");
					fail("expected pin outputs do not match actual outputs");
				}
			}	
			
		}catch(Exception e) {
			System.out.println("GOT: " + e.getClass().getName());
			System.out.println("===================================================");
			System.out.println("TEST " + testNumber + ": EXCEPTION THROWN");
			System.out.println("===================================================");
			System.out.println("MESSAGE: " + e.getMessage());
			System.out.println(circuit.toString());
			throw new RuntimeException(e);
		}	
		
	}

	
	/**
	 * This method checks whether or not the given input string is valid.
	 * 
	 * ** DO NOT MODIFY THIS FUNCTION **
	 * 
	 * @param input
	 */
	public void checkInvalid(String input, int testNumber) {
		CircuitBoard circuit;
		
		// first, parse the circuit
		try {			
			circuit = new Parser(input).parse();
			System.out.println("===================================================");
			System.out.println("TEST " + testNumber + ": PARSE ERROR");
			System.out.println("===================================================");
			System.out.println("Circuit file should be invalid!");
			System.out.println(circuit.toString());
			throw new RuntimeException("Circuit file should be invalid!");
		} catch (SyntaxError e) {
			
		}
	}
}
