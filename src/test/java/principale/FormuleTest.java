package principale;

import org.junit.Test;
import static org.junit.Assert.*;
import principale.*;

public class FormuleTest {

    @Test
    public void simplificationTest(){
        Formule f = new Formule();
        String t = "(x10 + x20)";
        String c = "( x10  + x20 ) ";
        String cg = "( -x10  + x20 ) ";

        assertTrue(f.simplification(t).equals("10+20"));
        assertTrue(f.simplification(c).equals("10+20"));
        assertTrue(f.simplification(cg).equals("-10+20"));
    }


}
