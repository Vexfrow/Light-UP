package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    static Stage stage;

    @Override
    public void start(Stage stage) throws IOException 
    {
        try
        {
            this.stage = stage;
            Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
            scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setHeight(432);
            stage.setWidth(1000);
            stage.centerOnScreen();
            stage.show();
     
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


    static void newScene(String newFileFXML) throws IOException {
        FXMLLoader newRoot = new FXMLLoader(App.class.getResource(newFileFXML + ".fxml"));
        scene.setRoot(newRoot.load());
    }
}