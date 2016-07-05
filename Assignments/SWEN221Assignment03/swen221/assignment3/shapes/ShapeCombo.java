/**
 * 
 */
package swen221.assignment3.shapes;

/**
 * This class represents a shape that comes from an operation of two shapes.
 * Notice that the two shapes as input could also be a result of other shapes.
 * The result of two is decided by three types of shape operators, which can
 * only be '+' for shape union, '-' for shape difference, or '&' for shape
 * intersection.<br>
 * <br>
 * PS: I didn't create one or more classes for shapeOperators. It's not necessary.
 * Instead, I used some <i>static final</i> char field to define these operators.
 * 
 * 
 * @author Hector
 *
 */
public class ShapeCombo implements Shape {
    
    // two input shapes
    private final Shape shapeA;
    private final Shape shapeB;
    // the shape operator
    private final char shapeOperator;

    // some constant char for three types of shape operators
    private static final char SHAPE_UNION = '+';
    private static final char SHAPE_DIFFERENCE = '-';
    private static final char SHAPE_INTERSECTION = '&';

    /**
     * Construct a shape that is the result of operation between two shapes. In
     * particular, the operation follows the pattern: <i>shapeA shapeOperator
     * shapeB</i>. Which one comes first matters.
     * 
     * @param shapeA
     * @param shapeB
     * @param shapeOperator
     */
    public ShapeCombo(Shape shapeA, Shape shapeB, char shapeOperator) {
        this.shapeA = shapeA;
        this.shapeB = shapeB;

        if (shapeOperator != SHAPE_UNION 
                && shapeOperator != SHAPE_DIFFERENCE 
                && shapeOperator != SHAPE_INTERSECTION) {
            throw new IllegalArgumentException("Unknown shape operator.");
        }

        this.shapeOperator = shapeOperator;
    }

    @Override
    public boolean contains(int x, int y) {
        if (shapeOperator == SHAPE_UNION) {
            return shapeA.contains(x, y) || shapeB.contains(x, y);
        } else if (shapeOperator == SHAPE_DIFFERENCE) {
            return shapeA.contains(x, y) && !shapeB.contains(x, y);
        } else {  // shapeOperator == SHAPE_INTERSECTION
            return shapeA.contains(x, y) && shapeB.contains(x, y);
        }
    }

    @Override
    public Rectangle boundingBox() {
        // the bounding boxes of two input shapes
        Rectangle boundingBoxA = shapeA.boundingBox();
        Rectangle boundingBoxB = shapeB.boundingBox();
        
        // check whether any or both of these two bounding box is null.
        if (boundingBoxA == null && boundingBoxB != null) {
            if (shapeOperator == SHAPE_UNION) {
                return boundingBoxB;
            } else {
                return null;
            }
        } else if (boundingBoxA != null && boundingBoxB == null) {
            if (shapeOperator == SHAPE_INTERSECTION) {
                return null;
            } else {
                return boundingBoxA;
            }
        } else if (boundingBoxA == null && boundingBoxB == null) {
            return null;
        }

        // four boundaries of shapeA
        int leftA = boundingBoxA.getX();
        int rightA = leftA + boundingBoxA.getWidth();
        int upA = boundingBoxA.getY();
        int downA = upA + boundingBoxA.getHeight();

        // four boundaries of shapeB
        int leftB = boundingBoxB.getX();
        int rightB = leftB + boundingBoxB.getWidth();
        int upB = boundingBoxB.getY();
        int downB = upB + boundingBoxB.getHeight();

        // left-most, right-most, up-most, and down-most boundary of both
        int leftMost = Math.min(leftA, leftB);
        int rightMost = Math.max(rightA, rightB);
        int upMost = Math.min(upA, upB);
        int downMost = Math.max(downA, downB);

        if (shapeOperator == SHAPE_UNION) {
            return new Rectangle(leftMost, upMost, rightMost - leftMost, downMost - upMost);
        } else if (shapeOperator == SHAPE_DIFFERENCE) {
            return boundingBoxA;
        } else {
            // if these two shapes have no intersection at all
            if (rightA < leftB || leftA > rightB || downA < upB || upA > downB) {
                return null;
            }

            // the boundary of intersection part
            int leftItstn = Math.max(leftA, leftB);
            int rightItstn = Math.min(rightA, rightB);
            int upItstn = Math.max(upA, upB);
            int downItstn = Math.min(downA, downB);
            return new Rectangle(leftItstn, upItstn, rightItstn - leftItstn, downItstn - upItstn);
        }
    }
}
