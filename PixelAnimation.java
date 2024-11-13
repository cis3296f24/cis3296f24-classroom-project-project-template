import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// Struggling to test code to read in graphics without distortion.


    public class PixelAnimation {

        public static void main(String[] args) {
            try {
                File imageFile = new File("HD_B_NoWM.png"); // Replace with your file path
                BufferedImage image = ImageIO.read(imageFile);

                int width = image.getWidth();
                int height = image.getHeight();

                int[][] pixelData = new int[height][width];

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        pixelData[y][x] = image.getRGB(x, y);
                    }
                }

                // Now you have the pixel data in the pixelData array
                // You can process and manipulate it as needed

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


