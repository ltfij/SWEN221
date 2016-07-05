package swen221.lab2.model;

/**
 * Implements an abstract version of Conway's game of life where the rules for
 * birth's and deaths are customisable. The simulation operators over a given
 * board using a given set of rules. The simulation "executes" the game one step
 * at a time.
 * 
 * @author David J. Pearce
 *
 */
public class Simulation {
	private Board board;
	private Rule[] rules;

	/**
	 * Construct game simulation using a given board and rule set.
	 * 
	 * @param board
	 *            The starting board to use
	 * @param rules
	 *            The set of rules which are applied in the order given
	 */
	public Simulation(Board board, Rule... rules) {
		this.board = board;
		this.rules = rules;
	}
	
	public Board getBoard() {
		return board;
	}

	/**
	 * Take a single step of the simulation. This is done by walking through
	 * each cell and calculating its new state by applying the rules in their
	 * order until either one applies and yields a new state, or not apply
	 * (hence, the original state is retained).
	 */
	public void step() {
		int width = board.getWidth();
		int height = board.getHeight();
		// nCells holds the updated state for each cell on the board.
		int[] nCells = new int[width * height];
		// Now visit each cell and compute it's updated cell. Note that we don't
		// update the board yet. This is to prevent one cell from seeing the
		// updated state of another cell in the same step.
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				nCells[(y*width)+x] = board.getCellState(x, y);
				for (Rule r : rules) {
					// Apply the rule
					int result = r.apply(x, y, board);
					if (result >= 0 && result <= 9) {
						// Yes, the rule was successfully applied. Therefore,
						// update the cell's state and move onto the next cell.
						nCells[(y*width)+x] = result;
						break;
					}
				}
			}
		}
		// Finally, go through and update each cell on the board with its new
		// state.
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				int nState = nCells[(y * width) + x];
				board.setCellState(x, y, nState);
			}
		}
	}
	
	/**
	 * Take N steps through the simulation. This is done by performing N single
	 * steps.
	 * 
	 * @param n The number of steps to take
	 */
	public void step(int n) {
		for(int i=0;i<n;++i) {
			step();
		}
	}
	
}
