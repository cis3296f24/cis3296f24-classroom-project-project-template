package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.FlappyGame;
import net.sf.sdedit.ui.components.SystemOut;
import utils.LoadSave;

public class LevelManager {

	private FlappyGame flappyGame;
	private BufferedImage[] levelSprite;
	private BufferedImage[] waterSprite;
	private ArrayList<Level> levels;
	private int lvlIndex = 0, aniTick, aniIndex;

	public LevelManager(FlappyGame flappyGame) {
		this.flappyGame = flappyGame;
		importOutsideSprites();
		createWater();
		levels = new ArrayList<>();
		buildAllLevels();
	}

	private void createWater() {
		waterSprite = new BufferedImage[5];
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.WATER_TOP);
		for (int i = 0; i < 4; i++)
			waterSprite[i] = img.getSubimage(i * 32, 0, 32, 32);
		waterSprite[4] = LoadSave.GetSpriteAtlas(LoadSave.WATER_BOTTOM);
	}

	public void loadNextLevel() {
		Level newLevel = levels.get(lvlIndex);
		flappyGame.getPlaying().getEnemyManager().loadEnemies(newLevel);
		flappyGame.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
		flappyGame.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
		flappyGame.getPlaying().getObjectManager().loadObjects(newLevel);
	}

	private void buildAllLevels() {
		BufferedImage[] allLevels = LoadSave.GetAllLevels();
		for (BufferedImage img : allLevels)
			levels.add(new Level(img));
	}

	private void importOutsideSprites() {
		// System.out.println("Loading Sprites " + LoadSave.LEVEL_ATLAS);
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
		levelSprite = new BufferedImage[48];
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 12; i++) {
				int index = j * 12 + i;
				levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
	}

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

	public void update() {
		updateWaterAnimation();
	}

	private void updateWaterAnimation() {
		aniTick++;
		if (aniTick >= 40) {
			aniTick = 0;
			aniIndex++;

			if (aniIndex >= 4)
				aniIndex = 0;
		}
	}

	public Level getCurrentLevel() {
		return levels.get(lvlIndex);
	}

	public int getAmountOfLevels() {
		return levels.size();
	}

	public int getLevelIndex() {
		return lvlIndex;
	}

	public void setLevelIndex(int lvlIndex) {
		this.lvlIndex = lvlIndex;
	}
}
