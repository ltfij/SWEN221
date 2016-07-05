package swen221.assignment2.chessview.pieces;

import swen221.assignment2.chessview.*;

public class Pawn extends PieceImpl implements Piece {
    
	public Pawn(boolean isWhite) {
		super(isWhite);
	}
	
	/**
	 * this method first checks a special invalid move that the pawn remains a pawn 
	 * when he reaches the final rank. After this check, it delegate all the logic 
	 * check to isValidMoveWithoutPromotionCheck(...) method.
	 */
	public boolean isValidMove(Position oldPosition, Position newPosition,
			Piece isTaken, Board board) {
	    
	    // if the pawn reach the final rank, but doesn't promote, it's invalid
	    // this check excludes the case that the pawn takes the king
		if (newPosition.row() == (isWhite ? 8 : 1) && !(isTaken instanceof King)) {
		    return false;
		}
	    
	    return isValidMoveWithoutPromotionCheck(oldPosition, newPosition, isTaken, board);
	}
	
	/**
	 * This method checks whether the logic is right for the pawn to move or take.
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
	public boolean isValidMoveWithoutPromotionCheck(Position oldPosition, Position newPosition,
            Piece isTaken, Board board) {
	    
	    int dir = isWhite ? 1 : -1;
        int oldRow = oldPosition.row();
        int oldCol = oldPosition.column();
        int newRow = newPosition.row();
        int newCol = newPosition.column();

        Piece pieceAtOldPos = board.pieceAt(oldPosition);
        Piece pieceAtNewPos = board.pieceAt(newPosition);
        
        Position enPassantPosition = new Position(oldRow, newCol);
        Piece enPassantPawn = board.pieceAt(enPassantPosition);
        
        // non-take move
        if (isTaken == null) {
            if ((oldRow + dir + dir) == newRow && oldCol == newCol) { // 2-step forward
                return (isWhite ? (oldRow == 2) : (oldRow == 7))
                        && pieceAtNewPos == null && this.equals(pieceAtOldPos)
                        && board.clearColumnExcept(oldPosition, newPosition, pieceAtOldPos, pieceAtNewPos);
            } else if ((oldRow + dir) == newRow && oldCol == newCol) {  // one step forward
                return  pieceAtNewPos == null && this.equals(pieceAtOldPos);
            } else {
                return false;
            }
        // take move
        } else {
            return this.equals(pieceAtOldPos)
                    && (isTaken.equals(pieceAtNewPos) || isTaken.equals(enPassantPawn))
                    && (oldRow + dir) == newRow
                    && Math.abs(newCol - oldCol) == 1; 
        }
	}
	
	public String toString() {
		if(isWhite) {
			return "P";
		} else {
			return "p";
		}
	}
}
