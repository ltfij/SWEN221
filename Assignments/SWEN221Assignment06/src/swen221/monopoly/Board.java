package swen221.monopoly;

import java.util.*;

import swen221.monopoly.locations.ColourGroup;
import swen221.monopoly.locations.Location;
import swen221.monopoly.locations.SpecialArea;
import swen221.monopoly.locations.Station;
import swen221.monopoly.locations.Street;
import swen221.monopoly.locations.Utility;

/**
 * Represents the Monopoly Board, which is made up from 40 different locations. The board simply
 * provides access functions to determine the location object at a given position on the board.
 * Positions are taken in clockwise order relative to the special location "Go" which is considered
 * position "0". Thus, position 1 is "Old Kent Road", position 2 is "Community Chest", etc.
 *
 * @author David J. Pearce
 */
public class Board {
    private ArrayList<Location> places = new ArrayList<Location>();

    /**
     * Construct a new board.
     */
    public Board() {

        // Construct the locations
        Street oldKentRoad = new Street("Old Kent Road", 60, 2);
        Street whiteChapel = new Street("Whitechapel Road", 60, 4);

        Street angel = new Street("The Angel Islington", 100, 6);
        Street euston = new Street("Euston Road", 100, 6);
        Street pentonville = new Street("Pentonville", 120, 9);

        Street pallmall = new Street("Pall Mall", 140, 10);
        Street whitehall = new Street("Whitehall", 140, 10);
        Street northumberland = new Street("Northumberland Ave", 160, 12);

        Street bow = new Street("Bow Street", 180, 14);
        Street marlborough = new Street("Marlborough Street", 180, 14);
        Street vine = new Street("Vine Street", 200, 16);

        Street strand = new Street("The Strand", 220, 18);
        Street fleet = new Street("Fleet Street", 220, 18);
        Street trafalgar = new Street("Trafalgar Square", 240, 20);

        Street leceister = new Street("Leceister Square", 260, 22);
        Street conventry = new Street("Conventry Street", 260, 22);
        Street picadilly = new Street("Picadilly Circus", 280, 24);

        Street regent = new Street("Regent Street", 300, 26);
        Street oxford = new Street("Oxford Street", 300, 26);
        Street bond = new Street("Bond Street", 320, 28);

        Street park = new Street("Park Lane", 350, 35);
        Street mayfair = new Street("Mayfair", 400, 50);

        // Setup the colour groups
        new ColourGroup("Brown", 50, oldKentRoad, whiteChapel);
        new ColourGroup("Cyan", 50, angel, euston, pentonville);
        new ColourGroup("Pink", 100, pallmall, whitehall, northumberland);
        new ColourGroup("Orange", 100, bow, marlborough, vine);
        new ColourGroup("Red", 150, strand, fleet, trafalgar);
        new ColourGroup("Yellow", 150, leceister, conventry, picadilly);
        new ColourGroup("Green", 200, regent, oxford, bond);
        new ColourGroup("Blue", 200, park, mayfair);

        // Finally, construct the board itself
        places.add(new SpecialArea("Go"));
        places.add(oldKentRoad);
        places.add(new SpecialArea("Community Chest"));
        places.add(whiteChapel);
        places.add(new SpecialArea("Income Tax"));
        places.add(new Station("Kings Cross Station", 200));
        places.add(angel);
        places.add(new SpecialArea("Chance"));
        places.add(euston);
        places.add(pentonville);
        places.add(new SpecialArea("Jail"));
        places.add(pallmall);
        places.add(new Utility("Electric Company", 150));
        places.add(whitehall);
        places.add(northumberland);
        places.add(new Station("Marylebone Station", 200));
        places.add(bow);
        places.add(new SpecialArea("Community Chest"));
        places.add(marlborough);
        places.add(vine);
        places.add(new SpecialArea("Free Parking"));
        places.add(strand);
        places.add(new SpecialArea("Chance"));
        places.add(fleet);
        places.add(trafalgar);
        places.add(new Station("Fenchurch St. Station", 200));
        places.add(leceister);
        places.add(conventry);
        places.add(new Utility("Water Works", 150));
        places.add(picadilly);
        places.add(new SpecialArea("Goto Jail"));
        places.add(regent);
        places.add(oxford);
        places.add(new SpecialArea("Community Chest"));
        places.add(bond);
        places.add(new Station("Livepool St. Station", 200));
        places.add(new SpecialArea("Chance"));
        places.add(park);
        places.add(new SpecialArea("Super Tax"));
        places.add(mayfair);
    }

    /**
     * Return location correspond to "Go" on the board
     */
    public Location getStartLocation() {
        return places.get(0);
    }

    /**
     * Find location which is a given number of steps along from the given location argument. Steps
     * are counted in an anti-clock wise fashion, as this follows the direction in which the
     * Monopoly game is played.
     */
    public Location findLocation(Location location, int steps) {
        int idx = places.indexOf(location);
        idx = (idx + steps) % places.size();
        return places.get(idx);
    }

    /**
     * Find location with the given name, or return null if no such location exists.
     */
    public Location findLocation(String name) {
        for (Location l : places) {
            if (l.getName().equals(name)) {
                return l;
            }
        }
        return null;
    }
}
