package ui;

import java.io.IOException;
import javafx.fxml.FXML;


/*
    Classe pour la vue principale
*/

public class PrimaryController {

    @FXML
    private void switchToGrid() throws IOException {
        App.setRoot("test");
    }
}
