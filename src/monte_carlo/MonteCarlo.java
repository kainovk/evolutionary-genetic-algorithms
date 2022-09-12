package monte_carlo;

import java.util.Map;

import static util.AdaptibilityUtils.generateBinary;
import static util.AdaptibilityUtils.getAdaptabilityDecimal;
import static util.LandscapeUtils.generateLandscapeDecimal;
import static util.LandscapeUtils.printLandscapeDecimal;

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
                System.out.println("MAX CHANGED!!");
            }
            System.out.println("Current s=" + binary + ", u=" + adaptability + ", max=" + max + ", maxS=" + maxS);
        }
        System.out.println("Found solution:\nmax=" + max + ", maxS=" + maxS);
    }

    public static void main(String[] args) {
        int l = 15;
        int n = 10;
        Map<String, Integer> landscape = generateLandscapeDecimal(l);
        printLandscapeDecimal(landscape);
        monteCarloMethod(l, n);
    }
}