import java.lang.Math;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] p;
    private int N;
    private int T;
    private double mean;
    private double stddev;
    private double lo;
    private double hi;
    public PercolationStats(int n, int trials) {
        if(n < 1 || trials < 1) {
            throw new IllegalArgumentException();
        }
        N = n;
        T = trials;
        p = new double[T];
        for(int i = 0; i < T; i++)
            p[i] = 0.0;
        for(int t = 0; t < T; t++) {
            Percolation pe = new Percolation(N);
            monte_carlo(pe, t);
        }
        mean = StdStats.mean(p);
        stddev = StdStats.stddev(p);
        lo = mean - stddev * 1.96 / Math.sqrt(T * 1.0);
        hi = mean + stddev * 1.96 / Math.sqrt(T * 1.0);
    }

    private void monte_carlo(Percolation pe, int trial) {
        while(!pe.percolates()) {
            while(true) {
                int i = StdRandom.uniform(1, N + 1);
                int j = StdRandom.uniform(1, N + 1);
                if(!pe.isOpen(i, j)) {
                    pe.open(i, j);
                    break;
                }
            }
        }
        p[trial] = pe.numberOfOpenSites() * 1.0 / (N * N);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return lo;
    }

    public double confidenceHi() {
        return hi;
    }

    public static void main(String[] args) {
    }
}