/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

/**
 *
 * @author dell
 */

public class staticSchedule {
    public int startLoop, curLoop, endLoop, numThreads;
    public static class LoopRange {
        public int start, end;
        static int [][] matrice;

    }

    public int step, modulo;
    public staticSchedule(int startLoop, int endLoop, int numThreads) {
        int numIterations = endLoop - startLoop + 1;
        curLoop = startLoop - 1;
        this.startLoop = startLoop; // indices de depart  Depart
        this.numThreads = numThreads; //  Nombre de threads
        step = numIterations / numThreads; // indices de FIN
        modulo = numIterations % numThreads; // Reste du nombre d'iterations
    }

    public  synchronized LoopRange loopGetRange() {
        if (curLoop >= endLoop) {
            return null;
        }
        LoopRange range = new LoopRange();
        range.start = curLoop + 1;
        curLoop = curLoop + step;
        if (modulo > 0) {
            curLoop++;
            modulo--;
        }
        range.end = Math.min(curLoop, endLoop);
        return range;
    }
}