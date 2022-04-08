/* 
    Classe pour manipuler les formules
*/

package formalisation;
import java.io.*;

import lecteur.LecteurFormule;


public class Formule {
    private String formule;
    private int nbVarDif;

    public static char[] caractereVar = {'1','2','3','4','5','6','7','8','9', '0'};
    public static char[] simplification = {' ','X','a', 'A', '(', ')', '*', '^', '&', 'x'};
    public static char[] disjonction = {'+','|','v'};
    public static char[] conjonction = {'*', '^', '&'};
    public static char[] negation = {'!', '-'};

    private int[] variable;

/*
        Constructeur avec paramètre
*/
    public Formule(String s){
        formule = s;
        nbVarDif = nbVariable();
        remplirListeVariable();

    }


/*
        Constructeur sans paramètre
*/
    public Formule(){
        formule = "";
        nbVarDif = 0;
        variable = new int[0];
    }



/*
    Permet de simplifier la formule afin de faciliter son écriture en un format DIMACS
*/
    public String supprimerSuperflue(){
        String t ="";
        int i =0;
        while(i < formule.length()){
            if(appartient(formule.charAt(i),disjonction)){
                t = t+ ' ';
            }else if(formule.charAt(i) == '!'){
                t = t+ '-';
            }else if(!appartient(formule.charAt(i), simplification)){
                t = t+ formule.charAt(i);
            }
            i++;
        }
        return t;
    }




/* 
    Renvoie le nombre de variable de la formule
*/
    public void simplifierDisjonction(){
        int i = 0;
        while(i< variable.length){
            if(appartient(-variable[i], variable, variable.length)){
                formule = "";
                variable = new int[0];
                nbVarDif = 0;
            }
            i++;
        }
    }


/* 
    Renvoie le nombre de variable de la formule
*/
    public int nbVariable(){
        int[] tab= new int[100];
        int posTab = 0;

        int i = 0;
        int actuel = -1;

        while(i < formule.length()){
            if(appartient(formule.charAt(i), caractereVar)){
                if(actuel == -1){
                    actuel = Character.getNumericValue(formule.charAt(i));
                }else{
                    actuel = actuel*10 + Character.getNumericValue(formule.charAt(i));
                }
            }else if(appartient(formule.charAt(i), disjonction) ||  appartient(formule.charAt(i), conjonction)){
                if(!appartient(actuel,tab,posTab)){
                    tab[posTab] = actuel;
                    posTab ++;
                }
                actuel = -1;
            }
            i++;
        }
        if(!appartient(actuel,tab,posTab) && actuel != -1){
            tab[posTab] = actuel;
            posTab ++;
        }

        return posTab;
    }



/* 
    Remplis la liste des variables
*/
public void remplirListeVariable(){
    int[] tab= new int[100];
    int posTab = 0;

    int i = 0;
    int actuel = -1;
    boolean neg = false;

    while(i < formule.length()){
        if(appartient(formule.charAt(i), caractereVar)){
            if(actuel == -1){
                actuel = Character.getNumericValue(formule.charAt(i));
            }else{
                actuel = actuel*10 + Character.getNumericValue(formule.charAt(i));
            }
        }else if(appartient(formule.charAt(i), negation)){
            neg = true;

        }else if(appartient(formule.charAt(i), disjonction) ||  appartient(formule.charAt(i), conjonction)){
            if(neg && !appartient(-actuel,tab,posTab)){
                tab[posTab] = -actuel;
                posTab ++;
                neg = false;
            }else if(!neg && !appartient(actuel,tab,posTab)){
                tab[posTab] = actuel;
                posTab ++;
            }
            actuel = -1;
        }
        
        i++;
    }
    if(neg && !appartient(-actuel,tab,posTab) && actuel != -1){
        tab[posTab] = -actuel;
        posTab ++;
    }else if(!neg && !appartient(actuel,tab,posTab) && actuel != -1){
        tab[posTab] = actuel;
        posTab ++;
    }

    variable = new int[posTab];
    int j = 0;
    while(j < posTab){
        variable[j] = tab[j];
        j++;
    }

}




/* 
    Verifie si un caractère fait partie d'une liste de caractère
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
    Verifie si un int fait partie d'une liste de int. posTab indique où la liste est finis, pasTab est forcément inférieur à la liste
*/
    public boolean appartient(int x, int[] liste, int posTab){
        int i = 0;
        while(i<posTab){
            if(x == liste[i]){
                return true;
            }
            i++;
        }
        return false;

    }



/* 
    Recupère le nombre de clause de la formule ainsi que le nombre de variable (la plus grande variable)
*/
    public String nbClauseEtVariable(){
        int i = 0;

        LecteurFormule lect = new LecteurFormule(formule);
        lect.demarrer();
        while(!lect.finDeSequence()){
            i++;
            lect.avancer();
        }
        String resultat = "" + nbVarDif +" " + i;
        return resultat;
    }



/*
    Transforme notre formule disjontive en une formule DIMACS (voir le sujet du projet si vous ne voyez pas à quoi ça ressemble)
*/
    public void ecrireFormule() throws IOException{
        File fichier = new File("fichierDIMACS.txt");
            if (!fichier.exists()) {
                fichier.createNewFile();
            }
        File cheminAbsoluDuFichier = fichier.getAbsoluteFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(cheminAbsoluDuFichier));

        LecteurFormule lect = new LecteurFormule(formule);

        lect.demarrer();
        bw.write("p cnf " + nbClauseEtVariable());
        while(!lect.finDeSequence()){
            Formule f = new Formule(lect.elementCourant());
            bw.write("\n" + f.supprimerSuperflue());
            lect.avancer();
        }
        bw.close();
        System.out.println("Sauvegarde de la formule dans le fichier suivant : " + cheminAbsoluDuFichier);


    }




/*
    Prend une variable, puis fait la conjonction entre la formule et la variable.
*/
    public String conjonction(int variable){
        if(nbVarDif==0){
            formule = (""+formule + variable);
        }else{
            formule = (formule + " * " + variable);
        }
        nbVarDif = nbVariable();
        return formule;
    }


/*
    Prend une autre formule, puis fait la conjonction entre la formule actuel et la nouvelle.
*/
    public String conjonction(Formule formule2){

        if(nbVarDif==0){
            formule = (""+formule + formule2.getFormule());
        }else{
            formule = (formule + " * " + formule2.getFormule());
        }
        nbVarDif = nbVariable();
        return formule;
    }


/*
    Prend une variable, puis fait la disjonction entre la formule et la variable.
*/
    public String disjonction(int variable){

        if(nbVarDif==0){
            formule = (""+formule+ variable);
        }else{
            formule = (formule  + " + " + variable);
        }
        nbVarDif = nbVariable();
        return formule;
    }


/*
    Prend une autre formule, puis fait la disjonction entre la formule actuel et la nouvelle.
*/
    public String disjonction(Formule formule2){

        if(nbVarDif==0){
            formule = (""+formule+ formule2.getFormule());
        }else if(formule2.nbVariable() != 0){
            formule = (formule  + " + " + formule2.getFormule());
        }
        nbVarDif = nbVariable();
        return formule;
    }



    public String getFormule(){
        return formule;
    }




    public Formule distribution(){

        return this;
    }
    
}