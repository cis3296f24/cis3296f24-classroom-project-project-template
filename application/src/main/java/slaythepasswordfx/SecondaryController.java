package slaythepasswordfx;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SecondaryController {

    @FXML
    private TextField inputField;

    //private Game game;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void validateInput() {
        String userInput = inputField.getText();

    }
}