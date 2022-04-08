package ui;

import java.io.IOException;
import java.text.*;
import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;


/*
    Classe pour la vue principale
*/

public class PrimaryController {

    @FXML
    private void switchToGrid() throws IOException {
        App.setRoot("test");
    }
}
