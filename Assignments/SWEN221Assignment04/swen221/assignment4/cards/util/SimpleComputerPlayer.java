package swen221.assignment4.cards.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import swen221.assignment4.cards.core.Card;
import swen221.assignment4.cards.core.Card.Suit;
import swen221.assignment4.cards.core.Hand;
import swen221.assignment4.cards.core.Player;
import swen221.assignment4.cards.core.Trick;
import swen221.assignment4.cards.core.ComparableCard;

/**
 * Implements a simple computer player who plays the highest card available when
 * the trick can still be won, otherwise discards the lowest card available. In
 * the special case that the player must win the trick (i.e. this is the last
 * card in the trick), then the player conservatively plays the least card
 * needed to win.
 * 
 * @author David J. Pearce
 * 
 */
public class SimpleComputerPlayer extends AbstractComputerPlayer {

    /**
     * Construct a computer Player in the game.
     * 
     * @param player
     *            --- the computer player
     */
    public SimpleComputerPlayer(Player player) {
        super(player);
    }

    @Override
    public Card getNextCard(Trick trick) {

        Hand hand = this.player.getHand();
        Player.Direction leadDirection = trick.getLeadPlayer();
        Card.Suit trumpSuit = trick.getTrumps();

        Card cardToPlay;

        if (leadDirection == this.player.getDirection()) {
            // then AI is leading this trick
            cardToPlay = leadStrategy(hand, trumpSuit);

        } else if (this.player.getDirection() == leadDirection.previous()) {
            // then AI is at the last position to finish this trick.
            // i.e. it's definite to know whether it can win or lose this trick
            Card.Suit leadSuit = trick.getCardPlayed(leadDirection).suit();
            cardToPlay = endStrategy(hand, leadSuit, trumpSuit, trick);

        } else {
            // AI is neither leading nor ending this trick.
            Card.Suit leadSuit = trick.getCardPlayed(leadDirection).suit();
            cardToPlay = normalStrategy(hand, leadSuit, trumpSuit, trick);
        }

        return cardToPlay;
    }

    /**
     * This is the strategy used by the computer player when it is opening a
     * trick. In this case, it doesn't have a lead to follow, i.e. it generally
     * plays the highest <I>ComparableCard</I> in its hand.
     * 
     * @param hand
     *            --- the hand of cards that the computer player has
     * @param trumpSuit
     *            --- the trump suit of this trick
     * @return --- the card that the computer player decided to play
     * 
     * @see swen221.assignment4.cards.core.ComparableCard
     *      #compareTo(ComparableCard)
     */
    private Card leadStrategy(Hand hand, Suit trumpSuit) {
        SortedSet<Card> playableCards = hand.getCards();
        Card cardToPlay = getHighest(playableCards, null, trumpSuit);
        /*
         * In the special case that the AI player might hold the highest card or
         * highest several cards, he'll conservatively re-select the least
         * needed card to play.
         */
        SortedSet<Card> cardsMatchingSelectedSuit = hand.matches(cardToPlay.suit());
        cardToPlay = conservativelySelect(cardToPlay, cardsMatchingSelectedSuit, cardToPlay.suit());

        return cardToPlay;
    }

    /**
     * This is the strategy used by the computer player when it is closing a
     * trick. In this case, it's definite to know whether it can win or lose
     * this trick. In the case that it can win, it conservatively plays the
     * least needed card to win; and in the case that it must lose, it select
     * the lowest sorted <I>ComparableCard</I> to play
     * 
     * @param hand
     *            --- the hand of cards that the computer player has
     * @param leadSuit
     *            --- the lead suit of this trick
     * @param trumpSuit
     *            --- the trump suit of this trick
     * @param trick
     *            --- the current trick
     * @return --- the card that the computer player decided to play
     * 
     * @see swen221.assignment4.cards.core.ComparableCard
     *      #compareTo(ComparableCard)
     */
    private Card endStrategy(Hand hand, Suit leadSuit, Suit trumpSuit, Trick trick) {
        // the highest card ever played in this trick
        Card highestCardPlayed = getHighest(trick.getCardsPlayed(), leadSuit, trumpSuit);
        // a set of cards that matches lead suit
        SortedSet<Card> cardsMatchingLead = hand.matches(leadSuit);
        // a set of cards that matches trump suit
        SortedSet<Card> cardsMatchingTrump = hand.matches(trumpSuit);
        Card cardToPlay;

        if (!cardsMatchingLead.isEmpty()) {
            // get all cards that follow lead and are higher than the highest
            // card ever played
            SortedSet<Card> playableCards = getCardsHigherThan(cardsMatchingLead, highestCardPlayed, leadSuit,
                    trumpSuit);
            if (playableCards.isEmpty()) {
                // if none, then cannot win, play the lowest card that can
                // follow the lead suit
                cardToPlay = getLowest(cardsMatchingLead, leadSuit, trumpSuit);
            } else {
                // if there is card(s) that can win, play the lowest
                cardToPlay = playableCards.first();
            }
        } else if (!cardsMatchingTrump.isEmpty()) {
            // get all cards that match trump and are higher than the highest
            // card ever played
            SortedSet<Card> playableCards = getCardsHigherThan(cardsMatchingTrump, highestCardPlayed, leadSuit,
                    trumpSuit);
            if (playableCards.isEmpty()) {
                // if none, then cannot win, play the lowest in hand
                cardToPlay = getLowest(hand.getCards(), leadSuit, trumpSuit);
            } else {
                // if there is card(s) that can win, play the lowest
                cardToPlay = playableCards.first();
            }
        } else {
            // no card to follow the lead, no trump card, definitely lose.
            // Play the lowest
            cardToPlay = getLowest(hand.getCards(), leadSuit, trumpSuit);
        }

        return cardToPlay;
    }

    /**
     * This is the strategy used by the computer player when it is neither
     * opening nor closing a trick. In this case, the Strategy can be from
     * offensive to defensive. Here we assume that AI is doing offensively, i.e.
     * it bets the highest card has the most possibility to win. Similarly like
     * in the end strategy, in the case that it must lose, it select the lowest
     * sorted <I>ComparableCard</I> to play. But in the case it might still have
     * chance to win, it plays the highest <I>ComparableCard</I>.
     * 
     * @param hand
     *            --- the hand of cards that the computer player has
     * @param leadSuit
     *            --- the lead suit of this trick
     * @param trumpSuit
     *            --- the trump suit of this trick
     * @param trick
     *            --- the current trick
     * @return --- the card that the computer player decided to play
     * 
     * @see swen221.assignment4.cards.core.ComparableCard
     *      #compareTo(ComparableCard)
     */
    private Card normalStrategy(Hand hand, Suit leadSuit, Suit trumpSuit, Trick trick) {
        // the highest card ever played in this trick
        Card highestCardPlayed = getHighest(trick.getCardsPlayed(), leadSuit, trumpSuit);
        // a set of cards that matches lead suit
        SortedSet<Card> cardsMatchingLead = hand.matches(leadSuit);
        // a set of cards that matches trump suit
        SortedSet<Card> cardsMatchingTrump = hand.matches(trumpSuit);
        Card cardToPlay;

        if (!cardsMatchingLead.isEmpty()) {
            // get all cards that follow lead and are higher than the highest
            // card ever played
            SortedSet<Card> playableCards = getCardsHigherThan(cardsMatchingLead, highestCardPlayed, leadSuit,
                    trumpSuit);
            if (playableCards.isEmpty()) {
                // if none, then cannot win, play the lowest card that can
                // follow the lead suit
                cardToPlay = getLowest(cardsMatchingLead, leadSuit, trumpSuit);
            } else {
                // if there is card(s) that can win, play the highest
                cardToPlay = playableCards.last();
                // conservatively re-select the card
                cardToPlay = conservativelySelect(cardToPlay, playableCards, leadSuit);
            }
        } else if (!cardsMatchingTrump.isEmpty()) {
            // get all cards that match trump and are higher than the highest
            // card ever played
            SortedSet<Card> playableCards = getCardsHigherThan(cardsMatchingTrump, highestCardPlayed, leadSuit,
                    trumpSuit);
            if (playableCards.isEmpty()) {
                // if none, then cannot win, play the lowest in hand
                cardToPlay = getLowest(hand.getCards(), leadSuit, trumpSuit);
            } else {
                // if there is card(s) that can win, play the highest
                cardToPlay = playableCards.last();
                // conservatively re-select the card
                cardToPlay = conservativelySelect(cardToPlay, playableCards, trumpSuit);
            }
        } else {
            // no card to follow the lead, no trump card, definitely lose.
            // Play the lowest
            cardToPlay = getLowest(hand.getCards(), leadSuit, trumpSuit);
        }
        return cardToPlay;
    }

    /**
     * This method takes a collection of Card objects in, sort them according to
     * the <I>ComparableCard</I> sorting criteria, and returns the highest one.
     * 
     * @param cards
     *            --- a collection of cards to be choose from
     * @param leadSuit
     *            --- the lead suit of this trick
     * @param trumpSuit
     *            --- the trump suit of this trick
     * @return --- the highest ranked card according to <I>ComparableCard</I>
     *         sorting criteria
     * 
     * @see swen221.assignment4.cards.core.ComparableCard
     *      #compareTo(ComparableCard)
     */
    private Card getHighest(Collection<Card> cards, Suit leadSuit, Suit trumpSuit) {
        // Should never get in here. Just for robustness.
        if (cards.isEmpty()) {
            return null;
        }

        SortedSet<ComparableCard> comparableCards = new TreeSet<>();
        for (Card card : cards) {
            comparableCards.add(new ComparableCard(card, leadSuit, trumpSuit));
        }
        Card highest = comparableCards.last().getCard();
        return highest;
    }

    /**
     * This method takes a collection of Card objects in, sort them according to
     * the <I>ComparableCard</I> sorting criteria, and returns the lowest one.
     * 
     * @param cards
     *            --- a collection of cards to be choose from
     * @param leadSuit
     *            --- the lead suit of this trick
     * @param trumpSuit
     *            --- the trump suit of this trick
     * @return --- the lowest ranked card according to <I>ComparableCard</I>
     *         sorting criteria
     * 
     * @see swen221.assignment4.cards.core.ComparableCard
     *      #compareTo(ComparableCard)
     */
    private Card getLowest(Collection<Card> cards, Suit leadSuit, Suit trumpSuit) {
        // Should never get in here. Just for robustness.
        if (cards.isEmpty()) {
            return null;
        }

        SortedSet<ComparableCard> comparableCards = new TreeSet<>();
        for (Card card : cards) {
            comparableCards.add(new ComparableCard(card, leadSuit, trumpSuit));
        }
        Card lowest = comparableCards.first().getCard();
        return lowest;
    }

    /**
     * This method takes a collection of Card objects in, sort them according to
     * the <I>ComparableCard</I> sorting criteria, and returns a sorted set of
     * Card objects that are higher than the given card according to the sorting
     * criteria in <I>ComparableCard</I>.
     * 
     * @param cards
     *            --- a collection of cards to be choose from
     * @param toBeCompared
     *            --- the card to be compared
     * @param leadSuit
     *            --- the lead suit of this trick
     * @param trumpSuit
     *            --- the trump suit of this trick
     * @return --- the lowest ranked card according to <I>ComparableCard</I>
     *         sorting criteria
     * 
     * @see swen221.assignment4.cards.core.ComparableCard
     *      #compareTo(ComparableCard)
     */
    private SortedSet<Card> getCardsHigherThan(SortedSet<Card> cards, Card toBeCompared, Suit leadSuit,
            Suit trumpSuit) {
        // Should never get in here. Just for robustness.
        if (cards.isEmpty()) {
            return null;
        }

        ComparableCard toBeComparedWrapped = new ComparableCard(toBeCompared, leadSuit, trumpSuit);
        SortedSet<ComparableCard> comparableCards = new TreeSet<>();

        // wrap and compare
        for (Card card : cards) {
            ComparableCard comparableCard = new ComparableCard(card, leadSuit, trumpSuit);
            if (comparableCard.compareTo(toBeComparedWrapped) > 0) {
                comparableCards.add(comparableCard);
            }
        }

        // unwrap and return
        SortedSet<Card> cardsHigher = new TreeSet<>();
        for (ComparableCard tc : comparableCards) {
            cardsHigher.add(tc.getCard());
        }

        return cardsHigher;
    }

    /**
     * This method treats a special case when the player holds the highest card
     * or highest several cards in one suit, then he should conservatively
     * select the least needed card to play. <br>
     * <br>
     * Example: he holds both Heart A, Heart K and Heart Q, then other player
     * cannot play A or K to win him if he plays Q. In this case, Q is the
     * rational choice.<br>
     * <br>
     * Note that this helper method requires that the card in the first argument
     * <I>card</I>, and cards in the second argument <I>playableCards</I>, must
     * be of same suit, and this suit must be consistent with the third argument
     * <I>suit</I>. If any of these requirements is not met, an IllegalArgument
     * exception will be thrown.
     * 
     * @param card
     *            --- the card that need to be checked
     * @param playableCards
     *            --- a collection of cards that the player can re-pick if the
     *            card indeed fit in this special case
     * @param suit
     *            --- the given suit.
     * @return --- the same card if the hand of cards does not fit into this
     *         special case, or another card if it does. The re-picked card is
     *         guaranteed to win the trick as well if the given card can win.
     */
    private Card conservativelySelect(Card card, SortedSet<Card> playableCards, Suit suit) {

        // some sanity check. Shouldn't get into any of these
        if (card.suit() != suit) { // shouldn't get here.
            throw new IllegalArgumentException("card suit should be the given suit.");
        }

        for (Card c : playableCards) {
            if (c.suit() != card.suit()) {
                throw new IllegalArgumentException("playableCards contains card(s) that doesn't match given suit.");
            }
        }

        if (!card.equals(playableCards.last())) { // shouldn't get here.
            throw new IllegalArgumentException("card should be the highest card in playableCards.");
        }

        ArrayList<Card> cardsList = new ArrayList<>(playableCards);
        Collections.sort(cardsList); // Should be unnecessary

        int lastIndex = cardsList.size() - 1;
        int ordinal = 12;
        Card selectedCard = card;

        boolean lastIndexDecreased = false;  // a boolean to record if the last index is decreased.

        while (selectedCard.rank().ordinal() == ordinal) {
            ordinal--;
            lastIndex--;

            lastIndexDecreased = true;

            if (lastIndex < 0) {
                break;
            }
            selectedCard = cardsList.get(lastIndex);
        }

        if (lastIndexDecreased) {
            selectedCard = cardsList.get(lastIndex + 1);
        }

        return selectedCard;
    }

}
