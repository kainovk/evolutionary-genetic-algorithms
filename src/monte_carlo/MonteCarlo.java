package monte_carlo;

import java.util.Map;

import static util.AdaptibilityUtils.generateBinary;
import static util.AdaptibilityUtils.getAdaptabilityDecimal;
import static util.LandscapeUtils.generateLandscape;
import static util.LandscapeUtils.printLandscape;

public class MonteCarlo {

    public static void monteCarloMethod(int l, int n) {
        System.out.println("Initial parameters: L=" + l + ", N=" + n);
        int max = 0;
        String maxS = "";

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
        System.out.println("Found solution:\nmax=" + max + ", maxS=" + maxS);
    }

    public static void main(String[] args) {
        int l = 15;
        int n = 32;
        Map<String, Integer> landscape = generateLandscape(l);
        printLandscape(landscape);
        monteCarloMethod(l, n);
    }
}