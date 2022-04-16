package formalisation;

import org.junit.Test;

import static org.junit.Assert.*;

public class ClauseTest {

    @Test
    public void toStringTest(){
        String[] test = new String[3];
        test[0] = "19";
        test[1] = "9";
        test[2] = "3";

        String[] test2 = new String[3];
        test2[0] = "-0";
        test2[1] = "-2";
        test2[2] = "-3";

        Clause c = new Clause(test, 3);
        Clause c2 = new Clause(test2, 3);


        assertTrue(c.toString().equals("19 9 3 "));
        assertTrue(c2.toString().equals("-0 -2 -3 "));
    }



    @Test
    public void remplirListeVariableTest(){
        String s = "(-20 + -27)";
        String s1 = "(-0 + -2)";

        Clause c = new Clause(s);
        Clause c2 = new Clause(s1);

        assertTrue(c.tabVariable[1].equals("-27"));
        assertTrue(c.tabVariable[0].equals("-20"));

        assertTrue(c2.tabVariable[0].equals("-0"));
        assertTrue(c2.tabVariable[1].equals("-2"));
    }



    @Test
    public void equivalent(){
        String s = "(-20 + -27)";
        String s1 = "(-0 + -2)";
        String s2 = "(-2 + -0)";
        String s3 = "(-2 + -0 + -1)";

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
        String s1 = "(-0 + -2)";
        String s2 = "(-2 + -0)";
        String s3 = "(-2 + -0 + -1)";
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
