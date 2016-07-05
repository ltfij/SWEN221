package cathedral.pieces;

import cathedral.util.Position;

/**
 * Provides a template for constructing corner buildings.
 * 
 * @author David J. Pearce
 * 
 */
public class L_Building extends BuildingTemplate {

	public L_Building() {
		
		//  1 |X|_|
		//  0 |X|X|
		//     0 1
		//
		// (orientation facing NORTH)
				
		// 
	    super(new Position(0, 1), new Position(0, 0), new Position(1, 0)); 
	}
	
	public String getName() {
		return "CornerBuilding";
	}
}
