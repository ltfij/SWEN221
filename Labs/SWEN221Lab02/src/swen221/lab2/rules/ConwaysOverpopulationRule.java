package swen221.lab2.rules;

import swen221.lab2.model.*;
import swen221.lab2.util.ConwayAbstractRule;

/**
 * This should implements Conway's rule for overproduction:
 * 
 * "Any live cell with more than three live neighbours dies, as if by over-population"
 * 
 * @author David J. Pearce
 *
 */
public class ConwaysOverpopulationRule extends ConwayAbstractRule {

	@Override
	public int apply(int x, int y, int neighbours) {
	    if (neighbours > 3) {
            // This rule was applied in this case
            return ConwayAbstractRule.DEAD;
        } else {
            // This rule was not applied in this case
            return Rule.NOT_APPLICABLE;
        }	
	}
}
