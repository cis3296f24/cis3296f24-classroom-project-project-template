package main;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;

import static main.FlappyGame.GAME_WIDTH;
import static main.FlappyGame.GAME_HEIGHT;

public class GamePanel extends JPanel {

    // private MouseInputs mouseInputs;  Per team remove mouse actions. Next group can add left click to fly the bird.
    private MouseInputs mouseInputs;
    private final FlappyGame flappyGame;
    private Image background;

    public GamePanel(FlappyGame flappyGame) {
        mouseInputs = new MouseInputs(this);
        this.flappyGame = flappyGame;
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        loadBackgroundImage();
    }


    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
    }

    private void loadBackgroundImage() {
        try {
            // Replace "background.png" with the actual path to your image
            background = ImageIO.read(getClass().getResource("./istockphoto-1190059388-612x612.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading background image.");
        }
    }

    public void updateGame() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        }

        flappyGame.render(g);
    }

    public FlappyGame getGame() {
        return flappyGame;
    }

}