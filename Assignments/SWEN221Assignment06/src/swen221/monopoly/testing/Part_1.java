package swen221.monopoly.testing;

import org.junit.*;
import static org.junit.Assert.*;

import swen221.monopoly.*;
import swen221.monopoly.locations.Location;

public class Part_1 {
    // this is where you must write your tests; do not alter the package, or the
    // name of this file. An example test is provided for you.

    @Test
    public void testValidBuyProperty_1() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game, "Park Lane", 1500);
            game.buyProperty(player);
            assertEquals(1150, player.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testValidBuyProperty_2() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game, "Water Works", 1500);
            game.buyProperty(player);
            assertEquals(1350, player.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testInvalidBuyProperty_1() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            // Cannot buy chance!
            Player player = setupMockPlayer(game, "Chance", 1500);
            game.buyProperty(player);
            fail("Cannot buy property \"Chance\"");
        } catch (GameOfMonopoly.InvalidMove e) {
            // OK
        }
    }

    @Test
    public void testInvalidBuyProperty_2() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            // Cannot buy same property twice
            Player player = setupMockPlayer(game, "Park Lane", 1500);
            game.buyProperty(player);
            game.buyProperty(player);
        } catch (GameOfMonopoly.InvalidMove e) {
            // OK
        }
    }

    @Test
    public void testInvalidBuyProperty_3() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            // Cannot spend more than your limit!
            Player player = setupMockPlayer(game, "Park Lane", 300);
            game.buyProperty(player);
            fail("Cannot buy property with insufficient funds");
        } catch (GameOfMonopoly.InvalidMove e) {
            // OK
        }
    }

    @Test
    public void testValidSell_1() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game, "Park Lane", 1500);
            game.buyProperty(player);
            game.sellProperty(player, player.getLocation());
            assertEquals(1500, player.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testInvalidSell_1() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game, "Park Lane", 1500);
            game.sellProperty(player, player.getLocation());
            fail("Cannot sell property you don't own!");
        } catch (GameOfMonopoly.InvalidMove e) {

        }
    }

    @Test
    public void testValidMortgage_1() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game, "Bow Street", 1500);
            game.buyProperty(player);
            game.mortgageProperty(player, player.getLocation());
            assertEquals(1410, player.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testInvalidMortgage_1() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game, "Park Lane", 1500);
            game.mortgageProperty(player, player.getLocation());
            fail("Cannot mortgage property you don't own!");
        } catch (GameOfMonopoly.InvalidMove e) {

        }
    }

    @Test
    public void testInvalidMortgage_2() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game, "Park Lane", 1500);
            game.buyProperty(player);
            game.sellProperty(player, player.getLocation());
            game.mortgageProperty(player, player.getLocation());
            fail("Cannot mortgage property you don't own!");
        } catch (GameOfMonopoly.InvalidMove e) {
        }
    }

    @Test
    public void testInvalidMortgage_3() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game, "Park Lane", 1500);
            game.buyProperty(player);
            game.mortgageProperty(player, player.getLocation());
            game.mortgageProperty(player, player.getLocation());
            fail("Cannot mortgage property already mortgaged!");
        } catch (GameOfMonopoly.InvalidMove e) {
        }
    }

    @Test
    public void testValidUnmortgage_1() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game, "Fleet Street", 1500);
            game.buyProperty(player);
            game.mortgageProperty(player, player.getLocation());
            assertEquals(1390, player.getBalance());
            game.unmortgageProperty(player, player.getLocation());
            assertEquals(1269, player.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testInvalidUnmortgage_1() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game, "Park Lane", 1500);
            game.buyProperty(player);
            game.unmortgageProperty(player, player.getLocation());
            fail("Cannot unmortgage property which is not already mortgaged!");
        } catch (GameOfMonopoly.InvalidMove e) {
        }
    }

    @Test
    public void testInvalidUnmortgage_2() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game, "Park Lane", 1500);
            game.unmortgageProperty(player, player.getLocation());
            fail("Cannot unmortgage property which you don't own!");
        } catch (GameOfMonopoly.InvalidMove e) {
        }
    }

    @Test
    public void testInvalidUnmortgage_3() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game, "Park Lane", 350);
            game.buyProperty(player);
            game.mortgageProperty(player, player.getLocation());
            game.unmortgageProperty(player, player.getLocation());
            fail("Cannot unmortgage property with insufficient funds!");
        } catch (GameOfMonopoly.InvalidMove e) {
        }
    }

    @Test
    public void testValidMovePlayer_1() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game, "Park Lane", 1500);
            game.movePlayer(player, 2);
            assertEquals(1500, player.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testValidMove_2() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player1 = setupMockPlayer(game, "The Angel Islington", 1500);
            Player player2 = setupMockPlayer(game, "Go", 1500);
            // Buy property
            game.buyProperty(player1);
            // Move player
            game.movePlayer(player2, 6);
            // Unmortgaged properties collect rent
            assertEquals(1494, player2.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testValidMove_3() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player1 = setupMockPlayer(game, "Old Kent Road", 1500);
            Player player2 = setupMockPlayer(game, "Go", 1500);
            // Buy properties
            game.buyProperty(player1);
            game.movePlayer(player1, 2);
            game.buyProperty(player1);
            // player 1 now owns all properties in the same colour group.
            // Move player 2
            game.movePlayer(player2, 3);
            // Unmortgaged properties collect rent
            assertEquals(1492, player2.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testValidMove_4() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player1 = setupMockPlayer(game, "Electric Company", 1500);
            Player player2 = setupMockPlayer(game, "Go", 1500);
            // Buy property
            game.buyProperty(player1);
            // Move player
            game.movePlayer(player2, 12);
            // Unmortgaged properties collect rent
            assertEquals(1452, player2.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testValidMove_5() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game, "The Angel Islington", 1500);
            // Buy property
            game.buyProperty(player);
            game.movePlayer(player, 12);
            game.movePlayer(player, 12);
            game.movePlayer(player, 12);
            game.movePlayer(player, 5);
            // Don't collect rent from yourself
            assertEquals(1600, player.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testValidMove_6() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player1 = setupMockPlayer(game, "Electric Company", 1500);
            Player player2 = setupMockPlayer(game, "Go", 1500);
            // Round 1
            game.buyProperty(player1);
            game.movePlayer(player1, 12);
            game.movePlayer(player2, 6);
            // Round 2
            game.movePlayer(player1, 4);
            game.buyProperty(player1);
            game.movePlayer(player2, 6);
            // Unmortgaged properties collect rent
            assertEquals(1452, player2.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testValidMove_7() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player1 = setupMockPlayer(game, "Electric Company", 1500);
            Player player2 = setupMockPlayer(game, "Go", 1500);
            // Buy property
            game.buyProperty(player1);
            // Mortgage property
            game.mortgageProperty(player1, player1.getLocation());
            game.movePlayer(player2, 12);
            // Mortgaged properties don't collect rent
            assertEquals(1500, player2.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testValidMove_8() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player1 = setupMockPlayer(game, "Kings Cross Station", 1500);
            Player player2 = setupMockPlayer(game, "Go", 1500);
            // Buy property
            game.buyProperty(player1);
            game.movePlayer(player2, 5);
            // Mortgaged properties don't collect rent
            assertEquals(1475, player2.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testValidMove_9() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player1 = setupMockPlayer(game, "Kings Cross Station", 1500);
            Player player2 = setupMockPlayer(game, "Go", 1500);
            // Buy property
            game.buyProperty(player1);
            game.movePlayer(player1, 10);
            game.buyProperty(player1);
            game.movePlayer(player2, 5);
            // Mortgaged properties don't collect rent
            assertEquals(1450, player2.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testValidMove_10() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player1 = setupMockPlayer(game, "Kings Cross Station", 1500);
            Player player2 = setupMockPlayer(game, "Go", 1500);
            // Buy property
            game.buyProperty(player1);
            game.movePlayer(player1, 10);
            game.buyProperty(player1);
            game.movePlayer(player1, 10);
            game.buyProperty(player1);
            game.movePlayer(player2, 5);
            // Mortgaged properties don't collect rent
            assertEquals(1425, player2.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testValidBuildHouse_1() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player1 = setupMockPlayer(game, "Park Lane", 1500);
            Player player2 = setupMockPlayer(game, "Bond Street", 1500);
            // Buy property
            game.buyProperty(player1);
            game.movePlayer(player1, 2);
            game.buyProperty(player1);
            game.buildHouses(player1, player1.getLocation(), 1);

            game.movePlayer(player2, 5);
            // Mortgaged properties don't collect rent
            assertEquals(1375, player2.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testValidBuildHouse_2() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player1 = setupMockPlayer(game, "Park Lane", 1500);
            Player player2 = setupMockPlayer(game, "Bond Street", 1500);
            // Buy property
            game.buyProperty(player1);
            game.movePlayer(player1, 2);
            game.buyProperty(player1);
            game.buildHouses(player1, player1.getLocation(), 2);

            game.movePlayer(player2, 5);
            // Mortgaged properties don't collect rent
            assertEquals(1350, player2.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testInvalidBuildHouse_1() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player1 = setupMockPlayer(game, "Park Lane", 1500);
            // Buy property
            game.buyProperty(player1);
            game.buildHouses(player1, player1.getLocation(), 2);
            fail("cannot build house on property unless all streets in colour group owned");
        } catch (GameOfMonopoly.InvalidMove e) {

        }
    }

    @Test
    public void testInvalidBuildHouse_2() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player1 = setupMockPlayer(game, "Park Lane", 1500);
            // Buy property
            game.buyProperty(player1);
            game.movePlayer(player1, 2);
            game.buyProperty(player1);
            game.buildHouses(player1, player1.getLocation(), 3);
            game.buildHouses(player1, player1.getLocation(), 3);
            fail("cannot build six houses on one property");
        } catch (GameOfMonopoly.InvalidMove e) {

        }
    }

    @Test
    public void testInvalidBuildHouse_3() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player1 = setupMockPlayer(game, "Park Lane", 400);
            // Buy property
            game.buyProperty(player1);
            game.movePlayer(player1, 2);
            game.buyProperty(player1);
            game.buildHouses(player1, player1.getLocation(), 5);
            fail("cannot build houses one property: insufficient funds");
        } catch (GameOfMonopoly.InvalidMove e) {

        }
    }

    @Test
    public void testValidBuildHotel_1() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player1 = setupMockPlayer(game, "Park Lane", 2000);
            Player player2 = setupMockPlayer(game, "Bond Street", 1500);
            // Buy property
            game.buyProperty(player1);
            game.movePlayer(player1, 2);
            game.buyProperty(player1);
            game.buildHouses(player1, player1.getLocation(), 5);
            game.buildHotel(player1, player1.getLocation());

            game.movePlayer(player2, 5);
            // Mortgaged properties don't collect rent
            assertEquals(1200, player2.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testInvalidBuildHotel_1() {
        GameOfMonopoly game = new GameOfMonopoly();
        Player player1;
        try {
            player1 = setupMockPlayer(game, "Park Lane", 1750);
            // Buy property
            game.buyProperty(player1);
            game.movePlayer(player1, 2);
            game.buyProperty(player1);
            game.buildHouses(player1, player1.getLocation(), 5);
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
            return;
        }
        try {
            game.buildHotel(player1, player1.getLocation());
            fail("cannot build hotel on property: insufficient funds");
        } catch (GameOfMonopoly.InvalidMove e) {

        }
    }

    @Test
    public void testInvalidBuildHotel_2() {
        GameOfMonopoly game = new GameOfMonopoly();
        Player player1;
        try {
            player1 = setupMockPlayer(game, "Park Lane", 2000);
            // Buy property
            game.buyProperty(player1);
            game.movePlayer(player1, 2);
            game.buyProperty(player1);
            game.buildHouses(player1, player1.getLocation(), 5);
            game.buildHotel(player1, player1.getLocation());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
            return;
        }
        try {
            game.buildHotel(player1, player1.getLocation());
            fail("cannot build hotel on property: already has property");
        } catch (GameOfMonopoly.InvalidMove e) {

        }
    }

    @Test
    public void testPassGo_1() {
        GameOfMonopoly game = new GameOfMonopoly();
        try {
            Player player = setupMockPlayer(game, "Park Lane", 1500);
            game.movePlayer(player, 12);
            assertEquals(1700, player.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    /**
     * Setup a mock game of monopoly with a player located at a given location.
     */
    private Player setupMockPlayer(GameOfMonopoly game, String locationName, int balance)
            throws GameOfMonopoly.InvalidMove {
        Board board = game.getBoard();
        Location location = board.findLocation(locationName);
        return new Player("Dave", Player.Token.ScottishTerrier, balance, location);
    }
}
