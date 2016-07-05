package swen221.assignment2.tests.part3;

import static swen221.assignment2.tests.TestHelpers.*;

import org.junit.Test;
import junit.framework.TestCase;

public class CheckTests extends TestCase {
	
	public @Test void testValidChecks() {
		String[][] tests = { 
				// Test 1
				{"a2-a4 a7-a5\nRa1-a3 Ra8-a6\nRa3-h3 Ra6-e6\nRh3xh7 Re6xe2+",
				 "8|_|n|b|q|k|b|n|r|\n"+
				 "7|_|p|p|p|p|p|p|R|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|p|_|_|_|_|_|_|_|\n"+
				 "4|P|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|_|P|P|P|r|P|P|P|\n"+
				 "1|_|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 2
				{"a2-a4 a7-a5\nRa1-a3 Ra8-a6\nRa3-h3 Ra6-e6\nRh3xh7 Re6xe2+\nBf1xRe2",
				 "8|_|n|b|q|k|b|n|r|\n"+
				 "7|_|p|p|p|p|p|p|R|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|p|_|_|_|_|_|_|_|\n"+
				 "4|P|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|_|P|P|P|B|P|P|P|\n"+
				 "1|_|N|B|Q|K|_|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 3
				{"Nb1-c3 e7-e6\nNc3-d5 e6-e5\nNd5xc7+ Ke8-e7\nNc7xRa8",
				 "8|N|n|b|q|_|b|n|r|\n"+
				 "7|p|p|_|p|k|p|p|p|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|_|_|_|_|p|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|P|P|P|P|P|P|P|P|\n"+
				 "1|R|_|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 4
				{"d2-d4 e7-e5\nd4-d5 Ke8-e7\nd5-d6+ c7xd6",
				 "8|r|n|b|q|_|b|n|r|\n"+
				 "7|p|p|_|p|k|p|p|p|\n"+
				 "6|_|_|_|p|_|_|_|_|\n"+
				 "5|_|_|_|_|p|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|P|P|P|_|P|P|P|P|\n"+
				 "1|R|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				}					
		};
		checkValidTests(tests);
	}
	
	public @Test void testInvalidChecks() {
		String[] tests = {
			"e2-e4+",
			"e2-e4 e7-e6\nBf1-a6 b7-b6\nBa6xBc8+",
			"Nb1-c3 e7-e6\nNc3-d5 e6-e5\nNd5xc7+ h7-h6",
			"c2-c3 h7-h6\nQd1-a4 d7-d6",
			"d2-d4 e7-e5\nd4xe5 a7-a6\ne5-e6 Nb8-c6\ne6-e7+"
		};
		
		checkInvalidTests(tests);
	}
}
