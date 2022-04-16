package formalisation;

public class Clause {
    public String[] tabVariable = new String[100];
    public int posTabVar;

    private static char[] caractereVar = {'1','2','3','4','5','6','7','8','9', '0'};
    private static char[] disjonction = {'+','|','v'};
    private static char[] negation = {'!', '-'};



    public Clause(String[] tabV, int posTabV){
        tabVariable = tabV;
        posTabVar = posTabV;
    }


    public Clause(String clauseText){
        posTabVar = 0;
        remplirListeVariable(clauseText);
    }


    public Clause(){
        posTabVar = 0;

    }




    /* 
        Remplis la liste des variables de la clause
    */
    public void remplirListeVariable(String formule){
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
                if(neg && !appartientTabVar("-"+actuel)){
                    tabVariable[posTabVar] = "-"+actuel;
                    posTabVar ++;
                    neg = false;
                }else if(!neg && !appartientTabVar(""+actuel)){
                    tabVariable[posTabVar] = ""+actuel;
                    posTabVar ++;
                }
                vide = true;
            }
            
            i++;
        }
        if(neg && !appartientTabVar("-"+actuel) && !vide){
            tabVariable[posTabVar] = "-"+actuel;
            posTabVar ++;
        }else if(!neg && !appartientTabVar(""+actuel) && !vide){
            tabVariable[posTabVar] = ""+actuel;
            posTabVar ++;
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


    /* 
    Verifie si une variable appartient à la liste de variable
    */
    public boolean appartientTabVar(String x){
        int i = 0;
        while(i<posTabVar){
            if(x.equals(tabVariable[i])){
                return true;
            }
            i++;
        }
        return false;

    }



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



    public String toString(){
        int i = 0;
        String res = "";
        while(i < posTabVar){
            res = res + tabVariable[i] + " ";
            i++;
        }
        return res;

    }


    
}
