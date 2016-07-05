package swen221.assignment4.cards.util;

import swen221.assignment4.cards.core.*;

/**
 * Represents an computer player in the game. This class can be overriden with
 * different implementations that use different kinds of A.I. to determine
 * appropriate moves.
 * 
 * @author David J. Pearce
 * 
 */
public abstract class AbstractComputerPlayer {
    protected Player player;

    public AbstractComputerPlayer(Player player) {
        this.player = player;
    }

    /**
     * Use a simple AI to decide which card in hand to play in the given trick.
     * 
     * @param trick
     *            --- the current trick
     * @return --- the card to be played
     */
    abstract public Card getNextCard(Trick trick);
    
    public void setPlayer(Player player) {
        this.player = player;
    }

}
