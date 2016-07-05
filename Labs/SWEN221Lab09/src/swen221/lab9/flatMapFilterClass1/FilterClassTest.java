package swen221.lab9.flatMapFilterClass1;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

class Point {
    int x;
    int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "Point [x=" + x + ", y=" + y + "]";
    }
}

class ColPoint extends Point {
    int color;

    ColPoint(int color, int x, int y) {
        super(x, y);
        this.color = color;
    }

    public String toString() {
        return "ColPoint [x=" + x + ", y=" + y + ", color=" + color + "]";
    }
}

public class FilterClassTest {
    public static List<Object> data1() {
        return Arrays.asList("a", "b", "c", 2, 5, "boo");
    }

    public static List<Point> data2() {
        return Arrays.asList(new Point(0, 0), new ColPoint(1, 1, 1), new Point(2, 2));
    }

    @Test
    public void testNoFilterData1() {
        List<Object> res = data1().stream().flatMap(FilterClass.isInstanceOf(Object.class)).collect(Collectors.toList());
        assertEquals(data1().size(), res.size());
        assertEquals(data1(), res);
    }

    @Test
    public void testNoFilterData2() {
        List<Point> data2 = data2();// point do not override equals
        List<Object> res = data2.stream().flatMap(FilterClass.isInstanceOf(Object.class)).collect(Collectors.toList());
        assertEquals(data2().size(), res.size());
        assertNotEquals(data2(), res);
        assertEquals(data2, res);
    }

    @Test
    public void testOnlyStrings() {
        List<String> res = data1().stream().flatMap(FilterClass.isInstanceOf(String.class)).collect(Collectors.toList());
        assertEquals(4, res.size());
        assertEquals(Arrays.asList("a", "b", "c", "boo"), res);
    }

    @Test
    public void testOnlyColPoint() {
        List<Point> data2 = data2();// point do not override equals
        List<ColPoint> res = data2.stream().flatMap(FilterClass.isInstanceOf(ColPoint.class)).collect(Collectors.toList());
        assertEquals(1, res.size());
        assertEquals(data2.get(1), res.get(0));
    }

    @Test
    public void testNone() {
        List<String> res = data2().stream().flatMap(FilterClass.isInstanceOf(String.class)).collect(Collectors.toList());
        assertEquals(0, res.size());
    }
}
