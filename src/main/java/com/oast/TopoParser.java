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
        ArrayList<Link> links = this.readLinks();

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

    /**
     *
     * @param line Line with Link parameters
     * @param id ID of the Link
     * @return
     */
    public Link readLink(String line, int id){
        if(line.length() == 5){
            String[] params = line.split(" ");
            return new Link(params, id);
        }
        else{
            System.out.println("Wrong number of parameters (" + line.length() + "), expected 5.");
            return null;
        }
    }

    public Demand readDemand(String line){
        //TODO
        return null;
    }

    public DemandPath readDemandPath(String line){
        //TODO
        return null;
    }

    /**
     * Sets currentLine to 0 and starts reading from the beginning
     * @return ArrayList of the links in the network
     */
    public ArrayList<Link> readLinks(){
        this.currentLine = 0;
        int numberOfLinks = Integer.getInteger(fileLines.get(0));
        ArrayList<Link> links = new ArrayList<Link>(numberOfLinks);

        int linkID = 1;

        try{
            for (;;currentLine++){
                String line = fileLines.get(currentLine);
                if(line.contains("-1"))
                    break;
                else {
                    Link link = readLink(line, linkID);
                    if(link != null){
                        links.add(link);
                        linkID++;
                    }else
                        System.out.println("Link hasn't been parsed.");
                }
            }
        }catch(Exception e){
            System.out.println("Error while reading a link line: " + e.getMessage());
        }

        return links;
    }
}
