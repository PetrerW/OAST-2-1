package com.oast;

import sun.rmi.server.InactiveGroupException;

import java.util.*;
import java.util.stream.Collectors;

public class EvolutionaryAlgorithm {

    // parametry algorytmu
    private int population;
    private double pCross;
    private double pMutate;
    private int maxTime;//czas w [s]
    private int maxNumberOfGenerations;
    private int maxNumberOfMutations;
    private int maxNumberOfContinuousNonBetterSolutions;
    private long seed = System.nanoTime();

    //parametry potrzebne do iteracji
    private int startTime = new Date().getSeconds();
    private int currentTime = new Date().getSeconds();
    private int numberOfGenerations = 0;
    private int numberOfMutations = 0;
    private int numberOfContinuousNonBetterSolutions = 0;

    protected Network network;

    public EvolutionaryAlgorithm(Network network) {
        this.network = network;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public EvolutionaryAlgorithm(int population, double pCross, double pMutate, int maxTime, int maxNumberOfGenerations,
                                 int maxNumberOfMutations, int maxNumberOfContinuousNonBetterSolutions, long seed) {
        this.population = population;
        this.pCross = pCross;
        this.pMutate = pMutate;
        this.maxTime = maxTime;
        this.maxNumberOfGenerations = maxNumberOfGenerations;
        this.maxNumberOfMutations = maxNumberOfMutations;
        this.maxNumberOfContinuousNonBetterSolutions = maxNumberOfContinuousNonBetterSolutions;
        this.seed = seed;
    }

    private Map<Point, Integer> mapOfValues = new Map<Point, Integer>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object key) {
            return false;
        }

        @Override
        public boolean containsValue(Object value) {
            return false;
        }

        @Override
        public Integer get(Object key) {
            return null;
        }

        @Override
        public Integer put(Point key, Integer value) {
            return null;
        }

        @Override
        public Integer remove(Object key) {
            return null;
        }

        @Override
        public void putAll(Map<? extends Point, ? extends Integer> m) {

        }

        @Override
        public void clear() {

        }

        @Override
        public Set<Point> keySet() {
            return null;
        }

        @Override
        public Collection<Integer> values() {
            return null;
        }

        @Override
        public Set<Entry<Point, Integer>> entrySet() {
            return null;
        }
    };

    //TODO Make it with numbers not strings
    /**
     *
     * @param choice
     * @return False if the loop should go on
     */
    public boolean computeStopCriterion(String choice){
        switch(choice){
            case "maxTime":
                if(this.currentTime < this.maxTime)
                    return false;
                break;
            case "maxNumberOfGenerations":
                if(this.numberOfGenerations < this.maxNumberOfGenerations)
                    return false;
                break;
            case "maxNumberOfMutations":
                if(this.numberOfMutations < this.maxNumberOfMutations)
                    return false;
                break;
            case "numberOfContinuousNonBetterSolutions":
                if(this.numberOfContinuousNonBetterSolutions < this.maxNumberOfContinuousNonBetterSolutions)
                    return false;
                break;
            default:
                return true;
        }
        return true;
    }

    //PETLA
   /*  while ((currentTime - startTime <= maxTime) && (numberOfGenerations <= maxNumberOfGenerations) && (numberOfMutations <= maxNumberOfMutations) && (numberOfContinuousNonBetterSolutions <= maxNumberOfContinuousNonBetterSolutions))
    {

    }*/


    public ArrayList<Solution> initializePopulation(){

        //TODO: Generate initial Chromosomes
        //Map<Solution, Integer> mapOfValues = new Map<Solution, Integer>() {
        //}
        //return new ArrayList<Solution>() {
        //}
        return null;
    }

    public Solution computeDDAP(String StopCriterionChoice){

        int n = 0;
        //TODO
        while (!computeStopCriterion(StopCriterionChoice))
        {
            //TODO Cross
            //TODO Mutation
            //TODO Choose the best population
            //TODO Save chosen population to file
        }
        return null;
    }

    public Solution computeDAP(){
        return null;
    }


}


