package swen221.monopoly.testing;

import static org.junit.Assert.*;
import org.junit.Test;

import swen221.monopoly.Board;
import swen221.monopoly.GameOfMonopoly;
import swen221.monopoly.GameOfMonopoly.InvalidMove;
import swen221.monopoly.Player;
import swen221.monopoly.locations.ColourGroup;
import swen221.monopoly.locations.Location;
import swen221.monopoly.locations.Property;
import swen221.monopoly.locations.Station;
import swen221.monopoly.locations.Street;
import swen221.monopoly.locations.Utility;

public class NewTest {

    public static final int BIG_FUND = 10000;
    // 22 streets
    public static final String[] STREETS = { "Old Kent Road", "Whitechapel Road", "The Angel Islington",
            "Euston Road", "Pentonville", "Pall Mall", "Whitehall", "Northumberland Ave", "Bow Street",
            "Marlborough Street", "Vine Street", "The Strand", "Fleet Street", "Trafalgar Square",
            "Leceister Square", "Conventry Street", "Picadilly Circus", "Regent Street", "Oxford Street",
            "Bond Street", "Park Lane", "Mayfair" };

    // special areas
    public static final String[] SPECIALS = { "Go", "Community Chest", "Income Tax", "Chance", "Jail",
            "Free Parking", "Goto Jail", "Super Tax" };

    // Utility companies
    public static final String[] UTILITIES = { "Electric Company", "Water Works" };

    // Stations
    public static final String[] STATIONS = { "Kings Cross Station", "Marylebone Station",
            "Fenchurch St. Station", "Livepool St. Station" };

    /**
     * Test moving players
     */
    @Test
    public void test01_move() {
        int steps = 1;
        GameOfMonopoly game = new GameOfMonopoly();
        String locationName = "Go";
        while (steps < 40) {
            try {
                Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                Board board = game.getBoard();
                Location location = board.findLocation(locationName);
                game.movePlayer(player, steps);
                assertTrue(
                        player.getName() + "'s " + player.getToken() + " should lands on "
                                + player.getLocation().getName(),
                        player.getLocation().equals(board.findLocation(location, steps)));
            } catch (InvalidMove e) {
                fail("player should move " + steps + " correctly");
            }
            steps++;
        }
    }

    /**
     * test moving past "Go", should get 200 dollars.
     */
    @Test
    public void test02_movePastGo() {
        int steps = 45;
        GameOfMonopoly game = new GameOfMonopoly();
        String locationName = "Go";
        try {
            Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
            Board board = game.getBoard();
            Location location = board.findLocation(locationName);
            game.movePlayer(player, steps);
            assertTrue(
                    player.getName() + "'s " + player.getToken() + " should lands on "
                            + player.getLocation().getName(),
                    player.getLocation().equals(board.findLocation(location, steps)));
            assertEquals(BIG_FUND + 200, player.getBalance());
        } catch (InvalidMove e) {
            fail("player should move " + steps + " correctly");
        }
    }

    /**
     * Test collecting rent from a property owned but without its colour group owned.
     */
    @Test
    public void test03_collectingRent() {
        int steps = 1;
        while (steps < 40) {
            GameOfMonopoly game = new GameOfMonopoly();
            String locationName = "Go";
            Board board = game.getBoard();
            Location start = board.findLocation("Go");
            try {
                Player player1 = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                Player player2 = setupMockPlayer(game, locationName, "Roxy", BIG_FUND);
                game.movePlayer(player1, steps);
                Location location = board.findLocation(start, steps);
                if (location instanceof Street || location instanceof Station
                        || location instanceof Utility) {
                    game.buyProperty(player1);
                    game.movePlayer(player2, steps);
                    Property property = (Property) location;
                    assertEquals(player1, property.getOwner());
                    assertEquals(property.getName() + " " + steps,
                            BIG_FUND - property.getPrice() + property.getRent(steps), player1.getBalance());
                    assertEquals(BIG_FUND - property.getRent(steps), player2.getBalance());
                }
            } catch (GameOfMonopoly.InvalidMove e) {
                fail("collecting rent failed");
            }
            steps++;
        }
    }

    /**
     * Test collecting double rent when the owner of this property owns the whole group as well.
     */
    @Test
    public void test32_collectingDoubleRent() {

        GameOfMonopoly game = new GameOfMonopoly();
        String locationName_1 = "Park Lane";

        try {
            int steps = 2;
            Player player_1 = setupMockPlayer(game, locationName_1, "Hector", BIG_FUND);
            Player player_2 = setupMockPlayer(game, locationName_1, "Roxy", BIG_FUND);
            game.buyProperty(player_1);
            game.movePlayer(player_1, steps);
            game.buyProperty(player_1);
            game.movePlayer(player_2, steps);

            assertEquals(BIG_FUND - 350 - 400 + 50 * 2, player_1.getBalance());
            assertEquals(BIG_FUND - 50 * 2, player_2.getBalance());

        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.toString());
        }
    }

    /**
     * Test a valid buy
     */
    @Test
    public void test01_valid_buyProperty() {
        try {
            GameOfMonopoly game = new GameOfMonopoly();

            // buy streets
            for (int i = 0; i < STREETS.length; i++) {
                String locationName = STREETS[i];
                Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                Location location = game.getBoard().findLocation(locationName);
                Street street = (Street) location;
                game.buyProperty(player);
                assertTrue(location.getName() + " should have an owner", location.hasOwner());
                assertEquals(BIG_FUND - street.getPrice(), player.getBalance());
                assertEquals(locationName, player.iterator().next().getName());
                assertEquals(player, street.getOwner());
            }

            // buy utility companies
            for (int i = 0; i < UTILITIES.length; i++) {
                String locationName = UTILITIES[i];
                Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                Location location = game.getBoard().findLocation(locationName);
                Utility util = (Utility) location;
                game.buyProperty(player);
                assertTrue(location.getName() + " should have an owner", location.hasOwner());
                assertEquals(BIG_FUND - util.getPrice(), player.getBalance());
                assertEquals(locationName, player.iterator().next().getName());
                assertEquals(player, util.getOwner());
            }

            // buy stations
            for (int i = 0; i < STATIONS.length; i++) {
                String locationName = STATIONS[i];
                Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                Location location = game.getBoard().findLocation(locationName);
                Station station = (Station) location;
                game.buyProperty(player);
                assertTrue(location.getName() + " should have an owner", location.hasOwner());
                assertEquals(BIG_FUND - station.getPrice(), player.getBalance());
                assertEquals(locationName, player.iterator().next().getName());
                assertEquals(player, station.getOwner());
            }

        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test invalid purchase of special areas
     */
    @Test
    public void test02_invalid_buySpecialArea() {

        GameOfMonopoly game = new GameOfMonopoly();
        for (int i = 0; i < SPECIALS.length; i++) {
            String locationName = SPECIALS[i];
            try {
                Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                game.buyProperty(player);
                fail("Shouldn't be able to buy this property");
            } catch (GameOfMonopoly.InvalidMove e) {
            }
        }
    }

    /**
     * Test that the player shouldn't buy other's property
     */
    @Test
    public void test03_invalid_buyOthersProperty() {

        GameOfMonopoly game = new GameOfMonopoly();
        // streets
        for (int i = 0; i < STREETS.length; i++) {
            String locationName = STREETS[i];
            try {
                Player player1 = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                Player player2 = setupMockPlayer(game, locationName, "Roxy", BIG_FUND);
                game.buyProperty(player1);
                game.buyProperty(player2);
                fail("Shouldn't be able to buy this property");
            } catch (GameOfMonopoly.InvalidMove e) {
            }
        }
        // utility
        for (int i = 0; i < UTILITIES.length; i++) {
            String locationName = UTILITIES[i];
            try {
                Player player1 = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                Player player2 = setupMockPlayer(game, locationName, "Roxy", BIG_FUND);
                game.buyProperty(player1);
                game.buyProperty(player2);
                fail("Shouldn't be able to buy this property");
            } catch (GameOfMonopoly.InvalidMove e) {
            }
        }
        // stations
        for (int i = 0; i < STATIONS.length; i++) {
            String locationName = STATIONS[i];
            try {
                Player player1 = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                Player player2 = setupMockPlayer(game, locationName, "Roxy", BIG_FUND);
                game.buyProperty(player1);
                game.buyProperty(player2);
                fail("Shouldn't be able to buy this property");
            } catch (GameOfMonopoly.InvalidMove e) {
            }
        }
    }

    /**
     * Test that the player shouldn't buy any property with insufficient fund
     */
    @Test
    public void test04_invalid_buyWithoutFund() {
        GameOfMonopoly game = new GameOfMonopoly();
        String locationName = "Park Lane";
        try {
            Player player = setupMockPlayer(game, locationName, "Hector", 0);
            game.buyProperty(player);
            fail("Shouldn't be able to buy this property");
        } catch (GameOfMonopoly.InvalidMove e) {
        }
    }

    /**
     * Test a valid selling
     */
    @Test
    public void test05_valid_sellProperty() {

        GameOfMonopoly game = new GameOfMonopoly();
        // streets
        for (int i = 0; i < STREETS.length; i++) {
            String locationName = STREETS[i];
            try {
                Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                game.buyProperty(player);
                Location location = game.getBoard().findLocation(locationName);
                game.sellProperty(player, location);
                assertTrue(location.getName() + " shouldn't have an owner", !location.hasOwner());
                assertEquals(BIG_FUND, player.getBalance());
                assertFalse(player.getName() + " shouldn't have this property",
                        player.getPortfolio().contains(location));
            } catch (GameOfMonopoly.InvalidMove e) {
                fail(e.getMessage());
            }
        }
        // utility
        for (int i = 0; i < UTILITIES.length; i++) {
            String locationName = UTILITIES[i];
            try {
                Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                game.buyProperty(player);
                Location location = game.getBoard().findLocation(locationName);
                game.sellProperty(player, location);
                assertTrue(location.getName() + " shouldn't have an owner", !location.hasOwner());
                assertEquals(BIG_FUND, player.getBalance());
                assertFalse(player.getName() + " shouldn't have this property",
                        player.getPortfolio().contains(location));
            } catch (GameOfMonopoly.InvalidMove e) {
                fail(e.getMessage());
            }
        }
        // stations
        for (int i = 0; i < STATIONS.length; i++) {
            String locationName = STATIONS[i];
            try {
                Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                game.buyProperty(player);
                Location location = game.getBoard().findLocation(locationName);
                game.sellProperty(player, location);
                assertTrue(location.getName() + " shouldn't have an owner", !location.hasOwner());
                assertEquals(BIG_FUND, player.getBalance());
                assertFalse(player.getName() + " shouldn't have this property",
                        player.getPortfolio().contains(location));
            } catch (GameOfMonopoly.InvalidMove e) {
                fail(e.getMessage());
            }
        }
    }

    /**
     * Test that the player shouldn't sell special areas
     */
    @Test
    public void test06_invalid_sellSpecialArea() {
        try {
            GameOfMonopoly game = new GameOfMonopoly();
            String locationName = "Community Chest";
            Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
            Location location = game.getBoard().findLocation("Community Chest");
            game.sellProperty(player, location);
            fail("Cannot sell a public property");
        } catch (GameOfMonopoly.InvalidMove e) {
        }
    }

    /**
     * Test that the player shouldn't sell other's property
     */
    @Test
    public void test07_invalid_sellOthersProperty() {
        try {
            GameOfMonopoly game = new GameOfMonopoly();
            String locationName = "Park Lane";
            Player player1 = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
            Player player2 = setupMockPlayer(game, locationName, "Roxy", BIG_FUND);
            Location location = game.getBoard().findLocation(locationName);
            game.buyProperty(player1);
            game.sellProperty(player2, location);
            fail("Cannot sell a property which belongs to someone else");
        } catch (GameOfMonopoly.InvalidMove e) {
        }
    }

    /**
     * Test a valid mortgage
     */
    @Test
    public void test09_valid_mortgage() {

        try {
            GameOfMonopoly game = new GameOfMonopoly();

            // mortgage streets
            for (int i = 0; i < STREETS.length; i++) {
                String locationName = STREETS[i];
                Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                Location location = game.getBoard().findLocation(locationName);
                Street street = (Street) location;
                game.buyProperty(player);
                game.mortgageProperty(player, location);
                assertTrue(location.getName() + " should have an owner", location.hasOwner());
                assertTrue(location.getName() + " should be mortgaged", street.isMortgaged());
                assertEquals(BIG_FUND - street.getPrice() + street.getPrice() / 2, player.getBalance());
                assertEquals(locationName, player.iterator().next().getName());
                assertEquals(player, street.getOwner());
            }

            // mortgage utility companies
            for (int i = 0; i < UTILITIES.length; i++) {
                String locationName = UTILITIES[i];
                Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                Location location = game.getBoard().findLocation(locationName);
                Utility util = (Utility) location;
                game.buyProperty(player);
                game.mortgageProperty(player, location);
                assertTrue(location.getName() + " should have an owner", location.hasOwner());
                assertTrue(location.getName() + " should be mortgaged", util.isMortgaged());
                assertEquals(BIG_FUND - util.getPrice() + util.getPrice() / 2, player.getBalance());
                assertEquals(locationName, player.iterator().next().getName());
                assertEquals(player, util.getOwner());
            }

            // mortgage stations
            for (int i = 0; i < STATIONS.length; i++) {
                String locationName = STATIONS[i];
                Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                Location location = game.getBoard().findLocation(locationName);
                Station station = (Station) location;
                game.buyProperty(player);
                game.mortgageProperty(player, location);
                assertTrue(location.getName() + " should have an owner", location.hasOwner());
                assertTrue(location.getName() + " should be mortgaged", station.isMortgaged());
                assertEquals(BIG_FUND - station.getPrice() + station.getPrice() / 2, player.getBalance());
                assertEquals(locationName, player.iterator().next().getName());
                assertEquals(player, station.getOwner());
            }

        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }

    }

    /**
     * Test that the player shouldn't mortgage a special area
     */
    @Test
    public void test10_invalid_mortgageSpecialArea() {
        GameOfMonopoly game = new GameOfMonopoly();
        for (int i = 0; i < SPECIALS.length; i++) {
            String locationName = SPECIALS[i];
            Board board = game.getBoard();
            Location location = board.findLocation(locationName);
            try {
                Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                game.mortgageProperty(player, location);
                fail("Shouldn't be able to mortgage this property");
            } catch (GameOfMonopoly.InvalidMove e) {
            }
        }
    }

    /**
     * Test that the player shouldn't sell a property on mortgage
     */
    @Test
    public void test11_invalid_sellMortgagedProperty() {

        GameOfMonopoly game = new GameOfMonopoly();

        // mortgage streets
        for (int i = 0; i < STREETS.length; i++) {
            String locationName = STREETS[i];
            try {
                Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                Location location = game.getBoard().findLocation(locationName);
                game.buyProperty(player);
                game.mortgageProperty(player, location);
                game.sellProperty(player, location);
                fail("Cannot sell a property with a mortgage on");
            } catch (GameOfMonopoly.InvalidMove e) {
            }
        }

        // mortgage utility companies
        for (int i = 0; i < UTILITIES.length; i++) {
            String locationName = UTILITIES[i];
            try {
                Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                Location location = game.getBoard().findLocation(locationName);
                game.buyProperty(player);
                game.mortgageProperty(player, location);
                game.sellProperty(player, location);
                fail("Cannot sell a property with a mortgage on");
            } catch (GameOfMonopoly.InvalidMove e) {
            }
        }

        // mortgage stations
        for (int i = 0; i < STATIONS.length; i++) {
            String locationName = STATIONS[i];
            try {
                Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                Location location = game.getBoard().findLocation(locationName);
                game.buyProperty(player);
                game.mortgageProperty(player, location);
                game.sellProperty(player, location);
                fail("Cannot sell a property with a mortgage on");
            } catch (GameOfMonopoly.InvalidMove e) {
            }
        }
    }

    /**
     * Test that the player shouldn't mortgage a property more than once
     */
    @Test
    public void test12_invalid_doubleMortgage() {
        try {
            GameOfMonopoly game = new GameOfMonopoly();
            String locationName = "Park Lane";
            Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
            Location location = game.getBoard().findLocation(locationName);
            game.buyProperty(player);
            game.mortgageProperty(player, location);
            game.mortgageProperty(player, location);
            fail("Invalid property, cannot mortage a property that already has a mortgage");
        } catch (GameOfMonopoly.InvalidMove e) {
        }
    }

    /**
     * Test that a player shouldn't mortgage other's property
     */
    @Test
    public void test13_invalid_mortgageOthersProperty() {
        try {
            GameOfMonopoly game = new GameOfMonopoly();
            String locationName = "Park Lane";
            Player player1 = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
            Player player2 = setupMockPlayer(game, locationName, "Roxy", BIG_FUND);
            Location location = game.getBoard().findLocation(locationName);
            game.buyProperty(player1);
            game.mortgageProperty(player2, location);
            fail("Cannot sell a property which belongs to someone else");
        } catch (GameOfMonopoly.InvalidMove e) {
        }
    }

    /**
     * Test a valid unmortgage
     */
    @Test
    public void test14_valid_unmortgage() {

        try {
            GameOfMonopoly game = new GameOfMonopoly();

            // mortgage streets
            for (int i = 0; i < STREETS.length; i++) {
                String locationName = STREETS[i];
                Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                Location location = game.getBoard().findLocation(locationName);
                Street street = (Street) location;
                game.buyProperty(player);
                game.mortgageProperty(player, location);
                game.unmortgageProperty(player, location);
                assertTrue(location.getName() + " should have an owner", location.hasOwner());
                assertFalse(location.getName() + " shouldn't be mortgaged", street.isMortgaged());
                assertEquals(BIG_FUND - street.getPrice() + street.getPrice() / 2
                        - (int) ((double) (street.getPrice() / 2) * 1.1), player.getBalance());
                assertEquals(locationName, player.iterator().next().getName());
                assertEquals(player, street.getOwner());
            }

            // mortgage utility companies
            for (int i = 0; i < UTILITIES.length; i++) {
                String locationName = UTILITIES[i];
                Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                Location location = game.getBoard().findLocation(locationName);
                Utility util = (Utility) location;
                game.buyProperty(player);
                game.mortgageProperty(player, location);
                game.unmortgageProperty(player, location);
                assertTrue(location.getName() + " should have an owner", location.hasOwner());
                assertFalse(location.getName() + " should be mortgaged", util.isMortgaged());
                assertEquals(BIG_FUND - util.getPrice() + util.getPrice() / 2
                        - (int) ((double) (util.getPrice() / 2) * 1.1), player.getBalance());
                assertEquals(locationName, player.iterator().next().getName());
                assertEquals(player, util.getOwner());
            }

            // mortgage stations
            for (int i = 0; i < STATIONS.length; i++) {
                String locationName = STATIONS[i];
                Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                Location location = game.getBoard().findLocation(locationName);
                Station station = (Station) location;
                game.buyProperty(player);
                game.mortgageProperty(player, location);
                game.unmortgageProperty(player, location);
                assertTrue(location.getName() + " should have an owner", location.hasOwner());
                assertFalse(location.getName() + " should be mortgaged", station.isMortgaged());
                assertEquals(BIG_FUND - station.getPrice() + station.getPrice() / 2
                        - (int) ((double) (station.getPrice() / 2) * 1.1), player.getBalance());
                assertEquals(locationName, player.iterator().next().getName());
                assertEquals(player, station.getOwner());
            }

        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.getMessage());
        }

    }

    /**
     * Test that a player shouldn't unmortgage a special area
     */
    @Test
    public void test15_invalid_unmortgageSpecialArea() {

        GameOfMonopoly game = new GameOfMonopoly();
        for (int i = 0; i < SPECIALS.length; i++) {
            String locationName = SPECIALS[i];
            Board board = game.getBoard();
            Location location = board.findLocation(locationName);
            try {
                Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                game.unmortgageProperty(player, location);
                fail("Shouldn't be able to mortgage this property");
            } catch (GameOfMonopoly.InvalidMove e) {
            }
        }
    }

    /**
     * Test that a player shouldn't unmortgage other's property
     */
    @Test
    public void test16_invalid_unmortgageOthersProperty() {

        GameOfMonopoly game = new GameOfMonopoly();

        for (int i = 0; i < STREETS.length; i++) {
            String locationName = STREETS[i];
            Location location = game.getBoard().findLocation(locationName);
            try {
                Player player1 = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                Player player2 = setupMockPlayer(game, locationName, "Roxy", BIG_FUND);
                game.buyProperty(player1);
                game.mortgageProperty(player1, location);
                game.unmortgageProperty(player2, location);
                fail("Shouldn't be able to buy this property");
            } catch (GameOfMonopoly.InvalidMove e) {
            }
        }

        for (int i = 0; i < UTILITIES.length; i++) {
            String locationName = UTILITIES[i];
            Location location = game.getBoard().findLocation(locationName);
            try {
                Player player1 = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                Player player2 = setupMockPlayer(game, locationName, "Roxy", BIG_FUND);
                game.buyProperty(player1);
                game.mortgageProperty(player1, location);
                game.unmortgageProperty(player2, location);
                fail("Shouldn't be able to buy this property");
            } catch (GameOfMonopoly.InvalidMove e) {
            }
        }

        for (int i = 0; i < STATIONS.length; i++) {
            String locationName = STATIONS[i];
            Location location = game.getBoard().findLocation(locationName);
            try {
                Player player1 = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                Player player2 = setupMockPlayer(game, locationName, "Roxy", BIG_FUND);
                game.buyProperty(player1);
                game.mortgageProperty(player1, location);
                game.unmortgageProperty(player2, location);
                fail("Shouldn't be able to buy this property");
            } catch (GameOfMonopoly.InvalidMove e) {
            }
        }
    }

    /**
     * Test that a player shouldn't unmortgage an ummortgaged property
     */
    @Test
    public void test17_invalid_ummortgageUnmortgaged() {
        GameOfMonopoly game = new GameOfMonopoly();
        for (int i = 0; i < STREETS.length; i++) {
            String locationName = STREETS[i];
            Location location = game.getBoard().findLocation(locationName);
            try {
                Player player = setupMockPlayer(game, locationName, "Hector", BIG_FUND);
                game.buyProperty(player);
                game.unmortgageProperty(player, location);
                fail("Shouldn't be able to unmortgage this property");
            } catch (GameOfMonopoly.InvalidMove e) {
            }
        }
    }

    /**
     * Test that a player shouldn't unmortgage a property with insufficient fund
     */
    @Test
    public void test18_invalid_unmortgageWithoutFund() {
        GameOfMonopoly game = new GameOfMonopoly();
        String locationName = "Park Lane";
        Location location = game.getBoard().findLocation(locationName);
        try {
            Player player = setupMockPlayer(game, locationName, "Hector", 353);
            game.buyProperty(player);
            game.mortgageProperty(player, location);
            game.unmortgageProperty(player, location);
            fail("Not enough funds to unmortage the property.");
        } catch (GameOfMonopoly.InvalidMove e) {
        }
    }

    /**
     * Test a valid case of building house
     */
    @Test
    public void test21_valid_buildHouse() {

        for (int i = 1; i <= 5; i++) {
            GameOfMonopoly game = new GameOfMonopoly();
            Board board = game.getBoard();
            String locationName_1 = "Park Lane";
            String locationName_2 = "Mayfair";
            Location location_1 = board.findLocation(locationName_1);
            Location location_2 = board.findLocation(locationName_2);

            try {
                Player player = setupMockPlayer(game, locationName_1, "Hector", BIG_FUND);
                game.buyProperty(player);
                game.movePlayer(player, 2);
                game.buyProperty(player);
                game.buildHouses(player, location_2, i);
                Street street_1 = (Street) location_1;
                Street street_2 = (Street) location_2;
                ColourGroup group = street_2.getColourGroup();
                int cost = i * group.getHouseCost();
                assertEquals("number mismatch", i, street_2.getHouses());
                assertEquals("money mismatch", BIG_FUND - cost - street_2.getPrice() - street_1.getPrice(),
                        player.getBalance());
            } catch (GameOfMonopoly.InvalidMove e) {
                fail(e.toString());
            }
        }
    }

    /**
     * Test that the player should't build a bad number of houses
     */
    @Test
    public void test22_invalid_buildHouse() {

        // build bad numbers of houses
        int[] badNumbers = new int[] { 0, 6, 8, 10 };
        for (int i = 0; i < badNumbers.length; i++) {
            GameOfMonopoly game = new GameOfMonopoly();
            Board board = game.getBoard();
            String locationName_1 = "Park Lane";
            String locationName_2 = "Mayfair";
            Location location_2 = board.findLocation(locationName_2);

            try {
                Player player = setupMockPlayer(game, locationName_1, "Hector", BIG_FUND);
                game.buyProperty(player);
                game.movePlayer(player, 2);
                game.buyProperty(player);
                game.buildHouses(player, location_2, badNumbers[i]);
                fail("can't bulid " + badNumbers[i] + " houses");
            } catch (GameOfMonopoly.InvalidMove e) {
            }
        }

        // build on a street that already has houses
        GameOfMonopoly game = new GameOfMonopoly();
        Board board = game.getBoard();
        String locationName_1 = "Park Lane";
        String locationName_2 = "Mayfair";
        Location location_2 = board.findLocation(locationName_2);

        try {
            Player player = setupMockPlayer(game, locationName_1, "Hector", BIG_FUND);
            game.buyProperty(player);
            game.movePlayer(player, 2);
            game.buyProperty(player);
            game.buildHouses(player, location_2, 3);
            Street street_2 = (Street) location_2;
            assertEquals("number mismatch", 3, street_2.getHouses());
            game.buildHouses(player, location_2, 3);
            fail("can't build more than 5 houses");
        } catch (GameOfMonopoly.InvalidMove e) {
        }
    }

    /**
     * Test that the player shouln't build on special areas
     */
    @Test
    public void test23_invalid_buildHouseOnNonStreet() {

        String[] locs = { "Go", "Kings Cross Station", "Electric Company" };
        for (int i = 0; i < locs.length; i++) {
            GameOfMonopoly game = new GameOfMonopoly();
            Board board = game.getBoard();
            String locationName_1 = locs[i];
            Location location_1 = board.findLocation(locationName_1);

            try {
                Player player = setupMockPlayer(game, locationName_1, "Hector", BIG_FUND);
                game.buildHouses(player, location_1, 3);
                fail("can't bulid houses on non-property");
            } catch (GameOfMonopoly.InvalidMove e) {
            }
        }
    }

    /**
     * Test that the player shouldn't build houses on hotel
     */
    @Test
    public void test24_invalid_buildHouseOnHotel() {

        GameOfMonopoly game = new GameOfMonopoly();
        Board board = game.getBoard();
        String locationName_1 = "Park Lane";
        String locationName_2 = "Mayfair";
        Location location_2 = board.findLocation(locationName_2);

        try {
            Player player = setupMockPlayer(game, locationName_1, "Hector", BIG_FUND);
            game.buyProperty(player);
            game.movePlayer(player, 2);
            game.buyProperty(player);
            game.buildHouses(player, location_2, 5);
            game.buildHotel(player, location_2);
            Street street_2 = (Street) location_2;
            assertEquals("number mismatch", 0, street_2.getHouses());
            assertEquals("number mismatch", 1, street_2.getHotels());
            assertEquals("number mismatch", 50 * 2 + 0 * 25 + 1 * 200, street_2.getRent(12));
            game.buildHouses(player, location_2, 1);
            fail("can't build house on hotel");
        } catch (GameOfMonopoly.InvalidMove e) {
        }
    }

    /**
     * Test that the player shouldn't build houses or a hotel with insufficient fund
     */
    @Test
    public void test25_invalid_buildWithoutFund() {

        // insufficient fund to build house
        GameOfMonopoly game = new GameOfMonopoly();
        Board board = game.getBoard();
        String locationName_1 = "Park Lane";
        String locationName_2 = "Mayfair";
        Location location_2 = board.findLocation(locationName_2);

        try {
            Player player = setupMockPlayer(game, locationName_1, "Hector", 350 + 400 + 200 * 3);
            game.buyProperty(player);
            game.movePlayer(player, 2);
            game.buyProperty(player);
            game.buildHouses(player, location_2, 5);
            fail("insufficient fund to build house");
        } catch (GameOfMonopoly.InvalidMove e) {
        }

        // insufficient fund to build Hotel
        game = new GameOfMonopoly();
        board = game.getBoard();
        locationName_1 = "Park Lane";
        locationName_2 = "Mayfair";
        location_2 = board.findLocation(locationName_2);

        try {
            Player player = setupMockPlayer(game, locationName_1, "Hector", 350 + 400 + 200 * 5);
            game.buyProperty(player);
            game.movePlayer(player, 2);
            game.buyProperty(player);
            game.buildHouses(player, location_2, 5);
            game.buildHotel(player, location_2);
            fail("insufficient fund to build hotel");
        } catch (GameOfMonopoly.InvalidMove e) {
        }
    }

    /**
     * Test a valid case of building hotel
     */
    @Test
    public void test26_valid_buildHotels() {

        GameOfMonopoly game = new GameOfMonopoly();
        Board board = game.getBoard();
        String locationName_1 = "Park Lane";
        String locationName_2 = "Mayfair";
        Location location_1 = board.findLocation(locationName_1);
        Location location_2 = board.findLocation(locationName_2);

        try {
            Player player = setupMockPlayer(game, locationName_1, "Hector", BIG_FUND);
            game.buyProperty(player);
            game.movePlayer(player, 2);
            game.buyProperty(player);
            game.buildHouses(player, location_2, 5);
            game.buildHotel(player, location_2);
            Street street_1 = (Street) location_1;
            Street street_2 = (Street) location_2;
            ColourGroup group = street_2.getColourGroup();
            int cost = group.getHouseCost() * 6;
            assertEquals("number mismatch", 0, street_2.getHouses());
            assertEquals("number mismatch", 1, street_2.getHotels());
            assertEquals("number mismatch", 50 * 2 + 0 * 25 + 1 * 200, street_2.getRent(12));
            assertEquals("money mismatch", BIG_FUND - cost - street_2.getPrice() - street_1.getPrice(),
                    player.getBalance());
        } catch (GameOfMonopoly.InvalidMove e) {
            fail(e.toString());
        }
    }

    /**
     * Test that the player shouldn't build a hotel with fewer than 5 houses
     */
    @Test
    public void test27_invalid_buildHotels() {

        GameOfMonopoly game = new GameOfMonopoly();
        Board board = game.getBoard();
        String locationName_1 = "Park Lane";
        String locationName_2 = "Mayfair";
        Location location_2 = board.findLocation(locationName_2);

        for (int i = 1; i < 5; i++) {
            try {
                Player player = setupMockPlayer(game, locationName_1, "Hector", BIG_FUND);
                game.buyProperty(player);
                game.movePlayer(player, 2);
                game.buyProperty(player);
                game.buildHouses(player, location_2, i);
                game.buildHotel(player, location_2);
                fail("doesn't have enough houses to build a hotel");
            } catch (GameOfMonopoly.InvalidMove e) {
            }
        }
    }

    /**
     * Test that the player shouldn't build more than one hotels
     */
    @Test
    public void test28_invalid_buildTwoHotels() {

        GameOfMonopoly game = new GameOfMonopoly();
        Board board = game.getBoard();
        String locationName_1 = "Park Lane";
        String locationName_2 = "Mayfair";
        Location location_2 = board.findLocation(locationName_2);

        try {
            Player player = setupMockPlayer(game, locationName_1, "Hector", BIG_FUND);
            game.buyProperty(player);
            game.movePlayer(player, 2);
            game.buyProperty(player);
            game.buildHouses(player, location_2, 5);
            game.buildHotel(player, location_2);
            game.buildHotel(player, location_2);
            fail("Can't build two hotels");
        } catch (GameOfMonopoly.InvalidMove e) {
        }
    }

    /**
     * Test that the player shouldn't build a hotel on non-street locations
     */
    @Test
    public void test29_invalid_buildHotelOnNonStreet() {

        String[] locs = { "Go", "Kings Cross Station", "Electric Company" };
        for (int i = 0; i < locs.length; i++) {
            GameOfMonopoly game = new GameOfMonopoly();
            Board board = game.getBoard();
            String locationName_1 = locs[i];
            Location location_1 = board.findLocation(locationName_1);

            try {
                Player player = setupMockPlayer(game, locationName_1, "Hector", BIG_FUND);
                game.buildHotel(player, location_1);
                fail("can't bulid hotel on non-property");
            } catch (GameOfMonopoly.InvalidMove e) {
            }
        }
    }

    /**
     * Test that the player shouldn't build on a mortgaged street
     */
    @Test
    public void test30_invalid_buildOnMortgaged() {

        // house
        GameOfMonopoly game = new GameOfMonopoly();
        Board board = game.getBoard();
        String locationName_1 = "Park Lane";
        String locationName_2 = "Mayfair";
        Location location_2 = board.findLocation(locationName_2);

        try {
            Player player = setupMockPlayer(game, locationName_1, "Hector", BIG_FUND);
            game.buyProperty(player);
            game.movePlayer(player, 2);
            game.buyProperty(player);
            game.mortgageProperty(player, location_2);
            game.buildHouses(player, location_2, 5);
            fail("cannot build on mortgaged property");
        } catch (GameOfMonopoly.InvalidMove e) {
        }

        // hotel
        game = new GameOfMonopoly();
        board = game.getBoard();
        locationName_1 = "Park Lane";
        locationName_2 = "Mayfair";
        location_2 = board.findLocation(locationName_2);

        try {
            Player player = setupMockPlayer(game, locationName_1, "Hector", BIG_FUND);
            game.buyProperty(player);
            game.movePlayer(player, 2);
            game.buyProperty(player);
            game.mortgageProperty(player, location_2);
            game.buildHotel(player, location_2);
            fail("cannot build on mortgaged property");
        } catch (GameOfMonopoly.InvalidMove e) {
        }
    }

    /**
     * Test that the player shouldn't build on a street whose group is not owned
     */
    @Test
    public void test31_invalid_buildOnNonUnicoloredProperty() {

        // house
        GameOfMonopoly game = new GameOfMonopoly();
        Board board = game.getBoard();
        String locationName_1 = "Park Lane";
        Location location_1 = board.findLocation(locationName_1);

        try {
            Player player = setupMockPlayer(game, locationName_1, "Hector", BIG_FUND);
            game.buyProperty(player);
            game.buildHouses(player, location_1, 5);
            fail("cannot build on a place in colour group that is not theirs!");
        } catch (GameOfMonopoly.InvalidMove e) {
        }

        // hotel
        game = new GameOfMonopoly();
        board = game.getBoard();
        locationName_1 = "Park Lane";
        location_1 = board.findLocation(locationName_1);

        try {
            Player player = setupMockPlayer(game, locationName_1, "Hector", BIG_FUND);
            game.buyProperty(player);
            game.buildHotel(player, location_1);
            fail("cannot build on a place in colour group that is not theirs!");
        } catch (GameOfMonopoly.InvalidMove e) {
        }
    }

    /**
     * some random test on bad names. can potentially reduce null pointer exception.
     */
    @Test
    public void test99_invalidName() {
        String locationName = "A Bad Name";
        GameOfMonopoly game = new GameOfMonopoly();
        Board board = game.getBoard();
        assertTrue(board.findLocation(locationName) == null);
    }

    /**
     * Setup a mock game of monopoly with a player located at a given location.
     */
    private Player setupMockPlayer(GameOfMonopoly game, String locationName, String playerName, int balance)
            throws GameOfMonopoly.InvalidMove {
        Board board = game.getBoard();
        Location location = board.findLocation(locationName);
        return new Player(playerName, Player.Token.ScottishTerrier, balance, location);
    }
}