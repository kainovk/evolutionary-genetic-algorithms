package util;

import java.util.HashMap;
import java.util.Map;

import static util.AdaptibilityUtils.*;

public class LandscapeUtils {

    public static void printLandscapeDecimal(Map<String, Integer> landscape) {
        System.out.println("Landscape:");
        landscape.forEach(
                (key, value) -> System.out.println(key + " " + value)
        );
        System.out.println();
    }

    public static void printLandscapeDouble(Map<String, Double> landscape) {
        System.out.println("Landscape:");
        landscape.forEach(
                (key, value) -> System.out.println(key + " " + value)
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

    public static Map<String, Double> generateLandscapeDouble(int l) {
        Map<String, Double> landscape = new HashMap<>();
        while (landscape.size() != 32) {
            String binary = generateBinary(l);
            double adaptability = getAdaptabilityQuadric(binary, l);
            landscape.put(binary, adaptability);
        }
        return landscape;
    }
}
