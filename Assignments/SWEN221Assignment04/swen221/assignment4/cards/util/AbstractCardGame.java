package swen221.assignment4.cards.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import swen221.assignment4.cards.core.*;
import swen221.assignment4.cards.core.Player.Direction;

/**
 * Represents an abstract whist-like card game. This provides a common
 * implementation of the CardGame interface, which the different variations on
 * the game can extend and modify. For example, some variations will score
 * differently. Others will deal different numbers of cards, etc.
 * 
 * @author David J. Pearce
 * 
 */
public abstract class AbstractCardGame implements CardGame, Serializable {

    /**
     * Auto-generated serial version UID
     */
    private static final long serialVersionUID = -8229326102931251094L;

    /**
     * A map of positions around the table to the players in the game.
     */
    protected final Map<Player.Direction, Player> players = new HashMap<Player.Direction, Player>();

    /**
     * Keeps track of the number of tricks each player has won in the current
     * round.
     */
    protected final Map<Player.Direction, Integer> tricks = new HashMap<Player.Direction, Integer>();

    /**
     * Keeps track of the player scores. In some games, this may equal the
     * number of tricks. In others, this may include certain bonuses that were
     * obtained.
     */
    protected final Map<Player.Direction, Integer> scores = new HashMap<Player.Direction, Integer>();

    /**
     * Keep track of which suit is currently trumps. Here, "null" may be used to
     * indicate no trumps.
     */
    protected Card.Suit trumps = Card.Suit.HEARTS;

    /**
     * The current trick being played.
     */
    protected Trick currentTrick;

    public AbstractCardGame() {
        for (Player.Direction d : Player.Direction.values()) {
            players.put(d, new Player(d));
            tricks.put(d, 0);
            scores.put(d, 0);
        }
    }

    // ========================================================
    // Methods required for Cloneable
    // ========================================================

    @Override
    public CardGame clone() {
        ByteArrayOutputStream memoryBuffer = new ByteArrayOutputStream();
        ObjectOutputStream objectOutput = null;
        ObjectInputStream objectInput = null;
        CardGame clone = null;
        try {
            // Serialisation
            objectOutput = new ObjectOutputStream(memoryBuffer);
            objectOutput.writeObject(this);
            objectOutput.flush();
            // De-serialisation
            objectInput = new ObjectInputStream(new ByteArrayInputStream(memoryBuffer.toByteArray()));
            clone = (CardGame) objectInput.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                objectOutput.close();
                objectInput.close();
            } catch (IOException e1) {
                System.err.println("IOException");
            }
        }
        return clone;
    }

    // ========================================================
    // Methods required for CardGame
    // ========================================================

    @Override
    public Player getPlayer(Player.Direction d) {
        return players.get(d);
    }

    @Override
    public Trick getTrick() {
        return currentTrick;
    }

    /**
     * Return the trump suit of the current trick
     * 
     * @return --- the trump suit of the current trick
     */
    public Card.Suit getTrumps() {
        return this.trumps;
    }

    @Override
    public boolean isHandFinished() {
        for (Player.Direction d : Player.Direction.values()) {
            if (players.get(d).getHand().size() > 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Set<Direction> getWinnersOfGame() {
        int maxScore = 0;
        // first, calculate winning score
        for (Player.Direction d : Player.Direction.values()) {
            maxScore = Math.max(maxScore, scores.get(d));
        }

        // second, calculate winners
        HashSet<Direction> winners = new HashSet<Direction>();
        for (Player.Direction d : Player.Direction.values()) {
            if (scores.get(d) == maxScore) {
                winners.add(d);
            }
        }
        return winners;
    }

    @Override
    public Map<Player.Direction, Integer> getTricksWon() {
        return tricks;
    }

    @Override
    public Map<Player.Direction, Integer> getOverallScores() {
        return scores;
    }

    @Override
    public void play(Direction player, Card card) throws IllegalMove {
        Player pl = players.get(player);
        currentTrick.play(pl, card);
    }

    @Override
    public void startRound() {
        // First, decide who the leader is for this round
        Player.Direction d = Player.Direction.NORTH;
        if (currentTrick != null) {
            d = currentTrick.getWinner();
        }
        // Second, start a new trick
        currentTrick = new Trick(d, trumps);
    }

    @Override
    public void endRound() {
        // Score previous round
        Player.Direction winner = currentTrick.getWinner();
        tricks.put(winner, tricks.get(winner) + 1);
    }

    @Override
    public void endHand() {
        // Update scores since we've completed a hand
        scoreHand();
        resetTricksWon();
        // now cycle trumps
        trumps = nextTrumps(currentTrick.getTrumps());
    }

    /**
     * Update the overall score by adding one point for the player who won this
     * trick.
     */
    public void scoreHand() {
        int maxScore = 0;
        // first, calculate winning score
        for (Player.Direction d : Player.Direction.values()) {
            maxScore = Math.max(maxScore, tricks.get(d));
        }
        // second, update winners
        for (Player.Direction d : Player.Direction.values()) {
            if (tricks.get(d) == maxScore) {
                scores.put(d, scores.get(d) + 1);
            }
        }
    }

    // ========================================================
    // Helper methods
    // ========================================================

    /**
     * Reset the score of this trick to 0 for all players
     */
    protected void resetTricksWon() {
        for (Player.Direction d : Player.Direction.values()) {
            tricks.put(d, 0);
        }
    }

    /**
     * Reset the overall score to 0 for all players
     */
    protected void resetOverallScores() {
        for (Player.Direction d : Player.Direction.values()) {
            scores.put(d, 0);
        }
    }

    /**
     * Create a complete deck of 52 cards.
     * 
     * @return --- a list of a complete deck of 52 cards.
     */
    public static List<Card> createDeck() {
        ArrayList<Card> deck = new ArrayList<Card>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                deck.add(new Card(suit, rank));
            }
        }
        return deck;
    }

    /**
     * Determine the next trumps in the usual sequence of Hearts, Clubs,
     * Diamonds, Spades, No Trumps.
     * 
     * @param s
     *            --- current trump suit
     * @return --- the suit that is going to be trump suit in the next trick
     */
    protected static Card.Suit nextTrumps(Card.Suit s) {
        if (s == null) {
            return Card.Suit.HEARTS;
        }
        switch (s) {
        case HEARTS:
            return Card.Suit.CLUBS;
        case CLUBS:
            return Card.Suit.DIAMONDS;
        case DIAMONDS:
            return Card.Suit.SPADES;
        case SPADES:
            return null;
        }
        // dead code!
        return Card.Suit.HEARTS;
    }
}
