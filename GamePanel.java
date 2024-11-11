import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

// We create a panel inside the frame for the game to display the pictures etc.
// Created by Shafiq 11/10/2024

public class GamePanel extends JPanel {

    private int yDelta = 150; // Direction for Flappy bird up and down.

    public GamePanel() {
        // KeyboardInputs keyboardInputs = new KeyboardInputs(this);
        addKeyListener(new KeyboardInputs(this)); // When you press the space bar key the player goes up or flies.
        addMouseListener(new MouseInputs(this)); // Incase we need to make flappy work with mouse clicks.
        // Following method changes the bird direction up
    }

    public void changeYDelta(int y) {
        yDelta += y;
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(100, yDelta, 50, 50);

        repaint();

    }

}