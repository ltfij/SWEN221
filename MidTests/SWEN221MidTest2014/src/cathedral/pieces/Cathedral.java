package cathedral.pieces;

import cathedral.util.Position;

/**
 * Provides a template for constructing cathedrals.
 * 
 * @author David J. Pearce
 * 
 */
public class Cathedral extends BuildingTemplate {
	
	public Cathedral() {
		
		//  2 |_|X|_|
		//  1 |_|X|_|
		//  0 |X|X|X|
		// -1 |_|X|_|
		//    -1 0 1
		//
		// (orientation facing NORTH)
		
		super(new Position(0,-1),
			  new Position(-1,0), new Position(0,0), new Position(1,0),
			  new Position(0,1),
			  new Position(0,2)
			  );
	}
	
	public String getName() {
		return "Cathedral";
	}
}
