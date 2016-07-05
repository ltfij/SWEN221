package swen221.lab3.model;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This class represents a collection of coins in the game.
 * 
 * @author David J. Pearce
 * 
 */
public class Coin extends PickupableItem {
	
	public Coin() {
	}

	@Override
	public String[] getActions() {
		return new String[]{"Pickup","Drop"};
	}

	@Override
	public String getDescription() {
		return "A gold coin";
	}	
	
	@Override
	public void draw(Graphics g) {
		int width = (int) g.getClipBounds().getWidth();
		int height = (int) g.getClipBounds().getHeight();
		int xStart = width / 4;
		int yStart = height / 4;
		int xEnd = (width * 3) / 4;
		int yEnd = (height * 3) / 4;
		g.setColor(Color.YELLOW);
		g.fillOval(xStart, yStart, xEnd - xStart, yEnd - yStart);
		g.setColor(Color.YELLOW.darker());
		g.fillOval(xStart+5, yStart+5, (xEnd-10) - xStart, (yEnd-10) - yStart);
		g.setColor(Color.BLACK);
		g.drawOval(xStart, yStart, xEnd - xStart, yEnd - yStart);
	}
}
