package swen221.lab8;

import swen221.lab8.core.Inspector;
import swen221.lab8.core.Widget;
import swen221.lab8.util.Rectangle;
import swen221.lab8.views.WidgetViewer;
import swen221.lab8.widgets.Background;
import swen221.lab8.widgets.Counter;
import swen221.lab8.widgets.Toggle;

/**
 * This class setups the list of all possible widgets.
 * 
 * @author David J. Pearce
 *
 */
public class Main {

    /**
     * This is a small selection of widget classes which can be used in the
     * program. In principle, you can write any kind of widget you like!
     */
    public static final Class<? extends Widget>[] ARRAY_OF_ALL_WIDGETS = new Class[] { Background.class, 
            Toggle.class, getClassFromBytes("swen221.lab8.widgets.Counter", Counter.COUNTER_BYTES) };

    /**
     * This method takes an array of bytes and turns them into a class. Of
     * course, the bytes must be organised correctly for this to work.
     * 
     * @param name
     * @param bytes
     * @return
     */
    private static Class<?> getClassFromBytes(final String className, final byte[] bytes) {
        // First, declare an inner class which can act as a class loader. I
        // would normally do it this way, but it's useful to see that it's
        // possible.
        class ByteLoader extends ClassLoader {
            @Override
            public Class<?> findClass(String name) throws ClassNotFoundException {
                if (name.equals(className)) {
                    return defineClass(name, bytes, 0, bytes.length);
                }
                return super.findClass(name);
            }
        }
        ;
        ByteLoader loader = new ByteLoader();
        try {
            return loader.findClass(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Invalid class provided", e);
        }
    }

    /**
     * This is an example program which creates a simple display with some
     * widgets on it.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Inspector inspector = new Inspector(ARRAY_OF_ALL_WIDGETS);
        //
        Widget background = inspector.newWidget("Background", new Rectangle(0, 0, 200, 200));
        Widget toggleA = inspector.newWidget("Toggle", new Rectangle(40, 40, 40, 40));
        Widget toggleB = inspector.newWidget("Toggle", new Rectangle(120, 40, 40, 40));
        Widget counterA = inspector.newWidget("Counter", new Rectangle(40, 120, 40, 40));
        Widget counterB = inspector.newWidget("Counter", new Rectangle(120, 120, 40, 40));
        //
        background.getChildren().add(toggleA);
        background.getChildren().add(toggleB);
        background.getChildren().add(counterA);
        background.getChildren().add(counterB);
        WidgetViewer view = new WidgetViewer(background);
    }
}
