package com.oast;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SolutionWriter {

    private Network network;

    public void writeSolutionToFile(String fileName, Solution solution, Network network) {
        this.network = network;
        String text = "";
        if (solution == null) {
            text = "No solution";
        } else {
            text = printSolution(solution);
        }
        try (PrintWriter writer = new PrintWriter(fileName + ".txt", "UTF-8")) {
            writer.println(text);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String printSolution(Solution solution) {
        List<Integer> cost = new ArrayList<>();
        for (int i = 0; i < network.getLinks().size(); i++) {
            cost.add(0);
        }

        List<Integer> signals = new ArrayList<>();
        for (int i = 0; i < network.getLinks().size(); i++) {
            signals.add(0);
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
                    sum += solution.getMapOfValues().get(new Point(path.getDemandId(), path.getId()));
                }
            }
            signals.set(j, (int) sum);
            cost.set(j, (int) Math.ceil(sum / (double) network.getLinks().get(j).getLinkModule()));
        }

        String text = signals.size() + "\n\n";
        for (int i = 0; i < signals.size(); i++) {
            text += i + 1 + " " + signals.get(i) + " " + cost.get(i) + "\n";
        }
        text += "\n" + network.getDemands().size() + "\n\n";

        for (int i = 0; i < network.getDemands().size(); i++) {
            int demandId = network.getDemands().get(i).getID();

            int counter = 0;
            for (Map.Entry<Point, Integer> entry : solution.getMapOfValues().entrySet()) {
                if (entry.getKey().getDemandId().equals(demandId) && entry.getValue() != 0)
                    counter++;
            }

            String pathsText = "";
            for (Map.Entry<Point, Integer> entry : solution.getMapOfValues().entrySet()) {
                if (entry.getKey().getDemandId().equals(demandId) && entry.getValue() != 0)
                    pathsText += entry.getKey().getPathId() + " " + entry.getValue() + "\n";
            }


            text += demandId + " " + counter + "\n";
            text += pathsText + "\n";
        }

        return text;
    }

}
