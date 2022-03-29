package principale;

import org.junit.Test;
import static org.junit.Assert.*;

public class FormuleTest {

    /*@Test
    public void simplificationTest(){
        Formule f = new Formule("(x10 + x20)");
        Formule f1 = new Formule("( x10  + x20 ) ");
        Formule f2 = new Formule("( -x10  + x20 ) ");

        assertTrue(f.simplification().equals("10+20"));
        assertTrue(f1.simplification().equals("10+20"));
        assertTrue(f2.simplification().equals("-10+20"));
    }
    */

    @Test
    public void varListeTest(){
        Formule f = new Formule("(x10 + x20)");
        char x = '0';
        char u = 'X';
        char y = '3';
        char r = 'z';

        assertTrue(f.verifListe(x, f.caractereVar));
        assertTrue(f.verifListe(u, f.simplification));
        assertTrue(!f.verifListe(u, f.caractereVar));
        assertTrue(!f.verifListe(r, f.caractereVar));
        assertTrue(f.verifListe(y, f.caractereVar));
    }


    @Test
    public void maxClauseTest(){
        Formule f = new Formule("(x10 + x20)");

        assertTrue(f.maxClause("x10 + x20") == 20);
        assertTrue(f.maxClause("( x10  + x24 ) ") == 24);
        assertTrue(f.maxClause("( -x65  + x20 ) ") == 65);
        assertTrue(f.maxClause("") == 0);
    }


    @Test
    public void nbClauseTest(){
        Formule f = new Formule("(x10 + x20) * ( x10  + x24 ) * ( x10  + x24 )");
        Formule f1 = new Formule("( x10  + x24 ) ");
        Formule f2 = new Formule("( -x65  + x20 ) ");
        Formule f4 = new Formule("");
        
        System.out.println(f.nbClauseEtVariable());
        assertTrue(f.nbClauseEtVariable().equals("24 3"));
        assertTrue(f1.nbClauseEtVariable().equals("24 1"));
        assertTrue(f2.nbClauseEtVariable().equals("65 1"));
        assertTrue(f4.nbClauseEtVariable().equals("0 0"));
    }

}
