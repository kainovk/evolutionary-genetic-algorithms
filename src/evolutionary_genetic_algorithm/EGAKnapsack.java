package evolutionary_genetic_algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

class Individual implements Comparable {

    private static final Random rand = new Random();

    private final int[] chromosome;
    private int fitness;
    private int weight;

    public Individual(int chromosomeNumber, int[] w, int[] c) {
        chromosome = new int[chromosomeNumber];
        for (int i = 0; i < chromosomeNumber; i++) {
            chromosome[i] = Math.random() >= 0.5 ? 1 : 0;
        }
        fitness = getFitness(c);
        weight = getWeight(w);
    }

    public Individual(int[] chromosome, int[] w, int[] c) {
        this.chromosome = chromosome;
        fitness = getFitness(c);
        weight = getWeight(w);
    }

    public Individual(Individual i1, Individual i2, int[] w, int[] c) {
        int l = rand.nextInt(c.length);
        chromosome = crossoverChromosomesUnique(i1.chromosome, i2.chromosome, l);
        fitness = getFitness(c);
        weight = getWeight(w);
    }

    public int getFitness() {
        return fitness;
    }

    public Set<Individual> crossover(Individual p2, int[] w, int[] c, int crossoverChoice) {
        Set<Individual> resultSet = new HashSet<>();
        Individual i1 = new Individual(this, p2, w, c);
        Individual i2 = new Individual(p2, this, w, c);

        switch (crossoverChoice) {
            case 1:
                break;
            case 2:
                i1.mutateInverse(100, c, w);
                i2.mutateInverse(100, c, w);
                break;
        }
        resultSet.add(i1);
        resultSet.add(i2);
        return resultSet;
    }

    public boolean isCorrect(int wMax, int[] w) {
        if (chromosome.length == 0) {
            return false;
        }
        int weightSum = 0;
        for (int i = 0; i < w.length; i++) {
            if (chromosome[i] == 1) {
                weightSum += w[i];
            }
        }
        return weightSum <= wMax;
    }

    private int[] crossoverChromosomesUnique(int[] chromosome1, int[] chromosome2, int l) {
        int length = chromosome1.length;
        int[] result = new int[length];
        for (int i = 0; i < l; i++) {
            result[i] = chromosome1[i];
        }
        for (int i = l + 1; i < length; i++) {
            result[i] = chromosome2[i];
        }
        return result;
    }

    private int getFitness(int[] c) {
        int result = 0;
        for (int i = 0; i < chromosome.length; i++) {
            if (chromosome[i] == 1) {
                result += c[i];
            }
        }
        return result;
    }

    // 0 -> 1 c шансом 10% для каждой хромосомы
    public void mutateEvolve(int mutationChance, int[] c, int[] w) {
        double evoluteChance = 0.1;
        if (mutationChance > 0 && (Math.random() * 100) < mutationChance) {
            for (int i = 0; i < chromosome.length; i++) {
                if (chromosome[i] == 0 && Math.random() < evoluteChance) {
                    chromosome[i] = 1;
                }
            }
        }
        fitness = getFitness(c);
        weight = getWeight(w);
    }

    public void mutateInverse(int mutationChance, int[] c, int[] w) {
        if (mutationChance > 0 && (Math.random() * 100) < mutationChance) {
            for (int i = 0; i < chromosome.length; i++) {
                chromosome[i] = chromosome[i] == 0 ? 1 : 0;
            }
        }
        fitness = getFitness(c);
        weight = getWeight(w);
    }

    public void mutateRearrange(int mutationChance, int[] c, int[] w) {
        int length = chromosome.length;
        if (mutationChance > 0 && (Math.random() * 100) < mutationChance) {
            int randomIndex = rand.nextInt(chromosome.length / 2);
            int tmp = chromosome[randomIndex];
            chromosome[randomIndex] = chromosome[length - randomIndex - 1];
            chromosome[length - randomIndex - 1] = tmp;
        }
        fitness = getFitness(c);
        weight = getWeight(w);
    }

    public int getWeight(int[] w) {
        int result = 0;
        for (int i = 0; i < chromosome.length; i++) {
            if (chromosome[i] == 1) {
                result += w[i];
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Individual{" +
                "chromosome=" + Arrays.toString(chromosome) +
                ", fitness=" + fitness +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individual that = (Individual) o;
        return Arrays.equals(chromosome, that.chromosome);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(chromosome);
    }


    @Override
    public int compareTo(Object o) {
        Individual other = (Individual) o;
        return getFitness() - other.getFitness();
    }
}

public class EGAKnapsack {

    private final Random rand = new Random();

    // численность популяции
    private final int populationSize;

    // число брачных пар
    private final int crossoversNumber;

    // вероятность мутации в процентах
    private final int mutationChance;

    // число поколений
    private final int lifeCycleLimit;

    // выбор операторов
    private final int formPopulationChoice;
    private final int reproduceChoice;
    private final int crossoverChoice;
    private final int mutateChoice;
    private final int selectionChoice;

    // задача о рюкзаке
    private final int wMax;
    private final int[] w;
    private final int[] c;

    public EGAKnapsack(int populationSize, int crossoversNumber,
                       int mutationChance, int lifeCycleLimit,
                       int wMax, int[] w, int[] c,
                       int formPopulationChoice, int reproduceChoice, int crossoverChoice, int mutateChoice, int selectionChoice) {
        this.populationSize = populationSize;
        this.crossoversNumber = crossoversNumber;
        this.mutationChance = mutationChance;
        this.lifeCycleLimit = lifeCycleLimit;
        this.wMax = wMax;
        this.w = w;
        this.c = c;
        this.formPopulationChoice = formPopulationChoice;
        this.reproduceChoice = reproduceChoice;
        this.crossoverChoice = crossoverChoice;
        this.mutateChoice = mutateChoice;
        this.selectionChoice = selectionChoice;
    }

    public void runEGA() {
        // 1) случайное формирование
        // 2) жадный алгоритм
        Set<Individual> individuals = formInitialPopulation(formPopulationChoice);

        for (int t = 0; t < lifeCycleLimit; t++) {
            printSeparator();
            System.out.println("POPULATION #" + (t + 1));
            printSeparator();

            // 1) случайный выбор
            // 2) турнирный выбор

            // 1) скрещивание
            // 2) скрещивание+инверсия
            for (int i = 0; i < crossoversNumber; i++) {
                reproduce(individuals, reproduceChoice);
            }

            // 1) 0 -> 1, c шансом 10% для каждой хромосомы
            // 2) инверсия
            // 3) перестановка 2 хромосом
            individuals = mutate(individuals, mutateChoice);

            // 1) случайный отбор
            // 2) fibonacci selection + (сортируем по fitness, удаляем по индексам фибоначчи, начиная с 1)
            individuals = formNextPopulation(individuals, selectionChoice);

            System.out.println("Best individual:");
            System.out.println(individuals.stream().max(Individual::compareTo));
        }

        printSeparator();
        System.out.println("FINAL POPULATION:");
        printSeparator();
        individuals.forEach(System.out::println);
        printSeparator();
        System.out.println("BEST INDIVIDUAL:");
        printSeparator();
        Individual bestIndividual = individuals.stream().max(Individual::compareTo).orElse(null);
        System.out.println(bestIndividual);
    }

    private Set<Individual> formNextPopulation(Set<Individual> individuals, int selectionChoice) {
        printSeparator();
        System.out.println("SELECTION PHASE");
        printSeparator();

        Set<Individual> resultSet = new TreeSet<>();
        switch (selectionChoice) {
            case 1:
                resultSet = randomSelection(individuals);
                break;
            case 2:
                resultSet = fibonacciSelection(individuals);
                break;
        }
        return resultSet;
    }

    private Set<Individual> fibonacciSelection(Set<Individual> individuals) {
        List<Individual> individualList = new ArrayList<>(individuals);
        Collections.sort(individualList);
        while (individualList.size() > populationSize) {
            List<Integer> fibNumbers = getFibNumbersUntil(individualList.size());
            while (!fibNumbers.isEmpty() && individualList.size() > populationSize) {
                int delIndex = fibNumbers.get(0);
                if (delIndex >= individualList.size()) {
                    break;
                }
                individualList.remove(delIndex);
                fibNumbers.remove(0);
            }
        }
        return new TreeSet<>(individualList);
    }

    private List<Integer> getFibNumbersUntil(int size) {
        List<Integer> numbers = new ArrayList<>();
        int lastNumber = 0;
        int i = 2; // start from fib(3) -> 1
        while (lastNumber < size - 1) {
            int number = getFibNumber(i);
            numbers.add(number);
            lastNumber = number;
            i++;
        }
        return numbers;
    }

    private int getFibNumber(int n) {
        if (n <= 1)
            return n;
        return getFibNumber(n - 1) + getFibNumber(n - 2);
    }

    private Set<Individual> randomSelection(Set<Individual> individuals) {
        List<Individual> resultList = new ArrayList<>(individuals);
        while (resultList.size() > populationSize) {
            int index = rand.nextInt(resultList.size());
            resultList.remove(index);
        }
        return new HashSet<>(resultList);
    }

    private void evaluateAndHandleConstraints() {

    }

    private Set<Individual> mutate(Set<Individual> individuals, int mutateChoice) {
        printSeparator();
        System.out.println("MUTATION PHASE");
        printSeparator();

        switch (mutateChoice) {
            case 1:
                individuals.forEach(i -> i.mutateEvolve(mutationChance, c, w));
                break;
            case 2:
                individuals.forEach(i -> i.mutateInverse(mutationChance, c, w));
                break;
            case 3:
                individuals.forEach(i -> i.mutateRearrange(mutationChance, c, w));
        }
        return individuals.stream().filter(individual -> individual.isCorrect(wMax, w)).collect(Collectors.toSet());
    }

    private void reproduce(Set<Individual> individuals, int reproduceChoice) {
        printSeparator();
        System.out.println("REPRODUCTION PHASE");
        printSeparator();
        Set<Individual> children = new HashSet<>();
        switch (reproduceChoice) {
            case 1:
                children = randomReproduction(individuals);
                break;
            case 2:
                children = tournamentReproduction(individuals);
                break;
        }
        children = children.stream()
                .filter(individual -> individual.isCorrect(wMax, w))
                .collect(Collectors.toSet());
        System.out.println("got children: " + children);
        individuals.addAll(children);
        System.out.printf("added %d children to initial list, now its size = %d%n", children.size(), individuals.size());
    }

    private Set<Individual> tournamentReproduction(Set<Individual> individuals) {
        int size = 3;

        Individual p1 = getRandomTournamentWinner(individuals, size);
        if (p1 == null) {
            System.out.println("cannot choose parent");
        }
        Individual p2 = null;
        int stopLimit = populationSize;
        while (p2 == null || p1.equals(p2)) {
            if (stopLimit > 0) {
                p2 = getRandomTournamentWinner(individuals, size);
                stopLimit--;
            } else {
                p2 = chooseWhateverNotP1(individuals, p1);
            }

        }
        return p1.crossover(p2, w, c, crossoverChoice);
    }

    private Individual chooseWhateverNotP1(Set<Individual> individuals, Individual p1) {
        for (Individual p2 : individuals) {
            if (!p2.equals(p1)) {
                return p2;
            }
        }
        return null;
    }

    private Individual getRandomTournamentWinner(Set<Individual> individuals, int size) {
        List<Individual> list = new ArrayList<>(individuals);
        Collections.shuffle(list);
        System.out.println("tournament:");
        Optional<Individual> winner = list.stream()
                .limit(size)
                .peek(individual -> System.out.println("candidate: " + individual))
                .reduce((i1, i2) -> {
                    if (i1.getFitness() > i2.getFitness()) {
                        return i1;
                    }
                    return i2;
                });
        return winner.orElse(null);
    }

    private Set<Individual> randomReproduction(Set<Individual> individuals) {
        List<Individual> parents = chooseParents(individuals);
        Individual p1 = parents.get(0);
        Individual p2 = parents.get(1);
        return p1.crossover(p2, w, c, crossoverChoice);
    }

    private List<Individual> chooseParents(Set<Individual> individuals) {
        List<Individual> list = new ArrayList<>(individuals);
        Collections.shuffle(list);
        return list.subList(0, 2);
    }

    private Set<Individual> formInitialPopulation(int formPopulationChoice) {
        printSeparator();
        System.out.println("INITIAL POPULATION FORMATION PHASE");
        printSeparator();
        Set<Individual> individuals = new TreeSet<>(Individual::compareTo);
        int chromosomeNumber = c.length;

        switch (formPopulationChoice) {
            case 1:
                formRandomly(individuals, chromosomeNumber);
                break;
            case 2:
                if (populationSize > chromosomeNumber) {
                    System.out.println("cannot populate: populationSize > chromosomeNumber");
                    System.exit(0);
                }
                formGreedy(individuals, chromosomeNumber);
                break;
        }
        return individuals;
    }

    private void formGreedy(Set<Individual> individuals, int chromosomeNumber) {
        for (int i = 0; i < populationSize; i++) {
            Individual individual = generateGreedyFromIndex(i, chromosomeNumber);
            System.out.println("generated greedy from index " + i + ": " + individual);
            individuals.add(individual);
        }
    }

    private Individual generateGreedyFromIndex(int i, int chromosomeNumber) {
        int[] chromosome = new int[chromosomeNumber];
        int curWeight = 0;
        for (int j = i; j < chromosomeNumber; j++) {
            if (wMax >= curWeight + w[j]) {
                chromosome[j] = 1;
                curWeight += w[j];
            }
        }
        for (int j = 0; j < i; j++) {
            if (wMax >= curWeight + w[j]) {
                chromosome[j] = 1;
                curWeight += w[j];
            }
        }
        return new Individual(chromosome, w, c);
    }

    private void formRandomly(Set<Individual> individuals, int chromosomeNumber) {
        while (individuals.size() < populationSize) {
            Individual individual = new Individual(chromosomeNumber, w, c);
            if (individual.isCorrect(wMax, w)) {
                individuals.add(individual);
                System.out.println("created an individual: " + individual);
            }
        }
    }

    private void printSeparator() {
        System.out.println("*********************************");
    }

    public static void main(String[] args) {
        /*Scanner sc = new Scanner(System.in);
        System.out.println("Enter population size:");
        int populationSize = sc.nextInt();
        System.out.println("Enter number of crossovers:");
        int crossoversNumber = sc.nextInt();
        System.out.println("Enter mutation chance in range [0,100]:");
        int mutationChance = sc.nextInt();
        System.out.println("Enter life cycle limit:");
        int lifeCycleLimit = sc.nextInt();

        System.out.println("Enter initial population operator:");
        System.out.println("1 - random");
        System.out.println("2 - greedy");
        int formPopulationChoice = sc.nextInt();
        System.out.println("Enter reproduction operator:");
        System.out.println("1 - random");
        System.out.println("2 - tournament");
        int reproduceChoice = sc.nextInt();
        System.out.println("Enter mutation operator:");
        System.out.println("1 - 0->1 with 10% chance for each chromosome");
        System.out.println("2 - inversion");
        System.out.println("3 - rearrange 2 chromosomes");
        int mutateChoice = sc.nextInt();
        System.out.println("Enter selection operator:");
        System.out.println("1 - random");
        System.out.println("2 - fibonacci selection");
        int selectionChoice = sc.nextInt();*/

        int populationSize = 15;
        int crossoversNumber = 5;
        int mutationChance = 20;
        int lifeCycleLimit = 50;

        int formPopulationChoice = 1;
        int reproduceChoice = 2;
        int crossoverChoice = 1;
        int mutateChoice = 1;
        int selectionChoice = 2;


        if (populationSize < 0 || lifeCycleLimit < 0 ||
                mutationChance < 0 || mutationChance > 100 ||
                crossoversNumber < 1 || crossoversNumber > populationSize / 2 ||
                formPopulationChoice < 1 || formPopulationChoice > 2 ||
                reproduceChoice < 1 || reproduceChoice > 2 ||
                mutateChoice < 1 || mutateChoice > 3 ||
                selectionChoice < 1 || selectionChoice > 2) {
            System.out.println("Incorrect input parameters");
            System.exit(-1);
        }

        int wMax = 114;
        int[] w = new int[]{17, 20, 1, 26, 28, 26, 21, 23, 18, 24, 7, 20, 28, 5, 22};
        int[] c = new int[]{27, 2, 17, 12, 3, 16, 10, 19, 1, 6, 12, 12, 27, 28, 9};

        EGAKnapsack ega = new EGAKnapsack(
                populationSize,
                crossoversNumber, mutationChance,
                lifeCycleLimit,
                wMax, w, c,
                formPopulationChoice, reproduceChoice, crossoverChoice, mutateChoice, selectionChoice);
        ega.runEGA();
    }
}