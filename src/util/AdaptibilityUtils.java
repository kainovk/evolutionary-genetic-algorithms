package util;

import java.util.Random;

public class AdaptibilityUtils {

    private static final Random rand = new Random();

    public static int getAdaptabilityDecimal(String binary) {
        return Integer.parseInt(binary, 2);
    }

    public static double getAdaptabilityQuadric(String binary, int l) {
        int decimal = Integer.parseInt(binary, 2);
        return Math.pow((decimal - Math.pow(2, l - 1)), 2);
    }

    public static String generateBinary(int length) {
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < length; i++) {
            binary.append(rand.nextInt(2));
        }
        return binary.toString();
    }
}
