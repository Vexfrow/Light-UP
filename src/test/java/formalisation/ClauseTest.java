package formalisation;

import org.junit.Test;


import java.util.ArrayList;

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

        assertEquals("19 9 3 ", c.toString());
        assertEquals("-1 -2 -3 ", c2.toString());
    }



    @Test
    public void remplirListesVariableTest(){
        String s = "(-20 + -27)";
        String s1 = "(-1 + -2)";

        Clause c = new Clause(s);
        Clause c2 = new Clause(s1);

        assertEquals(-27, (int)c.tabVariable.get(1));
        assertEquals(-20, (int)c.tabVariable.get(0));

        assertEquals(-1, (int)c2.tabVariable.get(0));
        assertEquals(-2, (int)c2.tabVariable.get(1));

        assertEquals(1, (int)c2.tabVarDiff.get(0));
        assertEquals(2, (int)c2.tabVarDiff.get(1));
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

        assertTrue(c.equals(c));
        assertFalse(c.equals(c1));
        assertTrue(c1.equals(c2));
        assertFalse(c2.equals(c3));

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

        ArrayList<Clause> tabC = new ArrayList<>();
        tabC.add(c);
        tabC.add(c1);
        tabC.add(c2);


        assertTrue(c.appartientTabClause(tabC));
        assertTrue(c1.appartientTabClause(tabC));
        assertTrue(c2.appartientTabClause(tabC));
        assertFalse(c3.appartientTabClause(tabC));
        assertTrue(c4.appartientTabClause(tabC));
    }

    
}
