package swen221.monopoly.locations;

import swen221.monopoly.Player;

/**
 * Represents a special kind of location on the board, such as "Go", "Chance"
 * and "Community Chest". Special areas have relatively little functionality.
 * For example, they do not charge rent when a player lands on them.
 * 
 * @author David J. Pearce
 *
 */
public class SpecialArea extends Location {
	
	/**
	 * Construct a new special area with a given name.
	 * 
	 * @param n
	 */
	public SpecialArea(String n) {
		super(n);
	}

	/**
	 * Special areas cannot be bought and sold, hence it's always the case that
	 * they have no owner.
	 */
	public boolean hasOwner() {
		return false;
	}

	/**
	 * Should not be called on Special Area
	 */
	public Player getOwner() {
		throw new RuntimeException(
				"Should not call getOwner() on a SpeciaArea!");
	}

	/**
	 * Should not be called on Special Area
	 */
	public int getRent() {
		throw new RuntimeException("Should not call getRent() on a SpeciaArea!");
	}
}
