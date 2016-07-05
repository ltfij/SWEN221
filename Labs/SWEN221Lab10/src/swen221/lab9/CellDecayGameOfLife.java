package swen221.lab9;

import swen221.lab9.model.*;
import swen221.lab9.util.Pair;
import swen221.lab9.util.Point;
import swen221.lab9.view.BoardFrame;

import static swen221.lab9.GameOfLife.*;

public class CellDecayGameOfLife {

    public final static int YOUNGER = -1;
    public final static int OLDER = 1;

    /**
     * The variation on the basic rules for the Game Of Life which models the age of a cell
     */
    public static final Rule[] CellDecayRules = {
            // The underproduction rule
            (Pair<Point, Board> p) -> neighbours(p) < 2
                    ? p.second().getCellState(p.first().getX(), p.first().getY()) + OLDER : null,
            // The overpopulation rule
            (Pair<Point, Board> p) -> neighbours(p) > 3
                    ? p.second().getCellState(p.first().getX(), p.first().getY()) + OLDER : null,
            // The reproduction rule
            (Pair<Point, Board> p) -> neighbours(p) == 3
                    ? p.second().getCellState(p.first().getX(), p.first().getY()) + YOUNGER : null, };

    /**
     * Count the number of neighbours for a cell at a given point on a given board.
     * 
     * @param pair
     *            The point and board package up to make it easier to access from a lambda rule.
     * @return
     */
    public static int neighbours(Pair<Point, Board> pair) {
        Point p = pair.first();
        Board board = pair.second();
        int count = 0;

        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= 1; ++dy) {
                if (dx != 0 || dy != 0) {
                    count += getNumAlive(p.getX() + dx, p.getY() + dy, board);
                }
            }
        }

        return count;
    }

    /**
     * Check the state of an adjancent cell, taking into account the edges of the board.
     * 
     * @param x
     * @param y
     * @param board
     * @return
     */
    private static int getNumAlive(int x, int y, BoardView board) {
        if (x < 0 || x >= board.getWidth()) {
            return 0;
        } else if (y < 0 || y >= board.getWidth()) {
            return 0;
        } else if (board.getCellState(x, y) == DEAD) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * The entry point for the GameOfLife application.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Board board = new Board(50, 50);
        Simulation sim = new Simulation(board, CellDecayRules);
        new BoardFrame(sim);
    }
}
