

public class FlappyGame {

    private GameWindow gameWindow;
    private GamePanel gamePanel;

    public FlappyGame() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
    }
}
