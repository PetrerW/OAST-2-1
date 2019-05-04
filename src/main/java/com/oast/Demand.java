package com.oast;

import java.util.ArrayList;
import java.util.Arrays;

public class Demand {
    //TODO
    private Node start;
    private Node end;
    private int volume;
    private ArrayList<DemandPath> demandPaths;
    private int ID;
    private int numberOfPaths;

    public Demand(Node start, Node end, int volume, ArrayList<DemandPath> demandPaths, int ID) {
        this.start = start;
        this.end = end;
        this.volume = volume;
        this.demandPaths = demandPaths;
        this.numberOfPaths = demandPaths.size();
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
        this.numberOfPaths = demandPaths.size();
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

    public int getNumberOfPaths() {
        return numberOfPaths;
    }

    public void setNumberOfPaths(int numberOfPaths) {
        this.numberOfPaths = numberOfPaths;
    }

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Demand)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Demand d = (Demand) o;

        if(d.volume == this.volume && Arrays.equals(this.demandPaths.toArray(), d.demandPaths.toArray()) &&
                this.end.equals(d.end) && this.start.equals(d.start) && this.ID == d.ID)
            return true;
        else
            return false;
    }
}
