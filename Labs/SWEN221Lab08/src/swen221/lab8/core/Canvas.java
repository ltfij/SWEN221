package swen221.lab8.core;

import java.awt.Color;

import swen221.lab8.util.Rectangle;

public interface Canvas {

    /**
     * Fill a given rectangular box with a given fill color
     * 
     * @param box
     *            The box which will be filled
     * @param color
     *            Color of rectangle
     */
    void fillRectangle(Rectangle box, Color color);

    /**
     * Draw a given rectangular box with a given line color
     * 
     * 
     * @param box
     *            The box whose outline will be drawn
     * @param color
     *            Color of rectangle
     */
    void drawRectangle(Rectangle box, Color color);
}
