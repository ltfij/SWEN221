package cathedral.pieces;

import cathedral.util.Position;

/**
 * Provides a template for constructing T-shaped buildings.
 * 
 * @author David J. Pearce
 * 
 */
public class T_Building extends BuildingTemplate {

	public T_Building() {

		//  1 |_|X|_|
		//  0 |X|X|X|
		//    -1 0 1
		//
		// (orientation facing NORTH)
						
		super(new Position(-1, 0), new Position(0, 0), new Position(1, 0),
				new Position(0, 1));		
	}
	
	public String getName() {
		return "LargeBuilding";
	}
}
