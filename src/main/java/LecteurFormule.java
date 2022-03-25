/* Il s'agit d'une classe permettant de parcourir la formule conjonctive 
afin de récuperer chaque formule disjonctive la constituant.
*/

public class LecteurFormule implements AccesSequentielModele1<String>{
    private String formule;
    private String elementActuel;


    public LecteurFormule(String formule){
        this.formule = formule;
        elementActuel = null;
    }

    //On se place sur la première formule 
    public void demarrer() {
        int i = 0;
        while(i < formule.length() && formule.charAt(i) != '*'){
            elementActuel = elementActuel + formule.charAt(i);
            i++;
        }
    }

    @Override
    public void avancer() {
    
        
    }

    @Override
    public boolean finDeSequence() {
        return false;
    }

    @Override
    public String elementCourant() {
        return null;
    }



}