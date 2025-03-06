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


import schedule.staticSchedule;
import schedule.staticSchedule.LoopRange;

/**
 *
 * @author expert
 */


public class MatrixProduct_parallel_static extends Thread {
    private static final int M = 4096;
    private static final int N = 2048;
    private static final int P = 2048;

    private final double[][] A;
    private final double[][] B;
    private final double[][] C;
    private final staticSchedule schedule;

    public MatrixProduct_parallel_static(double[][] A, double[][] B, double[][] C, staticSchedule schedule) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.schedule = schedule;
    }

    public void run() {
        LoopRange range;
        while ((range = schedule.loopGetRange()) != null) {
            for (int i = range.start; i <= range.end; i++)
                for (int j = 0; j < P; j++)
                    for (int k = 0; k < N; k++)
                        C[i][j] += A[i][k] * B[k][j];
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Initializing matrices for static scheduling...");
        long startTime = System.currentTimeMillis();

        double[][] A = new double[M][N];
        double[][] B = new double[N][P];
        double[][] C = new double[M][P];
        initialize(A, B, C);

        int numThreads = 30;
        staticSchedule schedule = new staticSchedule(0, M - 1, numThreads);

        MatrixProduct_parallel_static[] threads = new MatrixProduct_parallel_static[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new MatrixProduct_parallel_static(A, B, C, schedule);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Static scheduling completed in " + ((endTime - startTime) / 1000.0) + " s");
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
}
