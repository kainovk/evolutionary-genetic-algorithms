package util;

import java.util.HashMap;
import java.util.Map;

import static util.AdaptibilityUtils.generateBinary;
import static util.AdaptibilityUtils.getAdaptabilityDecimal;
import static util.AdaptibilityUtils.getAdaptability5SinPlusLn;

public class LandscapeUtils {

    public static void printLandscapeInteger(Map<String, Integer> landscape) {
        System.out.println("Landscape:");
        landscape.forEach(
                (key, value) -> System.out.println(key + " " + value)
        );
        System.out.println();
    }

    public static void printLandscapeDouble(Map<String, Double> landscape) {
        System.out.println("Landscape:");
        landscape.forEach(
                (key, value) -> System.out.printf("%s %.2f%n", key, value)
        );
        System.out.println();
    }

    public static Map<String, Integer> generateLandscapeDecimal(int l) {
        Map<String, Integer> landscape = new HashMap<>();
        while (landscape.size() != 32) {
            String binary = generateBinary(l);
            int adaptability = getAdaptabilityDecimal(binary);
            landscape.put(binary, adaptability);
        }
        return landscape;
    }

    public static Map<String, Double> generateLandscape5SinPlusLn(int l) {
        Map<String, Double> landscape = new HashMap<>();
        while (landscape.size() != 32) {
            String binary = generateBinary(l);
            double adaptability = getAdaptability5SinPlusLn(binary);
            landscape.put(binary, adaptability);
        }
        return landscape;
    }
}