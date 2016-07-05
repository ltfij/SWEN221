package swen221.assignment4.cards.core;

/**
 * This class represents a card in a trick-taking card game. Its purpose is only
 * to compare between each other in a strategy making process. A card in a
 * trick-taking game has its rank and suit. It also has two flags to indicate
 * whether its suit is lead suit, and whether its suit is trump suit.
 * 
 * @author Hector
 *
 */
public class ComparableCard implements Comparable<ComparableCard> {

    // the underlying card object
    private Card card;
    // whether its suit is lead suit
    private boolean isLead;
    // whether its suit is trump suit
    private boolean isTrump;

    /**
     * Construct a ComparableCard object with a card, the suit of lead, and the
     * suit of trump.
     * 
     * @param card
     *            --- the card that is used to compare
     * @param leadSuit
     *            --- the suit of lead. Note that in the case when the player is
     *            opening the trick, the lead suit is unknown, null is passed
     *            here indicating that the lead suit doesn't matter.
     * @param trumpSuit
     *            --- the suit of trump
     */
    public ComparableCard(Card card, Card.Suit leadSuit, Card.Suit trumpSuit) {
        this.card = card;

        if (this.card.suit() == leadSuit) {
            this.isLead = true;
        } else {
            this.isLead = false;
        }

        if (this.card.suit() == trumpSuit) {
            this.isTrump = true;
        } else {
            this.isTrump = false;
        }
    }

    /**
     * Return the card.
     * 
     * @return --- the card in this object.
     */
    public Card getCard() {
        return card;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((card == null) ? 0 : card.hashCode());
        result = prime * result + (isLead ? 1231 : 1237);
        result = prime * result + (isTrump ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ComparableCard other = (ComparableCard) obj;
        if (card == null) {
            if (other.card != null)
                return false;
        } else if (!card.equals(other.card))
            return false;
        if (isLead != other.isLead)
            return false;
        if (isTrump != other.isTrump)
            return false;
        return true;
    }

    /**
     * This overridden method is the key part in this class. It implements a way
     * of comparing different cards in the content of actual trick. Here whether
     * or not the card is a trump comes as the top priority to compare. Then the
     * second priority is whether or not it is a lead-suit.
     */
    @Override
    public int compareTo(ComparableCard other) {

        // Trump cards always win, so whether it's a trump card come as the
        // first priority
        if (this.isTrump && !other.isTrump) {
            return 1; // trump card always win
        } else if (!this.isTrump && other.isTrump) {
            return -1; // trump card always win
        } else if (this.isTrump && other.isTrump) {
            // both are trump card
            int result = this.card.rank().ordinal() - other.card.rank().ordinal();
            if (result > 0) {
                return 1;
            } else if (result < 0) {
                return -1;
            } else {
                return 0; // unreachable
            }
        } else {
            // neither is trump card, then we have to follow lead, so the lead
            // suit comes as second priority.
            if (this.isLead && !other.isLead) {
                return 1; // lead suit can beat others
            } else if (!this.isLead && other.isLead) {
                return -1; // lead suit can beat others
            } else {
                // both are lead-suit or neither, then we compare their rank
                int result = this.card.rank().ordinal() - other.card.rank().ordinal();
                if (result > 0) {
                    return 1;
                } else if (result < 0) {
                    return -1;
                } else {
                    // same rank, but different suit, then use Card.compareTo()
                    // to sort
                    if (this.card.compareTo(other.card) > 0) {
                        return 1;
                    } else if (this.card.compareTo(other.card) < 0) {
                        return -1;
                    } else {
                        return 0; // unreachable
                    }
                }
            }
        }
    }

}
