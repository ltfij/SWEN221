package swen221.monopoly.locations;

/**
 * Represents a street on the board, such as "Vine Street" or "Park Lane". Streets can be bought and
 * sold, and can have houses and hotels built on them. Their rent is a function of the base rate,
 * and the number of houses / hotels built on them.
 * 
 * @author David J. Pearce
 *
 */
public class Street extends Property {
    private int numHouses;
    private int numHotels;
    private int rent; // in $
    private ColourGroup colourGroup;

    /**
     * Construct a street with a given name, price value and rental cost.
     * 
     * @param name
     * @param price
     * @param rent
     */
    public Street(String name, int price, int rent) {
        super(name, price);
        this.rent = rent;
        colourGroup = null;
    }

    /**
     * Set the colour group this property is associated with.
     * 
     * @param group
     */
    public void setColourGroup(ColourGroup group) {
        colourGroup = group;
    }

    /**
     * Get colour group to which this street belongs. Will return null if setColourGroup not already
     * called.
     */
    public ColourGroup getColourGroup() {
        return colourGroup;
    }

    @Override
    public int getRent(int lastDiceRoll) {
        int rentOfGroup = rent;
        if (colourGroup.allPropertiesOwnedBy(getOwner())) {
            rentOfGroup = rent * 2;
        }
        int amount = rentOfGroup + (25 * numHouses) + (200 * numHotels);
        return amount;
    }

    /**
     * Get the number of houses which have been built on this street
     * 
     * @return
     */
    public int getHouses() {
        return numHouses;
    }

    /**
     * Get the number of hotels which have been built on this street
     * 
     * @return
     */
    public int getHotels() {
        return numHotels;
    }

    /**
     * Set the number of houses which have been built on this street
     * 
     * @return
     */
    public void setHouses(int numHouses) {
        this.numHouses = numHouses;
    }

    /**
     * Set the number of hotels which have been built on this street
     * 
     * @return
     */
    public void setHotels(int numHotels) {
        this.numHotels = numHotels;
    }
}
