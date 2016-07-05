package swen221.assignment4.cards.viewer;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

import swen221.assignment4.cards.core.CardGame;
import swen221.assignment4.cards.core.Player;
import swen221.assignment4.cards.variations.*;

public class ConfigurationDialog extends JDialog {
    /**
     * 
     */
    private static final long serialVersionUID = -4306155212156204264L;
    final String[] gameKindNames = { "Classic Whist", "Knock-Out Whist", "Single-Hand Whist" };
    @SuppressWarnings("rawtypes")
    final Class[] gameKinds = { ClassicWhist.class, KnockOutWhist.class, SingleHandWhist.class };

    public ConfigurationDialog(TableFrame parent) {
        super(parent, true);

        JPanel splashPanel = createSplashPanel();
        JPanel gamePanel = createGamePanel(parent);
        JPanel playerPanel = createPlayerPanel(parent);
        JPanel startPanel = createStartPanel();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(new GridBagLayout());

        c.gridwidth = GridBagConstraints.REMAINDER;
        add(splashPanel, c);
        c.gridwidth = 1;
        add(gamePanel, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(playerPanel, c);
        add(startPanel, c);
        pack();

        // Center window in screen
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension scrnsize = toolkit.getScreenSize();
        setBounds((scrnsize.width - getWidth()) / 2, (scrnsize.height - getHeight()) / 2, getWidth(), getHeight());

        // Display window!
        setVisible(true);
    }

    private JPanel createSplashPanel() {
        JPanel panel = new JPanel();
        Border tb = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        tb = BorderFactory.createCompoundBorder(tb, BorderFactory.createLineBorder(Color.GRAY, 1));
        tb = BorderFactory.createCompoundBorder(tb, BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBorder(tb);
        panel.add(new JLabel("Welcome to the Card Game!"));
        return panel;
    }

    @SuppressWarnings("serial")
    private JPanel createGamePanel(final TableFrame table) {
        final JRadioButton[] buttons = new JRadioButton[gameKinds.length];
        ButtonGroup group = new ButtonGroup();

        for (int i = 0; i != gameKinds.length; ++i) {
            @SuppressWarnings("rawtypes")
            final Class kind = gameKinds[i];
            buttons[i] = new JRadioButton("", kind.isInstance(table.getGame()));
            buttons[i].setAction(new AbstractAction(gameKindNames[i]) {
                public void actionPerformed(ActionEvent e) {
                    try {
                        table.setGame((CardGame) kind.newInstance());
                    } catch (IllegalAccessException ex) {

                    } catch (InstantiationException ex) {

                    }
                }
            });
            group.add(buttons[i]);
        }

        JPanel panel = new JPanel();
        Border cb = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        cb = BorderFactory.createCompoundBorder(cb, BorderFactory.createLineBorder(Color.GRAY, 1));
        cb = BorderFactory.createCompoundBorder(cb, BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBorder(cb);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Choose the Game Rules:"));
        panel.add(new JLabel(" "));
        for (JRadioButton box : buttons) {
            panel.add(box);
        }
        return panel;
    }

    @SuppressWarnings("serial")
    private JPanel createPlayerPanel(final TableFrame table) {
        final JCheckBox[] checkBoxes = new JCheckBox[4];

        for (final Player.Direction p : Player.Direction.values()) {
            checkBoxes[p.ordinal()] = new JCheckBox("", table.isComputerPlayer(p));
            checkBoxes[p.ordinal()].setAction(new AbstractAction(p + " is a Computer Player.") {
                public void actionPerformed(ActionEvent e) {
                    table.setComputerPlayer(p, !table.isComputerPlayer(p));
                }
            });
        }

        JPanel playerPanel = new JPanel();
        Border cb = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        cb = BorderFactory.createCompoundBorder(cb, BorderFactory.createLineBorder(Color.GRAY, 1));
        cb = BorderFactory.createCompoundBorder(cb, BorderFactory.createEmptyBorder(15, 15, 15, 15));
        playerPanel.setBorder(cb);
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.add(new JLabel("Choose the Computer Players:"));
        playerPanel.add(new JLabel(" "));
        for (JCheckBox box : checkBoxes) {
            playerPanel.add(box);
        }
        return playerPanel;
    }

    @SuppressWarnings("serial")
    private JPanel createStartPanel() {
        JPanel startPanel = new JPanel();
        JButton button = new JButton();
        button.setAction(new AbstractAction("Let's Play") {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        startPanel.add(button);
        return startPanel;
    }
}
