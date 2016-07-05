package swen221.assignment2.tests.part2;

import static swen221.assignment2.tests.TestHelpers.*;

import org.junit.Test;
import junit.framework.TestCase;

public class InvalidPawnTests extends TestCase {
	
	public @Test void testInvalidPawnMoves() {
		String[] tests = { 
			"e2-e3 c7-c6\ne3-e5",
			"e2-e5",
			"e2-e4 e7-e5\ne4-d5",
			"e2-e4 e7-e5\ne4-e3",
			"a2-a3 d7-d5\na3-a4 d5-d4\na4-a5 d4-d3\nd2-d4",
			"a2-a3 d7-d5\na3-a4 d5-d4\nd2-d4",
			// EXTRA tests including invalid takes
			"e2-e4 e7-e5\ne4-e6",
			"e2-e4 e7-e5\ne4-d3",
			"a2-a3 d7-d5\na3-a4 d5-d4\na4-a5 d4-d3\nd2xd3",
			"a2-a3 d7-d5\na3-a4 d5-d4\nd2xd4",
			"a2-a3 d7-d5\na3-a4 d5-d4\nc2xd4",
			"c2-c4 e7-e6\nc4-c5 d7-d6\nc5xe6",
			"c2-c4 d7-d5\nc4-c5 d5-d4\nd2-d3 d4xd3",
			"c2-c4 d7-d5\nc4-c5 d5-d4\ne2-e3 d4xe3\nBf1-e2 e3xf2",
			"c2-c4 d7-d5\nc4-c5 d5-d4\ne2-e3 d4xe3\nBf1-e2 e3xf2+\nKe1-f1 f2xf1",
			
		};
		checkInvalidTests(tests);
	}
	
	public @Test void testInvalidPawnTakes() {
		String[] tests = {
			"e2-e4 e7-e5\ne4xe5",
			"c2-c4 e7-e6\nc4xe6",
			"c2-c4 d7-d6\nc4xd6",
			"c2-c4 d7-d6\nc4xd5",
			"c2-c4 d7-d5\nc4-c5 d5-d4\nc5xd4"
		};
		
		checkInvalidTests(tests);
	}
}
