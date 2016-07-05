package swen221.lab6.connect.rules;

import swen221.lab6.connect.Game;
import swen221.lab6.connect.Game.Status;
import swen221.lab6.connect.core.Board;
import swen221.lab6.connect.core.Rule;
import swen221.lab6.connect.util.Position;

/**
 * Checks whether or not the game has been won by getting a vertical column of
 * the same tokens.
 * 
 * @author David J. Pearce
 *
 */
public class ColumnWinRule implements Rule {

	@Override
	public Game.Status apply(Game g) {
		// We want to check whether there is a column on the board which is full
		// of tokens with the same colour. To do this, we go through each column
		// in turn.
		Status r = null;
		for (int x = 0; x < 4; ++x) {
			r = checkColumn(g.getBoard(), x);
			if (r != null) {
				return r;
			}
		}
		return g.getStatus();
	}

	/**
	 * Check a single column on the board has all the same token. If so, we have
	 * a winner.
	 * 
	 * @param b
	 * @param x
	 * @return
	 */
	private Game.Status checkColumn(Board b, int x) {
		Board.Token first = b.getSquare(new Position(x, 0));
		if (first != null) {
			// There is a token in the first row. Now, all remaining tokens must
			// match this one to have a winner.
			for (int y = 1; y < 4; ++y) {
				Board.Token t = b.getSquare(new Position(x, y));
				if (t != first) {
					return null;
				}
			}
			// We found a winner!
			if (first == Board.Token.WHITE) {
				return Status.WHITEWON;
			} else {
				return Status.BLACKWON;
			}
		}
		return null;
	}
}
