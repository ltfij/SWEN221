package minesweeper.moves;


import java.util.ArrayList;

import minesweeper.*;
import minesweeper.squares.*;

/**
 * Represents a move that exposes a square. The square may contain a bomb, or be
 * blank. However, it should not be flagged.
 * 
 * @author David J. Pearce
 * 
 */
public class ExposeMove extends Move {
	
	/**
	 * Construct an ExposeMove at a given position on the board.
	 * 
	 * @param position
	 */
	public ExposeMove(Position position) {
		super(position);
	}

	/**
	 * Apply this move to a given game and check it is valid. A square can only
	 * be exposed if it is not already exposed. And, if either a bomb is exposed
	 * or there are no remaining unexposed squares, then this should be an
	 * EndGame move.
	 * 
	 * @param game
	 *            --- game to which this move is applied.
	 */
	public void apply(Game game) throws SyntaxError {
		Square square = game.squareAt(position);

		if (square.isFlagged() || !square.isHidden()) {
		    throw new SyntaxError("Invalid sheet");
		}
		
		square.setHidden(false); // now exposed
		
		if (square instanceof BlankSquare) {
		    BlankSquare bSquare = (BlankSquare)square;
		    int getNumberOfBombsAround = bSquare.getNumberOfBombsAround();
		    
		    if (getNumberOfBombsAround == 0) {
		        ArrayList<Position> neighbours = getHiddenNeighbours(position, game);
		        for (Position nbr : neighbours) {
		            Square nbrSquare = game.squareAt(nbr);
		            if (nbrSquare instanceof BlankSquare && nbrSquare.isHidden()) {
		                (new ExposeMove(nbr)).apply(game);
		            }
		        }
		    }
		}
	}

	private ArrayList<Position> getHiddenNeighbours(Position position, Game game) {
	    
	    int width = game.getWidth();
	    int height = game.getHeight();
	    
	    int x = position.getX();
	    int y = position.getY();
	    
	    ArrayList<Position> neighbours = new ArrayList<>();
	    
	    // (x-1,y-1), (x,y-1), (x+1,y-1), (x-1,y), (x+1,y), (x-1,y+1), (x,y+1) and (x+1,y+1).
	    int [][] potentialNeighbours = {{x-1,y-1}, {x,y-1}, {x+1,y-1}, {x-1,y},
	            {x+1,y}, {x-1,y+1}, {x,y+1}, {x+1,y+1}};
	    
	    for (int[] neighbour : potentialNeighbours) {
	        if ((neighbour[0] >=0 && neighbour[0] < width)
	                && (neighbour[1] >=0 && neighbour[1] < height)) {
	            Position newOne = new Position(neighbour[0], neighbour[1]);
	            Square newSquare = game.squareAt(newOne);
	            if (newSquare.isHidden() && newSquare instanceof BlankSquare) {
	                neighbours.add(newOne);
	            }
	        }
	    }
	    
	    return neighbours;
    }

    public String toString() {		
		return "E" + position;		
	}
}
