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
        elementActuel = null;
    }


    //On se place sur la première formule 
    public void demarrer() {
        i = 0;
        avancer();
    }


    
    public void avancer() {
        elementActuel = "";
        while(i < formule.length() && formule.charAt(i) != '*' && formule.charAt(i) != '&' && formule.charAt(i) != '^' ){
            elementActuel = elementActuel + formule.charAt(i);
            i++;
        }
        if(i < formule.length()){
            i++;
        }
    }


    
    public boolean finDeSequence() {
        return (i == formule.length() && elementActuel.equals(""));
    }


    
    public String elementCourant() {
        return elementActuel;
    }


}