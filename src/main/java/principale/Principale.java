package principale;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import formalisation.Grille;


public class Principale{

//Classe principale du projet

    public static void main (String[] args) throws IOException{

        int[][] grille = new int[7][7];
        grille[0][0] = -2;
        grille[0][1] = -2;
        grille[0][2] = -2;
        grille[0][3] = -2;
        grille[0][4] = -1;
        grille[0][5] = -2;
        grille[0][6] = -2;

        grille[1][0] = -2;
        grille[1][1] = -2;
        grille[1][2] = -2;
        grille[1][3] = -2;
        grille[1][4] = -2;
        grille[1][5] = -2;
        grille[1][6] = -2;

        grille[2][0] = 1;
        grille[2][1] = -2;
        grille[2][2] = -2;
        grille[2][3] = 1;
        grille[2][4] = -2;
        grille[2][5] = -2;
        grille[2][6] = -2;

        grille[3][0] = -2;
        grille[3][1] = -2;
        grille[3][2] = -1;
        grille[3][3] = -2;
        grille[3][4] = 2;
        grille[3][5] = -2;
        grille[3][6] = -2;

        grille[4][0] = -2;
        grille[4][1] = -2;
        grille[4][2] = -2;
        grille[4][3] = 2;
        grille[4][4] = -2;
        grille[4][5] = -2;
        grille[4][6] = 2;

        grille[5][0] = -2;
        grille[5][1] = -2;
        grille[5][2] = -2;
        grille[5][3] = -2;
        grille[5][4] = -2;
        grille[5][5] = -2;
        grille[5][6] = -2;

        grille[6][0] = -2;
        grille[6][1] = -2;
        grille[6][2] = 2;
        grille[6][3] = -2;
        grille[6][4] = -2;
        grille[6][5] = -2;
        grille[6][6] = -2;



        int[][] grille2 = new int[5][5];
        grille2[0][0] = -2;
        grille2[0][1] = 1;
        grille2[0][2] = -2;
        grille2[0][3] = -2;
        grille2[0][4] = -2;

        grille2[1][0] = -2;
        grille2[1][1] = -2;
        grille2[1][2] = -2;
        grille2[1][3] = -2;
        grille2[1][4] = -1;

        grille2[2][0] = -2;
        grille2[2][1] = -2;
        grille2[2][2] = -2;
        grille2[2][3] = -2;
        grille2[2][4] = -2;

        grille2[3][0] = -1;
        grille2[3][1] = -2;
        grille2[3][2] = -2;
        grille2[3][3] = -2;
        grille2[3][4] = -2;

        grille2[4][0] = -2;
        grille2[4][1] = -2;
        grille2[4][2] = -2;
        grille2[4][3] = 1;
        grille2[4][4] = -2;





        int[][] grille3 = new int[3][3];

        grille3[0][0] = -2;
        grille3[0][1] = -2;
        grille3[0][2] = 1;

        grille3[1][0] = -2;
        grille3[1][1] = -2;
        grille3[1][2] = -2;

        grille3[2][0] = -2;
        grille3[2][1] = -2;
        grille3[2][2] = -2;

        
        Grille g = new Grille(grille);

        // System.out.println(g.regle3());


        File fichier = new File("FormuleFinale.txt");
        if (!fichier.exists()) {
            fichier.createNewFile();
        }
        File cheminAbsoluDuFichier = fichier.getAbsoluteFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(cheminAbsoluDuFichier));


        File fichier2 = new File("fichierDIMACS.txt");
        if (!fichier2.exists()) {
            fichier2.createNewFile();
        }
        File cheminAbsoluDuFichier2 = fichier2.getAbsoluteFile();
        BufferedWriter bw2 = new BufferedWriter(new FileWriter(cheminAbsoluDuFichier2));


        bw.write(g.allRules().getFormule());
        bw2.write(g.allRules().formuleDIMACS());
        bw.close();
        bw2.close();



    }


}