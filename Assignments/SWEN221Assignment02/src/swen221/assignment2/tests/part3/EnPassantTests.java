package swen221.assignment2.tests.part3;

import static swen221.assignment2.tests.TestHelpers.*;

import org.junit.Test;
import junit.framework.TestCase;

// See: http://en.wikipedia.org/wiki/En_passant

public class EnPassantTests extends TestCase {
	
	public @Test void testEnPassant() {
		String[][] tests = { 
				// Test 1
				{"h2-h3 b7-b5\ng2-g3 b5-b4\na2-a4 b4xa3ep",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|p|_|p|p|p|p|p|p|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|_|_|_|_|_|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|p|_|_|_|_|_|P|P|\n"+
				 "2|_|P|P|P|P|P|_|_|\n"+
				 "1|R|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 2
				{"a2-a4 h7-h6\na4-a5 b7-b5\na5xb6ep",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|p|_|p|p|p|p|p|_|\n"+
				 "6|_|P|_|_|_|_|_|p|\n"+
				 "5|_|_|_|_|_|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|_|P|P|P|P|P|P|P|\n"+
				 "1|R|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				
				// Extra Test
				{"a2-a4 a7-a6\na4-a5 b7-b5\na5xb6ep c7-c5\nc2-c3 c5-c4\nd2-d4 c4xd3ep\n" +
				 "e2-e4 e7-e6\ne4-e5 f7-f5\ne5xf6ep g7-g5\ng2-g3 g5-g4\nh2-h4 g4xh3ep",
                 "8|r|n|b|q|k|b|n|r|\n"+
                 "7|_|_|_|p|_|_|_|p|\n"+
                 "6|p|P|_|_|p|P|_|_|\n"+
                 "5|_|_|_|_|_|_|_|_|\n"+
                 "4|_|_|_|_|_|_|_|_|\n"+
                 "3|_|_|P|p|_|_|P|p|\n"+
                 "2|_|P|_|_|_|P|_|_|\n"+
                 "1|R|N|B|Q|K|B|N|R|\n"+
                 "  a b c d e f g h"
                }
				
		};
		checkValidTests(tests);
	}	
	
	public @Test void testInvalidEnPassant() {
		String[] tests = {
			"h2-h3 b7-b5\na2-a3 b5-b4\na3-a4 b4xa3ep",
			"a2-a4 b7-b6\na4-a5 b6-b5\na5xb6ep",
			"a2-a3 b7-b5\na3-a4 b5-b4\na3xb5ep",
			"a2-a4 c7-c6\na4-a5 Qd8-b6\nh2-h3 Qb6-b5\na5xb6ep",
			// EXTRA tests
			"a2-a3 b7-b5\na3-a4 b5-b4\na4xb5ep",
			"a2-a4 c7-c6\na4-a5 Qd8-b6\nh2-h3 Qb6-b5\na5xQb6ep",
			"a2-a4 c7-c6\na4-a5 b7-b6\na5xb6ep",
			"a2-a4 a7-a5\na4xb5ep",
			// tricky!
			"d2-d4 e7-e5\nd4-d5 h7-h6\nd5xe6ep", // 
			"a2-a3 e7-e5\nd2-d4 e5-e4\nb2-b3 e4xd3ep",
			"d2-d4 a7-a6\nd4-d5 e7-e5\na2-a3 b7-b6\nd5xe6ep",
			"a2-a3 d7-d5\nb2-b3 d5-d4\ne2-e4 a7-a6\nc2-c3 d4xe3ep",
		};
		
		checkInvalidTests(tests);
	}
}
