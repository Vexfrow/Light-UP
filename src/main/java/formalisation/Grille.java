package formalisation;

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
        int res = CASE/nbLignes;

        if(CASE%nbLignes == 0){
            res = res-1;
        }
        return res;
    }


    /*
        Renvoie le numéro de la colonne correspondant à la case mise en paramètre
    */
    public int getColonne(int CASE){
        int res = CASE%nbColonnes-1;

        if(CASE%nbColonnes == 0){
            res = nbColonnes-1;
        }
        return res;
    }


    /*
        Renvoie l'entier correspondant aux coordonnées mises en paramètre
    */
    public int entierGrille(int ligne, int colonne){
        return (ligne*nbLignes+colonne+1);
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
    public Formule regle1(){

        Formule resRegle1 = new Formule();                          //On crée une formule vide où l'on va faire la conjonction de tout nos clauses
        int i =1;
        while(i <= nbColonnes*nbLignes){                             //On parcours toutes les cases
            if(grille[getLigne(i)][getColonne(i)] == CASE_BLANCHE){ //Si la case est une case blanche

                int j = 0;
                int[] adjacent = adjacent(i);                       //On récupère la liste des cases adjacentes

                String[] tabClause = new String[100];
                tabClause[0] = ""+i;
                int posTabClause = 1;


                while(j < adjacent.length){                         //Pour toutes les cases adjacente à la case blanche
                    tabClause[posTabClause] = ""+adjacent[j];       //On fait la disjonction de toutes les cases adjacentes
                    posTabClause++;
                    j++;
                }
                Clause c = new Clause(tabClause, posTabClause);

                resRegle1.conjonction(c);                           //On fait la disjonction de cette clause avec les autres 
            }
            i++;
        }
        return resRegle1;                                           //On renvoie la formule correspondant à la règle 1

    }




/*
    Renvoie la formule correspondant à la quatrième règle
    On fait la conjonction de la négation de toutes les cases noires
*/  
    public Formule regle4(){
        Formule resRegle4 = new Formule();                              //On crée une formule vide où l'on va faire la conjonction de tout nos clauses

        int i =1;
        while(i <= nbColonnes*nbLignes){                                 //On parcours toutes les cases
            if(grille[getLigne(i)][getColonne(i)] != CASE_BLANCHE){     //Si la case est une case noire
                Clause caseN = new Clause("(-" + i +")");               // On crée une nouvelle clause correpondant à la règle pour la case
                resRegle4.conjonction(caseN);                           // On fait la conjonction de cette clause avec les autres dans la formule
            }
            i++;
        }
        return resRegle4;                                               //On renvoie la formule correspondant à la règle 4
    }





/*
    Renvoie la formule correspondant à la deuxième règle
    On fait la conjonction, de la disjonction de chaque case blanche et de ses cases adjacentes
*/  
    public Formule regle2(){
        Formule resRegle2 = new Formule();                                          //On crée une formule vide où l'on va faire la conjonction de tout nos clauses
        int i =1;
        while(i <= nbColonnes*nbLignes){                                             //On parcours toutes les cases
            if(grille[getLigne(i)][getColonne(i)] == CASE_BLANCHE){                 //Si la case est une case blanche

                int j = 0;
                int[] adjacent = adjacent(i);                                       //On récupère la liste des cases adjacentes

                while(j < adjacent.length){                                         //On parcours la liste des cases adjacentes
                    Clause resCase = new Clause("(-"+i+" + -"+adjacent[j] + ")");   //On crée une clause correspondant à la règle pour la case
                    resRegle2.conjonction(resCase);                                 //On fait la conjonction de cette clause avec les autres
                    j++;
                }
            }
            i++;
        }
        return resRegle2;                                                           //On renvoie la formule correspondant à la règle 3
    }






    /*
        Renvoie la formule correspondant à la troisième règle
        On fait la conjonction, de la disjonction de toutes les possibilitées d'agencement de chaque cases noires avec un chiffre
    */  
    public Formule regle3(){
        Formule res = new Formule();
        int i =1;

        while(i <= nbColonnes*nbLignes){
            int typeC = grille[getLigne(i)][getColonne(i)];
            if(typeC != CASE_BLANCHE && typeC != CASE_NOIR_SANS_CHIFFRE && typeC != CASE_NOIR_CHIFFRE0){

                int[] adj = adjacenteUnique(i);

                if(typeC == adj.length){                //Si le numéro de la case correspond au nombre de case adjacente
                    int j = 0;
                    while(j < adj.length){
                        Clause c = new Clause();
                        c.disjonction(adj[j]);
                        j++;
                        res.conjonction(c);
                    }
                }
                
                
                
                else if(typeC == adj.length-1){    //Si il y a une case de + que le numéro indiqué
                    Clause c = new Clause();
                    int j = 0;
                    while(j < adj.length){
                        c.disjonction(-adj[j]);  
                        j++;
                    }

                    res.conjonction(c);

                    j = 0;
                    while(j < adj.length){
                        int[] adjSansJ = supprimerElementTab(adj, adj[j]);
                        for(int m = 0; m < adjSansJ.length; m++){
                            Clause c2 = new Clause();
                            c2.disjonction(adj[j]);
                            c2.disjonction(adjSansJ[m]);
                            res.conjonction(c2);
                        }
                        j++;
                    }
                }
                
                
                
                else if(typeC == adj.length-2){       //Si il y a deux case de + que le numéro indiqué
                    
                    if(typeC == CASE_NOIR_CHIFFRE1){
                        Clause c = new Clause();
                        int j = 0;
                        while(j < adj.length){
                           c.disjonction(adj[j]);  
                            j++;
                        }
                        res.conjonction(c);


                        j = 0;
                        while(j < adj.length){
                            int[] adjSansJ = supprimerElementTab(adj, adj[j]);
                            for(int m = 0; m < adjSansJ.length; m++){
                                c = new Clause();
                                c.disjonction(-adj[j]);
                                c.disjonction(-adjSansJ[m]);
                                res.conjonction(c);
                            }
                            j++;
                        }
                            
                            


                    }else{
                        Clause c = new Clause();

                        int j = 0;
                        while(j < adj.length){
                            int[] adjSansJ = supprimerElementTab(adj, adj[j]);
    
                            int m = 0;
                            while(m < adjSansJ.length){
                                int[] adjSansM = supprimerElementTab(adjSansJ, adjSansJ[m]);
    
    
                                int n = 0;
                                while(n < adjSansM.length){
                                    c = new Clause();
                                    c.disjonction(-adj[j]);
                                    c.disjonction(-adjSansJ[m]);
                                    c.disjonction(-adjSansM[n]);
                                    res.conjonction(c);
    
                                    c = new Clause();
                                    c.disjonction(adj[j]);
                                    c.disjonction(adjSansJ[m]);
                                    c.disjonction(adjSansM[n]);
                                    res.conjonction(c);

                                    n++;
                                }
                                m++;
                            }
                            j++;
                        }
                    }
                }
                
                
                
                else if(typeC == adj.length-3){        //Si il y a deux case de + que le numéro indiqué
                    Clause c = new Clause();
                    int j = 0;
                    while(j < adj.length){
                        c.disjonction(adj[j]);  
                        j++;
                    }
                    res.conjonction(c);

                    j = 0;
                    while(j < adj.length){
                        int[] adjSansJ = supprimerElementTab(adj, adj[j]);
                        for(int m = 0; m < adjSansJ.length; m++){
                            c = new Clause();
                            c.disjonction(-adj[j]);
                            c.disjonction(-adjSansJ[m]);
                            res.conjonction(c);
                        }
                        j++;
                    }
                } 



            }else if(typeC == CASE_NOIR_CHIFFRE0){
                int j = 0;
                int[] adj = adjacenteUnique(i);
                while(j < adj.length){
                    Clause c = new Clause();
                    c.disjonction(-adj[j]);
                    res.conjonction(c);
                    j++;
                }
            }
            i++;
        }

        return res;
    }




    public Formule allRules(){
        Formule allRules = new Formule();
        allRules.conjonction(regle1());
        allRules.conjonction(regle2());
        allRules.conjonction(regle3());
        allRules.conjonction(regle4());

        return allRules;
    }
    
}