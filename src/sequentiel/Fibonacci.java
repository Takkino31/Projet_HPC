package sequentiel;
public class Fibonacci {
    public long fibonacci(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        long x, y;        
            x = fibonacci(n - 1);           
            y = fibonacci(n - 2); 
        return x + y;
    }
    public static void main(String args[]){
        long startTime = System.currentTimeMillis();    
          final int n=50;
          Fibonacci fib= new Fibonacci();
          long resultat=fib.fibonacci(n);
          long stopTime = System.currentTimeMillis();
          long elapsedTime = stopTime - startTime;
          System . out . println ( "Fibonacci de " + n + " est de : " + resultat+" Temps d'exécution: "+(float)elapsedTime/1000+ " s" ) ;
    
    }
}
