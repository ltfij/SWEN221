package swen221.lab2.rules;

import swen221.lab2.model.*;
import swen221.lab2.util.CellDecayAbstractRule;

/**
 * This should implements Cell Decay version of Conway's rule for
 * under-production:
 * 
 * "Any live cell with fewer than two live neighbours gets older, as if caused
 * by under-population."
 * 
 * @author David J. Pearce
 *
 */
public class CellDecayUnderPopulationRule extends CellDecayAbstractRule {

    @Override
    public int apply(int x, int y, int neighbours) {
        if (neighbours < 2) {
            // This rule was applied in this case
            return CellDecayAbstractRule.board.getCellState(x, y) + GET_OLDER;
        } else {
            // This rule was not applied in this case
            return Rule.NOT_APPLICABLE;
        }
    }
}
