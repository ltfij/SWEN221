package swen221.assignment3.shapes;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * A canvas represents a place on which one can draw points. A canvas will
 * automatically resize to ensure that it is big enough for any point to be
 * drawn.
 * 
 * @author djp
 * 
 */
public class Canvas {
    private int width;
    private int height;
    private Color[][] grid;

    /**
     * Construct an empty Canvas.
     */
    public Canvas() {
        grid = null;
        width = 0;
        height = 0;
    }

    /**
     * Construct a canvas from an existing canvas.
     * 
     * @param c
     */
    public Canvas(Canvas c) {
        width = c.width;
        height = c.height;
        grid = new Color[width][height];
        for (int x = 0; x != width; ++x) {
            for (int y = 0; y != height; ++y) {
                grid[x][y] = c.grid[x][y];
            }
        }
    }

    /**
     * Return the current width of this canvas.
     * 
     * @return
     */
    public int width() {
        return width;
    }

    /**
     * Return the current height of this canvas
     * 
     * @return
     */
    public int height() {
        return height;
    }

    /**
     * Return the color at the given x and y co-ordinate. Observe that this
     * co-ordinate must be within the bounds of the canvas, otherwise an
     * exception will occur.
     * 
     * @return
     */
    public Color colorAt(int x, int y) {
        if (x < 0) {
            throw new IllegalArgumentException("x position cannot be negative!");
        }
        if (y < 0) {
            throw new IllegalArgumentException("y position cannot be negative!");
        }
        if (x >= width) {
            throw new IllegalArgumentException("x position exceeds canvas width!");
        }
        if (y >= height) {
            throw new IllegalArgumentException("y position exceeds canvas height!");
        }
        return grid[x][y];
    }

    /**
     * Draw a point of the specific color within the canvas. It can automatically resize 
     * to ensure that it is big enough for any point to be drawn.
     * 
     * @param x
     * @param y
     * @param color
     */
    public void draw(int x, int y, Color color) {
        if (x < 0) {
            throw new IllegalArgumentException("x position cannot be negative!");
        }
        if (y < 0) {
            throw new IllegalArgumentException("y position cannot be negative!");
        }

        int nwidth = Math.max(width, x + 1);
        int nheight = Math.max(height, y + 1);

        if (nwidth != width || nheight != height) {
            // In this case, the canvas is not big enough.
            // Therefore, we must automatically resize it.
            Color[][] ngrid = new Color[nwidth][nheight];
            for (int i = 0; i != nwidth; ++i) {
                for (int j = 0; j != nheight; ++j) {
                    if (i < width && j < height) {
                        // copy old color
                        ngrid[i][j] = grid[i][j];
                    } else {
                        // put in default color in.
                        ngrid[i][j] = Color.WHITE;
                    }
                }
            }

            grid = ngrid;
            width = nwidth;
            height = nheight;
        }
        grid[x][y] = color;
    }

    /**
     * Show the contents of the canvas using a simple GUI Window. This method is
     * for debugging purposes only.
     */
    @SuppressWarnings("serial")
    public void show() {
        String env = System.getenv("AUTOMARK");

        if (env != null) {
            return; // this is to ensure the marking script will work, even if
                    // you leave in the canvas.show().
        }

        final BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x != width; ++x) {
            for (int y = 0; y != height; ++y) {
                img.setRGB(x, y, grid[x][y].toRGB());
            }
        }

        JFrame window = new JFrame("Assignment 3, Canvas Viewer");
        
        java.awt.Canvas canvas = new java.awt.Canvas() {
            
            public void paint(Graphics g) {
                g.drawImage(img, 10, 10, null);
            }
        };
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(canvas);
        window.pack();
        window.setVisible(true);
        window.setBounds(0, 0, width + 40, height + 60);
    }

    /**
     * Convert the contents of the canvas into a string format, where each point
     * is given using as a 6 digit hexadecimal string.
     */
    public String toString() {
        StringBuilder r = new StringBuilder();
        // now write out the canvas
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                r.append(colorAt(x, y));
            }
            r.append('\n');
        }
        return r.toString();
    }

}
