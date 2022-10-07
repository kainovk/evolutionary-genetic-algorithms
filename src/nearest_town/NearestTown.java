package nearest_town;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class NearestTown {

    private static final Random rand = new Random();

    public static void nearestTownMethod(int townCount) {
        List<Integer> visitFrom = generateTownsSequentially(townCount);
        List<Integer> visitTo = generateTownsSequentially(townCount);
        //int[][] townsDistance = prepareConstantMatrix(townCount);
        //int[][] townsDistance = prepareConstantMatrix2();
        int[][] townsDistance = fillRandom(townCount);
        printTownsDistance(townsDistance);
        System.out.println();
        Map<Integer, Integer> visitedBy = new HashMap<>();
        //Integer startTown = rand.nextInt(townCount - 1) + 1;
        Integer startTown = 1;
        System.out.println("Start from town: " + startTown);

        for (int t = 0; t < townCount-1; t++) {
            System.out.println("Step " + (t + 1));

            Integer townTo = 0;
            Integer townFrom = 0;
            int minDistance = Integer.MAX_VALUE;
            for (Integer canVisitRowTown : visitFrom) {
                int minDistanceInRow = Integer.MAX_VALUE;
                int townToVisit = 0;
                for (Integer canVisitColTown : visitTo) {
                    int curDistanceInRow = townsDistance[canVisitRowTown - 1][canVisitColTown - 1];
                    if ((canVisitRowTown - 1) != (canVisitColTown - 1) &&
                            !visitedBy.containsKey(canVisitColTown) &&
                            !visitedBy.containsValue(canVisitRowTown) &&
                            !(visitedBy.containsKey(canVisitRowTown) && visitedBy.containsValue(canVisitColTown)) &&
                            minDistanceInRow > curDistanceInRow
                    ) {
                        minDistanceInRow = curDistanceInRow;
                        townToVisit = canVisitColTown;

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
            visitFrom.remove(townFrom);
            visitTo.remove(townTo);
            visitedBy.put(townTo, townFrom);
        }

        System.out.println("Answer:");
        constructPathFromTown(startTown, visitedBy);
    }

    private static void constructPathFromTown(Integer startTown, Map<Integer, Integer> visitedBy) {
        Map<Integer, Integer> passage = swapMapOfPairs(visitedBy);
        int townCount = visitedBy.size();
        List<Integer> unvisited = generateTownsSequentially(townCount);
        Integer lastTown = startTown;
        for (int i = 0; i < townCount; i++) {
            System.out.print(lastTown + " -> ");
            unvisited.remove(lastTown);
            lastTown = passage.get(lastTown);
        }
        System.out.println(lastTown);
    }

    private static Map<Integer, Integer> swapMapOfPairs(Map<Integer, Integer> visitedBy) {
        Map<Integer, Integer> passage = new HashMap<>();
        visitedBy.forEach(
                (k, v) -> passage.put(v, k)
        );
        return passage;
    }

    private static int[][] prepareConstantMatrix2() {
        return new int[][]{
                new int[]{9, 6, 5, 7, 3},
                new int[]{4, 4, 5, 9, 6},
                new int[]{5, 9, 6, 2, 7},
                new int[]{10, 9, 1, 8, 2},
                new int[]{2, 6, 8, 5, 8}
        };
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