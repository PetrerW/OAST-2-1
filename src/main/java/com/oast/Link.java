package com.oast;

/**
 * Directional Link class
 */
public class Link {
    //TODO
    private Node start;
    private Node end;

    public Link(Node first, Node second) {
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
}
