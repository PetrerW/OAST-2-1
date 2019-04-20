package com.oast;

import java.util.ArrayList;

public class DemandPath {
    private int id;

    /**
     * Contains only links' IDs (of the links that belong to the
     */
    private ArrayList<Integer> links;

    public void setLinks(ArrayList<Integer> links) {
        this.links = links;
    }

    public ArrayList<Integer> getLinks() {
        return links;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
