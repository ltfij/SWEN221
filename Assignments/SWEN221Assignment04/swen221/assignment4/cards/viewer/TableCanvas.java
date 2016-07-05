package swen221.assignment4.cards.viewer;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;

import swen221.assignment4.cards.core.*;

/**
 * A table canvas draws all of the cards in play during the game.
 * 
 * @author djp
 * 
 */
public class TableCanvas extends Canvas implements MouseListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1662269096343439773L;
    private final TableFrame parent;
    private Font font;
    private static final String[] preferredFonts = { "Arial", "Times New Roman" };

    public TableCanvas(TableFrame parent) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        HashSet<String> availableNames = new HashSet<String>();

        for (String name : env.getAvailableFontFamilyNames()) {
            availableNames.add(name);
        }

        for (String pf : preferredFonts) {
            if (availableNames.contains(pf)) {
                font = new Font(pf, Font.PLAIN, 20);
                break;
            }
        }

        this.parent = parent;
        setBounds(0, 0, 600, 600);
        addMouseListener(this);
    }

    public void paint(Graphics g) {
        CardGame game = parent.getGame();
        g.setColor(new Color(0, 150, 0));
        g.fillRect(0, 0, getWidth(), getHeight());
        if (font != null) {
            g.setFont(font);
        }
        drawVHand(game.getPlayer(Player.Direction.WEST), cardsWest, g, 10);
        drawVHand(game.getPlayer(Player.Direction.EAST), cardsEast, g, 490);
        drawHHand(game.getPlayer(Player.Direction.SOUTH), cardsSouth, g, 490);
        drawHHand(game.getPlayer(Player.Direction.NORTH), cardsNorth, g, 10);

        g.setColor(Color.YELLOW);
        g.drawRect(120, 120, 360, 360);
        Trick trick = game.getTrick();
        if (trick != null) {
            drawPlayedCard(trick.getCardPlayed(Player.Direction.NORTH), cardsNorth, g, 265, 150);
            drawPlayedCard(trick.getCardPlayed(Player.Direction.EAST), cardsEast, g, 350, 265);
            drawPlayedCard(trick.getCardPlayed(Player.Direction.SOUTH), cardsSouth, g, 265, 350);
            drawPlayedCard(trick.getCardPlayed(Player.Direction.WEST), cardsWest, g, 150, 265);
        }
    }

    private void drawPlayedCard(Card card, Image[] cards, Graphics g, int x, int y) {
        if (card != null) {
            Image cimg = cards[cn(card)];
            g.drawImage(cimg, x, y, null);
        }
    }

    private void drawVHand(Player player, Image[] cards, Graphics g, int xpos) {
        Hand hand = player.getHand();
        if (hand.size() > 0) {
            final float spacing = spacing(hand);
            int ypos = pos(hand.size(), spacing);
            for (Card c : hand) {
                Image cimg;
                if (parent.isComputerPlayer(player.getDirection())) {
                    cimg = cardDownHorizontal;
                } else {
                    cimg = cards[cn(c)];
                }
                g.drawImage(cimg, xpos, ypos, null);
                ypos += spacing;
            }
        }
    }

    private void drawHHand(Player player, Image[] cards, Graphics g, int ypos) {
        Hand hand = player.getHand();
        if (hand.size() > 0) {
            final float spacing = spacing(hand);
            int xpos = pos(hand.size(), spacing);
            for (Card c : hand) {
                Image cimg;
                if (parent.isComputerPlayer(player.getDirection())) {
                    cimg = cardDownVertical;
                } else {
                    cimg = cards[cn(c)];
                }
                g.drawImage(cimg, xpos, ypos, null);
                xpos += spacing;
            }
        }
    }

    private int pos(int size, float spacing) {
        return 300 - ((int) ((size * spacing) + 80) / 2);
    }

    private int spacing(Hand hand) {
        return Math.min(80, 300 / hand.size());
    }

    private int cn(Card c) {
        return c.suit().ordinal() * 13 + c.rank().ordinal();
    }

    private static final Image[] cardsNorth = new Image[52];
    private static final Image[] cardsEast = new Image[52];
    private static final Image[] cardsSouth = new Image[52];
    private static final Image[] cardsWest = new Image[52];
    private static final Image cardDownHorizontal;
    private static final Image cardDownVertical;
    private static char[] suits = { '\u2665', '\u2663', '\u2666', '\u2660' };
    private static String[] numbers = { "", "", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };

    static {
        int width = 75;
        int height = 100;

        for (int s = 0; s <= 3; ++s) {
            for (int i = 2; i <= 14; ++i) {
                int cn = (13 * s) + (i - 2);
                cardsNorth[cn] = createCardImage(width, height, s, i, Player.Direction.NORTH);
                cardsEast[cn] = createCardImage(width, height, s, i, Player.Direction.EAST);
                cardsSouth[cn] = createCardImage(width, height, s, i, Player.Direction.SOUTH);
                cardsWest[cn] = createCardImage(width, height, s, i, Player.Direction.WEST);
            }
        }

        cardDownHorizontal = createCardDownImage(height, width);
        cardDownVertical = createCardDownImage(width, height);
    }

    private static Image createCardImage(int width, int height, int suit, int number, Player.Direction dir) {
        // Yeah, this method is pretty crazy ... it probably could be a *lot*
        // better
        double theta;
        int cwidth = width;
        int cheight = height;
        int twidth = 0;
        int theight = 0;

        if (dir == Player.Direction.WEST) {
            theta = Math.PI / 2;
            cheight = width;
            cwidth = height;
            twidth = 0;
            theight = -height;
        } else if (dir == Player.Direction.EAST) {
            theta = -Math.PI / 2;
            cheight = width;
            cwidth = height;
            twidth = -width;
        } else if (dir == Player.Direction.SOUTH) {
            theta = 0;
        } else {
            theta = Math.PI;
            twidth = -width;
            theight = -height;
        }

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gs = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gs.getDefaultConfiguration();
        BufferedImage img = gc.createCompatibleImage(cwidth, cheight, Transparency.OPAQUE);
        Graphics2D g = img.createGraphics();

        AffineTransform at = new AffineTransform();
        at.rotate(theta);
        at.translate(twidth, theight);
        g.setTransform(at);

        g.setColor(new Color(0, 150, 0));
        g.fillRect(0, 0, width, height);
        g.setColor(Color.WHITE);
        g.fillRoundRect(0, 0, width, height, 15, 15);
        g.setColor(Color.BLACK);
        g.drawRoundRect(0, 0, width, height, 15, 15);

        if ((suit % 2) != 0) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.RED);
        }

        FontMetrics metrics = g.getFontMetrics();
        char[] numChars = (numbers[number]).toCharArray();
        char[] suitChars = { suits[suit] };
        int off = width - (metrics.charsWidth(numChars, 0, numChars.length) + 5);
        int ascent = metrics.getAscent();
        g.drawChars(numChars, 0, numChars.length, 5, 5 + ascent);
        g.drawChars(suitChars, 0, suitChars.length, 5, 5 + ascent + ascent);
        g.drawChars(numChars, 0, numChars.length, off, height - 5);
        g.drawChars(suitChars, 0, suitChars.length, off, height - 5 - ascent);

        return img;
    }

    private static Image createCardDownImage(int width, int height) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gs = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gs.getDefaultConfiguration();
        BufferedImage img = gc.createCompatibleImage(width, height, Transparency.OPAQUE);
        Graphics2D g = img.createGraphics();

        g.setColor(new Color(0, 150, 0));
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.fillRoundRect(0, 0, width, height, 15, 15);
        g.setColor(Color.GRAY);
        g.fillRoundRect(1, 1, width - 2, height - 2, 15, 15);

        return img;
    }

    public void determinePlayedCard(int distance, Player player) {
        try {
            Hand hand = player.getHand();
            if (hand.size() > 0) {
                float spacing = spacing(hand);
                int pos = pos(hand.size(), spacing);
                int idx = 1;
                for (Card card : hand) {
                    float width = spacing;
                    if (idx == hand.size()) {
                        width = 80; // for last card, which has larger surface
                    }
                    if (distance > pos && distance < (pos + width)) {
                        parent.playedEvent(player.getDirection(), card);
                        return;
                    }
                    pos += spacing;
                    idx++;
                }
            }
        } catch (IllegalMove e) {
            parent.statusEvent(e.getMessage());
        }
    }

    // ==============================================================
    // Following methods are required for MouseListener
    // ==============================================================

    public void mouseClicked(MouseEvent e) {
        CardGame game = parent.getGame();
        int xpos = e.getX();
        int ypos = e.getY();
        if (ypos < 110) {
            // NORTH CLICK
            determinePlayedCard(xpos, game.getPlayer(Player.Direction.NORTH));
        } else if (ypos > 490) {
            // SOUTH CLICK
            determinePlayedCard(xpos, game.getPlayer(Player.Direction.SOUTH));
        } else if (xpos < 110) {
            // WEST CLICK
            determinePlayedCard(ypos, game.getPlayer(Player.Direction.WEST));
        } else if (xpos > 490) {
            // EAST CLICK
            determinePlayedCard(ypos, game.getPlayer(Player.Direction.EAST));
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public static void pause(int time) throws InterruptedException {
        time = time * 1000; // convert in milli-sec
        Thread.sleep(time);
    }
}
