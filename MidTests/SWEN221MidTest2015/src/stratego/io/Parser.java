package stratego.io;

import java.util.*;

import stratego.Game;
import stratego.moves.*;
import stratego.pieces.*;
import stratego.util.Position;

public class Parser {
	private String text; // text to be processed
	private int index;  // position into text
	
	public Parser(String text) {
		this.text = text;
		this.index = 0;
	}

	public Game parse() throws GameError {
		ArrayList<Move> moves = new ArrayList<Move>();

		// Firstly, parse the number of moves
		int numberOfMoves = parseNumber();

		// Secondly, parse each move!
		for (int i = 0; i != numberOfMoves; ++i) {
			Game.Player player;
			if (i % 2 == 0) {
				player = Game.Player.RED;
			} else {
				player = Game.Player.BLUE;
			}
			Move move = parseMove(player);			
			moves.add(move);
		}

		Move[] arrayOfMoves = moves.toArray(new Move[moves.size()]);

		return new Game(arrayOfMoves);
	}
	
	/**
	 * This method parses a given move in the game, and constructs an object of
	 * the appropriate type to represent that move.
	 * 
	 * @return
	 * @throws GameError
	 */
	private Move parseMove(Game.Player player) throws GameError {
		skipWhiteSpace();
		char moveCharacter = text.charAt(index++);
		match('('); 
		Move move;

		switch (moveCharacter) {
		case 'M':
			move = parseBasicMove(player);
			break;
		case 'W':
			move = parseStrikeMove(player, StrikeMove.Outcome.WIN);
			break;
		case 'L':
			move = parseStrikeMove(player, StrikeMove.Outcome.LOSE);
			break;
		case 'D':
			move = parseStrikeMove(player, StrikeMove.Outcome.DRAW);
			break;
		default:
			syntaxError("unknown move character encountered: " + moveCharacter);
			return null; // unreachable
		}

		match(')');

		return move;
	}
	
	/**
	 * Parse a straighforward move, e.g. "M(1,2;N;1)"
	 * 
	 * @return
	 * @throws GameError
	 */
	private Move parseBasicMove(Game.Player player) throws GameError {
		Position origin = parsePosition();
		match(';');
		char directionCharacter = text.charAt(index);
		Game.Direction direction;
		
		switch (directionCharacter) {
		case 'N':
			direction = Game.Direction.NORTH;
			break;
		case 'S':
			direction = Game.Direction.SOUTH;
			break;
		case 'E':
			direction = Game.Direction.EAST;
			break;
		case 'W': 
			direction = Game.Direction.WEST;
			break;
		default:
			syntaxError("unknown building character encountered");
			return null; // unreachable
		}
		index = index + 1;
		match(';');
		int distance = parseNumber();		
		return new BasicMove(player, origin, direction, distance);
	}
	
	/**
	 * Parse a striking move, e.g. "T(1,2;N)"
	 * 
	 * @return
	 * @throws GameError
	 */
	private Move parseStrikeMove(Game.Player player, StrikeMove.Outcome outcome)
			throws GameError {
		Position origin = parsePosition();
		match(';');
		char directionCharacter = text.charAt(index);
		Game.Direction direction;

		switch (directionCharacter) {
		case 'N':
			direction = Game.Direction.NORTH;
			break;
		case 'S':
			direction = Game.Direction.SOUTH;
			break;
		case 'E':
			direction = Game.Direction.EAST;
			break;
		case 'W':
			direction = Game.Direction.WEST;
			break;
		default:
			syntaxError("unknown building character encountered");
			return null; // unreachable
		}
		index = index + 1;
		match(';');
		int distance = parseNumber();

		return new StrikeMove(player, origin, direction, distance, outcome);
	}
	
	/**
	 * Parse a position which is a string of the form "x,y" where x and y are
	 * integers giving the x and y components.
	 * 
	 * @return
	 * @throws GameError
	 */
	private Position parsePosition() throws GameError {
				
		int x = parseNumber();						
		match(',');
		int y = parseNumber();		
		
		return new Position(x,y);
	}
	
	/**
	 * This method parses an integer from a string consisting of one or more
	 * digits.
	 * 
	 * @return
	 */
	private int parseNumber() throws GameError {
		skipWhiteSpace();
		// first, determine all digits which make up the number
		int start = index;
		while (index < text.length() && Character.isDigit(text.charAt(index))) {
			index = index + 1;
		}

		// second, check that we found at least one digit
		if (index == start) {
			syntaxError("expecting a number");
		}

		// finally, convert the string into an actual integer.
		return Integer.parseInt(text.substring(start, index));
	}

	/**
	 * This method checks whether the given character is at the current position
	 * in the text. The method will first skip any whitespace at this point.
	 * 
	 * @param text
	 */
	private void match(char c) throws GameError {
		skipWhiteSpace();
		if (index < text.length() && text.charAt(index) == c) {
			index = index + 1;
		} else if (index < text.length()) {
			syntaxError("expecting '" + c + "', found '" + text.charAt(index)
					+ "'");
		} else {
			syntaxError("unexpected end-of-file");
		}
	}
	
	/**
	 * Move the current position past any "white space" characters. White space
	 * characters include spaces (i.e. ' '), tabs (i.e. '\t') and newlines (i.e.
	 * '\n').
	 */
	private void skipWhiteSpace() {
		while (index < text.length() && isWhitespace(text.charAt(index))) {
			index = index + 1;
		}
	}

	/**
	 * Check whether a given character is whitespace or not.
	 * 
	 * @param c
	 *            --- the character to test.
	 * @return
	 */
	private boolean isWhitespace(char c) {
		return c == ' ' || c == '\t' || c == '\n';
	}

	/**
	 * Print out useful debugging output, and throw a SyntaxError exception
	 * 
	 * @param msg
	 */
	private void syntaxError(String msg) throws GameError {
		ArrayList<String> lines = new ArrayList<String>();
		int i = 0;
		int start = 0;
		int linenum = 0;
		int col = 0;
		while(i < text.length()) {			
			if(text.charAt(i) == '\n') {
				String line = text.substring(start,i);
				lines.add(line);
				start = i+1;
			}
			if(i == index) { 
				linenum = lines.size();
				col = i - start;
			}
			i = i + 1;
		}
		// finally, process last line
		String line = text.substring(start,i);
		lines.add(line);
		System.err.println(msg);
		System.err.println(lines.get(linenum));					
		for(i=0;i<col;++i) {
			System.err.print(" ");
		}
		System.err.println("^\n");
		throw new GameError(msg);
	}
}
