package ui;

import java.io.IOException;
import java.util.Arrays;

import javax.net.ssl.SSLPermission;

import formalisation.Grille;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/*
SPÉCIFICATION GridController
GridController : Classe gérant les actions se produisant dans grid.fxml.
*/
public class GridController 
{
    /*---------------------------- ATTRIBUTS ----------------------------*/

    /* a) RECUPERATION DE L'ATTRIBUT STAGE */
    Stage stage = App.stage;

    /* b) RECUPERATION DANS LE FXML "grid.fxml" */
    //Récupération de la zone de texte fnc qui correspond à la première zone de texte en partant du haut
    @FXML
    TextArea fnc;

    //Récupération de la zone de texte dimacs qui correspond à la seconde zone de texte en partant du haut
    @FXML
    TextArea dimacs;

    //Récupération de la zone de texte satsolver qui correspond à la dernière zone de texte en partant du haut
    @FXML
    TextArea satsolver;

    //Récupération des boutons qui correspondent aux cases de la grille
    @FXML Button b00; @FXML Button b01; @FXML Button b02; @FXML Button b03; @FXML Button b04; @FXML Button b05; @FXML Button b06;
    @FXML Button b10; @FXML Button b11; @FXML Button b12; @FXML Button b13; @FXML Button b14; @FXML Button b15; @FXML Button b16;
    @FXML Button b20; @FXML Button b21; @FXML Button b22; @FXML Button b23; @FXML Button b24; @FXML Button b25; @FXML Button b26;
    @FXML Button b30; @FXML Button b31; @FXML Button b32; @FXML Button b33; @FXML Button b34; @FXML Button b35; @FXML Button b36;
    @FXML Button b40; @FXML Button b41; @FXML Button b42; @FXML Button b43; @FXML Button b44; @FXML Button b45; @FXML Button b46;
    @FXML Button b50; @FXML Button b51; @FXML Button b52; @FXML Button b53; @FXML Button b54; @FXML Button b55; @FXML Button b56;
    @FXML Button b60; @FXML Button b61; @FXML Button b62; @FXML Button b63; @FXML Button b64; @FXML Button b65; @FXML Button b66;

    /* c) Attributs de la classe */
    /*
    SPÉCIFICATION tab
    tab : tableau à deux dimension d'entiers qui représente la grille.
    Il sera donné en paramètre pour à la partie Logique de ce programme pour créer les résultats finaux.
    */
    int[][] tab = { {-2, -2, -2, -2, -2, -2, -2}, 
                    {-2, -2, -2, -2, -2, -2, -2},
                    {-2, -2, -2, -2, -2, -2, -2}, 
                    {-2, -2, -2, -2, -2, -2, -2},
                    {-2, -2, -2, -2, -2, -2, -2}, 
                    {-2, -2, -2, -2, -2, -2, -2},
                    {-2, -2, -2, -2, -2, -2, -2}};

  
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
        alerte.setHeaderText("Aide pour utiliser cet outil");
        alerte.setWidth(500);
        alerte.setHeight(600);
        alerte.setContentText("• Définissez votre grille en cliquant sur les cases\n\n• Appuyez sur le bouton \"Soumettre\" pour consulter le résultat\n\n• Appuyez sur le bouton \"Réinitialiser\" pour vider la grille et les champs de texte");
        alerte.show();
    }

    /* b) METHODES GERANT LES BOUTONS PROPRES A CETTE FENETRE */

    /*
    SPÉCIFICATION clickDetected
    clickDetected : Action déclenchée lors de l'activation d'un des boutons de la grille.
    Entraîne une modification du bouton en fonction de sa couleur et de son texte initial,
    et modifie le tableau tab en fonction. (→ : représente un clic sur le bouton)
    Une configuration sera représenté par un triplet : (couleur ; texte ; valeur dans tab)
    •   (blanc ; "" ; -2)   →   (noir ; "" ; -1)         
    •   (noir ; "" ; -1)    →   (noir ; "0" ; 0)
    •   (noir ; "0" ; 0)    →   (noir ; "1" ; 1)
    •   (noir ; "1" ; 1)    →   (noir ; "2" ; 2)
    •   (noir ; "2" ; 2)    →   (noir ; "3" ; 3)
    •   (noir ; "3" ; 3)    →   (noir ; "4" ; 4)
    •   (noir ; "4" ; 4)    →   (blanc ; "" ; -2)
    PARAMETRES :
    •   event de type Event : permet de récupérer l'objet sur lequel a été effectué le clic.
    */
    public void clickDetected(Event event)
    {
        Button bouton = (Button) event.getSource();
        String id = bouton.getId();
        String style = bouton.getStyle();
        String texte = bouton.getText();
        int j = id.charAt(1) - 48;
        int i = id.charAt(2) - 48;
        switch(style)
        {
            case("-fx-background-color: white;"):
                bouton.setStyle("-fx-background-color: black;");
                tab[i][j] = -1;
                break;

            case("-fx-background-color: black;"):
                switch(texte)
                {
                    case(""):
                        bouton.setText("0");
                        tab[i][j] = 0;
                        break;

                    case("0"):
                        bouton.setText("1");
                        tab[i][j] = 1;
                        break;

                    case("1"):
                        bouton.setText("2");
                        tab[i][j] = 2;
                        break;

                    case("2"):
                        bouton.setText("3");
                        tab[i][j] = 3;
                        break;

                    case("3"):
                        bouton.setText("4");
                        tab[i][j] = 4;
                        break;

                    case("4"):
                        tab[i][j] = -2;
                        bouton.setText("");
                        bouton.setStyle("-fx-background-color: white;");
                        break;
                }
                break;   
        }
    }

    /*
    SPÉCIFICATION submit
    submit : Action déclenchée lors de l'activation du bouton "Soumettre".
    Permet de valider et de lancer le programme.
    Concrètement on donne tab à la partie "logique" du projet puis on affiche 
    le résultat.
    */
    public void submit()
    {
        Grille grid = new Grille(tab);
        fnc.setText(grid.allRules().toString());
        dimacs.setText(grid.allRules().formuleDIMACS());
    }

    /*
    SPÉCIFICATION reset
    reset : Action déclenchée lors de l'activation du bouton "Réinitialiser".
    Permet de remettre la grille, tab et les champs de texte dans leur état inital.
    */
    public void reset()
    {
        //Façon de faire un peu bourrine pour reset les boutons j'y reflechirai ultérieurement :
        //Donc là c'est pour enlever le texte dans les boutons et les remettre en blanc
        b00.setText(""); b00.setStyle("-fx-background-color: white;");
        b01.setText(""); b01.setStyle("-fx-background-color: white;");
        b02.setText(""); b02.setStyle("-fx-background-color: white;");
        b03.setText(""); b03.setStyle("-fx-background-color: white;");
        b04.setText(""); b04.setStyle("-fx-background-color: white;");
        b05.setText(""); b05.setStyle("-fx-background-color: white;");
        b06.setText(""); b06.setStyle("-fx-background-color: white;");
        b10.setText(""); b10.setStyle("-fx-background-color: white;");
        b11.setText(""); b11.setStyle("-fx-background-color: white;");
        b12.setText(""); b12.setStyle("-fx-background-color: white;");
        b13.setText(""); b13.setStyle("-fx-background-color: white;");
        b14.setText(""); b14.setStyle("-fx-background-color: white;");
        b15.setText(""); b15.setStyle("-fx-background-color: white;");
        b16.setText(""); b16.setStyle("-fx-background-color: white;");
        b20.setText(""); b20.setStyle("-fx-background-color: white;");
        b21.setText(""); b21.setStyle("-fx-background-color: white;");
        b22.setText(""); b22.setStyle("-fx-background-color: white;");
        b23.setText(""); b23.setStyle("-fx-background-color: white;");
        b24.setText(""); b24.setStyle("-fx-background-color: white;");
        b25.setText(""); b25.setStyle("-fx-background-color: white;");
        b26.setText(""); b26.setStyle("-fx-background-color: white;");
        b30.setText(""); b30.setStyle("-fx-background-color: white;");
        b31.setText(""); b31.setStyle("-fx-background-color: white;");
        b32.setText(""); b32.setStyle("-fx-background-color: white;");
        b33.setText(""); b33.setStyle("-fx-background-color: white;");
        b34.setText(""); b34.setStyle("-fx-background-color: white;");
        b35.setText(""); b35.setStyle("-fx-background-color: white;");
        b36.setText(""); b36.setStyle("-fx-background-color: white;");
        b40.setText(""); b40.setStyle("-fx-background-color: white;");
        b41.setText(""); b41.setStyle("-fx-background-color: white;");
        b42.setText(""); b42.setStyle("-fx-background-color: white;");
        b43.setText(""); b43.setStyle("-fx-background-color: white;");
        b44.setText(""); b44.setStyle("-fx-background-color: white;");
        b45.setText(""); b45.setStyle("-fx-background-color: white;");
        b46.setText(""); b46.setStyle("-fx-background-color: white;");
        b50.setText(""); b50.setStyle("-fx-background-color: white;");
        b51.setText(""); b51.setStyle("-fx-background-color: white;");
        b52.setText(""); b52.setStyle("-fx-background-color: white;");
        b53.setText(""); b53.setStyle("-fx-background-color: white;");
        b54.setText(""); b54.setStyle("-fx-background-color: white;");
        b55.setText(""); b55.setStyle("-fx-background-color: white;");
        b56.setText(""); b56.setStyle("-fx-background-color: white;");
        b60.setText(""); b60.setStyle("-fx-background-color: white;");
        b61.setText(""); b61.setStyle("-fx-background-color: white;");
        b62.setText(""); b62.setStyle("-fx-background-color: white;");
        b63.setText(""); b63.setStyle("-fx-background-color: white;");
        b64.setText(""); b64.setStyle("-fx-background-color: white;");
        b65.setText(""); b65.setStyle("-fx-background-color: white;");
        b66.setText(""); b66.setStyle("-fx-background-color: white;");

        //Ici on réinitialise le tableau tab avec des -2 partout :
        int i = 0;
        int j = 0;
        while(i < 7)
        {
            j = 0;
            while(j < 7)
            {
                tab[i][j] = -2;
                j++;
            }
            i++;
        }

        //On vide les zones de texte
        fnc.setText("");
        dimacs.setText("");
        satsolver.setText("");
    }

  


    


}