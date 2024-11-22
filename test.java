
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class test extends Application {




    public static void main(String[] args) {

        launch(args);

        
        System.out.println("Welcome to the application!");

        // Check if any arguments were passed
        if (args.length > 0) {
            System.out.println("Arguments received:");
            for (String arg : args) {
                System.out.println(arg);
            }
        } else {
            System.out.println("No arguments were passed.");
        }

        // Add your application logic here
    }
}