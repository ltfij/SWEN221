package cathedral;

import java.util.ArrayList;
import java.util.HashMap;

import cathedral.io.GameError;
import cathedral.moves.Move;
import cathedral.pieces.*;
import cathedral.util.Position;

public class Game {

	/**
	 * Represents one of the two players in the game (black or white)
	 * 
	 * @author David J. Pearce
	 * 
	 */
	public enum Player {
		BLACK,
		WHITE;
	}

	/**
	 * Represents one of the four position of the compass (North, East, South and
	 * West). This is used to give each piece an "orientation" when its placed on
	 * the board.
	 * 
	 * @author David J. Pearce
	 * 
	 */
	public enum Direction {
		NORTH,
		EAST,
		SOUTH,
		WEST;
	}
	
	/**
	 * Stores the width of the board.
	 */
	private int width;
	
	/**
	 * Stores the height of the board.
	 */
	private int height;
	
	/**
	 * A 2-dimenional array representing the board itself.
	 */
	private Building[][] board;
	
	/**
	 * The array of moves which make up this game.
	 */
	private Move[] moves;
	
	private HashMap<String, Integer> memory;
	
	private ArrayList<Area> whitePlace;
	private ArrayList<Area> blackPlace;

		
	/**
	 * Construct a game with a board of a given width and height, and a sequence
	 * of moves to validate. Initially, the board is empty.
	 * 
	 * @param width
	 * @param height
	 * @param moves
	 */
	public Game(int width, int height, Move[] moves) {
		this.width = width;
		this.height = height;
		this.board = new Building[height][width];
		this.moves = moves;
		memory = new HashMap<>();
		whitePlace = new ArrayList<>();
		blackPlace = new ArrayList<>();
	}
	
	/**
	 * Get the width (in squares) of the board.
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Get the height (in squares) of the board.
	 * @return
	 */
	public int getHeight() {
		return height;
	}
		
	
	
	/**
	 * Get the building occupying a given square, or null if none.
	 * 
	 * @param x
	 *            Horizontal position on board
	 * @param y
	 *            Vertical position on board
	 * @return
	 */
	public Building getSquare(int x, int y) {
		return board[y][x];
	}
	
	/**
	 * Get the holder of a particular square, or null if none. The holder of a
	 * square is the player who captured that square. If the square is not
	 * captured, then there is no holder.
	 * 
	 * @param x
	 *            Horizontal position on board
	 * @param y
	 *            Vertical position on board
	 * @return
	 */
	public Game.Player getHolderOfSquare(int x, int y) {
		
		// FIXME: implement this function for part 6 only
	    
	    for (Area a : whitePlace) {
	        if (a.contains(new Position(x, y))) {
	            return Game.Player.WHITE;
	        }
	    }
	    
	    for (Area a : blackPlace) {
            if (a.contains(new Position(x, y))) {
                return Game.Player.BLACK;
            }
        }
	    
		return null;

	}
	
	/**
	 * Register that one player has captured an area of the board. Any pieces of
	 * the opposing player contained within the area are automatically removed.
	 * 
	 * @param player
	 * @param area
	 */
	public void capture(Game.Player player, Area area) {
		
		// FIXME: implement this function for part 6 only
	    
	    if (player == Game.Player.WHITE) {
	        whitePlace.add(area);
	    } else {
	        blackPlace.add(area);
	    }
	    
	    for (int j = 0; j < height; j++) {
	        for (int i = 0; i < width; i++) {
	            
	            Position p = new Position(i, j);
	            
	            if (board[j][i] != null && board[j][i].getPlayer() != player && area.contains(p)){
	                      
	                
	                if (board[j][i].getTemplate() instanceof L_Building) {
	                    if (player == Game.Player.WHITE) {
	                        memory.put("LB", memory.get("LB")-1);
	                    } else {
	                        memory.put("LW", memory.get("LW")-1);
	                    }
	                }
	                
	                if (board[j][i].getTemplate() instanceof S_Building) {
                        if (player == Game.Player.WHITE) {
                            memory.put("LB", memory.get("SB")-1);
                        } else {
                            memory.put("LW", memory.get("SW")-1);
                        }
                    }
	                
	                if (board[j][i].getTemplate() instanceof T_Building) {
                        if (player == Game.Player.WHITE) {
                            memory.put("TB", memory.get("TB")-1);
                        } else {
                            memory.put("TW", memory.get("TW")-1);
                        }
                    }
	                
	                for(Position square : board[j][i].getSquares()) {
                        board[square.getY()][square.getX()] = null;
                    } 
	                
//	                if (board[j][i].getTemplate() instanceof Cathedral) {
//                        
//                            memory.put("C", memory.get("TW")-1);
//                        
//                    }
	                
	            }
	            
	            
	            
	        }
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
//	    if (area.) {
//	        
//	    }
	    
		
	}
	
	/**
	 * Update the board by placing a given piece onto it. As part of this, the
	 * record of pieces currently held by each player needs to be updated as
	 * well.
	 * 
	 * @param building
	 * @throws GameError 
	 */
	public void place(Building building) throws GameError {
		// First, update board
		for(Position square : building.getSquares()) {
			board[square.getY()][square.getX()] = building;
		}		
		// Second, adjust count of player's pieces
		if (building.getTemplate() instanceof Cathedral) {
		    int num;
		    if (memory.get("C") == null) {
		        num = 0;
		    } else {
		        num = memory.get("C");
		    }
		    
		    memory.put("C", num+1);
		    
		    if (memory.get("C") >1) {
		        throw new GameError("wrong building number");
		    }

		}
		
		if (building.getTemplate() instanceof L_Building && building.getPlayer() == Player.WHITE) {
            int num;
            if (memory.get("LW") == null) {
                num = 0;
            } else {
                num = memory.get("LW");
            }
            
            memory.put("LW", num+1);
            
            if (memory.get("LW") >2) {
                throw new GameError("wrong building number");
            }

        }
		
		if (building.getTemplate() instanceof L_Building && building.getPlayer() == Player.BLACK) {
            int num;
            if (memory.get("LB") == null) {
                num = 0;
            } else {
                num = memory.get("LB");
            }
            
            memory.put("LB", num+1);
            
            if (memory.get("LB") >2) {
                throw new GameError("wrong building number");
            }

        }
		
		if (building.getTemplate() instanceof S_Building && building.getPlayer() == Player.WHITE) {
            int num;
            if (memory.get("SW") == null) {
                num = 0;
            } else {
                num = memory.get("SW");
            }
            
            memory.put("SW", num+1);
            
            if (memory.get("SW") >2) {
                throw new GameError("wrong building number");
            }

        }
        
        if (building.getTemplate() instanceof S_Building && building.getPlayer() == Player.BLACK) {
            int num;
            if (memory.get("SB") == null) {
                num = 0;
            } else {
                num = memory.get("SB");
            }
            
            memory.put("SB", num+1);
            
            if (memory.get("SB") >2) {
                throw new GameError("wrong building number");
            }

        }
		
		
        if (building.getTemplate() instanceof T_Building && building.getPlayer() == Player.WHITE) {
            int num;
            if (memory.get("TW") == null) {
                num = 0;
            } else {
                num = memory.get("TW");
            }
            
            memory.put("TW", num+1);
            
            if (memory.get("TW") >2) {
                throw new GameError("wrong building number");
            }

        }
        
        if (building.getTemplate() instanceof T_Building && building.getPlayer() == Player.BLACK) {
            int num;
            if (memory.get("TB") == null) {
                num = 0;
            } else {
                num = memory.get("TB");
            }
            
            memory.put("TB", num+1);
            
            if (memory.get("TB") >2) {
                throw new GameError("wrong building number");
            }

        }

	}			
		
	/**
	 * Run this game to produce the final board, whilst also checking each move
	 * against the rules of Cathedral. In the event of an invalid move being
	 * encountered, then a syntax error should be thrown.
	 */
	public void run() throws GameError {
		for(int i=0;i!=moves.length;++i) {
			Move move = moves[i];			
			move.apply(this);			
		}
	}
	
	/**
	 * Provide a human-readable view of the current game board. This is
	 * particularly useful to look at when debugging your code!
	 */
	public String toString() {
		String r = "";
		for(int i=height-1;i>=0;--i) {
			r += i;
			for(int j=0;j!=width;++j) {
				r += "|";
				Building b = board[i][j];
				if(b == null) {
					Game.Player holder = getHolderOfSquare(j,i);
					if(holder == Game.Player.WHITE) {
						r += "w";
					} else if(holder == Game.Player.BLACK) {
						r += "b";
					} else {
						r += "_";
					}
				} else if(b.getPlayer() == null) {
					r += "C"; // the cathedral is not owned by either player
				} else if(b.getPlayer() == Game.Player.WHITE){
					r += "W";
				} else {
					r += "B";
				}
			}
			r += "|\n";
		}
		r += " ";
		// Do the X-Axis
		for(int j=0;j!=width;++j) {
			r += " " + j;
		}
		return r;
	}
}
