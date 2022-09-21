package multiple_launch;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static hill_climbing.HillClimbingBreadth.hillClimbingBreadthMethod;
import static hill_climbing.HillClimbingDepth.hillClimbingDepthMethod;
import static monte_carlo.MonteCarlo.monteCarloMethod;
import static util.LandscapeUtils.generateLandscape5SinPlusLn;
import static util.LandscapeUtils.printLandscapeDouble;

public class MultipleLaunch {

    private static final Random rand = new Random();

    public static Map<String, Object> multipleLaunch(int l, int n) {
        Map<String, Object> ans = new HashMap<>();
        double max = 0;
        String maxS = "";
        for (int i = 0; i < n; i++) {
            System.out.println("STEP " + (i + 1));
            Map<String, Object> solution = runRandomMethod(l, n);
            String maxSi = (String) solution.get("maxS");
            if ((double) solution.get("max") > max) {
                max = (double) solution.get("max");
                maxS = maxSi;
            }
            System.out.println("\nCurrent s=" + maxSi + ", max=" + max + ", maxS=" + maxS);
            System.out.println("******************************************************");
        }
        ans.put("max", max);
        ans.put("maxS", maxS);
        return ans;
    }

    private static Map<String, Object> runRandomMethod(int l, int n) {
        Map<String, Object> ans = new HashMap<>();
        int k = rand.nextInt(3);
        switch (k) {
            case 0:
                System.out.println("Monte Carlo method:");
                ans = monteCarloMethod(l, n);
                break;
            case 1:
                System.out.println("Hill Climbing Depth method:");
                ans = hillClimbingDepthMethod(l, n);
                break;
            case 2:
                System.out.println("Hill Climbing Breadth method:");
                ans = hillClimbingBreadthMethod(l, n);
                break;
            default:
                System.out.println("method not found");
        }
        return ans;
    }

    public static void main(String[] args) {
        int l = 7;
        int n = 10;
        Map<String, Double> landscape = generateLandscape5SinPlusLn(l);
        printLandscapeDouble(landscape);
        Map<String, Object> ans = multipleLaunch(l, n);
        System.out.println("Found solution:\nmax=" + ans.get("max") + ", maxS=" + ans.get("maxS"));
    }
}
