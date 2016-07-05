package swen221.lab3.model;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This class represents a door in the game. It provides references to the rooms
 * which are on either side of the door.
 * 
 * @author David J. Pearce
 * 
 */
public class Door implements Item {	
	private Room oneSide;	
	private Room otherSide;
	
	public Door(Room oneSide, Room otherSide) {			
		this.oneSide = oneSide;
		this.otherSide = otherSide;
	}
	
	@Override
	public String[] getActions() {
		return new String[]{"Enter"};
	}

	@Override
	public boolean performAction(String action, Player player) {
		Room r = player.getLocation();
		// First, check which side of the door the player is on.
		if(r == oneSide) {
			player.setLocation(otherSide);
		} else {
			player.setLocation(oneSide);
		}
		// Done
		return true;
	}
		
	@Override
	public String getDescription() {
		return "A door between \"" + oneSide.getDescription() + "\" and \"" + otherSide.getDescription() + "\"";
	}

	/**
	 * Return the room on one side of the door
	 * 
	 * @return
	 */
	public Room oneSide() { return oneSide; }
	
	/**
	 * Return the room on the other side of the door
	 * 
	 * @return
	 */
	public Room otherSide() { return otherSide; }	

	@Override
	public void draw(Graphics g) {
		int width = (int) g.getClipBounds().getWidth();
		int height = (int) g.getClipBounds().getHeight();
		int xStart = width / 4;
		int yStart = height / 8;
		int xEnd = (width * 3) / 4;
		int yEnd = (height * 7) / 8;
		g.setColor(Color.GRAY);
		g.fillRect(xStart, yStart, xEnd - xStart, yEnd - yStart);
		g.setColor(Color.BLACK);
		g.drawRect(xStart, yStart, xEnd - xStart, yEnd - yStart);
		g.fillRect(xStart+10, height / 3,10,10);
	}
}
