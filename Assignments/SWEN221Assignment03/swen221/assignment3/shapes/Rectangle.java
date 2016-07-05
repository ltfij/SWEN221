package swen221.assignment3.shapes;

/**
 * This class represents a rectangle to be drawn in the canvas. Each Rectangle
 * object has its coordinates of left-top vertex, and its width and height.
 * 
 * @author Hector
 *
 */
public class Rectangle implements Shape {

    private final int x;
    private final int y;
    private final int width;
    private final int height;

    /**
     * Construct a rectangle with given coordinates of left-top vertex and its
     * width and height.
     * 
     * @param x  
     * @param y     --- x and y specifies the coordinate of the left-top vertex
     * @param width        --- the width of this rectangle
     * @param height       --- the height of this rectangle
     */
    public Rectangle(int x, int y, int width, int height) {
        
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("x & y cannot be less than 0.");
        }

        if (width <= 0) {
            throw new IllegalArgumentException("The width cannot be 0 or less than 0.");
        }

        if (height <= 0) {
            throw new IllegalArgumentException("The height cannot be 0 or less than 0.");
        }

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    /**
     * Return the x coordinate of the left-top vertex of current rectangle
     * 
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Return the y coordinate of the left-top vertex of current rectangle
     * 
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Return the width of current rectangle
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * Return the height of current rectangle
     * @return
     */
    public int getHeight() {
        return height;
    }

    @Override
    public boolean contains(int x, int y) {
        return (x >= this.x) && (x < this.x + width) && (y >= this.y) && (y < this.y + height);
    }

    @Override
    public Rectangle boundingBox() {
        return this;
    }

}
