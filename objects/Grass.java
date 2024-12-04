package objects;

/**
 * The Grass class represents a piece of grass in the game environment.
 * Each grass object has a position defined by x and y coordinates, and a type which can be used to
 * differentiate between different varieties of grass.
 */
public class Grass {

	private int x, y, type;

	/**
	 * Constructs a Grass object with the specified position and type.
	 *
	 * @param x the x-coordinate of the grass.
	 * @param y the y-coordinate of the grass.
	 * @param type the type of the grass.
	 */
	public Grass(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}

	/**
	 * Returns the x-coordinate of the grass.
	 *
	 * @return the x-coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the y-coordinate of the grass.
	 *
	 * @return the y-coordinate.
	 */
	public int getY() {
		return y;

	}

	/**
	 * Returns the type of the grass.
	 *
	 * @return the type.
	 */
	public int getType() {
		return type;
	}
}
