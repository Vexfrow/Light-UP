package jeu;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import formalisation.Grille;
import satsolver.*;

public class gridGenerator {

    private int taille;
    public Grille grid;
    private final int count0 = 1;
    private final int countM1 = 10;
    private final int countM2 = 40;
    private final int count1 = 2;
    private final int count2 = 2;
    private final int count3 = 2;
    private final int count4 = 2;

    private int compteur0 = 0;
    private int compteurM1 = 0;
    private int compteurM2 = 0;
    private int compteur1 = 0;
    private int compteur2 = 0;
    private int compteur3 = 0;
    private int compteur4 = 0;

    private int compteur3et4 = 0;

    public gridGenerator(int taille){
        this.taille = taille;
        grid = new Grille(taille);
    }


    public void generate() throws InterruptedException, IOException{
        String resultat = "Insatisfaisable";

        while(resultat.equals("Insatisfaisable")){
            int i = 1;

            while(i <= taille*taille){
                int random = nbRandom();
                int j = 0;
                while(!isPossible(random, i) && j < 10){
                    random = nbRandom();
                    j++;
                }

                if(j < 10){
                    grid.setValues(random, i);
                    i++;
                }else{
                    i = 1;
                    resetCompteur();
                }

            }
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/ui/dimacsCourant.txt"));
            bw.write(grid.allRules().formuleDIMACS());
            bw.flush();
            bw.close();
            DPLL sat = new DPLL("src/main/resources/ui/dimacsCourant.txt");
            resultat = sat.resultat();

        }
    }

    private void resetCompteur(){
        compteur0 = 0;
        compteurM1 = 0;
        compteurM2 = 0;
        compteur1 = 0;
        compteur2 = 0;
        compteur3 = 0;
        compteur4 = 0;
    }

    private boolean isPossible(int value, int position) throws InterruptedException{
        boolean compteur;

        switch(value){

            case(-1) :
                    compteur = compteurM1 < countM1;
                    compteurM1++;
                    break;
            case(0) :
                    compteur = compteur0 < count0;
                    compteur0++;
                    break;
            case(1) :
                    compteur = compteur1 < count1;
                    compteur1++;
                    break;
            case(2) :
                    compteur = compteur2 < count2;
                    compteur2++;
                    break;
            case(3) :
                    compteur = compteur3 < count3;
                    compteur3++;
                    break;
            case(4) :
                    compteur = compteur4 < count4;
                    compteur4++;
                    break;

            default :
                    compteur = compteurM2 < countM2;
                    compteurM2++;
                    break;
        }

        return (compteur && isPossibleR3(value, position));
    }



    private boolean isPossibleR3(int value, int position){
        return (grid.adjacenteUnique(position).length >= value);
    }


    private int nbRandom(){
        double random = new Random().nextDouble();
        int res;

        if(random < 0.1){
            res = -1;
        }else if(random < 0.4){
            res = -2;
        }else if(random < 0.7){
            res = 4;
        }else if(random < 0.85){
            res = 3;
        }else if(random < 0.9){
            res = 2;
        }else if(random < 0.95){
            res = 1;
        }else{
            res = 0;
        }

        return res;
    }


    public static void main(String[] args) throws InterruptedException, IOException{

        gridGenerator g = new gridGenerator(7);
        g.generate();

        int i =1;
        int col = 0;
        int ligne = 0;
        int count = 0;

        while(ligne < g.taille){
            while(col < g.taille){
                System.out.print("|\t");
                System.out.print(g.grid.getValues(i)+ "\t");
                if(g.grid.getValues(i) == 3 || g.grid.getValues(i) == 4){
                    count++;
                }
                col++;
                i++;
            }

            System.out.print("| \n");

            int l = 0;
            while(l < g.taille*16){
                System.out.print("-");
                l++;
            }
            System.out.print("-\n");
            ligne++;
            col = 0;
        }
        System.out.print("Count : " + count + '\n');

    }


    
}
