package parallel;

import java.util.concurrent.atomic.AtomicInteger;

public class MatriceMultiplication {

    // Déclaration des matrices (par exemple, matrice A, B et matrice résultat)
    private int M, N, P; // Dimensions des matrices
    private int[][] matriceA;
    private int[][] matriceB;
    private int[][] matriceResult;

    // Constructeur pour initialiser les matrices et leurs dimensions
    public MatriceMultiplication(int M, int N, int P) {
        this.M = M;
        this.N = N;
        this.P = P;
        this.matriceA = new int[M][P];
        this.matriceB = new int[P][N];
        this.matriceResult = new int[M][N];
    }

    // Méthode pour effectuer la multiplication des matrices avec static scheduling
    public void multiplierMatriceStaticScheduling() {
        int numThreads = 4; // Nombre de threads à utiliser
        Thread[] threads = new Thread[numThreads];

        // Division du travail en blocs égaux
        int blockSize = M / numThreads;

        for (int t = 0; t < numThreads; t++) {
            final int threadId = t;

            threads[t] = new Thread(() -> {
                // Calcul des indices de départ et de fin pour ce thread
                int start = threadId * blockSize;
                int end = (threadId == numThreads - 1) ? M : (threadId + 1) * blockSize;

                // Paralléliser le calcul pour ce bloc
                for (int i = start; i < end; i++) {
                    for (int j = 0; j < N; j++) {
                        // Ajouter la logique de multiplication des matrices
                        matriceResult[i][j] = 0;
                        for (int k = 0; k < P; k++) {
                            matriceResult[i][j] += matriceA[i][k] * matriceB[k][j];
                        }
                    }
                }
            });

            threads[t].start();
        }

        // Attendre la fin de l'exécution de tous les threads
        for (int t = 0; t < numThreads; t++) {
            try {
                threads[t].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Méthode pour effectuer la multiplication des matrices avec self scheduling
    public void multiplierMatriceSelfScheduling() {
        int numThreads = 4; // Nombre de threads à utiliser
        Thread[] threads = new Thread[numThreads];

        // Utilisation d'un compteur pour gérer l'itération de manière sûre
        AtomicInteger atomicCounter = new AtomicInteger(0);

        for (int t = 0; t < numThreads; t++) {
            threads[t] = new Thread(() -> {
                // Chaque thread prend dynamiquement une itération à traiter
                int i;
                while ((i = atomicCounter.getAndIncrement()) < M) {
                    for (int j = 0; j < N; j++) {
                        // Ajouter la logique de multiplication des matrices
                        matriceResult[i][j] = 0;
                        for (int k = 0; k < P; k++) {
                            matriceResult[i][j] += matriceA[i][k] * matriceB[k][j];
                        }
                    }
                }
            });

            threads[t].start();
        }

        // Attendre la fin de l'exécution de tous les threads
        for (int t = 0; t < numThreads; t++) {
            try {
                threads[t].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Autres méthodes pour afficher ou tester les résultats
    public void afficherMatriceResultat() {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(matriceResult[i][j] + " ");
            }
            System.out.println();
        }
    }
}
