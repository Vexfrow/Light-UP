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
        Grille f = new Grille("Truc", 3, 3);
        int[] test = {0,0};
        int[] test1 = {1,0};
        int[] test2 = {0,1};
        int[] test3 = {1,2};

        assertTrue(f.entierGrille(test) == (0));
        assertTrue(f.entierGrille(test1)== (3));
        assertTrue(f.entierGrille(test2)== (1));
        assertTrue(f.entierGrille(test3)== (5));
    }

}
