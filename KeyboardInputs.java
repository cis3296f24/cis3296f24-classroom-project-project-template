import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {
    GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void keyTyped(KeyEvent e) {
        // todo
    }
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed: ");
        switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
            break;
        case KeyEvent.VK_DOWN:
            break;
        case KeyEvent.VK_LEFT:
            break;
        case KeyEvent.VK_RIGHT:
            break;
        case KeyEvent.VK_SPACE:
            // System.out.println("Space");
            gamePanel.changeYDelta(-5);

            break;

        }
    }
    public void keyReleased(KeyEvent e) {
        // todo
    }

}
