package effects;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import main.FlappyGame;
import utils.LoadSave;

/**
 * Represents the rain effect in the game, generating and updating rain particles.
 * Note: Adding particles in this manner can be resource-intensive.
 */
public class Rain {

	private Point2D.Float[] drops;
	private Random rand;
	private float rainSpeed = 1.25f;
	private BufferedImage rainParticle;

	/**
	 * Initializes a new Rain effect with a certain number of drops.
	 * Loads the rain particle sprite.
	 */
	public Rain() {
		rand = new Random();
		drops = new Point2D.Float[1000];
		rainParticle = LoadSave.GetSpriteAtlas(LoadSave.RAIN_PARTICLE);
		initDrops();
	}

	/**
	 * Initializes the drops at random positions within the game height.
	 */
	private void initDrops() {
		for (int i = 0; i < drops.length; i++)
			drops[i] = getRndPos();
	}

	/**
	 * Generates a random position for a rain drop within the game height.
	 *
	 * @return A random Point2D.Float position for a drop.
	 */
	private Point2D.Float getRndPos() {
		return new Point2D.Float((int) getNewX(0), rand.nextInt(FlappyGame.GAME_HEIGHT));
	}

	/**
	 * Updates the position of each drop based on the rain speed.
	 * Resets the position of drops that exceed the game height.
	 *
	 * @param xLvlOffset The horizontal level offset affecting drop positions.
	 */
	public void update(int xLvlOffset) {
		for (Point2D.Float p : drops) {
			p.y += rainSpeed;
			if (p.y >= FlappyGame.GAME_HEIGHT) {
				p.y = -20;
				p.x = getNewX(xLvlOffset);
			}
		}
	}

	/**
	 * Calculates a new horizontal position for a drop, offset by the game level.
	 *
	 * @param xLvlOffset The horizontal level offset affecting drop positions.
	 * @return A new x-coordinate for the drop.
	 */
	private float getNewX(int xLvlOffset) {
		float value = (-FlappyGame.GAME_WIDTH) + rand.nextInt((int) (FlappyGame.GAME_WIDTH * 3f)) + xLvlOffset;
		return value;
	}

	/**
	 * Draws the rain drops onto the provided Graphics context.
	 *
	 * @param g          The Graphics context to draw onto.
	 * @param xLvlOffset The horizontal level offset affecting draw positions.
	 */
	public void draw(Graphics g, int xLvlOffset) {
		for (Point2D.Float p : drops)
			g.drawImage(rainParticle, (int) p.getX() - xLvlOffset, (int) p.getY(), 3, 12, null);
	}
}
