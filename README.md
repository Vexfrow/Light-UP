# Light-UP SOLVER

Il s'agit d'un programme qui permet de résoudre des grille de jeu de **Light-Up** (ou **Akari**).  
Le processus est simple : On a modélisé les régles du jeu (voir plus bas) en [logique propositionnelle](https://fr.wikipedia.org/wiki/Formule_propositionnelle#:~:text=En%20logique%20propositionnelle%20classique%2C%20une,de%20v%C3%A9rit%C3%A9%20peut%20%C3%AAtre%20d%C3%A9termin%C3%A9e.), puis transformé cette formule en un fichier de format [DIMACS](https://jix.github.io/varisat/manual/0.2.0/formats/dimacs.html) que l'on a résolu grâce à un [SAT-Solver](https://en.wikipedia.org/wiki/SAT_solver), implémenté par nos soin grâce à l'alorithme du DPLL.


## Les règles  
La grille est composée de 3 types de cases : des cases blanches, des cases noires sans chiffre et des cases noires avec.  
Les règles du jeu sont assez simples :  

- Toutes les cases blanches doivent être allumées, pour ce faire il faut qu'il y ait une ampoule située sur la même ligne ou la même colonne et qu'il n'y ait pas de case noire entre la case blanche et l'ampoule.  
- Il ne peut n'y avoir qu'une seule ampoule par ligne/colonne, sauf si elles sont séparées par une case noire.  
- Il ne peut pas y avoir d'ampoule sur les cases noires.  
- Il doit y avoir autant d'ampoule autour des cases noires chiffrées que le chiffre indiqué. Par exemple, si ce chiffre est 1, il ne doit pas y avoir qu'une seule ampoule autour de cette case (pas une de +, pas une de -). Cela ne fonctionne pas si l'ampoule est placée en diagonale.  


## Comment lancer le programme 
Il suffit seulement de télécharger le code source, puis de soit lancer la classe "GUIStarter.java" ou alors de créer un JAR du code source en utilisant la classe "GUIStarter.java" en tant que point d'entrée du programme.  


## Ce qui est prévue
- Version anglaise de ce README
- Possibilité de jouer au jeu  



