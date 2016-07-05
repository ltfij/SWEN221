package swen221.assignment2.chessview.moves;

import swen221.assignment2.chessview.*;
import swen221.assignment2.chessview.pieces.Piece;

public class SinglePieceTake extends SinglePieceMove {
	public Piece isTaken;
	
	public SinglePieceTake(Piece piece, Piece isTaken, Position oldPosition, Position newPosition) {
		super(piece,oldPosition,newPosition);
		this.isTaken = isTaken;
	}
	
	@Override
	public boolean isValid(Board board) {
	    
	    // XXX this might be redundant, cos in ChessGame.moveFromString(...), isTaken is 
	    // constructed as opposite color, means this conditional is always false.
	    // but it looks safer anyway...
	    if (piece.isWhite() == isTaken.isWhite()) {
	        return false;
	    }
	    
		return piece.isValidMove(oldPosition, newPosition, isTaken, board);
	}
	
	public String toString() {
		return pieceChar(piece) + oldPosition + "x" + pieceChar(isTaken) + newPosition; 
	}	
}
