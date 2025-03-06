package parallel;


import schedule.staticSchedule;
import sequentiel.MatrixProduct_sequentiel;

import java.util.concurrent.atomic.AtomicInteger;

public class MatrixProductTest {
    private static final int M = 4096;
    private static final int N = 2048;
    private static final int P = 2048;

    private static final double[][] A = new double[M][N];
    private static final double[][] B = new double[N][P];
    private static final double[][] C = new double[M][P];

    public static void main(String[] args) {
        // Initialisation des matrices A, B, C
        initializeMatrices();

        // Test de la multiplication séquentielle
        long startTime = System.currentTimeMillis();
        MatrixProduct_sequentiel matrixProductSeq = new MatrixProduct_sequentiel(A, B, C);
        matrixProductSeq.initialize();
        matrixProductSeq.multiplierMatrice();
        long stopTime = System.currentTimeMillis();
        long elapsedTimeSeq = stopTime - startTime;
        System.out.println("Temps d'exécution pour la multiplication séquentielle : " + (float)elapsedTimeSeq / 1000 + " s");

        // Test de la multiplication parallèle avec Static Scheduling
        startTime = System.currentTimeMillis();
        MatrixProduct_parallel_static matrixProductStatic = new MatrixProduct_parallel_static(A, B, C, new staticSchedule(0, M - 1, 4)); // Nombre de threads = 4
        matrixProductStatic.run();
        stopTime = System.currentTimeMillis();
        long elapsedTimeStatic = stopTime - startTime;
        System.out.println("Temps d'exécution pour Static Scheduling : " + (float)elapsedTimeStatic / 1000 + " s");

        // Test de la multiplication parallèle avec Self Scheduling
        startTime = System.currentTimeMillis();
        MatrixProduct_parallel_self matrixProductSelf = new MatrixProduct_parallel_self(A, B, C);
        matrixProductSelf.multiplierMatrice(4, 10); // Nombre de threads = 4 et taille du groupe = 10
        stopTime = System.currentTimeMillis();
        long elapsedTimeSelf = stopTime - startTime;
        System.out.println("Temps d'exécution pour Self Scheduling : " + (float)elapsedTimeSelf / 1000 + " s");
    }

    // Méthode pour initialiser les matrices A et B
    public static void initializeMatrices() {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                A[i][j] = 1.0;
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < P; j++) {
                B[i][j] = 1.0;
            }
        }

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < P; j++) {
                C[i][j] = 0.0;
            }
        }
    }
}
