package nearest_town;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class NearestTown {

    private static final Random rand = new Random();

    public static void nearestTownMethod(int townCount) {
        List<Integer> townsLeft = generateTownsSequentially(townCount);
        int[][] townsDistance = fillRandom(townCount);
        printTownsDistance(townsDistance);
        System.out.println();
        Map<Integer, Integer> passage = new HashMap<>();
        //Integer startTown = rand.nextInt(townCount - 1) + 1;
        Integer startTown = 1;
        System.out.println("Start from town: " + startTown);

        int townsPassed = 1;
        List<Integer> townsToVisit = new ArrayList<>(townsLeft);
        townsToVisit.remove(startTown);
        while (townsLeft.size() != 1) {
            System.out.println("Step " + townsPassed);
            System.out.println("Towns passed=" + townsPassed);
            System.out.println("Towns left: " + townsLeft);

            Integer townFrom = 0;
            Integer townTo = 0;
            int minDistance = Integer.MAX_VALUE;
            for (Integer town : townsLeft) {
                int minDistanceInRow = Integer.MAX_VALUE;
                int townToCur = 0;
                for (Integer townToVisit : townsToVisit) {
                    int curDistanceInRow = townsDistance[town - 1][townToVisit - 1];
                    if ((minDistanceInRow > curDistanceInRow) && (!Objects.equals(town, townToVisit))) {
                        minDistanceInRow = curDistanceInRow;
                        townToCur = townToVisit;
                    }
                }
                if (minDistance > minDistanceInRow) {
                    minDistance = minDistanceInRow;
                    townFrom = town;
                    townTo = townToCur;
                }
            }
            System.out.println("Choosing: " + townFrom + "-" + townTo);
            System.out.println("Distance=" + minDistance);
            townsLeft.remove(townFrom);
            townsToVisit.remove(townTo);
            townsPassed++;
        }

        // constructPathFromTown(startTown);
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
        nearestTownMethod(dimension);
    }
}