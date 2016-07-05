package cathedral.io;

import java.util.*;

import cathedral.Area;
import cathedral.Game;
import cathedral.moves.*;
import cathedral.pieces.*;
import cathedral.util.Position;
import cathedral.util.Rectangle;

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
			if(i == 0) {
				// The very first move is played by the WHITE player, where
				// he/she places the cathedral.
				player = null;
			} else if (i % 2 == 0) {
				player = Game.Player.WHITE;
			} else {
				player = Game.Player.BLACK;
			}
			Move move = parseMove(player);
			if(i == 0 && move instanceof PlaceBuilding) {
				PlaceBuilding pb = (PlaceBuilding) move;
				if(!pb.getTemplate().getName().equals("Cathedral")) {
					syntaxError("Cathedral must be placed first");
				}
			} else if(i == 0){
				syntaxError("Cathedral must be placed first");
			}
			moves.add(move);
		}

		Move[] arrayOfMoves = moves.toArray(new Move[moves.size()]);

		return new Game(10, 10, arrayOfMoves);
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
		char moveCharacter = text.charAt(index);		
		
		Move move;
		
		switch(moveCharacter) {
			case 'P':
				match('P');
				move = parsePlaceBuildingMove(player,false);				
				break;
			case 'A':
				match('A');
				move = parsePlaceBuildingMove(player,true);
				break;
			default:
				syntaxError("unknown move character encountered: " + moveCharacter);
				return null; // unreachable
		}
						
		return move;
	}
	
	/**
	 * Parse a place building move, e.g. "P(1,2:T)"
	 * 
	 * @return
	 * @throws GameError
	 */
	private Move parsePlaceBuildingMove(Game.Player player ,boolean isCapture) throws GameError {				
		
		match('(');
		
		Position origin = parsePosition();
		match(';');
		char directionCharacter = text.charAt(index);
		Game.Direction direction;
		
		switch (directionCharacter) {
		case 'N':
			match('N');
			direction = Game.Direction.NORTH;
			break;
		case 'S':
			match('S');
			direction = Game.Direction.SOUTH;
			break;
		case 'E':
			match('E');
			direction = Game.Direction.EAST;
			break;
		case 'W':
			match('W');
			direction = Game.Direction.WEST;
			break;
		default:
			syntaxError("unknown building character encountered");
			return null; // unreachable
		}
		
		match(';');
		char pieceCharacter = text.charAt(index);
		
		BuildingTemplate template;
		
		switch (pieceCharacter) {
		case 'S':
			match('S');
			template = new S_Building();
			break;
		case 'L':
			match('L');
			template = new L_Building();
			break;
		case 'T':
			match('T');
			template = new T_Building();
			break;			
		case 'C': 
			match('C');
			template = new Cathedral();
			break;
		default:
			syntaxError("unknown building character encountered");
			return null; // unreachable
		}
		
		if(isCapture) {
			Area area = parseCapturedArea();
			match(')');
			return new CaptureArea(player, origin, direction, template, area);
		} else {		
			match(')');
			return new PlaceBuilding(player, origin, direction, template);
		}
	}

	private Area parseCapturedArea() throws GameError {
		ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
		do {
			match(';');
			Position start = parsePosition();
			match('-');
			Position end = parsePosition();
			rectangles.add(new Rectangle(start, end));
		} while (text.charAt(index) == ';');

		Rectangle[] arrayOfRectangles = rectangles
				.toArray(new Rectangle[rectangles.size()]);

		return new Area(arrayOfRectangles);
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
