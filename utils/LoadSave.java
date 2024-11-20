package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.FlappyGame;

public class LoadSave {

    // public static final String PLAYER_ATLAS = "player_sprites.png";
    // public static final String PLAYER_ATLAS = "UD_FB.png";
    public static final String PLAYER_ATLAS = "HD_B-NoWM.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String LEVEL_ONE_DATA = "bird_one_data.png";

    // public static final String PLAYER_ATLAS = "player_sprites.png";
    // public static final String LEVEL_ATLAS = "outside_sprites.png";
    //	public static final String LEVEL_ONE_DATA = "level_one_data.png";
    // public static final String LEVEL_ONE_DATA = "level_one_data_long.png";

    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String MENU_BACKGROUND_IMG = "background_menu.png";
    // Start adding backgrounds in this class.
    public static final String PLAYING_BG_IMG = "scene_chatGPT.png";  // This is the background created by CHAT_GPT.
    public static final String GROUND_SPRING = "ground_spring.png";  // This is the background created by CHAT_GPT.


    public static BufferedImage GetSpriteAtlas(String fileName) {
        System.out.println(fileName);
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static int[][] GetLevelData() {
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48)
                    value = 0;
                lvlData[j][i] = value;
            }
        return lvlData;

    }
}