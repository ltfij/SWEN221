package swen221.lab3.model;

import java.awt.Graphics;

import swen221.lab3.model.Item;
import swen221.lab3.model.Room.Location;

public class SecretButton implements Item {
    
    private int code;

    public SecretButton(int code) {
        this.code = code;
    }

    @Override
    public String getDescription() {
        return "A button that unlocks a locked door somewhere";
    }

    @Override
    public String[] getActions() {
        return new String[]{"Press"};
    }

    @Override
    public boolean performAction(String action, Player player) {
        Room r = player.getLocation();
        
        for (Location location : Location.values()) {
            Item item = r.getItem(location);
            if (item instanceof LockedDoor) {
                if (((LockedDoor)item).getCode() == this.code) {
                    ((LockedDoor)item).unlockDoor(false);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void draw(Graphics g) {
        // TODO Auto-generated method stub
        
    }



}
