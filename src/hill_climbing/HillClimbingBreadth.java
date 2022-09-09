package hill_climbing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HillClimbingBreadth {

    private static final Random rand = new Random();

    private static final int L = 5;
    private static final int N = 32;

    public static void main(String[] args) {
        String binary = generateBinary();
        String maxS = binary;
        int max = getAdaptability(maxS);
        List<String> neighbours = getNeighbours(maxS);

        System.out.println("Initial parameters: L=" + L + ", N=" + N);
        for (int i = 0; i < N; i++) {
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
                neighbours = getNeighbours(maxS);
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

    private static List<String> getNeighbours(String binary) {
        List<String> neighbours = new ArrayList<>();
        for (int i = 0; i < L; i++) {
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

    private static String generateBinary() {
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < HillClimbingBreadth.L; i++) {
            binary.append(rand.nextInt(2));
        }
        return binary.toString();
    }
}