package swen221.lab8.widgets;

import java.awt.Color;

import swen221.lab8.core.Canvas;
import swen221.lab8.util.AbstractWidget;
import swen221.lab8.util.Rectangle;

/**
 * A very simple widget which provides a simple background colour on which other
 * widgets live
 * 
 * @author David J. Pearce
 *
 */
public class Background extends AbstractWidget {
    private Color color = Color.WHITE;

    public Background(Rectangle dimensions) {
        super(dimensions);
    }

    @Override
    public void paint(Canvas canvas) {
        canvas.fillRectangle(getDimensions(), color);
        super.paint(canvas);
    }
}
