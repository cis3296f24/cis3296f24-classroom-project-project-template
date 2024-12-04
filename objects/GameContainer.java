package objects;

import static utils.Constants.ObjectConstants.*;

import main.FlappyGame;

/**
 * Represents a game container object within the game.
 * A GameContainer has an x and y coordinate, and a specific object type.
 */
public class GameContainer extends GameObject {

	/**
	 * Constructs a new GameContainer object.
	 *
	 * @param x       The x-coordinate of the GameContainer.
	 * @param y       The y-coordinate of the GameContainer.
	 * @param objType The type of the object (e.g., BOX).
	 */
	public GameContainer(int x, int y, int objType) {
		super(x, y, objType);
		createHitbox();
	}

	/**
	 * Creates the hitbox for the GameContainer based on the object type.
	 */
	private void createHitbox() {
		if (objType == BOX) {
			initHitbox(25, 18);
			xDrawOffset = (int) (7 * FlappyGame.SCALE);
			yDrawOffset = (int) (12 * FlappyGame.SCALE);
		} else {
			initHitbox(23, 25);
			xDrawOffset = (int) (8 * FlappyGame.SCALE);
			yDrawOffset = (int) (5 * FlappyGame.SCALE);
		}

		hitbox.y += yDrawOffset + (int) (FlappyGame.SCALE * 2);
		hitbox.x += (float) xDrawOffset / 2;
	}

	/**
	 * Updates the GameContainer, advancing its animation if applicable.
	 */
	public void update() {
		if (doAnimation) {
			updateAnimationTick();
		}
	}
}
