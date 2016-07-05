package swen221.lab3.model;

import java.awt.Graphics;

/**
 * /**
 * This class represents a collection of books in the game.
 * 
 * @author Hector
 *
 */
public class Book extends PickupableItem {
    
    // a flag showing whether the book is read
    private boolean isRead;
    // the title of the book
    private String title;
    
    public Book(String title) {
        this.isRead = false;
        this.title = title;
    }

    @Override
    public String getDescription() {
        if (isRead) {
            return "A book entitled \"" + title + "\"; it looks like it has been read";
        } else {
            return "A book entitled \"" + title + "\"";
        }
    }

    @Override
    public String[] getActions() {
        return new String[]{"Pickup","Drop", "Read"};
    }
    
    @Override
    public boolean performAction(String action, Player player) {
        if (action.equals("Pickup")) {
            return pickupThisItem(player);
        } else if(action.equals("Drop")){
            return dropThisItem(player);
        } else if(action.equals("Read")){
            return readBook(player);
        } else {
            throw new IllegalArgumentException("Unknown action: " + action);
        }
    }

    private boolean readBook(Player player) {
        if (player.getInventory().contains(this)) {
            isRead = true;
        }
        return isRead;
    }

    @Override
    public void draw(Graphics g) {
        // TODO Auto-generated method stub
    }
}
