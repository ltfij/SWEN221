package sokoban.io;

import java.util.*;

import sokoban.Game;
import sokoban.moves.*;

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
		// Secondly, parse the width and height of the board
		int width = parseNumber();
		match(',');
		int height = parseNumber();
		// Third, parse each move!
		for (int i = 0; i != numberOfMoves; ++i) {
			Move move = parseMove();
			moves.add(move);
		}

		Move[] arrayOfMoves = moves.toArray(new Move[moves.size()]);

		return new Game(width,height,arrayOfMoves);
	}

	/**
	 * This method parses a given move in the game, and constructs an object of
	 * the appropriate type to represent that move.
	 *
	 * @return
	 * @throws GameError
	 */
	private Move parseMove() throws GameError {
		skipWhiteSpace();
		char moveCharacter = text.charAt(index++);
		if(moveCharacter == 'F') {
			return new FinishingMove();
		} else {
			match('(');
			Game.Direction direction = parseDirection();
			match(';');
			int numberOfSteps = parseNumber();
			match(')');

			switch (moveCharacter) {
			case 'W':
				return new WalkingMove(direction,numberOfSteps);
			case 'P':
				return new PushingMove(direction,numberOfSteps);
			case 'L':
				return new PullingMove(direction,numberOfSteps);

			default:
				syntaxError("unknown move character encountered: " + moveCharacter);
				return null; // unreachable
			}
		}
	}

	/**
	 * Parse a direction (e.g. N,S,E,W)
	 *
	 * @return
	 * @throws GameError
	 */
	private Game.Direction parseDirection() throws GameError {
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
		return direction;
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
