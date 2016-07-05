package swen221.assignment2.tests.part1;

import static swen221.assignment2.tests.TestHelpers.*;

import org.junit.Test;
import junit.framework.TestCase;

public class QueenTests extends TestCase {
	
	public @Test void testQueenMoves() {
		String[][] tests = { 
				// Test 1
				{"e2-e4 d7-d5\nQd1-g4",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|p|p|p|_|p|p|p|p|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|_|_|_|p|_|_|_|_|\n"+
				 "4|_|_|_|_|P|_|Q|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|P|P|P|P|_|P|P|P|\n"+
				 "1|R|N|B|_|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 2
				{"e2-e4 d7-d5\nQd1-g4 Qd8-d6",
				 "8|r|n|b|_|k|b|n|r|\n"+
				 "7|p|p|p|_|p|p|p|p|\n"+
				 "6|_|_|_|q|_|_|_|_|\n"+
				 "5|_|_|_|p|_|_|_|_|\n"+
				 "4|_|_|_|_|P|_|Q|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|P|P|P|P|_|P|P|P|\n"+
				 "1|R|N|B|_|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 3
				{"e2-e4 d7-d5\nQd1-g4 Qd8-d6\nQg4-e6",
				 "8|r|n|b|_|k|b|n|r|\n"+
				 "7|p|p|p|_|p|p|p|p|\n"+
				 "6|_|_|_|q|Q|_|_|_|\n"+
				 "5|_|_|_|p|_|_|_|_|\n"+
				 "4|_|_|_|_|P|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|P|P|P|P|_|P|P|P|\n"+
				 "1|R|N|B|_|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 4
				{"d2-d4 e7-e5\nQd1-d3 Qd8-f6\nQd3-h3",
				 "8|r|n|b|_|k|b|n|r|\n"+
				 "7|p|p|p|p|_|p|p|p|\n"+
				 "6|_|_|_|_|_|q|_|_|\n"+
				 "5|_|_|_|_|p|_|_|_|\n"+
				 "4|_|_|_|P|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|Q|\n"+
				 "2|P|P|P|_|P|P|P|P|\n"+
				 "1|R|N|B|_|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},				
		};
		checkValidTests(tests);
	}
	
	public @Test void testQueenTakes() {
		String[][] tests = {
				// Test 1
				{"e2-e4 d7-d5\nQd1-g4 Qd8-d6\nQg4xg7",
				 "8|r|n|b|_|k|b|n|r|\n"+
				 "7|p|p|p|_|p|p|Q|p|\n"+
				 "6|_|_|_|q|_|_|_|_|\n"+
				 "5|_|_|_|p|_|_|_|_|\n"+
				 "4|_|_|_|_|P|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|P|P|P|P|_|P|P|P|\n"+
				 "1|R|N|B|_|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 2
				{"e2-e4 d7-d5\nQd1-g4 Qd8-d6\nQg4xg7 Qd6-a3\nQg7xRh8",
				 "8|r|n|b|_|k|b|n|Q|\n"+
				 "7|p|p|p|_|p|p|_|p|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|_|_|_|p|_|_|_|_|\n"+
				 "4|_|_|_|_|P|_|_|_|\n"+
				 "3|q|_|_|_|_|_|_|_|\n"+
				 "2|P|P|P|P|_|P|P|P|\n"+
				 "1|R|N|B|_|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 3
				{"e2-e4 d7-d5\nQd1-g4 Qd8-d6\nQg4xg7 Qd6-a3\nQg7xRh8 Qa3xa2",
				 "8|r|n|b|_|k|b|n|Q|\n"+
				 "7|p|p|p|_|p|p|_|p|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|_|_|_|p|_|_|_|_|\n"+
				 "4|_|_|_|_|P|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|q|P|P|P|_|P|P|P|\n"+
				 "1|R|N|B|_|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 4
				{"e2-e4 d7-d5\nQd1-g4 Qd8-d6\nQg4xg7 Qd6-a3\nQg7xRh8 Qa3xa2\nQh8xNg8",
				 "8|r|n|b|_|k|b|Q|_|\n"+
				 "7|p|p|p|_|p|p|_|p|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|_|_|_|p|_|_|_|_|\n"+
				 "4|_|_|_|_|P|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|q|P|P|P|_|P|P|P|\n"+
				 "1|R|N|B|_|K|B|N|R|\n"+
				 "  a b c d e f g h"
				}
		};
		
		checkValidTests(tests);
	}
}
