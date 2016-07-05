package swen221.assignment4.cards.tests;

import static org.junit.Assert.fail;
import static swen221.assignment4.cards.core.Card.Rank.*;
import static swen221.assignment4.cards.core.Card.Suit.*;

import org.junit.runners.MethodSorters;

import swen221.assignment4.cards.core.Card;
import swen221.assignment4.cards.core.IllegalMove;
import swen221.assignment4.cards.core.Player;
import swen221.assignment4.cards.core.Trick;
import swen221.assignment4.cards.util.SimpleComputerPlayer;

import org.junit.FixMethodOrder;
import org.junit.Test;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Part4 {

    // ===========================================================================
    // Part 3 --- Simple Computer AI
    // ===========================================================================
    @Test
    public void testSimpleAI_1() throws IllegalMove {
        Card.Suit trumps = HEARTS;
        Card[] played = {};
        Card hand[] = { new Card(HEARTS, TWO), new Card(HEARTS, QUEEN), new Card(SPADES, ACE) };
        // lead strongest (trumps)
        checkCardPlayed(hand[1], trumps, played, hand);
    }

    @Test
    public void testSimpleAI_2() throws IllegalMove {
        Card.Suit trumps = null;
        Card[] played = {};
        Card hand[] = { new Card(DIAMONDS, ACE), new Card(HEARTS, TWO), new Card(HEARTS, QUEEN) };
        // lead strongest (no trumps)
        checkCardPlayed(hand[0], trumps, played, hand);
    }

    @Test
    public void testSimpleAI_3() throws IllegalMove {
        Card.Suit trumps = CLUBS;
        Card[] played = {};
        Card hand[] = { new Card(DIAMONDS, ACE), new Card(HEARTS, TWO), new Card(HEARTS, QUEEN),
                new Card(SPADES, TEN) };
        // lead strongest (trumps not available)
        checkCardPlayed(hand[0], trumps, played, hand);
    }

    @Test
    public void testSimpleAI_4() throws IllegalMove {
        Card.Suit trumps = CLUBS;
        Card[] played = {};
        Card hand[] = { new Card(DIAMONDS, ACE), new Card(HEARTS, TWO), new Card(HEARTS, QUEEN),
                new Card(SPADES, ACE) };
        // lead strongest (trumps not available)
        checkCardPlayed(hand[3], trumps, played, hand);
    }

    @Test
    public void testSimpleAI_5() throws IllegalMove {
        Card.Suit trumps = HEARTS;
        Card[] played = { new Card(HEARTS, ACE), new Card(HEARTS, KING) };
        Card hand[] = { new Card(HEARTS, TWO), new Card(HEARTS, QUEEN) };
        // follow suit (trumps), discard
        checkCardPlayed(hand[0], trumps, played, hand);
    }

    @Test
    public void testSimpleAI_6() throws IllegalMove {
        Card.Suit trumps = HEARTS;
        Card[] played = { new Card(CLUBS, ACE), new Card(CLUBS, KING) };
        Card hand[] = { new Card(HEARTS, TWO), new Card(CLUBS, QUEEN), new Card(CLUBS, THREE), new Card(CLUBS, TEN) };
        // follow suit (not trumps), discard
        checkCardPlayed(hand[2], trumps, played, hand);
    }

    @Test
    public void testSimpleAI_7() throws IllegalMove {
        Card.Suit trumps = null;
        Card[] played = { new Card(CLUBS, ACE), new Card(CLUBS, KING) };
        Card hand[] = { new Card(HEARTS, TWO), new Card(CLUBS, QUEEN), new Card(CLUBS, THREE), new Card(CLUBS, TEN) };
        // follow suit (no trumps), discard
        checkCardPlayed(hand[2], trumps, played, hand);
    }

    @Test
    public void testSimpleAI_8() throws IllegalMove {
        Card.Suit trumps = CLUBS;
        Card[] played = { new Card(CLUBS, FIVE), new Card(CLUBS, SEVEN) };
        Card hand[] = { new Card(HEARTS, TWO), new Card(CLUBS, QUEEN), new Card(CLUBS, TEN) };
        // follow suit (trumps), try and win with queen
        checkCardPlayed(hand[1], trumps, played, hand);
    }

    @Test
    public void testSimpleAI_9() throws IllegalMove {
        Card.Suit trumps = HEARTS;
        Card[] played = { new Card(CLUBS, FIVE), new Card(CLUBS, SEVEN) };
        Card hand[] = { new Card(HEARTS, TWO), new Card(CLUBS, QUEEN), new Card(CLUBS, TEN) };
        // follow suit (not trumps), try and win with queen
        checkCardPlayed(hand[1], trumps, played, hand);
    }

    @Test
    public void testSimpleAI_10() throws IllegalMove {
        Card.Suit trumps = null;
        Card[] played = { new Card(CLUBS, FIVE), new Card(CLUBS, SEVEN) };
        Card hand[] = { new Card(HEARTS, TWO), new Card(CLUBS, QUEEN), new Card(CLUBS, TEN) };
        // follow suit (no trumps), try and win with queen
        checkCardPlayed(hand[1], trumps, played, hand);
    }

    @Test
    public void testSimpleAI_11() throws IllegalMove {
        Card.Suit trumps = DIAMONDS;
        Card[] played = { new Card(DIAMONDS, ACE), new Card(CLUBS, KING) };
        Card hand[] = { new Card(HEARTS, TWO), new Card(SPADES, QUEEN) };
        // can't follow suit (trumps), discard
        checkCardPlayed(hand[0], trumps, played, hand);
    }

    @Test
    public void testSimpleAI_12() throws IllegalMove {
        Card.Suit trumps = DIAMONDS;
        Card[] played = { new Card(CLUBS, ACE), new Card(CLUBS, KING) };
        Card hand[] = { new Card(HEARTS, TWO), new Card(SPADES, QUEEN) };
        // can't follow suit (not trumps), discard
        checkCardPlayed(hand[0], trumps, played, hand);
    }

    @Test
    public void testSimpleAI_13() throws IllegalMove {
        Card.Suit trumps = null;
        Card[] played = { new Card(SPADES, QUEEN), new Card(CLUBS, KING) };
        Card hand[] = { new Card(HEARTS, TWO), new Card(SPADES, ACE) };
        // can't follow suit (no trumps), win with ace
        checkCardPlayed(hand[1], trumps, played, hand);
    }

    @Test
    public void testSimpleAI_14() throws IllegalMove {
        Card.Suit trumps = DIAMONDS;
        Card[] played = { new Card(DIAMONDS, ACE), new Card(CLUBS, KING) };
        Card hand[] = { new Card(HEARTS, TWO), new Card(SPADES, TWO) };
        // can't follow suit (trumps), discard
        checkCardPlayed(hand[0], trumps, played, hand);
    }

    @Test
    public void testSimpleAI_15() throws IllegalMove {
        Card.Suit trumps = HEARTS;
        Card[] played = { new Card(HEARTS, TEN), new Card(HEARTS, FIVE), new Card(HEARTS, NINE) };
        Card hand[] = { new Card(HEARTS, JACK), new Card(SPADES, ACE), new Card(HEARTS, KING) };
        // follow suit (trumps), must win with jack
        checkCardPlayed(hand[0], trumps, played, hand);
    }

    @Test
    public void testSimpleAI_16() throws IllegalMove {
        Card.Suit trumps = HEARTS;
        Card[] played = { new Card(CLUBS, TWO), new Card(SPADES, FIVE), new Card(DIAMONDS, NINE) };
        Card hand[] = { new Card(CLUBS, FIVE), new Card(HEARTS, ACE), new Card(CLUBS, KING) };
        // follow suit (not trumps), must win with five
        checkCardPlayed(hand[0], trumps, played, hand);
    }

    @Test
    public void testSimpleAI_17() throws IllegalMove {
        Card.Suit trumps = null;
        Card[] played = { new Card(CLUBS, TWO), new Card(SPADES, FIVE), new Card(SPADES, NINE) };
        Card hand[] = { new Card(CLUBS, FIVE), new Card(HEARTS, ACE), new Card(CLUBS, KING) };
        // follow suit (no trumps), must win with five
        checkCardPlayed(hand[0], trumps, played, hand);
    }

    @Test
    public void testSimpleAI_18() throws IllegalMove {
        Card.Suit trumps = HEARTS;
        Card[] played = { new Card(CLUBS, TWO), new Card(SPADES, FIVE), new Card(SPADES, NINE) };
        Card hand[] = { new Card(HEARTS, TEN), new Card(HEARTS, ACE), new Card(SPADES, KING) };
        // can't follow suit (not trumps), must win with ten
        checkCardPlayed(hand[0], trumps, played, hand);
    }

    @Test
    public void testSimpleAI_19() throws IllegalMove {
        Card.Suit trumps = HEARTS;
        Card[] played = { new Card(CLUBS, TWO), new Card(HEARTS, JACK), new Card(SPADES, NINE) };
        Card hand[] = { new Card(HEARTS, TEN), new Card(HEARTS, ACE), new Card(SPADES, KING), new Card(HEARTS, QUEEN) };
        // can't follow suit (not trumps), must win with Queen
        checkCardPlayed(hand[3], trumps, played, hand);
    }

    private void checkCardPlayed(Card expected, Card.Suit trumps, Card[] played, Card[] hand) throws IllegalMove {

        // First, calculate AI's direction and setup Trick. We're assuming that
        // NORTH always leads.
        Trick trick = new Trick(Player.Direction.NORTH, trumps);
        Player.Direction d = Player.Direction.NORTH;
        for (int i = 0; i != played.length; ++i) {
            Player ghost = new Player(d);
            ghost.getHand().add(played[i]);
            trick.play(ghost, played[i]);
            d = d.next();
        }
        Player computerPlayer = new Player(d);
        // Second, set up AI's hand
        for (Card c : hand) {
            computerPlayer.getHand().add(c);
        }
        // Third, check card that player will pick
        SimpleComputerPlayer ai = new SimpleComputerPlayer(computerPlayer);
        Card next = ai.getNextCard(trick);
        if (next.suit() != expected.suit() || next.rank() != expected.rank()) {
            fail("Invalid card drawn by AI (was " + next + ", expected " + expected + ")");
        }
    }
}
