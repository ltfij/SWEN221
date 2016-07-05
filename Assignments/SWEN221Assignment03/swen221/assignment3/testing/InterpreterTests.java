package swen221.assignment3.testing;

import static org.junit.Assert.*;

import org.junit.Test;

import swen221.assignment3.shapes.Canvas;
import swen221.assignment3.shapes.Interpreter;


public class InterpreterTests {
	/**
	 * Some simple tests that the fill command is working. You will want to add
	 * more of your own tests!
	 */
	@Test public void validFillTests() {
		String[][] inputs = {
				{"x = [2,2,4,4]\nfill x #0000ff\n","#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n"},
				{"xyz= [2,2,4,4]\nfill xyz #010203\n","#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#010203#010203#010203#010203\n#ffffff#ffffff#010203#010203#010203#010203\n#ffffff#ffffff#010203#010203#010203#010203\n#ffffff#ffffff#010203#010203#010203#010203\n"},
				{"x =[2,2,4,4]\nfill x #0000ff\n","#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n"},
				{"fill [2,2,4,4] #0000ff\n","#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n"},
				{"x1=[2,2,4,4]\nfill x1 #0000ff\n","#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n"},
				{"x=[2 ,2,4,4]\nfill x #0000ff\n","#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n"},
				{"x=[2, 2,4,4]\nfill x #0000ff\n","#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n"},
				{"x=[2, 2, 4, 4]\nfill x #0000ff\n","#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n"}
			};
		testValidInputs(inputs);
	}
	
	/**
	 * Some simple tests that the draw command is working. You will want to add
	 * more of your own tests!
	 */
	@Test public void validDrawTests() {
		String[][] inputs = {				
				{"x = [2,2,4,4]\ndraw x #0000ff\n","#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#ffffff#ffffff#0000ff\n#ffffff#ffffff#0000ff#ffffff#ffffff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n"},
				{"x = [2,2,4,4]\ndraw x #010203\n","#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#010203#010203#010203#010203\n#ffffff#ffffff#010203#ffffff#ffffff#010203\n#ffffff#ffffff#010203#ffffff#ffffff#010203\n#ffffff#ffffff#010203#010203#010203#010203\n"},
				{"draw [2,2,4,4] #010203\n","#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#010203#010203#010203#010203\n#ffffff#ffffff#010203#ffffff#ffffff#010203\n#ffffff#ffffff#010203#ffffff#ffffff#010203\n#ffffff#ffffff#010203#010203#010203#010203\n"},
				{"x = [2,2,4,4]\ndraw x #0000ff\ny = [0,0,3,3]\ndraw y #010203\n","#010203#010203#010203#ffffff#ffffff#ffffff\n#010203#ffffff#010203#ffffff#ffffff#ffffff\n#010203#010203#010203#0000ff#0000ff#0000ff\n#ffffff#ffffff#0000ff#ffffff#ffffff#0000ff\n#ffffff#ffffff#0000ff#ffffff#ffffff#0000ff\n#ffffff#ffffff#0000ff#0000ff#0000ff#0000ff\n"},				
		};
			
		testValidInputs(inputs);			
	}
	
	/**
	 * Some simple tests that shape union is working. You will want to add
	 * more of your own tests!
	 */
	@Test public void validUnionTests() {
		String[][] inputs = {			
				{"x = [2,2,4,4]\ny = [0,0,3,3]\ny = y + x\ndraw y #00ff00\n","#00ff00#00ff00#00ff00#ffffff#ffffff#ffffff\n#00ff00#ffffff#00ff00#ffffff#ffffff#ffffff\n#00ff00#00ff00#ffffff#00ff00#00ff00#00ff00\n#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00\n"},
				{"x = [2,2,4,4]\ny = [0,0,3,3]\ny = y+ x\ndraw y #00ff00\n","#00ff00#00ff00#00ff00#ffffff#ffffff#ffffff\n#00ff00#ffffff#00ff00#ffffff#ffffff#ffffff\n#00ff00#00ff00#ffffff#00ff00#00ff00#00ff00\n#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00\n"},
				{"x = [2,2,4,4]\ny = [0,0,3,3]\ny = y +x\ndraw y #00ff00\n","#00ff00#00ff00#00ff00#ffffff#ffffff#ffffff\n#00ff00#ffffff#00ff00#ffffff#ffffff#ffffff\n#00ff00#00ff00#ffffff#00ff00#00ff00#00ff00\n#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00\n"},
				{"x = [2,2,4,4]\ny = [0,0,3,3]\ny = y+x\ndraw y #00ff00\n","#00ff00#00ff00#00ff00#ffffff#ffffff#ffffff\n#00ff00#ffffff#00ff00#ffffff#ffffff#ffffff\n#00ff00#00ff00#ffffff#00ff00#00ff00#00ff00\n#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00\n"},
				{"x = [2,2,4,4]\ny = [0,0,3,3]\ny = (y + x)\ndraw y #00ff00\n","#00ff00#00ff00#00ff00#ffffff#ffffff#ffffff\n#00ff00#ffffff#00ff00#ffffff#ffffff#ffffff\n#00ff00#00ff00#ffffff#00ff00#00ff00#00ff00\n#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00\n"},
				{"x = [2,2,4,4]\nx = ([0,0,3,3] + x)\ndraw x #00ff00\n","#00ff00#00ff00#00ff00#ffffff#ffffff#ffffff\n#00ff00#ffffff#00ff00#ffffff#ffffff#ffffff\n#00ff00#00ff00#ffffff#00ff00#00ff00#00ff00\n#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00\n"},
				{"x = [2,2,4,4]\ny = [0,0,3,3]\ny = y + x\nfill y #00ff00\n","#00ff00#00ff00#00ff00#ffffff#ffffff#ffffff\n#00ff00#00ff00#00ff00#ffffff#ffffff#ffffff\n#00ff00#00ff00#00ff00#00ff00#00ff00#00ff00\n#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00\n#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00\n#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00\n"},
		        // EXTRA
				{"draw ([1,1,1,5] + [1,2,2,4] + [1,3,3,3] + [1,4,4,2] + [1,5,5,1]) #00ff00", "#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#00ff00#ffffff#ffffff#ffffff#ffffff\n#ffffff#00ff00#00ff00#ffffff#ffffff#ffffff\n#ffffff#00ff00#ffffff#00ff00#ffffff#ffffff\n#ffffff#00ff00#ffffff#ffffff#00ff00#ffffff\n#ffffff#00ff00#00ff00#00ff00#00ff00#00ff00\n"},
				{"fill ([1,1,1,5] + [1,2,2,4] + [1,3,3,3] + [1,4,4,2] + [1,5,5,1]) #00ff00", "#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#00ff00#ffffff#ffffff#ffffff#ffffff\n#ffffff#00ff00#00ff00#ffffff#ffffff#ffffff\n#ffffff#00ff00#00ff00#00ff00#ffffff#ffffff\n#ffffff#00ff00#00ff00#00ff00#00ff00#ffffff\n#ffffff#00ff00#00ff00#00ff00#00ff00#00ff00\n"},
				{"x = [1,1,4,4]\ny = [0,0,3,3]\ny = y + x\ndraw y #00ff00\n","#00ff00#00ff00#00ff00#ffffff#ffffff\n#00ff00#ffffff#ffffff#00ff00#00ff00\n#00ff00#ffffff#ffffff#ffffff#00ff00\n#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#00ff00#00ff00#00ff00#00ff00\n"},
				{"x = [3,3,4,4]\ny = [0,0,3,3]\ny = y + x\ndraw y #00ff00\n","#00ff00#00ff00#00ff00#ffffff#ffffff#ffffff#ffffff\n#00ff00#ffffff#00ff00#ffffff#ffffff#ffffff#ffffff\n#00ff00#00ff00#00ff00#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00\n#ffffff#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00\n"},
				{"x = [4,4,4,4]\ny = [0,0,3,3]\ny = y + x\ndraw y #00ff00\n","#00ff00#00ff00#00ff00#ffffff#ffffff#ffffff#ffffff#ffffff\n#00ff00#ffffff#00ff00#ffffff#ffffff#ffffff#ffffff#ffffff\n#00ff00#00ff00#00ff00#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00\n#ffffff#ffffff#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#ffffff#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#ffffff#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00\n"},
				{"draw ((([1,1,5,5] + [3,0,1,7]) + [0,3,7,1]) + ([3,3,1,1] + (([0,0,1,1] + ([6,0,1,1] + [0,6,1,1])) + [6,6,1,1]))) #00ff00", "#00ff00#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#00ff00#00ff00#ffffff#00ff00#00ff00#ffffff\n#ffffff#00ff00#ffffff#ffffff#ffffff#00ff00#ffffff\n#00ff00#ffffff#ffffff#ffffff#ffffff#ffffff#00ff00\n#ffffff#00ff00#ffffff#ffffff#ffffff#00ff00#ffffff\n#ffffff#00ff00#00ff00#ffffff#00ff00#00ff00#ffffff\n#00ff00#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n"},
				{"draw (((([0,0,7,7] - [1,0,2,7]) - [4,0,2,7]) - [0,1,7,2]) - [0,4,7,2]) + (((((([1,1,5,5] - [2,2,3,3]) + [3,3,1,1]) - [3,1,1,5]) + [3,3,1,1]) - [1,3,5,1]) + [3,3,1,1]) #00ff00", "#00ff00#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#00ff00#00ff00#ffffff#00ff00#00ff00#ffffff\n#ffffff#00ff00#ffffff#ffffff#ffffff#00ff00#ffffff\n#00ff00#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#00ff00#ffffff#ffffff#ffffff#00ff00#ffffff\n#ffffff#00ff00#00ff00#ffffff#00ff00#00ff00#ffffff\n#00ff00#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n"},
				{"x = [0,0,7,7]\ny=[1,0,2,7]\n  z=[4,0,2,7]\nxyz=[0,1,7,2]\nx123=[0,4,7,2]\na456=[1,1,5,5]\nhappy=[2,2,3,3]\nlalala=[3,3,1,1]\n\nlmfao=[3,1,1,5]\nboring=[3,3,1,1]\nalmostthere=[1,3,5,1]\nfinally=[3,3,1,1]\n draw ((((x - y) - z) - xyz) - x123) + ((((((a456 - happy) + lalala) - lmfao) + boring) - almostthere) + finally) #00ff00", "#00ff00#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#00ff00#00ff00#ffffff#00ff00#00ff00#ffffff\n#ffffff#00ff00#ffffff#ffffff#ffffff#00ff00#ffffff\n#00ff00#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#00ff00#ffffff#ffffff#ffffff#00ff00#ffffff\n#ffffff#00ff00#00ff00#ffffff#00ff00#00ff00#ffffff\n#00ff00#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n"},
				// skull
				{"fill (((([2,0,5,9]-([2,2,5,2]-[4,2,1,2]))-([4,0,1,9]&[4,7,1,1]+[4,4,1,1]))+([1,1,1,4]+[7,1,1,4])&[0,0,9,9])-(([0,7,9,1]&[6,0,1,9])+([0,7,9,1]&[2,0,1,9])))-([3,6,1,1]+[0,6,9,1]&[5,5,1,4]) #00ff00", "#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00#00ff00#ffffff\n#ffffff#00ff00#00ff00#00ff00#00ff00#00ff00#00ff00#00ff00\n#ffffff#00ff00#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#00ff00#ffffff#ffffff#00ff00#ffffff#ffffff#00ff00\n#ffffff#00ff00#00ff00#00ff00#ffffff#00ff00#00ff00#00ff00\n#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00#00ff00#ffffff\n#ffffff#ffffff#00ff00#ffffff#00ff00#ffffff#00ff00#ffffff\n#ffffff#ffffff#ffffff#00ff00#ffffff#00ff00#ffffff#ffffff\n#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00#00ff00#ffffff\n"},
				
		};

		testValidInputs(inputs);
	}
	
	/**
	 * Some simple tests that shape intersection is working. You will want to add
	 * more of your own tests!
	 */
	@Test public void validIntersectionTests() {
		String[][] inputs = {
				{"x = [2,0,5,5]\ny = [0,2,4,4]\ndraw x #00ff00\ndraw y #0000ff\ny = y & x\ndraw y #ff0000","#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00#00ff00\n#ffffff#ffffff#00ff00#ffffff#ffffff#ffffff#00ff00\n#0000ff#0000ff#ff0000#ff0000#ffffff#ffffff#00ff00\n#0000ff#ffffff#ff0000#ff0000#ffffff#ffffff#00ff00\n#0000ff#ffffff#ff0000#ff0000#00ff00#00ff00#00ff00\n#0000ff#0000ff#0000ff#0000ff#ffffff#ffffff#ffffff\n"},				
				{"x = [2,0,5,5]\ny = [0,2,4,4]\ndraw x #00ff00\ndraw y #0000ff\ny = y& x\ndraw y #ff0000","#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00#00ff00\n#ffffff#ffffff#00ff00#ffffff#ffffff#ffffff#00ff00\n#0000ff#0000ff#ff0000#ff0000#ffffff#ffffff#00ff00\n#0000ff#ffffff#ff0000#ff0000#ffffff#ffffff#00ff00\n#0000ff#ffffff#ff0000#ff0000#00ff00#00ff00#00ff00\n#0000ff#0000ff#0000ff#0000ff#ffffff#ffffff#ffffff\n"},
				{"x = [2,0,5,5]\ny = [0,2,4,4]\ndraw x #00ff00\ndraw y #0000ff\ny = y &x\ndraw y #ff0000","#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00#00ff00\n#ffffff#ffffff#00ff00#ffffff#ffffff#ffffff#00ff00\n#0000ff#0000ff#ff0000#ff0000#ffffff#ffffff#00ff00\n#0000ff#ffffff#ff0000#ff0000#ffffff#ffffff#00ff00\n#0000ff#ffffff#ff0000#ff0000#00ff00#00ff00#00ff00\n#0000ff#0000ff#0000ff#0000ff#ffffff#ffffff#ffffff\n"},
				{"x = [2,0,5,5]\ny = [0,2,4,4]\ndraw x #00ff00\ndraw y #0000ff\ny =y&x\ndraw y #ff0000","#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00#00ff00\n#ffffff#ffffff#00ff00#ffffff#ffffff#ffffff#00ff00\n#0000ff#0000ff#ff0000#ff0000#ffffff#ffffff#00ff00\n#0000ff#ffffff#ff0000#ff0000#ffffff#ffffff#00ff00\n#0000ff#ffffff#ff0000#ff0000#00ff00#00ff00#00ff00\n#0000ff#0000ff#0000ff#0000ff#ffffff#ffffff#ffffff\n"},
				{"x = [2,0,5,5]\ndraw x #00ff00\nx = [0,2,4,4]\ndraw x #0000ff\nx =x&[2,0,5,5]\ndraw x #ff0000","#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00#00ff00\n#ffffff#ffffff#00ff00#ffffff#ffffff#ffffff#00ff00\n#0000ff#0000ff#ff0000#ff0000#ffffff#ffffff#00ff00\n#0000ff#ffffff#ff0000#ff0000#ffffff#ffffff#00ff00\n#0000ff#ffffff#ff0000#ff0000#00ff00#00ff00#00ff00\n#0000ff#0000ff#0000ff#0000ff#ffffff#ffffff#ffffff\n"},
				{"x = [2,0,5,5]\ny = [0,2,4,4]\ndraw x #00ff00\ndraw y #0000ff\ny = y & x\nfill y #ff0000","#ffffff#ffffff#00ff00#00ff00#00ff00#00ff00#00ff00\n#ffffff#ffffff#00ff00#ffffff#ffffff#ffffff#00ff00\n#0000ff#0000ff#ff0000#ff0000#ffffff#ffffff#00ff00\n#0000ff#ffffff#ff0000#ff0000#ffffff#ffffff#00ff00\n#0000ff#ffffff#ff0000#ff0000#00ff00#00ff00#00ff00\n#0000ff#0000ff#0000ff#0000ff#ffffff#ffffff#ffffff\n"}				
		};				
			
		testValidInputs(inputs);			
	}
	
	/**
	 * Some simple tests that shape difference is working. You will want to add
	 * more of your own tests!
	 */
	@Test public void validDifferenceTests() {
		String[][] inputs = {
				{"x = [2,0,5,5]\ny = [0,2,4,5]\ny = y - x\ndraw y #ff0000\n","#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#ffffff#ffffff\n#ff0000#ff0000#ffffff#ffffff\n#ff0000#ff0000#ffffff#ffffff\n#ff0000#ff0000#ffffff#ffffff\n#ff0000#ffffff#ff0000#ff0000\n#ff0000#ff0000#ff0000#ff0000\n"},				
				{"x = [2,0,5,5]\ny = [0,2,4,5]\ny = y- x\ndraw y #ff0000\n","#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#ffffff#ffffff\n#ff0000#ff0000#ffffff#ffffff\n#ff0000#ff0000#ffffff#ffffff\n#ff0000#ff0000#ffffff#ffffff\n#ff0000#ffffff#ff0000#ff0000\n#ff0000#ff0000#ff0000#ff0000\n"},
				{"x = [2,0,5,5]\ny = [0,2,4,5]\ny = y -x\ndraw y #ff0000\n","#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#ffffff#ffffff\n#ff0000#ff0000#ffffff#ffffff\n#ff0000#ff0000#ffffff#ffffff\n#ff0000#ff0000#ffffff#ffffff\n#ff0000#ffffff#ff0000#ff0000\n#ff0000#ff0000#ff0000#ff0000\n"},
				{"x = [2,0,5,5]\ny = [0,2,4,5]\ny =  y-x\ndraw y #ff0000\n","#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#ffffff#ffffff\n#ff0000#ff0000#ffffff#ffffff\n#ff0000#ff0000#ffffff#ffffff\n#ff0000#ff0000#ffffff#ffffff\n#ff0000#ffffff#ff0000#ff0000\n#ff0000#ff0000#ff0000#ff0000\n"},
				{"x = [2,0,5,5]\nx = [0,2,4,5]-x\ndraw x #ff0000\n","#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#ffffff#ffffff\n#ff0000#ff0000#ffffff#ffffff\n#ff0000#ff0000#ffffff#ffffff\n#ff0000#ff0000#ffffff#ffffff\n#ff0000#ffffff#ff0000#ff0000\n#ff0000#ff0000#ff0000#ff0000\n"},
				{"x = [2,0,5,5]\ny = [0,2,4,5]\ny =  y-x\nfill y #ff0000\n","#ffffff#ffffff#ffffff#ffffff\n#ffffff#ffffff#ffffff#ffffff\n#ff0000#ff0000#ffffff#ffffff\n#ff0000#ff0000#ffffff#ffffff\n#ff0000#ff0000#ffffff#ffffff\n#ff0000#ff0000#ff0000#ff0000\n#ff0000#ff0000#ff0000#ff0000\n"}
		};
			
		testValidInputs(inputs);			
	}

	@Test public void invalidSyntaxTests() {
		// This test makes sure that the interpreter throws an appropriate error message
		String[] inputs = {
		        // invalid syntax
				"x    = [2,2,4,4]\nfill y #0000ff\n",
				"x =    [2,2,4,4]\nfill x #x000ff\n",
				"x = [2,   2,4,4]\nfill x #000hff\n",
				"x = [2,2,   4,4\nfill x #0000ff\n",
				"x = [2,  2,4,   4,2]\nfill x #0000ff\n",
				"x = [2.012  ,2,4,   4   ]\nfill x #0000ff\n",
				"x= [-4,4,2,2]\nfill x #010203\n",
				"x  =  [4,-4,2,2]\nfill x #010203\n",
				"    x= [4  ,4,-2,2]\n   fill x #010203\n",
				"x= [4,4,2,-2]\nfill x #010203\n",
				"x= [4,4,2,2]\nfill x\n",
				"x= [4,4,2,2]\ndraw z\n",
				"x = [2,0,5,5]\ny = [0,2,4,5]\ny =  y-z\nfill y #ff0000\n",
				"x = [2,0,5,5]\ny = [0,2,4,5]\ny =  [y-x]\nfill y #ff0000\n",
				"x = [2,0,5,5]\ny = [0,2,4,5]\ny =  (y-x\nfill y #ff0000\n",
				"x = [2,0,5]\nfill x #ff0000\n",
				"1x = [2,0,5]\nfill x #ff0000\n",
				"(x-y) = [2, 2,0,5]\nfill x #ff0000\n",
				"x-y = [2,2,0,5]\nfill x #ff0000\n",
				"x += [2,0,5,5]\nfill x #ff0000\n",
			};
				
		for(int i=0;i!=inputs.length;++i) {
			String input = inputs[i];
			try {
				Canvas canvas = new Interpreter(input).run();
				fail("Input " + i  + " should have given an error!");				
			} catch(IllegalArgumentException e) {
				// ok, this seems alright!
			} catch(Exception e) {
				fail("Exception occurred: " + e.getMessage());
			}
		}				
	}		
	
	private void testValidInputs(String[][] inputs) {
		for(int i=0;i!=inputs.length;++i) {
			String[] input = inputs[i];
			try {
				Canvas canvas = new Interpreter(input[0]).run();
				String output = canvas.toString();
				if(!input[1].equals(output)) {
					System.out.println(output);
					fail("Incorrect output on input " + i + " : " + input[0]);
				}
			} catch(Exception e) {
				fail("Exception occurred: " + e.getMessage());
			}
		}
	}	
}
