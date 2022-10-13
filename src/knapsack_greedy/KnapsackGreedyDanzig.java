package knapsack_greedy;

import java.util.Arrays;

public class KnapsackGreedyDanzig {

    public static int knapsackGreedy(int n, int wMax, int[] w, int[] c) {
        System.out.println("Initial parameters:");
        System.out.println("Items count: " + n);
        System.out.println("W max = " + wMax);
        System.out.println("Weights: " + Arrays.toString(w));
        System.out.println("Costs: " + Arrays.toString(c));
        double[] specificC = calcAndGetSpecificCosts(w, c);
        System.out.println("Specific costs: " + Arrays.toString(specificC));
        System.out.println();

        int sumW = 0;
        int sumC = 0;
        int[] order = naturalOrder(n);
        int[] ans = new int[n];
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (specificC[i] < specificC[j]) {
                    int tmp = order[i];
                    order[i] = order[j];
                    order[j] = tmp;

                    double dtmp = specificC[i];
                    specificC[i] = specificC[j];
                    specificC[j] = dtmp;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            System.out.println("Step " + (i + 1));
            System.out.println("Current item: " + (order[i] + 1));
            System.out.println("w=" + w[order[i]] + ", c=" + c[order[i]]);
            if (sumW + w[order[i]] <= wMax) {
                System.out.println("TAKE");
                sumW += w[order[i]];
                sumC += c[order[i]];
                ans[order[i]] = 1;
            }
            System.out.println("sumW=" + sumW + ", sumC=" + sumC);
            System.out.println("*************************");
        }

        System.out.println("Found solution:");
        System.out.println(Arrays.toString(ans));
        return sumC;
    }

    private static double[] calcAndGetSpecificCosts(int[] w, int[] c) {
        int len = w.length;
        double[] ans = new double[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (double) c[i] / w[i];
        }
        return ans;
    }

    private static int[] naturalOrder(int n) {
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = i;
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 5;
        int wMax = 15;
        int[] w = new int[]{3, 4, 5, 8, 9};
        int[] c = new int[]{1, 6, 4, 7, 6};

        int ans = knapsackGreedy(n, wMax, w, c);
        System.out.println(ans);
    }
}


