package formalisation;

public class Clause {
    public String[] tabVariable = new String[100];
    public int posTabVar;

    public int[] tabVarDiff = new int[100];
    public int posTabVarDiff;

    private static char[] caractereVar = {'1','2','3','4','5','6','7','8','9', '0'};
    private static char[] disjonction = {'+','|','v'};
    private static char[] negation = {'!', '-'};



    public Clause(String[] tabV, int posTabV){
        tabVariable = tabV;
        posTabVar = posTabV;
        posTabVarDiff = 0;
    }


    public Clause(String clauseText){
        posTabVar = 0;
        posTabVarDiff = 0;
        remplirListesVariable(clauseText);
    }


    public Clause(){
        posTabVar = 0;
        posTabVarDiff = 0;

    }




    /* 
        Remplis la liste des variables de la clause
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
                if(neg && !appartientTabVar("-"+actuel)){
                    tabVariable[posTabVar] = "-"+actuel;
                    posTabVar ++;
                    neg = false;
                }else if(!neg && !appartientTabVar(""+actuel)){
                    tabVariable[posTabVar] = ""+actuel;
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
        if(neg && !appartientTabVar("-"+actuel) && !vide){
            tabVariable[posTabVar] = "-"+actuel;
            posTabVar ++;
        }else if(!neg && !appartientTabVar(""+actuel) && !vide){
            tabVariable[posTabVar] = ""+actuel;
            posTabVar ++;
        }
        if(!appartientTabVarDiff(actuel) && !vide){
            tabVarDiff[posTabVarDiff] = actuel;
            posTabVarDiff++;
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



    /* 
    Verifie si une variable appartient à la liste de variable
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


    public void disjonction(int var){
        tabVariable[posTabVar] = ""+var;
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
