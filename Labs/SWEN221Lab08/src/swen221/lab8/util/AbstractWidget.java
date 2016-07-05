package swen221.lab8.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import swen221.lab8.core.Canvas;
import swen221.lab8.core.Widget;

public abstract class AbstractWidget implements Widget {
    private ArrayList<Widget> children = new ArrayList<Widget>();
    private Rectangle dimensions;

    public AbstractWidget(Rectangle dimensions) {
        this.dimensions = dimensions;
    }

    @Override
    public Rectangle getDimensions() {
        return dimensions;
    }

    @Override
    public List<Widget> getChildren() {
        return children;
    }

    @Override
    public void paint(Canvas canvas) {
        for (Widget child : children) {
            child.paint(canvas);
        }
    }

    @Override
    public void mouseClick(Point p) {
        // Pass clicks on to children
        for (Widget child : children) {
            Rectangle box = child.getDimensions();
            if (box.contains(p)) {
                child.mouseClick(p);
            }
        }
    }
}
