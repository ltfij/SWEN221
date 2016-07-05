package swen221.assignment2.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        swen221.assignment2.tests.part1.Part1TestSuite.class,
        swen221.assignment2.tests.part2.Part2TestSuite.class,
        swen221.assignment2.tests.part3.Part3TestSuite.class
})
public class CompleteTestSuite {
}
