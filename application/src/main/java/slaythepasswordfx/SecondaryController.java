package slaythepasswordfx;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


private TextField userInput;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void captureUserInput(){
        userInput = userInput.getText();
    }
}