package swen221.monopoly.locations;

import swen221.monopoly.Player;

/**
 * Represents a property on the board. A property is distinct from a location
 * because properties can be bought and sold. Example properties include
 * "Vine Street", "Eletric Company" and "Kings Cross Station">
 * 
 * @author David J. Pearce
 *
 */
public abstract class Property extends Location {
	private int price; // in $
	private boolean mortgaged;
	private Player owner;

	/**
	 * Construct a new property with a given name and purchase price.
	 * 
	 * @param name
	 * @param price
	 */
	public Property(String name, int price) {
		super(name);
		this.price = price;
		this.mortgaged = false;
		this.owner = null;
	}

	/**
	 * Get the value of this property in $.
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Mark property as mortgaged
	 */
	public void mortgage() {
		mortgaged = true;
	}

	/**
	 * Mark property as unmortgaged
	 */
	public void unmortgage() {
		mortgaged = false;
	}

	/**
	 * Check whether property is mortgaged or not
	 */
	public boolean isMortgaged() {
		return mortgaged;
	}

	/**
	 * Check whether this property has an owner or not.
	 */
	public boolean hasOwner() {
		return owner != null;
	}

	/**
	 * Get player who owns this property. Should only be called if hasOwner() ==
	 * true.
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * Set player who owns this property.
	 */
	public void setOwner(Player player) {
		owner = player;
	}

	/**
	 * Calculate rent for given property type. This should only be called when
	 * hasOwner() == true.
	 * 
	 * @param lastDiceRoll
	 *            the number of the last dice roll
	 */
	public abstract int getRent(int lastDiceRoll);
}
