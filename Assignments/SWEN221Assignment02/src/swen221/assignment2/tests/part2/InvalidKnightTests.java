package swen221.assignment2.tests.part2;

import static swen221.assignment2.tests.TestHelpers.*;

import org.junit.Test;
import junit.framework.TestCase;

public class InvalidKnightTests extends TestCase {
	
	public @Test void testInvalidKnightMoves() {
		String[] tests = { 
			"Nb1-b3",
			"Nb1-c4",
			"Nb1-d2",
			"Nb1-c3 d7-d5\nNc3-d5",
			"Nb1-c3 e7-e6\nNc3-a3",
			"Nb1-c3 e7-e6\nNc3-d5 e6-e5\nNd5xc7+ h7-h6"
		};
		checkInvalidTests(tests);
	}
	
	public @Test void testInvalidKnightTakes() {
		String[] tests = {
			"Nb1xd2",
			"Nb1xb7",
			"Nb1-c3 e7-e6\nNc3xe6",
			"e2-e4 Nb8-c6\ne4-e5 Nc6xd4",
			"d2-d4 Nb8-c6\nNg1-f3 Nc6-e5\nNf3xBe5",
			// extra test
			"e2-e4 Nb8-c6\ne4-e5 Nc6xc5",
			"d2-d4 Nb8-c6\nNg1-f3 Nc6-d4",
			"d2-d4 Nb8-c6\nNg1-f3 Nc6xNd4",
			"d2-d4 Nb8-c6\nNg1-f3 Nc6xd4\nNf3xKe4+",
			"d2-d4 Nb8-c6\nNg1-f3 Nc6xd4\nNf3xNe4+",
			"d2-d4 Nb8-c6\nNg1-f3 Nc6xd4\nNf3xNd4 a7-a5\nNd4-f5 b7-b5\nNf5-d6",
			"d2-d4 Nb8-c6\nNg1-f3 Nc6xd4\nKe1-d2 Nd4xNf3+\nKd2-e3 Nf3-e1\nKe3-d2 Ne1xNb1+",
		};
		
		checkInvalidTests(tests);
	}
}
