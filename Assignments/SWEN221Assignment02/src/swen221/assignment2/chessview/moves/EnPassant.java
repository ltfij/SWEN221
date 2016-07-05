package swen221.assignment2.chessview.moves;

import swen221.assignment2.chessview.*;
import swen221.assignment2.chessview.pieces.*;

/**
 * This represents an "en passant move" --- http://en.wikipedia.org/wiki/En_passant.
 * 
 * @author djp
 * 
 */
public class EnPassant implements MultiPieceMove {
    private SinglePieceMove move;
    private Position enPassantPosition;  // the position that the opposing pawn is taken
    
	public EnPassant(SinglePieceMove move) {
	    this.move = move;
	    enPassantPosition = null;
	}
	
	@Override
	public boolean isWhite() {
		return this.move.piece().isWhite();
	}
	
	@Override
	public boolean isValid(Board board) {
	    Piece piece = move.piece();
	    Position oldPosition = move.oldPosition();
	    Position newPosition = move.newPosition();
        int oldRow = oldPosition.row();
        int newCol = newPosition.column();
        enPassantPosition = new Position(oldRow, newCol);
        Piece isTaken = board.pieceAt(enPassantPosition);
        
        // don't take pieces other than pawn by en passent
        if (!(isTaken instanceof Pawn) || isTaken == null) {
            return false;
        }
        
        // a String of the taken pawn's colour
        String isTakenColour = !piece.isWhite() ? "white" : "black";
        // a String of the taken pawn's row
        String isTakenRow = !piece.isWhite() ? "2" : "7";
        // whether the opponent took a 2-step forward
        boolean opponentTook2Step = board.getPreviousMoves().get(isTakenColour + " pawn " + 
                isTakenRow + newPosition.column() + " moved 2-step");
        
        if (!(piece instanceof Pawn) || !opponentTook2Step
                || (piece.isWhite() && oldRow != 5) || (!piece.isWhite() && oldRow != 4)) {
            return false;
        }
        
        return piece.isValidMove(oldPosition, newPosition, isTaken, board); 
	}
	
	@Override
	public void apply(Board board) {	
        board.setPieceAt(enPassantPosition, null);
	    board.move(move.oldPosition(), move.newPosition());

	}
	
	public String toString() {
		return "ep";
	}
}
