package sokoban;

import sokoban.io.GameError;
import sokoban.moves.Move;
import sokoban.pieces.*;
import sokoban.util.Position;

/**
 * Represents the state of a game of Sokoban. In particular, the game holds the
 * position of each piece on the board and the list of moves being played.
 *
 * @author David J. Pearce
 *
 */
public class Game {

	/**
	 * Represents one of the four position of the compass (North, East, South and
	 * West). This is used to the direction in which the player moves.
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
	 * A 2-dimensional array representing the board itself.
	 */
	private Piece[][] board;

	/**
	 * The array of moves which make up this game.
	 */
	private Move[] moves;

	/**
	 * Construct a game of Sokoban
	 *
	 * @param moves
	 *            --- The moves that make up the game
	 */
	public Game(int width, int height, Move[] moves) {
		this.moves = moves;
		this.width = width;
		this.height = height;
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

	public Piece[][] getBoard() {
		return this.board;
	}

	/**
	 * Run this game to produce the final board, whilst also checking each move
	 * against the rules of Sokoban. In the event of an invalid move being
	 * encountered, then a game error should be thrown.
	 */
	public void run() throws GameError {
		for(int i=0;i!=moves.length;++i) {
			Move move = moves[i];
			move.apply(this);
		}
		// FIXME: check that game is completed or not
	}

	/**
	 * Get the piece at a given position on the board, or null if no piece at
	 * that position.
	 *
	 * @param position
	 *            Board position to get piece from
	 * @return
	 */
	public Piece getPiece(Position position) {
		return board[position.getY()][position.getX()];
	}

	/**
	 * Set the piece at a given position on the board. Note, this will overwrite
	 * the record of any other piece being at that position. position.
	 *
	 * @param position
	 *            Board position to place piece on
	 * @param piece
	 *            The piece to put at the given position.
	 * @return
	 */
	public void setPiece(Position position, Piece piece) {
		board[position.getY()][position.getX()] = piece;
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
				Piece p = board[i][j];
				if(p == null) {
					r += "_";
				} else {
					r += p.toString();
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
				board[height-(y+1)][x] = createPieceFromChar(pieceString.charAt(0));
			}
		}
	}

	private Piece createPieceFromChar(char c) {
		switch(c) {
		case '_':
			return null; // blank space
		case 'O':
			return new PlayerPiece();
		case '#':
			return new WallPiece();
		case 'X':
			return new CratePiece();
		case '+':
			return new StoragePiece();
		}
		// dummy
		return null;
	}
}
