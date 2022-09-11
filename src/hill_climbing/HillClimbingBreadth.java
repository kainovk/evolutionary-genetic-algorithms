package hill_climbing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static util.AdaptibilityUtils.generateBinary;
import static util.AdaptibilityUtils.getAdaptabilityDecimal;
import static util.LandscapeUtils.generateLandscape;
import static util.LandscapeUtils.printLandscape;

public class HillClimbingBreadth {

    public static void hillClimbingBreadthMethod(int l, int n) {
        System.out.println("Initial parameters: L=" + l + ", N=" + n);

        String binary = generateBinary(l);
        String maxS = binary;
        int max = getAdaptabilityDecimal(maxS);
        List<String> neighbours = getNeighbours(maxS, l);

        for (int i = 0; i < n; i++) {
            System.out.println("Step " + (i + 1));
            if (neighbours.isEmpty()) {
                break;
            }
            binary = getBestNeighbour(neighbours);
            if (max < getAdaptabilityDecimal(binary)) {
                maxS = binary;
                max = getAdaptabilityDecimal(maxS);
                neighbours = getNeighbours(maxS, l);
            } else {
                break;
            }
            System.out.println("Current s=" + binary + ", max=" + max + ", maxS=" + maxS);
            System.out.println("Current neighbours=" + neighbours);
        }
        System.out.println("\nFound solution:\nmax=" + max + ", maxS=" + maxS);
    }

    private static String getBestNeighbour(List<String> neighbours) {
        String best = "";
        int max = 0;
        for (String n : neighbours) {
            int adaptability = getAdaptabilityDecimal(n);
            if (max < adaptability) {
                max = adaptability;
                best = n;
            }
        }
        return best;
    }

    private static List<String> getNeighbours(String binary, int l) {
        List<String> neighbours = new ArrayList<>();
        for (int i = 0; i < l; i++) {
            StringBuilder sb = new StringBuilder(binary);
            char replaceChar = '0';
            if (binary.charAt(i) == '0') {
                replaceChar = '1';
            }
            sb.setCharAt(i, replaceChar);
            neighbours.add(sb.toString());
        }
        return neighbours;
    }

    public static void main(String[] args) {
        int l = 5;
        int n = 32;
        Map<String, Integer> landscape = generateLandscape(l);
        printLandscape(landscape);
        hillClimbingBreadthMethod(l, n);
    }
}