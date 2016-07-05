package swen221.lab2.rules;

import swen221.lab2.model.Rule;
import swen221.lab2.util.ConwayAbstractRule;

/**
 * This should implements Conway's rule for under-population:
 * 
 * "Any live cell with fewer than two live neighbours dies, as if caused by under-population."
 * 
 * @author David J. Pearce
 *
 */
public class ConwaysUnderpopulationRule extends ConwayAbstractRule {
	
    @Override
	public int apply(int x, int y, int neighbours) {
		if (neighbours < 2) {
			// This rule was applied in this case
			return ConwayAbstractRule.DEAD;
		} else {
			// This rule was not applied in this case
			return Rule.NOT_APPLICABLE;
		}
	}
}
