package entities;

import main.FlappyGame;
import utils.LoadSave;

import static utils.Constants.PlayerConstants.*;

/**
 * Represents a character in the Flappy Bird game with various attributes
 * and behaviors. It defines properties of the characters such as their
 * animations, atlas, and hitboxes.
 */
public enum PlayerCharacter {

    EAGLE(11, 11, 11, 11, 1, 1, 1,
            0, 0, 0, 0, 0, 0, 0,
            LoadSave.PLAYER_EAGLE, 10, 1, 11, 72, 70,
            40, 30, 21, 25),
    BAT(8, 8, 8, 8, 1, 1, 1,
            0, 0, 0, 0, 0, 0, 0,
            LoadSave.PLAYER_BAT, 13, 1, 8, 72, 96,
            30, 20, 26, 40),
    YELLOWBIRD(3, 3, 3, 3, 3, 1, 1,
            0, 0, 0, 0, 0, 0, 0,
            LoadSave.PLAYER_YELLOWBIRD, 0, 4, 3, 58, 40,
            30, 22, 22, 12),
    REDBIRD(3, 3, 3, 3, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1,
            LoadSave.PLAYER_REDBIRD, 0, 4, 3, 58, 40,
            30, 22, 22, 12);

    public int spriteA_IDLE, spriteA_RUNNING, spriteA_JUMP, spriteA_FALLING, spriteA_ATTACK, spriteA_HIT, spriteA_DEAD;
    public int rowIDLE, rowRUNNING, rowJUMP, rowFALLING, rowATTACK, rowHIT, rowDEAD;
    public String playerAtlas;
    public int centerPixelOffset;
    public int numRows, numColms;
    public int spriteW, spriteH;
    public int hitboxW, hitboxH;
    public int xDrawOffset, yDrawOffset;

    /**
     * Constructor for the PlayerCharacter enum.
     * @param spriteA_IDLE Amount of IDLE sprites.
     * @param spriteA_RUNNING Amount of RUNNING sprites.
     * @param spriteA_JUMP Amount of JUMP sprites.
     * @param spriteA_FALLING Amount of FALLING sprites.
     * @param spriteA_ATTACK Amount of ATTACK sprites.
     * @param spriteA_HIT Amount of HIT sprites.
     * @param spriteA_DEAD Amount of DEAD sprites.
     * @param rowIDLE IDLE animation row index.
     * @param rowRUNNING RUNNING animation row index.
     * @param rowJUMP JUMP animation row index.
     * @param rowFALLING FALLING animation row index.
     * @param rowATTACK ATTACK animation row index.
     * @param rowHIT HIT animation row index.
     * @param rowDEAD DEAD animation row index.
     * @param playerAtlas File path of the player's atlas.
     * @param centerPixelOffset Center pixel offset for positioning.
     * @param numbRows Number of rows in the sprite sheet.
     * @param numbCols Number of columns in the sprite sheet.
     * @param spriteW Width of each sprite.
     * @param spriteH Height of each sprite.
     * @param hitboxW Width of the character's hitbox.
     * @param hitboxH Height of the character's hitbox.
     * @param xDrawOffset Horizontal draw offset.
     * @param yDrawOffset Vertical draw offset.
     */
    PlayerCharacter(int spriteA_IDLE, int spriteA_RUNNING, int spriteA_JUMP, int spriteA_FALLING, int spriteA_ATTACK, int spriteA_HIT, int spriteA_DEAD,
                    int rowIDLE, int rowRUNNING, int rowJUMP, int rowFALLING, int rowATTACK, int rowHIT, int rowDEAD,
                    String playerAtlas, int centerPixelOffset, int numbRows, int numbCols, int spriteW, int spriteH,
                    int hitboxW, int hitboxH,
                    int xDrawOffset, int yDrawOffset) {

        this.spriteA_IDLE = spriteA_IDLE;
        this.spriteA_RUNNING = spriteA_RUNNING;
        this.spriteA_JUMP = spriteA_JUMP;
        this.spriteA_FALLING = spriteA_FALLING;
        this.spriteA_ATTACK = spriteA_ATTACK;
        this.spriteA_HIT = spriteA_HIT;
        this.spriteA_DEAD = spriteA_DEAD;

        this.rowIDLE = rowIDLE;
        this.rowRUNNING = rowRUNNING;
        this.rowJUMP = rowJUMP;
        this.rowFALLING = rowFALLING;
        this.rowATTACK = rowATTACK;
        this.rowHIT = rowHIT;
        this.rowDEAD = rowDEAD;

        this.playerAtlas = playerAtlas;
        this.centerPixelOffset = centerPixelOffset;
        this.numRows = numbRows;
        this.numColms = numbCols;
        this.spriteW = spriteW;
        this.spriteH = spriteH;

        this.hitboxW = hitboxW;
        this.hitboxH = hitboxH;

        this.xDrawOffset = (int) (xDrawOffset * FlappyGame.SCALE);
        this.yDrawOffset = (int) (yDrawOffset * FlappyGame.SCALE);
    }

    /**
     * Returns the amount of sprites for the specified player action.
     * @param player_action The player action to get the sprite amount for.
     * @return The number of sprites associated with the player action.
     */
    public int getSpriteAmount(int player_action) {
        return switch (player_action) {
            case IDLE -> spriteA_IDLE;
            case RUNNING -> spriteA_RUNNING;
            case JUMP -> spriteA_JUMP;
            case FALLING -> spriteA_FALLING;
            case ATTACK -> spriteA_ATTACK;
            case HIT -> spriteA_HIT;
            case DEAD -> spriteA_DEAD;
            default -> 1;
        };
    }

    /**
     * Returns the row index in the sprite sheet for the given player action.
     * @param player_action The player action to get the row index for.
     * @return The row index associated with the player action.
     */
    public int getRowIndex(int player_action) {
        return switch (player_action) {
            case IDLE -> rowIDLE;
            case RUNNING -> rowRUNNING;
            case JUMP -> rowJUMP;
            case FALLING -> rowFALLING;
            case ATTACK -> rowATTACK;
            case HIT -> rowHIT;
            case DEAD -> rowDEAD;
            default -> 1;
        };
    }

}
