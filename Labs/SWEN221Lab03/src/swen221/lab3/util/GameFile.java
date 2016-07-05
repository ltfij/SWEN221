package swen221.lab3.util;

import java.util.*;

/**
 * Provides some utility classes for representing "game files" as stored on
 * disk. An example game file would be:
 * 
 * <pre>
 * Room { name: "Dining Room" }
 * Room { name: "Lounge" }
 * Room { name: "Kitchen" }
 * Door { from: 1, to: 2 }
 * Door { from: 2, to: 3 }
 * Candle { location: 1, description: "A candle" }
 * </pre>
 * 
 * Each line of the file corresponds to a GameFile.Item organised in the
 * following manner:
 *
 * <pre>
 * Kind { field: value, ... }
 * </pre>
 * 
 * Here, the Kind of object is either Room, Door, or Item. The ID is a unique
 * number assigned to each object. Finally, field is the name of a field for the
 * object in question, whilst value gives a value for that field (either a
 * string or integer)
 * 
 * @author David J. Pearce
 *
 */
public class GameFile {

	/**
	 * Parse a list of strings where each strings corresponds to a game file
	 * item.
	 * 
	 * @param items
	 * @return
	 */
	public static Item[] parseItems(String... items) {
		// First, parse all item strings into gamefile item objects
		Item[] gameItems = new Item[items.length];
		
		for(int i=0;i!=items.length;++i) {
			Parser p = new GameFile.Parser(items[i]);
			gameItems[i] = p.parseItem();
		}
		
		return gameItems;
	}
	
	/**
	 * A gamefile item corresponds to something which can exist in the game
	 * world. This includes rooms, doors, objects, and furniture.
	 * 
	 * @author David J. Pearce
	 * 
	 */
	public static class Item {
		private String name;
		private HashMap<String, Object> fields;

		/**
		 * Construct a Object.
		 * 
		 * @param name
		 *            --- kind of object (cannot be null)
		 * @param fields
		 *            --- fields of object (cannot be null)
		 */
		public Item(String name, Map<String, Object> fields) {
			this.name = name;
			this.fields = new HashMap<String, Object>(fields);
		}

		/**
		 * Return the kind of this object (e.g. Door, Item, Room, etc). This
		 * method cannot return null.
		 * 
		 * @return
		 */
		public String name() {
			return name;
		}

		/**
		 * Return the value held in the given field of this object. This method
		 * may return null, if the field in question does not exist.
		 * 
		 * @param field
		 * @return
		 */
		public Object field(String field) {
			return fields.get(field);
		}

		/**
		 * Generate a string representing the object
		 */
		public String toString() {
			return name + " " + fields;
		}
	}

	/**
	 * The parser class is responsible for parsing game file items in a textual
	 * format and constructing a game world object. If the game file item is not
	 * syntactically valid, then an exception is thrown.
	 * 
	 * @author David J. Pearce
	 * 
	 */
	public static class Parser {
		private String text; // text to be processed
		private int index; // position into text

		public Parser(String text) {
			this.text = text;
			this.index = 0;
		}

		/**
		 * The following method parses a file object. An object is string of the
		 * form: "name id { field: value [, field:value]* }".
		 * 
		 * @return
		 * @throws SyntaxError
		 */
		public Item parseItem() {
			String kind = parseName();

			HashMap<String, Object> fields = new HashMap<String, Object>();

			match('{');

			boolean firstTime = true;
			while (index < text.length() && text.charAt(index) != '}') {
				if (!firstTime) {
					match(',');
				}
				firstTime = false;

				// now, parse the field itself
				String name = parseName();
				match(':');
				Object value = parseValue();
				fields.put(name, value);
				skipWhiteSpace();
			}

			match('}');

			return new Item(kind, fields);
		}

		/**
		 * This method parses a value, which is either a string literal (e.g.
		 * "abc"), an integer (e.g. 123) or a boolean (e.g. true or false).
		 * 
		 * @return
		 */
		private Object parseValue() {
			skipWhiteSpace();

			if (index >= text.length()) {
				syntaxError("unexpected end-of-file");
			}

			char lookahead = text.charAt(index);
			if (lookahead == '\"') {
				return parseString();
			} else if (Character.isDigit(lookahead)) {
				return parseNumber();
			} else {
				syntaxError("unexpected character");
				return null;
			}
		}

		/**
		 * This method parse a string literal, which is a sequence of characters
		 * contained with quotes. Examples include: "abc", "a12", "123",
		 * "asdasd"
		 * 
		 * @return
		 * @throws SyntaxError
		 */
		private String parseString() {
			match('\"');
			int start = index;
			while (index < text.length() && text.charAt(index) != '\"') {
				index++;
			}
			String n = text.substring(start, index);
			match('\"');
			return n;
		}

		/**
		 * This method parses an integer from a string consisting of one or more
		 * digits.
		 * 
		 * @return
		 */
		private int parseNumber() {
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
		 * This method parses a name, such as "room" or "object". A name is made
		 * up of one or more characters from the alphabet (e.g.
		 * 'a','b','c',...,'A','B','C').
		 * 
		 * @return
		 */
		private String parseName() {
			// first, skip any whitespace at the current position.
			skipWhiteSpace();

			// second, determine all letters which make up the name
			int start = index;
			while (index < text.length() && Character.isLetter(text.charAt(index))) {
				index = index + 1;
			}

			// third, check we have found at least one letter
			if (index == start) {
				syntaxError("expecting a name");
			}

			// finally, extract the letters from the text string and return them
			// as
			// a string.
			return text.substring(start, index);

		}
		
		/**
		 * This method checks whether the given character is at the current
		 * position in the text. The method will first skip any whitespace at
		 * this point.
		 * 
		 * @param text
		 */
		private void match(char c) {
			skipWhiteSpace();
			if (index < text.length() && text.charAt(index) == c) {
				index = index + 1;
			} else if (index < text.length()) {
				syntaxError("expecting '" + c + "', found '" + text.charAt(index) + "'");
			} else {
				syntaxError("unexpected end-of-file");
			}
		}

		/**
		 * Move the current position past any "white space" characters. White
		 * space characters include spaces (i.e. ' '), tabs (i.e. '\t') and
		 * newlines (i.e. '\n').
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
		private void syntaxError(String msg) {
			ArrayList<String> lines = new ArrayList<String>();
			int i = 0;
			int start = 0;
			int linenum = 0;
			int col = 0;
			while (i < text.length()) {
				if (text.charAt(i) == '\n') {
					String line = text.substring(start, i);
					lines.add(line);
					start = i + 1;
				}
				if (i == index) {
					linenum = lines.size();
					col = i - start;
				}
				i = i + 1;
			}
			// finally, process last line
			String line = text.substring(start, i);
			lines.add(line);
			System.err.println(msg);
			System.err.println(lines.get(linenum));
			for (i = 0; i != col; ++i) {
				System.err.print(" ");
			}
			System.err.println("^\n");
			throw new RuntimeException(msg);
		}
	}
}
