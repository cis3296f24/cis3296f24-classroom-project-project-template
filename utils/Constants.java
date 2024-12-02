/**
 * The Constants class holds various game-related constants used throughout the
 * Flappy Bird game. These constants include parameters for gravity, animation
 * speed, dialogue dimensions, projectile sizes, enemy attributes, and more.
 */
package utils;

import main.FlappyGame;

public class Constants {

	/** The gravitational force applied to objects in the game. */
	public static final float GRAVITY = 0.04f * FlappyGame.SCALE;

	/** The speed of animations within the game. */
	public static final int ANI_SPEED = 25;

	/**
	 * Contains constants related to dialogue display, including types and
	 * dimensions.
	 */
	public static class Dialogue {

		/** The dialogue type representing a question. */
		public static final int QUESTION = 0;

		/** The dialogue type representing an exclamation. */
		public static final int EXCLAMATION = 1;

		/** The width of the dialogue boxes. */
		public static final int DIALOGUE_WIDTH = (int) (14 * FlappyGame.SCALE);

		/** The height of the dialogue boxes. */
		public static final int DIALOGUE_HEIGHT = (int) (12 * FlappyGame.SCALE);

		/**
		 * Retrieves the amount of sprites for the specified dialogue type.
		 *
		 * @param type The type of dialogue (QUESTION or EXCLAMATION).
		 * @return The number of sprites for the dialogue type.
		 */
		public static int GetSpriteAmount(int type) {
			switch (type) {
				case QUESTION, EXCLAMATION:
					return 5;
			}
			return 0;
		}
	}

	/**
	 * Contains constants related to projectiles, such as sizes and speed.
	 */
	public static class Projectiles {

		/** Default width of a cannonball. */
		public static final int CANNON_BALL_DEFAULT_WIDTH = 15;

		/** Default height of a cannonball. */
		public static final int CANNON_BALL_DEFAULT_HEIGHT = 15;

		/** Scaled width of a cannonball. */
		public static final int CANNON_BALL_WIDTH = (int) (FlappyGame.SCALE * CANNON_BALL_DEFAULT_WIDTH);

		/** Scaled height of a cannonball. */
		public static final int CANNON_BALL_HEIGHT = (int) (FlappyGame.SCALE * CANNON_BALL_DEFAULT_HEIGHT);

		/** The speed of the projectiles. */
		public static final float SPEED = 0.75f * FlappyGame.SCALE;
	}

	/**
	 * Contains constants for various objects within the game, such as potions,
	 * containers, and spikes.
	 */
	public static class ObjectConstants {

		/** Identifier for a red potion. */
		public static final int RED_POTION = 0;

		/** Identifier for a blue potion. */
		public static final int BLUE_POTION = 1;

		/** Identifier for a barrel object. */
		public static final int BARREL = 2;

		/** Identifier for a box object. */
		public static final int BOX = 3;

		/** Identifier for a spike trap. */
		public static final int SPIKE = 4;

		/** Identifier for a left-facing cannon. */
		public static final int CANNON_LEFT = 5;

		/** Identifier for a right-facing cannon. */
		public static final int CANNON_RIGHT = 6;

		/** Identifier for the first type of tree. */
		public static final int TREE_ONE = 7;

		/** Identifier for the second type of tree. */
		public static final int TREE_TWO = 8;

		/** Identifier for the third type of tree. */
		public static final int TREE_THREE = 9;

		/** The value provided by consuming a red potion. */
		public static final int RED_POTION_VALUE = 15;

		/** The value provided by consuming a blue potion. */
		public static final int BLUE_POTION_VALUE = 10;

		/** Default width of a container object. */
		public static final int CONTAINER_WIDTH_DEFAULT = 40;

		/** Default height of a container object. */
		public static final int CONTAINER_HEIGHT_DEFAULT = 30;

		/** Scaled width of a container object. */
		public static final int CONTAINER_WIDTH = (int) (FlappyGame.SCALE * CONTAINER_WIDTH_DEFAULT);

		/** Scaled height of a container object. */
		public static final int CONTAINER_HEIGHT = (int) (FlappyGame.SCALE * CONTAINER_HEIGHT_DEFAULT);

		/** Default width of a potion object. */
		public static final int POTION_WIDTH_DEFAULT = 12;

		/** Default height of a potion object. */
		public static final int POTION_HEIGHT_DEFAULT = 16;

		/** Scaled width of a potion object. */
		public static final int POTION_WIDTH = (int) (FlappyGame.SCALE * POTION_WIDTH_DEFAULT);

		/** Scaled height of a potion object. */
		public static final int POTION_HEIGHT = (int) (FlappyGame.SCALE * POTION_HEIGHT_DEFAULT);

		/** Default width of a spike object. */
		public static final int SPIKE_WIDTH_DEFAULT = 32;

		/** Default height of a spike object. **/
		public static final int SPIKE_HEIGHT_DEFAULT = 32;

		/** Scaled width of a spike object. */
		public static final int SPIKE_WIDTH = (int) (FlappyGame.SCALE * SPIKE_WIDTH_DEFAULT);

		/** Scaled height of a spike object. */
		public static final int SPIKE_HEIGHT = (int) (FlappyGame.SCALE * SPIKE_HEIGHT_DEFAULT);

		/** Default width of a cannon object. */
		public static final int CANNON_WIDTH_DEFAULT = 40;

		/** Default height of a cannon object. */
		public static final int CANNON_HEIGHT_DEFAULT = 26;

		/** Scaled width of a cannon object. */
		public static final int CANNON_WIDTH = (int) (CANNON_WIDTH_DEFAULT * FlappyGame.SCALE);

		/** Scaled height of a cannon object. */
		public static final int CANNON_HEIGHT = (int) (CANNON_HEIGHT_DEFAULT * FlappyGame.SCALE);

		/**
		 * Retrieves the amount of sprites for the specified object type.
		 *
		 * @param object_type The type of the object.
		 * @return The number of sprites for the object type.
		 */
		public static int GetSpriteAmount(int object_type) {
			switch (object_type) {
				case RED_POTION, BLUE_POTION:
					return 7;
				case BARREL, BOX:
					return 8;
				case CANNON_LEFT, CANNON_RIGHT:
					return 7;
			}
			return 1;
		}

		/**
		 * Retrieves the offset in the x-axis for tree rendering based on the tree
		 * type.
		 *
		 * @param treeType The type of tree.
		 * @return The x-axis offset for the tree type.
		 */
		public static int GetTreeOffsetX(int treeType) {
			switch (treeType) {
				case TREE_ONE:
					return (FlappyGame.TILES_SIZE / 2) - (GetTreeWidth(treeType) / 2);
				case TREE_TWO:
					return (int) (FlappyGame.TILES_SIZE / 2.5f);
				case TREE_THREE:
					return (int) (FlappyGame.TILES_SIZE / 1.65f);
			}

			return 0;
		}

		/**
		 * Retrieves the offset in the y-axis for tree rendering based on the tree
		 * type.
		 *
		 * @param treeType The type of tree.
		 * @return The y-axis offset for the tree type.
		 */
		public static int GetTreeOffsetY(int treeType) {
			switch (treeType) {
				case TREE_ONE:
					return -GetTreeHeight(treeType) + FlappyGame.TILES_SIZE * 2;
				case TREE_TWO, TREE_THREE:
					return -GetTreeHeight(treeType) + (int) (FlappyGame.TILES_SIZE / 1.25f);
			}
			return 0;
		}

		/**
		 * Retrieves the width of the specified tree type.
		 *
		 * @param treeType The type of tree.
		 * @return The width of the tree.
		 */
		public static int GetTreeWidth(int treeType) {
			switch (treeType) {
				case TREE_ONE:
					return (int) (39 * FlappyGame.SCALE);
				case TREE_TWO:
					return (int) (62 * FlappyGame.SCALE);
				case TREE_THREE:
					return -(int) (62 * FlappyGame.SCALE);
			}
			return 0;
		}

		/**
		 * Retrieves the height of the specified tree type.
		 *
		 * @param treeType The type of tree.
		 * @return The height of the tree.
		 */
		public static int GetTreeHeight(int treeType) {
			switch (treeType) {
				case TREE_ONE:
					return (int) (92 * FlappyGame.SCALE);
				case TREE_TWO, TREE_THREE:
					return (int) (54 * FlappyGame.SCALE);
			}
			return 0;
		}
	}

	/**
	 * Contains constants related to various enemy attributes, including
	 * dimensions, states, and damage points.
	 */
	public static class EnemyConstants {

		/** Identifier for the Crabby enemy type. */
		public static final int CRABBY = 0;

		/** Identifier for the Pinkstar enemy type. **/
		public static final int PINKSTAR = 1;

		/** Identifier for the Shark enemy type. */
		public static final int SHARK = 2;

		/** State representing an idle enemy. */
		public static final int IDLE = 0;

		/** State representing an enemy in motion. */
		public static final int RUNNING = 1;

		/** State representing an enemy attack. */
		public static final int ATTACK = 2;

		/** State representing an enemy being hit. */
		public static final int HIT = 3;

		/** State representing a defeated enemy. */
		public static final int DEAD = 4;

		/** Default width of a Crabby enemy. */
		public static final int CRABBY_WIDTH_DEFAULT = 72;

		/** Default height of a Crabby enemy. */
		public static final int CRABBY_HEIGHT_DEFAULT = 32;

		/** Scaled width of a Crabby enemy. */
		public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * FlappyGame.SCALE);

		/** Scaled height of a Crabby enemy. */
		public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * FlappyGame.SCALE);

		/** X-axis draw offset for a Crabby enemy. */
		public static final int CRABBY_DRAWOFFSET_X = (int) (26 * FlappyGame.SCALE);

		/** Y-axis draw offset for a Crabby enemy. */
		public static final int CRABBY_DRAWOFFSET_Y = (int) (9 * FlappyGame.SCALE);

		/** Default width of a Pinkstar enemy. */
		public static final int PINKSTAR_WIDTH_DEFAULT = 34;

		/** Default height of a Pinkstar enemy. */
		public static final int PINKSTAR_HEIGHT_DEFAULT = 30;

		/** Scaled width of a Pinkstar enemy. */
		public static final int PINKSTAR_WIDTH = (int) (PINKSTAR_WIDTH_DEFAULT * FlappyGame.SCALE);

		/** Scaled height of a Pinkstar enemy. */
		public static final int PINKSTAR_HEIGHT = (int) (PINKSTAR_HEIGHT_DEFAULT * FlappyGame.SCALE);

		/** X-axis draw offset for a Pinkstar enemy. */
		public static final int PINKSTAR_DRAWOFFSET_X = (int) (9 * FlappyGame.SCALE);

		/** Y-axis draw offset for a Pinkstar enemy. */
		public static final int PINKSTAR_DRAWOFFSET_Y = (int) (7 * FlappyGame.SCALE);

		/** Default width of a Shark enemy. */
		public static final int SHARK_WIDTH_DEFAULT = 34;

		/** Default height of a Shark enemy. */
		public static final int SHARK_HEIGHT_DEFAULT = 30;

		/** Scaled width of a Shark enemy. */
		public static final int SHARK_WIDTH = (int) (SHARK_WIDTH_DEFAULT * FlappyGame.SCALE);

		/** Scaled height of a Shark enemy. */
		public static final int SHARK_HEIGHT = (int) (SHARK_HEIGHT_DEFAULT * FlappyGame.SCALE);

		/** X-axis draw offset for a Shark enemy. */
		public static final int SHARK_DRAWOFFSET_X = (int) (8 * FlappyGame.SCALE);

		/** Y-axis draw offset for a Shark enemy. */
		public static final int SHARK_DRAWOFFSET_Y = (int) (6 * FlappyGame.SCALE);

		/**
		 * Retrieves the amount of sprites for the specified enemy type and state.
		 *
		 * @param enemy_type The type of enemy.
		 * @param enemy_state The state of the enemy.
		 * @return The number of sprites for the enemy type and state.
		 */
		public static int GetSpriteAmount(int enemy_type, int enemy_state) {
			switch (enemy_state) {
				case IDLE: {
					if (enemy_type == CRABBY)
						return 9;
					else if (enemy_type == PINKSTAR || enemy_type == SHARK)
						return 8;
				}
				case RUNNING:
					return 6;
				case ATTACK:
					if (enemy_type == SHARK)
						return 8;
					return 7;
				case HIT:
					return 4;
				case DEAD:
					return 5;
			}
			return 0;
		}

		/**
		 * Retrieves the maximum health for the specified enemy type.
		 *
		 * @param enemy_type The type of enemy.
		 * @return The maximum health of the enemy type.
		 */
		public static int GetMaxHealth(int enemy_type) {
			switch (enemy_type) {
				case CRABBY:
					return 50;
				case PINKSTAR, SHARK:
					return 25;
				default:
					return 1;
			}
		}

		/**
		 * Retrieves the damage dealt by the specified enemy type.
		 *
		 * @param enemy_type The type of enemy.
		 * @return The damage dealt by the enemy type.
		 */
		public static int GetEnemyDmg(int enemy_type) {
			switch (enemy_type) {
				case CRABBY:
					return 15;
				case PINKSTAR:
					return 20;
				case SHARK:
					return 25;
				default:
					return 0;
			}
		}
	}

	/**
	 * Contains constants related to the game environment, such as cloud
	 * dimensions.
	 */
	public static class Environment {

		/** Default width of a big cloud. */
		public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;

		/** Default height of a big cloud. */
		public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;

		/** Default width of a small cloud. */
		public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;

		/** Default height of a small cloud. */
		public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;

		/** Scaled width of a big cloud. */
		public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * FlappyGame.SCALE);

		/** Scaled height of a big cloud. */
		public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * FlappyGame.SCALE);

		/** Scaled width of a small cloud. */
		public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * FlappyGame.SCALE);

		/** Scaled height of a small cloud. */
		public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * FlappyGame.SCALE);
	}

	/**
	 * Contains constants related to the user interface components, such as
	 * button dimensions.
	 */
	public static class UI {
		public static class Buttons {
			/** Default width of a button. */
			public static final int B_WIDTH_DEFAULT = 140;

			/** Default height of a button. */
			public static final int B_HEIGHT_DEFAULT = 56;

			/** Scaled width of a button. */
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * FlappyGame.SCALE);

			/** Scaled height of a button. */
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * FlappyGame.SCALE);
		}

		public static class PauseButtons {
			/** Default size of a sound button. */
			public static final int SOUND_SIZE_DEFAULT = 42;

			/** Scaled size of a sound button. */
			public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * FlappyGame.SCALE);
		}

		public static class URMButtons {
			/** Default size of a URM button. */
			public static final int URM_DEFAULT_SIZE = 56;

			/** Scaled size of a URM button. */
			public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * FlappyGame.SCALE);
		}

		public static class VolumeButtons {
			/** Default width of a volume button. */
			public static final int VOLUME_DEFAULT_WIDTH = 28;

			/** Default height of a volume button. */
			public static final int VOLUME_DEFAULT_HEIGHT = 44;

			/** Default width of a slider. */
			public static final int SLIDER_DEFAULT_WIDTH = 215;

			/** Scaled width of a volume button. */
			public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * FlappyGame.SCALE);

			/** Scaled height of a volume button. */
			public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * FlappyGame.SCALE);

			/** Scaled width of a slider. */
			public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * FlappyGame.SCALE);
		}
	}

	/**
	 * Contains constants representing various direction states.
	 */
	public static class Directions {

		/** Represents the left direction. */
		public static final int LEFT = 0;

		/** Represents the upwards direction. */
		public static final int UP = 1;

		/** Represents the right direction. */
		public static final int RIGHT = 2;

		/** Represents the downward direction. */
		public static final int DOWN = 3;
	}

	/**
	 * Contains constants related to player states and actions.
	 */
	public static class PlayerConstants {

		/** Player state representing idle. */
		public static final int IDLE = 0;

		/** Player state representing running. */
		public static final int RUNNING = 1;

		/** Player state representing jumping. */
		public static final int JUMP = 2;

		/** Player state representing falling. */
		public static final int FALLING = 3;

		/** Player state representing an attack. */
		public static final int ATTACK = 4;

		/** Player state representing being hit. */
		public static final int HIT = 5;

		/** Player state representing being dead. */
		public static final int DEAD = 6;

		/** Player collision state. */
		public static final int COLLIDED = 0;
	}
}
