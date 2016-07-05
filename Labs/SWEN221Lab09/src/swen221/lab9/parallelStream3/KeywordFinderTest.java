package swen221.lab9.parallelStream3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class KeywordFinderTest {

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

}
