package com.oast;

import java.util.ArrayList;
import java.util.Arrays;

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

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Network)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Network n = (Network) o;

        if(Arrays.equals(this.demands.toArray(), n.demands.toArray()) && Arrays.equals(this.links.toArray(), n.links.toArray()))
            return true;
        else
            return false;
    }
}