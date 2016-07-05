package swen221.monopoly.locations;

import java.util.*;

import swen221.monopoly.Player;

/**
 * Represents a colour group on the board. That is, a group of streets which
 * have the same colour. Colour groups are important as they affect the logic of
 * the game. For example, the rent for a given street is doubled when the owner
 * also owns all properties in its colour group.
 * 
 * @author David J. Pearce
 */
public class ColourGroup implements Iterable<Street> {
	private Street[] streets;
	private String colour;
	private int houseCost; // in $

	/**
	 * Create colour group made up of Streets supplied as arguments
	 */
	public ColourGroup(String colour, int houseCost, Street... streets) {
		this.streets = Arrays.copyOf(streets, streets.length);
		for (Street street : streets) {
			street.setColourGroup(this);
		}
		this.colour = colour;
		this.houseCost = houseCost;
	}

	/**
	 * Get colour name for this colour group (e.g. "Blue")
	 * 
	 * @return
	 */
	public String getColour() {
		return colour;
	}
	
	/**
	 * Get the cost in $ to build a house on a property in this group.
	 * 
	 * @return
	 */
	public int getHouseCost() {
		return houseCost;
	}
	
	/**
	 * Check whether all properties in this group are owned by the given player.
	 * 
	 * @param player
	 * @return
	 */
	public boolean allPropertiesOwnedBy(Player player) {
	    
		// Check whether all properties in colour group owned by same player
		for(Street st : streets) {
			if(st.getOwner() != player) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Get an iterator to iterate through all the streets in this group.
	 * 
	 * @return
	 */
	public Iterator<Street> iterator() {
		return Arrays.asList(streets).iterator();
	}
}
