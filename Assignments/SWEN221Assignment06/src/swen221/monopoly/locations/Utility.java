package swen221.monopoly.locations;

/**
 * Represents a utility on the board, which is a property that can be bought or sold. As for
 * stations, the rent calculation for a utility is dependent on whether the given landlord owns one
 * or both of the utilities.
 * 
 * @author David J. Pearce
 *
 */
public class Utility extends Property {

    /**
     * Construct a utility with a given name and purchase price
     * 
     * @param name
     * @param price
     */
    public Utility(String name, int price) {
        super(name, price);
    }

    /**
     * Calculate rent for this Utility. Should only be called if hasOwner() == true.
     */
    @Override
    public int getRent(int lastDiceRoll) {
        // first, determine how many utilities owned by player
        int numUtilitiesOwned = 0;
        for (Property p : getOwner()) {
            if (p instanceof Utility) {
                numUtilitiesOwned++;
            }
        }
        // now compute rent, taking number owned into account
        return 4 * lastDiceRoll * numUtilitiesOwned;
    }
}
