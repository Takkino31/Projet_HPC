# ProjetExamen_HPC_2024_2025

## Description

Ce projet est réalisé dans le cadre du Master 2 GLSI-SRT à l'ESP (École Supérieure Polytechnique) pour l'année académique 2024-2025. Il consiste en une implémentation de programmes parallèles en Java pour l'optimisation du produit matriciel et le calcul parallèle de Fibonacci. Le projet comprend l'évaluation des performances des approches séquentielles et parallèles ainsi que l'analyse des gains de performance obtenus grâce à la parallélisation.

## Packages

Le projet est structuré autour de quatre packages principaux :

1. **sequentiel** : Contient les classes `Fibonacci.java` et `MatrixProduct_sequentiel.java` qui implémentent des versions séquentielles du calcul de Fibonacci et du produit matriciel.

2. **parallel** : Contient les classes `MatrixProduct_parallel_static.java` et `MatrixProduct_parallel_self.java` qui implémentent les versions parallèles du produit matriciel en utilisant des algorithmes de répartition d'itération (static scheduling et self scheduling).

3. **schedule** : Contient les classes `staticSchedule.java` et `selfSchedule.java` qui gèrent la répartition des itérations dans les versions parallèles.

4. **fjcomp** : Contient le fichier `Fibonacci.fj` pour l'implémentation du calcul parallèle de Fibonacci à l'aide du compilateur FJComp. Le fichier jar du compilateur, `compiler.fjcomp.jar`, est aussi inclus.

## Objectifs

### Exercice 1 : Implémentation d'un programme parallèle manuel (14 points)

- Relevé des performances de l'ordinateur
- Exécution du programme séquentiel `MatrixProduct_sequentiel.java` et relevé des temps d'exécution
- Implémentation des programmes parallèles suivants :
    - `MatrixProduct_parallel_static.java`
    - `MatrixProduct_parallel_self.java`
- Vérification de la cohérence séquentielle entre les versions séquentielle et parallèle
- Évaluation des performances et calcul de l'accélération du parallélisme

### Exercice 2 : Implémentation d'un programme parallèle à l'aide du compilateur FJComp (6 points)

- Exécution du programme séquentiel `Fibonacci.java` avec `n=50`
- Utilisation du compilateur FJComp pour générer un code parallèle avec un nombre de threads et une profondeur maximale spécifiés
- Vérification de la cohérence séquentielle
- Évaluation des performances et tracé des courbes d'accélération

## Exécution du Projet

### 1. Exécution des Programmes Séquentiels

Pour exécuter les versions séquentielles des programmes, vous pouvez compiler et exécuter les classes `MatrixProduct_sequentiel.java` et `Fibonacci.java`.

```bash
# Compilation
javac sequentiel/MatrixProduct_sequentiel.java
javac sequentiel/Fibonacci.java

# Exécution
java sequentiel.MatrixProduct_sequentiel
java sequentiel.Fibonacci


# Compilation
javac parallel/MatrixProduct_parallel_static.java
javac parallel/MatrixProduct_parallel_self.java

# Exécution
java parallel.MatrixProduct_parallel_static
java parallel.MatrixProduct_parallel_self


# Compilation avec FJComp
java -jar compiler.fjcomp.jar fjcomp/Fibonacci.fj


## Structure du projet

.
├── parallel
│   ├── MatrixProduct_parallel_self.java
│   └── MatrixProduct_parallel_static.java
├── sequentiel
│   ├── Fibonacci.java
│   └── MatrixProduct_sequentiel.java
├── schedule
│   ├── staticSchedule.java
│   └── selfSchedule.java
├── fjcomp
│   ├── Fibonacci.fj
│   └── compiler.fjcomp.jar
└── README.md
