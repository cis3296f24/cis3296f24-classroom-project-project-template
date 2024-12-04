package objects;

import static utils.Constants.ANI_SPEED;
import static utils.Constants.ObjectConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.FlappyGame;

/**
 * The GameObject class represents a game element with position, type, and animation
 * capabilities. It manages its hitbox, activity state, and draw offsets.
 */
public class GameObject {

	protected int x, y, objType;
	protected Rectangle2D.Float hitbox;
	protected boolean doAnimation, active = true;
	protected int aniTick, aniIndex;
	protected int xDrawOffset, yDrawOffset;

	/**
	 * Constructs a GameObject with specified position and type.
	 *
	 * @param x       The x-coordinate of the object.
	 * @param y       The y-coordinate of the object.
	 * @param objType The type of the object.
	 */
	public GameObject(int x, int y, int objType) {
		this.x = x;
		this.y = y;
		this.objType = objType;
	}

	/**
	 * Updates the animation tick, and resets animation index if needed.
	 */
	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= ANI_SPEED) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(objType)) {
				aniIndex = 0;
				if (objType == BARREL || objType == BOX) {
					doAnimation = false;
					active = false;
				} else if (objType == CANNON_LEFT || objType == CANNON_RIGHT)
					doAnimation = false;
			}
		}
	}

	/**
	 * Resets the object's animation and activation state.
	 */
	public void reset() {
		aniIndex = 0;
		aniTick = 0;
		active = true;

		if (objType == BARREL || objType == BOX || objType == CANNON_LEFT || objType == CANNON_RIGHT)
			doAnimation = false;
		else
			doAnimation = true;
	}

	/**
	 * Initializes the hitbox for the object using specified dimensions.
	 *
	 * @param width  The width of the hitbox.
	 * @param height The height of the hitbox.
	 */
	protected void initHitbox(int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, (int) (width * FlappyGame.SCALE), (int) (height * FlappyGame.SCALE));
	}

	/**
	 * Draws the hitbox on the specified graphics context.
	 *
	 * @param g          The Graphics context.
	 * @param xLvlOffset The x-level offset for drawing.
	 */
	public void drawHitbox(Graphics g, int xLvlOffset) {
		g.setColor(Color.PINK);
		g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}

	/**
	 * Retrieves the object's type.
	 *
	 * @return The type of the object.
	 */
	public int getObjType() {
		return objType;
	}

	/**
	 * Retrieves the hitbox of the object.
	 *
	 * @return The hitbox of the object.
	 */
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	/**
	 * Checks if the object is active.
	 *
	 * @return True if the object is active; otherwise, false.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the active state of the object.
	 *
	 * @param active The active state to set.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Sets the animation state of the object.
	 *
	 * @param doAnimation Whether the object should animate.
	 */
	public void setAnimation(boolean doAnimation) {
		this.doAnimation = doAnimation;
	}

	/**
	 * Gets the x-draw offset.
	 *
	 * @return The x-draw offset.
	 */
	public int getxDrawOffset() {
		return xDrawOffset;
	}

	/**
	 * Gets the y-draw offset.
	 *
	 * @return The y-draw offset.
	 */
	public int getyDrawOffset() {
		return yDrawOffset;
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
	 * Gets the current animation tick.
	 *
	 * @return The current animation tick.
	 */
	public int getAniTick() {
		return aniTick;
	}
}
