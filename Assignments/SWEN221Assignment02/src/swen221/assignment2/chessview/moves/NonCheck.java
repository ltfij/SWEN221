package swen221.assignment2.chessview.moves;

import swen221.assignment2.chessview.*;

public class NonCheck implements Move {
	private MultiPieceMove move;	
	
	public NonCheck(MultiPieceMove move) {
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

        // after this move, neither white nor black should be checked
	    return !newBoard.isInCheck(isWhite())
	            && !newBoard.isInCheck(!isWhite())
	            && move.isValid(board);
	}
	
	@Override
	public void apply(Board board) {
		move.apply(board);
	}
	
	public String toString() {
		return move.toString();
	}
}
