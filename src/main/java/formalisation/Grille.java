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
        Renvoie la liste de toutes les cases adjacentes à la case mise en paramètre.
        Une case est dîtes adjacente à une autre si elles sont sur la même ligne/colonne et ne sont pas séparrées par une case noire (il n'y a pas d'histoire de distance)
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


    /*
        Renvoie la ligne correspondant à la case mise en paramètre
    */
    public int getLigne(int CASE){
        return CASE/nbLignes;
    }


    /*
        Renvoie la colonne correspondant à la case mise en paramètre
    */
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


    /*
        Renvoie l'entier correspondant à la dernière case blanche de la grille
    */  
    private int derniereCaseBlanche(){
        int i = nbColonnes*nbLignes-1;
        while(i >= 0 && grille[getLigne(i)][getColonne(i)] != CASE_BLANCHE){
            i = i-1;
        }
        return i;
    }


    /*
        Renvoie l'entier correspondant à la dernière case noire de la grille
    */  
    private int derniereCaseNoire(){
        int i = nbColonnes*nbLignes-1;
        while(i >= 0 && grille[getLigne(i)][getColonne(i)] == CASE_BLANCHE){
            i = i-1;
        }
        return i;
    }


    /*
        Renvoie la liste de toutes les cases adjacentes unique à la case mise en paramètre.
        Une case est dîtes adjacente unique à une autre si elles sont sur la même ligne/colonne et ne sont pas séparrées par une case noire (les cases doivent être cote à cote)
    */
    public int[] adjacenteUnique(int CASE){
        int posLigne = getLigne(CASE);
        int posCol = getColonne(CASE);

        int pos = 0;
        int[] truc = new int[4];

       if(posLigne-1 >= 0 && grille[posLigne-1][posCol]==CASE_BLANCHE){
            int [] machin = {posLigne-1,posCol};
            truc[pos] = entierGrille(machin);
            pos++;
        }

       if(posLigne+1 < nbLignes && grille[posLigne+1][posCol]==CASE_BLANCHE){
            int [] machin = {posLigne+1,posCol};
            truc[pos] = entierGrille(machin);
            pos++;
        }

        if(posCol-1 >= 0 && grille[posLigne][posCol-1]==CASE_BLANCHE){
            int [] machin = {posLigne,posCol-1};
            truc[pos] = entierGrille(machin);
            pos++;
        }

        if(posCol+1 < nbColonnes && grille[posLigne][posCol+1]==CASE_BLANCHE){
            int [] machin = {posLigne,posCol+1};
            truc[pos] = entierGrille(machin);
            pos++;
        }
        
        int[] res = new int[pos];
        int m = 0;
        while(m < pos){
            res[m] = truc[m];
            m++;
        }

        return res;
    }



    private int[] supprimerElementTab(int[] tab, int e){
        int i = 0;
        int pos = 0;
        int[] res = new int[tab.length-1];
        System.out.println(tab.length);
        System.out.println(res.length);
        while(i < tab.length){
            if(tab[i] != e){
                System.out.println("pos = " + pos);
                System.out.println("i = " + i);
                res[pos] = tab[i];
                pos++;
            }
            i++;
        }
        return res;
    }



    /*
        Ecris dans un fichier la formule correspondant à la première règle
    */  
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
                    bw.write(" + "+adjacent[j]);
                    j++;
                }

                if(i != derniereCaseBlanche()){
                    bw.write(") * ");
                }else{
                    bw.write(")");
                }
            }
            i++;
        }
        bw.close();
        System.out.println("Sauvegarde de la regle UNO dans le fichier suivant : " + cheminAbsoluDuFichier);


    }


    /*
        Ecris dans un fichier la formule correspondant à la quatrième règle
    */  
    public void regle4() throws IOException{
        File fichier = new File("regle4.txt");
        if (!fichier.exists()) {
            fichier.createNewFile();
        }
        File cheminAbsoluDuFichier = fichier.getAbsoluteFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(cheminAbsoluDuFichier));

        int i =0;
        while(i < nbColonnes*nbLignes){
            if(grille[getLigne(i)][getColonne(i)] != CASE_BLANCHE){
                if(i != derniereCaseNoire()){
                    bw.write("(-" +i + ") * ");
                }else{
                    bw.write("(-" +i + ") ");
                }
            }
            i++;
        }
        bw.close();
        System.out.println("Sauvegarde de la regle QUATRO dans le fichier suivant : " + cheminAbsoluDuFichier);
    }



    /*
        Ecris dans un fichier la formule correspondant à la deuxième règle
    */  
    public void regle2() throws IOException{
        File fichier = new File("regle2.txt");
        if (!fichier.exists()) {
            fichier.createNewFile();
        }
        File cheminAbsoluDuFichier = fichier.getAbsoluteFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(cheminAbsoluDuFichier));

        int i =0;
        while(i <= derniereCaseBlanche()){
            if(grille[getLigne(i)][getColonne(i)] == CASE_BLANCHE){
                int j = 0;
                int[] adjacent = adjacent(i);
                while(j < adjacent.length){
                    if(j!= adjacent.length-1 || i != derniereCaseBlanche()){
                        bw.write("(-"+i+" + -"+adjacent[j] + ") * ");
                    }else{
                        bw.write("(-"+i+" + -"+adjacent[j] + ")");
                    }
                    j++;
                }
            }
            i++;
        }
        bw.close();
        System.out.println("Sauvegarde de la regle DUE dans le fichier suivant : " + cheminAbsoluDuFichier);
    }




    /*
        Ecris dans un fichier la formule correspondant à la deuxième règle
    */  
    public void regle3() throws IOException{
        File fichier = new File("regle3.txt");
        if (!fichier.exists()) {
            fichier.createNewFile();
        }
        File cheminAbsoluDuFichier = fichier.getAbsoluteFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(cheminAbsoluDuFichier));

        int i =0;
        while(i <= derniereCaseNoire()){

            // Le cas où c'est une case noir avec un chiffre 0
            if(grille[getLigne(i)][getColonne(i)] == CASE_NOIR_CHIFFRE0){
                int j = 0;
                int[] adjacent = adjacenteUnique(i);
                while(j < adjacent.length){
                    bw.write("(-"+adjacent[j] + " * ");
                    j++;
                }
                bw.write(" ) * ");
            }


            // Le cas où c'est une case noir avec un chiffre 1
            if(grille[getLigne(i)][getColonne(i)] == CASE_NOIR_CHIFFRE1){
                int j = 0;
                int[] adjacent = adjacenteUnique(i);
                bw.write("( ");

                while(j < adjacent.length){
                    bw.write("("+adjacent[j] + " * ");

                    int[] adjSansJ = supprimerElementTab(adjacent, adjacent[j]);
                    int m = 0;
                    while(m < adjSansJ.length){
                        bw.write("-"+adjSansJ[m] + " * ");
                        m++;
                    }
                    bw.write(" ) + ");
                    j++;
                }
                bw.write(" ) * ");
            }


            // Le cas où c'est une case noir avec un chiffre 2
            if(grille[getLigne(i)][getColonne(i)] == CASE_NOIR_CHIFFRE2){
                int j = 0;
                int[] adjacent = adjacenteUnique(i);
                bw.write("( ");

                while(j < adjacent.length){

                    int[] adjSansJ = supprimerElementTab(adjacent, adjacent[j]);
                    int m = 0;
                    while(m < adjSansJ.length){
                        bw.write("("+adjacent[j] + " * ");
                        bw.write(adjSansJ[m] + " * ");

                        int[] adjSansJ2 = supprimerElementTab(adjSansJ, adjSansJ[m]);

                        int n = 0;
                        while(n < adjSansJ2.length){
                            bw.write("-"+adjSansJ2[n] + " * ");
                            n++;
                        }
                        bw.write(" ) + ");
                        m++;
                    }
                    j++;
                }
                bw.write(" ) * ");
            }










            i++;
        }
        bw.close();
        System.out.println("Sauvegarde de la regle TRES dans le fichier suivant : " + cheminAbsoluDuFichier);
    }
    
}