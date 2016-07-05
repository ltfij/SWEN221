package swen221.lab6;

import swen221.lab6.connect.Game;
import swen221.lab6.connect.core.Board;
import swen221.lab6.view.GraphicalUserInterface;

public class Main {
	@SuppressWarnings("unused")
    public static void main(String[] args) {
		Board board = new Board();
		Game game = new Game(board);
		GraphicalUserInterface gui = new GraphicalUserInterface(game);
	}
}
