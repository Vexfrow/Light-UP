package ui;

import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/*
SPÉCIFICATION WelcomeController
WelcomeController : Classe gérant les actions se produisant dans welcome.fxml
*/
public class WelcomeController 
{

    /*---------------------------- ATTRIBUTS ----------------------------*/

    /* a) RECUPERATION DE L'ATTRIBUT STAGE */
    Stage stage = App.stage; 

    /* b) RECUPERATION DANS LE FXML "welcome.fxml" */
    //Il n'y a rien a récupérer ici



    /*---------------------------- METHODES -----------------------------*/

    /* a) METHODES GERANT LA BARRE D'OUTIL */
    /*
    SPÉCIFICATION openLightUpSolver
    openLightUpSolver: Action déclenchée lors de l'activation du bouton "Solveur de Light Up".
    Permet l'ouverture de la fenêtre du même nom.
    */
    public void openLightUpSolver() throws IOException
    {
            stage.setResizable(true);
            stage.setTitle("Solveur de Light Up");
            
            App.newScene("grid");
            stage.setHeight(432);
            stage.setWidth(1000);
            stage.centerOnScreen();
    }

    /*
    SPÉCIFICATION openSatSolver
    openSatSolver: Action déclenchée lors de l'activation du bouton "SAT-Solver".
    Permet l'ouverture de la fenêtre du même nom.
    */
    public void openSatSolver() throws IOException
    {
        stage.setResizable(true);
        stage.setTitle("SAT-Solver");
        App.newScene("satSolver");
        stage.setHeight(432);
        stage.setWidth(1000);
        stage.centerOnScreen();
    }
    
    /*
    SPÉCIFICATION help
    help : Action déclenchée lors de l'activation du bouton "Aide".
    Entraîne l'apparition d'une alerte indiquant une aide.
    */
    public void help()
    {
        Alert alerte = new Alert(AlertType.INFORMATION);
        alerte.setHeaderText("Aide");
        alerte.setContentText("Utiliser la barre d'outil pour changer de fenêtre");
        alerte.show();
    }
    
}
