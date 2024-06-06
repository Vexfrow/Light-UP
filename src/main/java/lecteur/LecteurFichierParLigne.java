package lecteur;

import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class LecteurFichierParLigne implements AccesSequentielModele1<String> {

    public BufferedReader lecteur;
    public String nomFic;
    public String val;

    /**
     * Constructeur d'un lecteur de l'entrée standard.
     */
    public LecteurFichierParLigne() {
        this(null);
    }

    /**
     * Constructeur d'un lecteur séquentiel de fichier texte.
     *
     * @param nom nom du fichier texte lu.
     *            <br>Si le nom vaut <i>null</i>, c'est l'entrée standard qui est lue.
     */
    public LecteurFichierParLigne(String nom) {
        // donne des valeurs aux attributs.
        this.lecteur = null;
        this.val = "";
        this.nomFic = nom;
    }

    /**
     * Permet de preparer la lecture.
     * <br>Doit être lance avant le 1er accès a la sequence.
     */
    public void demarrer() {
        this.val = "";
        try {
            if (nomFic == null) { // pour une lecture de l'entrée standard (clavier ou redirection).
                this.lecteur = new BufferedReader(new InputStreamReader(System.in)); // creation d'un lecteur (BufferedReader) de l'entrée standard.
            } else { // pour une lecture à partir d'un fichier texte
                this.lecteur = new BufferedReader(new FileReader(nomFic)); // creation d'un lecteur (BufferedReader) de fichier.
            }
        } catch (IOException e) {
            throw new RuntimeException("ERREUR : Impossible d'ouvrir le fichier " + nomFic);
        }
        avancer(); //se positionner sur le debut de la sequence.
    }

    /**
     * Permet de preparer la ligne suivante.
     * <br>Apres avancer <em>finDeSeq()</em> peut renvoyer vrai.
     * <br>Ne doit pas être lancé si <em>finDeSeq()</em> est vrai.
     */
    public void avancer() {
        try {
            this.val = lecteur.readLine(); //lecture de la prochaine ligne.
        } catch (java.io.IOException e) {
            throw new RuntimeException("ERREUR : a classe LecteurFichierParLigne impossible d'effectuer la lecture dans le fichier " + nomFic);
        }
    }

    /**
     * Renvoie vrai si la fin de la lecture est atteinte.
     *
     * @return true si la fin de la lecture est atteinte.
     */
    public boolean finDeSequence() {
        return (val == null); // renvoi d'une expression booléenne vraie si la fin du fichier est atteinte.
    }

    /**
     * Permet de récupérer la dernière ligne lue.
     * <br>Ne doit pas être lancé si <em>finDeSeq()</em> est vrai.
     *
     * @return valeur courante.
     */
    public String elementCourant() {
        return val; // renvoi de la dernière ligne lue.
    }
}
