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

    
}
