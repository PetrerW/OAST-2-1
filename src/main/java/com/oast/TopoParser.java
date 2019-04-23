package com.oast;

import javax.lang.model.type.ArrayType;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Topology parser - from input .txt file
 */
public class TopoParser {
    String filename;
    ArrayList<String> fileLines;
    int currentLine;

    public TopoParser(String filename, ArrayList<String> fileLines, int currentLine) {
        this.filename = filename;
        this.fileLines = fileLines;
        this.currentLine = currentLine;
    }

    public TopoParser(String filename){
        this.filename = filename;
        //net12 have got about 1k lines
        this.fileLines = new ArrayList<>(1000);
        this.currentLine = 0;
    }

    public Network readNetwork(String filename){
        readFile();

        int numberOfLinks = Integer.getInteger(fileLines.get(0));
        ArrayList<Link> links = new ArrayList<Link>(numberOfLinks);

        //TODO move the code to readLinks
        for (;;currentLine++){
            String line = fileLines.get(currentLine);
            if(line.contains("-1"))
                break;
            else
                links.add(readLink(line));
        }
        //TODO get number of demands and initialize list with it
        Network Net = new Network(links, new ArrayList<Demand>(0));

        //TODO parse the rest of the lines in subfunctions
        return null;
    }

    public ArrayList<String> readFile(){

        try{
            Scanner in = new Scanner(new FileReader(filename));

            while(in.hasNext()) {
                fileLines.add(in.next());
            }
            in.close();

        }catch(Exception e){
            System.out.println("Error reading from file: " + filename);
        }
        return fileLines;
    }

    public Link readLink(String line){
        //TODO
        return null;
    }

    public Demand readDeand(String line){
        //TODO
        return null;
    }

    public DemandPath readDemandPath(String line){
        //TODO
        return null;
    }

    public ArrayList<Link> readLinks(String line){
        //TODO
        return null;
    }
}
