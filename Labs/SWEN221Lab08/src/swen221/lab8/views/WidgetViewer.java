package swen221.lab8.views;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

import swen221.lab8.core.Widget;
import swen221.lab8.util.Point;
import swen221.lab8.util.Rectangle;

/**
 * Provides a simple Graphical User Interface for the AdventureGame.
 * 
 * @author David J. Pearce
 *
 */
public class WidgetViewer extends JFrame {
    //
    private final MainDisplay mainDisplay;
    //
    private final Widget root;

    public WidgetViewer(Widget root) {
        super("Widget Viewer");

        this.root = root;
        this.mainDisplay = new MainDisplay();

        add(createCenterPanel(), BorderLayout.CENTER);

        setFocusable(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    @Override
    public void repaint() {
        mainDisplay.repaint();
    }

    private JPanel createCenterPanel() {
        // MainDisplay display = new MainDisplay();

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        Border cb = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3),
                BorderFactory.createLineBorder(Color.gray));
        centerPanel.setBorder(cb);
        centerPanel.add(mainDisplay, BorderLayout.CENTER);
        return centerPanel;
    }

    private class MainDisplay extends Canvas implements MouseListener {

        public MainDisplay() {
            Rectangle r = root.getDimensions();
            setBounds(0, 0, r.getWidth(), r.getHeight());
            addMouseListener(this);
        }

        public void paint(final Graphics g) {
            swen221.lab8.core.Canvas c = new swen221.lab8.core.Canvas() {

                @Override
                public void fillRectangle(Rectangle box, Color color) {
                    g.setColor(color);
                    g.fillRect(box.getX(), box.getY(), box.getWidth(), box.getHeight());
                }

                @Override
                public void drawRectangle(Rectangle box, Color color) {
                    g.setColor(color);
                    g.drawRect(box.getX(), box.getY(), box.getWidth(), box.getHeight());
                }
            };
            root.paint(c);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            root.mouseClick(new Point(x, y));
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }

}
