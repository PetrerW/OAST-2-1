package com.oast;

import java.util.ArrayList;

public class Demand {
    //TODO
    private Node start;
    private Node end;
    private int volume;
    private ArrayList<DemandPath> demandPaths;
    private int ID;

    public Demand(Node start, Node end, int volume, ArrayList<DemandPath> demandPaths, int ID) {
        this.start = start;
        this.end = end;
        this.volume = volume;
        this.demandPaths = demandPaths;
        this.ID = ID;
    }

    /**
     *
     * @param params Parameters from the line: start node, end node, volume.
     * @param demandPaths ArrayList of DemandPath objects
     * @param ID
     */
    public Demand(String[] params, ArrayList<DemandPath> demandPaths, int ID){
        this.start = new Node(Integer.parseInt(params[0]));
        this.end = new Node(Integer.parseInt(params[1]));
        this.volume = Integer.parseInt(params[2]);
        this.demandPaths = demandPaths;
        this.ID = ID;
    }

    public int getID() { return ID; }

    public void setID(int ID) { this.ID = ID; }

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
