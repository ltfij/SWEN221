package swen221.assignment2.tests;


import static org.junit.Assert.*;

import java.util.*;

import swen221.assignment2.chessview.*;

public class TestHelpers {

	// The following provides a simple helper method for all tests.
	public static void checkValidTests(String[][] tests) {
		for(String[] test : tests) {
			String input = test[0];
			String expectedOutput = test[1];
			try {
				ChessGame game = new ChessGame(input);
				List<Board> boards = game.boards();

				if (boards.isEmpty()) {
					fail("test failed with insufficient boards on input: " + input);
				}

				String actualOutput = boards.get(boards.size()-1).toString();

				if(!actualOutput.equals(expectedOutput)) {
					// the following print statements are helpful for debugging.
					System.out.println("INPUT:\n" + input);
					System.out.println("ACTUAL OUTPUT:\n" + actualOutput);
					System.out.println("EXPECTED OUTPUT:\n" +expectedOutput);							
					fail("test failed with incorrect last board on input: " + input);
				}
			} catch(Exception e) {
				fail("test failed because of exception on input: " + input);
			}
		}
	}
	
	// The following provides a simple helper method for all tests.
	public static void checkInvalidTests(String[] tests) {
		for(String input : tests) {			
			try {
				ChessGame game = new ChessGame(input);
				List<Board> boards = game.boards();
				List<Round> rounds = game.rounds();
				
				if (boards.isEmpty()) {
					fail("test failed with insufficient boards on input: " + input);
				}

				int nboards = boards.size();
				int eboards = rounds.size() * 2;
				if(rounds.get(rounds.size()-1).black() == null) {
					eboards--;
				}
												
				if(nboards != eboards) {
					// Basically, this happens because either too few, or too
					// many rounds happened. It should be that the last move in
					// each round is invalid and, hence, doesn't generate a new
					// board.
					System.out.println("INPUT:\n" + input);
					for(Board b : boards) {
						System.out.println("==================\n" + b);
					}
					fail("test failed with incorrect number of rounds");
				}
				
			} catch(Exception e) {
				fail("test failed because of exception on input: " + input);
			}
		}
	}
}
