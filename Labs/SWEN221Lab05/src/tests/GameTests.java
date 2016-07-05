package tests;

import org.junit.*;

import static org.junit.Assert.fail;
import minesweeper.*;

public class GameTests {

	/**
	 * ============================================================
	 * Part 1 --- Parsing
	 * ============================================================
	 */
	
	@Test
	public void validFile_1() {
		String dimensions = "3,3\n";
		String bombs = "1 (1,1)\n";
		String moves = "0\n";		
		String input = dimensions + bombs + moves; 
		checkValid(input,1);
	}
	
	@Test
	public void validFile_2() {
		String dimensions = "3,3\n";
		String bombs = "1 (1,1)\n";
		String moves = "1 E(0,0)\n";		
		String input = dimensions + bombs + moves; 
		checkValid(input,2);
	}
	
	@Test
	public void validFile_3() {
		String dimensions = "3,3\n";
		String bombs = "1 (1,1)\n";
		String moves = "1 F(0,0)\n";		
		String input = dimensions + bombs + moves; 
		checkValid(input,3);
	}
	
	@Test
	public void validFile_4() {
		String dimensions = "3,3\n";
		String bombs = "1 (1,1)\n";
		String moves = "2 F(0,0) U(0,0)\n";		
		String input = dimensions + bombs + moves; 
		checkValid(input,4);
	}
	
	@Test
	public void validFile_5() {
		String dimensions = "3,3\n";
		String bombs = "1 (1,1)\n";
		String moves = "1 L(1,1)\n";		
		String input = dimensions + bombs + moves; 
		checkValid(input,5);
	}
	
	/**
	 * ============================================================
	 * Part 2 --- Positions
	 * ============================================================
	 */
	
	@Test
	public void invalidFile_6() {
		// fails because bomb has invalid position
		String dimensions = "3,3\n";
		String bombs = "1 (3,3)\n";
		String moves = "0\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,6);
	}
	
	@Test
	public void invalidFile_7() {
		// fails because bomb has invalid position
		String dimensions = "3,3\n";
		String bombs = "1 (3,2)\n";
		String moves = "0\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,7);
	}
	
	@Test
	public void invalidFile_8() {
		// fails because bomb has invalid position
		String dimensions = "3,3\n";
		String bombs = "1 (2,3)\n";
		String moves = "0\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,8);
	}
	
	@Test
	public void invalidFile_9() {
		// fails because move has invalid position
		String dimensions = "3,3\n";
		String bombs = "1 (1,1)\n";
		String moves = "1 E(3,3)\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,9);
	}
	
	@Test
	public void invalidFile_10() {
		// fails because move has invalid position
		String dimensions = "3,3\n";
		String bombs = "1 (1,1)\n";
		String moves = "2 E(0,0) F(3,3)\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,10);
	}
	
	@Test
	public void invalidFile_11() {
		// fails because move has invalid position
		String dimensions = "3,3\n";
		String bombs = "1 (1,1)\n";
		String moves = "3 E(0,0) F(1,1) L(3,3)\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,11);
	}
	
	/**
	 * ============================================================
	 * Part 3 --- Exposing
	 * ============================================================
	 */

	@Test
	public void invalidFile_12() {
		// fails because exposing exposed square
		String dimensions = "3,3\n";
		String bombs = "1 (1,1)\n";
		String moves = "2 E(0,0) E(0,0)\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,12);
	}
	
	@Test
	public void invalidFile_13() {
		// fails because exposing exposed square
		String dimensions = "3,3\n";
		String bombs = "1 (1,1)\n";
		String moves = "3 E(0,0) E(2,2) E(0,0)\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,13);
	}
	
	@Test
	public void invalidFile_14() {
		// fails because exposing flagged square
		String dimensions = "3,3\n";
		String bombs = "1 (1,1)\n";
		String moves = "2 F(0,0) E(0,0)\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,14);
	}
	
	@Test
	public void invalidFile_15() {
		// fails because exposing flagged square
		String dimensions = "3,3\n";
		String bombs = "1 (1,1)\n";
		String moves = "5 F(0,0) F(1,1) F(2,2) E(2,0) E(2,2)\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,15);
	}
	
	/**
	 * ============================================================
	 * Part 4 --- Flagging
	 * ============================================================
	 */
	
	@Test
	public void invalidFile_16() {
		// fails because flagging flagged square
		String dimensions = "3,3\n";
		String bombs = "1 (1,1)\n";
		String moves = "2 F(0,0) F(0,0)\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,16);
	}
	
	@Test
	public void invalidFile_17() {
		// fails because flagging flagged square
		String dimensions = "3,3\n";
		String bombs = "1 (1,1)\n";
		String moves = "3 F(1,1) F(0,0) F(1,1)\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,17);
	}
	
	@Test
	public void invalidFile_18() {
		// fails because unflagging unflagged square
		String dimensions = "3,3\n";
		String bombs = "1 (1,1)\n";
		String moves = "1 U(0,0)\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,18);
	}
	
	@Test
	public void invalidFile_19() {
		// fails because unflagging unflagged square
		String dimensions = "3,3\n";
		String bombs = "1 (1,1)\n";
		String moves = "2 F(1,1) U(0,0)\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,19);
	}
	
	@Test
	public void invalidFile_20() {
		// fails because exposing flagged square
		String dimensions = "3,3\n";
		String bombs = "1 (1,1)\n";
		String moves = "2 F(0,0) E(0,0)\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,20);
	}
	
	/**
	 * ============================================================
	 * Part 5 --- Winning & Losing
	 * ============================================================
	 */
	
	@Test
	public void invalidFile_21() {
		// fails because no bomb at (0,0)
		String dimensions = "3,3\n";
		String bombs = "3 (1,1) (2,2) (0,2)\n";
		String moves = "1 L(0,0)\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,21);
	}
	
	@Test
	public void invalidFile_22() {
		// fails because bomb at exposed square
		String dimensions = "3,3\n";
		String bombs = "1 (1,1)\n";
		String moves = "1 W(1,1)\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,22);
	}
	
	@Test
	public void invalidFile_23() {
		// fails because haven't exposed all squares
		String dimensions = "3,1\n";
		String bombs = "1 (1,0)\n";
		String moves = "2 F(1,0) W(0,0)\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,23);
	}
	
	@Test
	public void invalidFile_24() {
		// fails because we should have won
		String dimensions = "3,1\n";
		String bombs = "1 (1,0)\n";
		String moves = "2 E(0,0) L(2,0)\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,24);
	}
	
	/**
	 * ============================================================
	 * Part 6 --- Recursive Exposure
	 * ============================================================
	 */
	
	@Test
	public void validFile_25() {
		// horizontal test
		String dimensions = "3,1\n";
		String bombs = "1 (2,0)\n";
		String moves = "1 W(0,0)\n";		
		String input = dimensions + bombs + moves; 
		checkValid(input,25);
	}
	
	@Test
	public void validFile_26() {
		// vertical test
		String dimensions = "1,3\n";
		String bombs = "1 (0,2)\n";
		String moves = "1 W(0,0)\n";		
		String input = dimensions + bombs + moves; 
		checkValid(input,26);
	}
	
	@Test
	public void validFile_27() {
		// combined test
		String dimensions = "5,5\n";
		String bombs = "2 (1,1) (2,2)\n";
		String moves = "4 E(0,0) E(1,0) E(0,1) W(4,4)\n";		
		String input = dimensions + bombs + moves; 
		checkValid(input,27);
	}
	
	@Test
	public void validFile_28() {
		// combined test, multiple bombs
		String dimensions = "4,4\n";
		String bombs = "4 (1,1) (2,2) (0,1) (0,3)\n";
		String moves = "13 E(0,0) E(1,0) F(0,1) F(1,1) E(3,0) E(1,2) F(0,3) F(2,2) E(1,3) E(2,3) E(3,3) E(3,2) W(0,2)\n";		
		String input = dimensions + bombs + moves; 
		checkValid(input,28);
	}	
	
	@Test
	public void invalidFile_29() {
		// fails because exposing exposed square
		String dimensions = "3,3\n";
		String bombs = "1 (2,2)\n";
		String moves = "2 E(0,0) E(1,0)\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,29);
	}
	
	@Test
	public void invalidFile_30() {
		// fails because flagged squares not recursively exposed
		String dimensions = "3,1\n";
		String bombs = "1 (2,0)\n";
		String moves = "2 F(1,0) W(0,0)\n";		
		String input = dimensions + bombs + moves; 
		checkInvalid(input,30);
	}
	
	/**
	 * ============================================================
	 * Helper Functions
	 * ============================================================
	 */
	
	/**
	 * This method checks whether or not the given input string is valid.
	 * 
	 * @param input
	 */
	public void checkValid(String input, int testNumber) {
		Game g;

		try {
			g = new Parser(input).parse();
		} catch (SyntaxError e) {
			System.out.println("===================================================");
			System.out.println("TEST: " + testNumber);
			System.out.println("===================================================");
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}

		try {
			g.validate();
		} catch (SyntaxError e) {
			System.out.println("===================================================");
			System.out.println("TEST: " + testNumber);
			System.out.println("===================================================");
			System.out.println(e.getMessage());
			System.out.println("\nCurrent Board:\n\n" + g.toString());
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * This method checks that given input string is invalid.
	 * 
	 * @param input
	 */
	public void checkInvalid(String input, int testNumber) {
		try {
			Game g = new Parser(input).parse();
			g.validate();
			System.out.println("===================================================");
			System.out.println("TEST: " + testNumber);
			System.out.println("===================================================");
			System.out.println("Input should be invalid:\n" + input);
			System.out.println("\nFinal Board:\n\n" + g.toString());
			fail("Input should be invalid:\n" + input);
		} catch(SyntaxError e) {			
			// pass			
		}
	}
}
