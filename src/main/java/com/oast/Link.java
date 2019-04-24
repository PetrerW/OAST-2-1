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

    public Link(Node start, Node end, int numberOfModules, Module module, int linkModule, int id) {
        this.start = start;
        this.end = end;
        this.numberOfModules = numberOfModules;
        this.module = module;
        this.linkModule = linkModule;
        this.id = id;
    }

    /**
     *
     * @param params Line in order: <start node ID> <end node ID> <number of modules> <module cost> <link module>
     * @param id ID of the link
     */
    public Link(String[] params, int id){
        this.start = new Node(Integer.getInteger(params[0]));
        this.end = new Node(Integer.getInteger(params[1]));
        this.numberOfModules = Integer.getInteger(params[2]);
        this.module = new Module(Integer.getInteger(params[3]));
        this.linkModule = Integer.getInteger(params[4]);
        this.id = id;
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
