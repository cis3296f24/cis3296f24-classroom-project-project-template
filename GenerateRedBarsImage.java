import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class GenerateRedBarsImage {
    public static void main(String[] args) {
        try {
            // Define dimensions for the new image
            int width = 1024; // Width of the image
            int height = 14; // Height of the image
            int difficulty = 0; // This is hard coded for now but we can randomize it or change it as levels get hard.
            int centerY = (int)(height / 2) - difficulty; // This is the gap Flappy Bird goes through. Wide is easy.
            int topPipeHeight = 0; // We will generate the random pipes from top and bottom with fixed gap.
            int bottomPipeHeight = 0; // We will generate the random pipes from top and bottom with fixed gap.
            int barWidth = 1; // Width of each bar should not exceed 1
            int pipeDistance = 5; // Spacing between bars

            // Create a new blank image
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Get the graphics object
            Graphics g = image.getGraphics(); // Create a graphics object we will turn into a Flappy level.

            // Set background color to black
            Color myColor = new Color(11, 229, 217); // This color code in R value of 11 is transparent bg.
            g.setColor(myColor);
            g.fillRect(0, 0, width, height); // Fill the whole map with the RED color code of 11 and any G,B value.

            // Randomly generate Red values for the bars
            Random random = new Random();

            for (int i = 0; i < width; i += pipeDistance) {

                // RED VALUES code for choosing a tile.
                int redUpperCoupling = 3;   // Upper pipe coupling Red value in RGB
                int redUpperCap = 15;       // Upper pipe cap Red value in RGB
                int redLowerCap = 27;       // Bottom pipe cap Red value in RGB
                int redLowerCoupling = 39;  // Lower pipe coupling Red value in RGB
                // G B VALUES
                int green = 32; // Just some visual colors not used for enemies in flappy bird.
                int blue = 8;   // Just some visual colors not used for spawning object in flappy bird.

                // Set the color codes for the pixel map.
                Color tile03 = new Color(redUpperCoupling, green, blue); // Upper pipe coupling Red value in RGB
                Color tile15 = new Color(redUpperCap, green, blue); // Upper pipe cap Red value in RGB
                Color tile27 = new Color(redLowerCap, green, blue); // Bottom pipe cap Red value in RGB
                Color tile39 = new Color(redLowerCoupling, green, blue); // Lower pipe coupling Red value in RGB

                int topPipeSize = random.nextInt((int)(5) + 1); // Create a random size top pipe
                int bottomPipeSize = height - topPipeSize; // Create the bottom pipe by subtracting the diff.

                int capOffset = 0;

                // int topPipeSize = (int) (red / 255.0 * (height / 2)); // Scale Red to bar height

                // Randomize the horizontal position of the bar within its spacing

                // Draw upper pipe and section
                // g.setColor(new Color(red, 0, 0)); // Red only
                // g.fillRect(i + offset, centerY - topPipeSize, barWidth, topPipeSize);

                // Draw upper pipe and coupling/s
                g.setColor(tile03);    // Upper pipe coupling Red value in RGB
                g.fillRect(i + pipeDistance, 0, barWidth, topPipeSize); // Bar width should always be 1 pixel wide
                g.setColor(tile15);    // Upper pipe coupling Red value in RGB
                g.fillRect(i + pipeDistance, barWidth + capOffset, barWidth, topPipeSize);

                // Draw lower pipe and sections
                g.setColor(tile27);    // Upper pipe coupling Red value in RGB
                g.fillRect(i + pipeDistance, height - bottomPipeSize - capOffset, barWidth, bottomPipeSize); // Create the top of the pipe
                g.setColor(tile39);    // Upper pipe coupling Red value in RGB
                g.fillRect(i + pipeDistance, height - bottomPipeSize, barWidth, bottomPipeSize); // Extreme bottom pipe coupling

            }

            // Dispose of the graphics object
            g.dispose();

            // Save the generated image
            File outputFile = new File("res/generated_red_bars_image.png");
            ImageIO.write(image, "png", outputFile);

            System.out.println("Image created: generated_red_bars_image.png");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
