package minesweeper;

import java.util.*;
import minesweeper.moves.*;
import minesweeper.squares.*;

/**
 * Responsible for validating that a given game adheres to the
 * rules of Minesweeper.
 * 
 * @author David J. Pearce
 * 
 */
public class Game {	
	public final static int GAME_CONTINUES = 0;
	public final static int PLAYER_LOST = 1;
	public final static int PLAYER_WON = 2;
	
	/**
	 * Stores the width of the board.
	 */
	private int width;
	
	/**
	 * Stores the height of the board.
	 */
	private int height;

	/**
	 * A 2-dimenional array representing the board itself.
	 */
	private Square[][] board;
	
	/**
	 * The array of moves which make up this game.
	 */
	private Move[] moves;

	/**
	 * Construct a game with a board of a given width and height, and a sequence
	 * of moves to validate. Initially, the board is empty and so we must still
	 * identify the bombs and then initialise the board.
	 * 
	 * @param width
	 * @param height
	 * @param moves
	 */
	public Game(int width, int height, Move[] moves) {
		this.board = new Square[height][width];
		this.moves = moves;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Get the board width
	 * 
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Get the board height
	 * 
	 * @return
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Place a bomb on the board. This should be done before
	 * calling the initialise method.
	 * 
	 * @param position
	 *            --- position of bomb on board.
	 */
	public void placeBomb(Position position) {
		int x = position.getX();
		int y = position.getY();
		board[y][x] = new BombSquare();
	}
	
	/**
	 * Return the square at a given position on the board.
	 * 
	 * @param position
	 * @return
	 */
	public Square squareAt(Position position) {
		return board[position.getY()][position.getX()];
	}
	
	/**
	 * Check whether the game is over or not. If the game is over, give a
	 * reason.
	 * 
	 * @return 0 if the game is not over yet; 1 if the game is over and the
	 *         player lost; 2 if the game is over and the player won.
	 */
	public int getStatus() {
		int numberOfHiddenBlanks = 0;

		for (int y = 0; y != height; ++y) {
			for (int x = 0; x != width; ++x) {
				Square sq = board[y][x];
				if (sq.isHidden() && sq instanceof BlankSquare) {
					numberOfHiddenBlanks++;
				} else if (!sq.isHidden() && sq instanceof BombSquare) {
					return PLAYER_LOST; // game is definitely over
				}
			}
		}

		if (numberOfHiddenBlanks == 0) {
			return PLAYER_WON;
		} else {
			return GAME_CONTINUES;
		}
	}
	
	/**
	 * Initialise the board. This should be done after all bombs are placed.
	 * This method goes through the board and calculates, for each blank square,
	 * the number of bombs in the surrounding area.
	 */
	public void initialise() {	
				
		for(int y=0;y!=height;++y) {
			for(int x=0;x!=width;++x) {				
				Square s = board[y][x];
				if(s == null) {					
					int numberOfBombs = countBombsAround(x,y);		
					board[y][x] = new BlankSquare(numberOfBombs);
				}
			}	
		}
	}
	
	private int countBombsAround(int x, int y) {
		int count = 0;
		
		// y - 1
		if(isBomb(x-1,y-1)) { count++; }
		if(isBomb(x,y-1)) { count++; }
		if(isBomb(x+1,y-1)) { count++; }
		
		// y = 0
		if(isBomb(x-1,y)) { count++; }		
		if(isBomb(x+1,y)) { count++; }
		
		// y + 1
		if(isBomb(x-1,y+1)) { count++; }
		if(isBomb(x,y+1)) { count++; }
		if(isBomb(x+1,y+1)) { count++; }
		
		return count;
	}
	
	private boolean isBomb(int x, int y) {
		if (x >= 0 && x < width && y >= 0 && y < height) {
			Square square = board[y][x];

			if (square instanceof BombSquare) {
				// is a bomb
				return true;
			}
		}
		// not a bomb
		return false;
	}
	
	/**
	 * Validate this game against the rules of Minesweeper. In the event of an
	 * invalid move being encountered, then a syntax error should be thrown.
	 */
	public void validate() throws SyntaxError {
		for(int i=0;i!=moves.length;++i) {
			Move move = moves[i];			
			move.apply(this);			
		}
	}
	
	public String toString() {
		String r = "";

		r = r + "+";
		for (int x = 0; x != width; ++x) {
			r = r + "-";
		}
		r = r + "+\n";
		
		for (int y = 0; y != height; ++y) {
			r = r + "|";
			for (int x = 0; x != width; ++x) {
				Square sq = board[y][x];
				if (sq.isFlagged()) {
					r = r + "F";
				} else if (sq.isHidden()) {
					r = r + "?";
				} else if (sq instanceof BombSquare) {
					r = r + "*";
				} else {
					BlankSquare bs = (BlankSquare) sq;
					int numberOfBombsAround = bs.getNumberOfBombsAround();
					if (numberOfBombsAround == 0) {
						r = r + " ";
					} else {
						r = r + numberOfBombsAround;
					}
				}
			}
			r = r + "|\n";
		}

		r = r + "+";
		for (int x = 0; x != width; ++x) {
			r = r + "-";
		}
		r = r + "+";
		
		return r;
	}
}
