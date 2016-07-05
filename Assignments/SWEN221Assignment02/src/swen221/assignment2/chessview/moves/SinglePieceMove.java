package swen221.assignment2.chessview.moves;

import swen221.assignment2.chessview.*;
import swen221.assignment2.chessview.pieces.*;

public class SinglePieceMove implements MultiPieceMove {
	protected Piece piece;
	protected Position oldPosition;
	protected Position newPosition;
	
	public SinglePieceMove(Piece piece, Position oldPosition, Position newPosition) {
		this.piece = piece;
		this.oldPosition = oldPosition;
		this.newPosition = newPosition;
	}
	
	public Piece piece() {
		return piece;
	}
	
	@Override
	public boolean isWhite() {
		return piece.isWhite();
	}
	
	public Position oldPosition() {
		return oldPosition;
	}
	
	public Position newPosition() {
		return newPosition;
	}
	
	@Override
	public boolean isValid(Board board) {
	    
	    if (oldPosition.row() < 1 || oldPosition.column() > 8 
	            || newPosition.row() < 1 || newPosition.column() > 8) {
	        return false;
	    }
	    
		return piece.isValidMove(oldPosition, newPosition, null, board);
	}
	
	@Override
	public void apply(Board b) {
		b.move(oldPosition,newPosition);
	}
	
	public String toString() {
		return pieceChar(piece) + oldPosition + "-" + newPosition; 
	}
	
	protected static String pieceChar(Piece p) {
		if(p instanceof Pawn) {
			return "";
		} else if(p instanceof Knight) {
			return "N";
		} else if(p instanceof Bishop) {
			return "B";
		} else if(p instanceof Rook) {
			return "R";
		} else if(p instanceof Queen) {
			return "Q";
		} else {
			return "K";
		}
	}
}
