package fr.istic.sbd.mondrian;

import java.io.*;
import java.util.*;

public class MondrianHelper
{
    private static List<Integer> listFirstQid = new ArrayList<Integer>();
    private static List<Integer> listSecondQid = new ArrayList<Integer>();
    private static Map<String, List<Integer>> firstQidDimension = new HashMap<String, List<Integer>>();
    private static Map<String, List<Integer>> secondQidDimension = new HashMap<String, List<Integer>>();
    
    public static void chooseDimension()
    {
        
    }
    
    public static void readDataSetCsvFile(String csvFileName)
    {
        try
        {
            String line = null;
            String[] splitResult;
            String firstQidName = "";
            String secondQidName = "";
            
            FileReader fr = new FileReader(csvFileName);
            BufferedReader br = new BufferedReader(fr);
            
            listFirstQid.clear();
            listSecondQid.clear();
            firstQidDimension.clear();
            secondQidDimension.clear();
            
            if((line = br.readLine()) != null)
            {
                splitResult = line.split(",");
                firstQidName = splitResult[0];
                secondQidName = splitResult[1];
            }
            
            while((line = br.readLine()) != null)
            {
                splitResult = line.split(",");
                listFirstQid.add(Integer.parseInt(splitResult[0]));
                listSecondQid.add(Integer.parseInt(splitResult[1]));
            }
            br.close();
            firstQidDimension.put(firstQidName,listFirstQid);
            secondQidDimension.put(secondQidName, listSecondQid);
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("Unable to open '"+ csvFileName+"'");
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
