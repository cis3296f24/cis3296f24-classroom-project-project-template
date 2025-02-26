package utils;

import main.FlappyGame;

public class Constants {

    // Background and other scenery constants for Flappy Bird
    public static class FlappyWorldConstants {

        public static final int GROUND_HEIGHT_DEFAULT = 320; // Length of the grass or floor picture.
        public static final int GROUND_WIDTH_DEFAULT = 2048; // Height or width of the grass or floor picture.

        // The constants need to be scaled to the Flappy Game scale defined in FlappyGame.java
        // See the usage in Playing.java
        public static final int GROUND_HEIGHT = (int) (GROUND_HEIGHT_DEFAULT * FlappyGame.SCALE);
        public static final int GROUND_WIDTH = (int) (GROUND_WIDTH_DEFAULT * FlappyGame.SCALE);
    }

    public static class UI {
        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * FlappyGame.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * FlappyGame.SCALE);
        }

        public static class PauseButtons {
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * FlappyGame.SCALE);
        }

        public static class URMButtons {
            public static final int URM_DEFAULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * FlappyGame.SCALE);

        }

        public static class VolumeButtons {
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int SLIDER_DEFAULT_WIDTH = 215;

            public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * FlappyGame.SCALE);
            public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * FlappyGame.SCALE);
            public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * FlappyGame.SCALE);
        }
    }

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int GROUND = 4;
        public static final int HIT = 5;
        public static final int ATTACK_1 = 6;
        public static final int ATTACK_JUMP_1 = 7;
        public static final int ATTACK_JUMP_2 = 8;

        public static final int DEAD = 4;
        public static final int ANI_SPEED = 25;
        public static final int COLLIDED = 0;

        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case RUNNING:
                    return 6;
                case IDLE:
                    return 5;
                case HIT:
                    return 4;
                case JUMP:
                case ATTACK_1:
                case ATTACK_JUMP_1:
                case ATTACK_JUMP_2:
                    return 3;
                case GROUND:
                    return 2;
                case FALLING:
                default:
                    return 1;
            }
        }
    }

}