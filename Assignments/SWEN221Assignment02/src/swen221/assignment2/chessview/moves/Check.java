package swen221.assignment2.chessview.moves;

import swen221.assignment2.chessview.*;

/**
 * This represents a "check move". Note that, a check move can only be made up
 * from an underlying simple move; that is, we can't check a check move.
 * 
 * @author djp
 * 
 */
public class Check implements Move {
	private MultiPieceMove move;
	
	public Check(MultiPieceMove move) {
		this.move = move;		
	}
	
	public MultiPieceMove move() {
		return move;
	}
	
	@Override
	public boolean isWhite() {
		return move.isWhite();
	}
	
	@Override
	public boolean isValid(Board board) {
	    Board newBoard = new Board(board);
	    newBoard.apply(move);
	    
	    // after this move, at least one side should be in check
	    return (newBoard.isInCheck(!isWhite()) || newBoard.isInCheck(isWhite()))
	            && move.isValid(board);
	}
	
	@Override
	public void apply(Board board) {
		move.apply(board);
	}
	
	public String toString() {
		return move.toString() + "+";
	}
}
