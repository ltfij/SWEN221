package swen221.lab8.core;

import java.util.List;

import swen221.lab8.util.Point;
import swen221.lab8.util.Rectangle;

public interface Widget {

    /**
     * Determine the bounding box for this widget.
     * 
     * @return
     */
    public Rectangle getDimensions();

    /**
     * An mouse click occurred on this widget at the given (relative)
     * coordinates
     * 
     * @param p
     *            The point where the mouse was clicked. This is always a point
     *            within the bounding box of this widget.
     */
    public void mouseClick(Point p);

    /**
     * Paint this widget to the given canvas.
     * 
     * @param canvas
     */
    public void paint(Canvas canvas);

    /**
     * Return the list of widgets contained in this widget (if any).
     * 
     * @return
     */
    public List<Widget> getChildren();

}
