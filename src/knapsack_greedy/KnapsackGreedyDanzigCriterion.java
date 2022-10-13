package knapsack_greedy;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class KnapsackGreedyDanzigCriterion {

    private static final Random rand = new Random();

    public static int knapsackGreedy(int n, int wMax, int[] w, int[] c) {
        DecimalFormat df = new DecimalFormat("#.###");

        System.out.println("Initial parameters:");
        System.out.println("Items count: " + n);
        System.out.println("W max = " + wMax);
        System.out.println("Weights: " + Arrays.toString(w));
        System.out.println("Costs: " + Arrays.toString(c));
        double[] specificCosts = calcAndGetSpecificCosts(w, c);
        System.out.print("Specific costs: ");
        printDoubleArrayWithFormat(specificCosts, df);
        System.out.println();

        double specificCostsSum = Arrays.stream(specificCosts).sum();
        double[] itemSector = calcItemsSectors(specificCosts, specificCostsSum);
        System.out.println("specificCostsSum=" + df.format(specificCostsSum));
        System.out.print("itemSector=");
        printDoubleArrayWithFormat(itemSector, df);

        List<Integer> unexaminedItems = generateItemsSequentially(n);
        int sumW = 0;
        int sumC = 0;
        int[] ans = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.println("Step " + (i + 1));

            Integer chosenItem = -1;
            double randomDouble = 0;
            while (!unexaminedItems.contains(chosenItem)) {
                randomDouble = rand.nextDouble();
                chosenItem = chooseItemBySegment(randomDouble, itemSector);
            }
            System.out.println("Random number [0,1) -> " + randomDouble);
            System.out.println("Chosen item: " + chosenItem);
            System.out.println("w=" + w[chosenItem - 1] + ", c=" + c[chosenItem - 1]);
            if (sumW + w[chosenItem - 1] <= wMax) {
                System.out.println("TAKE");
                sumW += w[chosenItem - 1];
                sumC += c[chosenItem - 1];
                ans[chosenItem - 1] = 1;
            }
            unexaminedItems.remove(chosenItem);
            System.out.println("sumW=" + sumW + ", sumC=" + sumC);
            System.out.println("*************************");
        }

        System.out.println("Found solution:");
        System.out.println(Arrays.toString(ans));
        return sumC;
    }

    private static void printDoubleArrayWithFormat(double[] arr, DecimalFormat df) {
        System.out.print("[");
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(df.format(arr[i]) + ", ");
        }
        System.out.print(df.format(arr[arr.length - 1]));
        System.out.println("]");
    }

    private static int chooseItemBySegment(double randomDouble, double[] itemSector) {
        int length = itemSector.length;
        for (int i = 0; i < length; i++) {
            if (randomDouble <= itemSector[i]) {
                return i + 1;
            }
        }
        return -1;
    }

    private static List<Integer> generateItemsSequentially(int n) {
        List<Integer> items = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            items.add(i + 1);
        }
        return items;
    }

    private static double[] calcItemsSectors(double[] w, double sum) {
        int length = w.length;
        double[] result = new double[length];
        result[0] = w[0] / sum;
        for (int i = 1; i < length; i++) {
            result[i] = result[i - 1] + w[i] / sum;
        }
        return result;
    }

    private static double[] calcAndGetSpecificCosts(int[] w, int[] c) {
        int len = w.length;
        double[] ans = new double[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (double) c[i] / w[i];
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


