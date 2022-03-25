package lecteur;
import org.junit.Test;

import lecteur.LecteurFormule;
import static org.junit.Assert.*;

public class LecteurFormuleTest {

    @Test
    public void elementCourantTest(){
        String t = "(x10 + x20) * (x32 + x62)";
        LecteurFormule lf = new LecteurFormule(t);

        lf.demarrer();
        assertTrue(lf.elementCourant().equals("(x10 + x20) "));
        lf.avancer();
        assertTrue(lf.elementCourant().equals(" (x32 + x62)"));
        lf.avancer();
        assertTrue(lf.elementCourant().equals(""));
    }
    
}
