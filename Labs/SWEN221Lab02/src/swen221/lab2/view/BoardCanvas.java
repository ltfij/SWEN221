package swen221.lab2.view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import swen221.lab2.model.Board;

public class BoardCanvas extends Canvas implements MouseListener {	
	
	private static int BOX_WIDTH = 10;
	private static int BOX_HEIGHT = 10;
	
	private static final Color[] colors = {
			new Color(  0,  0,  0), // 0
			new Color( 40, 40, 40), // 1
			new Color( 80, 80, 80), // 2
			new Color(100,100,100), // 3
			new Color(120,120,120), // 4
			new Color(140,140,140), // 5
			new Color(160,160,160), // 6
			new Color(180,180,180), // 7
			new Color(200,200,200), // 8
			new Color(255,255,255), // 9
	};
	private Board gameBoard;
	
	public BoardCanvas(Board board) {
		this.gameBoard = board;
		setBounds(0, 0, BOX_WIDTH*board.getWidth(), BOX_HEIGHT*board.getHeight());
		addMouseListener(this);
	}
	
	public void paint(Graphics g) {
		for(int x=0;x<gameBoard.getWidth();++x) {
			for(int y=0;y<gameBoard.getHeight();++y) {
				g.setColor(colors[gameBoard.getCellState(x, y)]);
				g.fillRect(x*BOX_WIDTH, y*BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
			}	
		}
	}
	
	private Image offscreen = null;
	
	public void update(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		if (offscreen == null || offscreen.getWidth(this) != width
				|| offscreen.getHeight(this) != height) {
			offscreen = createImage(width, height);
		}
		Image localOffscreen = offscreen;
		Graphics offgc = offscreen.getGraphics();		
		// do normal redraw
		paint(offgc);
		// transfer offscreen to window
		g.drawImage(localOffscreen, 0, 0, this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX() / 10;
		int y = e.getY() / 10;
		int state = gameBoard.getCellState(x, y);
		state = (state + 1) % 10;
		gameBoard.setCellState(x, y, state);
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}	
}
