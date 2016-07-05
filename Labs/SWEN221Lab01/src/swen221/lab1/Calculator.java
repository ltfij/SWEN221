package swen221.lab1;

import java.math.BigDecimal;

public class Calculator {
	// Input string being evaluated
	private String input;

	// current position within the input string
	private int index;

	/**
	 * Constructor a calculator to evaluate a given input string.
	 *
	 * @param input
	 */
	public Calculator(String input) {
		this.input = input;
		this.index = 0;
	}

	/**
	 * Evaluate the expression which begins at the current position in the
	 * input.
	 *
	 * @return
	 */
	public BigDecimal evaluate() {
		skipWhiteSpace();
		char lookahead = input.charAt(index);

		BigDecimal value = null;

		if(lookahead == '(') {
			value = evaluateBracketed();
		} else if(Character.isDigit(lookahead)) {
			value = readNumber();
		} else {
			error("'(','0',..,'9'");
		}

		skipWhiteSpace();
		if(index < input.length()) {
			lookahead = input.charAt(index);

			if(lookahead == '+') {
				match('+');
				value = value.add(evaluate());
			} else if(lookahead == '*') {
				match('*');
				value = value.multiply(evaluate());
			} else if(lookahead == '/') {
				match('/');
				BigDecimal divisor = evaluate();
				// See JavaDoc for java.lang.BigDecimal for more information on
				// why we need to use the scale and rounding mode here.
				value = value.divide(divisor,10,BigDecimal.ROUND_HALF_UP);
			} else if(lookahead == '-') {
				match('-');
				value = value.subtract(evaluate());
			} else {
				error("'+' or '-' or '*' or '\\'");
			}
		}

		return value;
	}

	/**
	 * Evaluate an expression which is enclosed in braces.
	 *
	 * @return
	 */
	private BigDecimal evaluateBracketed() {
		match('(');
		BigDecimal value = evaluate();
		match(')');
		return value;
	}

	/**
	 * Read a decimal number from the input stream which may contain one decimal
	 * point. For example, "123", "123.0" are valid numbers. But, "123,3" is
	 * not and neither is "123.0.0".
	 *
	 * @return
	 */
	private BigDecimal readNumber() {
		int start = index;
		while (index < input.length() && (Character.isDigit(input.charAt(index)) || input.charAt(index) == '.')) {
			index = index + 1;
		}

		return new BigDecimal(input.substring(start, index));
	}

	/**
	 * Match a given string at the current position in the input. This method is
	 * used when we are expecting a specific string to come next.
	 *
	 * @param text
	 */
	private void match(char text) {
		skipWhiteSpace();
		if(index < input.length() && input.charAt(index) == text) {
			index = index + 1;
		} else {
			error("\'" + text + "\'");
		}
	}

	/**
	 * Advance over any spaces or newline characters in the input string.
	 */
	private void skipWhiteSpace() {
		while (index < input.length()
				&& (input.charAt(index) == ' ' || input.charAt(index) == '\n')) {
			index = index + 1;
		}
	}

	/**
	 * Report an error arising at the current position in the input string.
	 */
	private void error(String expected) {
		String msg;
		if(index < input.length()) {
			msg = "Found " + "\'" + input.charAt(index) + "\'" + ", expected " + expected + ": ";
		} else {
			msg = "Reached End-Of-Input, expected " + expected + ": ";
		}
		System.err.println(msg + input);
		for(int i=0;i!=index+msg.length();++i) {
			System.err.print(" ");
		}
		System.err.println("^");
		throw new RuntimeException("Parse Error");
	}
}
