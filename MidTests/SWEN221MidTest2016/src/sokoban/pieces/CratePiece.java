package sokoban.pieces;

public class CratePiece extends AbstractPiece implements Piece {
	/**
	 * Return the string representation of the crate piece.
	 */
	public String toString() {

		if (this.isStored) {
			return "*";
		} else {
			return "X";
		}

	}
}
