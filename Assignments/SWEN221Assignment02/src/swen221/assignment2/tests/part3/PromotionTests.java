package swen221.assignment2.tests.part3;

import static swen221.assignment2.tests.TestHelpers.*;

import org.junit.Test;
import junit.framework.TestCase;

public class PromotionTests extends TestCase {
	
	public @Test void testPawnPromotions() {
		String[][] tests = { 
				// Test 1
				{"a2-a4 b7-b5\na4xb5 Nb8-c6\nb5-b6 a7-a6\nb6-b7 a6-a5\nb7-b8=N",
				 "8|r|N|b|q|k|b|n|r|\n"+
				 "7|_|_|p|p|p|p|p|p|\n"+
				 "6|_|_|n|_|_|_|_|_|\n"+
				 "5|p|_|_|_|_|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|_|P|P|P|P|P|P|P|\n"+
				 "1|R|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 2
				{"a2-a4 b7-b5\na4xb5 Nb8-c6\nb5-b6 a7-a6\nb6-b7 a6-a5\nb7-b8=Q a5-a4\nQb8xRa8",
				 "8|Q|_|b|q|k|b|n|r|\n"+
				 "7|_|_|p|p|p|p|p|p|\n"+
				 "6|_|_|n|_|_|_|_|_|\n"+
				 "5|_|_|_|_|_|_|_|_|\n"+
				 "4|p|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|_|P|P|P|P|P|P|P|\n"+
				 "1|R|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 3
				{"a2-a4 b7-b5\na4xb5 Nb8-c6\nb5-b6 a7-a5\nNb1-c3 Bc8-a6\nNg1-f3 e7-e6\nb6-b7 Qd8-h4\nb7-b8=Q+ Ke8-e7",
				 "8|r|Q|_|_|_|b|n|r|\n"+
				 "7|_|_|p|p|k|p|p|p|\n"+
				 "6|b|_|n|_|p|_|_|_|\n"+
				 "5|p|_|_|_|_|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|q|\n"+
				 "3|_|_|N|_|_|N|_|_|\n"+
				 "2|_|P|P|P|P|P|P|P|\n"+
				 "1|R|_|B|Q|K|B|_|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 4
				{"a2-a4 b7-b5\na4xb5 Nb8-c6\nb5-b6 a7-a5\nNb1-c3 Bc8-a6\nb6-b7 Qd8-c8\nb7xQc8=Q+",
				 "8|r|_|Q|_|k|b|n|r|\n"+
				 "7|_|_|p|p|p|p|p|p|\n"+
				 "6|b|_|n|_|_|_|_|_|\n"+
				 "5|p|_|_|_|_|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|_|_|N|_|_|_|_|_|\n"+
				 "2|_|P|P|P|P|P|P|P|\n"+
				 "1|R|_|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
		};
		checkValidTests(tests);
	}
	
	public @Test void testInvalidPawnPromotions() {
		String[] tests = {
			"a2-a4=N",
			// this one is wrong because it doesn't indicate 
			// that the king is in check after the pawn takes the queen and is promoted to a queen. 
			"a2-a4 b7-b5\na4xb5 Nb8-c6\nb5-b6 a7-a5\nNb1-c3 Bc8-a6\nb6-b7 Qd8-c8\nb7xQc8=Q",
			// EXTRA tests
			"a2-a4 b7-b5\na4xb5 Nb8-c6\nb5-b6 a7-a5\nNb1-c3 Bc8-a6\nb6-b7 Qd8-c8\nb7xQc8=N+",
			"a2-a4 b7-b5\na4xb5 Nb8-c6\nb5-b6 a7-a5\nNb1-c3 Bc8-a6\nb6-b7 Qd8-c8\nb7xNb8=N",
			"a2-a4 b7-b5\na4xb5 Nb8-c6\nb5-b6 a7-a5\nNb1-c3 Bc8-a6\nb6-b7 Qd8-c8\nb7-d8=R",
			"a2-a4 b7-b5\na4xb5 Nb8-c6\nb5-b6 a7-a5\nNb1-c3 Bc8-a6\nb6-b7 Qd8-c8\nb7xBd8=K+",
			// for black pawns
			"a2-a3 d7-d5\nb2-b3 d5-d4\nc2-c4 d4xc3ep\nBc1-b2 c3xBb2\nQd1-c1 b2xQc1=B+",
			"a2-a3 d7-d5\nb2-b3 d5-d4\nc2-c4 d4xc3ep\nBc1-b2 c3xBb2\nQd1-c1 b2xQc1=Q",
			// The pawn cannot remain as a pawn
			"a2-a3 d7-d5\nb2-b3 d5-d4\nc2-c4 d4xc3ep\nBc1-b2 c3xBb2\nQd1-c1 b2xQc1",
			"d2-d4 e7-e6\nd4-d5 c7-c5\nd5xc6ep Nb8-a6\nc6xd7+ Ke8-e7\nd7xBc8",

		};
		
		checkInvalidTests(tests);
	}
}
