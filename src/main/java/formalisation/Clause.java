package formalisation;

import java.util.ArrayList;

public class Clause {

    //Tableau des variables (variables pos != variable négatif)
    public ArrayList<Integer> tabVariable;

    //Tableau des variables différentes (variables pos == variable négatif).
    public ArrayList<Integer> tabVarDiff;

    //Différents tableaux de caractères qui correspondent aux différentes manières d'écrire la formule. 
    //Exemple : On peut représenter la disjonction par '+', '|' ou 'v'
    private static final char[] disjonction = {'+', '|', 'v'};
    private static final char[] negation = {'!', '-'};


    /*
        Permet de créer un objet clause vide.
    */
    public Clause() {
        tabVariable = new ArrayList<>();
        tabVarDiff = new ArrayList<>();
    }

    /*
        Permet de créer un objet clause à partir d'un String qui représente cette clause.
        Exemple : (x + x)
    */
    public Clause(String clauseText) {
        this();
        remplirListesVariable(clauseText);
    }


    /*
        Remplis la liste des variables de la clause à partir d'un String.
        Algorithme : On parcourt chaque caractère du String,
        si c'est une variable (représenté sous forme d'un int) → On la récupère
        sinon (c'est une des représentations de la disjonction) → On regarde si la variable que l'on a récupérée est déjà présente ou non dans les deux tableaux. Si ce n'est pas le cas, on l'ajoute.
    */
    public void remplirListesVariable(String formule) {
        int actuel = 0; //Entier actuel
        boolean vide = true;
        boolean neg = false;

        for (int i = 0; i < formule.length(); i++) {
            char a = formule.charAt(i);
            if (a >= '0' && a <= '9') {
                if (vide) {
                    actuel = Character.getNumericValue(a);
                    vide = false;
                } else {
                    actuel = actuel * 10 + Character.getNumericValue(a);
                }

            } else if (appartient(a, negation)) {
                neg = true;

            } else if (appartient(a, disjonction)) {
                if (neg) {
                    actuel = -actuel;
                    neg = false;
                }

                if (!tabVariable.contains(actuel)) {
                    tabVariable.add(actuel);
                    if (!tabVarDiff.contains(Math.abs(actuel)))
                        tabVarDiff.add(Math.abs(actuel));
                }

                vide = true;
            }
        }
        if (neg)
            actuel = -actuel;

        if (!tabVariable.contains(actuel)) {
            tabVariable.add(actuel);
            if (!tabVarDiff.contains(Math.abs(actuel)))
                tabVarDiff.add(Math.abs(actuel));
        }

    }


    /*
        Vérifie si un caractère appartient à une liste de caractère
        Algorithme : On parcourt la liste mise en paramètre et on compare avec l'élément mis en paramètre
    */
    public boolean appartient(char x, char[] liste) {
        int i = 0;
        while (i < liste.length) {
            if (x == liste[i]) {
                return true;
            }
            i++;
        }
        return false;
    }



    /* 
        Vérifie si une clause appartient à une liste de clause
        Algorithme : On parcourt la liste des clauses et on compare avec la clause mise en paramètre
    */

    public boolean appartientTabClause(Clause[] x, int posTabC) {
        int i = 0;
        while (i < posTabC) {
            if (equals(x[i])) {
                return true;
            }
            i++;
        }
        return false;
    }


    /*
        Permet de savoir si deux clauses sont équivalentes. Deux clauses sont dites équivalentes si elles ont exactement les mêmes variables, l'ordre importe peu.
        Algorithme : On parcourt la liste des variables de la clause actuelle et on regarde si ces variables sont dans la liste des variables de la clause mise en paramètre.
        On compare seulement si les deux clauses ont la même taille.
    */
    public boolean equals(Clause c) {
        boolean equal;
        if (c.tabVariable.size() == this.tabVariable.size()) {
            equal = true;
            int i = 0;
            int size = c.tabVariable.size();
            while (i < size && tabVariable.contains(c.tabVariable.get(i))) {
                i++;
            }
            if (i < size) {
                equal = false;
            }
        } else {
            equal = false;
        }

        return equal;
    }

    /*
        Permet de représenter l'objet Clause sous forme d'un String
    */
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int e : tabVariable) {
            res.append(e).append(' ');
        }
        return res.toString();
    }


    /*
        Permet de faire la disjonction d'une variable avec une clause.
        Algorithme : Si la variable n'est pas déjà dans la clause, on l'ajoute à la liste des variables. Idem pour le tableau de variables différentes.
    */
    public void disjonction(int var) {
        if (!tabVariable.contains(var)) {
            tabVariable.add(var);

            if (!tabVarDiff.contains(Math.abs(var))) {
                tabVarDiff.add(Math.abs(var));
            }
        }
    }


}
