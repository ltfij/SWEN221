package swen221.assignment2.chessview.pieces;

import swen221.assignment2.chessview.*;

public class Rook extends PieceImpl implements Piece {
	public Rook(boolean isWhite) {
		super(isWhite);
	}
	
	@Override
	public boolean isValidMove(Position oldPosition, Position newPosition,
			Piece isTaken, Board board) {
	    int diffCol = Math.abs(oldPosition.column() - newPosition.column());
        int diffRow = Math.abs(oldPosition.row() - newPosition.row());
		Piece p = board.pieceAt(oldPosition);
		Piece t = board.pieceAt(newPosition);
		
		// not vertical, not horizontal 
		if (diffCol != 0 && diffRow != 0) {
		    return false;
		}
		
		// check the object type
        boolean partialResult = this.equals(p)
                && (t == isTaken || (isTaken != null && isTaken.equals(t)));
        if (!partialResult) {
            return false;
        }
		
        // check whether there are obstacles
        if (diffCol == 0 && diffRow != 0) {
            return board.clearColumnExcept(oldPosition, newPosition, p, t);
        } else if (diffRow == 0 && diffCol != 0) {
            return board.clearRowExcept(oldPosition, newPosition, p, t);
        } else {
            return false;
        }
	}
	
	public String toString() {
		if(isWhite) {
			return "R";
		} else {
			return "r";
		}
	}
}
