package com.oast;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {


    public Main() {
    }

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj liczność populacji startowej (liczba chromosomów):");
        int population = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Podaj prawdopodobieństwo wystąpienia krzyżowania <0,1>:");
        scanner.useLocale(Locale.US);//wprowadzanie liczb z kropką jako separatorem
        double pCross = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Podaj prawdopodobieństwo wystąpienia mutacji <0,1>:");
        scanner.useLocale(Locale.US);//wprowadzanie liczb z kropką jako separatorem
        double pMutate = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("******Określenie kryteriów stopu algorytmu EA *******");
        System.out.println("Podaj maksymalny czas pracy algorytmu w [s]:");
        int maxTime = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Podaj maksymalna liczbe generacji:");
        int maxNumberOfGenerations = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Podaj maksymalna liczbe mutacji:");
        int maxNumberOfMutations = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Podaj maksymalna liczbe prób poprawy najlepszego rozwiązania:");
        int maxNumberOfContinuousNonBetterSolutions = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Wskaż ziarno dla generatora liczb losowych:");
        long seed = scanner.nextInt();
        scanner.nextLine();

        System.out.println("****** Wybór pliku topologii sieci i zapotrzebowań *******");
        System.out.println("1: net12_1.txt");
        System.out.println("2: net12_2.txt ");
        System.out.println("3: net4.txt");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                String path1 = "./net/net12_1.txt";
                TopoParser topoParser1 = new TopoParser(path1);
                break;
            case 2:
                String path2 = "./net/net12_2.txt";
                TopoParser topoParser2 = new TopoParser(path2);
                break;
            case 3:
                String path3 = "./net/net4.txt";
                TopoParser topoParser3 = new TopoParser(path3);
                break;
            default:
                System.out.println("Wprowadzono niepoprawna wartosc. Sprobuj ponownie!");
                break;
        }

        EvolutionaryAlgorithm evolutionaryAlgorithm = new EvolutionaryAlgorithm(population,pCross,pMutate,maxTime,maxNumberOfGenerations,maxNumberOfMutations,maxNumberOfContinuousNonBetterSolutions,seed);



        String path = "./net/net4.txt";
        TopoParser topoParser = new TopoParser(path);

        BruteForceAlgorithm bruteForceAlgorithm = new BruteForceAlgorithm(topoParser.readNetwork());
        List<Solution> allSolutions = bruteForceAlgorithm.getAllSolutions();

        Solution solutionDAP = bruteForceAlgorithm.computeDAP(allSolutions);
        Solution solutionDDAP = bruteForceAlgorithm.computeDDAP(allSolutions);

        new SolutionWriter().writeSolutionToFile(path + ("_solution_bruteforce_dap"), solutionDAP, bruteForceAlgorithm.getNetwork());
        new SolutionWriter().writeSolutionToFile(path + ("_solution_bruteforce_ddap"), solutionDDAP, bruteForceAlgorithm.getNetwork());

    }
}
