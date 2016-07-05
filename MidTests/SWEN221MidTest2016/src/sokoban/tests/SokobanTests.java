package sokoban.tests;

import org.junit.*;
import static org.junit.Assert.fail;

import org.junit.runners.MethodSorters;

import sokoban.Game;
import sokoban.io.*;

import org.junit.FixMethodOrder;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SokobanTests {

	/**
	 * ============================================================
	 * Part 1 --- Valid Walking Moves
	 * ============================================================
	 */

	@Test
	public void test_01() {
		// Check player can move one step north
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|_|O|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "W(N;1)";

		String finalBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|O|_|_|_|_|#|\n" +
				"3|#|_|_|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";
		
		checkValid(moves,startingBoard,finalBoard);
	}

	@Test
	public void test_02() {
		// Check player can move two steps east
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|_|O|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "W(E;2)";

		String finalBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|_|_|_|O|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";
		
		checkValid(moves,startingBoard,finalBoard);
	}
	
	/**
	 * ============================================================
	 * Part 2 --- Valid Pushing Moves
	 * ============================================================
	 */
	
	@Test
	public void test_03() {
		// Check player can push one step north
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|X|_|#|\n" +
				"3|#|_|_|_|_|O|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "P(N;1)";

		String finalBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|X|_|#|\n" +
				"4|#|_|_|_|_|O|_|#|\n" +
				"3|#|_|_|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";
		
		checkValid(moves,startingBoard,finalBoard);
	}
	
	@Test
	public void test_04() {
		// Check player can push two steps south
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|O|_|#|\n" +
				"3|#|_|_|_|_|X|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "P(S;2)";

		String finalBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|_|_|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|O|_|#|\n" +
				"1|#|_|_|#|#|X|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";
		
		checkValid(moves,startingBoard,finalBoard);
	}
	
	@Test
	public void test_05() {
		// Check player can move and then push
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|X|_|_|#|\n" +
				"3|#|_|_|_|_|O|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "2 8,8\n" +
					   "W(N;1)\n" +
				       "P(W;1)";

		String finalBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|X|O|_|_|#|\n" +
				"3|#|_|_|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";
		
		checkValid(moves,startingBoard,finalBoard);
	}
	
	@Test
	public void test_06() {
		// Check player can push then move then push
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|_|X|_|_|_|_|#|\n" +
				"2|#|_|O|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "4 8,8\n" +
					   "P(N;1)\n" +
				       "W(W;1)\n" +
					   "W(N;1)\n" +
					   "P(E;4)\n";

		String finalBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|O|X|#|\n" +
				"3|#|_|_|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";
		
		checkValid(moves,startingBoard,finalBoard);
	}
	
	/**
	 * ============================================================
	 * Part 3 --- Invalid Walking Moves
	 * ============================================================
	 */
	
	@Test
	public void test_07() {
		// Check player cannot move one step through a wall
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|O|_|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "W(W;1)";
		
		checkInvalid(moves,startingBoard);
	}
	
	@Test
	public void test_08() {
		// Check player cannot move two steps into a wall
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|_|_|_|_|_|_|#|\n" +
				"2|#|O|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "W(S;2)";
		
		checkInvalid(moves,startingBoard);
	}
	
	@Test
	public void test_09() {
		// Check player cannot move through a wall
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|_|_|_|#|\n" +
				"5|#|_|_|#|_|_|_|#|\n" +
				"4|#|_|_|#|_|_|_|#|\n" +
				"3|#|_|_|#|_|_|_|#|\n" +
				"2|#|_|O|#|_|_|_|#|\n" +
				"1|#|_|_|#|_|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "W(E;2)";
		
		checkInvalid(moves,startingBoard);
	}
	
	@Test
	public void test_10() {
		// Check player cannot move one step into a crate
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|X|_|_|_|_|_|#|\n" +
				"2|#|O|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "W(N;1)";
		
		checkInvalid(moves,startingBoard);
	}
	
	@Test
	public void test_11() {
		// Check player cannot move through a crate
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|X|_|_|_|#|\n" +
				"5|#|_|_|X|_|_|_|#|\n" +
				"4|#|_|_|X|_|_|_|#|\n" +
				"3|#|_|_|X|_|_|_|#|\n" +
				"2|#|_|O|X|_|_|_|#|\n" +
				"1|#|_|_|X|_|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "W(E;2)";
		
		checkInvalid(moves,startingBoard);
	}
	
	/**
	 * ============================================================
	 * Part 4 --- Invalid Pushing Moves
	 * ============================================================
	 */
	
	@Test
	public void test_12() {
		// Check player cannot push thin air
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|O|_|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "P(E;1)";
		
		checkInvalid(moves,startingBoard);
	}
	
	@Test
	public void test_13() {
		// Check player cannot push thin air
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|X|_|_|_|_|_|#|\n" +
				"3|#|O|_|_|_|_|_|#|\n" +
				"2|#|X|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "P(E;1)";
		
		checkInvalid(moves,startingBoard);
	}
	
	@Test
	public void test_14() {
		// Check player cannot push a wall
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|X|O|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "P(W;1)";
		
		checkInvalid(moves,startingBoard);
	}
	
	@Test
	public void test_15() {
		// Check player cannot push crate into a wall
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|_|X|O|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "P(W;2)";
		
		checkInvalid(moves,startingBoard);
	}
	
	@Test
	public void test_16() {
		// Check player cannot push more than one crate
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|O|X|X|_|_|#|\n" +
				"3|#|_|_|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "P(E;1)";
		
		checkInvalid(moves,startingBoard);
	}
	
	@Test
	public void test_17() {
		// Check player cannot push more than one crate
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|O|X|_|X|_|#|\n" +
				"3|#|_|_|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "P(E;2)";
		
		checkInvalid(moves,startingBoard);
	}
	
	/**
	 * ============================================================
	 * Part 5 --- Storage Locations
	 * ============================================================
	 */
	
	@Test
	public void test_18() {
		// Check player can walk into storage location
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|+|_|_|_|_|#|\n" +
				"3|#|_|O|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "W(N;1)\n";

		String finalBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|@|_|_|_|_|#|\n" +
				"3|#|_|_|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";
		
		checkValid(moves,startingBoard,finalBoard);
	}
	
	@Test
	public void test_19() {
		// Check player can walk through storage location
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|+|_|_|_|_|#|\n" +
				"3|#|_|O|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "W(N;2)\n";

		String finalBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|O|#|#|_|_|#|\n" +
				"4|#|_|+|_|_|_|_|#|\n" +
				"3|#|_|_|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";
		
		checkValid(moves,startingBoard,finalBoard);
	}
	
	@Test
	public void test_20() {
		// Check player can push crate into storage location
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|+|_|_|_|_|#|\n" +
				"3|#|_|X|_|_|_|_|#|\n" +
				"2|#|_|O|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "P(N;1)\n";

		String finalBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|*|_|_|_|_|#|\n" +
				"3|#|_|O|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";
		
		checkValid(moves,startingBoard,finalBoard);
	}
	
	@Test
	public void test_21() {
		// Check player can push crate through storage location
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|+|_|_|_|_|#|\n" +
				"3|#|_|X|_|_|_|_|#|\n" +
				"2|#|_|O|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "P(N;3)\n";

		String finalBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|X|#|#|_|_|#|\n" +
				"5|#|_|O|#|#|_|_|#|\n" +
				"4|#|_|+|_|_|_|_|#|\n" +
				"3|#|_|_|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";
		
		checkValid(moves,startingBoard,finalBoard);
	}
	
	@Test
	public void test_22() {
		// Check player can push crate through storage location
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|+|_|_|_|_|#|\n" +
				"3|#|_|X|_|_|_|_|#|\n" +
				"2|#|_|O|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "P(N;2)\n";

		String finalBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|X|#|#|_|_|#|\n" +
				"4|#|_|@|_|_|_|_|#|\n" +
				"3|#|_|_|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";
		
		checkValid(moves,startingBoard,finalBoard);
	}
	
	@Test
	public void test_23() {
		// Check player can push crate through storage location in three steps
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|+|_|_|_|_|#|\n" +
				"3|#|_|X|_|_|_|_|#|\n" +
				"2|#|_|O|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "3 8,8\n" +
					   "P(N;1)\n" +
					   "P(N;1)\n" +
					   "P(N;1)\n";

		String finalBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|X|#|#|_|_|#|\n" +
				"5|#|_|O|#|#|_|_|#|\n" +
				"4|#|_|+|_|_|_|_|#|\n" +
				"3|#|_|_|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";
		
		checkValid(moves,startingBoard,finalBoard);
	}
	
	@Test
	public void test_24() {
		// Check player cannot push storage location
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|+|_|_|_|_|#|\n" +
				"3|#|_|O|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "P(N;1)\n";
	
		checkInvalid(moves,startingBoard);
	}
	
	@Test
	public void test_25() {
		// Check player cannot push crate into storage location already holding something.
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|+|X|_|_|_|#|\n" +
				"3|#|_|X|_|_|_|_|#|\n" +
				"2|#|_|O|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "4 8,8\n" +
					   "P(N;1)\n" +
					   "W(E;2)\n" +
					   "W(N;1)\n" +
					   "P(W;1)\n";
	
		checkInvalid(moves,startingBoard);
	}
	
	/**
	 * ============================================================
	 * Part 6 --- Finishing Moves
	 * ============================================================
	 */
	
	@Test
	public void test_26() {
		// Check player can complete game with one push
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|+|_|_|_|_|#|\n" +
				"3|#|_|X|_|_|_|_|#|\n" +
				"2|#|_|O|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "2 8,8\n" +
					   "P(N;1)\n" +
					   "F\n";

		String finalBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|*|_|_|_|_|#|\n" +
				"3|#|_|O|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";
		
		checkValid(moves,startingBoard,finalBoard);
	}
	
	@Test
	public void test_27() {
		// Check player can complete game with one push, even if there are empty
		// storage locations.
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|+|_|_|+|_|#|\n" +
				"3|#|_|X|_|_|_|_|#|\n" +
				"2|#|_|O|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "2 8,8\n" +
					   "P(N;1)\n" +
					   "F\n";

		String finalBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|*|_|_|+|_|#|\n" +
				"3|#|_|O|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";
		
		checkValid(moves,startingBoard,finalBoard);
	}
	
	@Test
	public void test_28() {
		// Check player can complete game with two pushes
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|+|_|_|_|_|#|\n" +
				"3|#|_|X|_|X|+|_|#|\n" +
				"2|#|_|O|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "3 8,8\n" +
					   "P(N;1)\n" +
					   "W(E;1)\n" +
					   "P(E;1)\n" +
					   "F\n";

		String finalBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|*|_|_|_|_|#|\n" +
				"3|#|_|_|_|O|*|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";
		
		checkValid(moves,startingBoard,finalBoard);
	}
	
	@Test
	public void test_29() {
		// Check player cannot complete game with zero pushes
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|+|_|_|_|_|#|\n" +
				"3|#|_|X|_|_|_|_|#|\n" +
				"2|#|_|O|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "F\n";
		
		checkInvalid(moves,startingBoard);
	}

	@Test
	public void test_30() {
		// Check player cannot complete game with one push
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|+|_|_|+|_|#|\n" +
				"3|#|_|X|_|_|X|_|#|\n" +
				"2|#|_|O|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "2 8,8\n" +
					   "P(N;1)" +
					   "F\n";
		
		checkInvalid(moves,startingBoard);
	}

	@Test
	public void test_31() {
		// Check player cannot complete game with two pushes
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|+|_|_|+|_|#|\n" +
				"3|#|_|X|X|+|X|_|#|\n" +
				"2|#|_|O|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "3 8,8\n" +
					   "P(N;1)" +
					   "P(E;1)" +
					   "F\n";
		
		checkInvalid(moves,startingBoard);
	}

	@Test
	public void test_32() {
		// Check player cannot complete game by standing in storage location
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|X|_|_|_|_|_|#|\n" +
				"2|#|+|O|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "2 8,8\n" +
					   "W(W;1)" +					   
					   "F\n";
		
		checkInvalid(moves,startingBoard);
	}

	/**
	 * ============================================================
	 * Part 7 --- Pulling Moves
	 * ============================================================
	 */
	
	@Test
	public void test_33() {
		// Check player can pull crate one step
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|_|X|_|_|_|_|#|\n" +
				"2|#|_|O|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "L(S;1)";					   

		String finalBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|_|_|_|_|_|_|#|\n" +
				"2|#|_|X|#|#|_|_|#|\n" +
				"1|#|_|O|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";
		
		checkValid(moves,startingBoard,finalBoard);
	}
	
	@Test
	public void test_34() {
		// Check player can pull crate two steps
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|_|X|O|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "L(E;2)";					   

		String finalBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|_|_|_|X|O|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";
		
		checkValid(moves,startingBoard,finalBoard);
	}
	
	@Test
	public void test_35() {
		// Check player cannot pull "thin air"
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|_|_|O|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "L(E;2)";					   
		
		checkInvalid(moves,startingBoard);
	}

	@Test
	public void test_36() {
		// Check player cannot pull into a wall
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|O|X|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "L(W;1)";					   
		
		checkInvalid(moves,startingBoard);
	}
	
	@Test
	public void test_37() {
		// Check player cannot pull through a wall
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|_|_|_|#|\n" +
				"5|#|_|_|#|_|_|_|#|\n" +
				"4|#|_|_|#|_|_|_|#|\n" +
				"3|#|X|O|#|_|_|_|#|\n" +
				"2|#|_|_|#|_|_|_|#|\n" +
				"1|#|_|_|#|_|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "1 8,8\n" +
					   "L(E;3)";					   
		
		checkInvalid(moves,startingBoard);
	}
	
	@Test
	public void test_38() {
		// Check player can complete with one pull
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|_|X|O|+|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "2 8,8\n" +
					   "L(E;2)\n" +
				       "F";

		String finalBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|_|_|_|*|O|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";
		
		checkValid(moves,startingBoard,finalBoard);
	}
	
	@Test
	public void test_39() {
		// Check player can complete with push and pull
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|+|_|#|\n" +
				"3|#|_|_|O|X|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "5 8,8\n" +
					   "P(E;1)\n" +
					   "W(N;1)\n" +
					   "W(E;1)\n" +
					   "L(N;1)\n" +
				       "F";

		String finalBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|O|_|#|\n" +
				"4|#|_|_|_|_|*|_|#|\n" +
				"3|#|_|_|_|_|_|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";
		
		checkValid(moves,startingBoard,finalBoard);
	}
	
	@Test
	public void test_40() {
		// Check player can pull through storage location in two steps
		String startingBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|_|_|#|\n" +
				"4|#|_|_|_|_|_|_|#|\n" +
				"3|#|_|_|X|O|+|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";

		String moves = "4 8,8\n" +
					   "L(E;2)\n" +
					   "W(N;1)\n" +
					   "W(W;1)\n" +
					   "L(N;1)\n";

		String finalBoard =
				"7|#|#|#|#|#|#|#|#|\n" +
				"6|#|_|_|#|#|_|_|#|\n" +
				"5|#|_|_|#|#|O|_|#|\n" +
				"4|#|_|_|_|_|X|_|#|\n" +
				"3|#|_|_|_|_|+|_|#|\n" +
				"2|#|_|_|#|#|_|_|#|\n" +
				"1|#|_|_|#|#|_|_|#|\n" +
				"0|#|#|#|#|#|#|#|#|\n" +
				"  0 1 2 3 4 5 6 7";
		
		checkValid(moves,startingBoard,finalBoard);
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
	 * @param moves
	 */
	public void checkValid(String moves, String startingBoard, String expectedFinalBoard) {
		Game game;
		System.out
		.println("===================================================");
		System.out.println("TEST " + determineTestNumber());
		System.out
		.println("===================================================");

		// First, parse the list of moves
		try {
			game = new Parser(moves).parse();
			game.initialiseBoard(startingBoard);
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
	 * @param moves
	 */
	public void checkInvalid(String moves, String startingBoard) {
		Game game;

		System.out
		.println("===================================================");
		System.out.println("TEST " + determineTestNumber());
		System.out
		.println("===================================================");

		// First, parse the list of moves
		try {
			game = new Parser(moves).parse();
			game.initialiseBoard(startingBoard);
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

	/**
	 * Determine the number of the test being executed based on its method name.
	 * 
	 * ** DO NOT MODIFY THIS FUNCTION **
	 * 
	 * @return
	 */
	private int determineTestNumber() {
		StackTraceElement[] e = Thread.currentThread().getStackTrace();
		String line = e[3].toString();
		int numStart = line.indexOf('_')+1;
		return Integer.valueOf(line.substring(numStart, numStart+2));
	}
}
