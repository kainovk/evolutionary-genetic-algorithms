package hill_climbing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HillClimbingDepth {

    private static final Random rand = new Random();

    private static final int L = 5;
    private static final int N = 32;

    public static void main(String[] args) {
        String binary = generateBinary(L);
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
            int randomIndex = rand.nextInt(neighbours.size());
            binary = neighbours.get(randomIndex);
            neighbours.remove(randomIndex);
            if (max < getAdaptability(binary)) {
                maxS = binary;
                max = getAdaptability(maxS);
                neighbours = getNeighbours(maxS);
            }
        }
        System.out.println("\nFound solution:\nmax=" + max + ", maxS=" + maxS);
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

    private static String generateBinary(int length) {
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < length; i++) {
            binary.append(rand.nextInt(2));
        }
        return binary.toString();
    }
}
