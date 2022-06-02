package satsolver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DPLL {

    
        public int n; // nombre de clause
        public int l; // nombre de variable
        public int z; // nombre de litteraux isoles (taille du tableau)
        public boolean absurde; // Il y a un absurde dans les clauses
        public String tab[]; // Liste des clauses
        public int var[]; // Liste des valeurs des litteraux 0 ou 1(var[i] = litteral i+1 : ex = var[2] = n°3 = 0 ou 1 )
        public String isoles[]; // Liste des litteraux isole (a mettre a jour)
        public static void main (String[] args) throws InterruptedException, IOException{
            //Preparer les constructeur et lire le fichiers DIMACS
            DPLL sat = new DPLL("./Dimac.txt");
            boolean sortie = sat.dpll();

        }

        
        public String resultat () throws IOException, InterruptedException{
            boolean sortie = this.dpll();
            String res = "";
            int i = 0;
            if(sortie){
                while(i < this.l){
                    if(this.var[i] == 0){
                        res = res + " -"+(i+1);
                    }
                    else if(this.var[i] == 1){
                        res = res + " " + (i+1);
                    }
                    i++;
                }
                return res;
            }
            return "Insatisfaisable";
        }
    //zone constructeurs
        public DPLL (String fichier) throws IOException{
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

        public DPLL ( int n2, int l2, int z2,boolean absurde2,String tab2[],int var2[], String[] isoles2 ){
            this.n = n2; 
            this.l = l2; 
            this.z = z2;
            this.tab = new String[n];
            int i = 0;
            while(i < n){
                this.tab[i] = tab2[i];
                i++;
            }
            this.absurde = false;
            this.var= new int[l]; 
            i = 0;
            while (i < l){
                var[i] = var2[i];
                i++;
            }
            this.isoles = isoles2;
        }
        
        //algorithme dpll : si renvoie false alors il n'y a pas de solution
        //                  si renvoie true alors renvoie la solution
        public boolean dpll () throws InterruptedException, IOException{
            boolean estModele = false; // variable local
            if(this.absurde){ // l'ensemble des clauses contient une clause vide (soit une clause mise a 0 donc il ne peut pas y avoir de solution)
                return false;
            }
            else if (this.n == 0){ //L'ensemble est vide donc il y a une solution
                return true;
            }
            else{
                suppContenu();
                if(!suppIsole() ){
                    if(!resUnitaire()){
                        //La variable choisit doit etre la meme peut etre verifier ici
                        int i =0;
                        while(i < l && var[i] != 2){ // On cherche une variable non instancié
                            i++;
                        }
                        String[] copie = tab;                      
                        estModele = choix(i, 0, copie) || choix(i, 1, copie); //On relance les DPll avec les valeurs 0 et 1
                    }
                    else{
                        estModele = this.dpll(); //Si il y a changement on relance le DPLL 
                    }
    
                }
                else{
                    estModele = this.dpll(); //Si il y a changement on relance le DPLL 
                }
            }

            return !this.absurde & estModele; // non contient absurde ET il y a un modele
    
        }
        
        //Supprime les clauses sontenu dans d'autre
        public void suppContenu() throws IOException{
            int i = 0;
            int j = 0;
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
    
        //Supprime les clauses contenant des litteraux isoles (ajouter l'appel a DPLL)
        public boolean suppIsole() throws IOException{
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
    
        //applique la resolution unitaire
        public boolean resUnitaire() throws NumberFormatException, IOException{
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
    
        public boolean choix(int pos, int val, String[] copie) throws InterruptedException, IOException{            
            //Si on trouve une variable à instancier
            DPLL neo = new DPLL(this.n, this.l, this.z,this.absurde,copie,this.var, this.isoles);
            neo.maj(pos, val);            
            if(neo.dpll()){
                this.tab = neo.tab;
                this.n = neo.n;
                this.var = neo.var;
                this.z = neo.z;
                this. l= neo.l;
                this.absurde = neo.absurde;
                return true ;
            }
            
            this.absurde = neo.absurde;
            return false;            
        }
    
        //Done
        //supprime un element du tabelau et remonte les autres
        public void suppTab(int k) throws IOException{
            int j = k+1;
            while(j < this.n){
                this.tab[k] = this.tab[j];
                k++;
                j++;
            }
            this.n--;
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
                nega[0] = "-" + (i+1);
                val[0] = ""+(i+1);
    
                pos = false;
                neg = false;
    
                j = 0;
                while(j < n & !pos){
                    pos = estContenu(val, tab[j].split(" "));
                    j++;
                }
    
                j=0;
                while(j < n & !neg){
                    neg = estContenu(nega, tab[j].split(" "));
                    j++;
                }
    
                if(!neg & pos) {
                    isoles[z] = val[0]; 
                    var[i] = 1; //mettre la variable a 1 car elle est positive
                    i++; 
                    z++;                
                }
                else if(neg & !pos) {
                    isoles[z] = nega[0]; 
                    var[i] = 0; //mettre la variable a 0 car elle est négative
                    i++; 
                    z++;
                }
                else{
                    i++;
                }
            }
        }
    
        //Mettre a jour toute les clauses
        public void maj(int pos, int valeur) throws IOException{
            int i = 0;
            int j = 0;
            if(pos < this.l){
                this.var[pos] = valeur;
            }
            
            while(j < l){
                i = 0;
                if(this.var[j] != 2){ // Si la variable est instancié
                    String[] calc = {""+(j+1)};
                    String[] nega = {"-"+(j+1)};

                    if(this.var[j] == 0){ // si la variable est instancié à 0
                        while(i < n){ //Pour toute les clauses
                            if (estContenu(calc,this.tab[i].split(" "))){ //Si la variable est positive (a = 0)
                                //Juste enlever le litteral
                                int k = 0;
                                String sol = "";
                                String[] ligne =  this.tab[i].split(" ");
                                while(k < ligne.length ){
                                    
                                    if(!calc[0].equals(ligne[k])){
                                        if(sol == ""){ // Le premier element n'obeit pas aux memes regles d'écriture
                                            sol = ligne[k];
                                        }
                                        else{
                                            sol = sol +" "+ ligne[k]; // on ajoute " x" à la solution
                                        }
                                    }
                                    k++;
                                }
                                this.tab[i] = sol; // tab[i] moins le litteral 
                            }
                            else if (estContenu(nega,this.tab[i].split(" "))){ //Si la variable est négative (-a = -0 = 1)
                                //Suppression de la clause car : (1 | a | b | ... = 1) et (1 & A & B  = A & B )
                                this.suppTab(i);
                                // on reste au meme point car le tableau remonte
                                i--;
                            }
                            i++;
                        }
                    }
                    else if(this.var[j] == 1){ // si la variable est instancié à 1
                        while(i < this.n){ //Pour toute les clauses
                            if (estContenu(calc,this.tab[i].split(" "))){ //Si la variable est positive (a = 1)
                                //Pour les memes raison qu'au-dessus
                                this.suppTab(i); 
                                i--;
                            }
                            else if (estContenu(nega,this.tab[i].split(" "))){ //Si la variable est négative (-a = -1 = 0)
                                //Juste enlever le litteral
                                int k = 0;
                                String sol = "";
                                String[] ligne =  this.tab[i].split(" ");
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
                                this.tab[i] = sol; // tab[i] moins le litteral
                            }
                            i++;
                        }
                    }
                }
                j++;
            }
            this.maj();
        }
    
        //Met a jour la variable absurde pour dire si il existe une clause vide 
        public void maj() throws IOException{
            
            int i = 0;
            while(i < this.n){
                while(i < this.n && !this.tab[i].equals("")){
                    i++;
                }
                this.absurde = this.absurde || (i != this.n);                
                if(i < this.n && !this.tab[i].equals("")){
                    this.suppTab(i);
                    i--;   
                }
                i++;
            }


        }
    
    
    }