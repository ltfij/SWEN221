package swen221.lab1;

import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import org.junit.Test;
import static org.junit.Assert.*;
import java.math.BigDecimal;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CalculatorTests {
	
	private void test(String input) {
 		test(input, input);
	}

	@Test
	public void test_01() {
		test("3");
	}

	@Test
	public void test_02() {
		test("12387", "12387");
	}

	@Test
	public void test_03() {
		test("0.00137");
	}

	@Test
	public void test_04() {
		test("0.6789723");
	}

	@Test
	public void test_05() {
		test("1.22345");
	}

	@Test
	public void test_06() {
		test("1+1", "2");
	}

	@Test
	public void test_07() {
		test("2+0.387", "2.387");
	}

	@Test
	public void test_08() {
		test("3+47", "50");
	}

	@Test
	public void test_09() {
		test("4.01+7723.089", "7727.099");
	}

	@Test
	public void test_10() {
		test("0.5+4783", "4783.5");
	}

	@Test
	public void test_11() {
		test("0.0 + 12387", "12387.0");
	}

	@Test
	public void test_12() {
		test("0.00123+ 1", "1.00123");
	}

	@Test
	public void test_13() {
		test("1-1", "0");
	}

	@Test
	public void test_14() {
		test("2-0.387", "1.613");
	}

	@Test
	public void test_15() {
		test("3-47", "-44");
	}

	@Test
	public void test_16() {
		test("4.01-7723.089", "-7719.079");
	}

	@Test
	public void test_17() {
		test("0.5-4783", "-4782.5");
	}

	@Test
	public void test_18() {
		test("0.0 - 12387", "-12387.0");
	}

	@Test
	public void test_19() {
		test("0.00123- 1", "-0.99877");
	}

	@Test
	public void test_20() {
		test("3*3", "9");
	}

	@Test
	public void test_21() {
		test("2*0.387", "0.774");
	}

	@Test
	public void test_22() {
		test("3*47", "141");
	}

	@Test
	public void test_23() {
		test("4.01*7723.089", "30969.58689");
	}

	@Test
	public void test_24() {
		test("0.5*4783", "2391.5");
	}

	@Test
	public void test_25() {
		test("0.0 * 12387", "0.0");
	}

	@Test
	public void test_26() {
		test("0.00123*1", "0.00123");
	}

	@Test
	public void test_27() {
		test("1/1", "1");
	}

	@Test
	public void test_28() {
		test("2/0.5", "4");
	}

	@Test
	public void test_29() {
		test("3/4", "0.75");
	}

	@Test
	public void test_30() {
		test("400/7", "57.1428571429");
	}

	@Test
	public void test_31() {
		test("0.5/4783", "0.0001045369");
	}

	@Test
	public void test_32() {
		test("0.00923/1", "0.00923");
	}
	
	// =======================================================
	// Test Harness
	// =======================================================
	
	private void test(String input, String output) {		
		BigDecimal out = new Calculator(input).evaluate();
		BigDecimal num = new BigDecimal(output);
		if (num.compareTo(out) != 0) {
			fail(input + " => " + out + ", not " + output);
		}		
	}

}
