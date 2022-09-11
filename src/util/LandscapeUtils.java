package util;

import java.util.HashMap;
import java.util.Map;

import static util.AdaptibilityUtils.generateBinary;
import static util.AdaptibilityUtils.getAdaptabilityDecimal;

public class LandscapeUtils {

    public static void printLandscape(Map<String, Integer> landscape) {
        System.out.println("Landscape:");
        landscape.forEach(
                (key, value) -> System.out.println(key + " " + value)
        );
        System.out.println();
    }

    public static Map<String, Integer> generateLandscape(int l) {
        Map<String, Integer> landscape = new HashMap<>();
        while (landscape.size() != 32) {
            String binary = generateBinary(l);
            int adaptability = getAdaptabilityDecimal(binary);
            landscape.put(binary, adaptability);
        }
        return landscape;
    }
}
