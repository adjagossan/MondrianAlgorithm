package fr.istic.sbd.mondrian;

import java.io.*;
import java.util.*;

public class MondrianHelper 
{
    private static String firstQidName;
    private static String secondQidName;
    private static List<Integer> listFirstQid = new ArrayList<Integer>();
    private static List<Integer> listSecondQid = new ArrayList<Integer>();
    private static Map<String, List<Integer>> firstQidDimension = new HashMap<String, List<Integer>>();
    private static Map<String, List<Integer>> secondQidDimension = new HashMap<String, List<Integer>>();

    public static Map<String, List<Integer>> chooseDimension()
    {
        int x = 0, y = 0;
        if (firstQidDimension.containsKey(firstQidName)) {
            x = ((int) Collections.max(firstQidDimension.get(firstQidName))) - ((int) Collections.min(firstQidDimension.get(firstQidName)));
        }
        if (secondQidDimension.containsKey(secondQidName)) {
            y = ((int) Collections.max(secondQidDimension.get(secondQidName))) - ((int) Collections.min(secondQidDimension.get(secondQidName)));
        }
        if ((x - y) > 0) {
            return firstQidDimension;
        } else {
            return secondQidDimension;
        }
    }
    
    public static void frequencySet(){
        
    }

    public static void readDataSetCsvFile(String csvFileName) {
        try {
            String line = null;
            String[] splitResult;
            FileReader fr = new FileReader("./" + csvFileName);
            BufferedReader br = new BufferedReader(fr);

            listFirstQid.clear();
            listSecondQid.clear();
            firstQidDimension.clear();
            secondQidDimension.clear();

            if ((line = br.readLine()) != null)
            {
                splitResult = line.split(",");
                firstQidName = splitResult[0].trim();
                secondQidName = splitResult[1].trim();
            }

            while ((line = br.readLine()) != null) {
                splitResult = line.split(",");
                listFirstQid.add(Integer.parseInt(splitResult[0].trim()));
                listSecondQid.add(Integer.parseInt(splitResult[1].trim()));
            }
            br.close();
            firstQidDimension.put(firstQidName, listFirstQid);
            secondQidDimension.put(secondQidName, listSecondQid);
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open '" + csvFileName + "'");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
