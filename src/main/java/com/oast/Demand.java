package com.oast;

public class Demand {
    //TODO
    private Node start;
    private Node end;
    private int volume;

    public Demand(Node first, Node second) {
        this.start = first;
        this.end = second;
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
