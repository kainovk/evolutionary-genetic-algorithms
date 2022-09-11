package util;

import java.util.Random;

public class AdaptibilityUtils {

    private static final Random rand = new Random();

    public static int getAdaptabilityDecimal(String binary) {
        return Integer.parseInt(binary, 2);
    }

    public static double getAdaptability5SinPlusLn(String binary) {
        double decimal = Integer.parseInt(binary, 2);
        return 5 * Math.sin(decimal) + Math.log1p(decimal);
    }

    public static String generateBinary(int length) {
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < length; i++) {
            binary.append(rand.nextInt(2));
        }
        return binary.toString();
    }
}
