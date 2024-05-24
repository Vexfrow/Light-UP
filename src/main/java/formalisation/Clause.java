package formalisation;

import java.util.ArrayList;

public class Clause {

    //Tableau des variables (variables pos != variable négatif) ; Ainsi que que l'indicateur de sa taille.
    public ArrayList<Integer> tabVariable;

    //Tableau des variables différentes (variables pos == variable négatif) ; Ainsi que l'indicateur de sa taille.
    public ArrayList<Integer> tabVarDiff;

    //Différents tableaux de caractères qui correspondent aux différentes manières d'écrire la formule. 
    //Exemple : On peut représenter la disjonction par '+', '|' ou 'v'
    private static char[] caractereVar = {'1','2','3','4','5','6','7','8','9', '0'}; //TODO : Se baser sur le code ASCII
    private static char[] disjonction = {'+','|','v'};
    private static char[] negation = {'!', '-'};




    /*
        Permet de créer un objet clause vide.
    */
    public Clause(){
        tabVariable = new ArrayList<>();
        tabVarDiff = new ArrayList<>();
    }

    /*
        Permet de créer un objet clause à partir d'un String qui représente cette clause.
        Exemple : (x + x)
    */
    public Clause(String clauseText){
        this();
        remplirListesVariable(clauseText);
    }



    /*
        Remplis la liste des variables de la clause à partir d'un String.
        Algorithme : On parcourt chaque caractère du String,
        si c'est une variable (représenté sous forme d'un int) -> On la récupère
        sinon (c'est une des représentations de la disjonction) -> On regarde si la variable que l'on a récupérée est déjà présente ou non dans les deux tableaux. Si ce n'est pas le cas, on l'ajoute.
    */
    public void remplirListesVariable(String formule){
        int i = 0;
        int actuel = 0;
        boolean vide = true;
        boolean neg = false;

        while(i < formule.length()){
            if(appartient(formule.charAt(i), caractereVar)){
                if(vide){
                    actuel = Character.getNumericValue(formule.charAt(i));
                    vide = false;
                }else{
                    actuel = actuel*10 + Character.getNumericValue(formule.charAt(i));
                }

            }else if(appartient(formule.charAt(i), negation)){
                neg = true;

            }else if(appartient(formule.charAt(i), disjonction)){
                if(neg && !appartientTabVar(-actuel)){
                    tabVariable.add(-actuel);
                    neg = false;
                }else if(!neg && !appartientTabVar(actuel)){
                    tabVariable.add(actuel);
                }

                if(!appartientTabVarDiff(actuel)){
                    tabVarDiff.add(actuel);
                }
                vide = true;
            }
            
            i++;
        }
        if(neg && !appartientTabVar(-actuel) && !vide){
            tabVariable.add(-actuel);
        }else if(!neg && !appartientTabVar(actuel) && !vide){
            tabVariable.add(actuel);
        }
        if(!appartientTabVarDiff(actuel) && !vide){
            tabVarDiff.add(actuel);
        }

    }



    /* 
        Verifie si un caractère appartient à une liste de caractère
        Algorithme : On parours la liste mise en paramètre et on compare avec l'élément mis en paramètre
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


    /* 
        Verifie si une variable appartient à la liste de variable
        Algorithme : On parours la liste des variables et on compare avec la variable mise en paramètre
    */
    public boolean appartientTabVar(int x){
        return tabVariable.contains(x);
    }



    /* 
        Verifie si une variable appartient à la liste de variable
        Algorithme : On parours la liste des variables différentes et on compare avec la variable mise en paramètre
    */
    public boolean appartientTabVarDiff(int x){
        return tabVarDiff.contains(x);
    }

    /* 
        Verifie si une clause appartient à une liste de clause
        Algorithme : On parours la liste des clauses et on compare avec la clause mise en paramètre
    */

    public boolean appartientTabClause(Clause[] x, int posTabC){
        int i = 0;
        while(i<posTabC){
            if(equals(x[i])){
                return true;
            }
            i++;
        }
        return false;

    }



    /*
        Permet de savoir si deux clauses sont équivalentes. Deux clauses sont dîtes équivalentes si elles ont exactement les mêmes variables, l'ordre importe peu.
        Algorithme : On parcours la liste des variables de la clause actuelle et on regarde si ces variables sont dans la liste des variables de la clause mise en paramètre.
        On compare seulement si les deux clauses ont la même taille.
    */
    //todo : Override "equals" directement ?
    public boolean equals(Clause c){
        boolean equal;
        if(c.tabVariable.size() == this.tabVariable.size()){
            equal = true;
            int i = 0;
            int size = c.tabVariable.size();
            while(i < size && appartientTabVar(c.tabVariable.get(i))){
                i++;
            }
            if(i < size){
                equal = false;
            }
        }else{
            equal = false;
        }

        return equal;
    }

    /*
        Permet de représenter l'objet Clause sous forme d'un String
    */
    public String toString(){
        StringBuilder res = new StringBuilder();
        for(int e : tabVariable){
            res.append(e).append(" ");
        }
        return res.toString();
    }


    /*
        Permet de faire la disjonction d'une variable avec une clause.
        Algorithme : Si la variable n'est pas déjà dans la clause, on l'ajoute à la liste des variables. Idem pour le tableau de variables différentes.
    */
    public void disjonction(int var){
        if(!appartientTabVar(var)){
            tabVariable.add(var);

            if(!appartientTabVarDiff(var)){
                tabVarDiff.add(Math.abs(var));
            }
        }
    }


    
}
