package Satsolveur;

public class test {
    public static void main(String[] args){
        String st = "Une chaine pleine";
        String ts = "-5";
        String[] ligne = ts.split(" ");
        int i = 0;
        System.out.println("taille = " + ligne.length);
        while(i < ligne.length){
            System.out.println(ligne[i]);
            i++;
        }
        
    }


}
