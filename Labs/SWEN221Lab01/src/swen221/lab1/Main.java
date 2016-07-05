package swen221.lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class Main {
	private Main() {} // avoid instantiation of this class

	public static void main(String[] args) {
		final BufferedReader input =
			new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("Welcome to the Calculator!");			
			while(true) {				
				System.out.print("> ");
				String text = input.readLine();

				// commands goes here
				if(text.equals("help")) {
					printHelp();
				} else if(text.equals("exit")) {
					System.exit(0);
				} else {
					calculate(text);
				}
			}
		} catch(IOException e) {
			System.err.println("I/O Error - " + e.getMessage());
		}
	}

	private static void calculate(String text) {
		try {										
			Calculator calc = new Calculator(text);
			System.out.println("= " + calc.evaluate());
		} catch(RuntimeException e) {
			// Catching runtime exceptions is actually rather bad style;
			// see lecture about Exceptions later in the course!
			e.printStackTrace();
			System.err.println("Type \"help\" for help");
		}
	}

	private static void printHelp() {
		System.out.println("Calculator commands:");
		System.out.println("\thelp --- access this help page");
		System.out.println("\texit --- quit the calculator");
	}
}
