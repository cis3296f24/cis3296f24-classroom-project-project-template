package entities;

import static utils.Constants.EnemyConstants.*;
import static utils.Constants.Dialogue.*;
import static utils.HelpMethods.CanMoveHere;
import static utils.HelpMethods.IsFloor;
import static utils.Constants.Directions.*;

import gamestates.Playing;

/**
 * Represents a Pinkstar enemy in the game, with its unique behaviors and interactions.
 */
public class Pinkstar extends Enemy {
	private boolean preRoll = true;
	private int tickSinceLastDmgToPlayer;
	private int tickAfterRollInIdle;
	private int rollDurationTick, rollDuration = 300;

	/**
	 * Constructs a Pinkstar enemy with specified initial position.
	 *
	 * @param x The initial x-coordinate.
	 * @param y The initial y-coordinate.
	 */
	public Pinkstar(float x, float y) {
		super(x, y, PINKSTAR_WIDTH, PINKSTAR_HEIGHT, PINKSTAR);
		initHitbox(17, 21);
	}

	/**
	 * Updates Pinkstar's behavior and animation.
	 *
	 * @param lvlData The level data for the current frame.
	 * @param playing The current game state managing the player and other entities.
	 */
	public void update(int[][] lvlData, Playing playing) {
		updateBehavior(lvlData, playing);
		updateAnimationTick();
	}

	/**
	 * Updates Pinkstar's behavior based on its state and the level data.
	 *
	 * @param lvlData The level data for the current frame.
	 * @param playing The current game state managing the player and other entities.
	 */
	private void updateBehavior(int[][] lvlData, Playing playing) {
		if (firstUpdate)
			firstUpdateCheck(lvlData);

		if (inAir)
			inAirChecks(lvlData, playing);
		else {
			switch (state) {
				case IDLE:
					preRoll = true;
					if (tickAfterRollInIdle >= 120) {
						if (IsFloor(hitbox, lvlData))
							newState(RUNNING);
						else
							inAir = true;
						tickAfterRollInIdle = 0;
						tickSinceLastDmgToPlayer = 60;
					} else
						tickAfterRollInIdle++;
					break;
				case RUNNING:
					if (canSeePlayer(lvlData, playing.getPlayer())) {
						newState(ATTACK);
						setWalkDir(playing.getPlayer());
					}
					move(lvlData, playing);
					break;
				case ATTACK:
					if (preRoll) {
						if (aniIndex >= 3)
							preRoll = false;
					} else {
						move(lvlData, playing);
						checkDmgToPlayer(playing.getPlayer());
						checkRollOver(playing);
					}
					break;
				case HIT:
					if (aniIndex <= GetSpriteAmount(enemyType, state) - 2)
						pushBack(pushBackDir, lvlData, 2f);
					updatePushBackDrawOffset();
					tickAfterRollInIdle = 120;

					break;
			}
		}
	}

	/**
	 * Checks if the Pinkstar's attack hits the player and applies damage.
	 *
	 * @param player The player character.
	 */
	private void checkDmgToPlayer(Player player) {
		if (hitbox.intersects(player.getHitbox()))
			if (tickSinceLastDmgToPlayer >= 60) {
				tickSinceLastDmgToPlayer = 0;
				player.changeHealth(-GetEnemyDmg(enemyType), this);
			} else
				tickSinceLastDmgToPlayer++;
	}

	/**
	 * Sets the walk direction of Pinkstar towards the player.
	 *
	 * @param player The player character being tracked.
	 */
	private void setWalkDir(Player player) {
		if (player.getHitbox().x > hitbox.x)
			walkDir = RIGHT;
		else
			walkDir = LEFT;

	}

	/**
	 * Moves the Pinkstar based on its current state and walking direction.
	 *
	 * @param lvlData The level data for the current frame.
	 * @param playing The current game state managing the player and other entities.
	 */
	protected void move(int[][] lvlData, Playing playing) {
		float xSpeed = 0;

		if (walkDir == LEFT)
			xSpeed = -walkSpeed;
		else
			xSpeed = walkSpeed;

		if (state == ATTACK)
			xSpeed *= 2;

		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
			if (IsFloor(hitbox, xSpeed, lvlData)) {
				hitbox.x += xSpeed;
				return;
			}

		if (state == ATTACK) {
			rollOver(playing);
			rollDurationTick = 0;
		}

		changeWalkDir();
	}

	/**
	 * Updates the roll over state of the Pinkstar and handles dialogue display.
	 *
	 * @param playing The current game state managing the player and other entities.
	 */
	private void checkRollOver(Playing playing) {
		rollDurationTick++;
		if (rollDurationTick >= rollDuration) {
			rollOver(playing);
			rollDurationTick = 0;
		}
	}

	/**
	 * Resets the Pinkstar to IDLE state and adds dialogue to the game.
	 *
	 * @param playing The current game state managing the player and other entities.
	 */
	private void rollOver(Playing playing) {
		newState(IDLE);
		playing.addDialogue((int) hitbox.x, (int) hitbox.y, QUESTION);
	}

}
