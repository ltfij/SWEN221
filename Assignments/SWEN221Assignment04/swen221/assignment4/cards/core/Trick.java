package swen221.assignment4.cards.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

/**
 * Represents a trick being played. This includes the cards that have been
 * played so far, as well as what the suit of trumps is for this trick.
 * 
 * @author David J. Pearce
 * 
 */
public class Trick implements Serializable {
    /**
     * Auto-generated serial version UID
     */
    private static final long serialVersionUID = 3663083141828895138L;
    // four cards played by four player respectively
    private Card[] cards = new Card[4];
    // the lead direction
    private Player.Direction lead;
    // the trump suit of this trick
    private Card.Suit trumps;

    /**
     * Construct a new trick with a given lead player and suit of trumps.
     * 
     * @param lead
     *            --- lead player for this trick.
     * @param trumps
     *            --- maybe null if no trumps.
     */
    public Trick(Player.Direction lead, Card.Suit trumps) {
        this.lead = lead;
        this.trumps = trumps;
    }

    /**
     * Determine who the lead player for this trick is.
     * 
     * @return --- who the lead player for this trick is.
     */
    public Player.Direction getLeadPlayer() {
        return lead;
    }

    /**
     * Determine which suit are trumps for this trick, or null if there are no
     * trumps.
     * 
     * @return --- which suit are trumps for this trick, or null if there are no
     *         trumps.
     */
    public Card.Suit getTrumps() {
        return trumps;
    }

    /**
     * Get the list of cards played so far in the order they were played.
     * 
     * @return --- the list of cards played so far
     */
    public ArrayList<Card> getCardsPlayed() {
        ArrayList<Card> cs = new ArrayList<Card>();
        for (int i = 0; i != 4; ++i) {
            if (cards[i] != null) {
                cs.add(cards[i]);
            } else {
                break;
            }
        }
        return cs;
    }

    /**
     * Get the card played by a given player, or null if that player has yet to
     * play.
     * 
     * @param p
     *            --- player
     * @return --- the card played by a given player
     */
    public Card getCardPlayed(Player.Direction p) {
        Player.Direction player = lead;
        for (int i = 0; i != 4; ++i) {
            if (player.equals(p)) {
                return cards[i];
            }
            player = player.next();
        }
        // dead code
        return null;
    }

    /**
     * Determine the next player to play in this trick.
     * 
     * @return --- the next player to play in this trick.
     */
    public Player.Direction getNextToPlay() {
        Player.Direction dir = lead;
        for (int i = 0; i != 4; ++i) {
            if (cards[i] == null) {
                return dir;
            }
            dir = dir.next();
        }
        return null;
    }

    /**
     * Determine the winning player for this trick. This requires looking to see
     * which player led the highest card that followed suit; or, was a trump.
     * 
     * @return --- the winning player for this trick
     */
    public Player.Direction getWinner() {
        Player.Direction player = lead;
        Player.Direction winningPlayer = null;
        Card winningCard = cards[0];
        for (int i = 0; i != 4; ++i) {
            if (cards[i].suit() == winningCard.suit() && cards[i].compareTo(winningCard) >= 0) {
                winningPlayer = player;
                winningCard = cards[i];
            } else if (trumps != null && cards[i].suit() == trumps && winningCard.suit() != trumps) {
                // in this case, the winning card is a trump
                winningPlayer = player;
                winningCard = cards[i];
            }
            player = player.next();
        }
        return winningPlayer;
    }

    /**
     * Player attempts to play a card. This method checks that the given player
     * is entitled to play, and that the played card follows suit. If either of
     * these are not true, it throws an IllegalMove exception.
     * 
     * @param p
     *            --- the player who is to play the given card
     * @param c
     *            --- the card to be played
     * @throws IllegalMove
     *             -- if playing the card is illegal, an IllegalMove exception
     *             is thrown
     */
    public void play(Player p, Card c) throws IllegalMove {
        // the player cannot play a card that he doesn't have
        if (!p.getHand().contains(c)) {
            throw new IllegalMove(
                    "The player at " + p.getDirection().toString() + " doesn't have " + c.toString() + " to play.");
        }

        // the player can only play a card when it's his turn
        if (getNextToPlay() != p.getDirection()) {
            throw new IllegalMove("It's not the turn for the player at " + p.getDirection().toString() + "to play.");
        }

        /*
         * if the player is not the lead, and he has at least a card that he can
         * play to follow the lead, and he did not play that card, he violates
         * the rule.
         */
        if (p.getDirection() != lead) {
            Card.Suit leadSuit = getCardPlayed(lead).suit();
            Set<Card> cardsMatchesLead = p.getHand().matches(leadSuit);
            if (!cardsMatchesLead.isEmpty() && !cardsMatchesLead.contains(c)) {
                throw new IllegalMove(
                        "The player at " + p.getDirection().toString() + " doesn't follow the lead suit.");
            }
        }

        // Finally, play the card.
        for (int i = 0; i != 4; ++i) {
            if (cards[i] == null) {
                cards[i] = c;
                p.getHand().remove(c);
                break;
            }
        }
    }
}
