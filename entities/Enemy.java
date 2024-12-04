package entities;

import static utils.Constants.EnemyConstants.*;
import static utils.HelpMethods.*;

import java.awt.geom.Rectangle2D;

import gamestates.Playing;

import static utils.Constants.Directions.*;
import static utils.Constants.*;

import main.FlappyGame;

/**
 * Represents an enemy character in the game, defining common behaviors and attributes.
 */
public abstract class Enemy extends Entity {
	protected int enemyType;
	protected boolean firstUpdate = true;
	protected int walkDir = LEFT;
	protected int tileY;
	protected float attackDistance = FlappyGame.TILES_SIZE;
	protected boolean active = true;
	protected boolean attackChecked;
	protected int attackBoxOffsetX;

	/**
	 * Constructs an Enemy with specified position, size, and type.
	 *
	 * @param x the initial x-coordinate of the enemy.
	 * @param y the initial y-coordinate of the enemy.
	 * @param width the width of the enemy.
	 * @param height the height of the enemy.
	 * @param enemyType the type of the enemy.
	 */
	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;

		maxHealth = GetMaxHealth(enemyType);
		currentHealth = maxHealth;
		walkSpeed = FlappyGame.SCALE * 0.35f;
	}

	/**
	 * Updates the position of the enemy's attack box based on its hit box.
	 */
	protected void updateAttackBox() {
		attackBox.x = hitbox.x - attackBoxOffsetX;
		attackBox.y = hitbox.y;
	}

	/**
	 * Updates the position of the attack box considering the direction of walking.
	 */
	protected void updateAttackBoxFlip() {
		if (walkDir == RIGHT)
			attackBox.x = hitbox.x + hitbox.width;
		else
			attackBox.x = hitbox.x - attackBoxOffsetX;

		attackBox.y = hitbox.y;
	}

	/**
	 * Initializes the attack box of the enemy with specified dimensions and offset.
	 *
	 * @param w the width of the attack box.
	 * @param h the height of the attack box.
	 * @param attackBoxOffsetX the x offset for the attack box.
	 */
	protected void initAttackBox(int w, int h, int attackBoxOffsetX) {
		attackBox = new Rectangle2D.Float(x, y, (int) (w * FlappyGame.SCALE), (int) (h * FlappyGame.SCALE));
		this.attackBoxOffsetX = (int) (FlappyGame.SCALE * attackBoxOffsetX);
	}

	/**
	 * Performs the first update check to determine if the enemy is in the air.
	 *
	 * @param lvlData the level data providing the map's layout.
	 */
	protected void firstUpdateCheck(int[][] lvlData) {
		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
		firstUpdate = false;
	}

	/**
	 * Performs checks to update the enemy's state when it is in the air.
	 *
	 * @param lvlData the level data providing the map's layout.
	 * @param playing the current game state.
	 */
	protected void inAirChecks(int[][] lvlData, Playing playing) {
		if (state != HIT && state != DEAD) {
			updateInAir(lvlData);
			playing.getObjectManager().checkSpikesTouched(this);
			if (IsEntityInWater(hitbox, lvlData))
				hurt(maxHealth);
		}
	}

	/**
	 * Updates the enemy's position when in the air.
	 *
	 * @param lvlData the level data providing the map's layout.
	 */
	protected void updateInAir(int[][] lvlData) {
		if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
			hitbox.y += airSpeed;
			airSpeed += GRAVITY;
		} else {
			inAir = false;
			hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
			tileY = (int) (hitbox.y / FlappyGame.TILES_SIZE);
		}
	}

	/**
	 * Moves the enemy according to its walking direction and environment.
	 *
	 * @param lvlData the level data providing the map's layout.
	 */
	protected void move(int[][] lvlData) {
		float xSpeed = 0;

		if (walkDir == LEFT)
			xSpeed = -walkSpeed;
		else
			xSpeed = walkSpeed;

		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
			if (IsFloor(hitbox, xSpeed, lvlData)) {
				hitbox.x += xSpeed;
				return;
			}

		changeWalkDir();
	}

	/**
	 * Adjusts the enemy's walking direction to face the player.
	 *
	 * @param player the player character being tracked.
	 */
	protected void turnTowardsPlayer(Player player) {
		if (player.hitbox.x > hitbox.x)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}

	/**
	 * Determines if the enemy can see the player.
	 *
	 * @param lvlData the level data providing the map's layout.
	 * @param player the player character being tracked.
	 * @return true if the player is visible to the enemy; false otherwise.
	 */
	protected boolean canSeePlayer(int[][] lvlData, Player player) {
		int playerTileY = (int) (player.getHitbox().y / FlappyGame.TILES_SIZE);
		if (playerTileY == tileY)
			if (isPlayerInRange(player)) {
				if (IsSightClear(lvlData, hitbox, player.hitbox, tileY))
					return true;
			}
		return false;
	}

	/**
	 * Checks if the player is within the enemy's range.
	 *
	 * @param player the player character.
	 * @return true if the player is in range; false otherwise.
	 */
	protected boolean isPlayerInRange(Player player) {
		int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
		return absValue <= attackDistance * 5;
	}

	/**
	 * Checks if the player is close enough for the enemy to attack.
	 *
	 * @param player the player character.
	 * @return true if the player is close enough for an attack; false otherwise.
	 */
	protected boolean isPlayerCloseForAttack(Player player) {
		int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
		switch (enemyType) {
			case CRABBY -> {
				return absValue <= attackDistance;
			}
			case SHARK -> {
				return absValue <= attackDistance * 2;
			}
		}
		return false;
	}

	/**
	 * Reduces the enemy's health by a specified amount and updates its state.
	 *
	 * @param amount the amount of damage taken.
	 */
	public void hurt(int amount) {
		currentHealth -= amount;
		if (currentHealth <= 0)
			newState(DEAD);
		else {
			newState(HIT);
			if (walkDir == LEFT)
				pushBackDir = RIGHT;
			else
				pushBackDir = LEFT;
			pushBackOffsetDir = UP;
			pushDrawOffset = 0;
		}
	}

	/**
	 * Checks if the enemy's attack box has hit the player.
	 *
	 * @param attackBox the attack box of the enemy.
	 * @param player the player character.
	 */
	protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
		if (attackBox.intersects(player.hitbox))
			player.changeHealth(-GetEnemyDmg(enemyType), this);
		else {
			if (enemyType == SHARK)
				return;
		}
		attackChecked = true;
	}

	/**
	 * Updates the animation tick for the enemy's sprite animations.
	 */
	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= ANI_SPEED) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(enemyType, state)) {
				if (enemyType == CRABBY || enemyType == SHARK) {
					aniIndex = 0;

					switch (state) {
						case ATTACK, HIT -> state = IDLE;
						case DEAD -> active = false;
					}
				} else if (enemyType == PINKSTAR) {
					if (state == ATTACK)
						aniIndex = 3;
					else {
						aniIndex = 0;
						if (state == HIT) {
							state = IDLE;

						} else if (state == DEAD)
							active = false;
					}
				}
			}
		}
	}

	/**
	 * Changes the walking direction of the enemy.
	 */
	protected void changeWalkDir() {
		if (walkDir == LEFT)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}

	/**
	 * Resets the enemy to its initial state.
	 */
	public void resetEnemy() {
		hitbox.x = x;
		hitbox.y = y;
		firstUpdate = true;
		currentHealth = maxHealth;
		newState(IDLE);
		active = true;
		airSpeed = 0;

		pushDrawOffset = 0;

	}

	/**
	 * Computes the x offset for the enemy's sprite based on its walking direction.
	 *
	 * @return the x offset for rendering the enemy's sprite.
	 */
	public int flipX() {
		if (walkDir == RIGHT)
			return width;
		else
			return 0;
	}

	/**
	 * Computes the width multiplier for the enemy's sprite based on its walking direction.
	 *
	 * @return -1 if walking right; 1 if walking left.
	 */
	public int flipW() {
		if (walkDir == RIGHT)
			return -1;
		else
			return 1;
	}

	/**
	 * Checks if the enemy is active.
	 *
	 * @return true if the enemy is active; false otherwise.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Retrieves the push draw offset for the enemy.
	 *
	 * @return the push draw offset.
	 */
	public float getPushDrawOffset() {
		return pushDrawOffset;
	}
}
