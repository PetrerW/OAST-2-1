package com.oast;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

public class EvolutionaryAlgorithm {

    private Network network;

    private int maxTime; //czas w [s]
    private long seed;
    private float pCross;
    private float pMutate;
    private float percentOfBestChromosomes;
    private int numberOfChromosomes;
    private Random random;


    // ograniczenia
    private long endTime;
    private int maxNumberOfContinuousNonBetterSolutions;
    private int numberOfGenerations;
    private int maxMutationNumber;

    // aktualny stan
    private int currentGeneration;
    private int currentMutation;
    private int currentNumberOfContinuousNonBetterSolutions;


    public EvolutionaryAlgorithm(float pCross, float pMutate, int maxTime, int numberOfChromosomes, float percentOfBestChromosomes, int numberOfGenerations,
                                 int maxMutationNumber, int maxNumberOfContinuousNonBetterSolutions, long seed, Network network) {
        this.pCross = pCross;
        this.pMutate = pMutate;
        this.maxTime = maxTime;
        this.numberOfChromosomes = numberOfChromosomes;
        this.percentOfBestChromosomes = percentOfBestChromosomes;
        this.numberOfGenerations = numberOfGenerations;
        this.maxMutationNumber = maxMutationNumber;
        this.maxNumberOfContinuousNonBetterSolutions = maxNumberOfContinuousNonBetterSolutions;
        this.seed = seed;
        this.random = new Random(seed);
        this.network = network;

        this.currentGeneration = 0;
        this.currentNumberOfContinuousNonBetterSolutions = 0;
        this.currentMutation = 0;
    }

    /**
     * @return True if the loop should go on
     */
    public boolean computeStopCriterion() {

        if (System.currentTimeMillis() >= this.endTime)
            return false;

        if (this.currentGeneration >= this.numberOfGenerations)
            return false;

        if (this.currentMutation >= this.maxMutationNumber)
            return false;

        if (this.currentNumberOfContinuousNonBetterSolutions >= this.maxNumberOfContinuousNonBetterSolutions)
            return false;

        return true;
    }


    public Solution computeDDAP() {

        //Początkowa pula rozwiązań - chromosomy
        List<Solution> population = getInitialRandomPopulation(numberOfChromosomes, seed);

        //Startowe najlepsze rozwiązania - koszt = infinity
        Solution bestSolution = new Solution(new HashMap<>());
        bestSolution.setCost(Float.MAX_VALUE);

        //maxTime w sec
        endTime = System.currentTimeMillis() + maxTime * 1000;
        while (computeStopCriterion()) {
            currentGeneration++;

            Solution bestSolutionOfGeneration = new Solution(new HashMap<>());
            bestSolutionOfGeneration.setCost(Float.MAX_VALUE);

            for (int i = 0; i < population.size(); i++) {

                float cost = 0;
                //Obliczamy koszt dla każdego z chromosomów
                List<Integer> costsOfLinks = population.get(i).getCapacitiesOfLinks();
                for (int j = 0; j < population.get(i).getCapacitiesOfLinks().size(); j++) {
                    cost += network.getLinks().get(j).getModule().getCost() * costsOfLinks.get(j);

                }
                population.get(i).setCost(cost);

                //zapisujemy najlepsze rozwiazanie w generacji
                if (population.get(i).getCost() < bestSolutionOfGeneration.getCost())
                    bestSolutionOfGeneration = population.get(i);
            }

            // zapisujemy najlepsze rozwiazanie w historii
            if (bestSolutionOfGeneration.getCost() < bestSolution.getCost()) {
                bestSolution = bestSolutionOfGeneration;
                currentNumberOfContinuousNonBetterSolutions = 0;
            } else
                currentNumberOfContinuousNonBetterSolutions++;


            population = takeBestDDAP(population, percentOfBestChromosomes);
            population = crossover(population, seed, pCross);
            population = mutation(population, seed, pMutate);
            population = fillLinkCapacitiesForNewSolutions(population);
            // nie możemy w tym momencie wybrac najlepszych bo nie są obliczone koszta (dlatego przed mutacja)

            System.out.println("Cost of generation " + currentGeneration + ": " + bestSolutionOfGeneration.getCost());

        }

        System.out.println("Cost of best solution: " + bestSolution.getCost());

        return bestSolution;
    }


    private List<Solution> takeBestDDAP(List<Solution> solutions, float percentOfBestChromosomes) {
        // Wybieramy x procent najlepszych
        int subListEnd = Math.round(solutions.size() * (percentOfBestChromosomes / 100));
        List<Solution> list0 = solutions.stream()
                .sorted(Comparator.comparing(Solution::getCost))
                .collect(Collectors.toList());

        List<Solution> list = solutions.stream()
                .sorted(Comparator.comparing(Solution::getCost))
                .collect(Collectors.toList())
                .subList(0, Math.round(solutions.size() * (percentOfBestChromosomes / 100)));

        // Dopełniamy najlepszymi, aby populacja nie zmalała
        list.addAll(list0.subList(0, solutions.size() - subListEnd));

        return list;
    }


    private List<Solution> crossover(List<Solution> parents, long seed, float probabilityOfCrossover) {
        List<Solution> children = new ArrayList<>();

        int parentsSize = parents.size();
        //w jednej iteracji krzyżowanie 2 rodziców z listy, wiec liczba iteracji / 2
        // wywalamy rodzicow z listy i bierzemy kolejnych 2
        for (int i = 0; i < parentsSize / 2; i++) {
            children.addAll(
                    crossParents(parents.remove(random.nextInt(parents.size())),
                            parents.remove(random.nextInt(parents.size())),
                            probabilityOfCrossover, seed)
            );
        }
        return children;
    }


    private List<Solution> crossParents(Solution parent0, Solution parent1, float probabilityOfCrossover, long seed) {

        List<Solution> children = new ArrayList<>();

        double rand = random.nextDouble();

        // albo krzyżujemy rodziców i zwracamy dzieci, albo zwracamy rodziców
        if (rand < probabilityOfCrossover) {
            children = new ArrayList<>();
            children.add(new Solution(new HashMap<>()));
            children.add(new Solution(new HashMap<>()));

            for (int i = 0; i < parent0.getNumberOfGenes(); i++) {
                rand = random.nextDouble();

                if (rand > 0.5) {
                    children.get(0)
                            .getMapOfValues().
                            putAll(parent0.getGene(i + 1));
                    children.get(1)
                            .getMapOfValues()
                            .putAll(parent1.getGene(i + 1));
                } else {
                    children.get(1)
                            .getMapOfValues()
                            .putAll(parent0.getGene(i + 1));
                    children.get(0)
                            .getMapOfValues()
                            .putAll(parent1.getGene(i + 1));
                }
            }
            return children;
        }

        List<Solution> solutions = new ArrayList<>();
        solutions.add(parent0);
        solutions.add(parent1);

        return solutions;
    }


    private List<Solution> mutation(List<Solution> population, long seed, float probabilityOfMutation) {
        List<Solution> mutants = new ArrayList<>();

        double rand = random.nextDouble();

        for (int i = 0; i < population.size(); i++) {
            // Losowe wystąpienie mutacji
            if (rand < probabilityOfMutation) {

                currentMutation++;
                Map<Point, Integer> genes = new HashMap<>();

                for (int j = 0; j < population.get(i).getNumberOfGenes(); j++) {
                    genes.putAll(mutateGene(population.get(i).getGene(j + 1), seed));
                }
                mutants.add(new Solution(genes));
            } else {
                mutants.add(population.get(i));
            }
        }
        return mutants;
    }


    private Map<Point, Integer> mutateGene(Map<Point, Integer> gene, long seed) {

        Map<Point, Integer> mutatedGene = new HashMap<>();
        List<Point> points = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        for (Map.Entry<Point, Integer> entry : gene.entrySet()) {
            points.add(entry.getKey());
            values.add(entry.getValue());
        }

        for (int i = 0; i < values.size(); i++) {

            int i0 = random.nextInt(values.size());
            int i1 = random.nextInt(values.size());

            if (values.get(i0) != 0) {
                values.set(i0, values.get(i0) - 1);
                values.set(i1, values.get(i1) + 1);
                break;
            }
        }

        for (int i = 0; i < gene.size(); i++) {
            mutatedGene.put(points.remove(0), values.remove(0));
        }

        return mutatedGene;
    }


    private List<Solution> fillLinkCapacitiesForNewSolutions(List<Solution> solutions) {

        List<List<Integer>> linksCapacities = solutions.stream()
                .map(this::computeLinksCapacitiesOfSolution)
                .collect(Collectors.toList());

        for (int i = 0; i < solutions.size(); i++) {
            if (solutions.get(i).getCapacitiesOfLinks() == null)
                //TODO
                solutions.get(i).setCapacitiesOfLinks(linksCapacities.get(i));
        }
        return solutions;
    }


    public List<Solution> getInitialRandomPopulation(int numberOfChromosomes, long seed) {

        //Ze wszystkich możliwych kombinacji routingu (nie obliczone koszta itp) wybieramy losowe N chromosomow (numberOfChromosomes)
        List<List<Solution>> allCombinations = network.getDemands().stream()
                .map(this::getCombinationsOfOneDemand)
                .collect(Collectors.toList());

        List<Solution> routingPossibilities = new ArrayList<>();

        for (int i = 0; i < numberOfChromosomes; i++) {
            Solution chromosome = new Solution(new HashMap<>());
            for (int j = 0; j < allCombinations.size(); j++) {
                chromosome.getMapOfValues().putAll(allCombinations.get(j).get(random.nextInt(allCombinations.get(j).size())).getMapOfValues());
            }
            routingPossibilities.add(chromosome);
        }

        List<List<Integer>> linksCapacities = routingPossibilities.stream()
                .map(this::computeLinksCapacitiesOfSolution)
                .collect(Collectors.toList());


        List<Solution> list = new ArrayList<>();

        for (int i = 0; i < numberOfChromosomes; i++) {
            Integer rand = random.nextInt(routingPossibilities.size());
            routingPossibilities.get(rand).setCapacitiesOfLinks(linksCapacities.get(rand));
            list.add(routingPossibilities.get(rand));
        }
        return list;
    }


    private List<List<Integer>> getCombinations(Integer sum, Integer numberOfElements) {
        List<List<Integer>> lists = new ArrayList();
        List<Integer> list = new ArrayList();

        for (int i = 0; i <= sum; i++) {
            list.add(i);
        }

        for (int i = 0; i < numberOfElements; i++) {
            lists.add(list);
        }

        return Lists.cartesianProduct(lists).stream()
                .filter(product -> sum.equals(product.stream().mapToInt(Integer::intValue).sum()))
                .collect(Collectors.toList());
    }

    private List<Solution> getCombinationsOfOneDemand(Demand demand) {
        List<Solution> list = new ArrayList();
        Integer numberOfCombinations = calculateNewtonSymbol(demand.getNumberOfPaths() + demand.getVolume() - 1, demand.getVolume());
        List<List<Integer>> combinations = getCombinations(demand.getVolume(), demand.getNumberOfPaths());
        for (int i = 0; i < numberOfCombinations; i++) {
            Map<Point, Integer> mapOfValuesForOneDemand = new HashMap();
            for (int j = 0; j < demand.getNumberOfPaths(); j++) {
                Integer pathId = demand.getDemandPaths().get(j).getId();
                mapOfValuesForOneDemand.put(new Point(demand.getID(), pathId), combinations.get(i).get(pathId - 1));
            }
            list.add(new Solution(mapOfValuesForOneDemand));

        }
        return list;
    }

    private List<Integer> computeLinksCapacitiesOfSolution(Solution solution) {
        List<Integer> linksCapacities = new ArrayList<>();
        for (int i = 0; i < network.getLinks().size(); i++) {
            linksCapacities.add(0);
        }

        List<DemandPath> paths = new ArrayList();
        for (Demand demand : network.getDemands()) {
            paths.addAll(demand.getDemandPaths());
        }

        for (int j = 0; j < network.getLinks().size(); j++) {
            double sum = 0;
            for (DemandPath path : paths) {
                List<Integer> list = Arrays.stream(path.getLinks()).boxed().collect(Collectors.toList());
                if (list.contains(j + 1)) {
                    sum += solution.getMapOfValues()
                            .get(new Point(path.getDemandId(), path.getId()));
                }
            }
            linksCapacities.set(j, (int) Math.ceil(sum / (double) network.getLinks().get(j).getLinkModule()));
        }
        return linksCapacities;
    }


    private int calculateNewtonSymbol(int n, int k) {
        Integer result = 1;
        for (int i = 1; i <= k; i++)
            result = result * (n - i + 1) / i;
        return result;
    }

    public Network getNetwork() {
        return network;
    }


    public Solution computeDAP() {

        //Początkowa pula rozwiązań - chromosomy
        List<Solution> population = getInitialRandomPopulation(numberOfChromosomes, seed);

        //Startowe najlepsze rozwiązania - koszt = infinity
        Solution bestSolution = new Solution(new HashMap<>());
        bestSolution.setNumberOfLinksWithExceededCapacity(Integer.MAX_VALUE);

        //maxTime w sec
        endTime = System.currentTimeMillis() + maxTime * 1000;

        while (computeStopCriterion()) {
            currentGeneration++;

            Solution bestSolutionOfGeneration = new Solution(new HashMap<>());
            bestSolutionOfGeneration.setNumberOfLinksWithExceededCapacity(Integer.MAX_VALUE);

            for (int i = 0; i < population.size(); i++) {

                List<Integer> maxValues = new ArrayList<>();
                for (int j = 0; j < population.get(i).getCapacitiesOfLinks().size(); j++) {
                    maxValues.add(Math.max(0, population.get(i).getCapacitiesOfLinks().get(j) - network.getLinks().get(j).getNumberOfModules()));

                }
                population.get(i).setNumberOfLinksWithExceededCapacity(maxValues.stream().filter(p -> p > 0).collect(Collectors.toList()).size());


                //zapisujemy najlepsze rozwiazanie w generacji
                if (population.get(i).getNumberOfLinksWithExceededCapacity() < bestSolutionOfGeneration.getNumberOfLinksWithExceededCapacity())
                    bestSolutionOfGeneration = population.get(i);


            }

            // zapisujemy najlepsze rozwiazanie w historii
            if (bestSolutionOfGeneration.getNumberOfLinksWithExceededCapacity() < bestSolution.getNumberOfLinksWithExceededCapacity()) {
                bestSolution = bestSolutionOfGeneration;
                currentNumberOfContinuousNonBetterSolutions = 0;
            } else
                currentNumberOfContinuousNonBetterSolutions++;


            population = takeBestDAP(population, percentOfBestChromosomes);
            population = crossover(population, seed, pCross);
            population = mutation(population, seed, pMutate);
            population = fillLinkCapacitiesForNewSolutions(population);
            // nie możemy w tym momencie wybrac najlepszych bo nie są obliczone koszta (dlatego przed mutacja)

            System.out.println("Overload of generation " + currentGeneration + ": " + bestSolutionOfGeneration.getNumberOfLinksWithExceededCapacity());

        }

        System.out.println("Overload of best solution: " + bestSolution.getNumberOfLinksWithExceededCapacity());


        return bestSolution;
    }

    private List<Solution> takeBestDAP(List<Solution> solutions, float percentOfBestChromosomes) {
        // Wybieramy x procent najlepszych
        int subListEnd = Math.round(solutions.size() * (percentOfBestChromosomes / 100));
        List<Solution> list0 = solutions.stream()
                .sorted(Comparator.comparing(Solution::getNumberOfLinksWithExceededCapacity))
                .collect(Collectors.toList());

        List<Solution> list = solutions.stream()
                .sorted(Comparator.comparing(Solution::getNumberOfLinksWithExceededCapacity))
                .collect(Collectors.toList())
                .subList(0, Math.round(solutions.size() * (percentOfBestChromosomes / 100)));

        // Dopełniamy najlepszymi, aby populacja nie zmalała
        list.addAll(list0.subList(0, solutions.size() - subListEnd));

        return list;
    }
}











