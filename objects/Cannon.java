package objects;

import main.FlappyGame;

/**
 * The Cannon class represents a cannon object in the game, which can animate
 * and interact with the player based on their tile position.
 */
public class Cannon extends GameObject {

	private int tileY;

	/**
	 * Constructs a Cannon object with a specified position and type.
	 *
	 * @param x       The x-coordinate of the cannon.
	 * @param y       The y-coordinate of the cannon.
	 * @param objType The type of the game object.
	 */
	public Cannon(int x, int y, int objType) {
		super(x, y, objType);
		tileY = y / FlappyGame.TILES_SIZE;
		initHitbox(40, 26);
		//		hitbox.x -= (int) (1 * FlappyGame.SCALE);
		hitbox.y += (int) (6 * FlappyGame.SCALE);
	}

	/**
	 * Updates the cannon's state, progressing its animation if it's active.
	 */
	public void update() {
		if (doAnimation)
			updateAnimationTick();
	}

	/**
	 * Retrieves the y-coordinate tile position of the cannon.
	 *
	 * @return The y-coordinate tile position of the cannon.
	 */
	public int getTileY() {
		return tileY;
	}
}
