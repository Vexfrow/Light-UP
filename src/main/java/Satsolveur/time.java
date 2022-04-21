package Satsolveur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class time {

    
        public int n; // nombre de clause
        public int l; // nombre de variable
        public int z; // nombre de litteraux isoles (taille du tableau)
        public boolean absurde; // Il y a un absurde dans les clauses

        public String tab[]; // Liste des clauses
        public int var[]; // Liste des valeurs des litteraux 0 ou 1(var[i] = litteral i+1 : ex = var[2] = n°3 = 0 ou 1 )
        public String isoles[]; // Liste des litteraux isole (a mettre a jour)
        public static void main (String[] args) throws InterruptedException, IOException{
            //Preparer les constructeur et lire le fichiers DIMACS
            time sat = new time("./formulePlusSimple5");
            int i = 0;
            sat.afficher("./save.txt", false, "Tableau d'origine");
            System.out.println("Il y a une solution = " + sat.dpll());
            i = 0;
            sat.afficher("./save.txt", true,"Fin de Programme");          
            System.out.println("valeur d'absurde = " + sat.absurde);
             while(i < sat.l){
                 System.out.println("var[" + (i+1)+ "] " + sat.var[i]);
                 i++;
             }

        }
    
        public time (String fichier) throws IOException{
            File file = new File(fichier);
            BufferedReader buffer = new BufferedReader(new FileReader(file));
            String[] param = buffer.readLine().split(" ");
            this.l = Integer.parseInt(param[2]);
            this.n = Integer.parseInt(param[3]);
            String ligne;
            this.tab = new String[this.n];
            this.var = new int[this.l];
            this.z = 0;
            this.isoles  = new String[this.l];
            int i = 0;
            int j = 0;
            ligne = buffer.readLine();
            System.out.println("l = "+ this.l);
            while(ligne != null && i !=n){
                tab[i] = "";
                j = 0;
                while(j < (ligne.length()-2)){
                    tab[i] = tab[i] + ligne.charAt(j);
                    j++;
                }
                i++;
                ligne = buffer.readLine();
            }
            int g = 0;
            while (g < this.l){
                isoles[g] = "";
                g++;
            }            
            this.absurde = false;
            int  k = 0;
            while(k < this.l){
                this.var[k] = 2;
                k++;
            }
            buffer.close();
        }


        public time ( int n2, int l2, int z2,boolean absurde2,String tab2[],int var2[], String[] isoles2 ){
            n = n2; 
            l = l2; 
            z = z2;
            absurde = absurde2;
            tab = tab2;
            var= var2;
            isoles = isoles2;
        }
        
        //algorithme dpll : si renvoie false alors il n'y a pas de solution
        //                  si renvoie true alors renvoie la solution
        public boolean dpll () throws InterruptedException, IOException{
            boolean estModele = false; // variable local
            System.out.println("Dpll()");
            if(this.absurde){ // l'ensemble des clauses contient une clause vide (soit une clause mise a 0 donc il ne peut pas y avoir de solution)
                System.out.println("sortie par absurde");
                return false;
            }
            else if (this.n == 0){ //L'ensemble est vide donc il y a une solution
                return true;
            }
            else{
                suppContenu();
                afficher("./save.txt", true, "Apres SuppContenu");
                if(!suppIsole() ){
                    if(!resUnitaire()){
                        //La variable choisit doit etre la meme peut etre verifier ici
                        int i =0;
                        while(i < l && var[i] != 2){ // On cherche une variable non instancié
                            i++;
                        }                        
                        estModele = choix(i, 0) || choix(i, 1); //On relance les DPll avec les valeurs 0 et 1
                        System.out.println("estModele = " + estModele);
                    }
                    else{
                        afficher("./save.txt", true, "avec resUnitaire");
                        System.out.println("ca bouge");
                        estModele = this.dpll(); //Si il y a changement on relance le DPLL 
                    }
    
                }
                else{
                    afficher("./save.txt", true, "avec suppIsole");
                    estModele = this.dpll(); //Si il y a changement on relance le DPLL 
                }
            }

            return !this.absurde & estModele; // non contient absurde ET il y a un modele
    
        }
        //Done
        public void suppContenu(){
            int i = 0;
            int j = 0;
            //System.out.println("on est a SuppContenu");
            while(i < n){
                j =  0;
    
                //Conversion des lignes en tableau de int
                String[] s = tab[i].split(" ");    
                while(j < n){
                    if(i == j){ // on ne travaille pas sur la meme case
                        j++;
                    }
                    else{
    
                        //Conversion des lignes en tableau de int
                        String[] p = tab[j].split(" ");
                        if(estContenu(s, p))
                        {
                            suppTab(j);
                            if( j < i){ // si la case [i] remonte on remonte i
                                i--;
                            }
                        }
                        else{
                            j++;
                        }
                    }
                }
                i++;
            }
        }
   
        //Done
        //version avec des tableau de string
        public boolean  estContenu(String[] inclus, String[] dans){ 
            int l = inclus.length;
            int m = dans.length;
            int i = 0;
            int j = 0;
            boolean contient = true;
            while(i < l & contient){
                j = 0 ;
                while( j < m){
                    if(inclus[i].equals(dans[j])){
                        contient = true;
                        j = m;
                    }
                    else{
                        contient = false;
                        j++;
                    }
                }
                i++;
    
            }
            return contient;
        }
    
        //Done
        //Supprime les clauses contenant des litteraux isoles (ajouter l'appel a DPLL)
        public boolean suppIsole(){
            //System.out.println("on est a SuppIsole"); 
            isoleList();
            int i = 0;
            int j = 0;
            String[] iso = new String[1];
            boolean estChange = false;
            while( i < z){
                iso[0] = isoles[i];
                j = 0;
    
                while(j < n){
                    if(estContenu(iso, tab[j].split(" "))){ //Suppression des clauses contenant des litteraux isolés
                        suppTab(j);
                        j--;
                        estChange = true; 
                    }
                    j++;
                }
                i++;
            }
            maj();
                
            return estChange;
        }
    
        //Done
        //applique la resolution unitaire
        public boolean resUnitaire(){
            int i = 0;
            String sol = "";
            boolean estChange = false;
            String[] unit = new String[this.n];
            int j = 0;
            int m = 0;
            while(j < this.n){ // On parcours le tableau et on cherche la liste des classues unitaires
                String[] s = tab[j].split(" ");
                if(s.length == 1){
                    unit[m] = s[0];
                    m++;
                }
                j++;
            }
            while(i < m){
                sol = "";
                if( unit[i].charAt(0) == '-'){
                    for(int k = 1; k < unit[i].length(); k++){ // on prend la "valeur absolue de x"
                        sol = sol + unit[i].charAt(k);
                    }
                    maj(Integer.parseInt(sol) - 1, 0);  // la variable x est en position x-1
                    estChange = true;
                }
                else{
                    sol = unit[i]; 
                    maj(Integer.parseInt(sol) - 1, 1); // la variable x est en position x-1
                    estChange = true;
                }
                i++;
            }

            return estChange;
        }
    
        public boolean choix(int pos, int val) throws InterruptedException, IOException{
            System.out.println("\nOn est au début de choix avec " + val);
            
            //Si on trouve une variable à instancier
                time neo = new time(this.n, this.l, this.z,this.absurde,this.tab,this.var, this.isoles);
                
                neo.maj(pos, val);
                afficher("./save.txt", true, "choix apres MAJ");
                System.out.println("on a fait les calculs de choix avec la variable " + (pos+1) + " et la valeur " + val);
                
                if(neo.dpll()){
                    this.tab = neo.tab;
                    this.n = neo.n;
                    this.var = neo.var;
                    this.z = neo.z;
                    this. l= neo.l;
                    this.absurde = neo.absurde;
                    System.out.println("On sort du choix et on renvoie vrai");
                    return true ;
                }
                System.out.println("On sort du choix et on renvoie faux");
                System.out.println("");
                this.absurde = neo.absurde;
                return false;            
        }
    
        //Done
        //supprime un element du tabelau et remonte les autres
        public void suppTab(int k){
            int j = k+1;
            while(j < n){
                tab[k] = tab[j];
                k++;
                j++;
            }
            n--;
        }
    
        //Done
        //Met à jour la liste des litteraux isolés (nécessaire lors de la suppression des clauses avec litteraux isolés)
        public void isoleList ()
        {
            int j = 0;
            int i = 0;
            boolean pos = false;
            boolean neg = false;
            String[] val = new String[1];
            String[] nega = new String[1];
            while(i < l){
                
                //System.out.println ("i = " +i);
                nega[0] = "-" + (i+1);
                val[0] = ""+(i+1);
    
                pos = false;
                neg = false;
    
                j = 0;
                while(j < n & !pos){
                    pos = estContenu(val, tab[j].split(" "));
                    //System.out.println(val[0] + " = val + pos = " + pos);
                    j++;
                }
    
                j=0;
                while(j < n & !neg){
                    neg = estContenu(nega, tab[j].split(" "));
                    //System.out.println(nega[0] + " = val + pos = " + neg);
                    j++;
                }
    
                if(!neg & pos) {
                    isoles[z] = val[0]; 
                    var[i] = 1; //mettre la variable a 1 car elle est positive
                    i++; 
                    z++;                
                }
                else if(neg & !pos) {
                    System.out.println("z = " +isoles[z]);
                    isoles[z] = nega[0]; 
                    var[i] = 0; //mettre la variable a 0 car elle est négative
                    i++; 
                    z++;
                }
                else{
                    i++;
                }
                //System.out.println(isoles[z]);
            }
        }
    
        //Mettre a jour toute les clauses
        public void maj(int pos, int valeur){
            int i = 0;
            int j = 0;
            if(pos < this.l){
                var[pos] = valeur;
            }
            
            while(j < l){
                
                i = 0;
                if(var[j] != 2){ // Si la variable est instancié
                    String[] calc = {""+(j+1)};
                    String[] nega = {"-"+(j+1)};

                    if(var[j] == 0){ // si la variable est instancié à 0
                        while(i < n){ //Pour toute les clauses
                            if (estContenu(calc,tab[i].split(" "))){ //Si la variable est positive (a = 0)
                                //Juste enlever le litteral
                                int k = 0;
                                String sol = "";
                                String[] ligne =  tab[i].split(" ");
                                while(k < ligne.length ){
                                    // Le premier element n'obeit pas aux memes regles d'écriture
                                    if(!calc[0].equals(ligne[k])){
                                        if(sol == ""){
                                            sol = ligne[k];
                                            
                                        }
                                        else{
                                            sol = sol +" "+ ligne[k]; // on ajoute " x" à la solution
                                        }
                                    }
                                    k++;
                                }
                                if(sol.equals("")){
                                    this.absurde = true;
                                }
                                tab[i] = sol; // tab[i] moins le litteral 
                            }
                            else if (estContenu(nega,tab[i].split(" "))){ //Si la variable est négative (-a = -0 = 1)
                                //Suppression de la clause car : (1 | a | b | ... = 1) et (1 & A & B  = A & B )
                                suppTab(i);
                                // on reste au meme point car le tableau remonte
                                i--;
                            }
                            i++;
                        }
                    }
                    else if(var[j] == 1){ // si la variable est instancié à 1
                        while(i < this.n){ //Pour toute les clauses
                            if (estContenu(calc,tab[i].split(" "))){ //Si la variable est positive (a = 1)
                                //Pour les memes raison qu'au-dessus
                                suppTab(i); 
                                i--;
                            }
                            else if (estContenu(nega,tab[i].split(" "))){ //Si la variable est négative (-a = -1 = 0)
                                //Juste enlever le litteral
                                int k = 0;
                                String sol = "";
                                String[] ligne =  tab[i].split(" ");
                                while(k < ligne.length ){
                                    // Le premier element n'obeit pas aux memes regles d'écriture
                                    if(!nega[0].equals(ligne[k])){
                                        if(sol ==""){
                                            sol = ligne[k];
                                        }
                                        else{
                                            sol = sol +" "+ ligne[k]; // on ajoute " x" à la solution
                                        }
                                    }
                                    k++;
                                }
                                tab[i] = sol; // tab[i] moins le litteral
                            }
                            i++;
                        }
                    }
                }
                j++;
            }
            maj();
        }
    
        //Met a jour la variable absurde pour dire si il existe une clause vide 
        public void maj(){
    
            int i = 0;
            while(i < this.n){
                while(i < this.n && !this.tab[i].equals("")){
                    i++;
                }
                this.absurde = this.absurde || (i != this.n);                
                if(i < this.n && this.tab[i].equals("")  ){
                    suppTab(i);     
                }
            }
        }
    
     public void afficher(String file, boolean append, String ligne) throws IOException{
        BufferedWriter write = new BufferedWriter(new FileWriter(file, append));
        int i =0;
        write.newLine();
        write.write("{");
        write.newLine();
        write.write(ligne);
        write.newLine();
        while(i < this.n){
            write.write(tab[i]);
            write.newLine();
            i++;
        }
        write.write("n "+ this.n);
        write.newLine();
        write.write("l " +this.l);
        write.newLine();
        write.write("}");
        write.newLine();
        write.flush();
        write.close();
     }
}
