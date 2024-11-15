package slaythepasswordfx;

import java.io.IOException;
import javafx.fxml.FXML;
import javafX.scene.image.Image;
import javafx.scene.image.ImageView;

public class PrimaryController {

    @FXML
    private ImageView logo;

    public void initializeImage(){
        Image image = new Image(getClass().getResource("/images/Slay.The.Password.Logo.png").toExternalForm());
        logo.setImage(image)
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
//test
