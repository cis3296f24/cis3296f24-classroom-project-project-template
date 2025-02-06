package objects;

import java.util.Random;

/**
 * The Bird class represents a bird in the Flappy Bird game that moves
 * vertically under the influence of gravity.
 */
public class BackgroundTree {

	private int x, y, type, aniIndex, aniTick;

	/**
	 * Constructor to initialize a BackgroundTree object.
	 * aniIndex is set to a random value to create variation in tree animation.
	 *
	 * @param x The initial x-coordinate of the tree.
	 * @param y The initial y-coordinate of the tree.
	 * @param type The type of the tree.
	 */
	public BackgroundTree(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;

		Random r = new Random();
		aniIndex = r.nextInt(4);
	}

	/**
	 * Updates the animation tick and index for the tree.
	 * Resets the index if it exceeds the frame limit.
	 */
	public void update() {
		aniTick++;
		if (aniTick >= 35) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= 4)
				aniIndex = 0;
		}
	}

	/** Returns the animation index from the sprite sheet
	 *  for example if it's the attack pic or die pic etc.
	 * @return The current animation index of the tree.
	 */
	public int getAniIndex() {
		return aniIndex;
	}

	/**
	 * Sets the animation index for the tree.
	 *
	 * @param aniIndex The animation index to be set.
	 */
	public void setAniIndex(int aniIndex) {
		this.aniIndex = aniIndex;
	}

	/** Returns the x value.
	 * @return The current x-coordinate of the tree.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x-coordinate for the tree.
	 *
	 * @param x The x-coordinate to be set.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/** Returns the y Value
	 * @return The current y-coordinate of the tree.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y-coordinate for the tree.
	 *
	 * @param y The y-coordinate to be set.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/** Returns the type
	 * @return The type of the tree.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Sets the type of the tree.
	 *
	 * @param type The type to be set.
	 */
	public void setType(int type) {
		this.type = type;
	}
}
