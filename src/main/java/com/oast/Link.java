package com.oast;

/**
 * Directional Link class
 */
public class Link {
    private Node start;
    private Node end;
    private int numberOfModules;

    private Module module;
    private int linkModule;
    private int id;


    public Link(Node start, Node end) {
        this.start = start;
        this.end = end;
        this.numberOfModules = 1;
        this.module = new Module(1);
        this.linkModule = 1;
    }

    public void setNumberOfModules(int numberOfModules) {
        this.numberOfModules = numberOfModules;
    }

    public void setLinkModule(int linkModule) {
        this.linkModule = linkModule;
    }

    public int getNumberOfModules() {
        return numberOfModules;
    }

    public int getLinkModule() {
        return linkModule;
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

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Module getModule() { return module; }

    public void setModule(Module module) { this.module = module; }
}
