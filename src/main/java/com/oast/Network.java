package com.oast;

import java.util.ArrayList;

public class Network {
    private ArrayList<Link> links;
    private ArrayList<Demand> demands;

    public Network(ArrayList<Link> links, ArrayList<Demand> demands) {
        this.links = links;
        this.demands = demands;
    }

    public ArrayList<Demand> getDemands() {
        return demands;
    }

    public void setDemands(ArrayList<Demand> demands) {
        this.demands = demands;
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }
}
