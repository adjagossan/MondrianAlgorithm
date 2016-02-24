package fr.istic.sbd.mondrian;

import java.util.*;

public class LaunchMe {

    private static List<Data> dataSet = new ArrayList<Data>();
    private static Data data;
    private final static int rangeLengthMax = 2;
    private final static int randomStringLength = 4;

    public static void main(String[] args) {
        //toString(DataSetHelper.createDataSet(6, new int[]{3, 10}, new int[]{15, 20}, 4));
        //MondrianHelper.readDataSetCsvFile("dataset.csv");
        //System.out.println(MondrianHelper.chooseDimension());
    }

    public static void toString(List<Data> dataSet) {
        for (Data data : dataSet) {
            System.out.println(data.toString());
        }
    }
}