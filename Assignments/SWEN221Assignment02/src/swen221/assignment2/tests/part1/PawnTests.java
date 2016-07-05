package swen221.assignment2.tests.part1;

import static swen221.assignment2.tests.TestHelpers.*;

import org.junit.Test;
import junit.framework.TestCase;

public class PawnTests extends TestCase {
	
	public @Test void testPawnMoves() {
		String[][] tests = { 
				// Test 1
				{"a2-a3",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|p|p|p|p|p|p|p|p|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|_|_|_|_|_|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|P|_|_|_|_|_|_|_|\n"+
				 "2|_|P|P|P|P|P|P|P|\n"+
				 "1|R|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 2
				{"h2-h3 e7-e6",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|p|p|p|p|_|p|p|p|\n"+
				 "6|_|_|_|_|p|_|_|_|\n"+
				 "5|_|_|_|_|_|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|P|\n"+
				 "2|P|P|P|P|P|P|P|_|\n"+
				 "1|R|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 3
				{"h2-h4 e7-e5",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|p|p|p|p|_|p|p|p|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|_|_|_|_|p|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|P|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|P|P|P|P|P|P|P|_|\n"+
				 "1|R|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 4
				{"d2-d4 e7-e5\ne2-e3 d7-d5\nc2-c4",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|p|p|p|_|_|p|p|p|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|_|_|_|p|p|_|_|_|\n"+
				 "4|_|_|P|P|_|_|_|_|\n"+
				 "3|_|_|_|_|P|_|_|_|\n"+
				 "2|P|P|_|_|_|P|P|P|\n"+
				 "1|R|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},								
		};
		checkValidTests(tests);
	}
	
	public @Test void testPawnTakes() {
		String[][] tests = {
				// Test 1
				{"d2-d4 e7-e5\nd4xe5",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|p|p|p|p|_|p|p|p|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|_|_|_|_|P|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|P|P|P|_|P|P|P|P|\n"+
				 "1|R|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 2
				{"d2-d4 c7-c5\nd4xc5",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|p|p|_|p|p|p|p|p|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|_|_|P|_|_|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|P|P|P|_|P|P|P|P|\n"+
				 "1|R|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 3
				{"d2-d3 e7-e5\nd3-d4 e5xd4",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|p|p|p|p|_|p|p|p|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|_|_|_|_|_|_|_|_|\n"+
				 "4|_|_|_|p|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|P|P|P|_|P|P|P|P|\n"+
				 "1|R|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 4
				{"d2-d3 c7-c5\nd3-d4 c5xd4",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|p|p|_|p|p|p|p|p|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|_|_|_|_|_|_|_|_|\n"+
				 "4|_|_|_|p|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|P|P|P|_|P|P|P|P|\n"+
				 "1|R|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				}		
		};
		
		checkValidTests(tests);
	}
}
