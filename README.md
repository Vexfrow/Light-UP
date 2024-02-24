# Light-UP SOLVER Version Française (English version lower)

Il s'agit d'un programme qui permet de résoudre des grilles de jeu de **Light-Up** (ou **Akari**).  
Le processus est simple : On a modélisé les régles du jeu (voir plus bas) sous forme d'une formule en [logique propositionnelle](https://fr.wikipedia.org/wiki/Formule_propositionnelle), puis transformé cette formule en un fichier de format [DIMACS](https://jix.github.io/varisat/manual/0.2.0/formats/dimacs.html) que l'on a résolu grâce à un [SAT-Solver](https://en.wikipedia.org/wiki/SAT_solver), implémenté par nos soin grâce à l'algorithme du DPLL.


## Les règles  
La grille est composée de 3 types de cases : des cases blanches, des cases noires sans chiffre et des cases noires avec.  
Les règles du jeu sont assez simples :  

- Chaque case blanche doit être éclairée. Une case blanche est dîtes éclairée si il y a une ampoule sur la même ligne/colonne et qu'il n'y a pas de case noire entre la case blanche et l'ampoule.
- Il ne peut y avoir d'ampoule sur une case noire.
- Certaines cases noires contiennent un chiffre. Celui-ci est toujours compris entre 0 et 4. Il s’agit du nombre de cases adjacentes (horizontalement ou verticalement, mais pas en oblique) contenant une ampoule.
- Une ampoule ne peut pas en éclairer une autre (le rayon lumineux diffusé par une ampoule ne peut pas atteindre une case occupée par une autre ampoule).  




# Light-UP SOLVER English version (Version française au-dessus)

The objective of this project is to provides a solution to every solvable **Light-Up** (or **Akari**) grid.
The processus is rather simple : We have modelized the game's rules (see lower) in a formula in [propositionnal formula](https://en.wikipedia.org/wiki/Propositional_formula), then we have transformed this formula in a [DIMACS](https://jix.github.io/varisat/manual/0.2.0/formats/dimacs.html)'s file, a file that we can solves thanks to a [SAT-Solver](https://en.wikipedia.org/wiki/SAT_solver). The SAT-SOLVER used was also implemented by ourselves using the DPLL algorithm.


## The rules
The grid is composed of 3 types of cells : white, black whitout number and black with.
Here are the rules : 

The player places light bulbs in white cells such that no two bulbs shine on each other, until the entire grid is lit up.  
A bulb sends rays of light horizontally and vertically, illuminating its entire row and column unless its light is blocked by a black cell.  
A black cell may have a number on it from 0 to 4, indicating how many bulbs must be placed adjacent to its four sides; for example, a cell with a 4 must have four bulbs around it, one on each side, and a cell with a 0 cannot have a bulb next to any of its sides.  
An unnumbered black cell may have any number of light bulbs adjacent to it, or none. Bulbs placed diagonally adjacent to a numbered cell do not contribute to the bulb count.
