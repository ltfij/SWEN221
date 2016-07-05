package swen221.assignment4.cards.variations;

import java.util.List;
import swen221.assignment4.cards.core.Card;
import swen221.assignment4.cards.core.Player;
import swen221.assignment4.cards.util.AbstractCardGame;

/**
 * A simple variation of Whist where only a single hand is played.
 * 
 * @author David J. Pearce
 * 
 */
public class ClassicWhist extends AbstractCardGame {
    
    /**
     * 
     */
    private static final long serialVersionUID = 2614835887406603684L;

    public ClassicWhist() {
        
    }

    public String getName() {
        return "Single Hand Whist";
    }

    public boolean isGameFinished() {
        for (Player.Direction d : Player.Direction.values()) {
            if (scores.get(d) == 5) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void deal(List<Card> deck) {
        currentTrick = null;
        for (Player.Direction d : Player.Direction.values()) {
            players.get(d).getHand().clear();
        }
        Player.Direction d = Player.Direction.NORTH;
        for (int i = 0; i < deck.size(); ++i) {
            Card card = deck.get(i);
            players.get(d).getHand().add(card);
            d = d.next();
        }
    }

}
