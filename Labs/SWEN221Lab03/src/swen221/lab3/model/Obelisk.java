package swen221.lab3.model;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This class represents a collection of coins in the game.
 * 
 * @author David J. Pearce
 * 
 */
public class Obelisk implements Item {
	
	private boolean fallenOver = false;
	
	public Obelisk() {
	}

	@Override
	public String[] getActions() {
		return new String[]{"Pickup","Prod"};
	}

	@Override
	public boolean performAction(String action, Player player) {
		if(action == "Pickup") {
			return false; // cannot pickup stone
		} else {
			fallenOver = true;
			return true;
		}				
	}

	@Override
	public String getDescription() {
		if(fallenOver) {
			return "A stone obelisk which has fallen over";	
		} else {
			return "A stone obelisk standing upright";
		}
	}	
	
	@Override
	public void draw(Graphics g) {
		int width = (int) g.getClipBounds().getWidth();
		int height = (int) g.getClipBounds().getHeight();
		int xStart = width / 4;
		int yStart = height / 8;
		int xEnd = (width * 3) / 4;
		int yEnd = (height * 7) / 8;
		if(fallenOver) {
			yStart = (height*6)/8;			
		}
		g.setColor(Color.LIGHT_GRAY.brighter());
		g.fillRect(xStart, yStart, xEnd - xStart, yEnd - yStart);
		g.setColor(Color.BLACK);
		g.drawRect(xStart, yStart, xEnd - xStart, yEnd - yStart);
	}
}
