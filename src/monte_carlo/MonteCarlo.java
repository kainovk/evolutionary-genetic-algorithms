package monte_carlo;

import util.LandscapeUtils;

import java.util.HashMap;
import java.util.Map;

import static util.AdaptibilityUtils.generateBinary;
import static util.AdaptibilityUtils.getAdaptabilityDecimal;
import static util.LandscapeUtils.generateLandscapeDecimal;

public class MonteCarlo {

    public static Map<String, Object> monteCarloMethod(int l, int n) {
        System.out.println("Initial parameters: L=" + l + ", N=" + n);

        int max = 0;
        String maxS = "";
        Map<String, Object> ans = new HashMap<>();

        for (int i = 0; i < n; i++) {
            System.out.println("Step " + (i + 1));
            String binary = generateBinary(l);
            int adaptability = getAdaptabilityDecimal(binary);
            if (max < adaptability) {
                max = adaptability;
                maxS = binary;
            }
            System.out.println("Current s=" + binary + ", max=" + max + ", maxS=" + maxS);
        }
        ans.put("max", max);
        ans.put("maxS", maxS);
        return ans;
    }

    public static void main(String[] args) {
        int l = 15;
        int n = 32;
        Map<String, Integer> landscape = generateLandscapeDecimal(l);
        LandscapeUtils.printLandscapeInteger(landscape);
        Map<String, Object> ans = monteCarloMethod(l, n);
        System.out.println("\nFound solution:\nmax=" + ans.get("max") + ", maxS=" + ans.get("maxS"));
    }
}