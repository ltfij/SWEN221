package swen221.assignment2.tests.part1;

import static swen221.assignment2.tests.TestHelpers.*;

import org.junit.Test;
import junit.framework.TestCase;

public class RookTests extends TestCase {
	
	public @Test void testRookMoves() {
		String[][] tests = { 
				// Test 1
				{"a2-a4 a7-a5\nRa1-a3",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|_|p|p|p|p|p|p|p|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|p|_|_|_|_|_|_|_|\n"+
				 "4|P|_|_|_|_|_|_|_|\n"+
				 "3|R|_|_|_|_|_|_|_|\n"+
				 "2|_|P|P|P|P|P|P|P|\n"+
				 "1|_|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 2
				{"a2-a4 a7-a5\nRa1-a3 Ra8-a6",
				 "8|_|n|b|q|k|b|n|r|\n"+
				 "7|_|p|p|p|p|p|p|p|\n"+
				 "6|r|_|_|_|_|_|_|_|\n"+
				 "5|p|_|_|_|_|_|_|_|\n"+
				 "4|P|_|_|_|_|_|_|_|\n"+
				 "3|R|_|_|_|_|_|_|_|\n"+
				 "2|_|P|P|P|P|P|P|P|\n"+
				 "1|_|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 3
				{"a2-a4 a7-a5\nRa1-a3 Ra8-a6\nRa3-h3 Ra6-e6",
				 "8|_|n|b|q|k|b|n|r|\n"+
				 "7|_|p|p|p|p|p|p|p|\n"+
				 "6|_|_|_|_|r|_|_|_|\n"+
				 "5|p|_|_|_|_|_|_|_|\n"+
				 "4|P|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|R|\n"+
				 "2|_|P|P|P|P|P|P|P|\n"+
				 "1|_|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 4
				{"h2-h4 h7-h5\nRh1-h3 Rh8-h6\nRh3-a3 Rh6-e6",
				 "8|r|n|b|q|k|b|n|_|\n"+
				 "7|p|p|p|p|p|p|p|_|\n"+
				 "6|_|_|_|_|r|_|_|_|\n"+
				 "5|_|_|_|_|_|_|_|p|\n"+
				 "4|_|_|_|_|_|_|_|P|\n"+
				 "3|R|_|_|_|_|_|_|_|\n"+
				 "2|P|P|P|P|P|P|P|_|\n"+
				 "1|R|N|B|Q|K|B|N|_|\n"+
				 "  a b c d e f g h"
				}				
		};
		checkValidTests(tests);
	}
	
	public @Test void testRookTakes() {
		String[][] tests = {
				// Test 1
				{"a2-a4 a7-a5\nRa1-a3 Ra8-a6\nRa3-h3 Ra6-e6\nRh3xh7",
				 "8|_|n|b|q|k|b|n|r|\n"+
				 "7|_|p|p|p|p|p|p|R|\n"+
				 "6|_|_|_|_|r|_|_|_|\n"+
				 "5|p|_|_|_|_|_|_|_|\n"+
				 "4|P|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|_|P|P|P|P|P|P|P|\n"+
				 "1|_|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 2
				{"a2-a4 a7-a5\nRa1-a3 Ra8-a6\nRa3-h3 Ra6-d6\nRh3xh7 Rd6xd2",
				 "8|_|n|b|q|k|b|n|r|\n"+
				 "7|_|p|p|p|p|p|p|R|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|p|_|_|_|_|_|_|_|\n"+
				 "4|P|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|_|P|P|r|P|P|P|P|\n"+
				 "1|_|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 3
				{"h2-h4 h7-h5\nRh1-h3 Rh8-h6\nRh3-a3 Rh6-f6\nRa3xa7",
				 "8|r|n|b|q|k|b|n|_|\n"+
				 "7|R|p|p|p|p|p|p|_|\n"+
				 "6|_|_|_|_|_|r|_|_|\n"+
				 "5|_|_|_|_|_|_|_|p|\n"+
				 "4|_|_|_|_|_|_|_|P|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|P|P|P|P|P|P|P|_|\n"+
				 "1|R|N|B|Q|K|B|N|_|\n"+
				 "  a b c d e f g h"
				},
				// Test 4
				{"h2-h4 h7-h5\nRh1-h3 Rh8-h6\nRh3-a3 Rh6-f6\nRa3xa7 Rf6xf2",
				 "8|r|n|b|q|k|b|n|_|\n"+
				 "7|R|p|p|p|p|p|p|_|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|_|_|_|_|_|_|_|p|\n"+
				 "4|_|_|_|_|_|_|_|P|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|P|P|P|P|P|r|P|_|\n"+
				 "1|R|N|B|Q|K|B|N|_|\n"+
				 "  a b c d e f g h"
				}
		};
		
		checkValidTests(tests);
	}
}
