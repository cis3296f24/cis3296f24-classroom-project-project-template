package ui;

import static utils.Constants.UI.PauseButtons.SOUND_SIZE;
import static utils.Constants.UI.VolumeButtons.SLIDER_WIDTH;
import static utils.Constants.UI.VolumeButtons.VOLUME_HEIGHT;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import main.FlappyGame;

/**
 * The AudioOptions class manages the audio settings within the game,
 * providing control over music and sound effects through a graphical interface.
 * It allows users to toggle audio and adjust volumes as needed during gameplay.
 */
public class AudioOptions {
	// Class variables and methods...
	private VolumeButton volumeButton;
	private SoundButton musicButton, sfxButton;

	private FlappyGame flappyGame;

	/**
	 * The AudioOptions class provides a user interface for adjusting audio settings
	 * in the game, including toggling music and sound effects and adjusting the volume level.
	 *
	 * @param flappyGame The game instance to associate with these audio options.
	 */
	public AudioOptions(FlappyGame flappyGame) {
		this.flappyGame = flappyGame;
		createSoundButtons();
		createVolumeButton();
	}

	private void createVolumeButton() {
		int vX = (int) (309 * FlappyGame.SCALE);
		int vY = (int) (278 * FlappyGame.SCALE);
		volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
	}

	/**
	 * Updates the state of audio buttons, checking for changes in the mute states
	 * and volume level as needed.
	 */
	private void createSoundButtons() {
		int soundX = (int) (450 * FlappyGame.SCALE);
		int musicY = (int) (140 * FlappyGame.SCALE);
		int sfxY = (int) (186 * FlappyGame.SCALE);
		musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
		sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
	}

	/**
	 * Updates the current state of audio-related UI components,
	 * such as checking if buttons have been interacted with
	 * and making necessary visual updates.
	 */
	public void update() {
		musicButton.update();
		sfxButton.update();

		volumeButton.update();
	}

	/**
	 * Draws the audio options on the screen.
	 *
	 * @param g The Graphics object used for drawing.
	 */
	public void draw(Graphics g) {
		// Sound buttons
		musicButton.draw(g);
		sfxButton.draw(g);

		// Volume Button
		volumeButton.draw(g);
	}

	/**
	 * Adjusts the volume based on mouse drag events.
	 *
	 * @param e The MouseEvent triggered by dragging the mouse.
	 */
	public void mouseDragged(MouseEvent e) {
		if (volumeButton.isMousePressed()) {
			float valueBefore = volumeButton.getFloatValue();
			volumeButton.changeX(e.getX());
			float valueAfter = volumeButton.getFloatValue();
			if (valueBefore != valueAfter)
				flappyGame.getAudioPlayer().setVolume(valueAfter);
		}
	}

	/**
	 * Handles mouse press events on the audio option buttons.
	 *
	 * @param e The MouseEvent triggered by pressing the mouse.
	 */
	public void mousePressed(MouseEvent e) {
		if (isIn(e, musicButton))
			musicButton.setMousePressed(true);
		else if (isIn(e, sfxButton))
			sfxButton.setMousePressed(true);
		else if (isIn(e, volumeButton))
			volumeButton.setMousePressed(true);
	}

	/**
	 * Handles mouse release events on the audio option buttons,
	 * toggling mute states.
	 *
	 * @param e The MouseEvent triggered by releasing the mouse.
	 */
	public void mouseReleased(MouseEvent e) {
		if (isIn(e, musicButton)) {
			if (musicButton.isMousePressed()) {
				musicButton.setMuted(!musicButton.isMuted());
				flappyGame.getAudioPlayer().toggleSongMute();
			}

		} else if (isIn(e, sfxButton)) {
			if (sfxButton.isMousePressed()) {
				sfxButton.setMuted(!sfxButton.isMuted());
				flappyGame.getAudioPlayer().toggleEffectMute();
			}
		}

		musicButton.resetBools();
		sfxButton.resetBools();

		volumeButton.resetBools();
	}

	/**
	 * Checks the mouse position to determine button hovering states.
	 *
	 * @param e The MouseEvent triggered by moving the mouse.
	 */
	public void mouseMoved(MouseEvent e) {
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);

		volumeButton.setMouseOver(false);

		if (isIn(e, musicButton))
			musicButton.setMouseOver(true);
		else if (isIn(e, sfxButton))
			sfxButton.setMouseOver(true);
		else if (isIn(e, volumeButton))
			volumeButton.setMouseOver(true);
	}

	/**
	 * Determines whether a mouse event occurred within a button's boundary.
	 *
	 * @param e The MouseEvent containing the mouse coordinates.
	 * @param b The PauseButton to check against.
	 * @return  True if the mouse event is within the button's boundary, false otherwise.
	 */
	private boolean isIn(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

}
