package formalisation;

public class Clause {

    //Tableau des variables (variables pos != variable négatif) ; Ainsi que que l'indicateur de sa taille.
    public int[] tabVariable = new int[100];
    public int posTabVar;

    //Tableau des variables différentes (variables pos == variable négatif) ; Ainsi que que l'indicateur de sa taille.
    public int[] tabVarDiff = new int[100];
    public int posTabVarDiff;

    //Différents tableaux de caractères qui correspondent aux différentes manières d'écrire la formule. 
    //Exemple : On peut représenter la disjonction par '+', '|' ou 'v'
    private static char[] caractereVar = {'1','2','3','4','5','6','7','8','9', '0'};
    private static char[] disjonction = {'+','|','v'};
    private static char[] negation = {'!', '-'};



    /*
        Permet de crée un objet clause à partir d'un String qui représente cette clause.
        Exemple : (x + x)
    */
    public Clause(String clauseText){
        posTabVar = 0;
        posTabVarDiff = 0;
        remplirListesVariable(clauseText);
    }


    /*
        Permet de crée un objet clause vide.
    */
    public Clause(){
        posTabVar = 0;
        posTabVarDiff = 0;

    }




    /* 
        Remplis la liste des variables de la clause à partir d'un String.
        Algorithme : On parcours chaque caractère du String, 
        si c'est une variable (représenté sous forme d'un int) -> On la récupère
        sinon (c'est une des représentation de la disjonction) -> On regarde si la variable que l'on a récuperrée est déjà présente ou non dans les deux tableaux. Si ce n'est pas le cas, on l'ajoute.
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
                    tabVariable[posTabVar] = -actuel;
                    posTabVar ++;
                    neg = false;
                }else if(!neg && !appartientTabVar(actuel)){
                    tabVariable[posTabVar] = actuel;
                    posTabVar ++;
                }

                if(!appartientTabVarDiff(actuel)){
                    tabVarDiff[posTabVarDiff] = actuel;
                    posTabVarDiff++;
                }
                vide = true;
            }
            
            i++;
        }
        if(neg && !appartientTabVar(-actuel) && !vide){
            tabVariable[posTabVar] = -actuel;
            posTabVar ++;
        }else if(!neg && !appartientTabVar(actuel) && !vide){
            tabVariable[posTabVar] = actuel;
            posTabVar ++;
        }
        if(!appartientTabVarDiff(actuel) && !vide){
            tabVarDiff[posTabVarDiff] = actuel;
            posTabVarDiff++;
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
        int i = 0;
        while(i<posTabVar){
            if(x == tabVariable[i]){
                return true;
            }
            i++;
        }
        return false;

    }



    /* 
        Verifie si une variable appartient à la liste de variable
        Algorithme : On parours la liste des variables différentes et on compare avec la variable mise en paramètre
    */
    public boolean appartientTabVarDiff(int x){
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
        Verifie si une clause appartient à une liste de clause
        Algorithme : On parours la liste des clauses et on compare avec la clause mise en paramètre
    */

    public boolean appartientTabClause(Clause[] x, int posTabC){
        int i = 0;
        while(i<posTabC){
            if(this.equivalent(x[i])){
                return true;
            }
            i++;
        }
        return false;

    }



    /*
        Permet de savoir si deux clauses sont équivalentes. Deux clauses sont dîtes équivalentes si elles ont exactement les mêmes varaibles, l'ordre importe peu.
        Algorithme : On parcours la liste des variables de la clause actuels et on regarde si ces variables sont dans la liste de variable de la clause mise en paramètre.
        On compart seulement si les deux clauses ont la même taille.
    */
    public boolean equivalent(Clause c){
        boolean equal;
        if(posTabVar == c.posTabVar){
            equal = true;
            int i = 0;
            while(i < posTabVar && appartientTabVar(c.tabVariable[i])){
                i++;
            }
            if(i < posTabVar){
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
        int i = 0;
        String res = "";
        while(i < posTabVar){
            res = res + tabVariable[i] + " ";
            i++;
        }
        return res;

    }


    /*
        Permet de faire la disjonction d'une variable avec une clause.
        Algorithme : Si la variable n'est pas déjà dans la clause, on l'ajoute à la liste des variables. Idem pour le tableau de variables différentes.
    */
    public void disjonction(int var){
        if(!appartientTabVar(var)){
            tabVariable[posTabVar] = var;
            posTabVar++;

            if(!appartientTabVarDiff(var)){
                if( var < 0){
                    tabVarDiff[posTabVarDiff] = -var;
                }else{
                    tabVarDiff[posTabVarDiff] = var;
                }
                posTabVarDiff++;
            }

        }
    }


    
}
