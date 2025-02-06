package effects;

import static utils.Constants.ANI_SPEED;
import static utils.Constants.Dialogue.*;

/**
 * Represents a dialogue effect with animation in a game.
 * Keeps track of its position, type, and animation state.
 */
public class DialogueEffect {

	private int x, y, type;
	private int aniIndex, aniTick;
	private boolean active = true;

	/**
	 * Constructs a DialogueEffect with specified position and type.
	 *
	 * @param x    The x-coordinate of the dialogue effect.
	 * @param y    The y-coordinate of the dialogue effect.
	 * @param type The type of the dialogue effect.
	 */
	public DialogueEffect(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}

	/**
	 * Updates the animation tick, and resets animation index if needed.
	 * Deactivates the effect if the animation sequence is complete.
	 */
	public void update() {
		aniTick++;
		if (aniTick >= ANI_SPEED) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(type)) {
				active = false;
				aniIndex = 0;
			}
		}
	}

	/**
	 * Deactivates the dialogue effect.
	 */
	public void deactive() {
		active = false;
	}

	/**
	 * Resets the position and activates the dialogue effect.
	 *
	 * @param x The new x-coordinate of the dialogue effect.
	 * @param y The new y-coordinate of the dialogue effect.
	 */
	public void reset(int x, int y) {
		this.x = x;
		this.y = y;
		active = true;
	}

	/**
	 * Gets the current animation index.
	 *
	 * @return The current animation index.
	 */
	public int getAniIndex() {
		return aniIndex;
	}

	/**
	 * Gets the x-coordinate of the dialogue effect.
	 *
	 * @return The x-coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y-coordinate of the dialogue effect.
	 *
	 * @return The y-coordinate.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Gets the type of the dialogue effect.
	 *
	 * @return The type of the dialogue effect.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Checks if the dialogue effect is active.
	 *
	 * @return True if active, otherwise false.
	 */
	public boolean isActive() {
		return active;
	}
}
