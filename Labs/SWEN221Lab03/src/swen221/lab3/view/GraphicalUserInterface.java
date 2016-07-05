package swen221.lab3.view;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;

import swen221.lab3.AdventureGame;
import swen221.lab3.model.Item;
import swen221.lab3.model.Player;
import swen221.lab3.model.Room;

/**
 * Provides a simple Graphical User Interface for the AdventureGame.
 * 
 * @author David J. Pearce
 *
 */
public class GraphicalUserInterface extends JFrame {

	private static final int BOX_WIDTH = 100;
	private static final int BOX_HEIGHT = 100;
	private static final Color ROOM_BACKGROUND = new Color(200,200,200);
	private static final Color ROOM_BORDER = new Color(50,50,50);
	//
	private final MainDisplay mainDisplay;
	private final InventoryDisplay inventoryDisplay;
	private final JLabel locationText;

	private final AdventureGame game;

	public GraphicalUserInterface(AdventureGame game) {
		super("Adventure Game");

		this.game = game;	
		this.mainDisplay = new MainDisplay();
		this.inventoryDisplay = new InventoryDisplay();
		this.locationText = new JLabel(game.getPlayer().getLocation().getDescription());
		
		add(createTopPanel(),BorderLayout.NORTH);
		add(createCenterPanel(),BorderLayout.CENTER);		
		add(createBottomPanel(),BorderLayout.SOUTH);

		setFocusable(true);		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		pack();
		setVisible(true);		
	}	

	@Override
	public void repaint() {
		mainDisplay.repaint();
		inventoryDisplay.repaint();
	}
	
	private JPanel createTopPanel() {
		JPanel topPanel = new JPanel();		
		topPanel.add(locationText);
		return topPanel;
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
	
	private JPanel createBottomPanel() {
		JPanel bottomPanel = new JPanel();
		Border blackline = BorderFactory.createLineBorder(Color.black);
		bottomPanel.setBorder(BorderFactory.createTitledBorder(blackline, "Inventory"));
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));				
		bottomPanel.add(inventoryDisplay);
		return bottomPanel;
	}
	
	private class MainDisplay extends Canvas implements MouseListener {	
		
		public MainDisplay() {			
			setBounds(0, 0, BOX_WIDTH*3, BOX_HEIGHT*3);
			addMouseListener(this);
		}
		
		public void paint(Graphics g) {			
			Room currentLocation = game.getPlayer().getLocation();
			//
			locationText.setText(currentLocation.getDescription());
			//
			for (int x = 0; x < 3; ++x) {
				for (int y = 0; y < 3; ++y) {
					Room.Location location = getRoomLocation(x, y);
					Item item = currentLocation.getItem(location);
					drawLocation(x, y, BOX_WIDTH, BOX_HEIGHT, item, g);
				}
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			int x = e.getX() / BOX_WIDTH;
			int y = e.getY() / BOX_HEIGHT;
			//		
			Room.Location l = getRoomLocation(x,y);
			Room r = game.getPlayer().getLocation();
			Item i = r.getItem(l);
			if(i != null) {
				createActionMenu(e,i);
			}
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
		
	private class InventoryDisplay extends Canvas implements MouseListener {	

		private final int SIZE_DIVISOR = 4;
		private final int NUM_ITEMS = 12;
		
		public InventoryDisplay() {			
			setBounds(0, 0, (BOX_WIDTH*NUM_ITEMS)/SIZE_DIVISOR, (BOX_HEIGHT)/SIZE_DIVISOR);
			addMouseListener(this);
		}

		public void paint(Graphics g) {			
			Player player = game.getPlayer();
			//				
			//
			List<Item> inventory = player.getInventory();			
			for(int i=0;i<NUM_ITEMS;++i) {
				Item item = null;
				if(i < inventory.size()) {
					item = inventory.get(i);
				}				
				drawLocation(i,0,BOX_WIDTH/SIZE_DIVISOR,BOX_HEIGHT/SIZE_DIVISOR,item,g);
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			int x = (e.getX()*SIZE_DIVISOR) / BOX_WIDTH;
			//				
			List<Item> inventory = game.getPlayer().getInventory();
			
			if(x < inventory.size()) {
				createActionMenu(e,inventory.get(x));
			} 
			//					
			GraphicalUserInterface.this.repaint();
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

	private static void drawLocation(int x, int y, int width, int height, Item item, Graphics g) {
		g.translate(x*width,y*height);
		g.setClip(0,0,width,height);
		
		g.setColor(ROOM_BACKGROUND);
		// Fill the box with the background color
		g.fillRect(0, 0, width, height);
		// Draw an outline around the box
		g.setColor(ROOM_BORDER);
		g.drawRect(0, 0, width, height);
		// Now draw whatever is in the location		
		//		
		if(item != null) { item.draw(g); }
		g.translate(-(x*width), -(y*height));
	}
	
	private void createActionMenu(MouseEvent e, final Item item) {
		JPopupMenu actionMenu = new JPopupMenu();
		String[] actions = item.getActions();
						
		JMenuItem mi = new JMenuItem("Description");
		mi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(GraphicalUserInterface.this,item.getDescription());
			}			
		});
		actionMenu.add(mi);		
		for(int i=0;i!=actions.length;++i) {
			mi = new JMenuItem(actions[i]);
			mi.addActionListener(createItemListener(item,actions[i]));
			actionMenu.add(mi);
		}				
		actionMenu.show(e.getComponent(), e.getX(), e.getY());		
	}
	
	private ActionListener createItemListener(final Item i, final String action) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean r = i.performAction(action, game.getPlayer());
				if(!r) {
					JOptionPane.showMessageDialog(GraphicalUserInterface.this,"Could not \"" + action + "\" item");
				}
				// Force a repain in case anything has changed
				GraphicalUserInterface.this.repaint();
			}			
		};
	}
	
	/**
	 * Convert a room coordinate to a room location. Here, (0,0) represents
	 * NORTHWEST, whilst (2,2) represents SOUTHEAST.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private static Room.Location getRoomLocation(int x, int y) {
		return Room.Location.values()[(y*3)+x];
	}
}
