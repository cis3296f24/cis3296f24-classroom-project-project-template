package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import entities.Player;
import levels.LevelManager;
import main.FlappyGame;
import ui.PauseOverlay;
import utils.LoadSave;
import static utils.Constants.FlappyWorldConstants.*;

public class Playing extends State implements Statemethods {
    private Player player;
    private LevelManager levelManager;
    private PauseOverlay pauseOverlay;
    private boolean paused = false;

    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * FlappyGame.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * FlappyGame.GAME_WIDTH);
    private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
    private int maxTilesOffset = lvlTilesWide - FlappyGame.TILES_IN_WIDTH;
    private int maxLvlOffsetX = maxTilesOffset * FlappyGame.TILE_SIZE;

    private BufferedImage backgroundImg, flappyGroundImg;

    public Playing(FlappyGame flappyGame) {
        super(flappyGame);
        initClasses();
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.FlappyCity_BG_IMG); // This line loads the flappy background.
        flappyGroundImg = LoadSave.GetSpriteAtlas(LoadSave.GROUND_IMG); // This will create the floor or flappy ground.
    }

    private void initClasses() {
        levelManager = new LevelManager(flappyGame);
        player = new Player(200, 200, (int) (64 * FlappyGame.SCALE), (int) (40 * FlappyGame.SCALE));
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
    }

    @Override
    public void update() {
        if (!paused) {
            levelManager.update();
            player.update();
            checkCloseToBorder();
        } else {
            pauseOverlay.update();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        if (xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset < 0)
            xLvlOffset = 0;

    }

    // Everytime we draw something for Flappy Bird we need to add it here.
    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0 - (int) (xLvlOffset * 0.7), 0,FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT, null); // This will load the image with the dimensions of the game.
        g.drawImage(backgroundImg, FlappyGame.GAME_WIDTH - (int) (xLvlOffset * 0.7), 0,FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT, null); // This will load the image with the dimensions of the game.
        drawGround(g);
        levelManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }
    }

    // This method will draw the ground, grass, water etc.
    // I have trouble in this section to append and repeat.
    private void drawGround(Graphics g) {
        // System.out.println("xLvlOffset " + xLvlOffset);
        // for (int i = 0; i < 20; i++)  {
        for (int y = 0; y < 10; y++) {
            g.drawImage(flappyGroundImg, y * (int) (GROUND_WIDTH / 10) - (int) (xLvlOffset * 0.7), 850, (int) (GROUND_WIDTH / 10), (int) (GROUND_HEIGHT / 10), null);
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            player.setAttacking(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;
        }
        player.setRight(true); // I added this to replicate the forward motion of the bird.

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
        }

    }

    public void mouseDragged(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused)
            pauseOverlay.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseReleased(e);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseMoved(e);

    }

    public void unpauseGame() {
        paused = false;
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }

}