package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static main.FlappyGame.GAME_HEIGHT;
import static main.FlappyGame.GAME_WIDTH;

/**
 * GamePanel is a JPanel that renders the game's graphics and handles user inputs.
 */
public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
	private FlappyGame flappyGame;

	/**
	 * Constructs a GamePanel with the specified game instance.
	 *
	 * @param flappyGame The FlappyGame instance this panel is part of.
	 */
	public GamePanel(FlappyGame flappyGame) {
		mouseInputs = new MouseInputs(this);
		this.flappyGame = flappyGame;
		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}

	/**
	 * Sets the preferred size of the panel based on the game's width and height.
	 */
	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setPreferredSize(size);
	}

	/**
	 * Updates the game state. Currently does not perform any operations.
	 */
	public void updateGame() {
		// Method to update the game state
	}

	/**
	 * Renders the game's graphics.
	 *
	 * @param g The Graphics object used to render the graphics.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		flappyGame.render(g);
	}

	/**
	 * Returns the FlappyGame instance associated with this panel.
	 *
	 * @return The FlappyGame instance.
	 */
	public FlappyGame getGame() {
		return flappyGame;
	}
}
