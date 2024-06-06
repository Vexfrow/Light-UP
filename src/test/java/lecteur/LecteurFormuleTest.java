package lecteur;
import org.junit.Test;

import static org.junit.Assert.*;

public class LecteurFormuleTest {

    @Test
    public void elementCourantTest(){
        String t = "(x10 + x20) * (x32 + x62)";
        LecteurFormule lf = new LecteurFormule(t);

        lf.demarrer();
        assertEquals("(x10 + x20) ", lf.elementCourant());
        lf.avancer();
        assertEquals(" (x32 + x62)", lf.elementCourant());
        lf.avancer();
        assertEquals("", lf.elementCourant());
    }
    
}
