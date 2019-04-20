package com.oast;

import java.util.ArrayList;

public class Demand {
    //TODO
    private Node start;
    private Node end;
    private int volume;
    private ArrayList<DemandPath> demandPaths;

    public Demand(Node start, Node end, int volume, ArrayList<DemandPath> demandPaths) {
        this.start = start;
        this.end = end;
        this.volume = volume;
        this.demandPaths = demandPaths;
    }

    public ArrayList<DemandPath> getDemandPaths() {
        return demandPaths;
    }

    public void setDemandPaths(ArrayList<DemandPath> demandPaths) {
        this.demandPaths = demandPaths;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public void setEnd(Node end) {
        this.end = end;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
