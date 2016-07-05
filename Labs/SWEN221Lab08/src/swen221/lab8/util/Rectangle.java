package swen221.lab8.util;

public class Rectangle {
    private int x;
    private int y;
    private int width;
    private int height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean contains(Point p) {
        return p.getX() >= x && p.getX() < (x + width) && p.getY() >= y && p.getY() <= (y + height);
    }

    public String toString() {
        return "[" + x + "," + y + "," + width + "," + height + "]";
    }

    public boolean equals(Object o) {
        if (o instanceof Rectangle) {
            Rectangle r = (Rectangle) o;
            return x == r.x && y == r.y && width == r.width && height == r.height;
        }
        return false;
    }

    public int hashCode() {
        return x ^ y ^ width ^ height;
    }

}
