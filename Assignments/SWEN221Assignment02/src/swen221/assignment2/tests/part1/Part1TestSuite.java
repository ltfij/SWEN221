package swen221.assignment2.tests.part1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PawnTests.class,
        KnightTests.class,
        BishopTests.class,
        RookTests.class,
        QueenTests.class,
        KingTests.class
})
public class Part1TestSuite {
}
