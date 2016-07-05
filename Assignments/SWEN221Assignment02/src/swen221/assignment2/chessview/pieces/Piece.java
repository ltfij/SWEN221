package swen221.assignment2.chessview.pieces;

import swen221.assignment2.chessview.*;


public interface Piece {
	/**
	 * Determine whether this piece is white or black.
	 * @return
	 */
	public boolean isWhite();

	/**
	 * Check whether or not a given move on a given board is valid. For takes,
	 * the piece being taken must be supplied.
	 * 
	 * @param oldPosition
	 *            --- position of this piece before move.
	 * @param newPosition
	 *            --- position of this piece after move.
	 * @param isTaken
	 *            --- piece being taken, or null if no piece taken.
	 * @param board
	 *            --- board on which the validity of this move is being checked.
	 * @return
	 */
	public boolean isValidMove(Position oldPosition,
			Position newPosition, Piece isTaken, Board board);	
}
