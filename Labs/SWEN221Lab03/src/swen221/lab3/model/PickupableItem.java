package swen221.lab3.model;

import java.util.List;

public abstract class PickupableItem implements Item {
	
	@Override
	public boolean performAction(String action, Player player) {
		if (action.equals("Pickup")) {
			return pickupThisItem(player);
		} else if(action.equals("Drop")){
			return dropThisItem(player);
		} else {
			throw new IllegalArgumentException("Unknown action: " + action);
		}
	}
	
	protected boolean pickupThisItem(Player player) {		
		Room r = player.getLocation();
		// Determine location of item in room
		Room.Location l = r.containsItem(this);
		if(l != null) {
			// Add item into players inventory
			player.getInventory().add(this);
			// Remove item from the room
			r.setItem(l, null);
			return true;
		} else {
			// Cannot pickup item unless it's in the current room
			return false;
		}		
	}
	
	protected boolean dropThisItem(Player player) {
		List<Item> inventory = player.getInventory();
		if(inventory.contains(this)) {
			Room r = player.getLocation();
			// Try and add item into players inventory
			boolean ok = r.addItem(this);
			// Check whether it was added to the room
			if (ok) {
				// Remove item from the player
				inventory.remove(this);
			}
			return ok;
		} else {
			// cannot drop an item unless we are holding it.
			return false;
		}
		
	}
}
