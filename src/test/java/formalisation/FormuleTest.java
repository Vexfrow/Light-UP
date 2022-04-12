package formalisation;

import org.junit.Test;

import static org.junit.Assert.*;

public class FormuleTest {

    @Test
    public void simplificationTest(){
        Formule f = new Formule("(x10 + x20)");
        Formule f1 = new Formule("( a10  + a20 ) ");
        Formule f2 = new Formule("( -10  + 20 ) ");

        assertTrue(f.supprimerSuperflue().equals("10 20"));
        assertTrue(f1.supprimerSuperflue().equals("10 20"));
        assertTrue(f2.supprimerSuperflue().equals("-10 20"));
    }
    

    @Test
    public void appartientTest(){
        Formule f = new Formule("(10 + 20)");
        char x = '0';
        char u = 'X';
        char y = '3';
        char r = 'z';

        assertTrue(f.appartient(x, Formule.caractereVar));
        assertTrue(f.appartient(u, Formule.simplification));
        assertTrue(!f.appartient(u, Formule.caractereVar));
        assertTrue(!f.appartient(r, Formule.caractereVar));
        assertTrue(f.appartient(y, Formule.caractereVar));
    }


    @Test
    public void nbVariableTest(){
        Formule f = new Formule("(10 + 20)");
        Formule f1 = new Formule("( 10 ) ");
        Formule f2 = new Formule("( -65  + 20 + 0) ");
        Formule f3 = new Formule("");
        Formule f4 = new Formule("(10 + 20) * ( 10  + 24 ) * ( 10  + 24 )");
        Formule f5 = new Formule("0");

        assertTrue(f.nbVariable() == 2);
        assertTrue(f1.nbVariable() == 1);
        assertTrue(f2.nbVariable() == 3);
        assertTrue(f3.nbVariable() == 0);
        assertTrue(f4.nbVariable() == 3);
        assertTrue(f5.nbVariable() == 1);
    }


    @Test
    public void nbClauseEtVariableTest(){
        Formule f = new Formule("(10 + 20) * ( 10  + 24 ) * ( 10  + 24 )");
        Formule f1 = new Formule("( 10  + 24 ) ");
        Formule f2 = new Formule("( -65  + 20 ) ");
        Formule f4 = new Formule("");
        
        assertTrue(f.nbClauseEtVariable().equals("3 3"));
        assertTrue(f1.nbClauseEtVariable().equals("2 1"));
        assertTrue(f2.nbClauseEtVariable().equals("2 1"));
        assertTrue(f4.nbClauseEtVariable().equals("0 0"));
    }


    @Test
    public void disjonctionTest(){
        Formule f = new Formule("(1 + 2");
        Formule f1 = new Formule("1 + 2");

        f.disjonction(5);
        assertTrue(f.getFormule().equals("(1 + 2 + 5"));

        f.disjonction(5);
        f1.disjonction(5);

        assertTrue(f.getFormule().equals("(1 + 2 + 5 + 5"));
        assertTrue(f1.getFormule().equals("1 + 2 + 5"));

        f.disjonction(f1);

        assertTrue(f.getFormule().equals("(1 + 2 + 5 + 5 + 1 + 2 + 5"));

        
    }



}
