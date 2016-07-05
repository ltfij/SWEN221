package swen221.lab2.model;

import java.util.Arrays;

/**
 * <p>
 * Implements a two dimensional grid of cell "states", ranging from 0 up to (and
 * including) 9. In Conway's original Game of Life, only two states are needed:
 * alive and dead. However, for more complicated variations on the game we can
 * have more game states to represent qualities, such as "health" or
 * "happiness", etc.
 * </p>
 *  
 * @author David J. Pearce
 *
 */
public class Board implements BoardView {
	/**
	 * Implements a two-dimensional arrangement of cells in the board.
	 */
	private int[] cells;

	/**
	 * The width of the board
	 */
	private int width;
	
	/**
	 * The height of the board
	 */
	private int height;
	
	/**
	 * Construct an empty gameboard of a given dimension where each cell is in
	 * state 0.
	 * 
	 * @param width
	 *            Width of the board (in cells)
	 * @param height
	 *            Height of the board (in cells)
	 */
	public Board(int width, int height) {
		this.cells = new int[width*height];
		this.width = width;
		this.height = height;
		Arrays.fill(cells, 9);
	}
	
	/**
	 * Create a board from a given starting state.
	 * 
	 * @param cells
	 */
	public Board(int width, int height, int[] cells) {
		if(cells.length != (width*height)) {
			throw new IllegalArgumentException("Invalid cell array given");
		}
		this.cells = cells;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Get the width of the board (in cells)
	 * 
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Get the height of the board (in cells)
	 * 
	 * @return
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Get the state of a given cell on the board
	 * 
	 * @param x
	 *            Horizontal position of Cell
	 * @param y
	 *            Vertical position of Cell
	 * @return
	 */
	public int getCellState(int x, int y) {
		if(x < 0 || x >= width) {
			throw new IllegalArgumentException("Invalid X position: " + x);
		} else if(y < 0 || y >= height) {
			throw new IllegalArgumentException("Invalid Y position: " + y);
		}
		//
		return cells[(y*width)+x];
	}
	
	/**
	 * Set the state of a given cell on the board
	 * 
	 * @param x
	 *            Horizontal position of Cell
	 * @param y
	 *            Vertical position of Cell
	 * @param state
	 *            State to set cell to
	 * @return
	 */
	public void setCellState(int x, int y, int state) {
		if(x < 0 || x >= width) {
			throw new IllegalArgumentException("Invalid X position");
		} else if(y < 0 || y >= height) {
			throw new IllegalArgumentException("Invalid Y position");
		} else if(state < 0 || state > 9) {
			throw new IllegalArgumentException("invalid cell state: " + state);
		}
		//
		cells[(y*width)+x] = state;
	}
	
	public String toString() {
		String r = "";
		for(int x=0;x!=width+2;++x) {
			r = r + "-";
		}
		r += "\n";
		for(int y=0;y!=height;++y) {
			r = r + "|";
			for(int x=0;x!=width;++x) {
				r = r + Integer.toString(getCellState(x,y));
			}
			r = r + "|\n"; // newline character
		}
		for(int x=0;x!=width+2;++x) {
			r = r + "-";
		}
		return r;
	}
}
