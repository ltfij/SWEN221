package swen221.lab9.testing;

import org.junit.Test;
import org.junit.runners.MethodSorters;

import swen221.lab9.model.*;
import swen221.lab9.util.Pair;
import swen221.lab9.util.Point;

import static org.junit.Assert.*;
import static swen221.lab9.GameOfLife.ConwaysOriginalRules;

import java.util.function.Function;

import org.junit.FixMethodOrder;

/**
 * These test Conway's original rules for the Game of life in various ways. They
 * don't test other possible rule implementations though.
 * 
 * @author David J. Pearce
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameOfLifeTests {
	
	// =================================================================
	// Tests
	// =================================================================
			
	@Test
	public void test01_UnderPopulation() {
		int[] board = { 
				9, 9, 9,
				9, 0, 9,
				9, 9, 9				
		}; 
		
		int[] expected = { 
				9, 9, 9,
				9, 9, 9,
				9, 9, 9				
		}; 
		
		runTest(3,3,board,expected,1);
	}
	
	@Test
	public void test02_UnderPopulation() {
		int[] board = { 
				9, 9, 9,
				0, 0, 9,
				9, 9, 9				
		}; 
		
		int[] expected = { 
				9, 9, 9,
				9, 9, 9,
				9, 9, 9				
		}; 
		
		runTest(3,3,board,expected,1);
	}
	
	// ----------------------------------
	
	@Test
	public void test03_NextGeneration() {
		int[] board = { 
				0, 0, 0,
				9, 9, 9,
				9, 9, 9				
		}; 
		
		int[] expected = { 
				9, 0, 9,
				9, 0, 9,
				9, 9, 9				
		}; 
		
		runTest(3,3,board,expected,1);
	}
	
	@Test
	public void test04_NextGeneration() {
		int[] board = { 
				9, 0, 9,
				0, 0, 9,
				9, 9, 9				
		}; 
		
		int[] expected = { 
				0, 0, 9,
				0, 0, 9,
				9, 9, 9				
		}; 
		
		runTest(3,3,board,expected,1);
	}
	
	// ----------------------------------
	
	@Test
	public void test05_Reproduction() {
		int[] board = { 
				9, 0, 9,
				0, 9, 0,
				9, 9, 9				
		}; 
		
		int[] expected = { 
				9, 0, 9,
				9, 0, 9,
				9, 9, 9				
		}; 
		
		runTest(3,3,board,expected,1);
	}
	
	@Test
	public void test06_Reproduction() {
		int[] board = { 
				0, 9, 9,
				0, 9, 9,
				0, 9, 9				
		}; 
		
		int[] expected = { 
				9, 9, 9,
				0, 0, 9,
				9, 9, 9				
		}; 
		
		runTest(3,3,board,expected,1);
	}
	
	// ----------------------------------
	
	@Test
	public void test07_Overpopulation() {
		int[] board = { 
				0, 0, 0,
				0, 0, 0,
				0, 0, 0				
		}; 
		
		int[] expected = { 
				0, 9, 0,
				9, 9, 9,
				0, 9, 0				
		}; 
		
		runTest(3,3,board,expected,1);
	}
	
	@Test
	public void test08_Overpopulation() {
		int[] board = { 
				0, 9, 0,
				9, 0, 9,
				0, 9, 0				
		}; 
		
		int[] expected = { 
				9, 0, 9,
				0, 9, 0,
				9, 0, 9				
		}; 
		
		runTest(3,3,board,expected,1);
	}

	// ----------------------------------

	@Test
	public void test09_Glider() {
		int[] board = { 
				9, 0, 9, 9,
				9, 9, 0, 9,
				0, 0, 0, 9,
				9, 9, 9, 9
		}; 
		
		int[] expected = { 
				9, 9, 9, 9,
				0, 9, 0, 9,
				9, 0, 0, 9,
				9, 0, 9, 9
		}; 
		
		runTest(4,4,board,expected,1);
	}
	
	@Test
	public void test10_Glider() {
		int[] board = { 
				9, 0, 9, 9,
				9, 9, 0, 9,
				0, 0, 0, 9,
				9, 9, 9, 9
		}; 
		
		int[] expected = { 
				9, 9, 9, 9,
				9, 9, 0, 9,
				0, 9, 0, 9,
				9, 0, 0, 9
		}; 
		
		runTest(4,4,board,expected,2);
	}
	
	// =================================================================
	// Helper Methods
	// =================================================================
	
	private void runTest(int width, int height, int[] startingCells, int[] expectedCells, int steps) {
		// Create the game board and simulation
		Board board = new Board(width,height,startingCells);
		Simulation sim = new Simulation(board,ConwaysOriginalRules);
		// Run the simulation for the given number of steps
		sim.step(steps);		
		// Check the final board state is as expected
		for(int x=0;x<width;++x) {
			for(int y=0;y<height;++y) {
				int actual = board.getCellState(x, y);
				int expected = expectedCells[(y*width)+x];				
				if(actual != expected) {					
					System.err.println("ACTUAL BOARD:");
					System.err.println(board);
					System.err.println("EXPECTED BOARD:");
					System.err.println(new Board(width,height,expectedCells));
					fail("Cell (" + x + "," + y + ") has state " + actual + ", expected " + expected);
				}
				assertTrue(board.getCellState(x, y) == expected);
			}	
		}		
	}
	
	// NOTE: the following line is just there to prevent you from using the
	// previous solution to this lab. This line will be in the tests used by the
	// marking system. 
	private static final Function<Pair<Point,Board>,Integer>[] rules = ConwaysOriginalRules;
}
