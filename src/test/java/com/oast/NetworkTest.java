package com.oast;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class NetworkTest {

    @Test
    public void equals1() {
        ArrayList<Demand> Demands1 = new ArrayList<Demand>() {
            {
                //1st Demand
                add(new Demand(new Node(1), new Node(2), 3, new ArrayList<DemandPath>() {
                    {
                        add(new DemandPath(1, new int[] {1}));
                        add(new DemandPath(2, new int[] {2, 3}));
                        add(new DemandPath(3, new int[] {2, 5, 4}));
                    }
                }, 1));
            }
        };

        ArrayList<Demand> Demands2 = new ArrayList<Demand>() {
            {
                //1st Demand
                add(new Demand(new Node(1), new Node(2), 3, new ArrayList<DemandPath>() {
                    {
                        add(new DemandPath(1, new int[] {1}));
                        add(new DemandPath(2, new int[] {2, 3}));
                        add(new DemandPath(3, new int[] {2, 5, 4}));
                    }
                }, 1));
            }
        };

        ArrayList<Link> Links1 = new ArrayList<Link>(){
            {
                add(new Link(new Node(1), new Node(2), 3, new Module(4), 5, 6));
                add(new Link(new Node(12), new Node(11), 10, new Module(9), 8, 7));
            }
        };

        ArrayList<Link> Links2 = new ArrayList<Link>(){
            {
                add(new Link(new Node(1), new Node(2), 3, new Module(4), 5, 6));
                add(new Link(new Node(12), new Node(11), 10, new Module(9), 8, 7));
            }
        };

        Network Net1 = new Network(Links1, Demands1);
        Network Net2 = new Network(Links2, Demands2);

        Assert.assertEquals(Net1, Net2);
    }
}