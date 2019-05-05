package com.oast;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {


    public Main() {
    }

    public static void main(String[] args){

        //parametry określane przez użytkownika
        int population;
        double pCross;
        double pMutate;
        int maxTime;
        int maxNumberOfGenerations;
        int maxNumberOfMutations;
        int maxNumberOfContinuousNonBetterSolutions;
        long seed;
        Scanner scanner = new Scanner(System.in);

        do{
            System.out.println("Podaj liczność populacji startowej (liczba chromosomów):");
            while (!scanner.hasNextInt()){
                System.out.println("Wprowadzono niepoprawna wartosc. Podaj liczbe i sprobuj ponownie!");
                scanner.next();
            }
            population = scanner.nextInt();
        } while (population <= 0 );

        do{
            System.out.println("Podaj prawdopodobieństwo wystąpienia krzyżowania <0,1>:");
            while (!scanner.hasNextDouble()){
                System.out.println("Wprowadzono niepoprawna wartosc. Sprobuj ponownie!");
                scanner.next();
            }
            pCross = scanner.nextDouble();
        } while (pCross <= 0 || pCross >= 1);

        do{
            System.out.println("Podaj prawdopodobieństwo wystąpienia mutacji <0,1>:");
            while (!scanner.hasNextDouble()){
                System.out.println("Wprowadzono niepoprawna wartosc. Sprobuj ponownie!");
                scanner.next();
            }
            pMutate = scanner.nextDouble();
        } while (pMutate <= 0 || pMutate >= 1);

        System.out.println("******Określenie kryteriów stopu algorytmu EA *******");

        do{
            System.out.println("Podaj maksymalny czas pracy algorytmu w [s]:");
            while (!scanner.hasNextInt()){
                System.out.println("Wprowadzono niepoprawna wartosc. Podaj liczbe i sprobuj ponownie!");
                scanner.next();
            }
            maxTime = scanner.nextInt();
        } while (maxTime <= 0 );

        do{
            System.out.println("Podaj maksymalna liczbe generacji:");
            while (!scanner.hasNextInt()){
                System.out.println("Wprowadzono niepoprawna wartosc. Podaj liczbe i sprobuj ponownie!");
                scanner.next();
            }
            maxNumberOfGenerations = scanner.nextInt();
        } while (maxNumberOfGenerations <= 0 );

        do{
            System.out.println("Podaj maksymalna liczbe mutacji:");
            while (!scanner.hasNextInt()){
                System.out.println("Wprowadzono niepoprawna wartosc. Podaj liczbe i sprobuj ponownie!");
                scanner.next();
            }
            maxNumberOfMutations = scanner.nextInt();
        } while (maxNumberOfMutations <= 0 );

        do{
            System.out.println("Podaj maksymalna liczbe prób poprawy najlepszego rozwiązania:");
            while (!scanner.hasNextInt()){
                System.out.println("Wprowadzono niepoprawna wartosc. Podaj liczbe i sprobuj ponownie!");
                scanner.next();
            }
            maxNumberOfContinuousNonBetterSolutions = scanner.nextInt();
        } while (maxNumberOfContinuousNonBetterSolutions <= 0 );

        do{
            System.out.println("Wskaż ziarno dla generatora liczb losowych:");
            while (!scanner.hasNextLong()){
                System.out.println("Wprowadzono niepoprawna wartosc. Podaj liczbe i sprobuj ponownie!");
                scanner.next();
            }
            seed = scanner.nextInt();
        } while (seed <= 0 );

        System.out.println("****** Wybór pliku topologii sieci i zapotrzebowań *******");
        System.out.println("1: net12_1.txt");
        System.out.println("2: net12_2.txt ");
        System.out.println("3: net4.txt");

        String path = null;
        TopoParser topoParser = null;
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
            default:
                System.out.println("Wprowadzono niepoprawna wartosc. Sprobuj ponownie!");
                break;
        }

        System.out.print("Obliczanie. Prosze czekac...");

        EvolutionaryAlgorithm evolutionaryAlgorithm = new EvolutionaryAlgorithm(population,pCross,pMutate,maxTime,maxNumberOfGenerations,maxNumberOfMutations,maxNumberOfContinuousNonBetterSolutions,seed);

       // String path = "./net/net4.txt";
       // TopoParser topoParser = new TopoParser(path);

        BruteForceAlgorithm bruteForceAlgorithm = new BruteForceAlgorithm(topoParser.readNetwork());
        List<Solution> allSolutions = bruteForceAlgorithm.getAllSolutions();

        Solution solutionDAP = bruteForceAlgorithm.computeDAP(allSolutions);
        Solution solutionDDAP = bruteForceAlgorithm.computeDDAP(allSolutions);

        new SolutionWriter().writeSolutionToFile(path + ("_solution_bruteforce_dap"), solutionDAP, bruteForceAlgorithm.getNetwork());
        new SolutionWriter().writeSolutionToFile(path + ("_solution_bruteforce_ddap"), solutionDDAP, bruteForceAlgorithm.getNetwork());

    }
}
