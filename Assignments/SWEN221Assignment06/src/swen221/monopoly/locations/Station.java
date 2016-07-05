package swen221.monopoly.locations;

/**
 * Represents a station on the board. These are properties which can be bought
 * or sold. Their rent calculation is based upon the number of stations owned by
 * the landlord in question.
 * 
 * @author David J. Pearce
 *
 */
public class Station extends Property {
	
	/**
	 * Create a new station with a given name and purchase price.
	 * 
	 * @param name
	 * @param price
	 */
	public Station(String name, int price) {
		super(name, price);
	}

	/**
	 * Calculate rent for this station. This calculation requires determining
	 * how many other stations the given landlord owns. Hence, this should only
	 * be called if hasOwner() == true.
	 */
	@Override
	public int getRent(int lastDiceRoll) {
		// first, determine how many stations owned by player
		int nstations = 0;
		for (Property p : getOwner()) {
			if (p instanceof Station) {
				nstations++;
			}
		}
		// now compute rent, taking number owned into account
		return 25 * nstations;
	}
}
