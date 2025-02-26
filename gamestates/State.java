package gamestates;

import java.awt.event.MouseEvent;

import main.FlappyGame;
import ui.MenuButton;

public class State {

    protected FlappyGame flappyGame;

    public State(FlappyGame game) {
        this.flappyGame = game;
    }

    public boolean isIn(MouseEvent e, MenuButton mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }


    public FlappyGame getGame() {
        return flappyGame;
    }
}