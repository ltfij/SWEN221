package swen221.assignment2.chessview.pieces;

import swen221.assignment2.chessview.*;

public class Queen extends PieceImpl implements Piece {
	public Queen(boolean isWhite) {
		super(isWhite);
	}
	
	@Override
	public boolean isValidMove(Position oldPosition, Position newPosition,
			Piece isTaken, Board board) {
	    int diffCol = Math.max(oldPosition.column(), newPosition.column())
                - Math.min(oldPosition.column(), newPosition.column());
        int diffRow = Math.max(oldPosition.row(), newPosition.row())
                - Math.min(oldPosition.row(), newPosition.row());
		Piece p = board.pieceAt(oldPosition);
		Piece t = board.pieceAt(newPosition);
		
		// check the object type
		boolean partialResult = this.equals(p)
                && (t == isTaken || (isTaken != null && isTaken.equals(t)));
		if (!partialResult) {
		    return false;
		}
		
		// check whether there are obstacles
		if (diffCol == 0 && diffRow != 0) {
		    return partialResult && board.clearColumnExcept(oldPosition, newPosition, p, t);
		} else if (diffRow == 0 && diffCol != 0) {
		    return partialResult && board.clearRowExcept(oldPosition, newPosition, p, t);
		} else if (diffCol != 0 && diffRow != 0 && diffCol == diffRow) {
		    return partialResult && board.clearDiaganolExcept(oldPosition, newPosition, p, t);
		} else {
		    return false;
		}
	}
	
	public String toString() {
		if(isWhite) {
			return "Q";
		} else {
			return "q";
		}
	}
}
