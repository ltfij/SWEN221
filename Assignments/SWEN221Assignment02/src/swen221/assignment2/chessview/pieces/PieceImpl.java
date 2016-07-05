package swen221.assignment2.chessview.pieces;

public abstract class PieceImpl {
	protected boolean isWhite;
	
	public PieceImpl(boolean isWhite) {		
		this.isWhite = isWhite;
	}

	public boolean isWhite() {
		return isWhite;
	}
	
	public boolean equals(Object o) {
		if (o instanceof PieceImpl) {
			PieceImpl p = (PieceImpl) o;
			return o.getClass() == getClass() && isWhite == p.isWhite;
		}
		return false;
	}		
}
