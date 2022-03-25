package principale;
import java.io.*;

import lecteur.LecteurFormule;


public class Formule {
    
//1- Prend notre formule sous forme de String
//2 - ???
//3 - Profit
    public Formule(){

    }


    public String simplification(String formule){
        String t ="";
        int i =0;
        while(i < formule.length()){
            if(formule.charAt(i) != ' ' && formule.charAt(i) != 'x' && formule.charAt(i) != '(' && formule.charAt(i) != ')'){
                t = t+ formule.charAt(i);
            }
            i++;
        }
        return t;
    }


//Transforme notre formule disjontive en une formule DIMACS (voir le sujet du projet si vous ne voyez pas à quoi ça ressemble)
    public void ecrireFormule(String f) throws IOException{
        File fichier = new File("fichierDIMACS.txt");
            if (!fichier.exists()) {
                fichier.createNewFile();
            }
            File cheminAbsoluDuFichier = fichier.getAbsoluteFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(cheminAbsoluDuFichier));

        LecteurFormule lect = new LecteurFormule(f);
        lect.demarrer();
        while(!lect.finDeSequence()){
            bw.write(simplification(lect.elementCourant()));
            bw.write('\n');
            lect.avancer();
        }
        bw.write(simplification(lect.elementCourant()));
        bw.close();
        System.out.println("Sauvegarde da a formule dans le fichier suivant : " + cheminAbsoluDuFichier);


    }

    
}
