package ui;

import java.io.IOException;

import formalisation.Formule;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/*
SPÉCIFICATION SatSolverController
SatSolverController : Classe gérant les actions se produisant dans satSolver.fxml
*/
public class SatSolverController 
{
    /*---------------------------- ATTRIBUTS ----------------------------*/

    /* a) RECUPERATION DE L'ATTRIBUT STAGE */
    Stage stage = App.stage;

    /* b) RECUPERATION DANS LE FXML "grid.fxml" */
    //Récupération de la zone de texte formule qui correspond à la première zone de texte en partant du haut
    @FXML
    TextArea formule;

    //Récupération de la zone de texte dimacs qui correspond à la seconde zone de texte en partant du haut
    @FXML
    TextArea dimacs;

    //Récupération de la zone de texte satsolver qui correspond à la dernière zone de texte en partant du haut
    @FXML
    TextArea satsolver;

    /*---------------------------- METHODES -----------------------------*/

    /* a) METHODES GERANT LA BARRE D'OUTIL */
    /*
    SPÉCIFICATION backToWelcome
    backToWelcome : Action déclenchée lors de l'activation du bouton "Accueil".
    Permet l'ouverture de la fenêtre du même nom.
    */
    public void backToWelcome() throws IOException
    {
        stage.setTitle("Acceuil");
        App.newScene("welcome");
        stage.setHeight(432);
        stage.setWidth(1000);
        stage.setResizable(false);
        stage.centerOnScreen();
    }

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
    SPÉCIFICATION help
    help : Action déclenchée lors de l'activation du bouton "Aide".
    Entraîne l'apparition d'une alerte indiquant une aide.
    */
    public void help()
    {
        Alert alerte = new Alert(AlertType.INFORMATION);
        alerte.setHeaderText("Aide pour utiliser cet outil");
        alerte.setWidth(500);
        alerte.setHeight(600);
        alerte.setContentText("• Saisissez la formule dont vous voulez le modèle dans le premier champ\n\n• Appuyez sur le bouton \"Soumettre\" pour consulter le résultat\n\n• Appuyez sur le bouton \"Réinitialiser\" pour vider la grille et les champs de texte");
        alerte.show();
    }

    /*
    SPÉCIFICATION submit
    submit : Action déclenchée lors de l'activation du bouton "Soumettre".
    Permet de valider et de lancer le programme.
    Concrètement on récupère la formule saisie par l'utilisateur,
    puis on la donne à la partie "logique" du projet et on affiche 
    le résultat.
    */
    public void submit()
    {
        Formule formuleSaisie = new Formule(formule.getText());
        dimacs.setText(formuleSaisie.formuleDIMACS());
    }

    

    /*
    SPÉCIFICATION reset
    reset : Action déclenchée lors de l'activation du bouton "Réinitialiser".
    Permet de remettre les champs de texte dans leur état inital.
    */
    public void reset()
    {
        formule.setText("");
        dimacs.setText("");
        satsolver.setText("");
    }
}
