package swen221.lab3.model;

import java.util.ArrayList;

/**
 * This class represents a locked door in the game. It provides references
 * to the rooms which are on either side of the door. It is only openable 
 * once the player is in procession of a key that matches the secret code.
 * 
 * @author Hector
 * 
 */
public class LockedDoor extends Door {
    
    private int code;
    private boolean isLocked;

    public LockedDoor(Room oneSide, Room otherSide, int code) {
        super(oneSide, otherSide);
        this.code = code;
        this.isLocked = true;
    }
    
    @Override
    public String[] getActions() {
        return new String[]{"Unlock", "Lock", "Enter"};
    }
    
    @Override
    public boolean performAction(String action, Player player) {
        Room r = player.getLocation();
        
        // a list of all codes the player has found
        ArrayList<Integer> codes = new ArrayList<>();
        for (Item item : player.getInventory()) {
            if (item instanceof Key) {
                int code = ((Key) item).getCode();
                codes.add(code);
            }
        }
        
        // does the player found the key to this door?
        boolean hasKey = false;
        
        if (codes.contains(this.code)) {
            hasKey = true;  // yes
        } else {
            hasKey = false;   // no key found
        }
        
        if (action.equals("Lock")) {
            if (hasKey) {
                this.isLocked = true;
                return true;
            } else {
                return false;
            }
        } else if (action.equals("Unlock")) {
            if (hasKey) {
                this.isLocked = false;
                return true;
            } else {
                return false;
            }
        } else if (action.equals("Enter")) {
            if (isLocked) {
                return false;
            } else {
             // First, check which side of the door the player is on.
                if(r == oneSide()) {
                    player.setLocation(otherSide());
                } else {
                    player.setLocation(oneSide());
                }
                // Done
                return true;
            }
        } else {
            throw new IllegalArgumentException("Unknown action: " + action);
        }
    }
    
    @Override
    public String getDescription() {
        String s = "A door between \"" + this.oneSide().getDescription() + 
                "\" and \"" + this.otherSide().getDescription() + "\"; it's ";
        if (isLocked) {
            return s + "locked.";
            
        } else {
            return s + "unlocked.";
        }
    }
    
    public int getCode() {
        return this.code;
    }
    
    public void unlockDoor(boolean bool) {
        this.isLocked = bool;
    }
}
