package com.oast;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TopoParserTest {

    public TopoParser t;

    public void initialize(String filepath){
        t = new TopoParser(filepath);
    }

    @Test
    public void readFile() {
        //TODO How to relative path to the file in Java
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
        //TODO
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
    public void readDemand() {
        //TODO
    }

    @Test
    public void readDemandPath() {
        //TODO
    }

    @Test
    public void readLinks() {
        initialize("./net/net4.txt");
        ArrayList<String> lines = t.readFile();
        ArrayList<Link> links = t.readLinks();
        ArrayList<Link> trueLinks = new ArrayList<>(5);
        for(int i = 1; i < 6; i++){
            trueLinks.add(t.readLink(lines.get(i), i));
        }
        Assert.assertArrayEquals(trueLinks.toArray(), links.toArray());
    }
}