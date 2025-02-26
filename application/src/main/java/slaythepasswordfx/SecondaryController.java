package slaythepasswordfx; // NEEDED environment variables

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;

import game_logic.Game;

//import


public class SecondaryController {

    //essential FXML variables
    @FXML
    private TextField userInput;
    @FXML
    private Label HPLabel;
    @FXML
    private ImageView logoImageView;
    @FXML
    private Label hintLabel;
    @FXML 
    private Label LvlLabel;
    @FXML 
    private Label nameLabel;

    //Essential Backend variables

    //health variable
    private IntegerProperty health = new SimpleIntegerProperty();
    //lvl variable
    private IntegerProperty lvl = new SimpleIntegerProperty();
    //maxHealth variable
    private final int maxHealth = 5;
    //gameloop, where variables are found
    private Game game;
    //user prompts
    private List<String> prompts = new ArrayList<>();
    //hint texts for passwords.
    private StringProperty hintText = new SimpleStringProperty();
    //user prompt text
    @FXML
    private StringProperty promptText = new SimpleStringProperty();
    //User username
    @FXML
    private StringProperty username = new SimpleStringProperty();
    


    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    public void initialize(){ //make a new gameloop
        this.game = new Game();
        setDisplays();
        
    }

    @FXML
    public void load(Game loadedGameLoop){ // initialize a saved gameloop
        this.game = loadedGameLoop;
        setDisplays();
    }

    @FXML
    public void setDisplays(){
        //Set the userPrompt Display, random even between savefiles
        try {
            loadPromptsFromFile("/userInputTextPrompts.txt");
            String randPrompt = getRandomPrompt();
            promptText.set(randPrompt);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //set the Health labels
        health.set(game.getUser().getHealth());
        HPLabel.textProperty().bind(Bindings.createStringBinding(() -> String.format("%d / %d", health.get(), maxHealth), health)); 

        //display the level
        lvl.set(game.getLevel());
        LvlLabel.textProperty().bind(Bindings.createStringBinding(() -> String.format("Lvl: %d", lvl.get()), lvl));
        
        //display the userName
        username.set(game.getUser().getUsername());
        nameLabel.textProperty().bind(Bindings.createStringBinding(() -> String.format("Lvl: %d", lvl.get()), lvl));

        //Set the logo
        Image logo = new Image(getClass().getResourceAsStream("/assets/Untitled_Artwork_27.PNG"));
        logoImageView.setImage(logo); 

        //display the hints
        String hints = getConditionHints();
        hintText.set(hints);
        hintLabel.textProperty().bind(hintText);

        //display the username
        username.set(game.getUser().getUsername());
        nameLabel.textProperty().bind(username);

        
    }

    private String getConditionHints() {
    StringBuilder hintsBuilder = new StringBuilder();

    List<Condition> conditions =; game.getConditions().getConditions();
    for (Condition condition : conditions) { //loop through every condition, add breaks when multiple.
        if (hintsBuilder.length() > 0) {
            hintsBuilder.append("\n"); 
        }
        hintsBuilder.append(condition.hint());
    }
    return hintsBuilder.toString();
}

    

    @FXML
    private void captureUserInput(){

        //user input
        String Input = userInput.getText();

        //correct input.
        boolean correct = game.validateUserInput(Input); //assign to value and run function. Game logic present inside game class.
        if(correct){
            game.getUser().restorehp(1);
            setDisplays();
        }else{
            health.set(game.getUser().getHealth()); //duduct health on U.I
            setDisplays();

            //end on click if health = 0
            if(health.get() <= 0){
                EndPopup();
            }
        }
        userInput.clear();
    }

    private void EndPopup(){
        //Alert the user that the game has reached its end
        Alert gameOver = new Alert(Alert.AlertType.INFORMATION);
        gameOver.setTitle("Game Over");
        gameOver.setContentText("Your health has reached 0.");

        //wait for user to close popup
        gameOver.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    switchToPrimary();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadPromptsFromFile(String filePath) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IOException("File not found " + filePath);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String promptLine;
            while ((promptLine = reader.readLine()) != null) {
                prompts.add(promptLine.trim()); // Add each line to the list
            }
        }
    }

    private String getRandomPrompt() {
        if (prompts.isEmpty()) {
            return "Empty prompt file";
        }
        Random random = new Random();
        return prompts.get(random.nextInt(prompts.size()));
    }
}