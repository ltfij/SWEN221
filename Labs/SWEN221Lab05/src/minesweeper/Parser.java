package minesweeper;

import java.util.*;
import minesweeper.*;
import minesweeper.moves.*;

public class Parser {
	private String text; // text to be processed
	private int index;  // position into text
	
//	private int width;
//	private int height;
	
	public Parser(String text) {
		this.text = text;
		this.index = 0;
	}


	public Game parse() throws SyntaxError {
		
		// First, get the width and height
		int width = parseNumber();
		match(',');
		int height = parseNumber();
		
		// Second, get the position of all bombs
		int numberOfBombs = parseNumber();
		Position[] bombs = new Position[numberOfBombs];
		for(int i=0;i!=numberOfBombs;++i) {
			bombs[i] = parsePosition(width,height);
		}
		
		// Third, get all moves and create game.
		int numberOfMoves = parseNumber();
		Move[] moves = new Move[numberOfMoves];
		
		for(int i=0;i!=numberOfMoves;++i) {			
			moves[i]= parseMove(width,height);			
		}
		
		Game game = new Game(width,height,moves);
		
		// Finally, place all bombs and initialise game.
		for(Position p : bombs) {
			game.placeBomb(p);
		}
		
		game.initialise();
		
		// Done
		return game;
	}
	
	/**
	 * Parse a position which is a string of the form "(x,y)" where x and y are
	 * integers giving the x and y components.
	 * 
	 * @return
	 * @throws SyntaxError
	 */
	private Position parsePosition(int width, int height) throws SyntaxError {
				
		match('('); // problem?
		int x = parseNumber();						
		match(',');
		int y = parseNumber();		
		match(')');
		
		if (x < 0 || x >= width || y < 0 || y >= height) {
		    throw new SyntaxError("Invalid sheet");
		}
		
		return new Position(x,y);
	}
	
	/**
	 * Parse a move which consists of special character indicating what kind of
	 * move, followed by a position string. The move characters are: 'E' for
	 * expose move; 'F' for a flag move; 'U' for an unflag move; 'W' for a
	 * winning move; 'L' for a losing move which exposes a bomb.
	 * 
	 * @return
	 * @throws SyntaxError
	 */
	private Move parseMove(int width, int height) throws SyntaxError {
		skipWhiteSpace();
		
		char moveCharacter = text.charAt(index++);		
		Position p = parsePosition(width,height);
		
		switch(moveCharacter) {
			case 'E':
				return new ExposeMove(p);
			case 'F':
				return new FlagMove(p,true);
			case 'U':
				return new FlagMove(p,false);
			case 'W':
				return new EndMove(p,true);
			case 'L':
				return new EndMove(p,false);
		}
		
		// if we get here, then an error has occurred.
		syntaxError("invalid move character: " + moveCharacter);		
		return null;
	}

	/**
	 * This method parses an integer from a string consisting of one or more
	 * digits.
	 * 
	 * @return
	 */
	private int parseNumber() throws SyntaxError {
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
	private void match(char c) throws SyntaxError {
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
	private void syntaxError(String msg) throws SyntaxError {
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
		throw new SyntaxError(msg);
	}
}
