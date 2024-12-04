package levels;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Crabby;
import entities.Pinkstar;
import entities.Shark;
import main.FlappyGame;
import objects.BackgroundTree;
import objects.Cannon;
import objects.GameContainer;
import objects.Grass;
import objects.Potion;
import objects.Spike;

import static utils.Constants.EnemyConstants.*;
import static utils.Constants.ObjectConstants.*;

/**
 * Represents a game level, containing various entities and objects
 * such as enemies, potions, spikes, and background elements.
 */
public class Level {
	private BufferedImage img;
	private int[][] lvlData;

	private ArrayList<Crabby> crabs = new ArrayList<>();
	private ArrayList<Pinkstar> pinkstars = new ArrayList<>();
	private ArrayList<Shark> sharks = new ArrayList<>();
	private ArrayList<Potion> potions = new ArrayList<>();
	private ArrayList<Spike> spikes = new ArrayList<>();
	private ArrayList<GameContainer> containers = new ArrayList<>();
	private ArrayList<Cannon> cannons = new ArrayList<>();
	private ArrayList<BackgroundTree> trees = new ArrayList<>();
	private ArrayList<Grass> grass = new ArrayList<>();

	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;
	private int mobtype = 0;

	/**
	 * Constructs a new Level with the specified background image.
	 *
	 * @param img the BufferedImage representing the level background and layout
	 */
	public Level(BufferedImage img) {
		this.img = img;
		lvlData = new int[img.getHeight()][img.getWidth()];
		loadLevel();
		calcLvlOffsets();
	}

	/**
	 * Loads the level by processing the image and constructing the objects
	 * and entities within the level.
	 */
	private void loadLevel() {
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				Color c = new Color(img.getRGB(x, y));
				int red = c.getRed();
				int green = c.getGreen();
				int blue = c.getBlue();

				loadLevelData(red, x, y);
				loadEntities(green, x, y);
				loadObjects(blue, x, y);
			}
		}
	}

	/**
	 * Determines level data based on color values and positions.
	 *
	 * @param redValue the red component of the color at the pixel
	 * @param x the x-coordinate of the pixel
	 * @param y the y-coordinate of the pixel
	 */
	private void loadLevelData(int redValue, int x, int y) {
		if (redValue >= 50)
			lvlData[y][x] = 0;
		else
			lvlData[y][x] = redValue;
		switch (redValue) {
			case 0, 1, 2, 3, 30, 31, 33, 34, 35, 36, 37, 38, 39 -> {
				// grass.add(new Grass((int) (x * FlappyGame.TILES_SIZE), (int) (y * FlappyGame.TILES_SIZE) - FlappyGame.TILES_SIZE, getRndGrassType(x)));
			}
		}
	}

	/**
	 * Randomly determines grass type for a given position.
	 *
	 * @param xPos the x-coordinate to determine grass type
	 * @return the type of grass
	 */
	private int getRndGrassType(int xPos) {
		return xPos % 2;
	}

	/**
	 * Loads game entities based on color values and positions.
	 *
	 * @param greenValue the green component of the color at the pixel
	 * @param x the x-coordinate of the pixel
	 * @param y the y-coordinate of the pixel
	 */
	private void loadEntities(int greenValue, int x, int y) {
		mobtype++;
		greenValue = mobtype;
		if (greenValue > 2) mobtype = 0;
		switch (greenValue) {
			case CRABBY -> crabs.add(new Crabby(250, 25));
			case PINKSTAR -> pinkstars.add(new Pinkstar(325 * 3, 25));
			case SHARK -> sharks.add(new Shark(25 * 5, 25));
			case 100 -> playerSpawn = new Point(5, 5);
		}
	}

	/**
	 * Loads objects in the level based on color values and positions.
	 *
	 * @param blueValue the blue component of the color at the pixel
	 * @param x the x-coordinate of the pixel
	 * @param y the y-coordinate of the pixel
	 */
	private void loadObjects(int blueValue, int x, int y) {
		switch (blueValue) {
			// case RED_POTION, BLUE_POTION -> potions...
			// case BOX, BARREL -> containers...
			// More cases for other objects
		}
	}

	/**
	 * Calculates the level offsets based on the dimensions of the image.
	 */
	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - FlappyGame.TILES_IN_WIDTH;
		maxLvlOffsetX = FlappyGame.TILES_SIZE * maxTilesOffset;
	}

	// Additional methods with appropriate @return tags

	/**
	 * Gets the sprite index at the specified coordinates.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @return the sprite index at the specified location
	 */
	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	/**
	 * Returns the level data as a 2D array.
	 *
	 * @return the level data
	 */
	public int[][] getLevelData() {
		return lvlData;
	}

	/**
	 * Returns the maximum level offset on the x-axis.
	 *
	 * @return the maximum level x-offset
	 */
	public int getLvlOffset() {
		return maxLvlOffsetX;
	}

	/**
	 * Returns the player's spawn point in the level.
	 *
	 * @return the player spawn point as a Point object
	 */
	public Point getPlayerSpawn() {
		return playerSpawn;
	}

	// Getters for various entities and objects
	// Each method follows a similar pattern with a descriptive comment and @return tag

	/**
	 * Returns the list of Crabby entities in the level.
	 *
	 * @return the list of Crabby entities
	 */
	public ArrayList<Crabby> getCrabs() {
		return crabs;
	}

	/**
	 * Returns the list of Shark entities in the level.
	 *
	 * @return the list of Shark entities
	 */
	public ArrayList<Shark> getSharks() {
		return sharks;
	}

	// Other getters follow the same structure as above
}
