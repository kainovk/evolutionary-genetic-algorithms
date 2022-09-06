package monte_carlo;

import java.util.Random;

public class MonteCarlo {

    private static final Random rand = new Random();

    public static void main(String[] args) {
        int l = 15;
        int n = 32;
        int max = 0;
        String maxS = "empty";

        System.out.println("Initial parameters: L=" + l + ", N=" + n);
        for (int i = 0; i < n; i++) {
            String binary = generateBinary(l);
            System.out.println("Step " + (i + 1));
            System.out.println("Current s=" + binary + ", max=" + max + ", maxS=" + maxS);
            int adaptability = getAdaptability(binary);
            if (max < adaptability) {
                max = adaptability;
                maxS = binary;
            }
        }
        System.out.println("Found solution:\nmax=" + max + ", maxS=" + maxS);
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