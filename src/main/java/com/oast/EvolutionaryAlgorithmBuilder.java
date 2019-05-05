package com.oast;

public class EvolutionaryAlgorithmBuilder {
    private float pCross;
    private float pMutate;
    private int maxTime;
    private int numberOfChromosomes;
    private float percentOfBestChromosomes;
    private int numberOfGenerations;
    private int maxMutationNumber;
    private int maxNumberOfContinuousNonBetterSolutions;
    private long seed;
    private Network network;

    public EvolutionaryAlgorithmBuilder setpCross(float pCross) {
        this.pCross = pCross;
        return this;
    }

    public EvolutionaryAlgorithmBuilder setpMutate(float pMutate) {
        this.pMutate = pMutate;
        return this;
    }

    public EvolutionaryAlgorithmBuilder setMaxTime(int maxTime) {
        this.maxTime = maxTime;
        return this;
    }

    public EvolutionaryAlgorithmBuilder setNumberOfChromosomes(int numberOfChromosomes) {
        this.numberOfChromosomes = numberOfChromosomes;
        return this;
    }

    public EvolutionaryAlgorithmBuilder setPercentOfBestChromosomes(float percentOfBestChromosomes) {
        this.percentOfBestChromosomes = percentOfBestChromosomes;
        return this;
    }

    public EvolutionaryAlgorithmBuilder setNumberOfGenerations(int numberOfGenerations) {
        this.numberOfGenerations = numberOfGenerations;
        return this;
    }

    public EvolutionaryAlgorithmBuilder setMaxMutationNumber(int maxMutationNumber) {
        this.maxMutationNumber = maxMutationNumber;
        return this;
    }

    public EvolutionaryAlgorithmBuilder setMaxNumberOfContinuousNonBetterSolutions(int maxNumberOfContinuousNonBetterSolutions) {
        this.maxNumberOfContinuousNonBetterSolutions = maxNumberOfContinuousNonBetterSolutions;
        return this;
    }

    public EvolutionaryAlgorithmBuilder setSeed(long seed) {
        this.seed = seed;
        return this;
    }

    public EvolutionaryAlgorithmBuilder setNetwork(Network network) {
        this.network = network;
        return this;
    }

    public EvolutionaryAlgorithm createEvolutionaryAlgorithm() {
        return new EvolutionaryAlgorithm(pCross, pMutate, maxTime, numberOfChromosomes, percentOfBestChromosomes, numberOfGenerations, maxMutationNumber, maxNumberOfContinuousNonBetterSolutions, seed, network);
    }
}