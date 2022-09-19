package nearest_neighbour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NearestNeighbour {

    private static final Random rand = new Random();

    public static void nearestNeighbourMethod(int townCount) {

        List<Integer> towns = generateTownsSequentially(townCount);
        int[][] townsDistance = fillRandom(townCount);
        printTownsDistance(townsDistance);
        System.out.println();
        int[] passage = new int[townCount];
        passage[0] = rand.nextInt(townCount - 1) + 1;
        towns.remove(Integer.valueOf(passage[0]));
        System.out.println("Start from town: " + passage[0]);

        int pathDistance = 0;
        int townsPassed = 1;
        while (!towns.isEmpty()) {
            System.out.println("Step " + townsPassed);
            System.out.println("Towns passed=" + townsPassed);
            System.out.println("Towns left: " + towns);
            System.out.println("Current passage: " + Arrays.toString(passage));
            System.out.println("Current path distance: " + pathDistance);
            int fromTown = passage[townsPassed - 1];
            int nearestTown = 0;
            int minDistance = 11;
            for (Integer toTown : towns) {
                int dist = townsDistance[fromTown - 1][toTown - 1];
                if (dist < minDistance) {
                    minDistance = dist;
                    nearestTown = toTown;
                }
            }
            passage[townsPassed] = nearestTown;
            System.out.println("Choosing town: " + nearestTown);
            System.out.println("Distance from last town is "
                    + townsDistance[passage[townsPassed - 1] - 1][passage[townsPassed] - 1]);
            pathDistance +=townsDistance[passage[townsPassed - 1] - 1][passage[townsPassed] - 1];
            towns.remove(Integer.valueOf(nearestTown));
            townsPassed++;

            System.out.println("**************");
            if (townsPassed == townCount) {
                System.out.println("Choosing last town: " + passage[0]);
                System.out.println("Distance from last town is "
                        + townsDistance[passage[townsPassed - 1] - 1][passage[0] - 1]);
                pathDistance+=townsDistance[passage[townsPassed - 1] - 1][passage[0] - 1];
                System.out.println("Current passage: " + Arrays.toString(passage));
                System.out.println("Current path distance: " + pathDistance);
            }
        }


        printPath(passage);
        System.out.println("Path distance=" + pathDistance);
    }

    private static int[][] prepareConstantMatrix(int dimension) {
        int[][] ans = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                ans[i][j] = i % 3 + j % 2 + 1;
            }
        }
        return ans;
    }

    private static void printPath(int[] passage) {
        for (int i = 0; i < passage.length - 1; i++) {
            System.out.print(passage[i] + " -> ");
        }
        System.out.println(passage[passage.length - 1]);
    }

    private static int getPathDistance(int[][] townsDistance, int[] passage) {
        int dist = 0;
        for (int i = 0; i < townsDistance.length - 1; i++) {
            dist += townsDistance[passage[i] - 1][passage[i + 1] - 1];
        }
        return dist;
    }

    private static void printTownsDistance(int[][] townsDistance) {
        for (int[] ints : townsDistance) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }

    private static List<Integer> generateTownsSequentially(int townCount) {
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < townCount; i++) {
            ans.add(i + 1);
        }
        return ans;
    }

    private static int[][] fillRandom(int dimension) {
        int[][] ans = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                ans[i][j] = rand.nextInt(10) + 1; // [1, 10]
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int dimension = 5;
        nearestNeighbourMethod(dimension);
    }
}