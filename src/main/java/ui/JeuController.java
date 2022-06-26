package ui;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import jeu.gridGenerator;
import lecteur.LecteurStringParMot;

public class JeuController {

    /*---------------------------- ATTRIBUTS ----------------------------*/

    /* a) RECUPERATION DE L'ATTRIBUT STAGE */
    Stage stage = App.stage;


    /* b) RECUPERATION DANS LE FXML "jeux.fxml" */

    //Récuperation du bouton "Reset"
    @FXML
    Button button_reset;

    //Récuperation du bouton "Soumettre"
    @FXML
    Button button_submit;

    //Récuperation de la zone de texte où sont affichées les règles
    @FXML
    TextArea text_rules;


    //Récupération des boutons qui correspondent aux cases de la grille
    @FXML Button b00; @FXML Button b01; @FXML Button b02; @FXML Button b03; @FXML Button b04; @FXML Button b05; @FXML Button b06;
    @FXML Button b10; @FXML Button b11; @FXML Button b12; @FXML Button b13; @FXML Button b14; @FXML Button b15; @FXML Button b16;
    @FXML Button b20; @FXML Button b21; @FXML Button b22; @FXML Button b23; @FXML Button b24; @FXML Button b25; @FXML Button b26;
    @FXML Button b30; @FXML Button b31; @FXML Button b32; @FXML Button b33; @FXML Button b34; @FXML Button b35; @FXML Button b36;
    @FXML Button b40; @FXML Button b41; @FXML Button b42; @FXML Button b43; @FXML Button b44; @FXML Button b45; @FXML Button b46;
    @FXML Button b50; @FXML Button b51; @FXML Button b52; @FXML Button b53; @FXML Button b54; @FXML Button b55; @FXML Button b56;
    @FXML Button b60; @FXML Button b61; @FXML Button b62; @FXML Button b63; @FXML Button b64; @FXML Button b65; @FXML Button b66;


    /* c) CONSTANTES */

    //Constante pour la taille des cases
    private final int tailleCase = 50;

    //Constantes pour le style des cases 
    private final String styleBlanc = "-fx-background-color : white; -fx-background-radius : 0px;";
    private final String styleNoir = "-fx-background-color : #000000; -fx-background-radius : 0px;";
    private final String styleJauneCase = "-fx-background-color : yellow; -fx-background-radius : 0px;";
    private final String styleJaunePoint = "-fx-background-color : #F0B20F; -fx-background-radius : 300px;";


    /* c) ATTRIBUTS DE LA CLASSE */
    
    //Grille qui correspond à celle que l'on a genéré
    private gridGenerator grille;

    //Tableau contenant tout les bouttons de la grille
    private Button[] tabButton;

    //Tableau des valeurs de la grille (point jaune -> valeurs positive, case blanche/noire -> valeur négative)
    private int[] resultTab = new int[49];

    //Variable permettant de savoir si l'on peut modifier la grille ou non
    private boolean enable;

    //Variables contenants respectivement la liste des valeurs de chaque cases qui correspond à la solution, ainsi que la formule DIMACS de la grille
    private String resultatSolveur;
    private String formuleDimacs;


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
        alerte.setHeaderText("Aide pour jouer au jeu");
        alerte.setWidth(500);
        alerte.setHeight(600);
        alerte.setContentText("•Les règles pour jouer sont situées sur le coté \n\n• Appuyez sur chaque case pour indiquer où vous voulez mettre les ampoules\n\n• Une fois que vous avez fini de remplir la grille, vous pouvez appuyer sur \'Soumettre\' \n\n• Les autres boutons sont assez explicatifs");
        alerte.show();
    }



    /* b) METHODES GERANT LES BOUTONS PROPRES A CETTE FENETRE */

    /*
    SPÉCIFICATION clickDetected
    clickDetected : Action déclenchée lors de l'activation d'un des boutons de la grille.
    Entraîne une modification du bouton en fonction de sa couleur et de son texte initial,
    et modifie le tableau tab en fonction. (→ : représente un clic sur le bouton)
    Une configuration sera représenté par un doublet : (couleur ; case ou point)
    •   (blanc ; case)     →   (jaune ; point)         
    •   (jaune ; point)   ->   (blanc ; case)
    •   (jaune ; case)    ->   (jaune ; point)
    PARAMETRES :
    •   event de type Event : permet de récupérer l'objet sur lequel a été effectué le clic.
    */
    public void clickDetected(Event event){
        if(enable){
            Button bouton = (Button) event.getSource();
            String style = bouton.getStyle();
    
            switch(style){
                case(styleJaunePoint):
                    bouton.setStyle(styleBlanc);
                    break;  
    
                case(styleJauneCase):
                    
                case(styleBlanc):
                    bouton.setStyle(styleJaunePoint);
                    break;
                
            }
            actualize();
        }  
    }


    /*
    SPÉCIFICATION actualize
    actualize : Permet d'actualiser les cases de la grille après que l'on ai rajouter ou enlever un point jaune à celle ci
    */
    private void actualize(){
        int k = 0;
        while(k < tabButton.length){
            Button b = tabButton[k];
            if(b.getStyle().equals(styleBlanc) || b.getStyle().equals(styleJauneCase)){
                int[] listeB = grille.grid.adjacent(k+1);

                int i = 0;
                Boolean pointJ = false;
                while(i < listeB.length && !pointJ){
                    pointJ = tabButton[listeB[i]-1].getStyle().equals(styleJaunePoint);
                    i++;
                }
                String style = (pointJ) ? styleJauneCase : styleBlanc;
                b.setStyle(style);
            }
            k++;
        }
    }



    /*
    SPÉCIFICATION submit
    submit : Action déclenchée lors de l'activation du bouton "Soumettre".
    */
    public void submit(){
        int k = 0;

        while(k < tabButton.length){
            if(tabButton[k].getStyle().equals(styleJaunePoint)){
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

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        String res = (correct) ? "Yesssss" : "Nope !";

        alert.setContentText(res);
        alert.showAndWait();

        if(correct)
            generateGrid();
 
    }


    private boolean estContenue(int i){
        int k = 1;
        while(k <= resultTab.length && i != resultTab[k-1]){
            k++;
        }
        return k <= resultTab.length;
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
        text_rules.setText("Les règles du jeu sont assez simples :\n\n" +
        "- Toutes les cases blanches doivent être allumées, pour ce faire il faut qu'il y ait une ampoule située sur la même ligne ou la même colonne et qu'il n'y ait pas de case noire entre la case blanche et l'ampoule.\n\n" +
        "- Il ne peut n'y avoir qu'une seule ampoule par ligne/colonne, sauf si elles sont séparées par une case noire.\n\n"+  
        "- Il ne peut pas y avoir d'ampoule sur les cases noires.\n\n" +  
        "- Il doit y avoir autant d'ampoule autour des cases noires chiffrées que le chiffre indiqué.\n Par exemple, si ce chiffre est 1, il ne doit pas y avoir qu'une seule ampoule autour de cette case (pas une de +, pas une de -).\nCela ne fonctionne pas si l'ampoule est placée en diagonale.");
        text_rules.wrapTextProperty();
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
                tabButton[k].setStyle(styleBlanc);
                tabButton[k].setDisable(false);
            }else{
                String nb = (grille.grid.getValues(k+1) == -1) ? "" : ""+grille.grid.getValues(k+1);
                tabButton[k].setText(nb);
                tabButton[k].setStyle(styleNoir);
                tabButton[k].setDisable(true);
            }
            tabButton[k].setMaxWidth(tailleCase);
            tabButton[k].setPrefWidth(tailleCase);
            tabButton[k].setMinWidth(tailleCase);
            tabButton[k].setMaxHeight(tailleCase);
            tabButton[k].setPrefHeight(tailleCase);
            tabButton[k].setMinHeight(tailleCase);
            k++;

        }

        resultatSolveur = grille.getResultatSolveur();
        formuleDimacs = grille.getFormatDimacs();
        button_reset.setDisable(false);
        button_submit.setDisable(false);
        enable = true;

    }



    public void showSolution(){
        reset();
        int i = 0;
        LecteurStringParMot lect = new LecteurStringParMot(resultatSolveur);
        lect.demarrer();
        while(!lect.finDeSequence()){
            
            if(lect.elementCourant().charAt(0) != '-' && estChiffre(lect.elementCourant().charAt(0))){
                tabButton[i].setStyle(styleJaunePoint);
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
                tabButton[k].setStyle(styleBlanc);
                tabButton[k].setDisable(false);
            }else{
                String nb = (grille.grid.getValues(k+1) == -1) ? "" : ""+grille.grid.getValues(k+1);
                tabButton[k].setText(nb);
                tabButton[k].setStyle(styleNoir);
                tabButton[k].setDisable(true);
            }
            k++;

        }

    }

}
