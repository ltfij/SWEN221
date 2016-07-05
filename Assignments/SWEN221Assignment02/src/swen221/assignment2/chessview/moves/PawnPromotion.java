package swen221.assignment2.chessview.moves;

import swen221.assignment2.chessview.*;
import swen221.assignment2.chessview.pieces.*;

/**
 * This represents a pawn promotion.
 * @author djp
 *
 */
public class PawnPromotion implements MultiPieceMove {
    private SinglePieceMove move;
	private Piece promotion;
	
	public PawnPromotion(SinglePieceMove move, Piece promotion) {
	    this.move = move;
	    this.promotion = promotion;
	}
	
	@Override
	public boolean isWhite() {
		return move.piece().isWhite();
	}
	
	@Override
	public boolean isValid(Board board) {
	    Position oldPosition = move.oldPosition();
        Position newPosition = move.newPosition();
        int newRow = newPosition.row();
        Piece piece = move.piece();
        
        if (!(piece instanceof Pawn)) {
            return false;
        }
        
        Piece isTaken;
        if (move instanceof SinglePieceTake) {  // in case this is a take move, could be enPassent pawn
            isTaken = ((SinglePieceTake) move).isTaken;
        } else {
            isTaken = board.pieceAt(newPosition);  // XXX this should always be null, yes?
        }

        boolean promotionLogic = piece.isWhite() ? newRow == 8 : newRow == 1;
        
        return ((Pawn)piece).isValidMoveWithoutPromotionCheck(oldPosition, newPosition, isTaken, board)
                && promotionLogic;
	}
	
	@Override
	public void apply(Board board) {
	    board.move(move.oldPosition(), move.newPosition());
	    board.setPieceAt(move.newPosition(), promotion);
	    
	}
	
	public String toString() {
		return super.toString() + "=" + SinglePieceMove.pieceChar(promotion);
	}
}
