package swen221.assignment2.tests.part2;

import static swen221.assignment2.tests.TestHelpers.*;

import org.junit.Test;
import junit.framework.TestCase;

public class InvalidBishopTests extends TestCase {
	
	public @Test void testInvalidBishopMoves() {
		String[] tests = { 
				"Bc1-c3",
				"Bc1-e3",
				"Bc1-b3",
				"c2-c3 e7-e6\nBc1-c2",
				"c2-c3 Bc8-c6",
				"c2-c3 Bc8-e6",
				"c2-c3 e7-e6\nd2-d4 Bf8-c5\nBc1-d2 Bc5-e3",
				// EXTRA also test invalid takes
				"c2-c3 e7-e6\nd2-d4 Bf8-c5\nBc1-d2 Bc5xa7",
				"c2-c4 e7-e6\nd2-d4 Bf8-b4+\nBc1-d2 Bb4xe1",
				"c2-c4 e7-e6\nd2-d4 Bf8-b4+\nBc1-d2 Bb4xd2+",
		};
		checkInvalidTests(tests);
	}
	
	public @Test void testInvalidBishopTakes() {
		String[] tests = {
			"Bc1xc7",
			"Bc1xh6",
			"Bc1xd2",
			"d2-d3 e7-e6\nBc1-f4 f7-f6\nBf4xNb8",
			"c2-c3 e7-e6\nd2-d4 Bf8-c5\nBc1-d2 Bc5xf2"
		};
		
		checkInvalidTests(tests);
	}
}
