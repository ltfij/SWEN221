package swen221.assignment2.tests.part1;

import static swen221.assignment2.tests.TestHelpers.*;

import org.junit.Test;
import junit.framework.TestCase;

public class KingTests extends TestCase {
	
	public @Test void testKingMoves() {
		String[][] tests = { 
				// Test 1
				{"e2-e4 d7-d5\nKe1-e2",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|p|p|p|_|p|p|p|p|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|_|_|_|p|_|_|_|_|\n"+
				 "4|_|_|_|_|P|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|P|P|P|P|K|P|P|P|\n"+
				 "1|R|N|B|Q|_|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 2
				{"e2-e4 d7-d5\nBf1-d3 d5xe4\nKe1-f1",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|p|p|p|_|p|p|p|p|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|_|_|_|_|_|_|_|_|\n"+
				 "4|_|_|_|_|p|_|_|_|\n"+
				 "3|_|_|_|B|_|_|_|_|\n"+
				 "2|P|P|P|P|_|P|P|P|\n"+
				 "1|R|N|B|Q|_|K|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 3
				{"e2-e4 d7-d5\nBf1-d3 Ke8-d7",
				 "8|r|n|b|q|_|b|n|r|\n"+
				 "7|p|p|p|k|p|p|p|p|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|_|_|_|p|_|_|_|_|\n"+
				 "4|_|_|_|_|P|_|_|_|\n"+
				 "3|_|_|_|B|_|_|_|_|\n"+
				 "2|P|P|P|P|_|P|P|P|\n"+
				 "1|R|N|B|Q|K|_|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 4
				{"e2-e4 d7-d5\nBf1-d3 Qd8-d6\ne4xd5 Ke8-d8",
				 "8|r|n|b|k|_|b|n|r|\n"+
				 "7|p|p|p|_|p|p|p|p|\n"+
				 "6|_|_|_|q|_|_|_|_|\n"+
				 "5|_|_|_|P|_|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|B|_|_|_|_|\n"+
				 "2|P|P|P|P|_|P|P|P|\n"+
				 "1|R|N|B|Q|K|_|N|R|\n"+
				 "  a b c d e f g h"
				}			
		};
		checkValidTests(tests);
	}	
	
	public @Test void testKingtTakes() {
	    String[][] tests = {
	            // Linus's Test with small modification. Also a check test
	            {"f2-f4 e7-e5\nKe1-f2 c7-c6\nKf2-e3 g7-g6\nKe3-e4 h7-h6\nKe4xe5 f7-f6+\nKe5xf6", 
                // the last move is not executed.
                // king should not be able to take as its in check by the queen
                 "8|r|n|b|q|k|b|n|r|\n"+
                 "7|p|p|_|p|_|_|_|_|\n"+
                 "6|_|_|p|_|_|p|p|p|\n"+
                 "5|_|_|_|_|K|_|_|_|\n"+
                 "4|_|_|_|_|_|P|_|_|\n"+
                 "3|_|_|_|_|_|_|_|_|\n"+
                 "2|P|P|P|P|P|_|P|P|\n"+
                 "1|R|N|B|Q|_|B|N|R|\n"+
                 "  a b c d e f g h"
	            },
	            
	            // Extra test
	            {"e2-e4 d7-d5\nKe1-e2 Nb8-c6\ne4xd5 Nc6-d4+\nKe2-d3 e7-e5\nd5xe6ep Qd8-e7\nKd3xNd4",
                 "8|r|_|b|_|k|b|n|r|\n"+
                 "7|p|p|p|_|q|p|p|p|\n"+
                 "6|_|_|_|_|P|_|_|_|\n"+
                 "5|_|_|_|_|_|_|_|_|\n"+
                 "4|_|_|_|K|_|_|_|_|\n"+
                 "3|_|_|_|_|_|_|_|_|\n"+
                 "2|P|P|P|P|_|P|P|P|\n"+
                 "1|R|N|B|Q|_|B|N|R|\n"+
                 "  a b c d e f g h"
                }
	            
	    };
	    
	    checkValidTests(tests);
	}
	
}
