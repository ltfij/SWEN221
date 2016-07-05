package swen221.lab3.testing;

import org.junit.Test;
import org.junit.runners.MethodSorters;

import swen221.lab3.AdventureGame;
import swen221.lab3.model.*;
import swen221.lab3.model.Room.Location;
import swen221.lab3.util.GameFile;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;

/**
 * These test the adventure game
 * 
 * @author David J. Pearce
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdventureGameTests {
	
	// =================================================================
	// Tests
	// =================================================================
			
	@Test
	public void test_01_PickupCoin() {
		// Define game world for test
		String[] items = { 
				"Room { description: \"Dining Room\" }",				
				"Coin { location: 0 }"
		};
		// Create game
		AdventureGame game = new AdventureGame(GameFile.parseItems(items));
		//
		Player p = game.getPlayer();
		Room r = p.getLocation();
		Coin c = (Coin) r.getItem(Location.NORTHWEST);
		c.performAction("Pickup", p);
		// Check player has the coin
		assertTrue(p.getInventory().contains(c));
		// Check room doesn't have the coin
		assertTrue(r.containsItem(c) == null);
	}
	
	@Test
	public void test_02_EnterDoor() {
		// Define game world for test
		String[] items = { 
				"Room { description: \"Dining Room\" }",
				"Room { description: \"Hall\" }",
				"Door { from: 0, to: 1 }"
		};
		// Create game
		AdventureGame game = new AdventureGame(GameFile.parseItems(items));
		//
		Player p = game.getPlayer();
		Room r = p.getLocation();
		Door d = (Door) r.getItem(Location.NORTHWEST);
		d.performAction("Enter", p);
		// Check player not in old room
		assertTrue(p.getLocation() != r);
		// Check name of room is consistent
		assertTrue(p.getLocation().getDescription().equals("Hall"));		
	}
	
	@Test
	public void test_03_PickupBook() {
		// Define game world for test
		String[] items = { 
				"Room { description: \"Dining Room\" }",				
				"Book { location: 0, title: \"Great Expectations\" }"
		};
		// Create game
		AdventureGame game = new AdventureGame(GameFile.parseItems(items));
		//
		Player p = game.getPlayer();
		Room r = p.getLocation();
		Item i = (Item) r.getItem(Location.NORTHWEST);
		i.performAction("Pickup", p);
		// Check player has the book
		assertTrue(p.getInventory().contains(i));
		// Check room doesn't have the book
		assertTrue(r.containsItem(i) == null);
		// Check title is consistent
		assertTrue(i.getDescription().equals("A book entitled \"Great Expectations\""));
	}
	
	@Test
	public void test_04_DropBook() {
		// Define game world for test
		String[] items = { 
				"Room { description: \"Dining Room\" }",				
				"Book { location: 0, title: \"Great Expectations\" }"
		};
		// Create game
		AdventureGame game = new AdventureGame(GameFile.parseItems(items));
		//
		Player p = game.getPlayer();
		Room r = p.getLocation();
		Item i = (Item) r.getItem(Location.NORTHWEST);
		i.performAction("Pickup", p);
		i.performAction("Drop", p);
		// Check player doesn't have the book
		assertFalse(p.getInventory().contains(i));
		// Check room does have the book
		assertTrue(r.containsItem(i) != null);
	}
	
	@Test
	public void test_05_ReadBook() {
		// Define game world for test
		String[] items = { 
				"Room { description: \"Dining Room\" }",				
				"Book { location: 0, title: \"Great Expectations\" }"
		};
		// Create game
		AdventureGame game = new AdventureGame(GameFile.parseItems(items));
		//
		Player p = game.getPlayer();
		Room r = p.getLocation();
		Item i = (Item) r.getItem(Location.NORTHWEST);
		i.performAction("Pickup", p);
		// Check I can read this book
		assertTrue(i.performAction("Read", p));
		// Check title is consistent
		assertTrue(i.getDescription().equals("A book entitled \"Great Expectations\"; it looks like it has been read"));
	}
	
	@Test
	public void test_06_LockedDoor() {
		// Define game world for test
		String[] items = { 
				"Room { description: \"Dining Room\" }",
				"Room { description: \"Hall\" }",
				"LockedDoor { from: 0, to: 1, code: 123 }"
		};
		// Create game
		AdventureGame game = new AdventureGame(GameFile.parseItems(items));
		//
		Player p = game.getPlayer();
		Room r = p.getLocation();
		Item i = (Item) r.getItem(Location.NORTHWEST);
		// Check I cannot enter this door
		assertFalse(i.performAction("Enter", p));
		// Check nothing has happened
		assertTrue(p.getLocation() == r);
	}
	
	@Test
	public void test_07_UnlockDoorFails() {
		// Define game world for test
		String[] items = { 
				"Room { description: \"Dining Room\" }",
				"Room { description: \"Hall\" }",
				"LockedDoor { from: 0, to: 1, code: 123 }"
		};
		// Create game
		AdventureGame game = new AdventureGame(GameFile.parseItems(items));
		//
		Player p = game.getPlayer();
		Room r = p.getLocation();
		Item i = (Item) r.getItem(Location.NORTHWEST);
		// Check I cannot enter this door
		assertFalse(i.performAction("Unlock", p));
		// Check nothing has happened
		assertFalse(i.performAction("Enter", p));
	}
	
	@Test
	public void test_08_UnlockDoorFails() {
		// Define game world for test
		String[] items = { 
				"Room { description: \"Dining Room\" }",
				"Room { description: \"Hall\" }",
				"Key { location: 0, code: 456 }",
				"LockedDoor { from: 0, to: 1, code: 123 }",
				"Key { location: 0, code: 123 }"	
		};
		// Create game
		AdventureGame game = new AdventureGame(GameFile.parseItems(items));
		//
		Player p = game.getPlayer();
		Room r = p.getLocation();
		Item key = (Item) r.getItem(Location.NORTHWEST);
		Item door = (Item) r.getItem(Location.NORTH);
		key.performAction("Pickup", p);
		// Check I cannot enter this door yet
		assertFalse(door.performAction("Enter", p));
		// Check I can unlock this door
		assertFalse(door.performAction("Unlock", p));
		// Check I still cannot enter this door
		assertFalse(door.performAction("Enter", p));
	}
	
	@Test
	public void test_09_UnlockDoor() {
		// Define game world for test
		String[] items = { 
				"Room { description: \"Dining Room\" }",
				"Room { description: \"Hall\" }",
				"Key { location: 0, code: 123 }",
				"LockedDoor { from: 0, to: 1, code: 123 }"
		};
		// Create game
		AdventureGame game = new AdventureGame(GameFile.parseItems(items));
		//
		Player p = game.getPlayer();
		Room r = p.getLocation();
		Item key = (Item) r.getItem(Location.NORTHWEST);
		Item door = (Item) r.getItem(Location.NORTH);
		key.performAction("Pickup", p);
		// Check I cannot enter this door yet
		assertFalse(door.performAction("Enter", p));
		// Check I can unlock this door
		assertTrue(door.performAction("Unlock", p));
		// Check I can now enter this door
		assertTrue(door.performAction("Enter", p));
		// Check I moved into the other room
		assertTrue(p.getLocation().getDescription().equals("Hall"));
	}
	
	@Test
	public void test_10_LockDoor() {
		// Define game world for test
		String[] items = { 
				"Room { description: \"Dining Room\" }",
				"Room { description: \"Hall\" }",
				"Key { location: 0, code: 123 }",
				"LockedDoor { from: 0, to: 1, code: 123 }"
		};
		// Create game
		AdventureGame game = new AdventureGame(GameFile.parseItems(items));
		//
		Player p = game.getPlayer();
		Room r = p.getLocation();
		Item key = (Item) r.getItem(Location.NORTHWEST);
		Item door = (Item) r.getItem(Location.NORTH);
		key.performAction("Pickup", p);
		// Check I can unlock this door
		assertTrue(door.performAction("Unlock", p));
		// Check I can now enter this door
		assertTrue(door.performAction("Enter", p));
		// Check I can lock this door
		assertTrue(door.performAction("Lock", p));
		// Check I cannot now enter this door
		assertFalse(door.performAction("Enter", p));
	}
	
	@Test
	public void test_11_SecretButtonFails() {
		// Define game world for test
		String[] items = { 
				"Room { description: \"Dining Room\" }",
				"Room { description: \"Hall\" }",
				"SecretButton { location: 0, code: 456 }",
				"LockedDoor { from: 0, to: 1, code: 123 }"
		};
		// Create game
		AdventureGame game = new AdventureGame(GameFile.parseItems(items));
		//
		Player p = game.getPlayer();
		Room r = p.getLocation();
		Item button = (Item) r.getItem(Location.NORTHWEST);
		Item door = (Item) r.getItem(Location.NORTH);
		// Check I cannot go through this door
		assertFalse(door.performAction("Enter", p));
		// Now, press the button
		button.performAction("Press", p);
		// Check I still cannot unlock this door
		assertFalse(door.performAction("Enter", p)); 
	}
	
	@Test
	public void test_12_SecretButton() {
		// Define game world for test
		String[] items = { 
				"Room { description: \"Dining Room\" }",
				"Room { description: \"Hall\" }",
				"SecretButton { location: 0, code: 123 }",
				"LockedDoor { from: 0, to: 1, code: 123 }"
		};
		// Create game
		AdventureGame game = new AdventureGame(GameFile.parseItems(items));
		//
		Player p = game.getPlayer();
		Room r = p.getLocation();
		Item button = (Item) r.getItem(Location.NORTHWEST);
		Item door = (Item) r.getItem(Location.NORTH);
		// Check I cannot go through this door
		assertFalse(door.performAction("Enter", p));
		// Now, press the button
		button.performAction("Press", p);
		// Check I can now enter this door
		assertTrue(door.performAction("Enter", p));
		// Check I moved into the other room
		assertTrue(p.getLocation().getDescription().equals("Hall"));
	}
}
