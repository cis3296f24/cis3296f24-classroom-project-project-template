package utils;

import java.awt.geom.Rectangle2D;

import main.FlappyGame;

public class HelpMethods {

    boolean birdScored = false;
    private static int birdScore = 0;
    private static int previousValue = 0;
    private static boolean birdEntered = false;
    private static boolean birdExited = true;


    public static boolean CanMoveHereNew(float x, float y, float width, float height, int[][] lvlData) {
        if (!IsSolid(x, y, lvlData))
            if (!IsSolid(x + width, y + height, lvlData))
                if (!IsSolid(x + width, y, lvlData))
                    if (!IsSolid(x, y + height, lvlData))
                        return true;
        return false;
    }

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        if (!IsSolid(x, y, lvlData))
            if (!IsSolid(x + width, y + height, lvlData))
                if (!IsSolid(x + width, y, lvlData))
                    if (!IsSolid(x, y + height, lvlData)) {
                        return true;
                    }
        return false;
    }

    public static boolean IsExiting(float x, float y, int[][] lvlData) {
       // System.out.println("x1, y1 =" + x1 + " " + y1);
        int value = lvlData[(int) y][(int) x];
        System.out.println("x    " + x + " y    " + y);

        //  int value2 = lvlData[(int) y1][((int) x1) - 1];
        //System.out.println("Value Exiting " + value + " Value2 x1" + x1);
        if (value == 23) {
            System.out.println("Entered exiting code if!");
            return true;
        }
//
//        if (((lvlData[(int) y1][(int) x1]) == 23) & ((lvlData[(int) y1][(int)(x1) + 1]) == 11)) {
//            return true;
//        }
        return false;
    }


    public static boolean IsSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * FlappyGame.TILE_SIZE;
        // if (x < 0 || x >= FlappyGame.GAME_WIDTH)
        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= FlappyGame.GAME_HEIGHT)
            return true;

        float xIndex = x / FlappyGame.TILE_SIZE;
        float yIndex = y / FlappyGame.TILE_SIZE;
        int value = lvlData[(int) yIndex][(int) xIndex];

        // System.out.println("xIndex = " + (int) xIndex + "  yIndex = " + (int)yIndex + " Tile Value = " + value);

//        System.out.println(" x, y = " + x + ", " + y + previousValue + " <--- Previous Value and lvlData value -----> " + value + "  birdScore  > " + birdScore);
        // This checks for bird entering and increments the score.
//        if ((previousValue == 0) & (lvlData[(int) yIndex][(int) xIndex] == 23) & (lvlData[(int) yIndex][(int) xIndex + FlappyGame.TILE_SIZE + 1] == 11)) {
//            System.out.println("Entered score loop ////////////////////////////////////////");
//            birdScore += 1;
//            System.out.println("birdScore: " + birdScore);
//            previousValue = 255; // nonzero value
//        }

//        if ((lvlData[(int)yIndex][(int)xIndex] == 23) & (lvlData[(int) yIndex][(int) xIndex + FlappyGame.TILE_SIZE + 1] == 11)) {
//            System.out.println("Entered score loop ////////////////////////////////////////");
//            birdScore += 1;
//            System.out.println("birdScore: " + birdScore);
//            previousValue = 255; // nonzero value
//        }

         if ((value == 23) & !(birdEntered) & (birdExited)) {
       // if ((lvlData[(int) yIndex][(int) xIndex] == 23)
         //       & (lvlData[(int) yIndex][(int) xIndex + 1] == 11 )
         //       & !(birdEntered) & !(birdExited) ) {
            birdEntered = true;
            birdExited = false;
            System.out.println("Bird entered                  <<<<<<<<<<    ");
            if ((lvlData[(int) yIndex][(int) xIndex] == 23 ) & (lvlData[(int) yIndex][(int) xIndex + 1] == 11  )) {
                birdExited = true; // If the bird reached the end and next tile is 11 before tile 23.
                // birdEntered = false;
                System.out.println("Bird is exiting after this ");
                // birdScore++;
            }
        }

        if ((value == 11) & (birdExited))  {
                birdEntered = false;
               // System.out.println("Entered second if statement !");
        }

        if (value == 23) {
            return false;
        }

        if (value >= 48 || value < 0 || value != 11) {
            return true;

          //  return true;
        }
        return false;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / FlappyGame.TILE_SIZE);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * FlappyGame.TILE_SIZE;
            int xOffset = (int) (FlappyGame.TILE_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else
            // Left
            return currentTile * FlappyGame.TILE_SIZE;
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / FlappyGame.TILE_SIZE);
        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * FlappyGame.TILE_SIZE;
            int yOffset = (int) (FlappyGame.TILE_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else
            // Jumping
            return currentTile * FlappyGame.TILE_SIZE;

    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        // Check the pixel below bottomleft and bottomright
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData)) {
                return false;
                }

        return true;

    }
}