package entities;

import static utils.Constants.Directions.DOWN;
import static utils.Constants.Directions.LEFT;
import static utils.Constants.Directions.UP;
import static utils.HelpMethods.CanMoveHere;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.FlappyGame;

/**
 * The Entity class serves as a base class for all game entities,
 * encapsulating common properties such as position, health, and
 * hitboxes, as well as common behaviors.
 */
public abstract class Entity {

	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitbox;
	protected int aniTick, aniIndex;
	protected int state;
	protected float airSpeed;
	protected boolean inAir = false;
	protected int maxHealth;
	protected int currentHealth;
	protected Rectangle2D.Float attackBox;
	protected float walkSpeed;

	protected int pushBackDir;
	protected float pushDrawOffset;
	protected int pushBackOffsetDir = UP;

	/**
	 * Constructs an Entity with specified position and dimensions.
	 *
	 * @param x      the x-coordinate of the entity.
	 * @param y      the y-coordinate of the entity.
	 * @param width  the width of the entity.
	 * @param height the height of the entity.
	 */
	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Updates the draw offset used for rendering push-back effects.
	 * Adjusts direction when limits are reached.
	 */
	protected void updatePushBackDrawOffset() {
		float speed = 0.95f;
		float limit = -30f;

		if (pushBackOffsetDir == UP) {
			pushDrawOffset -= speed;
			if (pushDrawOffset <= limit)
				pushBackOffsetDir = DOWN;
		} else {
			pushDrawOffset += speed;
			if (pushDrawOffset >= 0)
				pushDrawOffset = 0;
		}
	}

	/**
	 * Moves the entity in the specified direction with a given speed multiplier,
	 * ensuring it doesn't move into invalid areas.
	 *
	 * @param pushBackDir the direction to push back the entity.
	 * @param lvlData     the level data array for collision detection.
	 * @param speedMulti  the speed multiplier for movement.
	 */
	protected void pushBack(int pushBackDir, int[][] lvlData, float speedMulti) {
		float xSpeed = 0;
		if (pushBackDir == LEFT)
			xSpeed = -walkSpeed;
		else
			xSpeed = walkSpeed;

		if (CanMoveHere(hitbox.x + xSpeed * speedMulti, hitbox.y, hitbox.width, hitbox.height, lvlData))
			hitbox.x += xSpeed * speedMulti;
	}

	/**
	 * Draws the entity's attack box on the specified graphics context.
	 *
	 * @param g         the Graphics context.
	 * @param xLvlOffset the x-level offset for drawing.
	 */
	protected void drawAttackBox(Graphics g, int xLvlOffset) {
		g.setColor(Color.red);
		g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
	}

	/**
	 * Draws the entity's hitbox on the specified graphics context.
	 *
	 * @param g         the Graphics context.
	 * @param xLvlOffset the x-level offset for drawing.
	 */
	protected void drawHitbox(Graphics g, int xLvlOffset) {
		g.setColor(Color.BLUE);
		g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}

	/**
	 * Initializes the hitbox for the entity using specified dimensions.
	 *
	 * @param width  the width of the hitbox.
	 * @param height the height of the hitbox.
	 */
	protected void initHitbox(int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, (int) (width * FlappyGame.SCALE), (int) (height * FlappyGame.SCALE));
	}

	/**
	 * Retrieves the hitbox of the entity.
	 *
	 * @return the hitbox as a Rectangle2D.Float object.
	 */
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	/**
	 * Retrieves the current state of the entity.
	 *
	 * @return the state as an integer.
	 */
	public int getState() {
		return state;
	}

	/**
	 * Retrieves the current animation index.
	 *
	 * @return the animation index as an integer.
	 */
	public int getAniIndex() {
		return aniIndex;
	}

	/**
	 * Sets the entity's state to the specified value and resets animation tick and index.
	 *
	 * @param state the new state to set for the entity.
	 */
	protected void newState(int state) {
		this.state = state;
		aniTick = 0;
		aniIndex = 0;
	}
}
