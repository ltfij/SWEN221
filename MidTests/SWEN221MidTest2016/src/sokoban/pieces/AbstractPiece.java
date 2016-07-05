package sokoban.pieces;

public abstract class AbstractPiece implements Piece {

	boolean isStored;

	public void setStored(boolean isStored){
		this.isStored = isStored;

	}

	public boolean isStored(){
		return this.isStored;

	}
}
