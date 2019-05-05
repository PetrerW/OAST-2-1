package com.oast;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

public class BruteForceAlgorithm {

    protected Network network;

    public BruteForceAlgorithm(Network network) {
        this.network = network;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public Solution computeDDAP(List<Solution> solutions) {
        Double finalCost = Double.MAX_VALUE;
        Double cost = 0.0;
        int bestSolution = 0;

        for (int i = 0; i < solutions.size(); i++) {
            List<Integer> costsOfLinks = solutions.get(i).getCapacitiesOfLinks();
            for (int j = 0; j < costsOfLinks.size(); j++) {
                cost += network.getLinks().get(j).getModule().getCost() * costsOfLinks.get(j);
            }
            if (cost < finalCost) {
                finalCost = cost;
                bestSolution = i;
            }
            cost = 0.0;
        }
        System.out.println("DDAP minimum cost: " + finalCost + "\n");

        return solutions.get(bestSolution);
    }

    public Solution computeDAP(List<Solution> solutions) {
        for (Solution solution : solutions) {
            List<Integer> maxValues = new ArrayList();
            for (int j = 0; j < solution.getCapacitiesOfLinks().size(); j++) {
                maxValues.add(Math.max(0, solution.getCapacitiesOfLinks().get(j) - network.getLinks().get(j).getNumberOfModules()));
            }
            solution.setNumberOfLinksWithExceededCapacity(maxValues.stream().filter(p -> p > 0).collect(Collectors.toList()).size());
            if (Collections.max(maxValues) == 0) {
                System.out.println("DAP Solution found");
                return solution;
            }
        }
        return null;
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

    private List<Solution> getSolutions() {
        List<List<Solution>> allCombinations = network.getDemands().stream()
                .map(this::getCombinationsOfOneDemand)
                .collect(Collectors.toList());

        List<List<Integer>> combinationOfIndexes = Lists.cartesianProduct(
                allCombinations.stream()
                .map(combination -> fillListWithSuccessiveIndexes(combination.size()))
                .collect(Collectors.toList()));

        List<Solution> list = combinationOfIndexes.stream()
                .map(indexes -> getSolution(allCombinations, indexes))
                .collect(Collectors.toList());

        return list;
    }

    private Solution getSolution(List<List<Solution>> combinations, List<Integer> indexes) {
        Solution solution = new Solution(new HashMap());
        for (int i = 0; i < combinations.size(); i++) {
            Integer index = indexes.get(i);
            List<Solution> list = combinations.get(i);
            Solution solution1 = list.get(index);
            Map<Point,Integer> map = solution1.getMapOfValues();
            solution.getMapOfValues().putAll(map);
        }
        return solution;
    }

    public List<Solution> getAllSolutions() {

        List<Solution> solutions = getSolutions();
        List<List<Integer>> linksCapacities = solutions.stream()
                .map(this::computeLinksCapacitiesOfSolution)
                .collect(Collectors.toList());

        for (int i = 0; i < solutions.size(); i++) {
            solutions.get(i).setCapacitiesOfLinks(linksCapacities.get(i));
        }
        return solutions;
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
                List<Integer> list  = Arrays.stream(path.getLinks()).boxed().collect( Collectors.toList() );
                if (list.contains(j + 1)) {
                    sum += solution.getMapOfValues()
                            .get(new Point(path.getDemandId(), path.getId()));
                }
            }
            linksCapacities.set(j, (int) Math.ceil(sum / (double) network.getLinks().get(j).getLinkModule()));
        }
        return linksCapacities;
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

    private int calculateNewtonSymbol(int n, int k) {
        Integer result = 1;
        for (int i = 1; i <= k; i++)
            result = result * (n - i + 1) / i;
        return result;
    }

    private List<Integer> fillListWithSuccessiveIndexes(int size) {
        List<Integer> oneCombination = new ArrayList();
        for (int j = 0; j < size; j++) {
            oneCombination.add(j);
        }
        return oneCombination;
    }
}
