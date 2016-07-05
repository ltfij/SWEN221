package cathedral.moves;

import cathedral.Area;
import cathedral.Game;
import cathedral.Game.Player;
import cathedral.io.GameError;
import cathedral.pieces.BuildingTemplate;
import cathedral.util.Position;

/**
 * Represents a move in the game where the player places a building on the board
 * and, as a result, captures an area of the board.
 * 
 * @author David J. Pearce
 * 
 */
public class CaptureArea extends PlaceBuilding {

	private Area area;
	
	public CaptureArea(Player player, Position origin,
			Game.Direction orientation, BuildingTemplate template, Area area) {
		super(player, origin, orientation, template);
		this.area = area;
	}

	@Override
	public void apply(Game game) throws GameError {
		// First, apply the place building move
		super.apply(game);
		game.capture(player, area);
	}
	
}
