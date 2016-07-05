package swen221.assignment3.shapes;

import java.util.*;

/**
 * Responsible for interpreting a shape program. The program is represented as a
 * string, through which the interpreter moves. For example, consider this shape
 * program:
 * 
 * <pre>
 * x =[0,0,10,10]
 * fill x #000000
 * </pre>
 * 
 * This program will be represented in the input string as follows:
 * 
 * <pre>
 * --------------------------------------------------------------
 * | x |   | = | [ | 0 | , | 0 | , | 1 | 0 | , | 1 | 0 | ] | \n |
 * --------------------------------------------------------------
 *   0   1   2   3   4   5   6   7   8   9   10  11  12  13  14
 * 
 * (continued)
 * --------------------------------------------------------------
 * | f | i | l | l |   | x |   | # | 0 | 0 | 0 | 0 | 0 | 0 | \n |
 * --------------------------------------------------------------
 *   14  15  16  17  18  19  20  21  22  23  24  25  26  27  38
 * </pre>
 * 
 * The interpreter starts at index 0 and attempts to decide what kind of command
 * we have. If the first four characters are "fill" then it's a fill command. If
 * the first four characters are "draw" then it's a draw command. Otherwise, it
 * must be an assignment command.
 * 
 * @author David J. Pearce
 *
 */
public class Interpreter {
    /**
     * The input program being interpreted by this class
     */
    private String input;

    /**
     * The current position within the input program that this interpreter has
     * reached.
     */
    private int index; // current position within the input string.

    /**
     * A mapping from variables to their shape value. When a variable is
     * assigned a new shape value, then this map is updated accordingly.
     */
    private HashMap<String, Shape> environment = new HashMap<>();

    /**
     * Construct an interpreter from a given input string representing a simple
     * shape program.
     * 
     * @param input
     *            ---- the string that need to be interpreted
     */
    public Interpreter(String input) {
        this.input = input;
        this.index = 0;
    }

    /**
     * This method creates an empty canvas onto which it evaluates each command
     * of the program in turn. The canvas is then returned.
     * 
     * @return ---- a canvas that shows the result of the input.
     */
    public Canvas run() {
        Canvas canvas = new Canvas();
        while (index < input.length()) {
            evaluateNextCommand(canvas);
        }
        return canvas;
    }

    /**
     * Evaluate the next command in the program. To do this, the interpreter
     * must first decide what kind of command it is. This is done by looking at
     * the first word of the input string at the current position.
     * 
     * @param canvas
     *            ----a canvas to be painted
     */
    private void evaluateNextCommand(Canvas canvas) {
        skipWhiteSpace();
        String cmd = readNext();
        if (cmd.equals("fill")) {
            // in this case, we are going to fill a shape
            Shape shape = evaluateShapeExpression();
            Color color = readColor();
            fillShape(color, shape, canvas);
        } else if (cmd.equals("draw")) {
            // in this case, we are going to draw a shape
            Shape shape = evaluateShapeExpression();
            Color color = readColor();
            drawShape(color, shape, canvas);
        } else if (!cmd.equals("")) {
            // in this case, we are going to assign a variable
            match("=");
            Shape rhs = evaluateShapeExpression();
            environment.put(cmd, rhs);
        }
        skipWhiteSpace();
    }

    /**
     * Evaluate a shape expression which is expected at the current position
     * within the input string. This is done by first looking at the current
     * character in the input string. If this is a '(', for example, then it
     * signals the start of a bracketed expression.
     * 
     * @return ---- a shape constructed from the evaluated result
     */
    private Shape evaluateShapeExpression() {
        skipWhiteSpace();
        char lookahead = input.charAt(index);
        Shape value = null;

        if (lookahead == '(') {
            // in this case, we have a bracketed sub-expression
            value = evaluateBracketedExpression();
        } else if (lookahead == '[') {
            // in this case, we have a rectangle expression
            value = evaluateRectangleExpression();
        } else if (Character.isLetter(lookahead)) {
            // in this case, we have an identifier
            value = evaluateVariableExpression();
        } else {
            error("unknown operator");
        }

        skipWhiteSpace();
        
        // to prevent index out of boundary
        if (index < input.length()) {
            lookahead = input.charAt(index);
            
            if (lookahead == '+' || lookahead == '-' || lookahead == '&') {
                // in this case we have a shape operation
                char so = readOperator(lookahead);
                Shape valueCopy = value; // just a copy of value
                Shape another = evaluateShapeExpression(); // another shape after
                // the shape operator
                value = new ShapeCombo(valueCopy, another, so);
            }
        }

        return value;
    }

    /**
     * Evaluate a bracketed shape expression. That is a shape expression which
     * is surrounded by braces.
     * 
     * @return ---- a shape constructed from the evaluated result
     */
    private Shape evaluateBracketedExpression() {
        /*
         * This method is essentially another evaluateShapeExpression() method.
         * It only matches the bracket before and after.
         */
        match("(");
        Shape value = evaluateShapeExpression();
        match(")");
        return value;
    }

    /**
     * Evaluate a rectangle expression. That is, four numbers separated by
     * comma's and '[', ']'.
     * 
     * @return ---- a shape constructed from the evaluated result
     */
    private Shape evaluateRectangleExpression() {
        match("[");
        int x = readNumber();
        match(",");
        int y = readNumber();
        match(",");
        int width = readNumber();
        match(",");
        int height = readNumber();
        match("]");
        Rectangle rectangle = new Rectangle(x, y, width, height);
        return rectangle;
    }

    /**
     * Evaluate a variable expression which is expected at the current input
     * position. A variable is a sequence of one or more digits or letters, of
     * which the first character must be a letter. Having determined the
     * variable name, its current value is then looked up in the environment.
     * 
     * @return ---- a shape constructed from the evaluated result
     */
    private Shape evaluateVariableExpression() {
        int start = index;
        String var = readNext();
        Shape s = environment.get(var);
        if (s == null) {
            index = start; // to get proper output
            error("undefined variable");
        }
        return s;
    }

    /**
     * Read a "word" from the input string. This is defined as a sequence of one
     * or more consecutive letters. Digits and other characters (e.g. '_' or
     * '+') are not permitted as part of a word.
     * 
     * @return
     */
    @SuppressWarnings("unused")
    private String readWord() {
        int start = index;
        // Advance through the input string from the current position whilst the
        // character at that position is a letter.
        while (index < input.length() && Character.isLetter(input.charAt(index))) {
            index++;
        }
        return input.substring(start, index);
    }

    /**
     * Read a number which is expected at the current input position. A number
     * is defined as a sequence of one or more digits.
     * 
     * @return ---- the int number parsed from current input position
     */
    private int readNumber() {
        skipWhiteSpace();
        int start = index;
        while (index < input.length() && Character.isDigit(input.charAt(index))) {
            index = index + 1;
        }
        return Integer.parseInt(input.substring(start, index));
    }

    /**
     * Read a token which is expected at the current input position. A token is
     * a sequence of one or more digits or letters, of which the first character
     * must be a letter. The returning String could be either a variable name or
     * a "draw" or "fill" command.
     * 
     * @return ---- a String of token that is parsed from current position
     */
    private String readNext() {
        int start = index;
        // A variable must not start with non-letter characters.
        if (!Character.isLetter(input.charAt(start))) {
            error("Illegal variable initials.");
        }

        while (index < input.length()
                && (Character.isLetter(input.charAt(index)) || Character.isDigit(input.charAt(index)))) {
            index++;
        }

        return input.substring(start, index);
    }

    /**
     * Read a shape operator which is expected at the current input position. A
     * shape operator can only be '+' for shape union, '-' for shape difference,
     * or '&' for shape intersection. This method returns the passed argument
     * char unchanged, so essentially it only advances the index by one.
     * 
     * @param chr
     *            --- the character that need to be parsed
     * @return --- the passed argument
     */
    private char readOperator(char chr) {
        /*
         * In this assignment, this error would never happen. This "if" is only
         * for a robust functional method
         */
        if (chr != '+' && chr != '-' && chr != '&') {
            error("Unknown shape operator.");
        }

        index++;
        return chr;
    }

    /**
     * Read a color which is expected at the current input position. A color is
     * a string of 7 characters, of which the first is a '#' and the remainder
     * are digits or letters.
     * 
     * @return ---- a color object parsed from current input position
     */
    private Color readColor() {
        skipWhiteSpace();
        if ((index + 7) > input.length()) {
            error("expecting color");
        }
        String str = input.substring(index, index + 7);
        index += 7;
        return new Color(str);
    }

    /**
     * Match a string of text which is expected at the current input position.
     * If the match fails, then an error is produced.
     * 
     * @param text
     *            ---- the string that need to be matched
     */
    private void match(String text) {
        skipWhiteSpace();
        if (input.startsWith(text, index)) {
            index += text.length();
        } else {
            error("expecting: " + text);
        }
    }

    /**
     * Skip over any "whitespace" at the current input position. That is, any
     * sequence of zero or more space or newline characters.
     */
    private void skipWhiteSpace() {
        while (index < input.length() && (input.charAt(index) == ' ' || input.charAt(index) == '\n')) {
            index = index + 1;
        }
    }

    /**
     * This method fills a given shape in a given colour onto the canvas.
     * 
     * @param color
     *            ---- the colour of the filled shape
     * @param shape
     *            ---- the shape to be filled
     * @param canvas
     *            ---- where to paint
     */
    private void fillShape(Color color, Shape shape, Canvas canvas) {
        Rectangle boundingBox = shape.boundingBox();
        /*
         * To prevent any non-positive width or non-positive height bounding box
         * which could be generated by an intersection operation.
         */
        if (boundingBox == null) {
            return;
        }

        // get four boundaries
        int left = boundingBox.getX();
        int right = left + boundingBox.getWidth();
        int up = boundingBox.getY();
        int down = up + boundingBox.getHeight();

        // fill the shape
        for (int x = left; x < right; x++) {
            for (int y = up; y < down; y++) {
                if (shape.contains(x, y)) {
                    canvas.draw(x, y, color);
                }
            }
        }
    }

    /**
     * This method draws a given shape in a given colour onto the canvas.
     * 
     * @param color
     *            ---- the colour of the drawn shape
     * @param shape
     *            ---- the shape to be drawn
     * @param canvas
     *            ---- where to paint
     */
    private void drawShape(Color color, Shape shape, Canvas canvas) {
        Rectangle boundingBox = shape.boundingBox();
        /*
         * To prevent any non-positive width or non-positive height bounding box
         * which could be generated by an intersection operation.
         */
        if (boundingBox == null) {
            return;
        }

        // four boundaries
        int left = boundingBox.getX();
        int right = left + boundingBox.getWidth();
        int up = boundingBox.getY();
        int down = up + boundingBox.getHeight();

        /*
         * two flags indicates: was last pixel inside the shape, and is current
         * pixel inside the shape
         */
        boolean wasInside;
        boolean isInside;

        // scan horizontally
        for (int y = up; y < down; y++) {

            // need to deal with the left-most pixel
            int x = left;
            if (shape.contains(x, y) && shape.contains(x + 1, y)) {
                canvas.draw(x, y, color);
            }

            // deal with pixels in the middle
            for (x = left + 1; x < right; x++) {
                wasInside = shape.contains(x - 1, y);
                isInside = shape.contains(x, y);
                if (!wasInside && isInside) {
                    // enter the shape
                    canvas.draw(x, y, color);
                } else if (wasInside && !isInside) {
                    // exit the shape
                    canvas.draw(x - 1, y, color);
                }
            }

            // need to deal with the right-most pixel, here x = right
            x--;
            if (shape.contains(x - 1, y) && shape.contains(x, y)) {
                canvas.draw(x, y, color);
            }
        }

        // scan vertically
        for (int x = left; x < right; x++) {

            // need to deal with the up-most pixel
            int y = up;
            if (shape.contains(x, y) && shape.contains(x, y + 1)) {
                canvas.draw(x, y, color);
            }

            // deal with pixels in the middle
            for (y = up + 1; y < down; y++) {
                wasInside = shape.contains(x, y - 1);
                isInside = shape.contains(x, y);
                if (!wasInside && isInside) {
                    // enter the shape
                    canvas.draw(x, y, color);
                } else if (wasInside && !isInside) {
                    // exit the shape
                    canvas.draw(x, y - 1, color);
                }
            }

            // need to deal with the down-most pixel, here y = down
            y--;
            if (shape.contains(x, y - 1) && shape.contains(x, y)) {
                canvas.draw(x, y, color);
            }
        }
    }

    /**
     * Report an error
     * 
     * @param error
     */
    private void error(String error) {
        String msg = error + "\n" + input + "\n";
        for (int i = 0; i < index; ++i) {
            msg += " ";
        }
        msg += "^";
        throw new IllegalArgumentException(msg);
    }
}
