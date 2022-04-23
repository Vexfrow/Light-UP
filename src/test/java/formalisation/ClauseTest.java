package formalisation;

import org.junit.Test;

import static org.junit.Assert.*;

public class ClauseTest {

    @Test
    public void toStringTest(){
        Clause c = new Clause();
        c.disjonction(19);
        c.disjonction(9);
        c.disjonction(3);


        Clause c2 = new Clause();
        c2.disjonction(-1);
        c2.disjonction(-2);
        c2.disjonction(-3);

        assertTrue(c.toString().equals("19 9 3 "));
        assertTrue(c2.toString().equals("-1 -2 -3 "));
    }



    @Test
    public void remplirListesVariableTest(){
        String s = "(-20 + -27)";
        String s1 = "(-1 + -2)";

        Clause c = new Clause(s);
        Clause c2 = new Clause(s1);

        assertTrue(c.tabVariable[1] == -27);
        assertTrue(c.tabVariable[0] == -20);

        assertTrue(c2.tabVariable[0] == -1);
        assertTrue(c2.tabVariable[1] == -2);

        assertTrue(c2.tabVarDiff[0] == 1);
        assertTrue(c2.tabVarDiff[1] == 2);
    }



    @Test
    public void equivalent(){
        String s = "(-20 + -27)";
        String s1 = "(-1 + -2)";
        String s2 = "(-2 + -1)";
        String s3 = "(-2 + -3 + -1)";

        Clause c = new Clause(s);
        Clause c1 = new Clause(s1);
        Clause c2 = new Clause(s2);
        Clause c3 = new Clause(s3);

        assertTrue(c.equivalent(c));
        assertTrue(!c.equivalent(c1));
        assertTrue(c1.equivalent(c2));
        assertTrue(!c2.equivalent(c3));

    }


    @Test
    public void appartientTabClause(){
        String s = "(-20 + -27)";
        String s1 = "(-9 + -2)";
        String s2 = "(-2 + -1)";
        String s3 = "(-2 + -8 + -1)";
        String s4 = "(-27 + -20)";

        Clause c = new Clause(s);
        Clause c1 = new Clause(s1);
        Clause c2 = new Clause(s2);
        Clause c3 = new Clause(s3);
        Clause c4 = new Clause(s4);

        Clause[] tabC = new Clause[3];
        tabC[0] = c;
        tabC[1] = c1;
        tabC[2] = c2;


        assertTrue(c.appartientTabClause(tabC, tabC.length));
        assertTrue(c1.appartientTabClause(tabC, tabC.length));
        assertTrue(c2.appartientTabClause(tabC, tabC.length));
        assertTrue(!c3.appartientTabClause(tabC, tabC.length));
        assertTrue(c4.appartientTabClause(tabC, tabC.length));
    }

    
}
