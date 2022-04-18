package ui;

import java.io.IOException;

import formalisation.Formule;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class SatSolverController 
{
    //OBJETS FXML
    //Les trois zones de textes
    @FXML
    TextArea formule;

    @FXML
    TextArea dimacs;

    @FXML
    TextArea satsolver;

    //Attributs
    Stage stage = App.stage; //J'utilise cette attribut pour pouvoir accéder à l'objet Stage notamment pour rendre la fenêtre redimensionnable

    //Méthode qui permet de retourner à l'accueil
    public void backToWelcome() throws IOException
    {
        stage.setTitle("Acceuil");
        App.newScene("welcome");
        stage.setHeight(149);
        stage.setWidth(415);
        stage.setResizable(false);
        stage.centerOnScreen();
    }

    //Méthode déclenchée lorsqu'on appuie sur le bouton "Soumettre"
    public void submit()
    {
        Formule formuleSaisie = new Formule(formule.getText());
        dimacs.setText(formuleSaisie.formuleDIMACS());
    }
}
