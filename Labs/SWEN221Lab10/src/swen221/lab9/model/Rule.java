package swen221.lab9.model;

import java.util.function.Function;

import swen221.lab9.util.Pair;
import swen221.lab9.util.Point;

/**
 * Provides a concept of a rule for controlling cell creation / destruction in the simulation. The
 * rule is a function taking a Point and a given Board, and producing an integer:
 * <ul>
 * <li>The point identifies a cell on the board to which this rule is being applied.</li>
 * <li>The integer return value represents the health of that cell after the rule is applied. If the
 * given rule could not be applied, it returns null.</li>
 * </ul>
 * 
 * @author David J. Pearce
 *
 */
public interface Rule extends Function<Pair<Point, Board>, Integer> {
    /*
     * The body of the rule is empty as its functionality is taken from the java.util.Function
     * interface. The purpose of this is simply to give a useful name to the concept of a rule which
     * we can use throughout the remainder of the program.
     */
}
