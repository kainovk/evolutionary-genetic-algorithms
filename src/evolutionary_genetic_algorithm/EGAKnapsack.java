package evolutionary_genetic_algorithm;

import java.util.Scanner;

import static knapsack_greedy.KnapsackGreedyCriterion.knapsackGreedyCriterion;
import static knapsack_greedy.KnapsackGreedyDanzigCriterion.knapsackGreedyDanzigCriterion;

public class EGAKnapsack {

    public static void runEGAKnapsack(int n, int wMax, int[] w, int[] c) {
        System.out.println("Choose algorithm: ");
        System.out.println("1 - knapsack greedy");
        System.out.println("2 - knapsack greedy danzig");
        Scanner sc = new Scanner(System.in);
        String choice="";

        while (!choice.equals("!quit")) {
            while (!choice.equals("1") && !choice.equals("2")) {
                choice = sc.next();
            }

            int ans;
            if (choice.equals("1")) {
                System.out.println("Starting Knapsack Greedy algorithm");
                ans = knapsackGreedyCriterion(n, wMax, w, c);
            } else {
                System.out.println("Starting Knapsack Greedy Danzig algorithm");
                ans = knapsackGreedyDanzigCriterion(n, wMax, w, c);
            }

            System.out.println("Answer from EGA: " + ans);

            System.out.println("Choose algorithm: ");
            System.out.println("1 - knapsack greedy");
            System.out.println("2 - knapsack greedy danzig");
            System.out.println("Or enter !quit for exit");
            choice = sc.next();
        }
    }

    public static void main(String[] args) {
        int n = 11;
        int wMax = 114;
        int[] w = new int[]{17, 20, 1, 26, 28, 26, 21, 23, 18, 24, 7, 20, 28, 5, 22};
        int[] c = new int[]{27, 2, 17, 12, 3, 16, 10, 19, 1, 6, 12, 12, 27, 28, 9};

        runEGAKnapsack(n, wMax, w, c);
    }
}
