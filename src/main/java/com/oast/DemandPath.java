package com.oast;

import java.util.Arrays;

public class DemandPath {
    private int id;
    private int demandId;

    /**
     * Contains only links' IDs (of the links that belong to the
     */
    private int[] links;

    public DemandPath(String[] params) {

        int[] tempLinks = new int[params.length-1];

        for(int i = 1; i<params.length; i++){
            tempLinks[i-1] = Integer.parseInt(params[i]);
        }

        this.links = tempLinks;
    }

    public int[] getLinks() { return links; }

    public void setLinks(int[] links) { this.links = links; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDemandId() {
        return demandId;
    }

    public void setDemandId(int demandId) {
        this.demandId = demandId;
    }

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof DemandPath)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        DemandPath dp = (DemandPath) o;

        if(this.id == dp.getId() && Arrays.equals(this.links, dp.getLinks()))
            return true;
        else
            return false;
    }
}
