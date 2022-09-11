package hill_climbing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HillClimbingBreadth {

    private static final Random rand = new Random();

    public static void hillClimbingBreadthMethod(int l, int n) {
        String binary = generateBinary(l);
        String maxS = binary;
        int max = getAdaptability(maxS);
        List<String> neighbours = getNeighbours(maxS, l);

        System.out.println("Initial parameters: L=" + l + ", N=" + n);
        for (int i = 0; i < n; i++) {
            System.out.println("Step " + (i + 1));
            System.out.println("Current s=" + binary + ", max=" + max + ", maxS=" + maxS);
            System.out.println("Current neighbours=" + neighbours);
            if (neighbours.isEmpty()) {
                break;
            }
            binary = getBestNeighbour(neighbours);
            if (max < getAdaptability(binary)) {
                maxS = binary;
                max = getAdaptability(maxS);
                neighbours = getNeighbours(maxS, l);
            } else {
                break;
            }
        }
        System.out.println("\nFound solution:\nmax=" + max + ", maxS=" + maxS);
    }

    private static String getBestNeighbour(List<String> neighbours) {
        String best = "";
        int max = 0;
        for (String n : neighbours) {
            int adaptability = getAdaptability(n);
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

    // value of decimal interpretation of a binary number
    private static int getAdaptability(String binary) {
        return Integer.parseInt(binary, 2);
    }

    private static String generateBinary(int l) {
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < l; i++) {
            binary.append(rand.nextInt(2));
        }
        return binary.toString();
    }

    public static void main(String[] args) {
        hillClimbingBreadthMethod(5, 32);
    }
}