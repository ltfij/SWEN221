package clint;
import java.io.*;
import java.util.*;

import swen221.monopoly.*;
import swen221.monopoly.locations.Location;
import swen221.monopoly.locations.Property;
import swen221.monopoly.locations.Street;

/**
 * This class contains the code for interfacing with the Monopoly game. It also
 * contains much of the game logic for controlling how the user can interact.
 *
 * @author David J. Pearce
 */
public class TextClient {

	/**
	 * Get string from System.in
	 */
	private static String inputString(String msg) {
		System.out.print(msg + " ");
		while (1 == 1) {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					System.in));
			try {
				return input.readLine();
			} catch (IOException e) {
				System.out.println("I/O Error ... please try again!");
			}
		}
	}

	/**
	 * Get integer from System.in
	 */
	private static int inputNumber(String msg) {
		System.out.print(msg + " ");
		while (1 == 1) {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					System.in));
			try {
				String v = input.readLine();
				return Integer.parseInt(v);
			} catch (IOException e) {
				System.out.println("Please enter a number!");
			}
		}
	}

	/**
	 * Input player details from System.in
	 */
	private static ArrayList<Player> inputPlayers(int nplayers, Location go) {
		// set up the tokens
		ArrayList<Player.Token> tokens = new ArrayList<Player.Token>();
		for(Player.Token t : Player.Token.values()) {
			tokens.add(t);
		}
		
		// now, input data
		ArrayList<Player> players = new ArrayList<Player>();

		for (int i = 0; i != nplayers; ++i) {
			String name = inputString("Player #" + i + " name?");
			String tokenName = inputString("Player #" + i + " token?");
			Player.Token token = Player.Token.valueOf(tokenName);
			while(!tokens.contains(token)) {
				System.out.print("Invalid token!  Must be one of: ");
				boolean firstTime = true;
				for (Player.Token t : Player.Token.values()) {
					if (!firstTime) {
						System.out.print(", ");
					}
					firstTime = false;
					System.out.print("\"" + t + "\"");
				}
				System.out.println();
				tokenName = inputString("Player #" + i + " token?");
				token = Player.Token.valueOf(tokenName);
			}
			tokens.remove(token);
			players.add(new Player(name, token, 1500, go));
		}
		return players;
	}

	/*
	 * ============================================================== Funny how
	 * the following all look like use cases ... or is it ?
	 * ==============================================================
	 */

	private static void movePlayer(Player player, int nsteps, GameOfMonopoly game) {
		int oldBalance = player.getBalance();
		game.movePlayer(player, nsteps);
		System.out.print(player.getName() + "'s " + player.getToken() + " lands on " + player.getLocation().getName());
		int newBalance = player.getBalance();
		if(oldBalance != newBalance) {
		    System.out.print("Rent deducted: $" + (oldBalance - newBalance));
		}
		System.out.println("");
	}

	private static void buyProperty(Player player, GameOfMonopoly game)
			throws GameOfMonopoly.InvalidMove {
		Location location = player.getLocation();
		game.buyProperty(player);
		System.out.println(player.getName() + " purchased "
				+ location.getName() + " from bank for $"
				+ ((Property) location).getPrice());
		System.out.println(player.getName() + " now has $"
				+ player.getBalance() + " remaining.");
	}

	private static void sellProperty(Player player, GameOfMonopoly game)
			throws GameOfMonopoly.InvalidMove {
		String name = inputString("Which property?");
		Location loc = game.getBoard().findLocation(name);
		if(loc == null) {
		    System.out.println("No such property!");
		    return;
		}
		game.sellProperty(player,loc);
		Property prop = (Property) loc; // safe
		System.out.println(prop.getName() + " sold to bank for $" + prop.getPrice());
		System.out.println(player.getName() + " now has $" + player.getBalance() + " remaining.");
	}

	private static void mortgageProperty(Player player, GameOfMonopoly game) throws GameOfMonopoly.InvalidMove {
		String name = inputString("Which property?");
		Location loc = game.getBoard().findLocation(name);
		if(loc == null) {
		    System.out.println("No such property!");
		    return;
		}
		int oldBalance = player.getBalance();
		game.mortgageProperty(player,loc);
		int newBalance = player.getBalance();
		Property prop = (Property) loc; // safe
		System.out.println(prop.getName() + " mortgaged for $" + (newBalance - oldBalance));
		System.out.println(player.getName() + " now has $" + player.getBalance() + " remaining.");
	}

	private static void unmortgageProperty(Player player, GameOfMonopoly game) throws GameOfMonopoly.InvalidMove {
		String name = inputString("Which property?");
		Location loc = game.getBoard().findLocation(name);
		if(loc == null) {
		    System.out.println("No such property!");
		    return;
		}
		int oldBalance = player.getBalance();
		game.unmortgageProperty(player,loc);
		int newBalance = player.getBalance();
		Property prop = (Property) loc; // safe
		System.out.println(prop.getName() + " unmortgaged for $" + (oldBalance - newBalance));
		System.out.println(player.getName() + " now has $" + player.getBalance() + " remaining.");
	}

	/**
	 * Print out details of properties owned by player
	 */
	private static void listProperties(Player player, GameOfMonopoly game) {
		System.out.println("Properties owned by " + player.getName());
		int c = 0;
		for (Property p : player) {
			System.out.print("* " + p.getName());
			if (p.isMortgaged()) {
				System.out.print(" (mortgaged)");
			}
			System.out.println("");
			c++;
		}
		if (c == 0) {
			System.out.println("* None.");
		}
	}

	/**
	 * Provide information on a specific location.
	 */
	private static void detailLocation(Player player, GameOfMonopoly game) {
		String name = inputString("Which location?");
		// check owned by player!
		Location loc = game.getBoard().findLocation(name);

		// check property OK to buy
		if (loc == null) {
			System.out.println("No such property!");
			return;
		}
		System.out.println("Information on " + loc.getName() + ":");
		if (loc instanceof Property) {
			Property prop = (Property) loc;
			System.out.println("* Price $" + prop.getPrice() + ".");
			if (prop.hasOwner()) {
				System.out.println("* Owned by " + prop.getOwner().getName()
						+ ".");
			} else {
				System.out.println("* Not Owned.");
			}
			if (prop.isMortgaged()) {
				System.out.println("* Mortgaged.");
			} else {
				System.out.println("* Unmortgaged.");
			}
			if (prop instanceof Street) {
				Street street = (Street) prop;
				System.out.println("* " + street.getHouses() + " houses "
						+ street.getHotels() + " and hotels.");
				System.out.println("* Colour "
						+ street.getColourGroup().getColour() + ".");
			}
		}
	}

	/**
	 * Provide player with set of things they can do. Lets them continue doing
	 * things for as long as they want.
	 */
	private static void playerOptions(Player player, GameOfMonopoly board) {
		System.out.println(player.getName() + " has $" + player.getBalance()
				+ " remaining.\n");
		System.out.println("Options for " + player.getName() + ":");
		System.out.println("* Buy, Sell, Mortgage, Unmortgage and Property");
		System.out.println("* Build House or Hotel");
		System.out.println("* List Owned Properties");
		System.out.println("* Get information on location");
		System.out.println("* End turn");
		while (1 == 1) {
			try {
			String cmd = inputString("[buy/sell/mortgage/unmortgage/list/info/end]");
			if (cmd.equals("end")) {
				return;
			} else if (cmd.equals("sell")) {
				sellProperty(player, board);
			} else if (cmd.equals("buy")) {
				buyProperty(player, board);
			} else if (cmd.equals("mortgage")) {
				mortgageProperty(player, board);
			} else if (cmd.equals("unmortgage")) {
				unmortgageProperty(player, board);
			} else if (cmd.equals("info")) {
				detailLocation(player, board);
			} else if (cmd.equals("list")) {
				listProperties(player, board);
			} else {
				System.out
						.println("Invalid command.  Enter 'end' to finish turn.");
			}
			} catch(GameOfMonopoly.InvalidMove e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void main(String args[]) {
		GameOfMonopoly game = new GameOfMonopoly();

		// Print banner ;)
		System.out.println("*** Monopoly Version 1.0 ***");
		System.out.println("By David J. Pearce");

		// input player info
		int nplayers = inputNumber("How many players?");
		ArrayList<Player> players = inputPlayers(nplayers,
				game.getBoard().getStartLocation());

		// now, begin the game!
		int turn = 1;
		Random dice = new Random();
		while (1 == 1) { // loop forever
			System.out.println("\n********************");
			System.out.println("***** TURN " + turn + " *******");
			System.out.println("********************\n");
			boolean firstTime = true;
			for (Player player : players) {
				if (!firstTime) {
					System.out.println("\n********************\n");
				}
				firstTime = false;
				int roll = dice.nextInt(10) + 2;
				System.out.println(player.getName() + " rolls a " + roll + ".");
				// now, move player and deduct rent
				movePlayer(player, roll, game);
				// now, give player options to sell/buy etc
				playerOptions(player, game);
				// now, is player still in game?
				if (player.getBalance() < 0) {
					// player is out of the game
					System.out.println("\n Game over for " + player.getName()
							+ " --- insufficient funds!");
					players.remove(player);
					break;
				}
			}
			turn++;
		}
	}
}
