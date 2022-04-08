package ui;

import java.io.IOException;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class testController{

    private Stage stage;
    private Scene scene;
    //private Parent root;

    @FXML
    private void initialize() {
        
    }


    @FXML
    private void showGrid(ActionEvent event) throws IOException {
            int SIZE = 7;
            int length = SIZE;
            int width = SIZE;
            GridPane root = new GridPane();  
            Button finish = new Button();  

            for(int y = 0; y < length; y++){
                for(int x = 0; x < width; x++){

                    Random rand = new Random();
                    int rand1 = rand.nextInt(2);

                    // Create a new TextField in each Iteration
                    TextField tf = new TextField();
                    tf.setPrefHeight(50);
                    tf.setPrefWidth(50);
                    tf.setAlignment(Pos.CENTER);
                    tf.setEditable(false);
                    tf.setText("(" + rand1 + ")");

                    // Iterate the Index using the loops
                    GridPane.setRowIndex(tf,y);
                    GridPane.setColumnIndex(tf,x);    
                    root.getChildren().add(tf);
                    //root.getChildren().add(roote);
                }    
            }
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();    

    }
}
