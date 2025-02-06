package audio;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * The AudioPlayer class manages audio playback, including songs and sound effects,
 * within the game. It provides functions to control volume, mute states, and
 * playback of specific audio clips.
 */
public class AudioPlayer {


	/** Identifier for the menu background music. */
	public static int MENU_1 = 0;

	/** Identifier for the first level music. */
	public static int LEVEL_1 = 1;

	/** Identifier for the second level music. */
	public static int LEVEL_2 = 2;

	public static int LEVEL_3 = 3;

	/** Identifier for the second level music. */
	public static int LEVEL_4 = 4;

	public static int LEVEL_5 = 5;

	/** Identifier for the sound effect played when the player dies. */
	public static int DIE = 0;

	/** Identifier for the jump sound effect. */
	public static int JUMP = 1;

	/** Identifier for the game over sound effect. */
	public static int GAMEOVER = 2;

	/** Identifier for the level completed sound effect. */
	public static int LVL_COMPLETED = 3;

	/** Identifier for the first attack sound effect. */
	public static int ATTACK_ONE = 4;

	/** Identifier for the second attack sound effect. */
	public static int ATTACK_TWO = 5;

	/** Identifier for the third attack sound effect. */
	public static int ATTACK_THREE = 6;

	private Clip[] songs, effects;
	private int currentSongId;
	private float volume = 0.5f;
	private boolean songMute, effectMute;
	private Random rand = new Random();

	/**
	 * Constructor for the AudioPlayer class.
	 * Initializes the audio system by loading songs and sound effects,
	 * and starts playing the menu background music.
	 */
	public AudioPlayer() {
		loadSongs();
		loadEffects();
		playSong(MENU_1);
	}

	/**
	 * Loads the songs used in the game into the songs array.
	 * Retrieves audio clips for each song by name.
	 */
	private void loadSongs() {
		String[] names = { "menu", "level1", "level2", "level3", "level4", "level5"};
		songs = new Clip[names.length];
		for (int i = 0; i < songs.length; i++)
			songs[i] = getClip(names[i]);
	}

	/**
	 * Loads the sound effects used in the game into the effects array.
	 * Retrieves audio clips for each sound effect by name and updates their volume.
	 */
	private void loadEffects() {
		String[] effectNames = { "die", "jump", "gameover", "lvlcompleted", "attack1", "attack2", "attack3" };
		effects = new Clip[effectNames.length];
		for (int i = 0; i < effects.length; i++)
			effects[i] = getClip(effectNames[i]);
		updateEffectsVolume();
	}


	/**
	 * Retrieves an audio clip by its name.
	 *
	 * @param name The name of the audio file (without path and extension).
	 * @return The Clip object for the given audio name, or null if it cannot be loaded.
	 */
	private Clip getClip(String name) {
		URL url = getClass().getResource("/audio/" + name + ".wav");
		AudioInputStream audio;

		try {
			audio = AudioSystem.getAudioInputStream(url);
			Clip c = AudioSystem.getClip();
			c.open(audio);
			return c;

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Sets the volume for both songs and effects.
	 *
	 * @param volume The new volume level, ranging from 0.0 (muted) to 1.0 (maximum).
	 */
	public void setVolume(float volume) {
		this.volume = volume;
		updateSongVolume();
		updateEffectsVolume();
	}

	/**
	 * Stops the currently playing song.
	 */
	public void stopSong() {
		if (songs[currentSongId].isActive())
			songs[currentSongId].stop();
	}

	/**
	 * Sets the song to be played based on the level index.
	 *
	 * @param lvlIndex The index of the level to determine which song to play.
	 */
	public void setLevelSong(int lvlIndex) {
		System.out.println("lvlIndex = " + lvlIndex);
		switch (lvlIndex + 1) {
			case 1 : playSong(LEVEL_1);
				System.out.println("Level 1 is now playing");
			break;
			case 2 : playSong(LEVEL_2);
				System.out.println("Level 2 is now playing");
			break;
			case 3 : playSong(LEVEL_3);
				System.out.println("Level 3 is now playing");
			break;
			case 4 : playSong(LEVEL_4);
				System.out.println("Level 4 is now playing");
			break;
			case 5 : playSong(LEVEL_5);
				System.out.println("Level 5 is now playing");
			break;
		}
	}

	/**
	 * Plays the level completion effect.
	 */
	public void lvlCompleted() {
		stopSong();
		playEffect(LVL_COMPLETED);
	}

	/**
	 * Plays a random attack sound effect.
	 */
	public void playAttackSound() {
		int start = 4;
		start += rand.nextInt(3);
		playEffect(start);
	}

	/**
	 * Plays an effect based on the provided effect index.
	 *
	 * @param effect The index of the effect to be played.
	 */
	public void playEffect(int effect) {
		if (effects[effect].getMicrosecondPosition() > 0)
			effects[effect].setMicrosecondPosition(0);
		effects[effect].start();
	}

	/**
	 * Starts and loops a song based on the provided song index.
	 *
	 * @param song The index of the song to be played.
	 */
	public void playSong(int song) {
		stopSong();
		currentSongId = song;
		updateSongVolume();
		songs[currentSongId].setMicrosecondPosition(0);
		songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
	}

	/**
	 * Toggles the mute state for the songs.
	 */
	public void toggleSongMute() {
		this.songMute = !songMute;
		for (Clip c : songs) {
			BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
			booleanControl.setValue(songMute);
		}
	}

	/**
	 * Toggles the mute state for the effects.
	 */
	public void toggleEffectMute() {
		this.effectMute = !effectMute;
		for (Clip c : effects) {
			BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
			booleanControl.setValue(effectMute);
		}
		if (!effectMute)
			playEffect(JUMP);
	}

	/**
	 * Updates the volume for the currently playing song.
	 */
	private void updateSongVolume() {

		FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();
		gainControl.setValue(gain);

	}

	/**
	 * Updates the volume for all sound effects.
	 */
	private void updateEffectsVolume() {
		for (Clip c : effects) {
			FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
			float range = gainControl.getMaximum() - gainControl.getMinimum();
			float gain = (range * volume) + gainControl.getMinimum();
			gainControl.setValue(gain);
		}
	}

}
