package swen221.assignment2.tests.part1;

import static swen221.assignment2.tests.TestHelpers.*;

import org.junit.Test;
import junit.framework.TestCase;

public class BishopTests extends TestCase {
	
	public @Test void testBishopMoves() {
		String[][] tests = { 
				// Test 1
				{"e2-e3 e7-e6\nBf1-d3",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|p|p|p|p|_|p|p|p|\n"+
				 "6|_|_|_|_|p|_|_|_|\n"+
				 "5|_|_|_|_|_|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|B|P|_|_|_|\n"+
				 "2|P|P|P|P|_|P|P|P|\n"+
				 "1|R|N|B|Q|K|_|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 2
				{"d2-d3 d7-d6\nBc1-e3",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|p|p|p|_|p|p|p|p|\n"+
				 "6|_|_|_|p|_|_|_|_|\n"+
				 "5|_|_|_|_|_|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|P|B|_|_|_|\n"+
				 "2|P|P|P|_|P|P|P|P|\n"+
				 "1|R|N|_|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 3
				{"e2-e3 e7-e6\nBf1-d3 Bf8-d6",
				 "8|r|n|b|q|k|_|n|r|\n"+
				 "7|p|p|p|p|_|p|p|p|\n"+
				 "6|_|_|_|b|p|_|_|_|\n"+
				 "5|_|_|_|_|_|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|B|P|_|_|_|\n"+
				 "2|P|P|P|P|_|P|P|P|\n"+
				 "1|R|N|B|Q|K|_|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 4
				{"d2-d3 d7-d6\nBc1-e3 Bc8-e6",
				 "8|r|n|_|q|k|b|n|r|\n"+
				 "7|p|p|p|_|p|p|p|p|\n"+
				 "6|_|_|_|p|b|_|_|_|\n"+
				 "5|_|_|_|_|_|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|P|B|_|_|_|\n"+
				 "2|P|P|P|_|P|P|P|P|\n"+
				 "1|R|N|_|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// EXTRA Test 4
				{"d2-d3 d7-d6\nBc1-e3 Bc8-e6\nBe3-c5 Be6-c4\nBc5-b4 Bc4-b5\nBb4-d2 Bb5-d7",
				 "8|r|n|_|q|k|b|n|r|\n"+
				 "7|p|p|p|b|p|p|p|p|\n"+
				 "6|_|_|_|p|_|_|_|_|\n"+
				 "5|_|_|_|_|_|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|P|_|_|_|_|\n"+
				 "2|P|P|P|B|P|P|P|P|\n"+
				 "1|R|N|_|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// EXTRA long test, from FIDE World Chess Championship 2010
				{"d2-d4 d7-d5\nc2-c4 e7-e6\nNg1-f3 Ng8-f6\nNb1-c3 Bf8-e7\nBc1-g5 h7-h6\n" + 
			     "Bg5-h4 O-O\ne2-e3 Nf6-e4\nBh4xBe7 Qd8xBe7\nRa1-c1 c7-c6\nBf1-e2 Ne4xNc3\n" + 
				 "Rc1xNc3 d5xc4\nBe2xc4 Nb8-d7\nO-O b7-b6\nBc4-d3 c6-c5\nBd3-e4 Ra8-b8\n" + 
			     "Qd1-c2 Nd7-f6\nd4xc5 Nf6xBe4\nQc2xNe4 b6xc5\nQe4-c2 Bc8-b7\nNf3-d2 Rf8-d8\n" +
				 "f2-f3 Bb7-a6\nRf1-f2 Rd8-d7\ng2-g3 Rb8-d8\nKg1-g2 Ba6-d3\nQc2-c1 Bd3-a6\n" +
			     "Rc3-a3 Ba6-b7\nNd2-b3 Rd7-c7\nNb3-a5 Bb7-a8\nNa5-c4 e6-e5\ne3-e4 f7-f5\n" +
				 "e4xf5 e5-e4\nf3xe4 Qe7xe4+\nKg2-h3 Rd8-d4\nNc4-e3 Qe4-e8\ng3-g4 h6-h5\n" +
			     "Kh3-h4 g7-g5+\nf5xg6ep Qe8xg6\nQc1-f1 Rd4xg4+\nKh4-h3 Rc7-e7\nRf2-f8+ Kg8-g7\n" +
				 "Ne3-f5+ Kg7-h7\nRa3-g3 Rg4xRg3+\nh2xRg3 Qg6-g4+\nKh3-h2 Re7-e2+\nKh2-g1 Re2-g2+\n" +
			     "Qf1xRg2 Ba8xQg2\nKg1xBg2 Qg4-e2+\nKg2-h3 c5-c4\na2-a4 a7-a5\nRf8-f6 Kh7-g8\n" +
				 "Nf5-h6+ Kg8-g7\nRf6-b6 Qe2-e4\nKh3-h2 Kg7-h7\nRb6-d6 Qe4-e5\nNh6-f7 Qe5xb2+\n" +
			     "Kh2-h3 Qb2-g7",
                 "8|_|_|_|_|_|_|_|_|\n"+
                 "7|_|_|_|_|_|N|q|k|\n"+
                 "6|_|_|_|R|_|_|_|_|\n"+
                 "5|p|_|_|_|_|_|_|p|\n"+
                 "4|P|_|p|_|_|_|_|_|\n"+
                 "3|_|_|_|_|_|_|P|K|\n"+
                 "2|_|_|_|_|_|_|_|_|\n"+
                 "1|_|_|_|_|_|_|_|_|\n"+
                 "  a b c d e f g h"
                }	
		};
		checkValidTests(tests);
	}
	
	public @Test void testBishopTakes() {
		String[][] tests = {
				// Test 1
				{"e2-e3 f7-f5\nBf1-d3 g7-g6\nBd3xf5",
				 "8|r|n|b|q|k|b|n|r|\n"+
				 "7|p|p|p|p|p|_|_|p|\n"+
				 "6|_|_|_|_|_|_|p|_|\n"+
				 "5|_|_|_|_|_|B|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|_|P|_|_|_|\n"+
				 "2|P|P|P|P|_|P|P|P|\n"+
				 "1|R|N|B|Q|K|_|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Extra Test 1, white bishop take, also test check
				{"e2-e3 f7-f5\nBf1-d3 g7-g6\nBd3xf5 e7-e6\nBf5xg6+ Ke8-e7\nBg6xh7 d7-d5",
                 "8|r|n|b|q|_|b|n|r|\n"+
                 "7|p|p|p|_|k|_|_|B|\n"+
                 "6|_|_|_|_|p|_|_|_|\n"+
                 "5|_|_|_|p|_|_|_|_|\n"+
                 "4|_|_|_|_|_|_|_|_|\n"+
                 "3|_|_|_|_|P|_|_|_|\n"+
                 "2|P|P|P|P|_|P|P|P|\n"+
                 "1|R|N|B|Q|K|_|N|R|\n"+
                 "  a b c d e f g h"
                },
				// Test 2
				{"c2-c4 e7-e5\nc4-c5 Bf8xc5",
				 "8|r|n|b|q|k|_|n|r|\n"+
				 "7|p|p|p|p|_|p|p|p|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|_|_|b|_|p|_|_|_|\n"+
				 "4|_|_|_|_|_|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|P|P|_|P|P|P|P|P|\n"+
				 "1|R|N|B|Q|K|B|N|R|\n"+
				 "  a b c d e f g h"
				},
				// Extra Test 2, black bishop take. also check, and king take
                {"c2-c4 e7-e5\nc4-c5 Bf8xc5\nd2-d4 Bc5xd4\ne2-e3 Bd4xe3\nh2-h4 Be3xf2+\nKe1xBf2",
                 "8|r|n|b|q|k|_|n|r|\n"+
                 "7|p|p|p|p|_|p|p|p|\n"+
                 "6|_|_|_|_|_|_|_|_|\n"+
                 "5|_|_|_|_|p|_|_|_|\n"+
                 "4|_|_|_|_|_|_|_|P|\n"+
                 "3|_|_|_|_|_|_|_|_|\n"+
                 "2|P|P|_|_|_|K|P|_|\n"+
                 "1|R|N|B|Q|_|B|N|R|\n"+
                 "  a b c d e f g h"
                },
				// Test 3
				{"f2-f4 e7-e5\ng2-g3 Bf8-c5\nNb1-c3 Bc5xNg1",
				 "8|r|n|b|q|k|_|n|r|\n"+
				 "7|p|p|p|p|_|p|p|p|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|_|_|_|_|p|_|_|_|\n"+
				 "4|_|_|_|_|_|P|_|_|\n"+
				 "3|_|_|N|_|_|_|P|_|\n"+
				 "2|P|P|P|P|P|_|_|P|\n"+
				 "1|R|_|B|Q|K|B|b|R|\n"+
				 "  a b c d e f g h"
				},
				// Test 4
				{"e2-e4 e7-e5\nBf1-a6 Bf8-a3\nBa6xb7 Ba3xb2",
				 "8|r|n|b|q|k|_|n|r|\n"+
				 "7|p|B|p|p|_|p|p|p|\n"+
				 "6|_|_|_|_|_|_|_|_|\n"+
				 "5|_|_|_|_|p|_|_|_|\n"+
				 "4|_|_|_|_|P|_|_|_|\n"+
				 "3|_|_|_|_|_|_|_|_|\n"+
				 "2|P|b|P|P|_|P|P|P|\n"+
				 "1|R|N|B|Q|K|_|N|R|\n"+
				 "  a b c d e f g h"
				}
		};
		
		checkValidTests(tests);
	}
}
