package swen221.assignment1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import maze.*;

/**
 * An implementation of the left walker, which you need to complete according to
 * the specification given in the assignment handout.
 *
 */
public class LeftWalker extends Walker {

    // Which direction the walker is currently facing
    private Direction currentDirection;

    // Whether there is a wall on each direction
    private boolean hasLeftWall;
    private boolean hasRightWall;
    private boolean hasFrontWall;
    private boolean hasBackWall;

    // Is the walker trying to find a wall?
    private boolean isSearching;

    // The Coordinate that the walker is standing on
    private Coord currentCoord;

    // The memory of the walker. The key is a square that he visited, and the
    // Value is a list of directions that he hasn't explored from this square.
    private HashMap<Coord, List<Direction>> toBeExplored;

    /**
     * Constructor, initially the walker is facing north, and the current
     * coordinate is set to (0, 0).
     */
    public LeftWalker() {
        super("Left Walker");
        currentDirection = Direction.NORTH;
        isSearching = true;
        currentCoord = new Coord(0, 0);
        toBeExplored = new HashMap<>();
    }

    /**
     * Given the view from current square that the walker is standing on, this
     * method decides which direction the walker should take.
     *
     * @param view
     *            The view the walker has from the current position in the maze.
     * @return The direction the walker should take
     */
    protected Direction move(View view) {

        // Use the pause() command to slow the simulation down so you can see
        // what's happening...
        pause(2000);

        // Put all possible directions the walker can currently take into the
        // map.
        if (!toBeExplored.containsKey(currentCoord)) {
            List<Direction> possibleMove = determinePossibleDirections(view);
            toBeExplored.put(currentCoord, possibleMove);
        }

        // Update the status of four directions, i.e. whether there is a wall in
        // that direction
        updateWalls(view);

        if (isSearching) {
            // Wall-hunt mode. In this mode, the walker is trying to find a wall
            // instead of trying to follow a wall
            searchForLeftWall();
        } else {
            // Wall-following mode.
            followLeftWall();
        }

        // After the walker made his decision, he memorise his current position
        // along with which direction he decided to go.
        memorise();

        return currentDirection;
    }

    /**
     * Update the status of four directions.
     * 
     * @param view
     *            the view the walker has from the current position in the maze.
     */
    private void updateWalls(View view) {
        hasLeftWall = hasLeftWall(view, currentDirection);
        hasRightWall = hasRightWall(view, currentDirection);
        hasFrontWall = hasFrontWall(view, currentDirection);
        hasBackWall = hasBackWall(view, currentDirection);
    }

    /**
     * This method represents the process of the walker trying to find a wall.
     * If there is no wall on all four directions, he keep moving forward. If
     * any wall shows up, he turns to the direction that has this wall on the
     * left.
     */
    private void searchForLeftWall() {
        // If he has found any wall
        isSearching = !hasLeftWall && !hasFrontWall && !hasRightWall && !hasBackWall;

        if (hasLeftWall && !hasFrontWall) {
            // keep current direction, no need to change anything
        } else if (hasFrontWall && !hasRightWall) { // turn right
            currentDirection = rightDirection(currentDirection);
        } else if (hasRightWall && !hasBackWall) { // turn back
            currentDirection = reversedDirection(currentDirection);
        } else if (hasBackWall && !hasLeftWall) { // turn left
            currentDirection = leftDirection(currentDirection);
        }
    }

    /**
     * This method represents the process of the walker trying to follow a wall
     * on the left.
     */
    private void followLeftWall() {
        if (!hasLeftWall) { // turn left
            currentDirection = leftDirection(currentDirection);
        } else if (!hasFrontWall) {
            // go forward, keep current direction unchanged
        } else if (!hasRightWall) { // turn right
            currentDirection = rightDirection(currentDirection);
        } else if (!hasBackWall) { // turn back
            currentDirection = reversedDirection(currentDirection);
        }
    }

    /**
     * This method represents the process of memorising. The walker memorise his
     * current position, and tick off the direction he decides to go to. So next
     * time when he come back to this position, he only chooses from the rest of
     * directions, and avoids going into the old path.
     */
    private void memorise() {
        // what choices the walker has
        List<Direction> directions = toBeExplored.get(currentCoord);

        // if the direction he just chose is not one of the remaining choices
        if (!directions.contains(currentDirection) && !directions.isEmpty()) {
            // he choose the first of the remaining choices.
            currentDirection = toBeExplored.get(currentCoord).get(0);
            // and he needs to search for a wall again (even he has one on the
            // other side)
            isSearching = true;
        }

        // tick off the direction he is going to take
        toBeExplored.get(currentCoord).remove(currentDirection);

        // update the current coordinate
        currentCoord = this.currentCoord.go(currentDirection);
    }

    /**
     * Helper method, returns the direction on the left of given direction.
     * 
     * @param currentDirection
     *            the direction given.
     * @return the direction on the left of given direction.
     */
    private Direction leftDirection(Direction currentDirection) {
        Direction direction = null;
        if (currentDirection == Direction.NORTH) {
            direction = Direction.WEST;
        } else if (currentDirection == Direction.EAST) {
            direction = Direction.NORTH;
        } else if (currentDirection == Direction.SOUTH) {
            direction = Direction.EAST;
        } else if (currentDirection == Direction.WEST) {
            direction = Direction.SOUTH;
        }
        return direction;
    }

    /**
     * Helper method, returns the direction on the right of given direction.
     * 
     * @param currentDirection
     *            the direction given.
     * @return the direction on the right of given direction.
     */
    private Direction rightDirection(Direction currentDirection) {
        Direction direction = null;
        if (currentDirection == Direction.NORTH) {
            direction = Direction.EAST;
        } else if (currentDirection == Direction.EAST) {
            direction = Direction.SOUTH;
        } else if (currentDirection == Direction.SOUTH) {
            direction = Direction.WEST;
        } else if (currentDirection == Direction.WEST) {
            direction = Direction.NORTH;
        }
        return direction;
    }

    /**
     * Helper method, returns the direction on the opposite of given direction.
     * 
     * @param currentDirection
     *            the direction given.
     * @return the direction on the opposite of given direction.
     */
    private Direction reversedDirection(Direction currentDirection) {
        Direction direction = null;
        if (currentDirection == Direction.NORTH) {
            direction = Direction.SOUTH;
        } else if (currentDirection == Direction.EAST) {
            direction = Direction.WEST;
        } else if (currentDirection == Direction.SOUTH) {
            direction = Direction.NORTH;
        } else if (currentDirection == Direction.WEST) {
            direction = Direction.EAST;
        }
        return direction;
    }

    /**
     * Helper method. It deduces whether there is a wall on the left, given the
     * direction as the front.
     * 
     * @param v
     *            the view the walker has from the current position in the maze.
     * @param currentDirection
     *            the direction given as front
     * @return true if there is a wall on the left, or false if not.
     */
    private boolean hasLeftWall(View v, Direction currentDirection) {
        Direction left = leftDirection(currentDirection);
        return !v.mayMove(left);
    }

    /**
     * Helper method. It deduces whether there is a wall on the right, given the
     * direction as the front.
     * 
     * @param v
     *            the view the walker has from the current position in the maze.
     * @param currentDirection
     *            the direction given as front
     * @return true if there is a wall on the right, or false if not.
     */
    private boolean hasRightWall(View v, Direction currentDirection) {
        Direction right = rightDirection(currentDirection);
        return !v.mayMove(right);
    }

    /**
     * Helper method. It deduces whether there is a wall in front, given the
     * direction as the front.
     * 
     * @param v
     *            the view the walker has from the current position in the maze.
     * @param currentDirection
     *            the direction given as front
     * @return true if there is a wall in front, or false if not.
     */
    private boolean hasFrontWall(View v, Direction currentDirection) {
        return !v.mayMove(currentDirection);
    }

    /**
     * Helper method. It deduces whether there is a wall behind, given the
     * direction as the front.
     * 
     * @param v
     *            the view the walker has from the current position in the maze.
     * @param currentDirection
     *            the direction given as front
     * @return true if there is a wall behind, or false if not.
     */
    private boolean hasBackWall(View v, Direction currentDirection) {
        Direction back = reversedDirection(currentDirection);
        return !v.mayMove(back);
    }

    /**
     * Determine the list of possible directions. That is, the directions which
     * are not blocked by a wall.
     *
     * @param v
     *            The View object, with which we can determine which directions
     *            are possible.
     * @return
     */
    private List<Direction> determinePossibleDirections(View v) {
        Direction[] allDirections = Direction.values();
        ArrayList<Direction> possibleDirections = new ArrayList<Direction>();
        for (Direction d : allDirections) {
            if (v.mayMove(d)) {
                // Yes, this is a valid direction
                possibleDirections.add(d);
            }
        }
        return possibleDirections;
    }

    /**
     * An inner class representing the relative position of the walker. The
     * walker is initially set to (0, 0), i.e. in this relative coordinate
     * system, (0, 0) is the coordinate of the start position of maze. The x
     * coordinate increases from left to right, and the y coordinate increases
     * from up to down.
     * 
     * @author Hector
     */
    private class Coord {
        // coordinates
        private int x;
        private int y;
        
        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * This method gives a new coordinate object that is one square away
         * towards the given direction.
         * 
         * @param currentDirection
         *            the direction that the new coordinate object is relatively
         *            positioned towards
         * @return a new coordinate object
         */
        public Coord go(Direction currentDirection) {
            Coord coord = null;
            if (currentDirection == Direction.NORTH) {
                coord = new Coord(this.x, this.y - 1);
            } else if (currentDirection == Direction.EAST) {
                coord = new Coord(this.x + 1, this.y);
            } else if (currentDirection == Direction.SOUTH) {
                coord = new Coord(this.x, this.y + 1);
            } else if (currentDirection == Direction.WEST) {
                coord = new Coord(this.x - 1, this.y);
            }
            return coord;
        }

        @Override
        /**
         * Overridden method, in order to let the HashMap take two Coord objects
         * that have same x and y as the same key.
         */
        public int hashCode() {
            int primeX = 383;  // just two big prime number
            int primeY = 17;
            return primeX * this.x + primeY * this.y;
        }

        @Override
        /**
         * Overrided method, in order to let the HashMap take two Coord objects
         * that have same x and y as the same key.
         */
        public boolean equals(Object obj) {

            if (this == obj) {
                return true;
            }

            if (obj instanceof Coord) {
                Coord coord = (Coord) obj;
                boolean b = (coord.x == this.x && coord.y == this.y);
                return b;
            }

            return false;
        }

    }
}