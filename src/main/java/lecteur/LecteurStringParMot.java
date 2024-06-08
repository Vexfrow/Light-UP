package lecteur;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Permet de lire séquentiellement en modèle 1 (traiter/avancer), mots par mots, une chaine de caractères lue sur l'entrée standard ou passée en paramètre.
 *
 * @author (Bolbenes Lucas < lucasbolbenestravail @ gmail.com >)
 * @version (02 / 04 / 2021)
 */
public class LecteurStringParMot implements AccesSequentielModele1<String> {

    private String ligne; // ligne de texte parcourue.
    private int debut; // position du debut du mot courant
    private int fin; // position de la fin du mot courant
    private String separateurs; // liste des séparateurs de mots.

    /**
     * Constructeur d'un lecteur à partir de la lecture d'une ligne sur l'entrée standard.
     * <br>Par défaut, le séparateur de mots est un espace.
     */
    public LecteurStringParMot() {
        this(null);
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in)); // créer BufferedReader un de l'entrée standard
            this.ligne = bf.readLine(); // lire une ligne sur l'entrée standard.
        } catch (IOException e) {
            throw new RuntimeException("ERREUR : Impossible de lire l'entrée standard.");
        }
    }

    /**
     * Constructeur d'un lecteur à partir d'une chaine de caractères.
     * <br>Par défaut, le seul séparateur de mots est un espace.
     */
    public LecteurStringParMot(String lig) {
        // donne des valeurs aux attributs
        ligne = lig;
        debut = 0;
        fin = 0;
        separateurs = " \t\n";
    }

    /**
     * Permet de preparer la lecture.
     * <br>Doit être lancé avant le 1er accès à la sequence.
     */
    public void demarrer() {
        this.debut = 0; // se positionner sur le debut de la chaine de caractères.
        this.fin = 0; // se positionner sur le debut de la chaine de caractères.
        // se positionner sur le premier mot de la chaine de caractères s'il existe.
        if (!finDeSequence()) {
            avancer();
        }
    }

    /**
     * Permet de preparer le mot suivant.
     * <br>Apres avancer <em>finDeSeq()</em> peut renvoyer vraie ;
     * <br>Ne doit pas être lancé si <em>finDeSeq()</em> est vrai.
     */
    public void avancer() {
        this.debut = prochaineLettre(fin); // debut doit correspondre à la position de la première lettre du prochain mot
        this.fin = prochainEspace(debut); // fin doit correspondre à la position du premièr séparateur apres le prochain mot
    }

    /**
     * Renvoie vrai si la fin de la lecture est atteinte.
     *
     * @return true si la fin de la lecture est atteinte.
     */

    public boolean finDeSequence() {
        return (debut >= ligne.length());
    }


    /**
     * Permet de récupérer le dernier mot lu.
     * <br>Ne doit pas être lancé si <em>finDeSeq()</em> est vrai.
     *
     * @return le dernier mot lu.
     */
    public String elementCourant() {
        StringBuilder val = new StringBuilder();
        /*
         * Faire un parcours de ligne de debut a fin
         * avec recopiage des caractères
         * dans la variable val.
         */
        for (int i = debut; i < fin; i++) {
            val.append(ligne.charAt(i));
        }

        return val.toString();
    }

    /**
     * Modifie la liste des séparateurs.
     *
     * @param s une chaine de caractères contenant la liste des caractères considérés comme des séparateurs de mots.
     */
    public void setSeparateurs(String s) {
        this.separateurs = s; // modifie l'attribut "séparateurs".
    }

    /**
     * Renvoie vrai si le caractère "c" est un séparateur.
     *
     * @param c un caractère.
     * @return renvoie vrai si le caractère "c" est considéré comme un séparateur de mots.
     */
    public boolean estSeparateur(char c) {
        /*
         * Faire une recherche de c dans
         * la chaine de caractères séparateurs.
         */
        int i = 0;
        while (i != separateurs.length() && c != separateurs.charAt(i)) {
            i++;
        }
        return i != separateurs.length(); // renvoi d'une expression booléenne vraie si le caractère "c" est dans l'attribut "séparateurs".
    }

    /**
     * Renvoie la position du prochain séparateur de mots à partir de la position i (comprise).
     * <br>Si aucun séparateur n'est trouvé, c'est la longueur de la chaine qui est renvoyée.
     *
     * @param i la position comprise à partir de laquelle le prochain séparateur de mots est recherché.
     * @return la position du premier caractère considéré comme un séparateur de mots.
     * <br>Si aucun séparateur n'est trouvé, c'est la longueur de la chaine qui est renvoyée.
     * Precondition : i <= la longueur de l'attribut ligne.
     */
    public int prochainEspace(int i) {
        /*
         * Faire une recherche dans "ligne" à partir de la position i
         * du 1er caractère séparateur.
         */
        while (i != (ligne.length()) && !estSeparateur(ligne.charAt(i))) {
            i++;
        }
        return i; // renvoi de la position du 1er caractère séparateur apres la position i.
    }

    /**
     * Renvoie la position du prochain caractère non séparateur de mots à partir de la position i (comprise).
     * <br>Si aucun caractère non séparateur n'est trouvé, c'est la longueur de la chaine qui est renvoyée.
     *
     * @param i la position comprise à partir de laquelle est recherché le prochain non séparateur de mots.
     * @return la position du premier caractère considéré comme un non séparateur de mots.
     * <br>Si aucun caractère non séparateur n'est trouvé, c'est la longueur de la chaine qui est renvoyée.
     * Precondition : i <= la longueur de l'attribut ligne.
     */
    public int prochaineLettre(int i) {
        /*
         * Faire une recherche dans "ligne" à partir de la position i
         * du 1er caractère non séparateur.
         */
        while (i < ligne.length() && estSeparateur(ligne.charAt(i))) {
            i++;
        }
        return i; // renvoi de la position du 1er caractère non séparateur apres la position i.
    }

    /**
     * Renvoie la fin de la ligne à partir du 1er caractère suivant le mot courant.
     * <br>Attention : ne change pas le mot courant.
     *
     * @return la fin de la ligne à partir du 1er caractère suivant le mot courant.
     */

    public String finLigne() {
        return ligne.substring(prochainEspace(fin));
    }


    /**
     * Renvoie un tableau constitue des mots d'une chaine de caractères.
     *
     * @param s           la chaine de caractère dont on veut récupérer un tableau de mots.
     * @param separateurs la liste des séparateurs de mots.
     * @return un tableau constitue des mots de la chaine de caractères "s".
     */

    public static ArrayList<String> decouper(String s, String separateurs) {
        ArrayList<String> resultat = new ArrayList<>();
        LecteurStringParMot lecteur = new LecteurStringParMot(s);
        lecteur.setSeparateurs(separateurs);
        /*
         * Utiliser un LecteurStringParMot de la chaine "s"
         * (dont on aura modifié la liste des séparateurs)
         * pour parcourir la chaine "s" et ajouter les mots
         * dans le tableau résultat.
         */
        lecteur.demarrer();
        while (!lecteur.finDeSequence()) {
            lecteur.avancer();
            resultat.add(lecteur.elementCourant());
        }

        return resultat;
    }

}
