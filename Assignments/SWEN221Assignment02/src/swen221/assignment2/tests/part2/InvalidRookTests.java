package swen221.assignment2.tests.part2;

import static swen221.assignment2.tests.TestHelpers.*;

import org.junit.Test;
import junit.framework.TestCase;

public class InvalidRookTests extends TestCase {
	
	public @Test void testInvalidRookMoves() {
		String[] tests = { 
		        // Extra tests
		        "Rc1-c3",
                "Ra1-a3",
                "Ra1-b3",
                "a2-a4 h7-h6\nRa1-a3 g7-g6\nRa3-c5",
                "a2-a4 h7-h5\nRa1-a3 Rh8-h6\nRa3-h3 Rh6-a6\nRh3-h7",
                "a2-a4 Re8-e6",
                "a2-a4 Rh8-h6",
                "a2-a4 Rh8-d6",
                "a2-a4 h7-h5\nRa1-a3 Rh8-h6\nRa3-h3 Rh6-d4",
                "a2-a4 h7-h5\nRa1-a3 Rh8-h6\nRa3-h3 Rh6-a6\nb2-b4 Ra6-a2",
		};
		checkInvalidTests(tests);
	}
	
	public @Test void testInvalidRookTakes() {
		String[] tests = {	
		        // Extra tests
		        "Rc1xc3",
		        "Ra1xa3",
		        "Ra1xb3",
		        "Ra1xa7",
		        "Ra1xb7",
		        "a2-a4 h7-h6\nRa1-a3 g7-g6\nRa3xc5",
		        "a2-a4 h7-h5\nRa1-a3 Rh8-h6\nRa3-h3 Rh6-a6\nRh3xh7",
		        "a2-a4 h7-h5\nRa1-a3 Rh8-h6\nRa3-h3 Rh6-a6\nRh3xh8",
		        "a2-a4 h7-h5\nRa1-a3 Rh8-h6\nRa3-h3 Rh6-a6\nRh3xd7",
		        "a2-a4 h7-h5\nRa1-a3 Rh8-h6\nRa3-h3 Rh6-a6\nRh3xh1",
		        "a2-a4 Re8xe6",
		        "a2-a4 Rh8xh6",
		        "a2-a4 Rh8xh2",
		        "a2-a4 Rh8xe2",
		        "a2-a4 h7-h5\nRa1-a3 Rh8-h6\nRa3-h3 Rh6xd4",
		        "a2-a4 h7-h5\nRa1-a3 Rh8-h6\nRa3-h3 Rh6-a6\nb2-b4 Ra6xa2",
		        "a2-a4 h7-h5\nRa1-a3 Rh8-h6\nRa3-h3 Rh6-a6\nb2-b4 Ra6xa1",
		        "a2-a4 h7-h5\nRa1-a3 Rh8-h6\nRa3-h3 Rh6-a6\nb2-b4 Ra6xe2",
		        "a2-a4 h7-h5\nRa1-a3 Rh8-h6\nRa3-h3 Rh6-a6\nb2-b4 Ra6xa8",

		};
		
		checkInvalidTests(tests);
	}
}
