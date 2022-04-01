package formalisation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Grille {

    private int[][] grille;
    private int CASE_NOIR_SANS_CHIFFRE = -1;
    private int CASE_BLANCHE = -2;
    private int CASE_NOIR_CHIFFRE0 = 0;
    private int CASE_NOIR_CHIFFRE1 = 1;
    private int CASE_NOIR_CHIFFRE2 = 2;
    private int CASE_NOIR_CHIFFRE3 = 3;
    private int CASE_NOIR_CHIFFRE4 = 4;
    private int nbLignes;
    private int nbColonnes;



    /*  
    On part du principe que les grilles sont carrées
        -1 -> Case noir sans chiffre
        -2 -> Case blanche
        0,1,2,3,4 -> Case noir avec un chiffre

        Les cases sont numérotées de 0 à n
    */
    public Grille(int[][] grille){
        this.grille = grille;
        nbColonnes = grille.length;
        nbLignes = nbColonnes;


    }


    /* 
        Création d'une grille à partir d'une definition de la grille sous forme d'un String 
    */ 
    public Grille(String definition, int lignes, int colonnes){
        grille = new int[colonnes-1][lignes-1];
        nbLignes = lignes;
        nbColonnes = colonnes;
        remplirGrille(definition);

    }


    /*
        Permet de remplir une grille à partir de sa définition sous forme d'un String
    */
    private void remplirGrille(String definition) {
        //TODO

        
    }


    /*
        Renvoie la liste de toutes les cases adjacentes à la case mise en paramètre
    */
    public int[] adjacent(int CASE){
        int posLigne = getLigne(CASE);
        int posCol = getColonne(CASE);

        int i=posLigne;
        int j=posCol;

        int pos = 0;
        int[] truc = new int[nbLignes+nbColonnes];


        j = posCol-1;

        while(j >= 0 && grille[i][j] == CASE_BLANCHE){
            int [] machin = {i,j};
            truc[pos] = entierGrille(machin);
            pos++;
            
            j = j-1;
        }

        j = posCol+1;

        while(j < nbColonnes && grille[i][j] == CASE_BLANCHE){
            int [] machin = {i,j};
            truc[pos] = entierGrille(machin);
            pos++;
            
            j++;
        }

        i =posLigne+1;
        j = posCol;

        while(i < nbLignes && grille[i][j] == CASE_BLANCHE){
            int [] machin = {i,j};
            truc[pos] = entierGrille(machin);
            pos++;
        
            i++;
        }

        i = posLigne-1;
        while(i >= 0 && grille[i][j] == CASE_BLANCHE){
            int [] machin = {i,j};
            truc[pos] = entierGrille(machin);
            pos++;
            
            i = i-1;
        }

        
        int[] res = new int[pos];
        int m = 0;
        while(m < pos){
            res[m] = truc[m];
            m++;
        }

        return res;
    }



    public int getLigne(int CASE){
        return CASE/nbLignes;
    }



    public int getColonne(int CASE){
        return CASE%nbColonnes;
    }


    /*
        Renvoie l'entier correspondant aux coordonnées mis en paramètre
        Liste d'entier à deux élements seulement
    */
    public int entierGrille(int[] CASE){
        return (CASE[0]*nbLignes+CASE[1]);
    }
    

    public void regle1() throws IOException{
        File fichier = new File("regle1.txt");
        if (!fichier.exists()) {
            fichier.createNewFile();
        }
        File cheminAbsoluDuFichier = fichier.getAbsoluteFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(cheminAbsoluDuFichier));

        int i =0;
        while(i < nbColonnes*nbLignes){
            if(grille[getLigne(i)][getColonne(i)] == CASE_BLANCHE){
                bw.write("(" + i);
                int j = 0;
                int[] adjacent = adjacent(i);
                while(j < adjacent.length){
                    bw.write(" | "+adjacent[j]);
                    j++;
                }
                bw.write(" ) & ");
            }
            i++;
        }
        bw.close();
        System.out.println("Sauvegarde de la regle UNO dans le fichier suivant : " + cheminAbsoluDuFichier);


    }

    
}
