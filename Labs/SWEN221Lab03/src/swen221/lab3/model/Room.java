package swen221.lab3.model;

/**
 * This class represents a room in the adventure game.
 * 
 * @author David J. Pearce
 *
 */
public class Room {
	
	/**
	 * The set of possible locations in the room.
	 * 
	 * @author David J. Pearce
	 *
	 */
	public enum Location {
		NORTHWEST, NORTH, NORTHEAST, WEST, CENTER, EAST, SOUTHWEST, SOUTH, SOUTHEAST 
	}
	
	private String description;
	private Item[] items;	
	
	public Room(String description) {
		this.description = description;
		this.items = new Item[Location.values().length];
	}
	
	/**
	 * Get the description of this room
	 */
	public String getDescription() { return description; }
	
	/**
	 * Get the item at a given location in the room, or null if no item at that
	 * location.
	 * 
	 * @param location
	 * @return
	 */
	public Item getItem(Location location) {
		return items[location.ordinal()];
	}	
	
	/**
	 * Set the item at a given location in the room. If null, then the item at
	 * that location is removed from the room.
	 * 
	 * @param location
	 * @param item
	 */
	public void setItem(Location location, Item item) {
		items[location.ordinal()] = item;
	}
	
	/**
	 * Add an item to the first available location in the room. Return true if
	 * the item was successfully added, or false otherwise.
	 * 
	 * @param item
	 */
	public boolean addItem(Item item) {
		for(Location l : Location.values()) {
			if(items[l.ordinal()] == null) {
				// This location is free
				items[l.ordinal()] = item;
				return true;
			}
		}
		// No space in the room!
		return false;
	}
	
	/**
	 * Check whether a room contains a given item. If so, then return the
	 * location of that item in the room.
	 * 
	 * @param item
	 * @return
	 */
	public Location containsItem(Item item) {
		for(int i=0;i!=items.length;++i) {
			if(items[i] == item) {
				return Location.values()[i];
			}
		}
		return null;
	}
}
