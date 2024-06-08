package formalisation;

import lecteur.LecteurFormule;

import java.util.ArrayList;


public class Formule {

    //Tableau des variables "différentes" : -x = x dans ce cas-là
    public ArrayList<Integer> tabVariablePositive;

    //Tableau des clauses
    public ArrayList<Clause> tabClause;


    /* 
        Il s'agit d'une classe ayant pour but de manipuler les formules.
        Une formule est une conjonction de plusieurs clauses
    */


    /*
        Constructeur sans paramètre (Formule vide)
        Crée un objet formule vide
    */
    public Formule() {
        tabVariablePositive = new ArrayList<>();
        tabClause = new ArrayList<>();
    }


    /*
    Constructeur avec paramètre
    Crée un objet formule à partir du string formule fourni et dont le format ressemble à : (x + x + -x) * (x + -x) * ....
    */
    public Formule(String s) {
        this();

        remplirTabClause(s);
        remplirTabVarDiff();
    }


    /*
        Permet de remplir la liste de clauses à partir d'une formule sous forme d'un string
        Algorithme : On parcourt le string grâce à un lecteur de String spécifique (voir la classe "LecteurFormule") qui sépare la formule en différente clause
        et on range ces clauses dans le tableau correspondant (on en augmente la taille)
    */
    private void remplirTabClause(String formule) {
        LecteurFormule lecteurF = new LecteurFormule(formule);

        lecteurF.demarrer();
        while (!lecteurF.finDeSequence()) {
            Clause c = new Clause(lecteurF.elementCourant());
            if (!c.appartientTabClause(tabClause)) tabClause.add(c);
            lecteurF.avancer();
        }
    }


    /*
        Remplis la liste des variables différentes (variable négatif == variable positif)
        Algorithme : On parcourt toutes les variables de toutes les clauses du tableau de clause. Si une variable n'est pas déjà dans le tableau → On le rajoute
        Sinon on ne fait rien.
    */
    private void remplirTabVarDiff() {
        for (Clause c : tabClause) {
            for (Integer var : c.tabVariablePositive)
                if (!tabVariablePositive.contains(var)) tabVariablePositive.add(var);
        }
    }

    /*
        Transforme notre formule disjonctive en une formule DIMACS.
        Algorithme : On parcourt la liste des clauses, que l'on écrit sous forme d'un string.
    */
    public String toFormuleDIMACS() {
        StringBuilder formuleDIMACS = new StringBuilder(("p cnf " + tabVariablePositive.size() + " " + tabClause.size()));
        for (Clause c : tabClause)
            formuleDIMACS.append('\n').append(c.toString()).append('0');

        return formuleDIMACS.toString();
    }


    /*
        Prend une clause, puis fait la conjonction entre la formule et la clause.
        Algorithme : Si la clause n'est pas déjà dans le tableau de clause (voir dans la Classe "Clause.java"), on rajoute cette clause à la liste
    */
    public void conjonction(Clause c) {
        if (!c.appartientTabClause(tabClause)) {
            tabClause.add(c);
            for (Integer var : c.tabVariablePositive)
                if (!tabVariablePositive.contains(var)) tabVariablePositive.add(var);
        }
    }


    /*
        Prend une autre formule, puis fait la conjonction entre la formule actuelle et la nouvelle.
        Algorithme : On parcourt la liste des clauses de la formule passée en paramètre et on fait la conjonction de ces clauses avec la formule actuelle.
    */
    public void conjonction(Formule f) {
        int i = 0;
        while (i < f.tabClause.size()) {
            conjonction(f.tabClause.get(i));
            i++;
        }
    }


    /*
        Permet d'écrire l'objet formule sous la forme d'un String du même format que celui récupéré en paramètre par le constructeur.
    */
//Todo : Modifier pour utiliser le "toString" de la classe Clause
    // TODO : Revoir la manière dont le string est construit
    public String toString() {
        StringBuilder s = new StringBuilder();
        int i = 0;

        while (i < tabClause.size()) {
            int j = 0;
            s.append("(");
            int size = tabClause.get(i).tabVariableUnique.size();
            while (j < size) {
                s.append(tabClause.get(i).tabVariableUnique.get(j));
                j++;
                if (j < size) {
                    s.append(" + ");
                }

            }
            s.append(")");
            i++;
            if (i < tabClause.size()) {
                s.append(" * ");
            }
        }

        return s.toString();
    }


}
