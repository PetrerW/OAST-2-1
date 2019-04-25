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

        //Set the current line at demands number
        if(this.fileLines.get(currentLine).equals("-1"))
            currentLine = currentLine + 2;
        else if(this.fileLines.get(currentLine).equals(""))
            currentLine++;


        //TODO get number of demands and initialize list with it
        Network Net = new Network(links, new ArrayList<Demand>(0));

        //TODO parse the rest of the lines in subfunctions
        return null;
    }

    public ArrayList<String> readFile(){

        try{
            Scanner in = new Scanner(new FileReader(filename));

            while(in.hasNext()) {
                fileLines.add(in.nextLine());
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
        if(line.split(" ").length == 5){
            String[] params = line.split(" ");
            return new Link(params, id);
        }
        else{
            System.out.println("Wrong number of parameters (" + line.length() + "), expected 5.");
            return null;
        }
    }

    public ArrayList<Demand> readDemands(){
        int numberOfDemands = Integer.parseInt(fileLines.get(currentLine));
        ArrayList<Demand> demands = new ArrayList<>(numberOfDemands);
        int DemandID = 1;

        try{
            for (;;currentLine++){
                String line = fileLines.get(currentLine);
                if(line.split("").length == 3){
                    Demand D = readDemand(line, DemandID);
                    if(D != null){
                        demands.add(D);
                        DemandID++;
                    }else
                        System.out.println("Demand hasn't been parsed. (readDemand returned null)");
                }
            }
        }catch(Exception e){
            System.out.println("Error while reading a demand line: " + e.getMessage());
        }
        return demands;
    }

    public Demand readDemand(String line, int DemandID) throws Exception{

        String[] params = line.split(" ");


        if(params.length!=3){
            throw new Exception("Wrong line size (" + line.split(" ").length + "), expected 3.");
        }
        else{
            //read demand paths
            currentLine++;
            int numberOfDemandPaths = Integer.parseInt(fileLines.get(currentLine));

            ArrayList<DemandPath> demandPaths = new ArrayList<>(numberOfDemandPaths);

            for(;currentLine < currentLine + numberOfDemandPaths; currentLine++){
                DemandPath DP = readDemandPath(fileLines.get(currentLine));
                if(DP != null){
                    demandPaths.add(DP);
                }
                else
                    throw new Exception("Demand path was null");
            }

            Demand D = new Demand(params, demandPaths, DemandID);
            return D;
        }
    }

    public DemandPath readDemandPath(String line) throws Exception{
        String[] params = line.split(" ");
        if(params.length != 3){
            throw new Exception("Wrong line size (" + line.split(" ").length + "), expected 3.");
        }
        else{
            //TODO
            return new DemandPath();
        }
    }

    /**
     * Sets currentLine to 0 and starts reading from the beginning
     * @return ArrayList of the links in the network
     */
    public ArrayList<Link> readLinks(){
        this.currentLine = 0;
        int numberOfLinks = Integer.parseInt(fileLines.get(0));
        ArrayList<Link> links = new ArrayList<Link>(numberOfLinks);

        int linkID = 1;

        try{
            for (currentLine = 1;;currentLine++){
                String line = fileLines.get(currentLine);
                if(line.contains("-1"))
                    break;
                else {
                    Link link = readLink(line, linkID);
                    if(link != null){
                        links.add(link);
                        linkID++;
                    }else
                        System.out.println("Link hasn't been parsed. (readLink returned null)");
                }
            }
        }catch(Exception e){
            System.out.println("Error while reading a link line: " + e.getMessage());
        }

        return links;
    }
}
