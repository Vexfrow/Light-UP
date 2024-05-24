package formalisation;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GrilleTest {

    private static int[][] grille;
    
    @Before
    public void test(){
        grille = new int[3][3];
        grille[0][0] = -2;
        grille[0][1] = -2;
        grille[0][2] = -2;

        grille[1][0] = -2;
        grille[1][1] = -2;
        grille[1][2] = -1;
        
        grille[2][0] = -2;
        grille[2][1] = -1;
        grille[2][2] = -2;

    }

    @Test
    public void entierGrilleTest(){
        Grille f = new Grille(grille);

        assertEquals((1), f.entierGrille(0, 0));
        assertEquals((4), f.entierGrille(1, 0));
        assertEquals((2), f.entierGrille(0, 1));
        assertEquals((6), f.entierGrille(1, 2));
    }

}
