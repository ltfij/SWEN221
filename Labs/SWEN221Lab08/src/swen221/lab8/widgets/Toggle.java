package swen221.lab8.widgets;

import java.awt.Color;

import swen221.lab8.core.Canvas;
import swen221.lab8.util.AbstractWidget;
import swen221.lab8.util.Point;
import swen221.lab8.util.Rectangle;

/**
 * A very simple widget which provides a simple background colour on which other
 * widgets live
 * 
 * @author David J. Pearce
 *
 */
public class Toggle extends AbstractWidget {
    private boolean value;

    public Toggle(Rectangle dimensions) {
        super(dimensions);
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean b) {
        value = b;
    }

    @Override
    public void paint(Canvas canvas) {
        Rectangle box = getDimensions();
        if (value) {
            canvas.fillRectangle(box, Color.DARK_GRAY);
        }
        canvas.drawRectangle(box, Color.BLACK);
        box = new Rectangle(box.getX() + 1, box.getY() + 1, box.getWidth() - 2, box.getHeight() - 2);
        canvas.drawRectangle(box, Color.BLACK);
    }

    @Override
    public void mouseClick(Point p) {
        value = !value;
    }
}
