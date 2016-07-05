package swen221.lab3.model;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This class represents a collection of coins in the game.
 * 
 * @author David J. Pearce
 * 
 */
public class Key extends PickupableItem {
	
	/**
	 * The code is secret and can be used to open doors.
	 */
	private int code;
	
	public Key(int code) {
		this.code = code;
	}

	@Override
	public String[] getActions() {
		return new String[]{"Pickup","Drop"};
	}

	/**
	 * Get the code associated with this key.
	 * 
	 * @return
	 */
	public int getCode() {
		return code;
	}
	
	@Override
	public String getDescription() {
		return "A key with code \"" + code + "\"";
	}	
	
	@Override
	public void draw(Graphics g) {
		int[] xpoints = {10,65,65,90,90,65,65,30,30,10};
		int[] ypoints = {40,40,30,30,60,60,50,50,65,65};
		scale(xpoints,g.getClipBounds().getWidth());
		scale(ypoints,g.getClipBounds().getHeight());
		g.setColor(Color.YELLOW);		
		g.fillPolygon(xpoints,ypoints,10);	
		g.setColor(Color.BLACK);
		g.drawPolygon(xpoints,ypoints,10);
	}
	
	private void scale(int[] points, double size) {
		double scale = size / 100;
		for(int i=0;i!=points.length;++i) {
			points[i] = (int) (points[i] * scale);
		}
	}
}
