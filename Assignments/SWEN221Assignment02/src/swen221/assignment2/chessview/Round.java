package swen221.assignment2.chessview;

import swen221.assignment2.chessview.moves.Move;

/**
 * A round consists of a move by white, and will normally also have a move by
 * black. The latter may not happen in the case that White wins the game.
 * 
 * @author djp
 * 
 */
public class Round {
	private Move white;
	private Move black;
	
	/**
	 * Create a round from a white move, and an optional black move.
	 * 
	 * @param white - whites move; cannot be null;
	 * @param black - blacks move; may be null.
	 */
	public Round(Move white, Move black) {
		if(white == null) {
			throw new IllegalArgumentException("A round must always consist of a move by white");
		}
		this.white = white;
		this.black = black;
	}
	
	public Move white() {
		return white;
	}
	
	public Move black() {
		return black;
	}
	
	public String toString() {
		String r = white.toString();
		if(black != null) {
			r += " " + black.toString();
		}
		return r;
	}
}
