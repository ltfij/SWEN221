package tests;

import org.junit.*;

import cathedral.Game;
import cathedral.io.Parser;
import cathedral.io.GameError;
import static org.junit.Assert.fail;

public class CathedralTests {

	/**
	 * ============================================================
	 * Part 1 --- Placement of Buildings
	 * ============================================================
	 */
	
	@Test
	public void test_01() {
		String input = "1\n" +
					   "P(5,5;N;C)"; // place cathedral at 5,5 facing north 
		String output = 
				 "9|_|_|_|_|_|_|_|_|_|_|\n"+
				 "8|_|_|_|_|_|_|_|_|_|_|\n"+
				 "7|_|_|_|_|_|C|_|_|_|_|\n"+
				 "6|_|_|_|_|_|C|_|_|_|_|\n"+
				 "5|_|_|_|_|C|C|C|_|_|_|\n"+
				 "4|_|_|_|_|_|C|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|_|_|\n"+
				 "2|_|_|_|_|_|_|_|_|_|_|\n"+
				 "1|_|_|_|_|_|_|_|_|_|_|\n"+
				 "0|_|_|_|_|_|_|_|_|_|_|\n"+						 
				 "  0 1 2 3 4 5 6 7 8 9";				
		checkValid(input,output,1);
	}
	
	@Test
	public void test_02() {
		String input = "2\n" +
						// place cathedral at 5,5 facing north
						// and T-building at 1,2 facing north
					   "P(5,5;N;C) P(1,2;N;T)";  
		String output = 
				 "9|_|_|_|_|_|_|_|_|_|_|\n"+
				 "8|_|_|_|_|_|_|_|_|_|_|\n"+
				 "7|_|_|_|_|_|C|_|_|_|_|\n"+
				 "6|_|_|_|_|_|C|_|_|_|_|\n"+
				 "5|_|_|_|_|C|C|C|_|_|_|\n"+
				 "4|_|_|_|_|_|C|_|_|_|_|\n"+
				 "3|_|B|_|_|_|_|_|_|_|_|\n"+
				 "2|B|B|B|_|_|_|_|_|_|_|\n"+
				 "1|_|_|_|_|_|_|_|_|_|_|\n"+
				 "0|_|_|_|_|_|_|_|_|_|_|\n"+						 
				 "  0 1 2 3 4 5 6 7 8 9";				
		checkValid(input,output,2);
	}
	
	@Test
	public void test_03() {
		String input = "3\n" +
						// place cathedral at 5,5 facing north
						// and T-building at 1,2 facing north
					   "P(5,5;N;C) P(1,2;N;T)" +
						// and L-building at 0,0 facing north
					    "P(1,0;N;S)";
		String output = 
				 "9|_|_|_|_|_|_|_|_|_|_|\n"+
				 "8|_|_|_|_|_|_|_|_|_|_|\n"+
				 "7|_|_|_|_|_|C|_|_|_|_|\n"+
				 "6|_|_|_|_|_|C|_|_|_|_|\n"+
				 "5|_|_|_|_|C|C|C|_|_|_|\n"+
				 "4|_|_|_|_|_|C|_|_|_|_|\n"+
				 "3|_|B|_|_|_|_|_|_|_|_|\n"+
				 "2|B|B|B|_|_|_|_|_|_|_|\n"+
				 "1|_|_|_|_|_|_|_|_|_|_|\n"+
				 "0|W|W|W|W|_|_|_|_|_|_|\n"+						 
				 "  0 1 2 3 4 5 6 7 8 9";				
		checkValid(input,output,3);
	}
	
	/**
	 * ============================================================
	 * Part 2 --- Rotation of Cathedral
	 * ============================================================
	 */
	
	@Test
	public void test_04() {
		String input = "1\n" +
					   "P(5,5;E;C)"; // place cathedral at 5,5 facing east
		String output = 
				 "9|_|_|_|_|_|_|_|_|_|_|\n"+
				 "8|_|_|_|_|_|_|_|_|_|_|\n"+
				 "7|_|_|_|_|_|_|_|_|_|_|\n"+
				 "6|_|_|_|_|_|C|_|_|_|_|\n"+
				 "5|_|_|_|_|C|C|C|C|_|_|\n"+
				 "4|_|_|_|_|_|C|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|_|_|\n"+
				 "2|_|_|_|_|_|_|_|_|_|_|\n"+
				 "1|_|_|_|_|_|_|_|_|_|_|\n"+
				 "0|_|_|_|_|_|_|_|_|_|_|\n"+						 
				 "  0 1 2 3 4 5 6 7 8 9";				
		checkValid(input,output,4);
	}
	
	@Test
	public void test_05() {
		String input = "1\n" +
					   "P(5,5;S;C)"; // place cathedral at 5,5 facing south
		String output = 
				 "9|_|_|_|_|_|_|_|_|_|_|\n"+
				 "8|_|_|_|_|_|_|_|_|_|_|\n"+
				 "7|_|_|_|_|_|_|_|_|_|_|\n"+
				 "6|_|_|_|_|_|C|_|_|_|_|\n"+
				 "5|_|_|_|_|C|C|C|_|_|_|\n"+
				 "4|_|_|_|_|_|C|_|_|_|_|\n"+
				 "3|_|_|_|_|_|C|_|_|_|_|\n"+
				 "2|_|_|_|_|_|_|_|_|_|_|\n"+
				 "1|_|_|_|_|_|_|_|_|_|_|\n"+
				 "0|_|_|_|_|_|_|_|_|_|_|\n"+						 
				 "  0 1 2 3 4 5 6 7 8 9";				
		checkValid(input,output,5);
	}
	
	@Test
	public void test_06() {
		String input = "1\n" +
					   "P(5,5;W;C)"; // place cathedral at 5,5 facing west
		String output = 
				 "9|_|_|_|_|_|_|_|_|_|_|\n"+
				 "8|_|_|_|_|_|_|_|_|_|_|\n"+
				 "7|_|_|_|_|_|_|_|_|_|_|\n"+
				 "6|_|_|_|_|_|C|_|_|_|_|\n"+
				 "5|_|_|_|C|C|C|C|_|_|_|\n"+
				 "4|_|_|_|_|_|C|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|_|_|\n"+
				 "2|_|_|_|_|_|_|_|_|_|_|\n"+
				 "1|_|_|_|_|_|_|_|_|_|_|\n"+
				 "0|_|_|_|_|_|_|_|_|_|_|\n"+						 
				 "  0 1 2 3 4 5 6 7 8 9";				
		checkValid(input,output,6);
	}
	
	/**
	 * ============================================================
	 * Part 3 --- Placement of other buildings
	 * ============================================================
	 */
	
	@Test
	public void test_07() {
		String input = "2\n" +
				// Place cathedral at 5,5 facing North;
				// and Place L-building at 9,9 facing North
				"P(5,5;N;C) P(0,8;N;L)";  
		
		String output = 
				 "9|B|_|_|_|_|_|_|_|_|_|\n"+
				 "8|B|B|_|_|_|_|_|_|_|_|\n"+
				 "7|_|_|_|_|_|C|_|_|_|_|\n"+
				 "6|_|_|_|_|_|C|_|_|_|_|\n"+
				 "5|_|_|_|_|C|C|C|_|_|_|\n"+
				 "4|_|_|_|_|_|C|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|_|_|\n"+
				 "2|_|_|_|_|_|_|_|_|_|_|\n"+
				 "1|_|_|_|_|_|_|_|_|_|_|\n"+
				 "0|_|_|_|_|_|_|_|_|_|_|\n"+						 
				 "  0 1 2 3 4 5 6 7 8 9";	
		
		checkValid(input,output,7);
	}
	
	@Test
	public void test_08() {
		String input = "2\n" +
				// Place cathedral at 5,5 facing North;
				// and Place L-building at 6,3 facing south 
				"P(5,5;N;C) P(6,3;W;L)";
		
		String output = 
				 "9|_|_|_|_|_|_|_|_|_|_|\n"+
				 "8|_|_|_|_|_|_|_|_|_|_|\n"+
				 "7|_|_|_|_|_|C|_|_|_|_|\n"+
				 "6|_|_|_|_|_|C|_|_|_|_|\n"+
				 "5|_|_|_|_|C|C|C|_|_|_|\n"+
				 "4|_|_|_|_|_|C|B|_|_|_|\n"+
				 "3|_|_|_|_|_|B|B|_|_|_|\n"+
				 "2|_|_|_|_|_|_|_|_|_|_|\n"+
				 "1|_|_|_|_|_|_|_|_|_|_|\n"+
				 "0|_|_|_|_|_|_|_|_|_|_|\n"+						 
				 "  0 1 2 3 4 5 6 7 8 9";
		
		checkValid(input,output,8);
	}
	
	@Test
	public void test_09() {
		String input = "3\n" +
				// Place cathedral at 5,5 facing North;
				// and Place L-building at 9,9 facing East
				"P(5,5;N;C) P(0,9;E;L)\n" +
				// Place big building t 1,4
				"P(1,4;S;T)\n";
		
		String output = 
				 "9|B|B|_|_|_|_|_|_|_|_|\n"+
				 "8|B|_|_|_|_|_|_|_|_|_|\n"+
				 "7|_|_|_|_|_|C|_|_|_|_|\n"+
				 "6|_|_|_|_|_|C|_|_|_|_|\n"+
				 "5|_|_|_|_|C|C|C|_|_|_|\n"+
				 "4|W|W|W|_|_|C|_|_|_|_|\n"+
				 "3|_|W|_|_|_|_|_|_|_|_|\n"+
				 "2|_|_|_|_|_|_|_|_|_|_|\n"+
				 "1|_|_|_|_|_|_|_|_|_|_|\n"+
				 "0|_|_|_|_|_|_|_|_|_|_|\n"+						 
				 "  0 1 2 3 4 5 6 7 8 9";	
		
		checkValid(input,output,9);
	}
	
	@Test
	public void test_10() {
		String input = "4\n" +
				// Place cathedral at 5,5 facing North;
				// and Place L-building at 9,9 facing East
				"P(5,5;N;C) P(0,9;E;L)\n" +
				// Place big building t 1,4; and  
				// S-building at 6,1 facing north
				"P(1,4;S;T) P(6,1;N;S)\n";
		
		String output = 
				 "9|B|B|_|_|_|_|_|_|_|_|\n"+
				 "8|B|_|_|_|_|_|_|_|_|_|\n"+
				 "7|_|_|_|_|_|C|_|_|_|_|\n"+
				 "6|_|_|_|_|_|C|_|_|_|_|\n"+
				 "5|_|_|_|_|C|C|C|_|_|_|\n"+
				 "4|W|W|W|_|_|C|_|_|_|_|\n"+
				 "3|_|W|_|_|_|_|_|_|_|_|\n"+
				 "2|_|_|_|_|_|_|_|_|_|_|\n"+
				 "1|_|_|_|_|_|B|B|B|B|_|\n"+
				 "0|_|_|_|_|_|_|_|_|_|_|\n"+						 
				 "  0 1 2 3 4 5 6 7 8 9";	
		
		checkValid(input,output,10);
	}
	
	@Test
	public void test_11() {
		String input = "5\n" +
				// Place cathedral at 5,5 facing North;
				// and Place L-building at 9,9 facing East
				"P(5,5;N;C) P(0,9;E;L)\n" +
				// Place big building at 1,4; and  
				// S-building at 6,1 facing north
				"P(1,4;S;T) P(6,1;N;S)\n" +
				// Place S-building at 9,6 facing east
				"P(9,6;E;S)";
		
		String output = 
				 "9|B|B|_|_|_|_|_|_|_|_|\n"+
				 "8|B|_|_|_|_|_|_|_|_|_|\n"+
				 "7|_|_|_|_|_|C|_|_|_|W|\n"+
				 "6|_|_|_|_|_|C|_|_|_|W|\n"+
				 "5|_|_|_|_|C|C|C|_|_|W|\n"+
				 "4|W|W|W|_|_|C|_|_|_|W|\n"+
				 "3|_|W|_|_|_|_|_|_|_|_|\n"+
				 "2|_|_|_|_|_|_|_|_|_|_|\n"+
				 "1|_|_|_|_|_|B|B|B|B|_|\n"+
				 "0|_|_|_|_|_|_|_|_|_|_|\n"+						 
				 "  0 1 2 3 4 5 6 7 8 9";	
		
		checkValid(input,output,11);
	}
	
	/**
	 * ============================================================
	 * Part 4 --- Invalid placements 
	 * ============================================================
	 */
	
	@Test
	public void test_12() {
		String input = "1\n" +
				// Attempt to place Cathedral off the board. This should generate a
				// GameError.
				"P(0,0;N;C)";  
				
		checkInvalid(input,12);
	}
	
	@Test
	public void test_13() {
		String input = "1\n" +
				// Attempt to place Cathedral off the board. This should generate a
				// GameError.
				"P(1,8;N;C)";  
				
		checkInvalid(input,13);
	}
	
	@Test
	public void test_14() {
		String input = "1\n" +
				// Attempt to place Cathedral off the board. This should generate a
				// GameError.
				"P(9,1;N;C)";  
				
		checkInvalid(input,14);
	}
	
	@Test
	public void test_15() {
		String input = "2\n" +
				// Place cathedral at 5,5 facing North;
				// and Place L-building off the board. This should generate a
				// GameError.
				"P(5,5;N;C) P(10,10;S;L)";  
				
		checkInvalid(input,15);
	}
	
	@Test
	public void test_16() {
		String input = "2\n" +
				// Place cathedral at 5,5 facing North;
				// and Place T-building over the Cathedral. This should generate a
				// GameError.
				"P(5,5;N;C) P(5,5;S;T)";  
				
		checkInvalid(input,16);
	}
	
	@Test
	public void test_17() {
		String input = "2\n" +
				// Place cathedral at 5,5 facing North;
				// and Place L-building over the Cathedral. This should generate a
				// GameError.
				"P(5,5;N;C) P(5,4;S;L)";  
				
		checkInvalid(input,17);
	}
	
	@Test
	public void test_18() {
		String input = "3\n" +
				// Place cathedral at 5,5 facing North;
				// and Place L-building at 6,3 facing south 
				"P(5,5;N;C) P(6,3;S;L)" + 
				// Place S-building over L-building. This should
				// throw a GameError.
				"P(4,3;N;S)";
				
		checkInvalid(input,18);
	}
	
	/**
	 * ============================================================
	 * Part 5 --- Use of invalid pieces
	 * ============================================================
	 */

	@Test
	public void test_19() {
		String input = "2\n" +
				// Attempt to place two cathedrals on the board. This should generate a
				// GameError.
				"P(5,5;N;C) P(6,2;N;C)";  
				
		checkInvalid(input,19);
	}
	
	@Test
	public void test_20() {
		String input = "3\n" +
				// Another attempt to place two cathedrals on the board. This should generate a
				// GameError.
				"P(5,5;N;C) P(0,9;E;L)\n" +
				"P(6,2;N;C)";  
				
		checkInvalid(input,20);
	}
	
	@Test
	public void test_21() {
		String input = "1\n" +
				// Attempt to place something other than cathedral first.  This should generate a
				// GameError.
				"P(5,5;N;T)";  
				
		checkInvalid(input,21);
	}
	
	@Test
	public void test_22() {
		String input = "6\n" +
				// Attempt by black player to place three L-pieces on the board.
				// This should throw a GameError.
				"P(5,5;N;C) P(0,9;E;L)\n" +
				"P(0,0;N;L) P(2,9;E;L)\n" +
				"P(2,0;N;L) P(4,9;E;L)\n";
				
		checkInvalid(input,22);
	}
	
	@Test
	public void test_23() {
		String input = "7\n" +
				// Attempt by white player to place three S-buildings on the board.
				// This should throw a GameError.
				"P(5,5;N;C) P(1,9;S;T)\n" +
				"P(1,0;N;S) P(3,8;W;L)\n" +
				"P(1,1;N;S) P(4,9;E;L)\n" + 
				"P(1,2;N;S)";
				
		checkInvalid(input,23);
	}
	
	/**
	 * ============================================================
	 * Part 6 --- Capture Area 
	 * ============================================================
	 */
	
	@Test
	public void test_24() {
		String input = "4\n" +
				// Black player captures an area of the board.
				"P(5,5;N;C) P(5,9;S;L)\n" +
				"P(1,4;S;T) A(8,5;S;T;6,9-9,6)\n";
		
		String output = 
				 "9|_|_|_|_|B|B|b|b|b|b|\n"+
				 "8|_|_|_|_|_|B|b|b|b|b|\n"+
				 "7|_|_|_|_|_|C|b|b|b|b|\n"+
				 "6|_|_|_|_|_|C|b|b|b|b|\n"+
				 "5|_|_|_|_|C|C|C|B|B|B|\n"+
				 "4|W|W|W|_|_|C|_|_|B|_|\n"+
				 "3|_|W|_|_|_|_|_|_|_|_|\n"+
				 "2|_|_|_|_|_|_|_|_|_|_|\n"+
				 "1|_|_|_|_|_|_|_|_|_|_|\n"+
				 "0|_|_|_|_|_|_|_|_|_|_|\n"+						 
				 "  0 1 2 3 4 5 6 7 8 9";	
		
		checkValid(input,output,24);
	}
	
	@Test
	public void test_25() {
		String input = "4\n" +
				// Black player captures an area of the board.
				"P(5,5;N;C) P(5,9;E;L)\n" +
				"P(1,4;S;T) A(8,5;S;T;6,8-9,6;7,9-9,9)\n";
		
		String output = 
				 "9|_|_|_|_|_|B|B|b|b|b|\n"+
				 "8|_|_|_|_|_|B|b|b|b|b|\n"+
				 "7|_|_|_|_|_|C|b|b|b|b|\n"+
				 "6|_|_|_|_|_|C|b|b|b|b|\n"+
				 "5|_|_|_|_|C|C|C|B|B|B|\n"+
				 "4|W|W|W|_|_|C|_|_|B|_|\n"+
				 "3|_|W|_|_|_|_|_|_|_|_|\n"+
				 "2|_|_|_|_|_|_|_|_|_|_|\n"+
				 "1|_|_|_|_|_|_|_|_|_|_|\n"+
				 "0|_|_|_|_|_|_|_|_|_|_|\n"+						 
				 "  0 1 2 3 4 5 6 7 8 9";	
		
		checkValid(input,output,25);
	}
	
	@Test
	public void test_26() {
		String input = "4\n" +
				// Black player captures an area of the board and one of the opponents
				// pieces.
				"P(5,5;N;C) P(5,9;S;L)\n" +
				"P(8,9;S;T) A(8,5;S;T;6,9-9,6)\n";
		
		String output = 
				 "9|_|_|_|_|B|B|b|b|b|b|\n"+
				 "8|_|_|_|_|_|B|b|b|b|b|\n"+
				 "7|_|_|_|_|_|C|b|b|b|b|\n"+
				 "6|_|_|_|_|_|C|b|b|b|b|\n"+
				 "5|_|_|_|_|C|C|C|B|B|B|\n"+
				 "4|_|_|_|_|_|C|_|_|B|_|\n"+
				 "3|_|_|_|_|_|_|_|_|_|_|\n"+
				 "2|_|_|_|_|_|_|_|_|_|_|\n"+
				 "1|_|_|_|_|_|_|_|_|_|_|\n"+
				 "0|_|_|_|_|_|_|_|_|_|_|\n"+						 
				 "  0 1 2 3 4 5 6 7 8 9";	
		
		checkValid(input,output,26);
	}
	
	@Test
	public void test_27() {
		String input = "8\n" +
				// Black player captures an area of the board and one of the opponents
				// pieces.  Opponent then plays piece again.
				"P(5,5;N;C) P(5,9;S;L)\n" +
				"P(8,9;S;T) A(8,5;S;T;6,9-9,6)\n" + 
				"P(1,0;N;T) P(9,0;W;L)\n" + 
				"P(1,3;E;T) P(2,7;S;S)";
		
		String output = 
				 "9|_|_|_|_|B|B|b|b|b|b|\n"+
				 "8|_|_|_|_|_|B|b|b|b|b|\n"+
				 "7|B|B|B|B|_|C|b|b|b|b|\n"+
				 "6|_|_|_|_|_|C|b|b|b|b|\n"+
				 "5|_|_|_|_|C|C|C|B|B|B|\n"+
				 "4|_|W|_|_|_|C|_|_|B|_|\n"+
				 "3|_|W|W|_|_|_|_|_|_|_|\n"+
				 "2|_|W|_|_|_|_|_|_|_|_|\n"+
				 "1|_|W|_|_|_|_|_|_|_|B|\n"+
				 "0|W|W|W|_|_|_|_|_|B|B|\n"+						 
				 "  0 1 2 3 4 5 6 7 8 9";	
		
		checkValid(input,output,27);
	}
	
	/**
	 * ============================================================
	 * Part 7 --- Invalid Capture Area 
	 * ============================================================
	 */
	
	@Test
	public void test_28() {
		String input = "2\n" +
				// Invalid area --- nothing captured.
				"P(5,5;N;C) A(8,5;S;T;6,9-9,6)\n";
		
		checkInvalid(input,28);
	}
	
	@Test
	public void test_29() {
		String input = "4\n" +
				// Black player almost captures an area of the board.
				"P(5,5;N;C) P(4,8;N;L)\n" +
				"P(1,4;S;T) A(8,5;S;T;6,9-9,6)\n";
		
		checkInvalid(input,29);
	}
	
	@Test
	public void test_30() {
		String input = "4\n" +
				// Black player captures an area of the board, but the reported area is
				// smaller than the actual area.
				"P(5,5;N;C) P(5,9;S;L)\n" +
				"P(1,4;S;T) A(8,5;S;T;6,8-9,6)\n";
		
		checkInvalid(input,30);
	}
	
	@Test
	public void test_31() {
		String input = "6\n" +
				// Black player captures an area of the board containing two white
				// pieces.
				"P(5,5;N;C) P(5,9;S;L)\n" +
				"P(9,8;W;T) P(4,2;N;S)\n" +
				"P(7,9;S;T) A(8,5;S;T;6,9-9,6)\n";
		
		checkInvalid(input,31);
	}
	
	/**
	 * ============================================================
	 * Helper Functions 
	 * ============================================================
	 */
	
	/**
	 * This method checks whether or not the given input string is valid.
	 * 
	 * ** DO NOT MODIFY THIS FUNCTION **
	 * 
	 * @param input
	 */
	public void checkValid(String input, String expectedFinalBoard,
			int testNumber) {
		Game game;

		System.out
		.println("===================================================");
		System.out.println("TEST " + testNumber);
		System.out
		.println("===================================================");
		
		// First, parse the list of moves
		try {
			game = new Parser(input).parse();
			game.run();
		} catch (GameError e) {						
			throw new RuntimeException(e);
		}

		// Second, create the board and apply each move to the board.
		String finalBoard = game.toString();
		if (!finalBoard.equals(expectedFinalBoard)) {			
			System.out.println("EXPECTED:");
			System.out.println(expectedFinalBoard);
			System.out.println("ACTUAL:");
			System.out.println(finalBoard);
			fail("Invalid final board --- see the console for details");
		}
	}
	
	/**
	 * This method checks whether or not the given input string is invalid.
	 * 
	 * ** DO NOT MODIFY THIS FUNCTION **
	 * 
	 * @param input
	 */
	public void checkInvalid(String input, int testNumber) {
		Game game;

		System.out
		.println("===================================================");
		System.out.println("TEST " + testNumber);
		System.out
		.println("===================================================");

		// First, parse the list of moves
		try {
			game = new Parser(input).parse();
			game.run();
			System.out.println("Test should have generated a GameError, but didn't");
			System.out.println("\nBOARD:");
			System.out.println(game.toString());
			fail("Test failed to throw a GameError (as expected)");
		} catch (GameError e) {			
			// This is what we want to happen, since the input game is invalid.
			// Therefore, we simply exit the test correctly.
			return;
		}
	}
}
