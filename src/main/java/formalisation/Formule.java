package formalisation;

import lecteur.LecteurFormule;


public class Formule {

    //Tableau des variables "differentes" : -x = x dans ce cas là ; Ainsi que que l'indicateur de sa taille
    public int[] tabVarDiff;
    public int posTabVarDiff;

    //Tableau des clauses ; Ainsi que que l'indicateur de sa taille
    public Clause[] tabClause;
    public int posTabClause;


    /* 
        Il s'agit d'une classe ayant pour but de manipuler les formules.
        Une formule est une conjonction de plusieurs clauses
    */



    /*
    Constructeur avec paramètre
    Crée un objet formule à partir du string formule fournis et dont le format ressemble à : (x + x + -x) * (x + -x) * ....
    */
    public Formule(String s){

        tabVarDiff = new int[100];
        posTabVarDiff = 0;

        tabClause = new Clause[1000];
        posTabClause = 0;

        remplirTabClause(s);
        remplirTabVarDiff();


    }


    /*
        Constructeur sans paramètre (Formule vide)
        Crée un objet formule vide 
    */
    public Formule(){
        tabClause = new Clause[1000];
        posTabClause = 0;

        tabVarDiff = new int[100];
        posTabVarDiff = 0;

    }




    /*
        Permet de remplir la liste de clauses à partir d'une formule sous forme d'un string
        Algorithme : On parcours le string grâce à un lecteur de String spécifique (voir la classe "LecteurFormule") qui separt la formule en differente clause
        et on range ces clauses dans le tableau correspondants (ont on augmente la taille)
    */
    private void remplirTabClause(String formule){
        LecteurFormule lectF = new LecteurFormule(formule);

        lectF.demarrer();
        while(!lectF.finDeSequence()){
            Clause c =  new Clause (lectF.elementCourant());
            if(!c.appartientTabClause(tabClause, posTabClause)){
                tabClause[posTabClause] =  new Clause (lectF.elementCourant());
                posTabClause++;
            }
            lectF.avancer();
        }
    }



    /*
        Remplis la liste des variables différentes (variable negatif == variable positif)
        Algorithme : On parcours toutes les variables de toutes les clauses du tableau de clause. Si une variable n'est pas déjà dans le tableau -> On le rajoute
        Sinon on ne fait rien.
    */
    private void remplirTabVarDiff() {
        int i = 0;
        while(i < posTabClause){
            int j = 0;
            Clause c = tabClause[i];
            while(j < c.posTabVarDiff){
                if(!appartient(c.tabVarDiff[j])){
                    tabVarDiff[posTabVarDiff] = c.tabVarDiff[j];
                    posTabVarDiff++;
                }
                j++;
            }
            i++;
        }
    }




    /*
        Permet de verifier si une variable est presente dans la liste des variables différentes
        Algorithme : On parcours le tableau de variable et on compare l'élément courant du tableau avec l'élément mis en paramètre
    */
    public boolean appartient(int x){
        int i = 0;
        while(i<posTabVarDiff){
            if(x == tabVarDiff[i]){
                return true;
            }
            i++;
        }
        return false;
    }




    /*
        Transforme notre formule disjontive en une formule DIMACS.
        Algorithme : On parcours la liste des clauses, que l'on écrit sous forme d'un string.
    */
    public String formuleDIMACS() {
        String formuleDIMACS = ("p cnf " + posTabVarDiff + " " + posTabClause );
        int i = 0;

        while(i < posTabClause){
            formuleDIMACS = formuleDIMACS + '\n' + tabClause[i].toString() + '0';
            i++;
        }
        return formuleDIMACS;
    }




    /*
        Prend une clause, puis fait la conjonction entre la formule et la clause.
        Algorithme : Si la clause n'est pas déjà dans le tableau de clause (voir dans la Classe "CLause.java"), on rajoute cette clause à la liste
    */
    public void conjonction(Clause c){
        if(!c.appartientTabClause(tabClause, posTabClause)){
            tabClause[posTabClause] = c;
            posTabClause++;
            remplirTabVarDiff();
        }
    }


    /*
        Prend une autre formule, puis fait la conjonction entre la formule actuel et la nouvelle.
        Algorithme : On parcours la liste des clauses de la formule passée en paramètre et on fait la conjonction de ces clauses avec la formule actuelle.
    */
    public void conjonction(Formule f){
        int i = 0;
        while(i < f.posTabClause){
            conjonction(f.tabClause[i]);
            i++;
        }
    }


    /*
        Permet d'écrire l'objet formule sous la forme d'un String du même format que celui récupéré en paramètre par le constructeur.
    */

    public String toString(){
        String s = "";
        int i = 0;

        while(i < posTabClause){
            int j = 0;
            s = s+ "(";
            while(j < tabClause[i].posTabVar){
                s = s + tabClause[i].tabVariable[j];
                j++;
                if(j < tabClause[i].posTabVar){
                    s = s + " + ";
                }

            }
            s = s + ")";
            i++;
            if(i < posTabClause){
                s = s + " * ";
            }
        }

        return s;
    }

    
    
}
