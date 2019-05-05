package com.oast;

import java.util.List;

public class Main {


    public Main() {
    }

    public static void main(String[] args){

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
