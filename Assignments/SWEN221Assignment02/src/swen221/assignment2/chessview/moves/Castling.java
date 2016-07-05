package swen221.assignment2.chessview.moves;

import java.util.ArrayList;

import swen221.assignment2.chessview.*;
import swen221.assignment2.chessview.pieces.*;

public class Castling implements MultiPieceMove {
	private boolean kingSide;
	private boolean isWhite;
	
	public Castling(boolean isWhite, boolean kingSide) {
		this.kingSide = kingSide;
		this.isWhite = isWhite;
	}
	
	@Override
	public boolean isWhite() {
		return isWhite;
	}
	
	@Override
	public boolean isValid(Board board) {
	    
	    //=====1.Neither the king nor the chosen rook has previously moved===========
	    // a String of current colour
        String colourString = isWhite ? "white" : "black";
        // a String of the involved rook's position
        String rookPosString = (isWhite ? "1" : "8") + (kingSide ? "8" : "1");
        boolean kingMoved = board.getPreviousMoves().get(colourString + " king moved");
        boolean rookMoved = board.getPreviousMoves().get(colourString + " rook " + rookPosString + " moved");
        if (kingMoved || rookMoved) {
            return false;
        }
        
        //=====2.There are no pieces between the king and the chosen rook=========
        Position kingPosition = new Position((isWhite ? 1 : 8) , 5);
        Position rookPosition = new Position((isWhite ? 1 : 8) , (kingSide ? 8 : 1));
        Piece king = board.pieceAt(kingPosition);
        Piece rook = board.pieceAt(rookPosition);
        if (!(king instanceof King) || !(rook instanceof Rook)) {
            return false; 
        }
	    boolean noObstacles = board.clearRowExcept(kingPosition, rookPosition, king, rook);
        if (!noObstacles) {
            return false;
        }
	    
        //=====3.One may not castle out of, through, or into check===========
        if (board.isInCheck(isWhite)) {
            return false;
        }

        // the path that the king castles through, including where he end in.
        ArrayList<Position> kingsPath = new ArrayList<>();
        if (isWhite() && kingSide) {
            kingsPath.add(new Position(1 , 6));
            kingsPath.add(new Position(1 , 7));
        } else if (isWhite() && !kingSide) {
            kingsPath.add(new Position(1 , 4));
            kingsPath.add(new Position(1 , 3));
        } else if (!isWhite() && kingSide) {
            kingsPath.add(new Position(8 , 6));
            kingsPath.add(new Position(8 , 7));
        } else if (!isWhite() && !kingSide) {
            kingsPath.add(new Position(8 , 4));
            kingsPath.add(new Position(8 , 3));
        }
        // any position in the path is being checked?
        for (Position pos : kingsPath) {
            Board newBoard = new Board(board);
            newBoard.setPieceAt(pos, king);
            newBoard.setPieceAt(kingPosition, null);
            if (newBoard.isInCheck(isWhite)) {
                return false;
            }
        }
        
	    return true;
	}
	
	@Override
	public void apply(Board board) {
	    Position kingPosition = new Position((isWhite ? 1 : 8) , 5);
	    Position kingNewPosition = new Position((isWhite ? 1 : 8), (kingSide ? 7 : 3));
        Position rookPosition = new Position((isWhite ? 1 : 8) , (kingSide ? 8 : 1));
        Position rookNewPosition = new Position((isWhite ? 1 : 8) , (kingSide ? 6 : 4));
	    
	    board.move(kingPosition, kingNewPosition);
	    board.move(rookPosition, rookNewPosition);
	}
	
	public String toString() {
		if(kingSide) {
			return "O-O";
		} else {
			return "O-O-O";
		}
	}
}
