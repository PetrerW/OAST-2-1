package com.oast;

/**
 * Directional Link class
 */
public class Link {
    private Node start;
    private Node end;
    private int numberOfModules;
    private double moduleCost;
    private int module;

    public Link(Node start, Node end, int numberOfModules, double moduleCost, int module) {
        this.start = start;
        this.end = end;
        this.numberOfModules = numberOfModules;
        this.moduleCost = moduleCost;
        this.module = module;
    }

    public Link(Node start, Node end) {
        this.start = start;
        this.end = end;
        this.numberOfModules = 1;
        this.moduleCost = 1;
        this.module = 1;
    }

    public void setNumberOfModules(int numberOfModules) {
        this.numberOfModules = numberOfModules;
    }

    public void setModuleCost(double moduleCost) {
        this.moduleCost = moduleCost;
    }

    public void setModule(int module) {
        this.module = module;
    }

    public int getNumberOfModules() {
        return numberOfModules;
    }

    public double getModuleCost() {
        return moduleCost;
    }

    public int getModule() {
        return module;
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
}
