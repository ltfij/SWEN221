package swen221.lab6.connect.rules;

import swen221.lab6.connect.Game;
import swen221.lab6.connect.Game.Status;
import swen221.lab6.connect.core.Board;
import swen221.lab6.connect.core.Rule;
import swen221.lab6.connect.util.Position;

/**
 * Check whether or not one or more pieces on the board has been captured.
 *
 * @author David J. Pearce
 *
 */
public class CaptureRule implements Rule {

	@Override
	public Game.Status apply(Game g) {
		// In this rule, we need to check whether a given token has been
		// captured or not. What we do is go through every position on the board
		// and individually check whether it is captured or not.
		Board current = g.getBoard();
		Board original = current.clone();
		for (int x = 0; x < 4; ++x) {
			for (int y = 0; y < 4; ++y) {
				checkCaptured(current, original, x, y);
			}
		}
		return Status.ONGOING;
	}

	/**
	 * Check whether a given token on the board has been captured or not.
	 *
	 * @param current
	 *            --- The current game board which we are updating.
	 * @param original
	 *            --- The original state of the game board, before we removed
	 *            any captured pieces. This is necessary in the case of a
	 *            "mutual capture".
	 * @param x
	 *            --- X position of square to check
	 * @param y
	 *            --- Y position of square to check
	 * @return
	 */
	private void checkCaptured(Board current, Board original, int x, int y) {
		Position p = new Position(x, y);
		Board.Token t = original.getSquare(p);
		if (t != null) {
			// Yes, there is a token at this point. So, now we need to decide
			// whether or not it is captured.
			if (isCaptured(original, t, x, y)) {
				// Yes it is captured, so remove from board.
				current.setSquare(p, null);
			}
		}
	}

	/**
	 * Check whether a given piece is captured or not. There are essentially
	 * four different cases to check: short and long vertical, and short and
	 * long horizontal.
	 *
	 *
	 * @param original
	 *            --- The board that we are checking against.
	 * @param token
	 *            --- the token
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isCaptured(Board original, Board.Token token, int x, int y) {
		boolean r = false;

		// XXX all long captures, need to check if the other middle position is empty.
		// OR!!! the game allows long capture to capture two piece at one time.
		// if so, this game is not playable at all
		
		r |= isHorizontalLongCapture(original, token, x, y);
		r |= isHorizontalShortCapture(original, token, x, y);
		r |= isVerticalLongCapture(original, token, x, y);
		r |= isVerticalShortCapture(original, token, x, y);

		return r;
	}

	/**
	 * Check for these two cases:
	 *
	 * <pre>
	 * |X|*|?|X
	 * </pre>
	 *
	 * and
	 *
	 * <pre>
	 * |X|?|*|X
	 * </pre>
	 *
	 * @param original
	 * @param token
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isHorizontalLongCapture(Board original, Board.Token token, int x, int y) {
		if (x == 1 || x == 2) {
			Position left = new Position(0, y);
			Position right = new Position(3, y);
			Board.Token opp = getOpposite(token);
			return original.getSquare(left) == opp && original.getSquare(right) == opp;
		}
		return false;
	}

	/**
	 * Check for these two cases:
	 *
	 * <pre>
	 * |X|*|X|?
	 * </pre>
	 *
	 * and
	 *
	 * <pre>
	 * |?|X|*|X
	 * </pre>
	 *
	 * @param original
	 * @param token
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isHorizontalShortCapture(Board original, Board.Token token, int x, int y) {
		if (x == 1 || x == 2) {
			Position left = new Position(x - 1, y);
			Position right = new Position(x + 1, y);
			Board.Token opp = getOpposite(token);
			return original.getSquare(left) == opp && original.getSquare(right) == opp;
		}
		return false;
	}

	/**
	 * Check for the two cases:
	 *
	 * <pre>
	 * |X|
	 * |*|
	 * |?|
	 * |X|
	 * </pre>
	 *
	 * and
	 *
	 * <pre>
	 * |X|
	 * |?|
	 * |*|
	 * |X|
	 * </pre>
	 *
	 * @param original
	 * @param token
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isVerticalLongCapture(Board original, Board.Token token, int x, int y) {
		if (y == 1 || y == 2) {
			Position top = new Position(x, 0);
			Position bottom = new Position(x, 3);
			Board.Token opp = getOpposite(token);
			return original.getSquare(top) == opp && original.getSquare(bottom) == opp;
		}
		return false;
	}

	/**
	 * Check for these two cases:
	 *
	 * <pre>
	 * |X|
	 * |*|
	 * |X|
	 * |?|
	 * </pre>
	 *
	 * and
	 *
	 * <pre>
	 * |?|
	 * |X|
	 * |*|
	 * |X|
	 * </pre>
	 *
	 * @param original
	 * @param token
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isVerticalShortCapture(Board original, Board.Token token, int x, int y) {
		if (y == 1 || y == 2) {
			Position top = new Position(x, y - 1);
			Position bottom = new Position(x, y + 1);
			Board.Token opp = getOpposite(token);
			return original.getSquare(top) == opp && original.getSquare(bottom) == opp;
		}
		return false;
	}

	/**
	 * Return the opposite player's token kind. This is needed to determine for
	 * a given token on the board, what colour the pieces are which could
	 * capture it.
	 *
	 * @param token
	 * @return
	 */
	private Board.Token getOpposite(Board.Token token) {
		if (token == Board.Token.WHITE) {
			return Board.Token.BLACK;
		} else {
			return Board.Token.WHITE;
		}
	}
}
