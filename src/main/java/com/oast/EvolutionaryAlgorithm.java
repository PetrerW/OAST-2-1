package com.oast;

import sun.rmi.server.InactiveGroupException;

import java.util.*;

public class EvolutionaryAlgorithm {


    // parametry algorytmu
    private int population = 1000;
    private double pCross = 0.25;
    private double pMutate = 0.01;
    private int maxTime = 300;//czas w [s]
    private int numberOfGenerations = 100;
    private int maxMutationNumber = 1000000;
    private int maxNumberOfContinuousNonBetterSolutions = 10;
    private long seed = System.nanoTime();


    private int numberOfContinuousNonBetterSolutions = 10;
    private int tableLength = 1000;
    private double p = 0.5;
    private int maxCrossNumber = 200000;
    private int maxNoChangeGenerations = 25000;
    private int crossNumber = 0;
    private int mutationNumber = 0;
    private int noChangeGenerations = 0;

    private int startTime = new Date().getSeconds();
    private int currentTime = new Date().getSeconds();

    private int numberOfChromosomes = 10000;

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

    public EvolutionaryAlgorithm() {
    }

    public EvolutionaryAlgorithm(int population, double pCross, double pMutate, int maxTime, int numberOfGenerations,
                                 int maxMutationNumber, int maxNumberOfContinuousNonBetterSolutions, long seed) {
        this.population = population;
        this.pCross = pCross;
        this.pMutate = pMutate;
        this.maxTime = maxTime;
        this.numberOfGenerations = numberOfGenerations;
        this.maxMutationNumber = maxMutationNumber;
        this.maxNumberOfContinuousNonBetterSolutions = maxNumberOfContinuousNonBetterSolutions;
        this.seed = seed;
    }

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
            case "numberOfGenerations":
                if(this.numberOfGenerations < this.maxNoChangeGenerations)
                    return false;
                break;
            case "maxMutationNumber":
                if(this.mutationNumber < this.maxMutationNumber)
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
