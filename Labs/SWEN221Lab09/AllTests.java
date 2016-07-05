import static org.junit.Assert.*;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import swen221.lab9.flatMapFilterClass1.FilterClass;
import swen221.lab9.parallelStream3.KeywordFinder;
import swen221.lab9.simplyfyReflection2.ReflectionHelper;

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

public class AllTests {

    List<String> keywords = Arrays.asList("key", "tumeric", "can", "godzilla", "cat", "kitten", "apocalipse");
    List<Long> occ3 = Arrays.asList(132L, 0L, 132L, 132L, 132L, 264L, 0L);

    @Test
    public void testHardCoded1() {
        for (int i = 0; i < keywords.size(); i++) {
            Long c1 = KeywordFinder.count1(keywords.get(i), Paths.get("searchInMe3.txt"));
            assertEquals(occ3.get(i), c1);
        }
    }

    @Test
    public void testHardCoded2() {
        for (int i = 0; i < keywords.size(); i++) {
            Long c1 = KeywordFinder.count2(keywords.get(i), Paths.get("searchInMe3.txt"));
            assertEquals(occ3.get(i), c1);
        }
    }

    @Test
    public void test0() {
        for (String s : keywords) {
            long c1 = KeywordFinder.count1(s, Paths.get("searchInMe0.txt"));
            long c2 = KeywordFinder.count2(s, Paths.get("searchInMe0.txt"));
            assertEquals(c1, c2);
        }
    }

    @Test
    public void test1() {
        for (String s : keywords) {
            long c1 = KeywordFinder.count1(s, Paths.get("searchInMe1.txt"));
            long c2 = KeywordFinder.count2(s, Paths.get("searchInMe1.txt"));
            assertEquals(c1, c2);
        }
    }

    @Test
    public void test2() {
        for (String s : keywords) {
            long c1 = KeywordFinder.count1(s, Paths.get("searchInMe2.txt"));
            long c2 = KeywordFinder.count2(s, Paths.get("searchInMe2.txt"));
            assertEquals(c1, c2);
        }
    }

    @Test
    public void test3() {
        for (String s : keywords) {
            long c1 = KeywordFinder.count1(s, Paths.get("searchInMe3.txt"));
            long c2 = KeywordFinder.count2(s, Paths.get("searchInMe3.txt"));
            assertEquals(c1, c2);
        }
    }

    @Test
    public void test4() {
        for (String s : keywords) {
            long c1 = KeywordFinder.count1(s, Paths.get("searchInMe4.txt"));
            long c2 = KeywordFinder.count2(s, Paths.get("searchInMe4.txt"));
            assertEquals(c1, c2);
        }
    }

    @Test
    public void test_apocalipseCountdown() {
        long c0 = KeywordFinder.count1("apocalipse", Paths.get("searchInMe0.txt"));
        long c1 = KeywordFinder.count1("apocalipse", Paths.get("searchInMe1.txt"));
        long c2 = KeywordFinder.count1("apocalipse", Paths.get("searchInMe4.txt"));
        assertEquals(c0, 0);
        assertTrue(c1 > c2);
    }

    @Test
    public void testExample() {
        String res = (String) ReflectionHelper.tryCatch(() -> String.class.getMethod("toString").invoke(""));
        assertEquals("", res);
    }

    @SuppressWarnings("serial")
    static class MyError extends Error {
    }

    @SuppressWarnings("serial")
    static class MyRuntime extends RuntimeException {
    }

    @SuppressWarnings("serial")
    static class MyChecked extends Exception {
    }

    static class DoErrors {
        public int foo() {
            throw new MyError();
        }

        public int bar() {
            throw new MyRuntime();
        }

        public int beer() throws Throwable {
            throw new MyChecked();
        }
    }

    @Test(expected = MyError.class)
    public void testError() {
        @SuppressWarnings("unused")
        String res = (String) ReflectionHelper.tryCatch(() -> DoErrors.class.getMethod("foo").invoke(new DoErrors()));
        throw new Error("Expected error");
    }

    @Test(expected = MyRuntime.class)
    public void testRuntime() {
        @SuppressWarnings("unused")
        String res = (String) ReflectionHelper.tryCatch(() -> DoErrors.class.getMethod("bar").invoke(new DoErrors()));
        throw new Error("Expected error");
    }

    @Test(expected = MyChecked.class)
    public void testChecked() throws Throwable {
        try {
            @SuppressWarnings("unused")
            String res = (String) ReflectionHelper
                    .tryCatch(() -> DoErrors.class.getMethod("beer").invoke(new DoErrors()));
        } catch (Error e) {
            throw e.getCause();
        }
        throw new Error("Expected error");
    }

    @Test(expected = NoSuchMethodException.class)
    public void testNoMethod() throws Throwable {
        try {
            @SuppressWarnings("unused")
            String res = (String) ReflectionHelper
                    .tryCatch(() -> DoErrors.class.getMethod("orange").invoke(new DoErrors()));
        } catch (Error e) {
            throw e.getCause();
        }
        throw new Error("Expected error");
    }

    public static List<Object> data1() {
        return Arrays.asList("a", "b", "c", 2, 5, "boo");
    }

    public static List<Point> data2() {
        return Arrays.asList(new Point(0, 0), new ColPoint(1, 1, 1), new Point(2, 2));
    }

    @Test
    public void testNoFilterData1() {
        List<Object> res = data1().stream().flatMap(FilterClass.isInstanceOf(Object.class))
                .collect(Collectors.toList());
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
        List<String> res = data1().stream().flatMap(FilterClass.isInstanceOf(String.class))
                .collect(Collectors.toList());
        assertEquals(4, res.size());
        assertEquals(Arrays.asList("a", "b", "c", "boo"), res);
    }

    @Test
    public void testOnlyColPoint() {
        List<Point> data2 = data2();// point do not override equals
        List<ColPoint> res = data2.stream().flatMap(FilterClass.isInstanceOf(ColPoint.class))
                .collect(Collectors.toList());
        assertEquals(1, res.size());
        assertEquals(data2.get(1), res.get(0));
    }

    @Test
    public void testNone() {
        List<String> res = data2().stream().flatMap(FilterClass.isInstanceOf(String.class))
                .collect(Collectors.toList());
        assertEquals(0, res.size());
    }

}
