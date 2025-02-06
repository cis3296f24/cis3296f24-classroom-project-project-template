package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

/**
 * GameWindow manages the creation and configuration of the game window.
 * It listens to the window's focus changes.
 */
public class GameWindow {
	private JFrame jframe;

	/**
	 * Constructs a GameWindow and sets up the JFrame with the provided GamePanel.
	 *
	 * @param gamePanel The GamePanel to be added to the JFrame.
	 */
	public GameWindow(GamePanel gamePanel) {

		jframe = new JFrame();

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(gamePanel);

		jframe.setResizable(false);
		jframe.pack();
		jframe.setLocationRelativeTo(null);
		jframe.setVisible(true);
		jframe.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowLostFocus(WindowEvent e) {
				gamePanel.getGame().windowFocusLost();
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
				// This method is intentionally left blank
			}
		});
	}
}
