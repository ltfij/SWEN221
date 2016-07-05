package swen221.lab2.rules;

import swen221.lab2.model.*;
import swen221.lab2.util.CellDecayAbstractRule;

/**
 * This should implements Cell Decay version of Conway's rule for happiness:
 * 
 * "Any cell with exactly three live neighbours gets younger, as if by happiness."
 * 
 * @author David J. Pearce
 *
 */
public class CellDecayReproductionRule extends CellDecayAbstractRule {

    @Override
    public int apply(int x, int y, int neighbours) {
        if (neighbours == 3) {
            // This rule was applied in this case
            return CellDecayAbstractRule.board.getCellState(x, y) + GET_YOUNGER;
        } else {
            // This rule was not applied in this case
            return Rule.NOT_APPLICABLE;
        }
    }
}
