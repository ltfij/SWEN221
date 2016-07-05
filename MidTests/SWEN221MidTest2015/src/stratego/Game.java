package stratego;

import stratego.io.GameError;
import stratego.moves.Move;
import stratego.pieces.*;
import stratego.util.Position;

/**
 * Represents the state of a game of Stratego. In particular, the game holds the
 * position of each piece on the board and the list of moves being played.
 *
 * @author David J. Pearce
 *
 */
public class Game {
	/**
	 * Represents one of the two players in the game (black or white)
	 *
	 * @author David J. Pearce
	 *
	 */
	public enum Player {
		RED,
		BLUE;
	}

	/**
	 * Represents one of the four position of the compass (North, East, South and
	 * West). This is used to the direction in which a given piece moves.
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
	private Piece[][] board;

	/**
	 * The array of moves which make up this game.
	 */
	private Move[] moves;
	
	private boolean redFlagCaptured = false;
	private boolean blueFlagCaptured = false;
	
	private boolean redCannotMove = false;
    private boolean blueCannotMove = false;

	/**
	 * Construct a game of Stratego
	 *
	 * @param moves
	 *            --- The moves that make up the game
	 */
	public Game(Move[] moves) {
		this.moves = moves;
		this.width = 10;
		this.height = 10;
		board = new Piece[height][width];
	}

	/**
	 * Get the height of the game board.
	 * @return
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Get the width of the game board.
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Run this game to produce the final board, whilst also checking each move
	 * against the rules of Stratego. In the event of an invalid move being
	 * encountered, then a game error should be thrown.
	 */
	public void run() throws GameError {
		for(int i=0;i!=moves.length;++i) {
			Move move = moves[i];
			move.apply(this);
		}
	}

	/**
	 * Get the piece at a given position on the board, or null if no piece at
	 * that position.
	 *
	 * @param pos
	 * @return
	 */
	public Piece getPiece(Position pos) {
		return board[pos.getY()][pos.getX()];
	}

	/**
	 * Set the piece at a given position on the board. Note, this will overwrite
	 * the record of any other piece being at that position. position.
	 *
	 * @param pos
	 * @return
	 */
	public void setPiece(Position pos, Piece p) {
		board[pos.getY()][pos.getX()] = p;
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
				Piece b = board[i][j];
				if(b instanceof ImpassableTerrain) {
					r += "##";
				} else if(b != null) {
					r += b.getPlayer() == Game.Player.BLUE ? "b" : "r";
					if(b instanceof BombPiece) {
						r += "B";
					} else if(b instanceof FlagPiece) {
						r += "F";
					} else if(b instanceof RegularPiece) {
						RegularPiece p = (RegularPiece) b;
						r += p.getRank();
					}
				} else {
					r += "__";
				}
			}
			r += "|\n";
		}
		r += " ";
		// Do the X-Axis
		for(int j=0;j!=width;++j) {
			r += " " + j + " ";
		}
		return r;
	}

	/**
	 * Initialse the board from a given input board. This includes the placement
	 * of all terrain and pieces.
	 */
	public void initialiseBoard(String boardString) {
		// You don't need to understand this!
		String[] columns = boardString.split("\\|");
		for(int y=0;y!=height;++y) {
			for(int x=0;x!=width;++x) {
				String pieceString = columns[y*(width+1)+x+1];
				Game.Player player = pieceString.charAt(0) == 'r' ? Game.Player.RED
						: Game.Player.BLUE;
				board[9-y][x] = createPieceFromChar(player,pieceString.charAt(1));
			}
		}
	}

	private Piece createPieceFromChar(Game.Player player, char c) {
		switch(c) {
		case '_':
			return null; // blank space
		case 'B':
			return (Piece) new BombPiece(player);
		case 'F':
			return new FlagPiece(player);
		case '#':
			return new ImpassableTerrain();
		case '0':
			return new RegularPiece(player,RegularPiece.SPY);
		case '1':
			return new RegularPiece(player,RegularPiece.SCOUT);
		case '2':
			return new RegularPiece(player,RegularPiece.MINER);
		case '3':
			return new RegularPiece(player,RegularPiece.SERGENT);
		case '4':
			return new RegularPiece(player,RegularPiece.LIEUTENANT);
		case '5':
			return new RegularPiece(player,RegularPiece.CAPTAIN);
		case '6':
			return new RegularPiece(player,RegularPiece.MAJOR);
		case '7':
			return new RegularPiece(player,RegularPiece.COLONEL);
		case '8':
			return new RegularPiece(player,RegularPiece.GENERAL);
		case '9':
			return new RegularPiece(player,RegularPiece.MARSHAL);
		}
		// dummy
		return null;
	}

	/**
	 * A template for the layout of pieces on the board
	 */
	private static final String layout =
			"bs112b1121" +
			"f911122233" +
			"3344445555" +
			"666778bbbb";

	
	public void flagCaptured(Game.Player player) {
	    if (player == Game.Player.BLUE) {
	        blueFlagCaptured = true;
	    } else if (player == Game.Player.RED){
	        redFlagCaptured = true;
	    }
	}
	
	public void setCannotMove(Game.Player player) {
        if (player == Game.Player.BLUE) {
            blueCannotMove = true;
        } else if (player == Game.Player.RED){
            redCannotMove = true;
        }
    }
	
	
    public boolean isEnded() {
        return redFlagCaptured || blueFlagCaptured || blueCannotMove || redCannotMove;
    }

    public boolean noPossibleMoves(Game.Player player) {
        
        for (int w = 0; w < getWidth(); w++) {
            for (int h = 0; h < getHeight(); h++) {
                Position pos = new Position(w, h);
                Piece p = getPiece(pos);
                if (p != null && p.getPlayer() == player && p.canMove(pos, this)) {
                    return false;
                }
            }
        }
        
        return true;
        
    }
}
