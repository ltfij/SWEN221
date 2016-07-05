package swen221.assignment4.cards.core;

import java.io.Serializable;

/**
 * Represents a player on the board, which can be either a computer player or a
 * human. Every player has a direction (North, East, South, West) and a hand of
 * cards which they are currently playing.
 * 
 * @author David J. Pearce
 * 
 */
public class Player implements Serializable {

    /**
     * Auto-generated serial version UID
     */
    private static final long serialVersionUID = 1572198327356685283L;

    /**
     * Represents one of the four position on the table (North, East, South and
     * West).
     * 
     * @author David J. Pearce
     * 
     */
    public enum Direction {
        NORTH, EAST, SOUTH, WEST;

        /**
         * Returns the next direction to play after this one (i.e. following a
         * clockwise rotation).
         * 
         * @return --- the next direction
         */
        public Direction next() {
            if (this.equals(NORTH))
                return EAST;
            if (this.equals(EAST))
                return SOUTH;
            if (this.equals(SOUTH))
                return WEST;
            return NORTH;
        }

        /**
         * Returns the previous direction to play before this one (i.e.
         * following a anti-clockwise rotation).
         * 
         * @return --- the previous direction
         */
        public Direction previous() {
            if (this.equals(NORTH))
                return WEST;
            if (this.equals(EAST))
                return NORTH;
            if (this.equals(SOUTH))
                return EAST;
            return SOUTH;
        }
    }

    // the direction of where this player is seated
    private final Direction direction;
    // the hand of cards held by this player
    private Hand hand;

    /**
     * Construct a player in the gien direction
     * 
     * @param direction
     *            --- the direction where the player is seated
     */
    public Player(Direction direction) {
        this.direction = direction;
        this.hand = new Hand();
    }

    /**
     * Get the position in which this player is sitting.
     * 
     * @return --- the position in which this player is sitting.
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Get the current hand of this player.
     * 
     * @return --- the current hand of this player.
     */
    public Hand getHand() {
        return hand;
    }
}
