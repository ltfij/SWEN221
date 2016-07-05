package swen221.lab9;

import swen221.lab9.model.*;
import swen221.lab9.util.*;
import swen221.lab9.view.BoardFrame;

public class GameOfLife {
    public final static int ALIVE = 0;
    public final static int DEAD = 9;

    /**
     * The standard rule set for Conway's "Game of Life".
     */
    public static final Rule[] ConwaysOriginalRules = {
            // The underproduction rule
            (Pair<Point, Board> p) -> neighbours(p) < 2 ? DEAD : null,
            // The over-population rule
            (Pair<Point, Board> p) -> neighbours(p) > 3 ? DEAD : null,
            // The reproduction rule
            (Pair<Point, Board> p) -> neighbours(p) == 3 ? ALIVE : null, };

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
        Simulation sim = new Simulation(board, ConwaysOriginalRules);
        new BoardFrame(sim);
    }
}
