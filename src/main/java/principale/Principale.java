package principale;
import java.io.IOException;

import principale.Formule;

public class Principale{

//Classe principale du projet

    public static void main (String[] args) throws IOException{
        Formule f = new Formule();
        f.ecrireFormule("(x10 + x20) * (x32 + x62)");

    }


}