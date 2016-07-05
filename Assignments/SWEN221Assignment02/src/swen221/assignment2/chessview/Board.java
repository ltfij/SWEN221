package swen221.assignment2.chessview;

import java.util.HashMap;

import swen221.assignment2.chessview.moves.Move;
import swen221.assignment2.chessview.pieces.*;

public class Board {
    
	private Piece[][] pieces; // this is the underlying data structure for a board.
	// a collection of several boolean values for complicated moves, see constructor.
	private HashMap<String, Boolean> previousMoves;

	/**
	 * Construct an initial board.
	 */
	public Board() {
		pieces = new Piece[9][9];
		
		for(int i=1;i<=8;++i) {
			pieces[2][i] = new Pawn(true);
			pieces[7][i] = new Pawn(false);
		}

		// rooks
		pieces[1][1] = new Rook(true);
		pieces[1][8] = new Rook(true);
		pieces[8][1] = new Rook(false);
		pieces[8][8] = new Rook(false);

		// knights
		pieces[1][2] = new Knight(true);
		pieces[1][7] = new Knight(true);
		pieces[8][2] = new Knight(false);
		pieces[8][7] = new Knight(false);

		// bishops
		pieces[1][3] = new Bishop(true);
		pieces[1][6] = new Bishop(true);
		pieces[8][3] = new Bishop(false);
		pieces[8][6] = new Bishop(false);

		// king + queen
		pieces[1][4] = new Queen(true);
		pieces[1][5] = new King(true);
		pieces[8][4] = new Queen(false);
		pieces[8][5] = new King(false);
		
		previousMoves = new HashMap<>();
		// This is what the map is like initially
		// Strings are all lower case characters
		previousMoves.put("white king moved", false);
		previousMoves.put("black king moved", false);
		previousMoves.put("white rook 11 moved", false);
		previousMoves.put("white rook 18 moved", false);
		previousMoves.put("black rook 81 moved", false);
		previousMoves.put("black rook 88 moved", false);
		previousMoves.put("white pawn 21 moved 2-step", false);
		previousMoves.put("white pawn 22 moved 2-step", false);
		previousMoves.put("white pawn 23 moved 2-step", false);
		previousMoves.put("white pawn 24 moved 2-step", false);
		previousMoves.put("white pawn 25 moved 2-step", false);
		previousMoves.put("white pawn 26 moved 2-step", false);
		previousMoves.put("white pawn 27 moved 2-step", false);
		previousMoves.put("white pawn 28 moved 2-step", false);
		previousMoves.put("black pawn 71 moved 2-step", false);
		previousMoves.put("black pawn 72 moved 2-step", false);
		previousMoves.put("black pawn 73 moved 2-step", false);
		previousMoves.put("black pawn 74 moved 2-step", false);
		previousMoves.put("black pawn 75 moved 2-step", false);
		previousMoves.put("black pawn 76 moved 2-step", false);
		previousMoves.put("black pawn 77 moved 2-step", false);
		previousMoves.put("black pawn 78 moved 2-step", false);
		
	}

	/**
	 * Construct a board which is identical to another board.
	 * 
	 * @param board
	 */
	public Board(Board board) {
		this.pieces = new Piece[9][9];
		for(int row=1;row<=8;++row) {
			for(int col=1;col<=8;++col) {
				this.pieces[row][col] = board.pieces[row][col];
			}
		}
		// copy the memory as well
		this.previousMoves = board.previousMoves;
	}

	/**
	 * Apply a given move to this board, returning true is successful, otherwise
	 * false.
	 * 
	 * @param move
	 * @return
	 */
	public boolean apply(Move move) {
		if(move.isValid(this)) {		
			move.apply(this);
			return true;			
		} else {			
			return false;			
		}
	}
	
	/**
	 * Move a piece from one position to another.
	 * This method also detects what piece is about to make the move,
	 * and update involved boolean values stored in the private field:
	 * previousMoves.
	 * 
	 * @param oldPosition
	 * @param newPosition
	 */
	public void move(Position oldPosition, Position newPosition) {
	    
	    Piece p = pieces[oldPosition.row()][oldPosition.column()];
	    
	    /*
	     * what piece is about to make this move?
	     * A pawn moved two steps? A king moved? A Rook moved?
	     * update the previousMoves Map.
	     */
	    if (p instanceof Pawn) {
	        // if the pawn is making a 2-step forward, set the involved boolean to true
            if (Math.abs(newPosition.row() - oldPosition.row()) == 2) {
                for (int col = 1; col < 9; col++) {
                    if (p.isWhite() && oldPosition.row() == 2 && oldPosition.column() == col) {
                        previousMoves.put("white pawn 2" + col + " moved 2-step", true);
                    } else if (!p.isWhite() && oldPosition.row() == 7 && oldPosition.column() == col) {
                        previousMoves.put("black pawn 7" + col + " moved 2-step", true);
                    }
                }
            } else { // otherwise set all pawn booleans to false (pawns with same colour)
                setPawnBoolsToFalse(p.isWhite());
            }
        } else {
            // if not a pawn move, set all pawn booleans to false (pawns with same colour)
            setPawnBoolsToFalse(p.isWhite());
            
            if (p instanceof King) {
                // the king has moved (no chance to do a castling)
                if (p.isWhite()) {
                    previousMoves.put("white king moved", true);
                } else {
                    previousMoves.put("black king moved", true);
                }
            } else if (p instanceof Rook) {
                // one of the rooks has moved (no chance to do a castling with this rook)
                if (p.isWhite() && oldPosition.row() == 1 && oldPosition.column() == 1) {
                    previousMoves.put("white rook 11 moved", true);
                } else if (p.isWhite() && oldPosition.row() == 1 && oldPosition.column() == 8) {
                    previousMoves.put("white rook 18 moved", true);
                } else if (!p.isWhite() && oldPosition.row() == 8 && oldPosition.column() == 1) {
                    previousMoves.put("black rook 81 moved", true);
                } else if (!p.isWhite() && oldPosition.row() == 8 && oldPosition.column() == 8) {
                    previousMoves.put("black rook 88 moved", true);
                }
            }
        }

		pieces[newPosition.row()][newPosition.column()] = p;
		pieces[oldPosition.row()][oldPosition.column()] = null;
	}
	
	/**
	 * A helper method, to set pawn booleans to false. This method only does changes to
	 * one side according to the colour given as argument
	 * 
	 * @param isWhite     true to set all booleans of white pawns to false; false to set
	 *                    the opposite side booleans to false.
	 */
	private void setPawnBoolsToFalse(boolean isWhite) {
	    if (isWhite) {
	        previousMoves.put("white pawn 21 moved 2-step", false);
	        previousMoves.put("white pawn 22 moved 2-step", false);
	        previousMoves.put("white pawn 23 moved 2-step", false);
	        previousMoves.put("white pawn 24 moved 2-step", false);
	        previousMoves.put("white pawn 25 moved 2-step", false);
	        previousMoves.put("white pawn 26 moved 2-step", false);
	        previousMoves.put("white pawn 27 moved 2-step", false);
	        previousMoves.put("white pawn 28 moved 2-step", false);
	    } else {
	        previousMoves.put("black pawn 71 moved 2-step", false);
	        previousMoves.put("black pawn 72 moved 2-step", false);
	        previousMoves.put("black pawn 73 moved 2-step", false);
	        previousMoves.put("black pawn 74 moved 2-step", false);
	        previousMoves.put("black pawn 75 moved 2-step", false);
	        previousMoves.put("black pawn 76 moved 2-step", false);
	        previousMoves.put("black pawn 77 moved 2-step", false);
	        previousMoves.put("black pawn 78 moved 2-step", false);
	    } 
	}
	
	public void setPieceAt(Position pos, Piece piece) {
		pieces[pos.row()][pos.column()] = piece;
	}
	
	public Piece pieceAt(Position pos) {
		return pieces[pos.row()][pos.column()];
	}
	
	/**
	 * Get the recordings of previous moves
	 * 
	 * @return
	 */
	public HashMap<String, Boolean> getPreviousMoves() {
        return this.previousMoves;
    }
	
	public String toString() {
		String r = "";
		for(int row=8;row!=0;row--) {
			r += row + "|";
			for(int col=1;col<=8;col++) {				
				Piece p = pieces[row][col];
				if(p != null) {					
					r += p + "|";
				} else {
					r += "_|";
				}				
			}
			r += "\n";
		}
		return r + "  a b c d e f g h";
	}

	/**
	 * This method determines whether or not one side is in check.
	 * 
	 * @param isWhite
	 *            --- true means check whether white is in check; otherwise,
	 *            check black.
	 * @return
	 */
	public boolean isInCheck(boolean isWhite) {
		King king = null; // opposition king
		Position kingPos = null;

		// First, find my king
		outer: for (int row = 1; row <= 8; ++row) {
			for (int col = 1; col <= 8; ++col) {
				Position pos = new Position(row, col);
				Piece p = pieceAt(pos);
				if (p instanceof King && p.isWhite() == isWhite) {
					// found him.
					king = (King) p;
					kingPos = pos;
					// The following will break out of the entire loop, not
					// just the innermost loop. This isn't exactly great
					// style, but it is pretty convenient here.
					break outer;
				}
			}
		}

		// Second, check opposition pieces to see whether they can take
		// my king or not.  If one can, we're in check!
		for (int row = 1; row <= 8; ++row) {
			for (int col = 1; col <= 8; ++col) {
				Position pos = new Position(row, col);
				Piece p = pieceAt(pos);
				// If this is an opposition piece, and it can take my king,
				// then we're definitely in check.
				if (p != null && p.isWhite() != isWhite
						&& p.isValidMove(pos, kingPos, king, this)) {
					// p can take opposition king, so we're in check.						
					return true;
				}
			}
		}
		
		// couldn't find any piece in check.
		return false;
	}
	
	/**
	 * The following method checks whether the given diaganol is completely
	 * clear, except for a given set of pieces. Observe that this doesn't
	 * guarantee a given diaganol move is valid, since this method does not
	 * ensure anything about the relative positions of the given pieces.
	 * 
	 * @param startPosition - start of diaganol
	 * @param endPosition - end of diaganol
	 * @param exceptions - the list of pieces allowed on the diaganol
	 * @return
	 */
	public boolean clearDiaganolExcept(Position startPosition,
			Position endPosition, Piece... exceptions) {			
		int startCol = startPosition.column();
		int endCol = endPosition.column();
		int startRow = startPosition.row();
		int endRow = endPosition.row();		
		int diffCol = Math.max(startCol,endCol) - Math.min(startCol,endCol);
		int diffRow = Math.max(startRow,endRow) - Math.min(startRow,endRow);		
		
		if(diffCol != diffRow && diffCol == 0) {			
			return false;
		}
		
		int row = startRow;
		int col = startCol;
		while(row != endRow && col != endCol) {			
			Piece p = pieces[row][col];			
			if(p != null && !contains(p,exceptions)) {				
				return false;
			}
			col = col <= endCol ? col + 1 : col - 1;
			row = row <= endRow ? row + 1 : row - 1;
		}				
		
		return true;
	}

	/**
	 * The following method checks whether the given column is completely
	 * clear, except for a given set of pieces. Observe that this doesn't
	 * guarantee a given column move is valid, since this method does not
	 * ensure anything about the relative positions of the given pieces.
	 * 
	 * @param startPosition - start of column
	 * @param endPosition - end of column
	 * @param exceptions - the list of pieces allowed on the column
	 * @return
	 */
	public boolean clearColumnExcept(Position startPosition,
			Position endPosition, Piece... exceptions) {			
		int minCol = Math.min(startPosition.column(), endPosition.column());
		int maxCol = Math.max(startPosition.column(), endPosition.column());
		int minRow = Math.min(startPosition.row(), endPosition.row());
		int maxRow = Math.max(startPosition.row(), endPosition.row());		
		int diffCol = maxCol - minCol;
		int diffRow = maxRow - minRow;
		
		if(diffCol != 0 || diffRow == 0) {
			return false;
		}
		
		int row = minRow;
        while(row != maxRow) {         
            Piece p = pieces[row][minCol];         
            if(p != null && !contains(p,exceptions)) {              
                return false;
            }
            row++;
        }   
        	
		return true;
	}
	
	/**
	 * The following method checks whether the given row is completely
	 * clear, except for a given set of pieces. Observe that this doesn't
	 * guarantee a given row move is valid, since this method does not
	 * ensure anything about the relative positions of the given pieces.
	 * 
	 * @param startPosition - start of row
	 * @param endPosition - end of row
	 * @param exceptions - the list of pieces allowed on the row
	 * @return
	 */
	public boolean clearRowExcept(Position startPosition,
			Position endPosition, Piece... exceptions) {			
		int minCol = Math.min(startPosition.column(), endPosition.column());
		int maxCol = Math.max(startPosition.column(), endPosition.column());
		int minRow = Math.min(startPosition.row(), endPosition.row());
		int maxRow = Math.max(startPosition.row(), endPosition.row());		
		int diffCol = maxCol - minCol;
		int diffRow = maxRow - minRow;
		
		if(diffRow != 0 || diffCol == 0) {
			return false;
		}
		
		int col = minCol;		
		while(col != maxCol) {			
			Piece p = pieces[minRow][col];			
			if(p != null && !contains(p,exceptions)) {			
				return false;
			}					
			col++; 
		}
		
		return true;
	}
	
	// Helper method for the clear?????Except methods above.
	private static boolean contains(Piece p1, Piece... pieces) {		
		for(Piece p2 : pieces) {						
			if(p1 == p2) {									
				return true;
			}
		}									
		
		return false;
	}	
}
