package knapsack_greedy;

import java.util.Arrays;

public class KnapsackGreedy {

    public static int knapsackGreedy(int n, int wMax, int[] w, int[] c) {
        System.out.println("Initial parameters:");
        System.out.println("Items count: " + n);
        System.out.println("W max = " + wMax);
        System.out.println("Weights: " + Arrays.toString(w));
        System.out.println("Costs: " + Arrays.toString(c));
        System.out.println();

        int sumW = 0;
        int sumC = 0;
        int[] order = naturalOrder(n);
        int[] ans = new int[n];
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (c[i] < c[j]) {
                    int tmp = c[i];
                    c[i] = c[j];
                    c[j] = tmp;

                    tmp = w[i];
                    w[i] = w[j];
                    w[j] = tmp;

                    tmp = order[i];
                    order[i] = order[j];
                    order[j] = tmp;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            System.out.println("Step " + (i + 1));
            System.out.println("Current item: " + (order[i]+1));
            System.out.println("w="+w[order[i]] + ", c=" + c[order[i]]);
            if (sumW + w[i] <= wMax) {
                System.out.println("TAKE");
                sumW += w[i];
                sumC += c[i];
                ans[order[i]] = 1;
            }
            System.out.println("sumW=" + sumW + ", sumC=" + sumC);
            System.out.println("*************************");
        }

        System.out.println("Found solution:");
        System.out.println(Arrays.toString(ans));
        return sumC;
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
        int[] w = new int[]{5, 2, 6, 4, 7};
        int[] c = new int[]{4, 2, 7, 5, 7};

        int ans = knapsackGreedy(n, wMax, w, c);
        System.out.println(ans);
    }
}


