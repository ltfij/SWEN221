package swen221.lab6.connect.core;

import swen221.lab6.connect.util.Position;

/**
 * Represents the 8x8 board in a game of CONNECT. Each square on the board can
 * either be empty, or hold a black or white token. The board is simply
 * responsible for storing this information, not for deciding on the rules of
 * the game.
 * 
 * @author David J. Pearce
 *
 */
public class Board implements Cloneable {
	public enum Token {BLACK,WHITE}; 
	
	/**
	 * The Game board is a 2-dimensional array
	 */
	private Token[][] board;
	
	/**
	 * Create an empty game board with 8x8 empty squares
	 */
	public Board() {
		this.board = new Token[4][4];
	}
		
	/**
	 * Get the token at a given x and y position, or null if the square is
	 * empty.
	 * 
	 * @param pos
	 *            --- Position of square to get
	 * @return
	 */
	public Token getSquare(Position pos) {
		return board[pos.getX()][pos.getY()];
	}
	
	/**
	 * Set the token at a given x and y position or null (to set the square as
	 * empty).
	 * 
	 * @param pos
	 *            --- Position of square to set
	 * @param token
	 *            --- Token to place at that position
	 * @return
	 */
	public void setSquare(Position pos, Token token) {
		board[pos.getX()][pos.getY()] = token;
	}
	
	/**
	 * Create an identical copy of this game board.
	 */
	public Board clone() {
		Board b = new Board();
		for(int x=0;x<4;++x) {
			for(int y=0;y<4;++y) {
				Position p = new Position(x,y);
				b.setSquare(p, getSquare(p));
			}	
		}
		return b;
	}
	
	/**
	 * Construct human-readable string representation of the board.
	 */
	public String toString() {
		String r = "";
		for (int y = 0; y < 4; ++y) {
			for (int x = 0; x < 4; ++x) {
				r += "|";
				r += toString(board[x][y]);
			}
			r += "|\n";
		}
		return r;
	}
	
	/**
	 * Convert a given token into a string when generating a human-readable text
	 * representation of the board.
	 * 
	 * @param t
	 * @return
	 */
	private static String toString(Token t) {
		if(t == Token.BLACK) {
			return "B";
		} else if(t == Token.WHITE) {
			return "W";
		}
		return "_";
	}
}
