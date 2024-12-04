package entities;

import static utils.Constants.EnemyConstants.*;
import static utils.HelpMethods.IsFloor;
import static utils.Constants.Dialogue.*;

import gamestates.Playing;

/**
 * Represents the Crabby enemy type in the game, handling its behavior and animations.
 */
public class Crabby extends Enemy {

	/**
	 * Constructs a new Crabby enemy with specified initial position.
	 *
	 * @param x the initial x-coordinate.
	 * @param y the initial y-coordinate.
	 */
	public Crabby(float x, float y) {
		super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
		initHitbox(22, 19);
		initAttackBox(82, 19, 30);
	}

	/**
	 * Updates the Crabby's behavior, animation, and attack box based on the level data and game state.
	 *
	 * @param lvlData the level data containing environmental information.
	 * @param playing the current game state.
	 */
	public void update(int[][] lvlData, Playing playing) {
		updateBehavior(lvlData, playing);
		updateAnimationTick();
		updateAttackBox();
	}

	/**
	 * Controls the behavior of Crabby based on its state and the level data.
	 *
	 * @param lvlData the level data for the current frame.
	 * @param playing the current game state managing the player and other entities.
	 */
	private void updateBehavior(int[][] lvlData, Playing playing) {
		if (firstUpdate)
			firstUpdateCheck(lvlData);

		if (inAir) {
			inAirChecks(lvlData, playing);
		} else {
			switch (state) {
				case IDLE:
					if (IsFloor(hitbox, lvlData))
						newState(RUNNING);
					else
						inAir = true;
					break;
				case RUNNING:
					if (canSeePlayer(lvlData, playing.getPlayer())) {
						turnTowardsPlayer(playing.getPlayer());
						if (isPlayerCloseForAttack(playing.getPlayer()))
							newState(ATTACK);
					}
					move(lvlData);

					if (inAir)
						playing.addDialogue((int) hitbox.x, (int) hitbox.y, EXCLAMATION);

					break;
				case ATTACK:
					if (aniIndex == 0)
						attackChecked = false;
					if (aniIndex == 3 && !attackChecked)
						checkPlayerHit(attackBox, playing.getPlayer());
					break;
				case HIT:
					if (aniIndex <= GetSpriteAmount(enemyType, state) - 2)
						pushBack(pushBackDir, lvlData, 2f);
					updatePushBackDrawOffset();
					break;
			}
		}
	}

}
