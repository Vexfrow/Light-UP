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
            stage.setTitle("Solveur de Light Up");
            
            App.newScene("grid");
            stage.setHeight(432);
            stage.setWidth(1000);
            stage.centerOnScreen();
    }

    public void openSatSolver() throws IOException
    {
        stage.setResizable(true);
        stage.setTitle("SAT-Solver");
        
        App.newScene("satSolver");
        stage.setHeight(432);
        stage.setWidth(1000);
        stage.centerOnScreen();
    }
    
}
