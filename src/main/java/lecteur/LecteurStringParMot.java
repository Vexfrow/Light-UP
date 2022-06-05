package lecteur;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
/**
 * Permet de lire sequentiellement en modele 1 (traiter/avancer), mots par mots, une chaine de caracteres lue sur l'entree standard ou passee en parametre.
 *
 * @author (Bolbenes Lucas <lucasbolbenestravail@gmail.com>)
 * @version (02/04/2021)
 */ 
public class LecteurStringParMot implements AccesSequentielModele1<String>{
    // en public pour l'etape 1 pour les tests. en private sinon.
    public String ligne; // ligne de texte parcourue.
    public int debut; // position du debut du mot courant
    public int fin; // position de la fin du mot courant
    public String separateurs; // liste des separateurs de mots.
    //private String ligne; // ligne de texte parcourue.
    //private int debut; // position du debut du mot courant
    //private int fin; // position de la fin du mot courant
    //private String separateurs; // liste des separateurs de mots.

    /**
     * Constructeur d'un lecteur a partir de la lecture d'une ligne sur l'entree standard.
     * <br>Par defaut, le separateur de mots est un espace.
     */
    public LecteurStringParMot(){
        this(null);
        try{
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in)); // creer BufferedReader un de l'entree standard
            this.ligne = bf.readLine(); // lire une ligne sur l'entree standard.
        }catch(IOException e){
            throw new RuntimeException("ERREUR : Impossible de lire l'entree standard.");
        }
    }

    /**
     * Constructeur d'un lecteur a partir d'une chaine de caracteres.
     * <br>Par defaut, le seul separateur de mots est un espace.
     */
    public LecteurStringParMot(String lig){
        // donne des valeurs aux attributs
        ligne = lig;
        debut = 0;
        fin = 0;
        separateurs = " \t \n";
    }

    /**
     * Permet de preparer la lecture.
     * <br>Doit etre lance avant le 1er acces a la sequence.
     */
    public void demarrer(){
        this.debut = 0; // se positionner sur le debut de la chaine de caracteres.
        this.fin = 0; // se positionner sur le debut de la chaine de caracteres.
        // se positionner sur le premier mot de la chaine de caracteres s'il existe.
        if (! finDeSequence()){
            avancer();
        }
    }

    /**
     * Permet de preparer le mot suivant.
     * <br>Apres avancer <em>finDeSeq()</em> peut renvoyer vrai;
     * <br>Ne doit pas etre lance si <em>finDeSeq()</em> est vrai.
     */
    public void avancer(){
        this.debut = prochaineLettre(fin); // debut doit correspondre a la position de la 1ere lettre du prochain mot
        this.fin = prochainEspace(debut); // fin doit correspondre a la position du 1ere separateur apres le prochain mot
    }

    /**
     * Renvoie vrai si la fin de la lecture est atteinte.
     * @return true si la fin de la lecture est atteinte.
     */
  
    public boolean finDeSequence() {
    	return (debut >= (ligne.length()));
    }
    

    /**
     * Permet de recuperer le dernier mot lu.
     * <br>Ne doit pas etre lance si <em>finDeSeq()</em> est vrai.
     * @return le dernier mot lu.
     */
    public String elementCourant(){
        String val = "";
        /*
         * Faire un parcours de ligne de debut a fin
         * avec recopie des caracteres
         * dans la variable val.
         */
        int c = debut;
        while(c < fin) 
        {
        	val += ligne.charAt(c);
        	c++;
        }
        return val;
    }   

    /**
     * Modifie la liste des separateurs.
     * @param s une chaine de caracteres contenant la liste des caracteres consideres comme des separateurs de mots.
     */
    public void setSeparateurs(String s){
        this.separateurs = s; // modifie l'attribut separateurs.
    }

    /**
     * Renvoie vrai si le caractere c est un separateur.
     * @param c un caractere.
     * @return renvoie vrai si le caracteres c est considere comme un separateur de mots.
     */
    public boolean estSeparateur(char c){
        /*
         * Faire une recheche de c dans 
         * la chaine de caracteres separateurs.
         */
    	int i = 0;
    	while(i != separateurs.length() && c != separateurs.charAt(i)) {
    		i++;
    	}
        return i != separateurs.length(); // renvoi d'une expression booleene vraie si le caractere c est dans l'attribut separateurs.
    }

    /**
     * Renvoie la position du prochain separateur de mots a partir de la position i (comprise).
     * <br>Si aucun separateur n'est trouve, c'est la longueur de la chaine qui est renvoyee.
     * @param i la position comprise a partir de laquelle est recheche le prochain separateur de mots.
     * @return la position du premier caractere considere comme un separateur de mots. 
     * <br>Si aucun separateur n'est trouve, c'est la longueur de la chaine qui est renvoyee.
     * Precondition : i <= la longueur de l'attribut ligne.
     */
    public int prochainEspace(int i){
        /*
         * Faire une recherche dans ligne a partir de la position i 
         * du 1er caractere separateur.
         */
    	while(i != (ligne.length()) && estSeparateur(ligne.charAt(i))==false) 
    	{
    		i++;
    	}
        return i; // renvoi de la position du 1er caractere separateur apres la position i.
    }

    /**
     * Renvoie la position du prochain caractere non separateur de mots a partir de la position i (comprise).
     * <br>Si aucun caractere non separateur n'est trouve, c'est la longueur de la chaine qui est renvoyee.
     * @param i la position comprise a partir de laquelle est recheche le prochain non separateur de mots.
     * @return la position du premier caractere considere comme un non separateur de mots. 
     * <br>Si aucun caractere non separateur n'est trouve, c'est la longueur de la chaine qui est renvoyee.
     * Precondition : i <= la longueur de l'attribut ligne.
     */
    public int prochaineLettre(int i){
        /*
         * Faire une recherche dans ligne a partir de la position i 
         * du 1er caractere non separateur.
         */
    	while(i != (ligne.length()) && estSeparateur(ligne.charAt(i))) 
    	{
    		i++;
    	}
        return i; // renvoi de la position du 1er caractere non separateur apres la position i.
    }

    /**
     * Renvoie la fin de la ligne a partir du 1er caractere suivant le mot courant.
     * <br>Attention : ne change pas le mot courant.
     * @return la fin de la ligne a partir du 1er caractere suivant le mot courant.
     */

    public String finLigne(){
        int i = prochainEspace(fin); // position du 1er caractere suivant le mot courant.
        String s = "";
        /*
         * Faire un parcours partiel jusqu'a la fin de la ligne
         * en recopiant les caracteres dans s.
         */
        while(i < ligne.length()) {
        	s += ligne.charAt(i);
        	i++;
        }
        return s;
    }

    /**
     * Renvoie le nombre de mots contenu dans une chaine de caracteres.
     * @param s la chaine de caractere dont on veut compter les mots.
     * @param sparateurs la liste des separateurs de mots.
     * @return le nombre de mots contenu dans une chaine de caracteres.
     */

    public static int nbMots(String s,String separateurs){
        LecteurStringParMot lect = new LecteurStringParMot(s);
        lect.setSeparateurs(separateurs);
        int nb = 0;
        /*
         * Utiliser un LecteurStringParMot de la chaine s 
         * (dont on aura modifie la liste des separateurs)
         * pour parcourir la chaine s et compter les mots.
         */
        lect.demarrer();
        while( ! lect.finDeSequence()) 
        {
        	lect.avancer();
        	nb++;
        }

        return nb;
    }

    /**
     * Renvoie un tableau constitue des mots d'une chaine de caracteres.
     * @param s la chaine de caractere dont on veut recuperer un tableau de mots.
     * @param sparateurs la liste des separateurs de mots.
     * @return un tableau constitue des mots de la chaine de caracteres s.
     */

    public static String[] decouper(String s,String separateurs)
    {
        int nbMots = nbMots(s,separateurs);
        String[] resultat = new String[nbMots];
        LecteurStringParMot lect = new LecteurStringParMot(s);
        lect.setSeparateurs(separateurs);
        /*
         * Utiliser un LecteurStringParMot de la chaine s
         * (dont on aura modifie la liste des separateurs)
         * pour parcourir la chaine s et ajouter les mots
         * dans le tableau resultat.
         */
        int i = 0;
        while( i < nbMots) 
        {
        	lect.avancer();
        	resultat[i] = lect.elementCourant();
        	i++;
        }
        
        return resultat;
    }

}
