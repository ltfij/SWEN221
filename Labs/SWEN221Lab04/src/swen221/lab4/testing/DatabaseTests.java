package swen221.lab4.testing;

import java.util.Arrays;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

import swen221.lab4.io.DatabaseFileReader;
import swen221.lab4.lang.*;


/**
 * Some straightforward test cases for database functionality
 * 
 * @author David J. Pearce
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseTests {
	
	@Test
	public void test01_ValidFile() {
		String data = "ID:int*,Name:str\n" + 
					  "3093209,Sophie\n";
		//
		try {
			Database db = new DatabaseFileReader(data).read();
		} catch (DuplicateKeyException|InvalidRowException e) {
			fail(e.getMessage());
			return;
		} 
	}
	
	@Test
	public void test02_GetValidIndex() {
		String data = "ID:int*,Name:str\n" + 
					  "3093209,Sophie\n";
		//		
		Database db;
		//
		try {
			db = new DatabaseFileReader(data).read();
		} catch (DuplicateKeyException|InvalidRowException e) {
			fail(e.getMessage());
			return;
		} 
		// Check column 0 is key field
		assertTrue(db.getKeyField() == 0);
		// Check that there is only one row in the database
		assertTrue(db.size() == 1);
		// Check that the row at index 0 matches
		assertTrue(Arrays.equals(db.getRow(0),new Object[]{3093209,"Sophie"}));		
	}
	
	@Test
	public void test03_GetValidIndex() {
		String data = "ID:int*,Name:str\n" + 
					  "3093209,Sophie\n";
		//		
		Database db;
		//
		try {
			db = new DatabaseFileReader(data).read();
		} catch (DuplicateKeyException|InvalidRowException e) {
			fail(e.getMessage());
			return;
		} 
		// Check column 0 is key field
		assertTrue(db.getKeyField() == 0);
		// Check that there is only one row in the database
		assertTrue(db.size() == 1);
		// Check that the row at index 0 matches
		assertTrue(Arrays.equals(db.getRow(0),new Object[]{3093209,"Sophie"}));		
	}
	
	@Test
	public void test04_GetInvalidIndex() {
		String data = "ID:int*,Name:str\n" + 
					  "3093209,Sophie\n";
		//
		Database db;
		//
		try {
			db = new DatabaseFileReader(data).read();
		} catch (DuplicateKeyException|InvalidRowException e) {
			fail(e.getMessage());
			return;
		} 
		//
		try {
			// Check that invalid index fails
			db.getRow(-1);
		} catch(IndexOutOfBoundsException e) {
			return;
		}
		
		fail("IndexOutOfBoundsException not thrown");
	}
	
	@Test
	public void test05_GetInvalidIndex() {
		String data = "ID:int*,Name:str\n" + 
				"3093209,Sophie\n";
		//
		Database db;
		//
		try {
			db = new DatabaseFileReader(data).read();
		} catch (DuplicateKeyException|InvalidRowException e) {
			fail(e.getMessage());
			return;
		} 
		//
		try {
			// Check that invalid index fails	
			db.getRow(2);
		} catch(IndexOutOfBoundsException e) {
			return;
		}

		fail("IndexOutOfBoundsException not thrown");
	}
	
	@Test
	public void test06_GetValidKey() {
		String data = "ID:int*,Name:str\n" + 
					  "3093209,Sophie\n" + 
				      "3010312,Dave";
		//
		Database db;
		//
		try {
			db = new DatabaseFileReader(data).read();
		} catch (DuplicateKeyException|InvalidRowException e) {
			fail(e.getMessage());
			return;
		}
		// Check column 0 is key field
		assertTrue(db.getKeyField() == 0);
		// Check that there is only one row in the database
		assertTrue(db.size() == 2);
		//
		try {
			// Check that field with key 3093209 matches
			assertTrue(Arrays.equals(db.getRow((Integer) 3093209),new Object[] { 3093209,"Sophie" }));
			// Check that field with key 3010312 matches
			assertTrue(Arrays.equals(db.getRow((Integer) 3010312),new Object[] { 3010312,"Dave" }));
		} catch (InvalidKeyException e) {
			fail("Invalid Key Exception");
		}
	}
	
	@Test
	public void test07_GetValidKey() {
		String data = "ID:int,Name:str*\n" + 
					  "3093209,Sophie\n" + 
				      "3010312,Dave";
		//
		Database db;
		try {
			db = new DatabaseFileReader(data).read();
		} catch (DuplicateKeyException|InvalidRowException e) {
			fail(e.getMessage());
			return;
		}
		// Check column 0 is key field
		assertTrue(db.getKeyField() == 1);
		//
		try {
			// Check that field with key Sophie matches
			assertTrue(Arrays.equals(db.getRow("Sophie"),new Object[] { 3093209,"Sophie" }));
			// Check that field with key Dave matches
			assertTrue(Arrays.equals(db.getRow("Dave"),new Object[] { 3010312,"Dave" }));
		} catch (InvalidKeyException e) {
			fail("Invalid Key Exception");
		}
	}
	
	@Test
	public void test08_GetInvalidKey() {
		String data = "ID:int*,Name:str\n" + 
					  "3093209,Sophie\n" + 
				      "3010312,Dave";
		//
		Database db;
		//
		try {
			db = new DatabaseFileReader(data).read();
		} catch (DuplicateKeyException|InvalidRowException e) {
			fail(e.getMessage());
			return;
		}		
		// 		
		try {
			// Check that field with invalid key fails
			db.getRow((Integer) 1);			
		} catch (InvalidKeyException e) {
			return;
		}
		fail("Invalid Key Exception");
	}
	
	@Test
	public void test09_GetInvalidKey() {
		String data = "ID:int*,Name:str\n" + 
					  "3093209,Sophie\n" + 
				      "3010312,Dave";
		//
		Database db;
		//
		try {
			db = new DatabaseFileReader(data).read();
		} catch (DuplicateKeyException|InvalidRowException e) {
			fail(e.getMessage());
			return;
		}	
		// 
		try {
			// Check that field with invalid key fails
			db.getRow("Sophie");
		} catch (InvalidKeyException e) {
			return;
		}
		fail("Invalid Key Exception");
	}
	
	@Test
	public void test10_AddValidRow() {
		String data = "ID:int*,Name:str\n" + 
					  "3093209,Sophie\n";
		//
		Database db;
		//
		try {
			db = new DatabaseFileReader(data).read();
		} catch (DuplicateKeyException|InvalidRowException e) {
			fail(e.getMessage());
			return;
		}	
		//
		try {
			db.addRow(30103012,"Dave");		
			// Check that there is only one row in the database
			assertTrue(db.size() == 2);
			// Check that row at index 0 matches
			assertTrue(Arrays.equals(db.getRow(0),new Object[]{3093209,"Sophie"}));
			// Check that row at index 1 matches
			assertTrue(Arrays.equals(db.getRow(1),new Object[] { 30103012,"Dave" }));
		} catch (InvalidRowException e) {
			fail("Invalid Row Exception");
		} catch (DuplicateKeyException e) {
			fail("Duplicate Key Exception");
		}
	}
	
	@Test
	public void test11_AddInvalidRow() {
		String data = "ID:int*,Name:str\n" + 
					  "3093209,Sophie\n";
		//
		Database db;
		//
		try {
			db = new DatabaseFileReader(data).read();
		} catch (DuplicateKeyException|InvalidRowException e) {
			fail(e.getMessage());
			return;
		}	
		//
		try {
			db.addRow(3093209,"Sophie");		
		} catch (InvalidRowException e) {
			fail("Invalid Row Exception");
		} catch (DuplicateKeyException e) {
			return;
		}
		fail("DuplicateKeyException not thrown");
	}
	
	@Test
	public void test12_AddInvalidRow() {
		String data = "ID:int*,Name:str\n" + 
					  "3093209,Sophie\n";
		//
		Database db;
		//
		try {
			db = new DatabaseFileReader(data).read();
		} catch (DuplicateKeyException|InvalidRowException e) {
			fail(e.getMessage());
			return;
		}	
		//
		try {
			db.addRow(3093209,"Dave");		
		} catch (InvalidRowException e) {
			fail("Invalid Row Exception");
		} catch (DuplicateKeyException e) {
			return;
		}
		fail("DuplicateKeyException not thrown");
	}
	
	@Test
	public void test13_AddInvalidRow() {
		String data = "ID:int*,Name:str\n" + 
					  "3093209,Sophie\n";
		//
		Database db;
		//
		try {
			db = new DatabaseFileReader(data).read();
		} catch (DuplicateKeyException|InvalidRowException e) {
			fail(e.getMessage());
			return;
		}	
		//
		try {
			db.addRow("Dave","Dave");		
		} catch (InvalidRowException e) {
			return;
		} catch (DuplicateKeyException e) {
			fail("Invalid Key Exception");
		}
		fail("InvalidRowException not thrown");
	}
	
	@Test
	public void test14_AddInvalidRow() {
		String data = "ID:int*,Name:str\n" + 
					  "3093209,Sophie\n";
		//
		Database db;
		//
		try {
			db = new DatabaseFileReader(data).read();
		} catch (DuplicateKeyException|InvalidRowException e) {
			fail(e.getMessage());
			return;
		}	
		//
		try {
			db.addRow(30103012,30103012);		
		} catch (InvalidRowException e) {
			return;
		} catch (DuplicateKeyException e) {
			fail("Duplicate Key Exception");
		}
		fail("InvalidRowException not thrown");
	}
	
	@Test
	public void test15_AddInvalidRow() {
		String data = "ID:int*,Name:str\n" + 
					  "3093209,Sophie\n";
		//
		Database db;
		//
		try {
			db = new DatabaseFileReader(data).read();
		} catch (DuplicateKeyException|InvalidRowException e) {
			fail(e.getMessage());
			return;
		}	
		//
		try {
			db.addRow(30103012);		
		} catch (InvalidRowException e) {
			return;
		} catch (DuplicateKeyException e) {
			fail("Duplicate Key Exception");
		}
		fail("InvalidRowException not thrown");
	}
	
	@Test
	public void test16_AddInvalidRow() {
		String data = "ID:int*,Name:str\n" + 
					  "3093209,Sophie\n";
		//
		Database db;
		//
		try {
			db = new DatabaseFileReader(data).read();
		} catch (DuplicateKeyException|InvalidRowException e) {
			fail(e.getMessage());
			return;
		}	
		//
		try {
			db.addRow(30103012,"Dave",1);		
		} catch (InvalidRowException e) {
			return;
		} catch (DuplicateKeyException e) {
			fail("Duplicate Key Exception");
		}
		fail("InvalidRowException not thrown");
	}
}
