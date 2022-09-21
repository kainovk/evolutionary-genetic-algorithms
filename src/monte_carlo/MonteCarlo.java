package monte_carlo;

import util.LandscapeUtils;

import java.util.HashMap;
import java.util.Map;

import static util.AdaptibilityUtils.generateBinary;
import static util.AdaptibilityUtils.getAdaptability5SinPlusLn;
import static util.LandscapeUtils.generateLandscapeDecimal;

public class MonteCarlo {

    public static Map<String, Object> monteCarloMethod(int l, int n) {
        System.out.println("Initial parameters: L=" + l + ", N=" + n);

        double max = 0;
        String maxS = "";
        Map<String, Object> ans = new HashMap<>();

        for (int i = 0; i < n; i++) {
            System.out.println("Step " + (i + 1));
            String binary = generateBinary(l);
            double adaptability = getAdaptability5SinPlusLn(binary);
            if (max < adaptability) {
                max = adaptability;
                maxS = binary;
                System.out.println("MAX CHANGED!!");
            }
            System.out.println("Current s=" + binary + ", u=" + adaptability + ", max=" + max + ", maxS=" + maxS);
        }
        System.out.println("Found solution:\nmax=" + max + ", maxS=" + maxS);
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