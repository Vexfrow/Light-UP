/* 
    Classe pour manipuler les formules
*/

package formalisation;

import lecteur.LecteurFormule;


public class Formule {

    public String[] tabVar;
    public int posTabVar;
    private int nbVar;

    public Clause[] tabClause;
    public int posTabClause;


/*
    Constructeur avec paramètre
    Crée un objet formule à partir du string formule fournis
*/
    public Formule(String s){
        tabVar = new String[100];
        posTabVar = 0;
        nbVar = 0;

        tabClause = new Clause[1000];
        posTabClause = 0;
        remplirTabClause(s);
        remplirTabVar();


    }


    /*
    Constructeur sans paramètre (Formule vide)
    Crée un objet formule vide 
    */
    public Formule(){
        tabClause = new Clause[1000];
        posTabClause = 0;
        nbVar = 0;

        tabVar = new String[100];
        posTabVar = 0;
    }




    /*
        Permet de remplir la liste de clauses à partir d'une formule textuelle
    */
    private void remplirTabClause(String formule){
        LecteurFormule lectF = new LecteurFormule(formule);

        lectF.demarrer();
        while(!lectF.finDeSequence()){
            tabClause[posTabClause] =  new Clause (lectF.elementCourant());
            posTabClause++;
            lectF.avancer();
        }
    }



    /*
        Permet de remplir la liste des variables
    */
    private void remplirTabVar() {
        int i = 0;
        while(i < posTabClause){
            int j = 0;
            Clause c = tabClause[i];
            while(j < c.posTabVar){
                if(!appartient(c.tabVariable[j])){
                    tabVar[posTabVar] = c.tabVariable[j];
                    posTabVar++;
                    if(!estNegatif(c.tabVariable[j])){
                        nbVar++;
                    }
                }
                j++;
            }
            i++;
        }
    }




    /*
        Permet de verifier si une variable est presente dans la liste des variables
    */
    public boolean appartient(String x){
        int i = 0;
        while(i<posTabVar){
            if(x.equals(tabVar[i])){
                return true;
            }
            i++;
        }
        return false;
    }



    /*
        Permet de verifier si une variable est presente dans la liste des variables
    */
    public boolean estNegatif(String x){
        if(x.length()>0){
            return (x.charAt(0) == '-');
        }
        return false;
    }




    /*
        Transforme notre formule disjontive en une formule DIMACS (voir le sujet du projet si vous ne voyez pas à quoi ça ressemble)
    */
    public String formuleDIMACS() {
        String formuleDIMACS = ("p cnf " + nbVar + " " + posTabClause +'\n');
        int i = 0;

        while(i < posTabClause){
            formuleDIMACS = formuleDIMACS + tabClause[i].toString() + "\n";
            i++;
        }
        return formuleDIMACS;
    }



    /*
        Prend une autre formule, puis fait la conjonction entre la formule actuel et la nouvelle.
    */
    public void conjonction(Clause c){
        if(!c.appartientTabClause(tabClause, posTabClause)){
            tabClause[posTabClause] = c;
            posTabClause++;
            remplirTabVar();
        }
    }


    public void conjonction(Formule f){
        int i = 0;
        while(i < f.posTabClause){
            conjonction(f.tabClause[i]);
            i++;
        }
    }

    
    
}
