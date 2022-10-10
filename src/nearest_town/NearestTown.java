package nearest_town;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class NearestTown {

    private static final Random rand = new Random();

    public static void nearestTownMethod(int townCount) {
        int[][] townsDistance = fillRandom(townCount);
        printTownsDistance(townsDistance);
        System.out.println();

        List<Integer> unvisited = generateTownsSequentially(townCount);
        List<Integer> passage = new LinkedList<>();
        Integer startTown = rand.nextInt(townCount - 1) + 1;
        System.out.println("Start from town: " + startTown);
        unvisited.remove(startTown);
        passage.add(startTown);

        int currentPathDistance = 0;
        for (int t = 0; t < townCount - 1; t++) {
            System.out.println("Step " + (t + 1));
            int townFrom = 0;
            Integer townTo = 0;
            int minDistance = Integer.MAX_VALUE;
            for (Integer canVisitRowTown : passage) {
                int minDistanceInRow = Integer.MAX_VALUE;
                int townToVisit = 0;
                for (Integer canVisitColTown : unvisited) {
                    int curDistanceInRow = townsDistance[canVisitRowTown - 1][canVisitColTown - 1];
                    if ((canVisitRowTown - 1) != (canVisitColTown - 1)) {
                        System.out.println("candidate: " + canVisitRowTown + "-" + canVisitColTown +
                                ", distance=" + curDistanceInRow);
                        if (minDistanceInRow > curDistanceInRow) {
                            minDistanceInRow = curDistanceInRow;
                            townToVisit = canVisitColTown;
                        }
                    }
                }
                if (minDistance > minDistanceInRow) {
                    minDistance = minDistanceInRow;
                    townFrom = canVisitRowTown;
                    townTo = townToVisit;
                }
            }
            System.out.println("Choosing: " + townFrom + "-" + townTo);
            System.out.println("Distance=" + minDistance);
            currentPathDistance += minDistance;
            System.out.println("Current path distance: " + currentPathDistance);
            int insertIndex = passage.indexOf(townFrom);
            passage.add(insertIndex + 1, townTo);
            unvisited.remove(townTo);
            System.out.println("Current passage: " + passage);
        }

        Integer lastTownFrom = passage.get(passage.size() - 1);
        int distance = townsDistance[lastTownFrom - 1][startTown - 1];
        System.out.println("Return to start town: " + lastTownFrom + "-" + startTown);
        System.out.println("Distance=" + distance);
        currentPathDistance += distance;

        System.out.println("Answer:");
        System.out.println(passage);
        System.out.println("Distance: " + currentPathDistance);
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