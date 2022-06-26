package ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
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
    @FXML
    Label text_welcome;

    /*---------------------------- METHODES -----------------------------*/

    /* a) METHODES GERANT LA BARRE D'OUTIL */
    /*
    SPÉCIFICATION openLightUpSolver
    openLightUpSolver: Action déclenchée lors de l'activation du bouton "Solveur de Light Up".
    Permet l'ouverture de la fenêtre du même nom.
    */
    public void openLightUpSolver() throws IOException{
            App.newScene("grid");
    }

    /*
    SPÉCIFICATION openSatSolver
    openSatSolver: Action déclenchée lors de l'activation du bouton "SAT-Solver".
    Permet l'ouverture de la fenêtre du même nom.
    */
    public void openSatSolver() throws IOException{
        App.newScene("satSolver");
    }
    
    /*
    SPÉCIFICATION help
    help : Action déclenchée lors de l'activation du bouton "Aide".
    Entraîne l'apparition d'une alerte indiquant une aide.
    */
    public void help(){
        Alert alerte = new Alert(AlertType.INFORMATION);
        alerte.setHeaderText("Aide");
        alerte.setContentText("Utiliser la barre d'outil pour changer de fenêtre");
        alerte.show();
    }

    
    /*
    SPÉCIFICATION jouer
    jouer : Action déclenchée lors de l'activation du bouton "Jouer".
    Permet l'ouverture de la fenêtre du même nom.
    */
    public void jouer() throws IOException{  
        App.newScene("jeux");
    }
    

    @FXML
    public void initialize(){
        stage.setTitle("Acceuil");
        stage.setHeight(432);
        stage.setWidth(1000);
        stage.setResizable(false);
        stage.centerOnScreen();
        text_welcome.setText("Ce programme a été réalisé dans le cadre d'un projet étudiant à l'UGA.\n\nLes auteurs sont : \n\t • Thomas Grégoire, \n\t • Vial Fabien, \n\t • Bolbènes Lucas, \n\t • Combe Gaëtan, \n\t • El Kbabty Mouad. ");
    }
}
