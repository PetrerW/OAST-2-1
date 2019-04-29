package com.oast;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DemandTest {

    @Test
    public void equals1() {
        Demand D = new Demand(new Node(1), new Node(2), 3, new ArrayList<DemandPath>() {
            {
                add(new DemandPath(1, new int[] {1}));
                add(new DemandPath(2, new int[] {2, 3}));
                add(new DemandPath(3, new int[] {2, 5, 4}));
            }
        }, 1);

        Demand D2 = new Demand(new Node(1), new Node(2), 3, new ArrayList<DemandPath>() {
            {
                add(new DemandPath(1, new int[] {1}));
                add(new DemandPath(2, new int[] {2, 3}));
                add(new DemandPath(3, new int[] {2, 5, 4}));
            }
        }, 1);

        Assert.assertEquals(D2, D);
    }
}