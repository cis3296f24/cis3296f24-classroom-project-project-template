package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.FlappyGame;
import utils.LoadSave;

/**
 * This class manages the levels in the FlappyGame, including loading, drawing,
 * and updating them. It handles the level sprites and transitions between levels.
 */
public class LevelManager {

	private FlappyGame flappyGame;
	private BufferedImage[] levelSprite;
	private BufferedImage[] waterSprite;
	private ArrayList<Level> levels;
	private int lvlIndex = 0, aniTick, aniIndex;

	/**
	 * Constructs a LevelManager with the specified FlappyGame instance.
	 *
	 * @param flappyGame The instance of FlappyGame to be managed.
	 */
	public LevelManager(FlappyGame flappyGame) {
		this.flappyGame = flappyGame;
		importOutsideSprites();
		createWater();
		levels = new ArrayList<>();
		buildAllLevels();
	}

	/**
	 * Initializes the sprites for water by loading them from the assets.
	 */
	private void createWater() {
		waterSprite = new BufferedImage[5];
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.WATER_TOP);
		for (int i = 0; i < 4; i++)
			waterSprite[i] = img.getSubimage(i * 32, 0, 32, 32);
		waterSprite[4] = LoadSave.GetSpriteAtlas(LoadSave.WATER_BOTTOM);
	}

	/**
	 * Loads the next level, updating all relevant game components such as
	 * enemies, player state, and objects.
	 */
	public void loadNextLevel() {
		Level newLevel = levels.get(lvlIndex);
		flappyGame.getPlaying().getEnemyManager().loadEnemies(newLevel);
		flappyGame.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
		flappyGame.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
		flappyGame.getPlaying().getObjectManager().loadObjects(newLevel);
	}

	/**
	 * Builds all levels by loading the images and creating Level instances.
	 */
	private void buildAllLevels() {
		BufferedImage[] allLevels = LoadSave.GetAllLevels();
		for (BufferedImage img : allLevels)
			levels.add(new Level(img));
	}

	/**
	 * Imports all level sprites needed for rendering the levels.
	 */
	private void importOutsideSprites() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
		levelSprite = new BufferedImage[48];
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 12; i++) {
				int index = j * 12 + i;
				levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
	}

	/**
	 * Draws the current level onto the provided Graphics object.
	 *
	 * @param g The Graphics object used to draw the level.
	 * @param lvlOffset The horizontal offset to apply when drawing the level.
	 */
	public void draw(Graphics g, int lvlOffset) {
		for (int j = 0; j < FlappyGame.TILES_IN_HEIGHT; j++)
			for (int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
				int index = levels.get(lvlIndex).getSpriteIndex(i, j);
				int x = FlappyGame.TILES_SIZE * i - lvlOffset;
				int y = FlappyGame.TILES_SIZE * j;
				if (index == 48)
					g.drawImage(waterSprite[aniIndex], x, y, FlappyGame.TILES_SIZE, FlappyGame.TILES_SIZE, null);
				else if (index == 49)
					g.drawImage(waterSprite[4], x, y, FlappyGame.TILES_SIZE, FlappyGame.TILES_SIZE, null);
				else
					g.drawImage(levelSprite[index], x, y, FlappyGame.TILES_SIZE, FlappyGame.TILES_SIZE, null);
			}
	}

	/**
	 * Updates the current level's state, including animating water.
	 */
	public void update() {
		updateWaterAnimation();
	}

	/**
	 * Handles the animation of water sprites in the level.
	 */
	private void updateWaterAnimation() {
		aniTick++;
		if (aniTick >= 40) {
			aniTick = 0;
			aniIndex++;

			if (aniIndex >= 4)
				aniIndex = 0;
		}
	}

	/**
	 * Returns the currently active level.
	 *
	 * @return The current Level object.
	 */
	public Level getCurrentLevel() {
		return levels.get(lvlIndex);
	}

	/**
	 * Returns the total number of levels.
	 *
	 * @return The number of levels.
	 */
	public int getAmountOfLevels() {
		return levels.size();
	}

	/**
	 * Returns the index of the current level.
	 *
	 * @return The index of the current level.
	 */
	public int getLevelIndex() {
		return lvlIndex;
	}

	/**
	 * Sets the index of the current level.
	 *
	 * @param lvlIndex The index to set as the current level.
	 */
	public void setLevelIndex(int lvlIndex) {
		this.lvlIndex = lvlIndex;
	}
}
