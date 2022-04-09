package ui;

import java.io.IOException;

import javafx.fxml.FXML;

public class GrilleController{

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}