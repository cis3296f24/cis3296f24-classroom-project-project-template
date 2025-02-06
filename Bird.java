/**
 * The Bird class represents a bird in the Flappy Bird game that moves
 * vertically under the influence of gravity.
 */
public class Bird {
    private float x, y; // The x and y coordinates of the bird.
    private float velocity; // The current vertical velocity of the bird.

    /**
     * Constructs a new Bird with the specified x and y coordinates.
     * The bird's initial velocity is set to zero.
     *
     * @param x the initial x-coordinate of the bird.
     * @param y the initial y-coordinate of the bird.
     */
    public Bird(float x, float y) {
        this.x = x;
        this.y = y;
        this.velocity = 0;
    }

    /**
     * Updates the bird's position and velocity. Gravity is applied, and the bird
     * is constrained to remain within the bounds of the window.
     */
    public void update() {
        velocity += 0.5f; // Gravity
        y += velocity;

        if (y + 20 > 600) { // Window height
            y = 600 - 20;
            velocity = 0;
        } else if (y < 0) {
            y = 0;
            velocity = 0;
        }
    }

    /**
     * Sets the vertical velocity of the bird.
     *
     * @param velocity the new vertical velocity of the bird.
     */
    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    /**
     * Gets the x-coordinate of the bird.
     *
     * @return the x-coordinate of the bird.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the bird.
     *
     * @return the y-coordinate of the bird.
     */
    public float getY() {
        return y;
    }
}
