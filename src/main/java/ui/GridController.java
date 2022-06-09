package ui;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import formalisation.Grille;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import lecteur.LecteurStringParMot;
import satsolver.DPLL;

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

    /*
    SPÉCIFICATION submited
    submited : booléen qui indique si on a appuyé sur le bouton soumettre.
    */
    boolean submited;
  
    /*---------------------------- METHODES -----------------------------*/

    /* a) METHODES GERANT LA BARRE D'OUTIL */
    /*
    SPÉCIFICATION backToWelcome
    backToWelcome : Action déclenchée lors de l'activation du bouton "Accueil".
    Permet l'ouverture de la fenêtre du même nom.
    */
    public void backToWelcome() throws IOException{
        App.newScene("welcome");
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


    /*
    SPÉCIFICATION jouer
    jouer : Action déclenchée lors de l'activation du bouton "Jouer".
    Permet l'ouverture de la fenêtre du même nom.
    */
    public void jouer() throws IOException{
        App.newScene("jeux");
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
        if(submited == false)
        {
            Button bouton = (Button) event.getSource();
            String id = bouton.getId();
            
            String style = bouton.getStyle();
            String texte = bouton.getText();
            int j = id.charAt(1) - 48;
            int i = id.charAt(2) - 48;
            switch(style)
            {
                case("-fx-background-color : white; -fx-background-radius : 0px;"):
                    bouton.setStyle("-fx-background-color : black; -fx-background-radius : 0px;");
                    tab[i][j] = -1;
                    break;

                case("-fx-background-color : black; -fx-background-radius : 0px;"):
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
                            bouton.setStyle("-fx-background-color : white; -fx-background-radius : 0px;");
                            break;
                    }
                    break;   
            }
        }
    }

    /*
    SPÉCIFICATION submit
    submit : Action déclenchée lors de l'activation du bouton "Soumettre".
    Permet de valider et de lancer le programme.
    Concrètement on donne tab à la partie "logique" du projet puis on affiche 
    le résultat.
    */
    public void submit() throws IOException, InterruptedException
    {
        submited = true;
        Grille grid = new Grille(tab);
        String stringDimacs = grid.allRules().formuleDIMACS();
        fnc.setText(grid.allRules().toString());
        dimacs.setText(stringDimacs);
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/ui/dimacsCourant.txt"));
        bw.write(stringDimacs);
        bw.flush();
        bw.close();
        DPLL sat = new DPLL("src/main/resources/ui/dimacsCourant.txt");
        String resultat = sat.resultat();
        satsolver.setText(resultat);
        resultGrid(resultat);
    }

    /*
    SPÉCIFICATION reset
    reset : Action déclenchée lors de l'activation du bouton "Réinitialiser".
    Permet de remettre la grille, tab et les champs de texte dans leur état inital.
    */
    public void reset(){
        submited = false;
        Button[] tabButton = new Button[] {b00,b10,b20,b30,b40,b50,b60,b01,b11,b21,b31,b41,b51,b61,b02,b12,b22,b32,b42,b52,b62,b03,b13,b23,b33,b43,b53,b63,b04,b14,b24,b34,b44,b54,b64,b05,b15,b25,b35,b45,b55,b65,b06,b16,b26,b36,b46,b56,b66};
        int k = 0;
        while(k < tabButton.length)
        {
            tabButton[k].setText("");
            tabButton[k].setStyle("-fx-background-color : white; -fx-background-radius : 0px;");
            tabButton[k].setMaxWidth(40);
            tabButton[k].setPrefWidth(40);
            tabButton[k].setMinWidth(40);
            tabButton[k].setMaxHeight(40);
            tabButton[k].setPrefHeight(40);
            tabButton[k].setMinHeight(40);
            k++;
        }
       
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

    public void resultGrid(String resultat)
    {
        Button[] tabButton = new Button[] {b00,b10,b20,b30,b40,b50,b60,b01,b11,b21,b31,b41,b51,b61,b02,b12,b22,b32,b42,b52,b62,b03,b13,b23,b33,b43,b53,b63,b04,b14,b24,b34,b44,b54,b64,b05,b15,b25,b35,b45,b55,b65,b06,b16,b26,b36,b46,b56,b66};
        int i = 0;
        LecteurStringParMot lect = new LecteurStringParMot(resultat);
        lect.demarrer();
        while(!lect.finDeSequence())
        {
            
            if(lect.elementCourant().charAt(0) != '-' && estChiffre(lect.elementCourant().charAt(0)))
            {
                tabButton[i].setStyle("-fx-background-color : #F0B20F; -fx-background-radius : 300px;");
                tabButton[i].setMaxWidth(30);
                tabButton[i].setPrefWidth(30);
                tabButton[i].setMinWidth(30);
                tabButton[i].setMaxHeight(30);
                tabButton[i].setPrefHeight(30);
                tabButton[i].setMinHeight(30);
                
            }
            i++;
            lect.avancer();
        }
        
    }

    public boolean estChiffre (char c){

        return ( c >= '0' && c <= '9');
    }
  


    @FXML 
    public void initialize(){
        stage.setHeight(432);
        stage.setWidth(1000);
        stage.centerOnScreen();
        stage.setResizable(true);
        stage.setTitle("Solveur de Light Up");
    }
    


}