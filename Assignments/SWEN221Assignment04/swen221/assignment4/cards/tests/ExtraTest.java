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
public class ExtraTest {

    @Test
    public void testSimpleAI_1() throws IllegalMove {
        Card.Suit trumps = null;
        Card[] played = {};
        Card hand[] = { new Card(DIAMONDS, ACE), new Card(DIAMONDS, KING), new Card(SPADES, KING) };
        checkCardPlayed(hand[1], trumps, played, hand);
    }

    @Test
    public void testSimpleAI_2() throws IllegalMove {
        Card.Suit trumps = HEARTS;
        Card[] played = {};
        Card hand[] = { new Card(HEARTS, ACE), new Card(HEARTS, KING), new Card(SPADES, KING) };
        checkCardPlayed(hand[1], trumps, played, hand);
    }

    @Test
    public void testSimpleAI_3() throws IllegalMove {
        Card.Suit trumps = null;
        Card[] played = {new Card(DIAMONDS, TEN)};
        Card hand[] = { new Card(DIAMONDS, ACE), new Card(DIAMONDS, KING), new Card(SPADES, KING) };
        checkCardPlayed(hand[1], trumps, played, hand);
    }
    
    @Test
    public void testSimpleAI_4() throws IllegalMove {
        Card.Suit trumps = HEARTS;
        Card[] played = {new Card(CLUBS, TWO), new Card(CLUBS, TEN)};
        Card hand[] = { new Card(HEARTS, ACE), new Card(HEARTS, KING), new Card(SPADES, KING) };
        checkCardPlayed(hand[1], trumps, played, hand);
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
