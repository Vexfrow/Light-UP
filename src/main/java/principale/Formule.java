/* Classe pour manipuler la formule
*/

package principale;
import java.io.*;

import lecteur.LecteurFormule;


public class Formule {
    private String formule;
    public char[] caractereVar = {'1','2','3','4','5','6','7','8','9', '0'};
    public char[] simplification = {' ', 'X', 'A', '(', ')', '*', '^', '&'};

    
    public Formule(String s){
        formule = s;
    }



/*
    Permet de simplifier la formule afin de la rendre 
*/
    public String simplification(String formule){
        String t ="";
        int i =0;
        while(i < formule.length()){
            if(formule.charAt(i) == '+' || formule.charAt(i) == '|' || formule.charAt(i) == 'v'){
                t = t+ ' ';
            }else if(formule.charAt(i) == '!'){
                t = t+ '-';
            }else if(!verifListe(formule.charAt(i), simplification)){
                t = t+ formule.charAt(i);
            }
            i++;
        }
        return t;
    }



/* 
Renvoie la plus haute variable d'une clause
*/
    public int maxClause(String formule){
        int i = 0;
        int actuel = 0;
        int max = 0;

        while(i < formule.length()){
            if(verifListe(formule.charAt(i), caractereVar)){
                actuel = actuel*10 + Character.getNumericValue(formule.charAt(i));
                
            }else if(formule.charAt(i) == '+' || formule.charAt(i) == '|' || formule.charAt(i) == 'v'){
                if(actuel > max){
                    max = actuel;
                }
                actuel = 0;
            }
            i++;
        }
        if(actuel > max){
            max = actuel;
        }

        return max;
    }



/* 
Verifie si un caractère fait partie d'une liste de caractère
*/
    public boolean verifListe(char x, char[] liste){
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
Recupère le nombre de clause de la formule ainsi que le nombre de variable (la plus grande variable)
*/
    public String nbClauseEtVariable(){
        int i = 0;
        int max = 0;

        LecteurFormule lect = new LecteurFormule(formule);
        lect.demarrer();
        while(!lect.finDeSequence()){
            if(max < maxClause(lect.elementCourant())){
                max = maxClause(lect.elementCourant());
            }
            i++;
            lect.avancer();
        }
        String resultat = "" + max +" " + i;
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
            bw.write("\n" +simplification(lect.elementCourant()));
            lect.avancer();
        }
        bw.close();
        System.out.println("Sauvegarde de la formule dans le fichier suivant : " + cheminAbsoluDuFichier);


    }

    
}
