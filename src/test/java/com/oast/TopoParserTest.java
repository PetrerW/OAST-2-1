package com.oast;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class TopoParserTest {

    public TopoParser t;

    public void initialize(String filepath) {
        t = new TopoParser(filepath);
    }

    @Test
    public void readFile() {
        initialize("./net/net4.txt");
        ArrayList<String> lines = t.readFile();
        Assert.assertNotNull(lines);
        Assert.assertEquals(lines.get(1), "1 2 72 1 2");

//        for(int i = 0; i<lines.size(); i++){
//            System.out.println(lines.get(i));
//        }
    }

    @Test
    public void readNetwork() {
        initialize("./net/net4.txt");
        Network Net = t.readNetwork();

        ArrayList<Demand> D_true = generateTrueDemands();
        ArrayList<Link> L_true = generateTrueLinks();

        Network N_true = new Network(L_true, D_true);

        Assert.assertEquals(N_true, Net);
    }

    @Test
    public void readLink() {
        initialize("./net/net4.txt");
        ArrayList<String> lines = t.readFile();
        Link readLink = t.readLink(lines.get(1), 1);
        //Rewritten by hand from 2nd line of the file
        Link link = new Link(new Node(1), new Node(2), 72, new Module(1), 2, 1);
        Assert.assertEquals(link, readLink);
    }

    @Test
    public void readLinks() {
        initialize("./net/net4.txt");
        ArrayList<Link> links = t.readLinks();
        ArrayList<Link> trueLinks = generateTrueLinks();
        Assert.assertArrayEquals(trueLinks.toArray(), links.toArray());
    }

    public ArrayList<Link> generateTrueLinks(){
        ArrayList<String> lines = t.readFile();
        ArrayList<Link> trueLinks = new ArrayList<>(5);
        for (int i = 1; i < 6; i++) {
            trueLinks.add(t.readLink(lines.get(i), i));
        }
        return trueLinks;
    }

    public ArrayList<Demand> generateTrueDemands(){
        ArrayList<Demand> D_true = new ArrayList<Demand>() {
            {
                //1st Demand
                add(new Demand(new Node(1), new Node(2), 3, new ArrayList<DemandPath>() {
                    {
                        add(new DemandPath(1, new int[] {1}));
                        add(new DemandPath(2, new int[] {2, 3}));
                        add(new DemandPath(3, new int[] {2, 5, 4}));
                    }
                }, 1));

                //2nd Demand
                add(new Demand(new Node(1), new Node(3), 4, new ArrayList<DemandPath>() {
                    {
                        add(new DemandPath(1, new int[] {2}));
                        add(new DemandPath(2, new int[] {1, 3}));
                        add(new DemandPath(3, new int[] {1, 4, 5}));
                    }
                }, 2));

                //3rd Demand
                add(new Demand(new Node(1), new Node(4), 5, new ArrayList<DemandPath>() {
                    {
                        add(new DemandPath(1, new int[] {1, 4}));
                        add(new DemandPath(2, new int[] {2, 5}));
                    }
                }, 3));

                //4th Demand
                add(new Demand(new Node(2), new Node(3), 2, new ArrayList<DemandPath>() {
                    {
                        add(new DemandPath(1, new int[] {3}));
                        add(new DemandPath(2, new int[] {1, 2}));
                        add(new DemandPath(3, new int[] {4, 5}));
                    }
                }, 4));

                add(new Demand(new Node(2), new Node(4), 2, new ArrayList<DemandPath>() {
                    {
                        add(new DemandPath(1, new int[] {4}));
                        add(new DemandPath(2, new int[] {3, 5}));
                        add(new DemandPath(3, new int[] {1, 2, 5}));
                    }
                }, 5));

                add(new Demand(new Node(3), new Node(4), 4, new ArrayList<DemandPath>() {
                    {
                        add(new DemandPath(1, new int[] {5}));
                        add(new DemandPath(2, new int[] {3, 4}));
                        add(new DemandPath(3, new int[] {2, 1, 4}));
                    }
                }, 6));
            }
        };

        return D_true;
    }

    @Test
    public void readDemands() {
        initialize("./net/net4.txt");
        t.readFile();
        t.currentLine = 8;

        ArrayList<Demand> D_true = generateTrueDemands();
        ArrayList<Demand> D = t.readDemands();

        Assert.assertEquals(D_true.get(0), D.get(0));
//        Assert.assertTrue(Arrays.deepEquals(D_true.toArray(), D.toArray()));
    }


    @Test
    public void readDemand() {
        initialize("./net/net4.txt");
        t.currentLine = 11;
        try{
            Demand D = t.readDemand(t.fileLines.get(t.currentLine), 1);
            ArrayList<DemandPath> demandPaths = new ArrayList<DemandPath>() {
                {
                    add(new DemandPath(1, new int[] {1}));
                    add(new DemandPath(2, new int[] {2, 3}));
                    add(new DemandPath(3, new int[] {2, 5, 4}));
                }
            };
            Demand D_true = new Demand(new Node(1), new Node(2), 3, demandPaths, 1);
            Assert.assertEquals(D, D_true);
        }catch (Exception e){

        }
    }

    @Test
    public void readDemandPath() {
        initialize("./net/net4.txt");
        String[] lines = {"1 2", "2 1 3", "3 1 4 5"};
        DemandPath DP1 = t.readDemandPath(lines[0]);
        DemandPath DP1_true = new DemandPath(1, new int[] {2});
        DemandPath DP2 = t.readDemandPath(lines[1]);
        DemandPath DP2_true = new DemandPath(2, new int[] {1, 3});
        DemandPath DP3 = t.readDemandPath(lines[2]);
        DemandPath DP3_true = new DemandPath(3, new int[] {1, 4, 5});

        Assert.assertEquals(DP1.getId(), DP1_true.getId());
        Assert.assertArrayEquals(DP1.getLinks(), DP1_true.getLinks());
        Assert.assertEquals(DP2.getId(), DP2_true.getId());
        Assert.assertArrayEquals(DP2.getLinks(), DP2_true.getLinks());
        Assert.assertEquals(DP3.getId(), DP3_true.getId());
        Assert.assertArrayEquals(DP3.getLinks(), DP3_true.getLinks());
    }
}
