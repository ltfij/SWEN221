package swen221.monopoly.testing;

import org.junit.*;
import static org.junit.Assert.*;

import swen221.monopoly.*;
import swen221.monopoly.locations.Location;
import swen221.monopoly.locations.Street;

public class MonopolyTests {
    // this is where you must write your tests; do not alter the package, or the
    // name of this file. An example test is provided for you.

    @Test
    public void testValidBuyProperty_1() {
        // Construct a "mini-game" of Monopoly and with a single player. The
        // player attempts to buy a property. We check that the right amount has
        // been deducted from his/her balance, and that he/she now owns the
        // property and vice-versa.
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Board board = game.getBoard();
            String locationName = "Park Lane";
            int balance = 1500;
            Location location = board.findLocation(locationName);
            Player player = new Player("Dave", Player.Token.ScottishTerrier, balance, location);
            // Player player = setupMockPlayer(game,"Park Lane",1500);
            game.buyProperty(player);
            assertEquals(1150, player.getBalance());
            assertEquals("Park Lane", player.iterator().next().getName());
            Street street = (Street) game.getBoard().findLocation("Park Lane");
            assertEquals(player, street.getOwner());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    /**
     * Setup a mock game of monopoly with a player located at a given location.
     */
    @SuppressWarnings("unused")
    private Player setupMockPlayer(GameOfMonopoly game, String locationName, int balance)
            throws GameOfMonopoly.InvalidMove {
        Board board = game.getBoard();
        Location location = board.findLocation(locationName);
        return new Player("Dave", Player.Token.ScottishTerrier, balance, location);
    }
}
