import java.awt.Graphics;

import javax.swing.JPanel;

// We create a panel inside the frame for the game to display the pictures etc.
// Created by Shafiq 11/10/2024

public class GamePanel extends JPanel {

    public GamePanel() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.fillRect(100, 100, 200, 50);

    }

}