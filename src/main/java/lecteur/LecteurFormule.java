package lecteur;

/* Il s'agit d'une classe permettant de parcourir la formule conjonctive 
afin de récuperer chaque formule disjonctive la constituant.
*/

public class LecteurFormule implements AccesSequentielModele1<String>{
    private String formule;
    private String elementActuel;
    private int i;


    public LecteurFormule(String formule){
        this.formule = formule;
    }

    //On se place sur la première formule 
    public void demarrer() {
        i = 0;
        avancer();
    }

    @Override
    public void avancer() {
        elementActuel = "";
        while(i < formule.length() && formule.charAt(i) != '*'){
            elementActuel = elementActuel + formule.charAt(i);
            i++;
        }
        if(i < formule.length()){
            i++;
        }
        
    }

    @Override
    public boolean finDeSequence() {
        return (i == formule.length());
    }

    @Override
    public String elementCourant() {
        return elementActuel;
    }



}