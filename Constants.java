public class Constants {

    public static class PlayerConstants {
        // public static final String NAME = "Player";
        public static final int FALLING = 1;
        public static final int FLYING = 2;
        public static final int DEATH = 3;

        // This method returns player count for each row of the png pixel image in case there are
        // two-dimensional rows of pictures with more details of flying falling and dying.
        public static int GetSpriteCount(int player_action) {
            switch (player_action) {
                case FALLING:
                    return 8;
                case FLYING:
                    return 8;
                case DEATH:
                    return 8;
                default:
                    return 8;
            }
        }
    }
}
