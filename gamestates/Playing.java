package gamestates;

import java.awt.*;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import levels.LevelManager;
import main.FlappyGame;
import ui.PauseOverlay;
import utils.HelpMethods;
import utils.LoadSave;

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
    private int score = 0;
    private int count = 0;
    int counter =0;
    // Getter for the score
    public int getScore() {
        return score;
    }

    // Method to increment the score
    public void incrementScore() {
        score++;
    }

    // Reset the score when the game starts or resets
    public void resetScore() {
        score = 0;
    }
    public int increaseCount (){//count is used as a timer since it will increment by 1 every millisecond
        count++;
        return count;
    }


    public Playing(FlappyGame flappyGame) {
        super(flappyGame);
        initClasses();
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
            increaseCount();
        }
        else {
            pauseOverlay.update();
        } if(counter==0) {
          System.out.println();
        } else if (increaseCount() % 400 ==0){ //this checks if count reaches 300 once it does it increases the score by 1- Brian Nguyen
        incrementScore();}
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder +3;

        if (xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset < 0)
            xLvlOffset = 0;

    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, FlappyGame.GAME_WIDTH, FlappyGame.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Score: " + score, 50, 50);
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
                counter++;
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