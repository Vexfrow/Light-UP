package ui;

import java.io.IOException;

import javafx.stage.Stage;

public class WelcomeController 
{

    //Attributs
    Stage stage = App.stage; //J'utilise cette attribut pour pouvoir accéder à l'objet Stage notamment pour rendre la fenêtre redimensionnable

    
    
    //Méthode pour ouvrir une nouvelle fenêtre
    //Méthodes pour les boutons de l'accueil
    public void openLightUpSolver() throws IOException
    {
            stage.setResizable(true);
            stage.setHeight(338);
            stage.setWidth(800);

            stage.centerOnScreen();
            

            App.newScene("grid");
    }

    public void openSatSolver() throws IOException
    {
            stage.setResizable(true);
            App.newScene("satSolver");
    }
    
}
