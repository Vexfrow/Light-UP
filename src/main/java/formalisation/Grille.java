package formalisation;

import java.io.IOException;

public class Grille {

    private static int CASE_NOIR_SANS_CHIFFRE = -1;
    private static int CASE_BLANCHE = -2;
    private static int CASE_NOIR_CHIFFRE0 = 0;
    private static int CASE_NOIR_CHIFFRE1 = 1;
    private static int CASE_NOIR_CHIFFRE2 = 2;
    private static int CASE_NOIR_CHIFFRE3 = 3;
    private static int CASE_NOIR_CHIFFRE4 = 4;

    private int[][] grille;
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
        (Pas de format définie pour le moment)
    */ 
    public Grille(String definition, int lignes, int colonnes){
        grille = new int[colonnes-1][lignes-1];
        nbLignes = lignes;
        nbColonnes = colonnes;
        remplirGrille(definition);

    }


    /*
        Permet de remplir une grille à partir de sa définition sous forme d'un String
        (Pas de format définie pour le moment)
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
        int[] truc = new int[nbLignes+nbColonnes]; //Crée une liste avec une taille maximale
        //Liste que l'on va remplir plus bas


        j = posCol-1;

        while(j >= 0 && grille[i][j] == CASE_BLANCHE){  //Récupère les cases adjacentes situées à gauche de notre case 
            truc[pos] = entierGrille(i,j);
            pos++;
            
            j = j-1;
        }

        j = posCol+1;

        while(j < nbColonnes && grille[i][j] == CASE_BLANCHE){ //Récupère les cases adjacentes situées à droite de notre case 
            truc[pos] = entierGrille(i,j);
            pos++;
            
            j++;
        }

        i =posLigne+1;
        j = posCol;

        while(i < nbLignes && grille[i][j] == CASE_BLANCHE){ //Récupère les cases adjacentes situées en bas de notre case 
            truc[pos] = entierGrille(i,j);
            pos++;
        
            i++;
        }

        i = posLigne-1;
        while(i >= 0 && grille[i][j] == CASE_BLANCHE){   //Récupère les cases adjacentes situées en haut de notre case
            truc[pos] = entierGrille(i,j);
            pos++;
            
            i = i-1;
        }

        
        int[] res = new int[pos];    //Recrée une liste avec une taille définie et la remplie (pour éviter des éléments vides)
        int m = 0;
        while(m < pos){
            res[m] = truc[m];
            m++;
        }

        return res;
    }


    /*
        Renvoie le numéro de la ligne correspondant à la case mise en paramètre
    */
    public int getLigne(int CASE){
        return CASE/nbLignes;
    }


    /*
        Renvoie le numéro de la colonne correspondant à la case mise en paramètre
    */
    public int getColonne(int CASE){
        return CASE%nbColonnes;
    }


    /*
        Renvoie l'entier correspondant aux coordonnées mises en paramètre
    */
    public int entierGrille(int ligne, int colonne){
        return (ligne*nbLignes+colonne);
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

       if(posLigne-1 >= 0 && grille[posLigne-1][posCol]==CASE_BLANCHE){ //Récupère la case adjacente situé en haut de notre case 
            truc[pos] = entierGrille(posLigne-1, posCol);
            pos++;
        }

       if(posLigne+1 < nbLignes && grille[posLigne+1][posCol]==CASE_BLANCHE){ //Récupère la case adjacente situé en bas de notre case 
            truc[pos] = entierGrille(posLigne+1,posCol);
            pos++;
        }

        if(posCol-1 >= 0 && grille[posLigne][posCol-1]==CASE_BLANCHE){ //Récupère la case adjacente situé à gauche de notre case 
            truc[pos] = entierGrille(posLigne,posCol-1);
            pos++;
        }

        if(posCol+1 < nbColonnes && grille[posLigne][posCol+1]==CASE_BLANCHE){ //Récupère la case adjacente situé à droite de notre case 
            truc[pos] = entierGrille(posLigne,posCol+1);
            pos++;
        }
        
        int[] res = new int[pos]; //Recrée une liste avec une taille définie et la remplie (pour éviter des éléments vides)
        int m = 0;
        while(m < pos){
            res[m] = truc[m];
            m++;
        }

        return res;
    }


/*
    Prend un tableau d'entier ainsi qu'un entier appartenant au tableau, puis renvoie le tableau sans cet entier
    Schèma de recherche
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
    On fait la conjonction, de la disjonction, de toutes les cases adjacentes des cases blanches
*/  
    public String regle1(){

        Formule resRegle1 = new Formule(); //On crée une formule vide où l'on va faire la conjonction de tout nos clauses
        int i =0;
        while(i < nbColonnes*nbLignes){     //On parcours toutes les cases

            if(grille[getLigne(i)][getColonne(i)] == CASE_BLANCHE){ //Si la case est une case blanche
                Formule resC = new Formule("(" +i); //On crée une formule avec la case blanche

                int j = 0;
                int[] adjacent = adjacent(i); //On récupère la liste des cases adjacentes

                while(j < adjacent.length){ //Pour toutes les cases adjacente à la case blanche
                    resC.disjonction(adjacent[j]); //On fait la disjonction de toutes les cases adjacente 
                    j++;
                }
                String resCase = resC.getFormule()+")";  //On "ferme" la clause

                Formule resCaseF = new Formule(resCase);    //On crée une nouvelle formule correspond à la clause de la case blanche
                resRegle1.conjonction(resCaseF);    //On fait la disjonction de cette clause avec les autres 
            }
            i++;
        }
        return resRegle1.getFormule();  //On renvoie la formule correspondant à la règle 1

    }




/*
    Renvoie la formule correspondant à la quatrième règle
    On fait la conjonction de la négation de toutes les cases noires
*/  
    public String regle4() throws IOException{
        Formule resRegle4 = new Formule(); //On crée une formule vide où l'on va faire la conjonction de tout nos clauses

        int i =0;
        while(i < nbColonnes*nbLignes){ //On parcours toutes les cases
            if(grille[getLigne(i)][getColonne(i)] != CASE_BLANCHE){ //Si la case est une case noire
                Formule caseN = new Formule("(-" + i +")"); // On crée une nouvelle formule correspondant à la clause
                resRegle4.conjonction(caseN); // On fait la conjonction de cette clause avec les autres
            }
            i++;
        }
        return resRegle4.getFormule(); //On renvoie la formule correspondant à la règle 4
    }



/*
    Renvoie la formule correspondant à la deuxième règle
    On fait la conjonction, de la disjonction de chaque case blanche et de ses cases adjacentes
*/  
    public String regle2() throws IOException{
        Formule resRegle2 = new Formule(); //On crée une formule vide où l'on va faire la conjonction de tout nos clauses
        int i =0;
        while(i < nbColonnes*nbLignes){ //On parcours toutes les cases
            if(grille[getLigne(i)][getColonne(i)] == CASE_BLANCHE){ //Si la case est une case blanche

                int j = 0;
                int[] adjacent = adjacent(i);//On récupère la liste des cases adjacentes

                while(j < adjacent.length){ //On parcours la liste des cases adjacentes
                    Formule resCase = new Formule("(-"+i+" + -"+adjacent[j] + ")"); //On crée une formule correspondant à la clause
                    resRegle2.conjonction(resCase); //On fait la conjonction de cette clause avec les autres
                    j++;
                }
            }
            System.out.println(i);
            i++;
        }
        return resRegle2.getFormule(); //On renvoie la formule correspondant à la règle 3
    }






    /*
        Ecris dans un fichier la formule correspondant à la troisième règle
        On fait la conjonction, de la disjonction de toutes les possibilitées d'agencement de chaque cases noires avec un chiffre
    */  
    public String regle3() throws IOException{
        Formule resRegle3 = new Formule();
        int i =0;
        while(i < nbColonnes*nbLignes){
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
                res.conjonction(resCase);
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


            // Le cas où c'est une case noir avec un chiffre 3
            if(grille[getLigne(i)][getColonne(i)] == CASE_NOIR_CHIFFRE3){
                Formule resCase = new Formule();
                Formule f = new Formule("(");
                Formule f2 = new Formule(")");
                int[] adjacent = adjacenteUnique(i);
                if(adjacent.length == 3){
                    resCase.conjonction(f);
                    for(int n = 0; n < adjacent.length; n++){
                        resCase.conjonction(adjacent[n]);
                    }
                    resCase.conjonction(f2);

                }else{
                    Formule resF = new Formule(f.getFormule());
                    int j = 0;
                    while(j < adjacent.length){
                        resF.disjonction(-adjacent[j]);  
                        j++;
                    }
                    resF.conjonction(f2);
                    resCase.conjonction(resF);
                    j = 0;
                    while(j < adjacent.length){
                        int[] adjSansJ = supprimerElementTab(adjacent, adjacent[j]);
                        for(int m = 0; m < adjSansJ.length; m++){
                            resF = new Formule(f.getFormule());
                            resF.disjonction(adjacent[j]);
                            resF.disjonction(adjSansJ[m]);
                            resF.disjonction(f2);
                            resCase.conjonction(resF);
                        }
                        j++;
                    }
    
                }
                res.conjonction(resCase);

            }




            // Le cas où c'est une case noir avec un chiffre 4
            if(grille[getLigne(i)][getColonne(i)] == CASE_NOIR_CHIFFRE4){
                Formule resCase = new Formule();
                int j = 0;
                int[] adjacent = adjacenteUnique(i);
                while(j < adjacent.length){
                    resCase.conjonction(adjacent[j]);
                    j++;
                }
                res = new Formule(resCase.getFormule());
            }
            
            resRegle3.conjonction(res);
            i++;
        }

        return resRegle3.getFormule();
    }
    
}