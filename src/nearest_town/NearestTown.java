package nearest_town;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class NearestTown {

    private static final Random rand = new Random();

    public static void nearestTownMethod(int townCount) {
        List<Integer> visitFrom = generateTownsSequentially(townCount);
        List<Integer> visitTo = generateTownsSequentially(townCount);

        int[][] townsDistance = fillRandom(townCount);
        printTownsDistance(townsDistance);
        System.out.println();
        Map<Integer, Integer> visitedBy = new HashMap<>();
        Integer startTown = rand.nextInt(townCount - 1) + 1;
        System.out.println("Start from town: " + startTown);
        int currentPathDistance = 0;
        for (int t = 0; t < townCount - 1; t++) {
            System.out.println("Step " + (t + 1));
            System.out.println("Current pairs:");
            printAllPairs(visitedBy);
            Integer townTo = 0;
            Integer townFrom = 0;
            int minDistance = Integer.MAX_VALUE;
            System.out.println("can visit from: " + visitFrom);
            System.out.println("can visit to: " + visitTo);
            for (Integer canVisitRowTown : visitFrom) {
                int minDistanceInRow = Integer.MAX_VALUE;
                int townToVisit = 0;
                for (Integer canVisitColTown : visitTo) {
                    int curDistanceInRow = townsDistance[canVisitRowTown - 1][canVisitColTown - 1];
                    if ((canVisitRowTown - 1) != (canVisitColTown - 1) &&
                            !visitedBy.containsKey(canVisitColTown) &&
                            !visitedBy.containsValue(canVisitRowTown) &&
                            !(visitedBy.containsKey(canVisitRowTown) &&
                                    Objects.equals(visitedBy.get(canVisitRowTown), canVisitColTown)) &&
                            canStick(canVisitRowTown, canVisitColTown, visitedBy, startTown, townCount)
                    ) {
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
            visitFrom.remove(townFrom);
            visitTo.remove(townTo);
            visitedBy.put(townTo, townFrom);
        }

        Integer lastTownFrom = visitFrom.get(0);
        Integer lastTownTo = visitTo.get(0);
        int distance = townsDistance[lastTownFrom - 1][lastTownTo - 1];
        System.out.println("Choosing last towns: " + lastTownFrom + "-" + lastTownTo);
        System.out.println("Distance=" + distance);
        currentPathDistance += distance;
        visitedBy.put(lastTownTo, lastTownFrom);

        System.out.println("Answer:");
        constructPathFromTown(startTown, visitedBy);
        System.out.println("Distance: " + currentPathDistance);
    }

    private static void printAllPairs(Map<Integer, Integer> visitedBy) {
        visitedBy.forEach(
                (k, v) -> System.out.println(v + "-" + k)
        );
    }

    private static boolean canStick(Integer fromTown, Integer toTown, Map<Integer, Integer> visitedBy, Integer startTown, int townCount) {
        Map<Integer, Integer> passage = swapMapOfPairs(visitedBy);

        if (chainExists(toTown, fromTown, passage)) {
            return false;
        }
        int leftSteps = countStepsBetweenTowns(startTown, fromTown, startTown, passage, townCount);
        int rightSteps = countStepsBetweenTowns(toTown, startTown, startTown, passage, townCount);
        if (leftSteps == -1 || rightSteps == -1) {
            return true;
        }
        return leftSteps + rightSteps >= (townCount - 1);
    }

    private static boolean chainExists(Integer from, Integer to, Map<Integer, Integer> passage) {
        while (from != null) {
            if (from.equals(to)) {
                return true;
            }
            from = passage.get(from);
        }
        return false;
    }

    private static int countStepsBetweenTowns(Integer to, Integer start, Integer startTown, Map<Integer, Integer> passage, int townCount) {
        int steps = 0;
        while (steps != townCount && to != null) {
            if (to.equals(start)) {
                return steps;
            }
            to = passage.get(to);
            steps++;
        }
        return -1;
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