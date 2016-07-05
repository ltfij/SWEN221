package swen221.assignment2.chessview.pieces;

import swen221.assignment2.chessview.*;

public class Bishop extends PieceImpl implements Piece {
	public Bishop(boolean isWhite) {
		super(isWhite);
	}
	
	@Override
	public boolean isValidMove(Position oldPosition, Position newPosition,
			Piece isTaken, Board board) {
	    int diffCol = Math.abs(oldPosition.column() - newPosition.column());
        int diffRow = Math.abs(oldPosition.row() - newPosition.row());
		Piece p = board.pieceAt(oldPosition);
		Piece t = board.pieceAt(newPosition);
		return this.equals(p)
		        && (t == isTaken || (isTaken != null && isTaken.equals(t)))
		        && board.clearDiaganolExcept(oldPosition, newPosition, p, t)
				&& diffCol == diffRow;
	}
	
	public String toString() {
		if(isWhite) {
			return "B";
		} else {
			return "b";
		}
	}
}
