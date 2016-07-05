package swen221.lab3.model;

import java.awt.Graphics;

/**
 * This class represents an item stored in a room.
 * 
 * @author djp
 *
 */
public interface Item {

	/**
	 * Get a description of this item.
	 * 
	 * @return
	 */
	public String getDescription();
	
	/**
	 * Get the list of actions which can be performed on this item. This list
	 * can be empty, though it should never be null.
	 * 
	 * @return
	 */
	public String[] getActions();
	
	/**
	 * The player performs a given action on this item. This should be a valid
	 * action from the list of actions returned.
	 * 
	 * @param action
	 * @return
	 */
	public boolean performAction(String action, Player player);
	
	/**
	 * Draw the item to a given graphics context.
	 * 
	 * @param g
	 */
	public void draw(Graphics g);
	
}
