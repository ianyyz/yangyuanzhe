package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    public double[] result;
    public int Time;

    public PercolationStats(int N, int T, PercolationFactory pf){
        if (N <= 0 || T <= 0){
            throw new IllegalArgumentException("those are illegal arguments.");
        }
        Time = T;
        result = new double[T];
        for( int i = 0; i< T; i++){
            int[] idx = new int[N*N];
            StdRandom.shuffle(idx);
            Percolation p = pf.make(N);
            int index = 0;
            while (!p.percolates()){
                p.open( (int)idx[index] / N,idx[index] % N);
                index += 1;
            }
            result[i] = p.numberOfOpenSites()/(N*N);
        }


    }   // perform T independent experiments on an N-by-N grid
    public double mean(){
        return StdStats.mean(result);

    }                                           // sample mean of percolation threshold
    public double stddev(){
        return StdStats.stddev(result);

    }                                   // sample standard deviation of percolation threshold
    public double confidenceLow(){
        return mean() - 1.96*stddev()/Math.sqrt((double) Time);

    }                                  // low endpoint of 95% confidence interval
    public double confidenceHigh(){
        return mean() + 1.96*stddev()/Math.sqrt((double) Time);
    }                                 // high endpoint of 95% confidence interval

}
