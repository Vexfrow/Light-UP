/* 
    Classe pour manipuler les formules
*/

package formalisation;

import lecteur.LecteurFormule;


public class Formule {

    public String[] tabVar;
    public int posTabVar;

    public int[] tabVarDiff;
    public int posTabVarDiff;

    public Clause[] tabClause;
    public int posTabClause;


/*
    Constructeur avec paramètre
    Crée un objet formule à partir du string formule fournis
*/
    public Formule(String s){
        tabVar = new String[100];
        posTabVar = 0;

        tabVarDiff = new int[100];
        posTabVarDiff = 0;

        tabClause = new Clause[1000];
        posTabClause = 0;

        remplirTabClause(s);
        remplirTabVar();
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
            Clause c =  new Clause (lectF.elementCourant());
            if(!c.appartientTabClause(tabClause, posTabClause)){
                tabClause[posTabClause] =  new Clause (lectF.elementCourant());
                posTabClause++;
            }
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
                }
                j++;
            }
            i++;
        }
    }




    /*
        Remplis la liste des variables différentes (negatif == positif)
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
        Permet de verifier si une variable est presente dans la liste des variables différentes
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
        Transforme notre formule disjontive en une formule DIMACS (voir le sujet du projet si vous ne voyez pas à quoi ça ressemble)
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
        Prend une autre formule, puis fait la conjonction entre la formule actuel et la nouvelle.
    */
    public void conjonction(Clause c){
        if(!c.appartientTabClause(tabClause, posTabClause)){
            tabClause[posTabClause] = c;
            posTabClause++;
            remplirTabVar();
            remplirTabVarDiff();
        }
    }


    public void conjonction(Formule f){
        int i = 0;
        while(i < f.posTabClause){
            conjonction(f.tabClause[i]);
            i++;
        }
    }




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
