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

	public Level(BufferedImage img) {
		this.img = img;
		lvlData = new int[img.getHeight()][img.getWidth()];
		loadLevel();
		calcLvlOffsets();
	}

	private void loadLevel() {

		// Looping through the image colors just once. Instead of one per
		// object/enemy/etc..
		// Removed many methods in HelpMethods class.

		for (int y = 0; y < img.getHeight(); y++)
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

	private int getRndGrassType(int xPos) {
		return xPos % 2;
	}

	private void loadEntities(int greenValue, int x, int y) {
		mobtype ++;
		greenValue = mobtype;
		if (greenValue > 2) mobtype =0;
		switch (greenValue) {
//		case CRABBY -> crabs.add(new Crabby(x * FlappyGame.TILES_SIZE, y * FlappyGame.TILES_SIZE));
//		case PINKSTAR -> pinkstars.add(new Pinkstar(x * FlappyGame.TILES_SIZE, y * FlappyGame.TILES_SIZE));
//		case SHARK -> sharks.add(new Shark(x * FlappyGame.TILES_SIZE, y * FlappyGame.TILES_SIZE));
		case CRABBY -> crabs.add(new Crabby(250,25 ));
		case PINKSTAR -> pinkstars.add(new Pinkstar(325 * 3,25 ));
		case SHARK -> sharks.add(new Shark(25 * 5,25));		// case 100 -> playerSpawn = new Point(x * FlappyGame.TILES_SIZE, y * FlappyGame.TILES_SIZE);
		// Bird spawn point left of screen in the middle appx.
		case 100 -> playerSpawn = new Point(5, 5);
		}
	}

	private void loadObjects(int blueValue, int x, int y) {
		switch (blueValue) {
		//		case RED_POTION, BLUE_POTION -> potions.add(new Potion(x * FlappyGame.TILES_SIZE, y * FlappyGame.TILES_SIZE, blueValue));
		//		case BOX, BARREL -> containers.add(new GameContainer(x * FlappyGame.TILES_SIZE, y * FlappyGame.TILES_SIZE, blueValue));
		//		case SPIKE -> spikes.add(new Spike(x * FlappyGame.TILES_SIZE, y * FlappyGame.TILES_SIZE, SPIKE));
		//		case CANNON_LEFT, CANNON_RIGHT -> cannons.add(new Cannon(x * FlappyGame.TILES_SIZE, y * FlappyGame.TILES_SIZE, blueValue));
		// case TREE_ONE, TREE_TWO, TREE_THREE -> trees.add(new BackgroundTree(x * FlappyGame.TILES_SIZE, y * FlappyGame.TILES_SIZE, blueValue));
		}
	}

	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - FlappyGame.TILES_IN_WIDTH;
		maxLvlOffsetX = FlappyGame.TILES_SIZE * maxTilesOffset;
		// System.out.println("maxLvlOffsetX: read from inside Level.java" + maxLvlOffsetX);
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	public int[][] getLevelData() {
		return lvlData;
	}

	public int getLvlOffset() {
		return maxLvlOffsetX;
	}

	public Point getPlayerSpawn() {
		return playerSpawn;
	}

	public ArrayList<Crabby> getCrabs() {
		return crabs;
	}

	public ArrayList<Shark> getSharks() {
		return sharks;
	}

	public ArrayList<Potion> getPotions() {
		return potions;
	}

	public ArrayList<GameContainer> getContainers() {
		return containers;
	}

	public ArrayList<Spike> getSpikes() {
		return spikes;
	}

	public ArrayList<Cannon> getCannons() {
		return cannons;
	}

	public ArrayList<Pinkstar> getPinkstars() {
		return pinkstars;
	}

	public ArrayList<BackgroundTree> getTrees() {
		return trees;
	}

	public ArrayList<Grass> getGrass() {
		return grass;
	}

}
