package sokoban.moves;

import sokoban.Game;
import sokoban.io.GameError;
import sokoban.pieces.CratePiece;
import sokoban.pieces.Piece;
import sokoban.util.Position;

public class FinishingMove implements Move {

	public void apply(Game game) throws GameError {

		for (int row = 0; row < game.getHeight(); row++) {
			for (int col = 0; col < game.getWidth(); col++) {

				Piece piece = game.getPiece(new Position(col, row));

				if (piece instanceof CratePiece) {
					CratePiece cPiece = (CratePiece) piece;
					if (!cPiece.isStored()) {
						throw new GameError("   ");
					}

				}

			}
		}

	}

}
