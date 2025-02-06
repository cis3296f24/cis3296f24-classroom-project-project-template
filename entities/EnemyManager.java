package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import levels.Level;
import utils.LoadSave;
import static utils.Constants.EnemyConstants.*;

/**
 * Manages enemy behaviors, including updating, drawing, and handling enemy interactions.
 */
public class EnemyManager {

	private Playing playing;
	private BufferedImage[][] crabbyArr, pinkstarArr, sharkArr;
	private Level currentLevel;

	/**
	 * Constructs an EnemyManager with the specified playing state.
	 *
	 * @param playing the current playing game state.
	 */
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
	}

	/**
	 * Loads enemies from the specified level.
	 *
	 * @param level the level from which to load enemies.
	 */
	public void loadEnemies(Level level) {
		this.currentLevel = level;
	}

	/**
	 * Updates the state of all active enemies.
	 *
	 * @param lvlData the data representing the current level layout.
	 */
	public void update(int[][] lvlData) {
		boolean isAnyActive = false;
		for (Crabby c : currentLevel.getCrabs())
			if (c.isActive()) {
				c.update(lvlData, playing);
				isAnyActive = true;
			}

		for (Pinkstar p : currentLevel.getPinkstars())
			if (p.isActive()) {
				p.update(lvlData, playing);
				isAnyActive = true;
			}

		for (Shark s : currentLevel.getSharks())
			if (s.isActive()) {
				s.update(lvlData, playing);
				isAnyActive = true;
			}

		if (!isAnyActive) {
			playing.setLevelCompleted(true);
		}
	}

	/**
	 * Draws all active enemies using the given graphics object and level offset.
	 *
	 * @param g the graphics object used for drawing.
	 * @param xLvlOffset the horizontal offset of the level.
	 */
	public void draw(Graphics g, int xLvlOffset) {
		drawCrabs(g, xLvlOffset);
		drawPinkstars(g, xLvlOffset);
		drawSharks(g, xLvlOffset);
	}

	private void drawSharks(Graphics g, int xLvlOffset) {
		for (Shark s : currentLevel.getSharks())
			if (s.isActive()) {
				g.drawImage(sharkArr[s.getState()][s.getAniIndex()],
						(int) s.getHitbox().x - xLvlOffset - SHARK_DRAWOFFSET_X + s.flipX(),
						(int) s.getHitbox().y - SHARK_DRAWOFFSET_Y + (int) s.getPushDrawOffset(),
						SHARK_WIDTH * s.flipW(), SHARK_HEIGHT, null);
			}
	}

	private void drawPinkstars(Graphics g, int xLvlOffset) {
		for (Pinkstar p : currentLevel.getPinkstars())
			if (p.isActive()) {
				g.drawImage(pinkstarArr[p.getState()][p.getAniIndex()],
						(int) p.getHitbox().x - xLvlOffset - PINKSTAR_DRAWOFFSET_X + p.flipX(),
						(int) p.getHitbox().y - PINKSTAR_DRAWOFFSET_Y + (int) p.getPushDrawOffset(),
						PINKSTAR_WIDTH * p.flipW(), PINKSTAR_HEIGHT, null);
			}
	}

	private void drawCrabs(Graphics g, int xLvlOffset) {
		for (Crabby c : currentLevel.getCrabs())
			if (c.isActive()) {
				g.drawImage(crabbyArr[c.getState()][c.getAniIndex()],
						(int) c.getHitbox().x - xLvlOffset - CRABBY_DRAWOFFSET_X + c.flipX(),
						(int) c.getHitbox().y - CRABBY_DRAWOFFSET_Y + (int) c.getPushDrawOffset(),
						CRABBY_WIDTH * c.flipW(), CRABBY_HEIGHT, null);
			}
	}

	/**
	 * Checks if the specified attack box intersects with any active enemy's hitbox.
	 *
	 * @param attackBox the rectangle representing the attack box to check.
	 */
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for (Crabby c : currentLevel.getCrabs())
			if (c.isActive())
				if (c.getState() != DEAD && c.getState() != HIT)
					if (attackBox.intersects(c.getHitbox())) {
						c.hurt(20);
						return;
					}

		for (Pinkstar p : currentLevel.getPinkstars())
			if (p.isActive()) {
				if (p.getState() == ATTACK && p.getAniIndex() >= 3)
					return;
				else {
					if (p.getState() != DEAD && p.getState() != HIT)
						if (attackBox.intersects(p.getHitbox())) {
							p.hurt(20);
							return;
						}
				}
			}

		for (Shark s : currentLevel.getSharks())
			if (s.isActive()) {
				if (s.getState() != DEAD && s.getState() != HIT)
					if (attackBox.intersects(s.getHitbox())) {
						s.hurt(20);
						return;
					}
			}
	}

	/**
	 * Loads enemy images for crabs, pinkstars, and sharks.
	 */
	private void loadEnemyImgs() {
		crabbyArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.CRABBY_SPRITE), 9, 5, CRABBY_WIDTH_DEFAULT,
				CRABBY_HEIGHT_DEFAULT);
		pinkstarArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.PINKSTAR_ATLAS), 8, 5, PINKSTAR_WIDTH_DEFAULT,
				PINKSTAR_HEIGHT_DEFAULT);
		sharkArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.SHARK_ATLAS), 8, 5, SHARK_WIDTH_DEFAULT,
				SHARK_HEIGHT_DEFAULT);
	}

	/**
	 * Divides an image atlas into a 2D array of buffered images of specified dimensions.
	 *
	 * @param atlas the BufferedImage representing the sprite atlas.
	 * @param xSize the number of horizontal sprites.
	 * @param ySize the number of vertical sprites.
	 * @param spriteW the width of a single sprite.
	 * @param spriteH the height of a single sprite.
	 * @return a 2D array of images cropped from the atlas.
	 */
	private BufferedImage[][] getImgArr(BufferedImage atlas, int xSize, int ySize, int spriteW, int spriteH) {
		BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];
		for (int j = 0; j < tempArr.length; j++)
			for (int i = 0; i < tempArr[j].length; i++)
				tempArr[j][i] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);
		return tempArr;
	}

	/**
	 * Resets all enemies to their initial state.
	 */
	public void resetAllEnemies() {
		for (Crabby c : currentLevel.getCrabs())
			c.resetEnemy();
		for (Pinkstar p : currentLevel.getPinkstars())
			p.resetEnemy();
		for (Shark s : currentLevel.getSharks())
			s.resetEnemy();
	}

}
