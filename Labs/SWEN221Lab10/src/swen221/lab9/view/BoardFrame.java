package swen221.lab9.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;

import swen221.lab9.model.*;

public class BoardFrame extends JFrame implements ActionListener {

    private static final long serialVersionUID = -8789018861326505061L;
    private final JPanel bottomPanel;
    private final JPanel centerPanel;
    private final BoardCanvas boardCanvas;
    private final ClockThread clock;
    private final Simulation simulation;
    private boolean running;

    public BoardFrame(Simulation simulation) {
        super("Conway's Game of Life");

        this.simulation = simulation;
        boardCanvas = new BoardCanvas(simulation.getBoard());

        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        Border cb = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3),
                BorderFactory.createLineBorder(Color.gray));
        centerPanel.setBorder(cb);
        centerPanel.add(boardCanvas, BorderLayout.CENTER);

        JButton resetButton = new JButton("Reset");
        JButton runBuntton = new JButton("Run");
        JButton stopButton = new JButton("Stop");

        resetButton.addActionListener(this);
        runBuntton.addActionListener(this);
        stopButton.addActionListener(this);

        bottomPanel = new JPanel();
        bottomPanel.add(resetButton);
        bottomPanel.add(runBuntton);
        bottomPanel.add(stopButton);

        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setFocusable(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        // finally, start the clock ticking

        clock = new ClockThread(10, this);
        clock.start();
    }

    public synchronized void clockTick() {
        // First, execute the simulation
        if (running) {
            simulation.step();
        }
        // Finally, repaint the entire display
        boardCanvas.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("Reset")) {
            clearBoard();
        } else if (cmd.equals("Run")) {
            running = true;
        } else if (cmd.equals("Stop")) {
            running = false;
        }
    }

    private void clearBoard() {
        Board board = simulation.getBoard();
        for (int x = 0; x < board.getWidth(); ++x) {
            for (int y = 0; y < board.getHeight(); ++y) {
                board.setCellState(x, y, 9);
            }
        }
    }
}
