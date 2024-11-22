import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
// import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class PopOutBrowser extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a WebView
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        
        // Load a URL (you can change this to any URL you want)
        webEngine.load("https://www.example.com");

        // Set up the scene and stage
        StackPane root = new StackPane();
        root.getChildren().add(webView);
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("Pop-Out Browser");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
