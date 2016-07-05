package swen221.assignment4.cards.viewer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.*;

import swen221.assignment4.cards.core.*;
import swen221.assignment4.cards.util.AbstractCardGame;
import swen221.assignment4.cards.util.AbstractComputerPlayer;
import swen221.assignment4.cards.util.SimpleComputerPlayer;
import swen221.assignment4.cards.variations.*;

/**
 * A Table Frame constructs the window that is the "card table".
 * 
 * @author djp
 */
@SuppressWarnings("serial")
public final class TableFrame extends JFrame {
    private final TableCanvas canvas;
    private final JLabel statusBar;
    private final JLabel trickBar;
    private final JLabel scoreBar;
    private final JButton duplicateButton;
    private Map<Player.Direction, AbstractComputerPlayer> computerPlayers = new HashMap<>();
    private CardGame game;

    /**
     * Create a TableFrame which asks the user for configuration details about
     * the game. For example, which players should be human and which should be
     * computer players.
     */
    public TableFrame() {
        this(new ClassicWhist());

        // Set all players except south to be computer players by default.
        for (Player.Direction dir : Player.Direction.values()) {
            if (dir != Player.Direction.SOUTH) {
                computerPlayers.put(dir, new SimpleComputerPlayer(game.getPlayer(dir)));
            }
        }

        // Ask the user to configure the game
        new ConfigurationDialog(this);

        // Finally, start the game ...
        startHand();
    }

    /**
     * Construct a table frame from a given game. All players are assumed to be
     * human.
     * 
     * @param game
     */
    public TableFrame(CardGame game) {
        super("Card Game");

        this.game = game;

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.LINE_START;
        setLayout(new GridBagLayout());

        canvas = new TableCanvas(this);

        statusBar = new JLabel("Status");
        scoreBar = new JLabel("Score");
        trickBar = new JLabel("Tricks");
        add(scoreBar, c);
        add(canvas, c);
        add(statusBar, c);
        add(trickBar, c);
        duplicateButton = new JButton("Duplicate");
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.gridheight = 2;
        c.anchor = GridBagConstraints.LINE_END;
        add(duplicateButton, c);

        duplicateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // Call clone method in parent class (TableFrame)
                TableFrame.this.clone();
            }

        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        // Center window in screen
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension scrnsize = toolkit.getScreenSize();
        setBounds((scrnsize.width - getWidth()) / 2, (scrnsize.height - getHeight()) / 2, getWidth(), getHeight());
        // Display window
        setVisible(true);
    }

    public CardGame getGame() {
        return game;
    }

    public void setGame(CardGame game) {
        this.game = game;
        for (Player.Direction d : Player.Direction.values()) {
            AbstractComputerPlayer cp = computerPlayers.get(d);
            if (cp != null) {
                cp.setPlayer(game.getPlayer(d));
            }
        }
    }

    public boolean isComputerPlayer(Player.Direction player) {
        return computerPlayers.containsKey(player);
    }

    public void setComputerPlayer(Player.Direction player, boolean flag) {
        if (flag) {
            computerPlayers.put(player, new SimpleComputerPlayer(game.getPlayer(player)));
        } else {
            computerPlayers.remove(player);
        }
    }

    public void startHand() {
        // Initially default game is classic whist
        java.util.List<Card> deck = AbstractCardGame.createDeck();
        Collections.shuffle(deck);
        game.deal(deck);
        startRound();
    }

    public void startRound() {
        game.startRound();
        continueRound();
    }

    public void continueRound() {
        statusEvent(game.getTrick().getNextToPlay() + " to start");
        updateScoreBars();

        if (isComputerPlayer(game.getTrick().getNextToPlay())) {
            // Computer player to start
            requestTimerEvent(500);
        }

        canvas.repaint();
    }

    public void playedEvent(Player.Direction player, Card card) throws IllegalMove {
        game.play(player, card);
        statusEvent(player + " played " + card);
        canvas.repaint();
        Player.Direction nextPlayer = game.getTrick().getNextToPlay();
        if (nextPlayer == null) {
            // Round has finished
            requestTimerEvent(500);
        } else if (isComputerPlayer(nextPlayer)) {
            // Computer player next to play
            requestTimerEvent(500);
        }
    }

    public void statusEvent(String msg) {
        Card.Suit trumps = game.getTrick().getTrumps();
        if (trumps == null) {
            statusBar.setText("No trumps, " + msg);
        } else {
            statusBar.setText(trumps + " are trumps, " + msg);
        }

    }

    public void timerEvent() {
        if (game.getTrick().getNextToPlay() == null) {
            game.endRound();
            if (game.isHandFinished()) {
                game.endHand();
                if (game.isGameFinished()) {
                    // Ok, the game has finished so signal who actually won, and
                    // ask the player if they want to go again.
                    String str = "";
                    boolean firstTime = true;
                    Set<Player.Direction> winners = game.getWinnersOfGame();
                    for (Player.Direction d : winners) {
                        if (!firstTime) {
                            str += ", ";
                        }
                        firstTime = false;
                        str += d;
                    }
                    if (winners.size() > 1) {
                        str += " are the winners!!";
                    } else {
                        str += " is the winner!!";
                    }
                    int r = JOptionPane.showConfirmDialog(this, new JLabel(str + "  Play Again?"), "Yes",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (r != JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
                startHand();
            } else {
                startRound();
            }
        } else {
            // this indicates we're waiting for a computer player to play.
            Player.Direction nextPlayer = game.getTrick().getNextToPlay();
            try {
                AbstractComputerPlayer computerPlayer = computerPlayers.get(nextPlayer);
                if (computerPlayer != null) {
                    playedEvent(nextPlayer, computerPlayer.getNextCard(game.getTrick()));
                }
            } catch (IllegalMove e) {
                throw new RuntimeException("Computer player is cheating!", e);
            }
        }
    }

    public void requestTimerEvent(final int delay) {
        Thread timer = new Thread() {
            public void run() {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    // ignore
                }
                timerEvent();
            }
        };
        timer.start();
    }

    public void updateScoreBars() {
        Map<Player.Direction, Integer> tricks = game.getTricksWon();

        String trickScore = "";
        boolean firstTime = true;
        for (Player.Direction d : Player.Direction.values()) {
            if (!firstTime) {
                trickScore += ", ";
            }
            trickScore += d + ": " + tricks.get(d);
            firstTime = false;
        }
        trickBar.setText("Tricks Won: " + trickScore);

        Map<Player.Direction, Integer> scores = game.getOverallScores();
        String overallScore = "";
        firstTime = true;
        for (Player.Direction d : Player.Direction.values()) {
            if (!firstTime) {
                overallScore += ", ";
            }
            overallScore += d + ": " + scores.get(d);
            firstTime = false;
        }
        scoreBar.setText("Overall Score: " + overallScore);
    }

    public TableFrame clone() {
        TableFrame duplicate = new TableFrame(game.clone());

        // Configure the players map
        for (Map.Entry<Player.Direction, AbstractComputerPlayer> p : computerPlayers.entrySet()) {
            duplicate.setComputerPlayer(p.getKey(), true);
        }

        // Update the various displays to signal who's go it is and/or to
        // register a computer player to take a turn
        duplicate.continueRound();

        // Done
        return duplicate;
    }
}
