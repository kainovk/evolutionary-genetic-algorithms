package hill_climbing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static util.AdaptibilityUtils.generateBinary;
import static util.AdaptibilityUtils.getAdaptabilityDecimal;
import static util.LandscapeUtils.generateLandscapeDecimal;
import static util.LandscapeUtils.printLandscapeDecimal;

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
            int adaptability = getAdaptabilityDecimal(binary);
            if (max < adaptability) {
                maxS = binary;
                max = adaptability;
                neighbours = getNeighbours(maxS, l);
                System.out.println("MAX CHANGED!!");
            } else {
                break;
            }
            System.out.println("Current s=" + binary + ", u=" + adaptability+ ", max=" + max + ", maxS=" + maxS);
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
        int n = 10;
        Map<String, Integer> landscape = generateLandscapeDecimal(l);
        printLandscapeDecimal(landscape);
        hillClimbingBreadthMethod(l, n);
    }
}