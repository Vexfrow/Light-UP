/* 
    Classe pour manipuler les formules
*/

package formalisation;

import lecteur.LecteurFormule;


public class Formule {

    private String[] tabVar;
    private int posVar;

    private Clause[] tabClause;
    private int posTabClause;

    public static char[] caractereVar = {'1','2','3','4','5','6','7','8','9', '0'};
    public static char[] simplification = {' ','X','a', 'A', '(', ')', '*', '^', '&', 'x'};
    public static char[] disjonction = {'+','|','v'};
    public static char[] conjonction = {'*', '^', '&'};
    public static char[] negation = {'!', '-'};


/*
    Constructeur avec paramètre
    Crée un objet formule à partir du string formule fournis
*/
    public Formule(String s){
        tabVar = new String[100];
        posVar = 0;

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

        tabVar = new String[100];
        posVar = 0;
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
                    tabVar[posVar] = c.tabVariable[j];
                    posVar++;
                }
                j++;
            }
            i++;
        }
    }





/* 
    Verifie si un caractère appartient à une liste de caractère
*/
    public boolean appartient(char x, char[] liste){
        int i = 0;
        while(i<liste.length){
            if(x == liste[i]){
                return true;
            }
            i++;
        }
        return false;
    }



    public boolean appartient(String x){
        int i = 0;
        while(i<posVar){
            if(x.equals(tabVar[i])){
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
        String formuleDIMACS = ("p cnf " + posVar + " " + posTabClause +'\n');
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
        tabClause[posTabClause] = c;
        posTabClause++;
    }


    // public void conjonction(Formule c){
    //     int i = 0;
    //     while(i < c.posTabClause){
    //         if(!c.tabClause[i].appartientTabClause(tabClause, posTabClause)){
                
    //         }

    //     }
    //     tabClause[posTabClause] = c;
    //     posTabClause++;
    // }

    
    
}
