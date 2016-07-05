package swen221.assignment2.tests.part2;

import static swen221.assignment2.tests.TestHelpers.*;

import org.junit.Test;
import junit.framework.TestCase;

public class InvalidKingTests extends TestCase {
	
	public @Test void testInvalidKingMoves() {
		String[] tests = { 
			"Ke1-e3",
			"Ke1-e2",
			"Ke1-d2",
			"e2-e4 e7-e6\nKe1-e3",
			"d2-d4 e7-e6\nKe1-c3",
			"e2-e4 e7-e6\nKe1-e2 e6-e5\nKe1-e2",
			// EXTRA test
			"d2-d4 e7-e6\nKe1-d3",
			"e2-e4 e7-e6\nKe1-e2 e6-e5\nKe2xe5",
			"e2-e4 e7-e6\nKe1-e2 e6-e5\nKe2-f1",
		};
		checkInvalidTests(tests);
	}
	
	public @Test void testInvalidKingTakes() {
		String[] tests = {
			"Ke1xe7",
			"Ke1xe2",
			"Ke1xd2",
			"e2-e4 e7-e6\nKe1xe2",
			"e2-e4 b7-b5\nKe1-e2 c7-c6\nKe2xb5",
			// EXTRA test, from linus. last move make the king being checked, so illegal
			"f2-f4 e7-e5\nKe1-f2 c7-c6\nKf2-e3 g7-g6\nKe3-e4 h7-h6\nKe4xe5 f7-f6+\nKe5xf6",
		};
		
		checkInvalidTests(tests);
	}
}
