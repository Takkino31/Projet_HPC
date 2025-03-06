/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallel;

/**
 *
 * @author dell
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import schedule.selfSchedule;

public class MatrixProduct_parallel_self {
    private static final int M = 4096;
    private static final int N = 2048;
    private static final int P = 2048;

    private final double[][] A;
    private final double[][] B;
    private final double[][] C;

    public MatrixProduct_parallel_self(double[][] A, double[][] B, double[][] C) {
        this.A = A;
        this.B = B;
        this.C = C;
    }

    public static void initialize(double[][] A, double[][] B, double[][] C) {
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A[i][j] = 1.0;

        for (int i = 0; i < N; i++)
            for (int j = 0; j < P; j++)
                B[i][j] = 1.0;

        for (int i = 0; i < M; i++)
            for (int j = 0; j < P; j++)
                C[i][j] = 0.0;
    }

    public void multiplierMatrice(int numThreads, int groupSize) {
        System.out.println("Start self scheduling with " + numThreads + " threads..." + " Groupsize " + groupSize);
        Thread[] threads = new Thread[numThreads];
        selfSchedule scheduler = new selfSchedule(0, M - 1, groupSize);

        for (int t = 0; t < numThreads; t++) {
            threads[t] = new Thread(() -> {
                selfSchedule.LoopRange range;
                while ((range = scheduler.loopGetRange()) != null) {
                    for (int i = range.start; i <= range.end; i++)
                        for (int j = 0; j < P; j++)
                            for (int k = 0; k < N; k++)
                                C[i][j] += A[i][k] * B[k][j];
                }
            });
            threads[t].start();
        }

        for (int t = 0; t < numThreads; t++) {
            try {
                threads[t].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Self scheduling completed.");
    }

    public static void main(String[] args) {
        System.out.println("Initializing matrices for self scheduling...");
        long startTime = System.currentTimeMillis();

        double[][] A = new double[M][N];
        double[][] B = new double[N][P];
        double[][] C = new double[M][P];
        initialize(A, B, C);

        MatrixProduct_parallel_self matrixProduct = new MatrixProduct_parallel_self(A, B, C);
        matrixProduct.multiplierMatrice(30, 2048);

        long stopTime = System.currentTimeMillis();
        System.out.println("Self scheduling completed in " + ((stopTime - startTime) / 1000.0) + " s");
    }
}
