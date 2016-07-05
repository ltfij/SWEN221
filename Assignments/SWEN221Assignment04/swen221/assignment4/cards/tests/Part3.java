package swen221.assignment4.cards.tests;

import static org.junit.Assert.fail;
import static swen221.assignment4.cards.core.Card.Rank.*;
import static swen221.assignment4.cards.core.Card.Suit.*;

import org.junit.runners.MethodSorters;

import swen221.assignment4.cards.core.*;
import swen221.assignment4.cards.core.Player.Direction;
import swen221.assignment4.cards.util.AbstractCardGame;
import swen221.assignment4.cards.variations.SingleHandWhist;

import org.junit.FixMethodOrder;
import org.junit.Test;

import java.util.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Part3 {
	
	// In all the tests below, the following deck is used. Each card is dealt to
	// the player indicated in the comment.
	private final Card[] DECK = {
			new Card(HEARTS, TWO),     // NORTH
			new Card(HEARTS, QUEEN),   // EAST 
			new Card(SPADES, ACE),     // SOUTH
			new Card(HEARTS, THREE),   // WEST
			
			new Card(CLUBS, QUEEN),    // NORTH
			new Card(CLUBS, TWO),      // EAST
			new Card(CLUBS, ACE),      // SOUTH
			new Card(CLUBS, THREE),    // WEST
			
		    new Card(DIAMONDS, QUEEN), // NORTH
		    new Card(DIAMONDS, TWO),   // EAST
		    new Card(DIAMONDS, TEN),   // SOUTH
		    new Card(DIAMONDS, THREE), // WEST
		    
			new Card(SPADES, KING),     // NORTH 
			new Card(SPADES, THREE),   // EAST
			new Card(SPADES, QUEEN),   // SOUTH
			new Card(SPADES, FOUR)     // WEST
	};
	
	@Test
	public void testValidClone_1() throws IllegalMove {		
		Card before[] = { 
				new Card(HEARTS, TWO),   // NORTH 
				new Card(HEARTS, QUEEN), // EAST 
				new Card(SPADES, ACE),   // SOUTH
				new Card(HEARTS, THREE)  // WEST
				// EAST WINS
		};
		Card afterOriginal[] = {};
		Card afterClone[] = {};
		int[] scoresOriginal = { 0, 1, 0, 0 };
		int[] scoresClone = { 0, 1, 0, 0 };
		// East to win trick...
		testValidClone(before, afterOriginal, afterClone, scoresOriginal,
				scoresClone);
	}
	
	@Test
	public void testValidClone_2() throws IllegalMove {
		Card[] before = {};
		
		Card[] afterOriginal = { 
				new Card(HEARTS, TWO),   // NORTH 
				new Card(HEARTS, QUEEN), // EAST 
				new Card(SPADES, ACE),   // SOUTH
				new Card(HEARTS, THREE)  // WEST
				// EAST WINS
		};
		Card[] afterClone = { 
				new Card(CLUBS, QUEEN),  // NORTH
				new Card(CLUBS, TWO),    // EAST 
				new Card(CLUBS, ACE),    // SOUTH
				new Card(CLUBS, THREE)   // WEST				
				// SOUTH WINS				
		};
		int[] scoresOriginal = { 0, 1, 0, 0 };
		int[] scoresClone = { 0, 0, 1, 0 };
		// East to win trick in original, south to win in clone...
		testValidClone(before, afterOriginal, afterClone, scoresOriginal,
				scoresClone);
	}
	
	@Test
	public void testValidClone_3() throws IllegalMove {
		Card[] before = { 
				new Card(CLUBS, QUEEN),  // NORTH
				new Card(CLUBS, TWO),    // EAST 
				new Card(CLUBS, ACE),    // SOUTH
				new Card(CLUBS, THREE),  // WEST 
				// SOUTH WINS
		};
		Card[] afterOriginal = { 								
				new Card(SPADES, ACE),     // SOUTH
				new Card(SPADES, FOUR),    // WEST
				new Card(SPADES, KING),    // NORTH 
				new Card(SPADES, THREE),   // EAST
				// SOUTH WINS
		};
		Card[] afterClone = { 				
				new Card(DIAMONDS, TEN),   // SOUTH
			    new Card(DIAMONDS, THREE), // WEST
			    new Card(DIAMONDS, QUEEN), // NORTH
			    new Card(DIAMONDS, TWO)    // EAST			    
				// NORTH WINS
		};
		int[] scoresOriginal = { 0, 0, 2, 0 };
		int[] scoresClone = { 1, 0, 1, 0 };
		// East to win trick in original, south to win in clone...
		testValidClone(before, afterOriginal, afterClone, scoresOriginal,
				scoresClone);
	}
	
	@Test
	public void testValidClone_4() throws IllegalMove {
		Card[] before = { 
				new Card(HEARTS, TWO),   // NORTH 
				new Card(HEARTS, QUEEN), // EAST 
				new Card(SPADES, QUEEN), // SOUTH
				new Card(HEARTS, THREE)  // WEST
				// EAST WINS
		};
		Card[] afterOriginal = { 
				new Card(SPADES, THREE),   // EAST
				new Card(SPADES, ACE),     // SOUTH
				new Card(SPADES, FOUR),    // WEST
				new Card(SPADES, KING),    // NORTH 				
				// SOUTH WINS 
		};
		Card[] afterClone = { 
			    new Card(DIAMONDS, TWO),   // EAST
				new Card(DIAMONDS, TEN),   // SOUTH
			    new Card(DIAMONDS, THREE), // WEST
			    new Card(DIAMONDS, QUEEN), // NORTH			   
				// NORTH WINS
			    new Card(CLUBS, QUEEN),  // NORTH
				new Card(CLUBS, TWO),    // EAST 
				new Card(CLUBS, ACE),    // SOUTH
				new Card(CLUBS, THREE),  // WEST 
				// SOUTH WINS			    
		};
		int[] scoresOriginal = { 0, 1, 1, 0 };
		int[] scoresClone = { 1, 1, 1, 0 };
		// East to win trick in original, south to win in clone...
		testValidClone(before, afterOriginal, afterClone, scoresOriginal,
				scoresClone);
	}
	
	@Test
	public void testValidClone_5() throws IllegalMove {
		
		Card before[] = { 
				new Card(HEARTS, TWO),   // NORTH 				 
		};
		Card afterOriginal[] = { 
				new Card(HEARTS, QUEEN), // EAST 
				new Card(SPADES, ACE),   // SOUTH
				new Card(HEARTS, THREE)  // WEST
				// EAST WINS
		};
		Card afterClone[] = { 
				new Card(HEARTS, QUEEN), // EAST 
				new Card(SPADES, QUEEN), // SOUTH
				new Card(HEARTS, THREE),  // WEST
				// EAST WINS
				new Card(SPADES, THREE),   // EAST
				new Card(SPADES, ACE),     // SOUTH
				new Card(SPADES, FOUR),    // WEST
				new Card(SPADES, KING),    // NORTH 				
				// SOUTH WINS
		};
		int[] scoresOriginal = { 0, 1, 0, 0 };
		int[] scoresClone = { 0, 1, 1, 0 };
		// East to win trick...
		testValidClone(before, afterOriginal, afterClone, scoresOriginal,
				scoresClone);
	}
	
	/**
	 * Play a game upto a given point. Then, clone the game in two and play the
	 * remainder of each differently. Finally, check that the right scores for
	 * each have been calculated.
	 * 
	 * @param before
	 *            Sequence of cards to play up to clone point.
	 * @param afterOriginal
	 *            Sequence of cards to play for original game (after cloning).
	 * @param afterClone
	 *            Sequence of cards to play for cloned game.
	 * @param scoresOriginal
	 *            Expected scores at end of original game
	 * @param scoresCloned
	 *            Expected scores at end of cloned game
	 * @throws IllegalMove
	 */
	private void testValidClone(Card[] before,
			Card[] afterOriginal, Card[] afterClone, int[] scoresOriginal,
			int[] scoresClone) throws IllegalMove {		
		// First, create a game and deal our deck
		SingleHandWhist game = new SingleHandWhist();
		game.deal(Arrays.asList(DECK));
		// Second, play all cards up to the clone point
		playCards(game, 0, before);
		// Third, clone the game
		AbstractCardGame clone = (AbstractCardGame) game.clone();
		// Fourth, play all cards after the clone point
		playCards(game, before.length, afterOriginal);		
		playCards(clone, before.length, afterClone);				
		// Fifth, sanity check the original game score
		Map<Direction, Integer> scores = game.getTricksWon();
		Direction d = Direction.NORTH;
		for (int i = 0; i != 4; ++i) {
			int score = scores.get(d);
			if (score != scoresOriginal[i]) {
				fail("Scores for original game are incorrect (player " + d
						+ " has score " + score + ", should be "
						+ scoresOriginal[i] + ")");
			}
			d = d.next();
		}
		// Sixth, sanity check the cloned game score
		scores = clone.getTricksWon();
		d = Direction.NORTH;
		for (int i = 0; i != 4; ++i) {
			int score = scores.get(d);
			if (score != scoresClone[i]) {
				fail("Scores for cloned game are incorrect (player " + d
						+ " has score " + score + ", should be "
						+ scoresClone[i] + ")");
			}
			d = d.next();
		}
		// Done
	}
	
	/**
	 * Play a set of given cards in the order they occur. It is assumed that all
	 * players have the correct cards to allow this.
	 * 
	 * @param game
	 *            The game we are playing.
	 * @param index
	 *            Gives the index through the entire game that we are starting
	 *            at.
	 * @param cards
	 *            The sequence of cards to be played, in the order they are to
	 *            be played.
	 * @throws IllegalMove
	 */
	private void playCards(CardGame game, int index, Card[] cards)
			throws IllegalMove {

		for (int i = 0; i != cards.length; ++i) {
			if ((index + i) % 4 == 0) {			
				game.startRound();				
			}
			Player.Direction player = game.getTrick().getNextToPlay();			
			game.play(player, cards[i]);
			if ((index + i) % 4 == 3) {							
				game.endRound();				
			}
		}
	}
}
