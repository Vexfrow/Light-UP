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


/*
    Prend un tableau et un élément contenue dans le tableau et renvoie le tableau sans cet élément
*/ 
    private int[] supprimerElementTab(int[] tab, int e){
        int i = 0;
        int pos = 0;
        int[] res = new int[tab.length-1];
        while(i < tab.length){
            if(tab[i] != e){
                res[pos] = tab[i];
                pos++;
            }
            i++;
        }
        return res;
    }




/*
    Renvoie la formule correspondant à la première règle
*/  
    public String regle1(){

        Formule resRegle1 = new Formule();
        int i =0;
        while(i < nbColonnes*nbLignes){
            String resCase = "";

            if(grille[getLigne(i)][getColonne(i)] == CASE_BLANCHE){
                Formule resC = new Formule("(" +i);

                int j = 0;
                int[] adjacent = adjacent(i);

                while(j < adjacent.length){
                    resC.disjonction(adjacent[j]);
                    j++;
                }
                resCase = resCase + resC.getFormule()+")";

                Formule resCaseF = new Formule(resCase);
                resRegle1.conjonction(resCaseF);
            }
            i++;
        }
        return resRegle1.getFormule();

    }




/*
    Renvoie la formule correspondant à la quatrième règle
*/  
    public String regle4() throws IOException{
        Formule resRegle4 = new Formule();

        int i =0;
        while(i < nbColonnes*nbLignes){
            if(grille[getLigne(i)][getColonne(i)] != CASE_BLANCHE){
                Formule caseN = new Formule("(-" + i +")");
                resRegle4.conjonction(caseN);
            }
            i++;
        }
        return resRegle4.getFormule();
    }



/*
    Renvoie la formule correspondant à la deuxième règle
*/  
    public String regle2() throws IOException{
        Formule resRegle3 = new Formule();
        int i =0;
        while(i <= nbColonnes*nbLignes){
            if(grille[getLigne(i)][getColonne(i)] == CASE_BLANCHE){
                int j = 0;
                int[] adjacent = adjacent(i);
                while(j < adjacent.length){
                    Formule resCase = new Formule("(-"+i+" + -"+adjacent[j] + ")");
                    resRegle3.conjonction(resCase);
                    j++;
                }
            }
            i++;
        }
        return resRegle3.getFormule();
    }






    /*
        Ecris dans un fichier la formule correspondant à la troisième règle
    */  
    public String regle3() throws IOException{
        Formule resRegle3 = new Formule();
        int i =0;
        while(i <= nbColonnes*nbLignes){
            Formule res = new Formule();
            
            // Le cas où c'est une case noir avec un chiffre 0
            if(grille[getLigne(i)][getColonne(i)] == CASE_NOIR_CHIFFRE0){
                Formule resCase = new Formule();
                int j = 0;
                int[] adjacent = adjacenteUnique(i);
                while(j < adjacent.length){
                    Formule casj = new Formule("-"+adjacent[j]);
                    resCase.conjonction(casj);
                    j++;
                }
                res = resCase;
            }


            // // Le cas où c'est une case noir avec un chiffre 1
            // if(grille[getLigne(i)][getColonne(i)] == CASE_NOIR_CHIFFRE1){
            //     int j = 0;
            //     res = "(";
            //     int[] adjacent = adjacenteUnique(i);

            //     while(j < adjacent.length){
            //         String resCase = "("+adjacent[j];

            //         int[] adjSansJ = supprimerElementTab(adjacent, adjacent[j]);
            //         int m = 0;
            //         while(m < adjSansJ.length){
            //             resCase = f.conjonction(resCase, "-"+adjSansJ[m]);
            //             m++;
            //         }
            //         res = f.disjonction(res, resCase+")");
            //         j++;
            //     }
            //     res= res+")";
            // }




            // // Le cas où c'est une case noir avec un chiffre 2
            // if(grille[getLigne(i)][getColonne(i)] == CASE_NOIR_CHIFFRE2){
            //     int j = 0;
            //     int[] adjacent = adjacenteUnique(i);
            //     bw.write("( ");

            //     while(j < adjacent.length){

            //         int[] adjSansJ = supprimerElementTab(adjacent, adjacent[j]);
            //         int m = 0;
            //         while(m < adjSansJ.length){
            //             bw.write("("+adjacent[j] + " * ");
            //             bw.write(adjSansJ[m] + " * ");

            //             int[] adjSansJ2 = supprimerElementTab(adjSansJ, adjSansJ[m]);

            //             int n = 0;
            //             while(n < adjSansJ2.length){
            //                 bw.write("-"+adjSansJ2[n] + " * ");
            //                 n++;
            //             }
            //             bw.write(" ) + ");
            //             m++;
            //         }
            //         j++;
            //     }
            //     bw.write(" ) * ");
            // }



            // Le cas où c'est une case noir avec un chiffre 4
            if(grille[getLigne(i)][getColonne(i)] == CASE_NOIR_CHIFFRE4){
                Formule resCase = new Formule();
                int j = 0;
                int[] adjacent = adjacenteUnique(i);
                while(j < adjacent.length){
                    resCase.conjonction(adjacent[j]);
                    j++;
                }
                res = resCase;
            }
            

            resRegle3.conjonction(res);
            i++;
        }

        return resRegle3.getFormule();
    }
    
}