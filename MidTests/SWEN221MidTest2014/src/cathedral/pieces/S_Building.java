package cathedral.pieces;

import cathedral.util.Position;

/**
 * Provides a template for constructing straight buildings.
 * 
 * @author David J. Pearce
 * 
 */
public class S_Building extends BuildingTemplate {

	public S_Building() {

		//  0 |X|X|X|X|
		//    -1 0 1 2
		//
		// (orientation facing NORTH)
		
		super(new Position(-1, 0), new Position(0, 0), new Position(1, 0),
				new Position(2, 0));
	}
	
	public String getName() {
		return "StraightBuilding";
	}
}
