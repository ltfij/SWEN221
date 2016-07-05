package swen221.lab9.util;

/**
 * Represents an (x,y) point in two-dimensional space
 * 
 * @author David J. Pearce
 * 
 */
public final class Point {
    private final int x;
    private final int y;

    /**
     * Construct a point in 2D space
     * 
     * @param x
     * @param y
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    /**
     * Convert a string of the form "(x,y)" into a point
     * 
     * @param input
     * @return
     */
    public static Point fromString(String input) {
        String[] elements = input.split(",");
        int x = Integer.parseInt(elements[0]);
        int y = Integer.parseInt(elements[1]);
        return new Point(x, y);
    }
}
