package com.problem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vivek on 29/6/15.
 * direct succession x>y
 * causality x→y    (x→y ⟺ x>y ∧ y≯x)
 * parallel x || y  (x||y ⟺ x>y ∧ y>x)
 * unrelated x # y  (x#y ⟺ x≯y ∧ y≯x)
 */
public class Problem2 {
    private static Map<String, List<String>> processMap = new HashMap<>();
    public static void main(String [] args){
        if(args.length<1){
            System.out.println("Please provide path of event log as args[0]");
            return;
        }
        Map<String, Integer> mapping = createMap();
        Map<String, List<String>> casesMap = readCsvFile(args[0]);
        Digraph G = createProcessGraph(casesMap, mapping);
        System.out.println(G.stringRepresentation(mapping));
    }

    private static Digraph createProcessGraph(Map<String, List<String>> casesMap, Map<String, Integer> mapping) {
        Digraph G = new Digraph(9);
        for (Map.Entry<String, List<String>> entry : casesMap.entrySet()) {
            List<String> serviceIds = entry.getValue();
            for(int i=0; i<serviceIds.size(); i++){
                if(i<8)
                    G.addEdge(mapping.get(serviceIds.get(i)), mapping.get(serviceIds.get(i+1)));
            }
        }
        return G;
    }

    public static Map<String, List<String>> readCsvFile(String path){
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(path));
            int i=0;
            while ((line = br.readLine()) != null) {
                if(i++==0){
                    continue;
                }
                String[] process = line.split(csvSplitBy);
                if(processMap.get(process[0])==null){
                    List<String> pidList = new ArrayList<>();
                    pidList.add(process[1]);
                    processMap.put(process[0],pidList);
                }
                else{
                    List<String> pidList = processMap.get(process[0]);
                    pidList.add(process[1]);
                    processMap.put(process[0],pidList);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return processMap;
    }

    private static Map<String, Integer> createMap() {
        Map<String, Integer> mapping = new HashMap<>();
        mapping.put("ONES", 1);
        mapping.put("TWOS", 2);
        mapping.put("THREES", 3);
        mapping.put("FOURS", 4);
        mapping.put("FIVES", 5);
        mapping.put("SIXS", 6);
        mapping.put("SEVENS", 7);
        mapping.put("EIGHTS", 8);
        mapping.put("NINES", 9);
        return mapping;
    }
}
