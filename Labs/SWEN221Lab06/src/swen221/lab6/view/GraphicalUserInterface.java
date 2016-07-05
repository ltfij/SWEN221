package swen221.lab6.view;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

import swen221.lab6.connect.Game;
import swen221.lab6.connect.core.Board;
import swen221.lab6.connect.util.Position;

/**
 * Provides a simple Graphical User Interface for the Connect Game.
 * 
 * @author David J. Pearce
 *
 */
@SuppressWarnings("serial")
public class GraphicalUserInterface extends JFrame {

	private static final int BOX_WIDTH = 100;
	private static final int BOX_HEIGHT = 100;
	private static final Color BACKGROUND = new Color(200,200,200);
	private static final Color BORDER = new Color(50,50,50);
	//
	private final MainDisplay mainDisplay;

	private final Game game;

	public GraphicalUserInterface(Game game) {
		super("Connect");

		this.game = game;	
		this.mainDisplay = new MainDisplay();
		
		add(createCenterPanel(),BorderLayout.CENTER);				
		setFocusable(true);		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		pack();
		setVisible(true);		
	}	

	@Override
	public void repaint() {
		mainDisplay.repaint();
	}
	
	private JPanel createCenterPanel() {
		//MainDisplay display = new MainDisplay();
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		Border cb = BorderFactory.createCompoundBorder(BorderFactory
				.createEmptyBorder(3, 3, 3, 3), BorderFactory
				.createLineBorder(Color.gray));
		centerPanel.setBorder(cb);
		centerPanel.add(mainDisplay, BorderLayout.CENTER);
		return centerPanel;
	}
	
	private class MainDisplay extends Canvas implements MouseListener {	
		
		public MainDisplay() {
			setMinimumSize(new Dimension(BOX_WIDTH*4,BOX_HEIGHT*4));
			setBounds(0, 0, BOX_WIDTH*4, BOX_HEIGHT*4);
			addMouseListener(this);
			pack();
		}
		
		public void paint(Graphics g) {			
			//
			Board board = game.getBoard();
			//
			for (int x = 0; x < 4; ++x) {
				for (int y = 0; y < 4; ++y) {
					Board.Token token  = board.getSquare(new Position(x,y));
					drawLocation(x, y, BOX_WIDTH, BOX_HEIGHT, token, g);
				}
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			int x = e.getX() / BOX_WIDTH;
			int y = e.getY() / BOX_HEIGHT;
			//		
			createActionMenu(e, new Position(x,y));
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
		
	private static void drawLocation(int x, int y, int width, int height, Board.Token token, Graphics g) {
		g.translate(x*width,y*height);
		g.setClip(0,0,width,height);
		
		g.setColor(BACKGROUND);
		// Fill the box with the background color
		g.fillRect(0, 0, width, height);
		// Draw an outline around the box
		g.setColor(BORDER);
		g.drawRect(0, 0, width, height);
		// Now draw whatever is in the location		
		//		
		if(token == Board.Token.WHITE) {			
			g.setColor(Color.WHITE);
			g.fillOval(5,5,width-10,height-10);
		} else if(token == Board.Token.BLACK) {			
			g.setColor(Color.BLACK);				
			g.fillOval(5,5,width-10,height-10); 
		}
		g.translate(-(x*width), -(y*height));
	}
	
	private void createActionMenu(MouseEvent e, final Position pos) {
		JPopupMenu actionMenu = new JPopupMenu();
						
		JMenuItem mi = new JMenuItem("Place WHITE token");
		mi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					game.placeToken(pos, Board.Token.WHITE);
					reportStatus();					
				} catch(IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(GraphicalUserInterface.this,ex.getMessage());
				}
				GraphicalUserInterface.this.repaint();
				
			}			
		});
		actionMenu.add(mi);		
		mi = new JMenuItem("Place BLACK token");
		mi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					game.placeToken(pos, Board.Token.BLACK);
					reportStatus();
				} catch(IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(GraphicalUserInterface.this,ex.getMessage());
				}
				GraphicalUserInterface.this.repaint();
			}			
		});
		actionMenu.add(mi);		
		
		actionMenu.show(e.getComponent(), e.getX(), e.getY());		
	}
	
	private void reportStatus() {
		switch(game.getStatus()) {
		case STALEMATE:
			JOptionPane.showMessageDialog(this,"Stalemate!");
			break;
		case BLACKWON:
			JOptionPane.showMessageDialog(this,"Black Wins!");
			break;
		case WHITEWON:
			JOptionPane.showMessageDialog(this,"White Wins!");
			break;
		default:
		}
	}
}
