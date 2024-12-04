// Tool created by Shafiq Rahman 11/22/2024

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *          Platformer Game pixel level creator.
 *          This tool uses this legend to use the tile codes
 *          to generate the pixel level file.
 *
 *            0   1   2   3   4   5   6   7   8   9  10  11
 *           12  13  14  15  16  17  18  19  20  21  22  23
 *           24  25  26  27  28  29  30  31  32  33  34  35
 *           36  37  38  39  40  41  42  43  44  45  46  47
 */

public class LevelCreateTool {
    public static void main(String[] args) {
        try {
        for (int lvlidx = 0; lvlidx <= 4; lvlidx++) {
                // Define dimensions for the new image
            int width = 50; // Width of the image
            int height = 14; // Height of the image
            int difficulty = 5; // This is hard coded for now but we can randomize it or change it as levels get hard.
            int centerY = (int)(height / 2) - difficulty; // This is the gap Flappy Bird goes through. Wide is easy.
            int topPipeHeight = 0; // We will generate the random pipes from top and bottom with fixed gap.
            int bottomPipeHeight = 0; // We will generate the random pipes from top and bottom with fixed gap.
            int barWidth = 1; // Width of each bar should not exceed 1
            int pipeDistance = 7; // Spacing between bars
            // Create a new blank image
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // Get the graphics object
            Graphics g = image.getGraphics(); // Create a graphics object we will turn into a Flappy level.
            // Set background color to black
            Color myColor = new Color(11, 234, 154); // This color code in R value of 11 is transparent bg.
            g.setColor(myColor);
            g.fillRect(0, 0, width, height); // Fill the whole map with the RED color code of 11 and any G,B value.
            // Randomly generate Red values for the bars
            Random random = new Random();
            for (int i = 0; i < width; i += pipeDistance) {
                // To create the map you need to refer to the original legend line 15 in this class
                // and import the numbers here to match the pictures and look at the upper and lower
                // codes and replace them here. You will be matching the code with outside_sprite_new.png file.
                // RED VALUES code for choosing a tile.
                //
                // PLEASE NOTE THE RED VALUE IN THE RGB FILE IS THE CODE TO IDENTIFY WHICH TILE TO LOAD.
                int columnChoice = lvlidx;  // This will load the first column for and create a map.
                int redScoreKeepingTile = 23; // This tile and tile 11 are invisible to the bird and player.
                int redUpperCoupling = 0 + columnChoice;   // Upper pipe coupling
                int redUpperCap = 12 + columnChoice;       // Upper pipe cap
                int redLowerCap = 24 + columnChoice;       // Bottom pipe cap
                int redLowerCoupling = 36 + columnChoice;  // Lower pipe coupling
                // G B VALUES
                int green = 32;  // Just some visual colors not used for enemies in flappy bird.
                int blue = 8;   // Just some visual colors not used for spawning object in flappy bird.
                // Set the color codes for the pixel map.
                Color scoreTile = new Color(redScoreKeepingTile, green + 140, blue + 3);
                Color tile03 = new Color(redUpperCoupling, green, blue); // Upper pipe coupling
                Color tile15 = new Color(redUpperCap, green, blue); // Upper pipe cap
                Color tile27 = new Color(redLowerCap, green, blue); // Bottom pipe cap
                Color tile39 = new Color(redLowerCoupling, green, blue); // Lower pipe coupling
                int topPipeSize = random.nextInt((int) (7)); // Create a random size top pipe
                System.out.println("topPipeSize: " + topPipeSize);
                int bottomPipeSize = topPipeSize; // Creates the bottom pipe with difficulty offset.
                // if (topPipeSize < 2) topPipeSize = 2;   // keep the top pipe minimum of 2 or more.
                int capOffset = 1; // Set cap offset one tile so the cap can fit. Remember the height is in pixels of 14 total.
                // int topPipeSize = (int) (red / 255.0 * (height / 2)); // Scale Red to bar height
                // Randomize the horizontal position of the bar within its spacing
                // Draw upper pipe and section
                // g.setColor(new Color(red, 0, 0)); // Red only
                // g.fillRect(i + offset, centerY - topPipeSize, barWidth, topPipeSize);
                // Draw upper pipe and coupling/s
               g.setColor(tile03);    // Upper pipe coupling
               g.fillRect(i + pipeDistance, 0, barWidth, height - topPipeSize - capOffset - difficulty -2); // Bar width should always be 1 pixel wide
               g.setColor(tile15);    // Upper pipe cap
               g.fillRect(i + pipeDistance, height - topPipeSize - capOffset - difficulty -2, barWidth, 1);
               // Draw score keeping invisible tile
                g.setColor(scoreTile);
                g.fillRect(i + pipeDistance, height - topPipeSize - capOffset - difficulty -1, barWidth, difficulty + 1);
                // Draw lower pipe and sections
                g.setColor(tile27);    // Lower pipe cap
                g.fillRect(i + pipeDistance, height - bottomPipeSize - capOffset, barWidth, bottomPipeSize + 1); // Create the top of the pipe
                g.setColor(tile39);    // Lower pipe coupling
                g.fillRect(i + pipeDistance, height - bottomPipeSize, barWidth, bottomPipeSize); // Extreme bottom pipe coupling
            }
            // Dispose of the graphics object
            g.dispose();
            // Save the generated image
                File outputFile = new File("res/lvls/" + (lvlidx + 1) +".png");
                ImageIO.write(image, "png", outputFile);
                System.out.println("Image created " + outputFile);
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
