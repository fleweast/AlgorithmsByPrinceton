import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double mean, stddev, confidenceLo, confidenceHi;
    private static double CONFIDENCE_95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if (n<= 0 && trials<=0) {
            throw new IllegalArgumentException();
        }
        double[] thresholds = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation experiment = new Percolation(n);
            while (!experiment.percolates()) {
                int openRow = StdRandom.uniform(1, n+1);
                int openCol = StdRandom.uniform(1, n+1);
                experiment.open(openRow, openCol);
            }
            thresholds[i] = (double) experiment.numberOfOpenSites()/ (double) (n*n);
        }
        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);
        confidenceLo = mean - (CONFIDENCE_95 * stddev) / Math.sqrt(trials);
        confidenceHi = mean + (CONFIDENCE_95 * stddev) / Math.sqrt(trials);

    }

    // sample mean of percolation threshold
    public double mean(){
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return  confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println(String.format("%-25s = %f", "mean", stats.mean()));
        StdOut.println(String.format("%-25s = %f", "stddev", stats.stddev()));
        StdOut.println(String.format("%-25s = [%f, %f]", "95% confidence interval", stats.confidenceLo(), stats.confidenceHi()));
    }

}