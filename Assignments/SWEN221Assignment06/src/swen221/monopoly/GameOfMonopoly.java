package swen221.monopoly;

import swen221.monopoly.locations.ColourGroup;
import swen221.monopoly.locations.Location;
import swen221.monopoly.locations.Property;
import swen221.monopoly.locations.Street;

/**
 * Represents a game of monopoly. This contains an internal representation of the game board, and
 * provides a number of methods for moving players around the board, buying properties, building
 * houses, etc.
 *
 * @author David J. Pearce
 *
 */
public class GameOfMonopoly {
    private Board board = new Board();

    /**
     * Get the current game board.
     *
     * @return
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Move a player from current location a number of steps around the board, determined by the
     * dice roll. Rent should be deducted as necessary. The players balance may go negative as a
     * result of this.
     */
    public void movePlayer(Player player, int diceRoll) {
        if (player == null) {
            return;
        }

        // First, check whether we have passed Go or not. If so, then credit
        // $200 into the account.
        if (movePassesGo(player.getLocation(), diceRoll)) {
            player.credit(200);
        }

        // if (diceRoll < 1) {
        // throw new InvalidMove("dice rolled a wrong number.");
        // }

        // Second, determine the new location on the board, and move the player
        // to that location.
        Location loc = board.findLocation(player.getLocation(), diceRoll);
        player.setLocation(loc);

        // Second, collect rent if applicable
        if (loc.hasOwner()) {
            Property prop = (Property) loc; // only properties can have owners
            if (!prop.isMortgaged() && !prop.getOwner().equals(player)) {
                // can only collect rent on unmortgaged properties
                // and, obviously, don't collect on our own properties!
                int rent = prop.getRent(diceRoll);
                player.deduct(rent);
                prop.getOwner().credit(rent);
            }
        }
    }

    /**
     * Check whether a given number of moves from a starting location passes through the special
     * location "Go" or not. Observe that, if we start on Go, that doesn't count.
     */
    private boolean movePassesGo(Location start, int steps) {
        for (int i = 1; i < steps; ++i) {
            if (board.findLocation(start, i).getName().equals("Go")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Allow player to try and purchase location they are on. Only allowed if location is a
     * property, property not already owned and player has enough money!
     */
    public void buyProperty(Player player) throws InvalidMove {
        if (player == null) {
            throw new InvalidMove("Player cannot be null ");
        }

        Location loc = player.getLocation();
        if (!(loc instanceof Property)) {
            throw new InvalidMove(
                    player + " cannot buy location " + loc.getName() + ": it's not a property!");
        }
        Property prop = (Property) loc;
        if (prop.hasOwner()) {
            throw new InvalidMove(player + " cannot buy location " + prop.getName() + ": it's owned by "
                    + prop.getOwner().getName() + "!");
        }
        if (prop.getPrice() > player.getBalance()) {
            throw new InvalidMove(
                    player + " cannot buy location " + prop.getName() + ": player has insufficient funds!");
        }
        // OK, player can buy the property!
        player.buy(prop);
    }

    /**
     * Allow player to sell a property. Only allowed if property actually owned by player and not
     * already mortgaged.
     */
    public void sellProperty(Player player, Location loc) throws InvalidMove {
        if (player == null) {
            throw new InvalidMove("Player cannot be null ");
        }

        if (loc == null) {
            throw new InvalidMove("Location cannot be null ");
        }

        if (!(loc instanceof Property)) {
            throw new InvalidMove(
                    player + " cannot sell location " + loc.getName() + ": it's not a property!");
        }
        Property prop = (Property) loc;
        if (player != null && !player.equals(prop.getOwner())) {
            throw new InvalidMove(player + " cannot sell location " + loc.getName() + ": it's not theirs!");
        }
        if (prop.isMortgaged()) {
            throw new InvalidMove(player + " cannot sell location " + loc.getName() + ": it's mortgaged!");
        }

        // OK, we can sell the property!
        player.sell(prop);

    }

    /**
     * Allow player to mortgage a property. Only allowed if property actually owned by player and
     * not already mortgaged.
     */
    public void mortgageProperty(Player player, Location loc) throws InvalidMove {
        if (player == null) {
            throw new InvalidMove("Player cannot be null ");
        }

        if (loc == null) {
            throw new InvalidMove("Location cannot be null ");
        }

        if (!(loc instanceof Property)) {
            throw new InvalidMove(
                    player + " cannot mortgage location " + loc.getName() + ": it's not a property!");
        }
        Property prop = (Property) loc;
        if (player != null && !player.equals(prop.getOwner())) {
            throw new InvalidMove(
                    player + " cannot mortgage location " + loc.getName() + ": it's not theirs!");
        }
        if (prop.isMortgaged()) {
            throw new InvalidMove(
                    player + " cannot mortgage location " + loc.getName() + ": it's already mortgaged!");
        }

        // OK, we can mortgage the property!
        player.credit(prop.getPrice() / 2);
        prop.mortgage();
    }

    /**
     * Allow player to mortgage a property. Only allowed if property actually owned by player, it is
     * mortgaged and player has enough money to pay for it.
     */
    public void unmortgageProperty(Player player, Location loc) throws InvalidMove {
        if (player == null) {
            throw new InvalidMove("Player cannot be null ");
        }

        if (loc == null) {
            throw new InvalidMove("Location cannot be null ");
        }

        if (!(loc instanceof Property)) {
            throw new InvalidMove(
                    player + " cannot unmortgage location " + loc.getName() + ": it's not a property!");
        }
        Property prop = (Property) loc;
        if (player != null && !player.equals(prop.getOwner())) {
            throw new InvalidMove(
                    player + " cannot unmortgage location " + loc.getName() + ": it's not theirs!");
        }
        if (!prop.isMortgaged()) {
            throw new InvalidMove(
                    player + " cannot unmortgage location " + loc.getName() + ": it's not mortgaged!");
        }
        int cost = (int) ((double) (prop.getPrice() / 2) * 1.1);
        if (cost > player.getBalance()) {
            throw new InvalidMove(
                    player + " cannot mortgage location " + loc.getName() + ": insufficient funds!");
        }
        // OK, we can unmortgage the property!
        player.deduct(cost);
        prop.unmortgage();
    }

    /**
     * Build one or more houses on a given property. This property must be owned by the player, and
     * cannot be mortgated. Furthermore, the player must own all properties in its colour group and
     * there must be spaces for the houses.
     * 
     * @param player
     * @param loc
     * @param numHouses
     */
    public void buildHouses(Player player, Location loc, int numHouses) throws InvalidMove {
        if (player == null) {
            throw new InvalidMove("Player cannot be null ");
        }

        if (loc == null) {
            throw new InvalidMove("Location cannot be null ");
        }

        if (numHouses <= 0 || numHouses > 5) {
            throw new InvalidMove(player + " cannot build " + numHouses + " houses, not valid number ");
        }

        if (!(loc instanceof Street)) {
            throw new InvalidMove(
                    player + " cannot build houses on location " + loc.getName() + ": it's not a street!");
        }
        // Check street is not mortgaged
        Street street = (Street) loc;
        if (street.isMortgaged()) {
            throw new InvalidMove(
                    player + " cannot build houses on location " + loc.getName() + ": it's mortgaged!");
        }
        // Check all properties in colour group are owned
        ColourGroup group = street.getColourGroup();
        for (Street st : group) {
            if (st.getOwner() != player) {
                throw new InvalidMove(player + " cannot build houses on location " + loc.getName() + ", "
                        + st.getName() + " in colour group is not theirs!");
            }
        }
        // Check space to build houses
        if ((street.getHouses() + numHouses) > 5) {
            throw new InvalidMove(
                    player + " cannot build houses on location " + loc.getName() + ": not enough space!");
        } else if (street.getHotels() != 0) {
            throw new InvalidMove(player + " cannot build houses on location " + loc.getName()
                    + ": hotel already present!");
        }
        // Check available funds
        int cost = numHouses * group.getHouseCost();
        if (player.getBalance() < cost) {
            throw new InvalidMove(
                    player + " cannot build houses on location " + loc.getName() + ": insufficient funds!");
        }
        //
        player.deduct(cost);
        street.setHouses(street.getHouses() + numHouses);
    }

    /**
     * Build a hotel on a given property. This property must be owned by the player, and cannot be
     * mortgaged. Furthermore, the player must own all properties in its colour group and the
     * property must already contain give houses.
     * 
     * @param player
     * @param loc
     * @param numHouses
     */
    public void buildHotel(Player player, Location loc) throws InvalidMove {
        if (player == null) {
            throw new InvalidMove("Player cannot be null ");
        }

        if (loc == null) {
            throw new InvalidMove("Location cannot be null ");
        }

        if (!(loc instanceof Street)) {
            throw new InvalidMove(
                    player + " cannot build hotel on location " + loc.getName() + ": it's not a street!");
        }
        // Check street is not mortgaged
        Street street = (Street) loc;
        if (street.isMortgaged()) {
            throw new InvalidMove(
                    player + " cannot build hotel on location " + loc.getName() + ": it's mortgaged!");
        }
        // Check all properties in colour group are owned
        ColourGroup group = street.getColourGroup();
        for (Street st : group) {
            if (st.getOwner() != player) {
                throw new InvalidMove(player + " cannot build hotel on location " + loc.getName() + ", "
                        + st.getName() + " in colour group is not theirs!");
            }
        }
        // Check space to build houses
        if (street.getHouses() != 5) {
            throw new InvalidMove(player + " cannot build hotel on location " + loc.getName()
                    + ": need five houses first!");
        } else if (street.getHotels() != 0) {
            throw new InvalidMove(
                    player + " cannot build hotel on location " + loc.getName() + ": hotel already present!");
        }
        // Check available funds
        int cost = group.getHouseCost();
        if (player.getBalance() < cost) {
            throw new InvalidMove(
                    player + " cannot build hotel on location " + loc.getName() + ": insufficient funds!");
        }
        //
        player.deduct(cost);
        street.setHouses(0);
        street.setHotels(1);
    }

    /**
     * Indicates an attempt to make an invalid move.
     *
     */
    @SuppressWarnings("serial")
    public static class InvalidMove extends Exception {
        public InvalidMove(String msg) {
            super(msg);
        }
    }
}
