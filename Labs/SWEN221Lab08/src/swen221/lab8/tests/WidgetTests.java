package swen221.lab8.tests;

import org.junit.*;
import org.junit.runners.MethodSorters;

import swen221.lab8.Main;
import swen221.lab8.core.Inspector;
import swen221.lab8.core.Widget;
import swen221.lab8.util.Point;
import swen221.lab8.util.Rectangle;
import swen221.lab8.views.WidgetViewer;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WidgetTests {

    @Test
    public void test01_newWidget() {
        Inspector inspector = new Inspector(Main.ARRAY_OF_ALL_WIDGETS);
        Widget background = inspector.newWidget("Background", new Rectangle(0, 0, 200, 200));
        assertEquals(background.getDimensions(), new Rectangle(0, 0, 200, 200));
    }

    @Test
    public void test02_Toggle() {
        Inspector inspector = new Inspector(Main.ARRAY_OF_ALL_WIDGETS);
        //
        Widget background = inspector.newWidget("Background", new Rectangle(0, 0, 200, 200));
        Widget toggleA = inspector.newWidget("Toggle", new Rectangle(40, 40, 40, 40));
        Widget toggleB = inspector.newWidget("Toggle", new Rectangle(120, 40, 40, 40));
        background.getChildren().add(toggleA);
        background.getChildren().add(toggleB);
        //
        String[] clicks = { "50,50" };
        //
        // Second, replay the sequence of events on widgets
        playEventSequence(clicks, background);
        // Finally, check things happened as we expected
        assertEquals(inspector.getAttributeValue(toggleA, "value"), Boolean.TRUE);
        assertEquals(inspector.getAttributeValue(toggleB, "value"), Boolean.FALSE);
    }

    @Test
    public void test03_Toggle() {
        Inspector inspector = new Inspector(Main.ARRAY_OF_ALL_WIDGETS);
        //
        Widget background = inspector.newWidget("Background", new Rectangle(0, 0, 200, 200));
        Widget toggle = inspector.newWidget("Toggle", new Rectangle(80, 80, 40, 40));
        background.getChildren().add(toggle);
        //
        String[] clicks = { "0,0" };
        //
        // Second, replay the sequence of events on widgets
        playEventSequence(clicks, background);
        // Finally, check things happened as we expected
        assertEquals(inspector.getAttributeValue(toggle, "value"), Boolean.FALSE);
    }

    @Test
    public void test04_Toggle() {
        Inspector inspector = new Inspector(Main.ARRAY_OF_ALL_WIDGETS);
        //
        Widget background = inspector.newWidget("Background", new Rectangle(0, 0, 200, 200));
        Widget toggle = inspector.newWidget("Toggle", new Rectangle(80, 80, 40, 40));
        background.getChildren().add(toggle);
        //
        String[] clicks = { "100,100", "90,90" };
        //
        // Second, replay the sequence of events on widgets
        playEventSequence(clicks, background);
        // Finally, check things happened as we expected
        assertEquals(inspector.getAttributeValue(toggle, "value"), Boolean.FALSE);
    }

    @Test
    public void test05_Toggle() {
        Inspector inspector = new Inspector(Main.ARRAY_OF_ALL_WIDGETS);
        //
        Widget background = inspector.newWidget("Background", new Rectangle(0, 0, 200, 200));
        Widget toggleA = inspector.newWidget("Toggle", new Rectangle(40, 40, 40, 40));
        Widget toggleB = inspector.newWidget("Toggle", new Rectangle(120, 120, 40, 40));
        background.getChildren().add(toggleA);
        background.getChildren().add(toggleB);
        //
        String[] clicks = { "50,50" };
        //
        inspector.setAttribute(toggleA, "value", true);
        inspector.setAttribute(toggleB, "value", true);
        // Second, replay the sequence of events on widgets
        playEventSequence(clicks, background);
        // Finally, check things happened as we expected
        assertEquals(inspector.getAttributeValue(toggleA, "value"), Boolean.FALSE);
        assertEquals(inspector.getAttributeValue(toggleB, "value"), Boolean.TRUE);
    }

    @Test
    public void test06_Counter() {
        Inspector inspector = new Inspector(Main.ARRAY_OF_ALL_WIDGETS);
        //
        Widget background = inspector.newWidget("Background", new Rectangle(0, 0, 200, 200));
        Widget counterA = inspector.newWidget("Counter", new Rectangle(30, 30, 60, 60));
        background.getChildren().add(counterA);
        //
        String[] clicks = { "50,50", "50,50", "50,50" };
        //
        // Second, replay the sequence of events on widgets
        playEventSequence(clicks, background);
        // Finally, check things happened as we expected
        assertEquals(inspector.getAttributeValue(counterA, "value"), 3);
    }

    @Test
    public void test07_Counter() {
        Inspector inspector = new Inspector(Main.ARRAY_OF_ALL_WIDGETS);
        //
        Widget background = inspector.newWidget("Background", new Rectangle(0, 0, 200, 200));
        Widget counterA = inspector.newWidget("Counter", new Rectangle(30, 30, 60, 60));
        Widget counterB = inspector.newWidget("Counter", new Rectangle(120, 30, 60, 60));
        background.getChildren().add(counterA);
        background.getChildren().add(counterB);
        //
        inspector.setAttribute(counterA, "value", 1);
        inspector.setAttribute(counterB, "value", 5);
        //
        String[] clicks = { "50,50", "140,50", "50,50" };
        //
        // Second, replay the sequence of events on widgets
        playEventSequence(clicks, background);
        // Finally, check things happened as we expected
        assertEquals(inspector.getAttributeValue(counterA, "value"), 3);
        assertEquals(inspector.getAttributeValue(counterB, "value"), 6);
    }

    /**
     * Replay a sequence of clicks onto our widget system. This will have some
     * kind of effect on the widget system and, afterwards, we can observe what
     * changed.
     * 
     * @param inspector
     * @param clicks
     * @param root
     */
    private void playEventSequence(String[] clicks, Widget root) {
        WidgetViewer view = new WidgetViewer(root);
        Point[] points = toPointArray(clicks);
        pause();
        for (Point p : points) {
            root.mouseClick(p);
            view.repaint();
            pause();
        }
    }

    private static void pause() {
        try {
            Thread.currentThread().sleep(200);
        } catch (InterruptedException e) {
        }
    }

    /**
     * Convert an array of "click strings" into an array of points.
     * 
     * @param clicks
     * @return
     */
    private static Point[] toPointArray(String[] clicks) {
        Point[] points = new Point[clicks.length];
        for (int i = 0; i != points.length; ++i) {
            points[i] = Point.fromString(clicks[i]);
        }
        return points;
    }
}
