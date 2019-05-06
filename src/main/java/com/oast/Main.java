package com.oast;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Function;

public class Main {


    public Main() {
    }

    public static void main(String[] args){

        //parametry określane przez użytkownika
        int population;
        float pCross;
        float pMutate;
        int maxTime;
        int maxNumberOfGenerations;
        int maxNumberOfMutations;
        int maxNumberOfContinuousNonBetterSolutions;
        long seed;
        String BF;
        int criterionChoice;
        Scanner scanner = new Scanner(System.in);
        String choiceDAPorDDAP;
        String path = null;
        TopoParser topoParser = null;

        do{
            System.out.println("Type in start population size (chromosomes count):");
            while (!scanner.hasNextInt()){
                System.out.println("You have typed in a wrong value. Please pass a number and try again.");
                scanner.next();
            }
            population = scanner.nextInt();
        } while (population <= 0 );

        do{
            System.out.println("Type in crossover probability <0,1>:");
            while (!scanner.hasNext()){
                System.out.println("You have typed in a wrong value. Please pass a number and try again.");
                scanner.next();
            }
            pCross = scanner.nextFloat();
        } while (pCross < 0 || pCross > 1);

        do{
            System.out.println("Type in mutation probability <0,1>:");
            while (!scanner.hasNext()){
                System.out.println("You have typed in a wrong value. Please pass a number and try again.");
                scanner.next();
            }
            pMutate = scanner.nextFloat();
        } while (pMutate < 0 || pMutate > 1);

        System.out.println("***********EA stop algorithm criterions**************");

        do{
            System.out.println("Type in maximal work time of the algorithm [s]:");
            while (!scanner.hasNextInt()){
                System.out.println("You have typed in a wrong value. Please pass a number and try again.");
                scanner.next();
            }
            maxTime = scanner.nextInt();
        } while (maxTime <= 0 );

        do{
            System.out.println("Type in maximal number of generations:");
            while (!scanner.hasNextInt()){
                System.out.println("You have typed in a wrong value. Please pass a number and try again.");
                scanner.next();
            }
            maxNumberOfGenerations = scanner.nextInt();
        } while (maxNumberOfGenerations <= 0 );

        do{
            System.out.println("Type in maximal number of mutations:");
            while (!scanner.hasNextInt()){
                System.out.println("You have typed in a wrong value. Please pass a number and try again.");
                scanner.next();
            }
            maxNumberOfMutations = scanner.nextInt();
        } while (maxNumberOfMutations <= 0 );

        do{
            System.out.println("Type in maximal number of the best solution improvement trials:");
            while (!scanner.hasNextInt()){
                System.out.println("You have typed in a wrong value. Please pass a number and try again.");
                scanner.next();
            }
            maxNumberOfContinuousNonBetterSolutions = scanner.nextInt();
        } while (maxNumberOfContinuousNonBetterSolutions <= 0 );

        do{
            System.out.println("Type in random number generator seed:");
            while (!scanner.hasNextLong()){
                System.out.println("You have typed in a wrong value. Please pass a number and try again.");
                scanner.next();
            }
            seed = scanner.nextLong();
        } while (seed <= 0 );

        int choiceMenu = 0;

        do {
            System.out.println("****** Choice of the file with the network *******");
            System.out.println("1: net12_1.txt");
            System.out.println("2: net12_2.txt ");
            System.out.println("3: net4.txt");
            System.out.println("4: Exit");

            choiceMenu = scanner.nextInt();
            switch (choiceMenu) {
                case 1:
                    path = "./net/net12_1.txt";
                    topoParser = new TopoParser(path);
                    break;
                case 2:
                    path = "./net/net12_2.txt";
                    topoParser = new TopoParser(path);
                    break;
                case 3:
                    path = "./net/net4.txt";
                    topoParser = new TopoParser(path);
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("You have typed in a wrong value. Please pass a number and try again.");
                    break;
            }
            System.out.println("****** Choice of the algorithm *******");
            System.out.println("1: EvolutionaryAlgorithm");
            System.out.println("2: BruteForce ");

            choiceMenu = scanner.nextInt();
            switch (choiceMenu) {
                case 1:
                    System.out.println("Do you want to try solving the problem DAP or DDAP?");

                    do {
                        while (!scanner.hasNextLine()) {
                            System.out.println("You have typed in a wrong value. Please pass \"DAP\" or \"DDAP\".");
                            scanner.next();
                        }
                        choiceDAPorDDAP = scanner.nextLine();
                    } while (!choiceDAPorDDAP.equals("DAP") && !choiceDAPorDDAP.equals("DDAP"));

                    System.out.println("Computing. Please wait...");

                    if (choiceDAPorDDAP.equals("DAP")) {
                        //DAP
                        EvolutionaryAlgorithm evolutionaryAlgorithm2 = new EvolutionaryAlgorithmBuilder()
                                .setMaxMutationNumber(maxNumberOfMutations)
                                .setMaxNumberOfContinuousNonBetterSolutions(maxNumberOfContinuousNonBetterSolutions)
                                .setMaxTime(maxTime)
                                .setNetwork(topoParser.readNetwork())
                                .setNumberOfChromosomes(population)
                                .setNumberOfGenerations(maxNumberOfGenerations)
                                .setpCross(pCross)
                                .setpMutate(pMutate)
                                .setSeed(seed)
                                .setPercentOfBestChromosomes(70)                //TODO z palca
                                .createEvolutionaryAlgorithm();

                        Solution solutionDAP = evolutionaryAlgorithm2.computeDAP();

                        new SolutionWriter().writeSolutionToFile(path + ("_solution_evo_dap"), solutionDAP, evolutionaryAlgorithm2.getNetwork());
                    } else {
                        //DDAP
                        EvolutionaryAlgorithm evolutionaryAlgorithm = new EvolutionaryAlgorithmBuilder()
                                .setMaxMutationNumber(maxNumberOfMutations)
                                .setMaxNumberOfContinuousNonBetterSolutions(maxNumberOfContinuousNonBetterSolutions)
                                .setMaxTime(maxTime)
                                .setNetwork(topoParser.readNetwork())
                                .setNumberOfChromosomes(population)
                                .setNumberOfGenerations(maxNumberOfGenerations)
                                .setpCross(pCross)
                                .setpMutate(pMutate)
                                .setSeed(seed)
                                .setPercentOfBestChromosomes(70)                //TODO z palca
                                .createEvolutionaryAlgorithm();

                        Solution solutionDDAPevo = evolutionaryAlgorithm.computeDDAP();
                        new SolutionWriter().writeSolutionToFile(path + ("_solution_evo_ddap"), solutionDDAPevo, evolutionaryAlgorithm.getNetwork());
                    }

                    break;
                case 2:

                        System.out.println("Do you want to try solving the problem DAP or DDAP?");

                        do {
                            while (!scanner.hasNextLine()) {
                                System.out.println("You have typed in a wrong value. Please pass \"DAP\" or \"DDAP\".");
                                scanner.next();
                            }
                            choiceDAPorDDAP = scanner.nextLine();
                        } while (!choiceDAPorDDAP.equals("DAP") && !choiceDAPorDDAP.equals("DDAP"));

                        System.out.println("Computing. Please wait...");

                        if (choiceDAPorDDAP.equals("DAP")) {
                            //DAP
                            try {
                                BruteForceAlgorithm bruteForceAlgorithm = new BruteForceAlgorithm(topoParser.readNetwork());
                                List<Solution> allSolutions = bruteForceAlgorithm.getAllSolutions();

                                Solution solutionDAPbf = bruteForceAlgorithm.computeDAP(allSolutions);
                                new SolutionWriter().writeSolutionToFile(path + ("_solution_bruteforce_dap"), solutionDAPbf, bruteForceAlgorithm.getNetwork());
                            }
                            catch (Exception e) {
                                System.out.println("Exception caught: " + e.getMessage());
                                System.out.println("Make sure the network is not too big for the BF algorithm");
                            }
                        } else {
                            //DDAP
                            try {

                                BruteForceAlgorithm bruteForceAlgorithm = new BruteForceAlgorithm(topoParser.readNetwork());
                                List<Solution> allSolutions = bruteForceAlgorithm.getAllSolutions();

                                Solution solutionDDAPbf = bruteForceAlgorithm.computeDDAP(allSolutions);
                                new SolutionWriter().writeSolutionToFile(path + ("_solution_bruteforce_ddap"), solutionDDAPbf, bruteForceAlgorithm.getNetwork());
                            }
                            catch (Exception e) {
                                System.out.println("Exception caught: " + e.getMessage());
                                System.out.println("Make sure the network is not too big for the BF algorithm");
                            }
                        }

                    break;

                default:
                    System.out.println("You have typed in a wrong value. Please pass a number and try again.");
                    break;
            }

                System.out.println("Do you want to AGAIN or EXIT? (A/E)");
                do{
                    while (!scanner.hasNextLine()){
                        System.out.println("You have typed in a wrong value. Please pass \"A\" or \"E\".");
                        scanner.next();
                    }
                    BF = scanner.nextLine();
                } while (!BF.equals("A") && !BF.equals("E"));

                if (BF.equals("A")) {
                    choiceMenu = 0;
                }else{
                    choiceMenu = 4;
                }

        } while(choiceMenu != 4);

/*
        System.out.println("****** Choice of the file with the network *******");
        System.out.println("1: net12_1.txt");
        System.out.println("2: net12_2.txt ");
        System.out.println("3: net4.txt");
        System.out.println("4: Exit");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                path = "./net/net12_1.txt";
                topoParser = new TopoParser(path);
                break;
            case 2:
                path = "./net/net12_2.txt";
                topoParser = new TopoParser(path);
                break;
            case 3:
                path = "./net/net4.txt";
                topoParser = new TopoParser(path);
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("You have typed in a wrong value. Please pass a number and try again.");
                break;
        }

        System.out.println("****** Choice of the algorithm *******");
        System.out.println("1: EvolutionaryAlgorithm");
        System.out.println("2: BruteForce ");
        System.out.println("3: Return ");

        int choice2 = scanner.nextInt();
        switch (choice2) {
            case 1:
                System.out.println("Do you want to try solving the problem DAP or DDAP?");

                do{
                    while (!scanner.hasNextLine()){
                        System.out.println("You have typed in a wrong value. Please pass \"DAP\" or \"DDAP\".");
                        scanner.next();
                    }
                    choiceDAPorDDAP = scanner.nextLine();
                } while (!choiceDAPorDDAP.equals("DAP") && !choiceDAPorDDAP.equals("DDAP"));

                System.out.println("Computing. Please wait...");

                if (choiceDAPorDDAP.equals("DAP")) {
                        //DAP
                        EvolutionaryAlgorithm evolutionaryAlgorithm2 = new EvolutionaryAlgorithmBuilder()
                                .setMaxMutationNumber(maxNumberOfMutations)
                                .setMaxNumberOfContinuousNonBetterSolutions(maxNumberOfContinuousNonBetterSolutions)
                                .setMaxTime(maxTime)
                                .setNetwork(topoParser.readNetwork())
                                .setNumberOfChromosomes(population)
                                .setNumberOfGenerations(maxNumberOfGenerations)
                                .setpCross(pCross)
                                .setpMutate(pMutate)
                                .setSeed(seed)
                                .setPercentOfBestChromosomes(70)                //TODO z palca
                                .createEvolutionaryAlgorithm();

                        Solution solutionDAP = evolutionaryAlgorithm2.computeDAP();

                        new SolutionWriter().writeSolutionToFile(path + ("_solution_evo_dap"), solutionDAP, evolutionaryAlgorithm2.getNetwork());
                }
                else{
                    //DDAP
                    EvolutionaryAlgorithm evolutionaryAlgorithm = new EvolutionaryAlgorithmBuilder()
                            .setMaxMutationNumber(maxNumberOfMutations)
                            .setMaxNumberOfContinuousNonBetterSolutions(maxNumberOfContinuousNonBetterSolutions)
                            .setMaxTime(maxTime)
                            .setNetwork(topoParser.readNetwork())
                            .setNumberOfChromosomes(population)
                            .setNumberOfGenerations(maxNumberOfGenerations)
                            .setpCross(pCross)
                            .setpMutate(pMutate)
                            .setSeed(seed)
                            .setPercentOfBestChromosomes(70)                //TODO z palca
                            .createEvolutionaryAlgorithm();

                    Solution solutionDDAPevo = evolutionaryAlgorithm.computeDDAP();
                    new SolutionWriter().writeSolutionToFile(path + ("_solution_evo_ddap"), solutionDDAPevo, evolutionaryAlgorithm.getNetwork());
                }

                break;
            case 2:

                try {
                    //bf
                    BruteForceAlgorithm bruteForceAlgorithm = new BruteForceAlgorithm(topoParser.readNetwork());
                    List<Solution> allSolutions = bruteForceAlgorithm.getAllSolutions();

                    Solution solutionDAPbf = bruteForceAlgorithm.computeDAP(allSolutions);
                    Solution solutionDDAPbf = bruteForceAlgorithm.computeDDAP(allSolutions);

                    new SolutionWriter().writeSolutionToFile(path + ("_solution_bruteforce_dap"), solutionDAPbf, bruteForceAlgorithm.getNetwork());
                    new SolutionWriter().writeSolutionToFile(path + ("_solution_bruteforce_ddap"), solutionDDAPbf, bruteForceAlgorithm.getNetwork());
                } catch (Exception e) {
                    System.out.println("Exception caught: " + e.getMessage());
                    System.out.println("Make sure the network is not too big for the BF algorithm");
                }
                break;
            case 3:
                break;

            default:
                System.out.println("You have typed in a wrong value. Please pass a number and try again.");
                break;
        }
*/
/*
        // evo
        EvolutionaryAlgorithm evolutionaryAlgorithm = new EvolutionaryAlgorithmBuilder()
                .setMaxMutationNumber(maxNumberOfMutations)
                .setMaxNumberOfContinuousNonBetterSolutions(maxNumberOfContinuousNonBetterSolutions)
                .setMaxTime(maxTime)
                .setNetwork(topoParser.readNetwork())
                .setNumberOfChromosomes(population)
                .setNumberOfGenerations(maxNumberOfGenerations)
                .setpCross(pCross)
                .setpMutate(pMutate)
                .setSeed(seed)
                .setPercentOfBestChromosomes(70)                //TODO z palca
                .createEvolutionaryAlgorithm();

        Solution solutionDDAPevo = evolutionaryAlgorithm.computeDDAP();
        new SolutionWriter().writeSolutionToFile(path + ("_solution_evo_ddap"), solutionDDAPevo, evolutionaryAlgorithm.getNetwork());

        System.out.println("Do you want to try solving the problem with bruteforce method? (yes/no)");
        do{
            while (!scanner.hasNextLine()){
                System.out.println("You have typed in a wrong value. Please pass \"yes\" or \"no\".");
                scanner.next();
            }
            BF = scanner.nextLine();
        } while (!BF.equals("yes") && !BF.equals("no"));

        if (BF.equals("yes")) {
            try {
                //bf
                BruteForceAlgorithm bruteForceAlgorithm = new BruteForceAlgorithm(topoParser.readNetwork());
                List<Solution> allSolutions = bruteForceAlgorithm.getAllSolutions();

                Solution solutionDAPbf = bruteForceAlgorithm.computeDAP(allSolutions);
                Solution solutionDDAPbf = bruteForceAlgorithm.computeDDAP(allSolutions);

                new SolutionWriter().writeSolutionToFile(path + ("_solution_bruteforce_dap"), solutionDAPbf, bruteForceAlgorithm.getNetwork());
                new SolutionWriter().writeSolutionToFile(path + ("_solution_bruteforce_ddap"), solutionDDAPbf, bruteForceAlgorithm.getNetwork());
            } catch (Exception e) {
                System.out.println("Exception caught: " + e.getMessage());
                System.out.println("Make sure the network is not too big for the BF algorithm");
            }
        }
*/
/*
        // evo
        EvolutionaryAlgorithm evolutionaryAlgorithm2 = new EvolutionaryAlgorithmBuilder()
                .setMaxMutationNumber(maxNumberOfMutations)
                .setMaxNumberOfContinuousNonBetterSolutions(maxNumberOfContinuousNonBetterSolutions)
                .setMaxTime(maxTime)
                .setNetwork(topoParser.readNetwork())
                .setNumberOfChromosomes(population)
                .setNumberOfGenerations(maxNumberOfGenerations)
                .setpCross(pCross)
                .setpMutate(pMutate)
                .setSeed(seed)
                .setPercentOfBestChromosomes(70)                //TODO z palca
                .createEvolutionaryAlgorithm();


        Solution solutionDAP = evolutionaryAlgorithm2.computeDAP();

        new SolutionWriter().writeSolutionToFile(path + ("_solution_evo_dap"), solutionDAP, evolutionaryAlgorithm2.getNetwork());
*/
    }

}
