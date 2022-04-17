package formalisation;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;

public class FormuleTest {

        Clause c;
        Clause c1;
        Clause c2;
        Clause c3;
        Clause c4;


    @Before
    public void test(){
        String s = "(-20 + -27)";
        String s1 = "(-0 + -2)";
        String s2 = "(-2 + -0)";
        String s3 = "(-2 + -0 + -1)";
        String s4 = "(-27 + -20)";

        c = new Clause(s);
        c1 = new Clause(s1);
        c2 = new Clause(s2);
        c3 = new Clause(s3);
        c4 = new Clause(s4);

    }



    @Test
    public void remplirListeVariableTest(){
        Formule f1 = new Formule("(-20 + -27) * (-0 + -2)");
        Formule f2 = new Formule("(-20 + -27) * (-0 + -2) * (-2 + -0)");
        Formule f3 = new Formule("(-20 + -27) * (-0 + -2) * (-2 + -0 + -1)");

        assert(f1.tabVar[0].equals("-20"));
        assert(f1.tabVar[1].equals("-27"));
        assert(f1.tabVar[2].equals("-0"));
        assert(f1.tabVar[3].equals("-2"));
        assert(f1.posTabVar == 4);

        assert(f2.tabVar[0].equals("-20"));
        assert(f2.tabVar[1].equals("-27"));
        assert(f2.tabVar[2].equals("-0"));
        assert(f2.tabVar[3].equals("-2"));
        assert(f2.posTabVar == 4);

        assert(f3.tabVar[0].equals("-20"));
        assert(f3.tabVar[1].equals("-27"));
        assert(f3.tabVar[2].equals("-0"));
        assert(f3.tabVar[3].equals("-2"));
        assert(f3.tabVar[4].equals("-1"));
        assert(f3.posTabVar == 5);


    }

    @Test
    public void remplirTabClauseTest(){
        Formule f1 = new Formule("(-20 + -27) * (-0 + -2)");
        Formule f2 = new Formule("(-20 + -27) * (-0 + -2) * (-2 + -0)");
        Formule f3 = new Formule("(-20 + -27) * (-0 + -2) * (-2 + -0 + -1)");

        System.out.println(f1.tabClause[0].toString());
        System.out.println(c.toString());

        System.out.println(f1.tabClause[0].posTabVar);
        System.out.println(c.posTabVar);

        assert(f1.tabClause[0].toString().equals(c.toString()));
        assert(f1.tabClause[1].toString().equals(c1.toString()));
        assert(f1.posTabClause == 2);

        assert(f2.tabClause[0].toString().equals(c.toString()));
        assert(f2.tabClause[1].toString().equals(c1.toString()));
        assert(f2.posTabClause == 2);

        assert(f3.tabClause[0].toString().equals(c.toString()));
        assert(f3.tabClause[1].toString().equals(c1.toString()));
        assert(f3.tabClause[2].toString().equals(c3.toString()));
        assert(f3.posTabClause == 3);


    }
    


    public void conjonctionTest(){
        Formule f1 = new Formule("(-20 + -27) * (-0 + -2)");

        assert(f1.posTabClause == 2);

        f1.conjonction(c);
        assert(!(f1.posTabClause == 3));
        assert(f1.posTabClause == 2);

        f1.conjonction(c2);
        assert(!(f1.posTabClause == 3));
        assert(f1.posTabClause == 2);

        f1.conjonction(c4);
        assert(f1.posTabClause == 3);
    }





}
