package sokoban.pieces;

public class PlayerPiece extends AbstractPiece implements Piece {

	/**
	 * Return the string representation of the player piece.
	 */
	public String toString() {

		if (this.isStored) {
			return "@";
		} else {
			return "O";
		}
	}
}
