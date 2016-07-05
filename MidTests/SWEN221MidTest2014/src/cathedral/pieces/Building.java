package cathedral.pieces;

import java.util.Arrays;

import cathedral.Game;
import cathedral.util.Position;

/**
 * Represents a building which has been placed on to the board. Every building
 * occupies a number of squares on the board, and is owned by a given player.
 * 
 * @author David J. Pearce
 * 
 */
public final class Building {
	/**
	 * The player who owns this building
	 */
	private Game.Player player;
	
	/**
	 * The squares on the board which make up this building.
	 */
	private Position[] squares;
	
	/**
	 * The building template from which this building was constructed
	 */
	private BuildingTemplate template;
	
	public Building(Game.Player player, BuildingTemplate template, Position... squares) {
		this.squares = squares;
		this.player = player;
		this.template = template;
	}
		
	/**
	 * Get the squares on the board which make up this building.
	 * 
	 * @return
	 */
	public Position[] getSquares() {
		return squares;
	}
	
	/**
	 * Get the player who owns this building.
	 * 
	 * @return
	 */
	public Game.Player getPlayer() {
		return player;
	}
	
	/**
	 * Get the building template from which this building was constructed.
	 * 
	 * @return
	 */
	public BuildingTemplate getTemplate() {
		return template;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((player == null) ? 0 : player.hashCode());
        result = prime * result + Arrays.hashCode(squares);
        result = prime * result + ((template == null) ? 0 : template.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Building other = (Building) obj;
        if (player != other.player)
            return false;
        if (!Arrays.equals(squares, other.squares))
            return false;
        if (template == null) {
            if (other.template != null)
                return false;
        } else if (!template.equals(other.template))
            return false;
        return true;
    }
	
	
	
	
	
}
