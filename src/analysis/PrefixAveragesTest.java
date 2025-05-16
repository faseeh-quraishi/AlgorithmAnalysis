package analysis;

import java.util.Random;

public class PrefixAveragesTest {
    // Algorithm 1: Computes prefix averages using nested loops (O(n^2) time complexity)
    public static double[] prefixAverages1(int[] input) {
        int n = input.length;
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            int sum = 0;
            // Sum all elements from A[0] to A[i]
            for (int j = 0; j <= i; j++) {
                sum += input[j];
            }
            result[i] = (double) sum / (i + 1); // Compute average up to index i
        }
        return result;
    }

    // Algorithm 2: Computes prefix averages using running sum (O(n) time complexity)
    public static double[] prefixAverages2(int[] input) {
    	//  Initiating Variables
        int n = input.length;
        double[] result = new double[n];
        int sum = 0;
        //	Maintain a running sum and compute average at each step
        for (int i = 0; i < n; i++) {
            sum += input[i]; 
            result[i] = (double) sum / (i + 1); // Compute average up to index i
        }
        return result;
    }

    // Generates a random integer array of size n with values between -n and +n
    public static int[] generateRandomArray(int n) {
        Random rand = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(2 * n + 1) - n; // Range: [-n, n]
        }
        return arr;
    }

    // Entry point of the program: tests both algorithms on growing array sizes
    public static void main(String[] args) {
        // Print table header with units
        System.out.printf("%-10s %-20s %-20s\n", "Size", "Time O(n^2) (ms)", "Time O(n) (ms)");

        // Start testing for sizes from 8 to 65536 (doubling each time)
        for (int size = 8; size <= 65536; size *= 2) {
            int[] testArray = generateRandomArray(size); // Generate test data

            // Measure execution time of O(n^2) algorithm
            long start1 = System.nanoTime();
            prefixAverages1(testArray);
            long end1 = System.nanoTime();
            double time1 = (end1 - start1) / 1e6; // Time in milliseconds

            // Measure execution time of O(n) algorithm
            long start2 = System.nanoTime();
            prefixAverages2(testArray);
            long end2 = System.nanoTime();
            double time2 = (end2 - start2) / 1e6; // Time in milliseconds

            // Print execution times for both algorithms
            System.out.printf("%-10d %-20.3f %-20.3f\n", size, time1, time2);
        }
    }
}
