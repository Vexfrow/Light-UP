package ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class GrilleController{
    //ChoiceBox<int>[][] matrix; //names the grid of buttons

    @FXML
    private void initialize() {
    	// Initialize the person table with the two columns.
        
    }


    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}