package swen221.assignment2.tests.part3;

import static swen221.assignment2.tests.TestHelpers.*;

import org.junit.Test;
import junit.framework.TestCase;

public class CastleTests extends TestCase {
	
	public @Test void testValidCastling() {
		String[][] tests = { 
				// Test 1
				{"e2-e4 e7-e6\nNg1-f3 d7-d6\nBf1-c4 f7-f6\nO-O",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|p|p|p|_|_|_|p|p|\n"+
				 "6|_|_|_|p|p|p|_|_|\n"+
				 "5|_|_|_|_|_|_|_|_|\n"+
				 "4|_|_|B|_|P|_|_|_|\n"+
				 "3|_|_|_|_|_|N|_|_|\n"+
				 "2|P|P|P|P|_|P|P|P|\n"+
				 "1|R|N|B|Q|_|R|K|_|\n"+
				 "  a b c d e f g h"
				},
				// Test 2
				{"e2-e3 e7-e6\nd2-d3 Ng8-f6\nc2-c3 Bf8-c5\na2-a3 O-O",
				 "8|r|n|b|q|_|r|k|_|\n"+
				 "7|p|p|p|p|_|p|p|p|\n"+
				 "6|_|_|_|_|p|n|_|_|\n"+
				 "5|_|_|b|_|_|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|P|_|P|P|P|_|_|_|\n"+
				 "2|_|P|_|_|_|P|P|P|\n"+
				 "1|R|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 3
				{"d2-d4 a7-a6\nQd1-d3 b7-b6\nBc1-e3 c7-c6\nNb1-a3 d7-d6\nO-O-O",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|_|_|_|_|p|p|p|p|\n"+
				 "6|p|p|p|p|_|_|_|_|\n"+
				 "5|_|_|_|_|_|_|_|_|\n"+
				 "4|_|_|_|P|_|_|_|_|\n"+
				 "3|N|_|_|Q|B|_|_|_|\n"+
				 "2|P|P|P|_|P|P|P|P|\n"+
				 "1|_|_|K|R|_|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 4
				{"a2-a3 d7-d5\nb2-b3 Qd8-d6\nc2-c3 Bc8-e6\nd2-d3 Nb8-a6\ne2-e3 O-O-O",
				 "8|_|_|k|r|_|b|n|r|\n"+
				 "7|p|p|p|_|p|p|p|p|\n"+
				 "6|n|_|_|q|b|_|_|_|\n"+
				 "5|_|_|_|p|_|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|P|P|P|P|P|_|_|_|\n"+
				 "2|_|_|_|_|_|P|P|P|\n"+
				 "1|R|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				}
		
		};
		checkValidTests(tests);
	}
	
	
	
	public @Test void testInvalidCastling() {
		String[] tests = {
			"e2-e4 e7-e6\nNg1-f3 d7-d6\nO-O",
			"e2-e3 e7-e6\nd2-d3 Ng8-f6\nc2-c3 O-O",
			"d2-d4 a7-a6\nQd1-d3 b7-b6\nNb1-a3 d7-d6\nO-O-O",
			"a2-a3 d7-d5\nb2-b3 Qd8-d6\nc2-c3 Bc8-e6\ne2-e3 O-O-O",
			"e2-e4 e7-e6\nNg1-f3 d7-d6\nBf1-c4 f7-f6\nKe1-f1 g7-g6\nKf1-e1 h7-h6\nO-O", // tricksy
			"d2-d4 a7-a6\nQd1-d3 b7-b6\nBc1-e3 c7-c6\nNb1-a3 d7-d6\nKe1-d1 g7-g6\nKd1-e1 h7-h6\nO-O-O",
			// EXTRA tests
			"a2-a3 d7-d5\nb2-b3 Qd8-d6\nc2-c3 Bc8-h3\nd2-d3 Nb8-c6\ne2-e3 Ke8-d8\nf2-f3 Kd8-e8\n" +
			"g2-g3 O-O-O",
			"a2-a3 d7-d5\nb2-b3 Qd8-d6\nc2-c3 Bc8-h3\nd2-d3 Nb8-c6\ne2-e3 Ra8-d8\nf2-f3 Rd8-a8\n" +
			"g2-g3 O-O-O",
			"a2-a3 d7-d5\nb2-b3 Qd8-d6\nc2-c3 Bc8-h3\nd2-d3 Nb8-c6\ne2-e3 Ra8-d8\nf2-f3 Rd8-a8\n" +
		    "g2-g3 Ke8-d8\na3-a4 Kd8-e8\nb3-b4 O-O-O",
		    "Nb1-a3 a7-a6\nd2-d3 b7-b6\nBc1-h6 c7-c6\nc2-c3 d7-d6\nQd1-a4 e7-e6\nNa3-c4 f7-f6\n" +
		    "a2-a3 g7-g6\nb2-b3 a6-a5\ne2-e3 b6-b5\nRa1-a2 c6-c5\nKe1-e2 d6-d5\nO-O-O",
		    // whether the king can castle out of, through or into check
		    "d2-d4 d7-d5\ne2-e4 e7-e5\na2-a3 c7-c5\nb2-b3 Qd8-h4\nc2-c3 Bc8-g4\ng2-g3 Nb8-a6\n" +
		    "Bf1-b5+ O-O-O", // out of
		    "d2-d4 d7-d5\ne2-e4 e7-e5\na2-a3 c7-c5\nb2-b3 Qd8-h4\nc2-c3 Bc8-g4\ng2-g3 Nb8-a6\n" +
            "Qd1xBg4 b7-b6\nQg4-g5 h7-h6\ng3xQh4 O-O-O", // through
            "d2-d4 d7-d5\ne2-e4 e7-e5\na2-a3 c7-c5\nb2-b3 Qd8-h4\nc2-c3 Bc8-g4\ng2-g3 Nb8-a6\n" +
            "Qd1xBg4 b7-b6\ng3xQh4 O-O-O" // into
			
		};
		
		checkInvalidTests(tests);
	}
}
