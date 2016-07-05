package cathedral.moves;

import java.util.HashMap;

import cathedral.Game;
import cathedral.Game.Player;
import cathedral.io.GameError;
import cathedral.pieces.*;
import cathedral.util.Position;

/**
 * Place a given building at a given origin on the board facing in a given
 * direction.
 * 
 * @author David J. Pearce
 * 
 */
public class PlaceBuilding extends Move {
	private Position origin;
	
	private Game.Direction orientation;
	
	private BuildingTemplate template;

	public PlaceBuilding(Player player, Position origin, Game.Direction orientation, BuildingTemplate template) {
		super(player);
		this.origin = origin;
		this.orientation = orientation;
		this.template = template;				
	}
	
	@Override
	public void apply(Game game) throws GameError {
		Building building = template.create(origin,orientation,player);
		
		Position[] positions = building.getSquares();
		int width = game.getWidth();
		int height = game.getHeight();
		
		for (Position p : positions) {
		    if (p.getX()<0 || p.getX()>=width || p.getY()<0 || p.getY()>=height) {
		        throw new GameError("out of boundary");
		    }
		    
		    if (game.getSquare(p.getX(), p.getY()) != null) {
		        throw new GameError("building overlap");
		    }
		    
		}
		
		game.place(building);
		
		
		
		
		
		
		
		


	}	
	
	public BuildingTemplate getTemplate() {
		return template;
	}
}
