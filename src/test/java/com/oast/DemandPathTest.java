package com.oast;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DemandPathTest {

    @Test
    public void equals1() {
        DemandPath DP = new DemandPath(1, new int[] {1, 2, 3, 4, 5});
        DemandPath DP_2 = new DemandPath(1, new int[] {1, 2, 3, 4, 5});
        Assert.assertEquals(DP, DP_2);
    }
}