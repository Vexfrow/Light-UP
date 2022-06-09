package ui;

import java.io.File;
import java.io.IOException;


import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import jeu.gridGenerator;
import lecteur.LecteurStringParMot;

public class JeuController {

    Stage stage = App.stage;


    @FXML Button b00; @FXML Button b01; @FXML Button b02; @FXML Button b03; @FXML Button b04; @FXML Button b05; @FXML Button b06;
    @FXML Button b10; @FXML Button b11; @FXML Button b12; @FXML Button b13; @FXML Button b14; @FXML Button b15; @FXML Button b16;
    @FXML Button b20; @FXML Button b21; @FXML Button b22; @FXML Button b23; @FXML Button b24; @FXML Button b25; @FXML Button b26;
    @FXML Button b30; @FXML Button b31; @FXML Button b32; @FXML Button b33; @FXML Button b34; @FXML Button b35; @FXML Button b36;
    @FXML Button b40; @FXML Button b41; @FXML Button b42; @FXML Button b43; @FXML Button b44; @FXML Button b45; @FXML Button b46;
    @FXML Button b50; @FXML Button b51; @FXML Button b52; @FXML Button b53; @FXML Button b54; @FXML Button b55; @FXML Button b56;
    @FXML Button b60; @FXML Button b61; @FXML Button b62; @FXML Button b63; @FXML Button b64; @FXML Button b65; @FXML Button b66;


    gridGenerator grille;

    Button[] tabButton;

    int[] resultTab = new int[49];

    @FXML 
    MediaView mediaView;

    @FXML
    Button button_reset;

    @FXML
    Button button_submit;

    boolean enable;


    String resultatSolveur;
    String formuleDimacs;


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
    public void openSatSolver() throws IOException{
        App.newScene("satSolver");
    }

    /*
    SPÉCIFICATION openLightUpSolver
    openLightUpSolver: Action déclenchée lors de l'activation du bouton "Solveur de Light Up".
    Permet l'ouverture de la fenêtre du même nom.
    */
    public void openLightUpSolver() throws IOException{  
        App.newScene("grid");
    }



    /*
    SPÉCIFICATION help
    help : Action déclenchée lors de l'activation du bouton "Aide".
    Entraîne l'apparition d'une alerte indiquant une aide.
    */
    public void help(){
        Alert alerte = new Alert(AlertType.INFORMATION);
        alerte.setHeaderText("Aide pour utiliser cet outil");
        alerte.setWidth(500);
        alerte.setHeight(600);
        alerte.setContentText("• Saisissez la formule dont vous voulez le modèle dans le premier champ\n\n• Appuyez sur le bouton \"Soumettre\" pour consulter le résultat\n\n• Appuyez sur le bouton \"Réinitialiser\" pour vider la grille et les champs de texte");
        alerte.show();
    }



    /* b) METHODES GERANT LES BOUTONS PROPRES A CETTE FENETRE */

    /*
    SPÉCIFICATION clickDetected
    clickDetected : Action déclenchée lors de l'activation d'un des boutons de la grille.
    Entraîne une modification du bouton en fonction de sa couleur et de son texte initial,
    et modifie le tableau tab en fonction. (→ : représente un clic sur le bouton)
    Une configuration sera représenté par un triplet : (couleur ; texte ; valeur dans tab)
    •   (blanc ; "" ; -2)   →   (jaune ; "" ; -1)         
    •   (noir ; "" ; -1)    →   (noir ; "0" ; 0)
    PARAMETRES :
    •   event de type Event : permet de récupérer l'objet sur lequel a été effectué le clic.
    */
    public void clickDetected(Event event){
        if(enable){
            Button bouton = (Button) event.getSource();
            
            String style = bouton.getStyle();
            int[] listeB = grille.grid.adjacent(getPositionButton(bouton)+1);
            int i;
    
            switch(style){
                case("-fx-background-color : white; -fx-background-radius : 0px;"):
                    i = 0;
                    while(i < listeB.length){
                        tabButton[listeB[i]-1].setStyle("-fx-background-color : yellow; -fx-background-radius : 0px;");
                        i++;
                    }
                    bouton.setStyle("-fx-background-color : #F0B20F; -fx-background-radius : 300px;");
                    break;
    
                case("-fx-background-color : #F0B20F; -fx-background-radius : 300px;"):
                    i = 0;
                    while(i < listeB.length){
                        tabButton[listeB[i]-1].setStyle("-fx-background-color : white; -fx-background-radius : 0px;");
                        i++;
                    }
                    bouton.setStyle("-fx-background-color : white; -fx-background-radius : 0px;");
                    break;  
    
    
                case("-fx-background-color : yellow; -fx-background-radius : 0px;"):
                    bouton.setStyle("-fx-background-color : #F0B20F; -fx-background-radius : 300px;");
                    break;
            }
        }  
    }


    private int getPositionButton(Button b){
        int i = 0;
        while( i < tabButton.length && !b.equals(tabButton[i])){
            i++;
        }
        return i;
    }


    /*
    SPÉCIFICATION submit
    submit : Action déclenchée lors de l'activation du bouton "Soumettre".
    */
    public void submit(){
        int k = 0;

        while(k < tabButton.length){
            if(tabButton[k].getStyle().equals("-fx-background-color : #F0B20F; -fx-background-radius : 300px;")){
                resultTab[k] = k+1;
            }else{
                resultTab[k] = -(k+1);
            }
            k++;
        }


        LecteurStringParMot lect = new LecteurStringParMot(formuleDimacs);
        lect.demarrer();
        lect.avancer();
        lect.avancer();
        lect.avancer();
        lect.avancer();


        boolean correct = true;
        while(!lect.finDeSequence() && correct){
            correct = false;
            int i = Integer.parseInt(lect.elementCourant());

            while(i != 0 && !correct && !lect.finDeSequence()){
                correct = estContenue(i);
                lect.avancer();
                i = Integer.parseInt(lect.elementCourant());

            }
            if(correct){
                while(i != 0 && !lect.finDeSequence()){
                    lect.avancer();
                    i = Integer.parseInt(lect.elementCourant());
                }
            }
            lect.avancer();
        }

        Media media;
        MediaPlayer mp;

        if(correct){
            File file = new File("./src/main/resources/content/congratulation.mp4");
            media = new Media(file.toURI().toString());
            mp = new MediaPlayer(media);
            mp.setAutoPlay(true);
            mediaView.setMediaPlayer(mp);
            mediaView.setFitWidth(400);
            mediaView.setFitHeight(250);
            generateGrid();

        }else{
            File file = new File("./src/main/resources/content/wrong.mp4");
            media = new Media(file.toURI().toString());
            mp = new MediaPlayer(media);
            mp.setAutoPlay(true);
            mediaView.setMediaPlayer(mp);
            mediaView.setFitWidth(200);
            mediaView.setFitHeight(250);
        }
 
    }


    private boolean estContenue(int i){
        int k = 1;
        while(k <= resultTab.length && i != resultTab[k-1]){
            k++;
        }
        return k <= resultTab.length;
    }



    public void resultGrid(String resultat){
        int i = 0;
        LecteurStringParMot lect = new LecteurStringParMot(resultat);
        lect.demarrer();
        while(!lect.finDeSequence()){
            
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
    private void initialize(){
        stage.setHeight(432);
        stage.setWidth(1000);
        stage.centerOnScreen();
        stage.setResizable(true);
        stage.setTitle("Grille jouable");
        enable = true;
        tabButton = new Button[] {b00,b10,b20,b30,b40,b50,b60,b01,b11,b21,b31,b41,b51,b61,b02,b12,b22,b32,b42,b52,b62,b03,b13,b23,b33,b43,b53,b63,b04,b14,b24,b34,b44,b54,b64,b05,b15,b25,b35,b45,b55,b65,b06,b16,b26,b36,b46,b56,b66};
        generateGrid();
    }



    public void generateGrid(){
        grille = new gridGenerator(7);
        grille.generateGrid();

        int k = 0;
        
        while(k < tabButton.length){

            if(grille.grid.getValues(k+1) == -2){
                tabButton[k].setText("");
                tabButton[k].setStyle("-fx-background-color : white; -fx-background-radius : 0px;");
                tabButton[k].setDisable(false);
            }else{
                String nb = (grille.grid.getValues(k+1) == -1) ? "" : ""+grille.grid.getValues(k+1);
                tabButton[k].setText(nb);
                tabButton[k].setStyle("-fx-background-color : black; -fx-background-radius : 0px;");
                tabButton[k].setDisable(true);
            }
            tabButton[k].setMaxWidth(50);
            tabButton[k].setPrefWidth(50);
            tabButton[k].setMinWidth(50);
            tabButton[k].setMaxHeight(50);
            tabButton[k].setPrefHeight(50);
            tabButton[k].setMinHeight(50);
            k++;

        }

        resultatSolveur = grille.getResultatSolveur();
        formuleDimacs = grille.getFormatDimacs();
        button_reset.setDisable(false);
        button_submit.setDisable(false);

    }



    public void showSolution(){
        reset();
        int i = 0;
        LecteurStringParMot lect = new LecteurStringParMot(resultatSolveur);
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
        button_reset.setDisable(true);
        button_submit.setDisable(true);
        enable = false;

    }





    public void reset(){
        int k = 0;
        Button[] tabButton = new Button[] {b00,b10,b20,b30,b40,b50,b60,b01,b11,b21,b31,b41,b51,b61,b02,b12,b22,b32,b42,b52,b62,b03,b13,b23,b33,b43,b53,b63,b04,b14,b24,b34,b44,b54,b64,b05,b15,b25,b35,b45,b55,b65,b06,b16,b26,b36,b46,b56,b66};
        
        while(k < tabButton.length){

            if(grille.grid.getValues(k+1) == -2){
                tabButton[k].setText("");
                tabButton[k].setStyle("-fx-background-color : white; -fx-background-radius : 0px;");
                tabButton[k].setDisable(false);
            }else{
                String nb = (grille.grid.getValues(k+1) == -1) ? "" : ""+grille.grid.getValues(k+1);
                tabButton[k].setText(nb);
                tabButton[k].setStyle("-fx-background-color : black; -fx-background-radius : 0px;");
                tabButton[k].setDisable(true);
            }
            k++;

        }

    }

}
