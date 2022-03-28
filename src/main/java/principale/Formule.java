/* Classe pour manipuler la formule
*/

package principale;
import java.io.*;

import lecteur.LecteurFormule;


public class Formule {
    private String formule;
    public char[] caractereVar = {'1','2','3','4','5','6','7','8','9', '0'};
    public char[] simplification = {' ', 'X', 'A', '(', ')'};

    
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



/* Renvoie le nombre de variable (la plus haute) 

A REVVVOIIIIIRRRRRRRR §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§*/
    public int nbVariable(){
        int i = 0;
        int x = 0;
        int y = 0;
        boolean avant = false;
        while(i < formule.length()){
            if(verifListe(formule.charAt(i), caractereVar) && !avant){
                avant = true;
                x = 10* Character.getNumericValue(formule.charAt(i));
            }else if(verifListe(formule.charAt(i), caractereVar)){
                x = x + Character.getNumericValue(formule.charAt(i));
                avant = false;
            }
            if(x > y){
                y = x;
            }
            i++;
        }

        return y;
    }



/* verifie si un caractère fait partie d'une liste de caractère*/
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



    /* Recupère le nombre de clause de la formule*/
    public int nbClause(){
        int i = 0;
        LecteurFormule lect = new LecteurFormule(formule);
        lect.demarrer();
        while(!lect.finDeSequence()){
            i++;
            lect.avancer();
        }
        return i;
    }


//Transforme notre formule disjontive en une formule DIMACS (voir le sujet du projet si vous ne voyez pas à quoi ça ressemble)
    public void ecrireFormule() throws IOException{
        File fichier = new File("fichierDIMACS.txt");
            if (!fichier.exists()) {
                fichier.createNewFile();
            }
        File cheminAbsoluDuFichier = fichier.getAbsoluteFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(cheminAbsoluDuFichier));

        LecteurFormule lect = new LecteurFormule(formule);

        lect.demarrer();
        bw.write("p cnf " + nbVariable() + " " + nbClause() + "\n");
        while(!lect.finDeSequence()){
            bw.write(simplification(lect.elementCourant()) + " \n");
            lect.avancer();
        }
        bw.close();
        System.out.println("Sauvegarde da a formule dans le fichier suivant : " + cheminAbsoluDuFichier);


    }

    
}
