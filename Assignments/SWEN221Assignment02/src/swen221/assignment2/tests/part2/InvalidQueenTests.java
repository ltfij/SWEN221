package swen221.assignment2.tests.part2;

import static swen221.assignment2.tests.TestHelpers.*;

import org.junit.Test;
import junit.framework.TestCase;

public class InvalidQueenTests extends TestCase {
	
	public @Test void testInvalidQueenMoves() {
		String[] tests = { 
			"Qd1-d4",
			"Qd1-f3",
			"Qd1-e3",
			"Qd1-e1",
			"e2-e4 d7-d5\nQd1-e2 d5xe4\nQe2-e6",
			"e2-e4 d7-d5\nQd1-e2 d5xe4\nQe3-e2",
			"c2-c3 d7-d5\nQd1-b3 Qd8-d7\nQb3-e6",
			// Extra tests including invalid takes
			"e2-e4 d7-d5\nQd1-e2 d5xe4\nQe2-e4",
			"e2-e4 d7-d5\nQd1-e2 d5xe4\nQe2xf1+",
			"c2-c3 d7-d5\nQd1-b3 Qd8-d7\nQb3xQd7+",
			"c2-c3 d7-d5\nQd1-b3 Qd8-d7\nQb3xd5+",
			"c2-c3 d7-d5\nQd1-b3 Qd8-d7\nQb3xd5 Qd7-a4\nQd5-b3 Bc8-e6\nKe1-d1 Be6-g4\n" +
			"Qb3-e4+", // last move is illegal, make your own king being checked
			// test if the piece can be out of board
			"e2-e4 d7-d5\nQd1-e2 d5xe4\nQe2-i6",
			"e2-e4 d7-d5\ne4xd5 e7-e6\nc2-c4 e6xd5\nQd1-e2+ Ke8-d7\nQe2-e9"
		};
		checkInvalidTests(tests);
	}
	
	public @Test void testInvalidQueenTakes() {
		String[] tests = {
			"Qd1xQd8",
			"Qd1xBc1",
			"e2-e4 d7-d5\nQd1-e2 d5xe4\nQe2xe6",
			"e2-e4 d7-d5\nQd1-e2 d5xe4\nQe3xe2",
			"c2-c3 d7-d5\nQd1-b3 e7-e6\nQb3xe6"
		};
		
		checkInvalidTests(tests);
	}
}
