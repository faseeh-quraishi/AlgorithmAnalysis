package analysis;

import java.util.Random;

public class MaxSumTest {
    // Variables to store the start and end indices of the max subarray
    static private int seqStart = 0;
    static private int seqEnd = -1;

    /**
     * Cubic time algorithm (O(n^3)) to find the maximum subarray sum.
     * Uses three nested loops to consider all subarrays.
     */
    public static int maxSubSum1(int[] a) {
        int maxSum = 0;
        for (int i = 0; i < a.length; i++)
            for (int j = i; j < a.length; j++) {
                int thisSum = 0;
                for (int k = i; k <= j; k++)
                    thisSum += a[k];

                if (thisSum > maxSum) {
                    maxSum = thisSum;
                    seqStart = i;
                    seqEnd = j;
                }
            }
        return maxSum;
    }

    /**
     * Quadratic time algorithm (O(n^2)) to find the maximum subarray sum.
     * Improves on cubic version by eliminating the innermost loop.
     */
    public static int maxSubSum2(int[] a) {
        int maxSum = 0;
        for (int i = 0; i < a.length; i++) {
            int thisSum = 0;
            for (int j = i; j < a.length; j++) {
                thisSum += a[j];
                if (thisSum > maxSum) {
                    maxSum = thisSum;
                    seqStart = i;
                    seqEnd = j;
                }
            }
        }
        return maxSum;
    }

    /**
     * Divide-and-conquer algorithm (O(n log n)) for maximum subarray sum.
     * Recursively divides the array and considers three cases:
     * max in left, max in right, and max crossing the middle.
     */
    public static int maxSubSum3(int[] a) {
        return a.length > 0 ? maxSumRec(a, 0, a.length - 1) : 0;
    }

    /**
     * Recursive helper function for divide-and-conquer algorithm.
     */
    private static int maxSumRec(int[] a, int left, int right) {
        if (left == right)
            return a[left] > 0 ? a[left] : 0;

        int center = (left + right) / 2;
        int maxLeftSum = maxSumRec(a, left, center);
        int maxRightSum = maxSumRec(a, center + 1, right);

        int maxLeftBorderSum = 0, leftBorderSum = 0;
        for (int i = center; i >= left; i--) {
            leftBorderSum += a[i];
            if (leftBorderSum > maxLeftBorderSum)
                maxLeftBorderSum = leftBorderSum;
        }

        int maxRightBorderSum = 0, rightBorderSum = 0;
        for (int i = center + 1; i <= right; i++) {
            rightBorderSum += a[i];
            if (rightBorderSum > maxRightBorderSum)
                maxRightBorderSum = rightBorderSum;
        }

        return max3(maxLeftSum, maxRightSum, maxLeftBorderSum + maxRightBorderSum);
    }

    /**
     * Linear-time algorithm (Kadane’s algorithm, O(n)) for maximum subarray sum.
     * Keeps track of current sum and resets when it drops below zero.
     */
    public static int maxSubSum4(int[] a) {
        int maxSum = 0, thisSum = 0;
        for (int i = 0, j = 0; j < a.length; j++) {
            thisSum += a[j];

            if (thisSum > maxSum) {
                maxSum = thisSum;
                seqStart = i;
                seqEnd = j;
            } else if (thisSum < 0) {
                i = j + 1;
                thisSum = 0;
            }
        }
        return maxSum;
    }

    /**
     * Returns maximum among three integers.
     */
    private static int max3(int a, int b, int c) {
        return a > b ? (a > c ? a : c) : (b > c ? b : c);
    }

    private static Random rand = new Random();

    /**
     * Main method to test all max subarray algorithms with different array sizes.
     */
    public static void main(String[] args) {
        Random aR = new Random();

        System.out.printf("%-12s | %-15s | %-20s | %-10s\n", 
            "Array Size", "Algorithm", "Max Sum (Start-End)", "Time (ms)");
        System.out.println("---------------------------------------------------------------");

        for (int size = 8; size <= 65536; size *= 2) {
            int[] a = aR.ints(size, -size, size).toArray();
            int maxSum;
            long start, end;

            // maxSubSum1 (O(n^3))
            start = System.currentTimeMillis();
            maxSum = maxSubSum1(a);
            end = System.currentTimeMillis();
            System.out.printf("%-12d | %-15s | %-20s | %-10d\n",
                size, "maxSubSum1", maxSum + " (" + seqStart + "-" + seqEnd + ")", end - start);

            // maxSubSum2 (O(n^2))
            start = System.currentTimeMillis();
            maxSum = maxSubSum2(a);
            end = System.currentTimeMillis();
            System.out.printf("%-12d | %-15s | %-20s | %-10d\n",
                size, "maxSubSum2", maxSum + " (" + seqStart + "-" + seqEnd + ")", end - start);

            // maxSubSum3 (O(n log n))
            start = System.currentTimeMillis();
            maxSum = maxSubSum3(a);
            end = System.currentTimeMillis();
            System.out.printf("%-12d | %-15s | %-20s | %-10d\n",
                size, "maxSubSum3", maxSum + " (" + seqStart + "-" + seqEnd + ")", end - start);

            // maxSubSum4 (O(n))
            start = System.currentTimeMillis();
            maxSum = maxSubSum4(a);
            end = System.currentTimeMillis();
            System.out.printf("%-12d | %-15s | %-20s | %-10d\n",
                size, "maxSubSum4", maxSum + " (" + seqStart + "-" + seqEnd + ")", end - start);
        }
    }
}
