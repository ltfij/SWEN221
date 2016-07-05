package swen221.lab2.testing;

import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;

import static swen221.lab2.CellDecayGameOfLife.CellDecayRules;
import swen221.lab2.model.*;

/**
 * These test Conway's original rules for the Game of life in various ways. They
 * don't test other possible rule implementations though.
 * 
 * @author David J. Pearce
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CellDecayTests {
	
	// =================================================================
	// Tests
	// =================================================================
			
	@Test
	public void test11_CellDecay_UnderPopulation() {
		int[] board = { 
				9, 9, 9,
				9, 0, 9,
				9, 9, 9				
		}; 
		
		int[] expected = { 
				9, 9, 9,
				9, 2, 9,
				9, 9, 9				
		}; 
		
		runTest(3,3,board,expected,2);
	}
	
	@Test
	public void test12_CellDecay_UnderPopulation() {
		int[] board = { 
				9, 9, 9,
				8, 0, 9,
				9, 9, 9				
		}; 
		
		int[] expected = { 
				9, 9, 9,
				9, 2, 9,
				9, 9, 9				
		}; 
		
		runTest(3,3,board,expected,2);
	}
	
	// ----------------------------------
	
	@Test
	public void test13_CellDecay_NextGeneration() {
		int[] board = { 
				1, 0, 3,
				9, 9, 9,
				9, 9, 9				
		}; 
		
		int[] expected = { 
				2, 0, 4,
				8, 7, 8,
				9, 9, 9				
		}; 
		
		runTest(3,3,board,expected,2);
	}
	
	@Test
	public void test14_CellDecay_NextGeneration() {
		int[] board = { 
				9, 0, 9,
				0, 0, 9,
				9, 9, 9				
		}; 
		
		int[] expected = { 
				7, 0, 9,
				0, 0, 9,
				9, 9, 9				
		}; 
		
		runTest(3,3,board,expected,2);
	}
	
	// ----------------------------------
	
	@Test
	public void test15_CellDecay_Reproduction() {
		int[] board = { 
				9, 0, 9,
				0, 9, 0,
				9, 9, 9				
		}; 
		
		int[] expected = { 
				8, 0, 8,
				1, 7, 1,
				9, 8, 9				
		}; 
		
		runTest(3,3,board,expected,2);
	}
	
	@Test
	public void test16_CellDecay_Reproduction() {
		int[] board = { 
				0, 9, 9,
				0, 9, 9,
				0, 9, 9				
		}; 
		
		int[] expected = { 
				1, 8, 9,
				0, 7, 9,
				1, 8, 9				
		}; 
		
		runTest(3,3,board,expected,2);
	}
	
	// ----------------------------------
	
	@Test
	public void test17_CellDecay_Overpopulation() {
		int[] board = { 
				0, 0, 0,
				0, 1, 0,
				0, 0, 0				
		}; 
		
		int[] expected = { 
				0, 2, 0,
				2, 3, 2,
				0, 2, 0				
		}; 
		
		runTest(3,3,board,expected,2);
	}
	
	@Test
	public void test18_CellDecay_Overpopulation() {
		int[] board = { 
				0, 9, 0,
				9, 0, 9,
				0, 9, 0				
		}; 
		
		int[] expected = { 
				0, 9, 0,
				9, 2, 9,
				0, 9, 0		
		}; 
		
		runTest(3,3,board,expected,2);
	}

	// ----------------------------------

	@Test
	public void test19_CellDecay_Glider() {
		int[] board = { 
				9, 0, 9, 9,
				9, 9, 0, 9,
				0, 0, 0, 9,
				9, 9, 9, 9
		}; 
		
		int[] expected = { 
				9, 1, 9, 9,
				8, 9, 0, 9,
				1, 0, 0, 9,
				9, 8, 9, 9
		}; 
		
		runTest(4,4,board,expected,1);
	}
	
	@Test
	public void test20_CellDecay_Glider() {
		int[] board = { 
				9, 0, 9, 9,
				9, 9, 0, 9,
				0, 0, 0, 9,
				9, 9, 9, 9
		}; 
		
		int[] expected = { 
				9, 1, 9, 9,
				7, 9, 0, 9,
				0, 1, 0, 9,
				8, 7, 8, 9
		}; 
		
		runTest(4,4,board,expected,2);
	}
	
	// =================================================================
	// Helper Methods
	// =================================================================
	
	private void runTest(int width, int height, int[] startingCells, int[] expectedCells, int steps) {
		// Create the game board and simulation
		Board board = new Board(width,height,startingCells);
		Simulation sim = new Simulation(board,CellDecayRules);
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
}
