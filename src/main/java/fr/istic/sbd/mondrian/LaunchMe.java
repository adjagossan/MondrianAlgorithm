package fr.istic.sbd.mondrian;

import java.util.*;

public class LaunchMe {

    private static List<Data> dataSet = new ArrayList<Data>();
    private static Data data;
    private final static int rangeLengthMax = 2;
    private final static int randomStringLength = 4;

    public static void main(String[] args) {
        List<Data> data = DataSetHelper.createDataSet(6, new int[]{3, 20}, new int[]{5, 25}, 4);
        //toStringt(data);
        MondrianHelper.mondrian(data, 2);
        MondrianHelper.display();
        //toString(MondrianHelper.chooseDimension(data));
        //MondrianHelper.readDataSetCsvFile("dataset.csv");
        //System.out.println(MondrianHelper.chooseDimension());
        //7-7-8-10-11-14 
        //SortedMap<Integer, Integer> frequencySet = new TreeMap<Integer, Integer>();
        //frequencySet.put(6, 2);
        //frequencySet.put(7, 3);
        //frequencySet.put(8, 4);
        //frequencySet.put(10, 5);
        //frequencySet.put(12, 3);
        //System.out.println("Med "+MondrianHelper.findMedian(frequencySet));
       // System.out.println(MondrianHelper.chooseDimension(data));
    }

    public static void toString(List<Integer> dataSet){
        for (Integer data : dataSet) {
            System.out.println(data.toString());
        }
    }
    
    public static void toStringt(List<Data> dataSet){
        for (Data data : dataSet) {
            System.out.println(data.toString());
        }
    }
        
}