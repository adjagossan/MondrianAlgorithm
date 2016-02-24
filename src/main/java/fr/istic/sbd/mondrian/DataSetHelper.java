package fr.istic.sbd.mondrian;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import org.apache.commons.lang3.RandomStringUtils;

public class DataSetHelper {

    private static List<Data> dataSet = new ArrayList<Data>();
    private static Data data;
    private final static int rangeLengthMax = 2;
    private final static int randomStringLength = 4;

    public static List<Data> createDataSet(int dataSetLength, int[] rangeForFirstQid, int[] rangeForLastQid, int randomStringSetLength) {
        int randomNumberForFirstQid;
        int randomNumberForLastQid;
        List<String> randomStringSet = getRandomStringSet(randomStringSetLength, randomStringLength);
        String randomStringForSensitiveData;
        QID qid;
        BufferedWriter bw = null;
        try
        {
            bw = getDataSetFile("dataset");
            for (int IdRowDataSet = 1; IdRowDataSet <= dataSetLength; IdRowDataSet++)
            {
                randomNumberForFirstQid = getRandomNumberInRange(rangeForFirstQid);
                randomNumberForLastQid = getRandomNumberInRange(rangeForLastQid);
                qid = new QID(randomNumberForFirstQid, randomNumberForLastQid);
                bw.append(qid.getFirstQid()+", "+qid.getSecondQid()+" \n");
                randomStringForSensitiveData = randomStringSet.get(getRandomNumberInRange(new int[]{1, randomStringSetLength}) - 1);
                data = new Data(IdRowDataSet, qid, randomStringForSensitiveData);
                dataSet.add(data);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
        finally
        {
            try
            {
                bw.flush();
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(DataSetHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dataSet;
    }

    public static int getRandomNumberInRange(int[] range) {
        if (range.length < rangeLengthMax) {
            throw new IllegalArgumentException("length of range must be equals to 2");
        }
        if (range[0] >= range[1]) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((range[1] - range[0]) + 1) + range[0];
    }

    public static String getRandomString(int randomStringLength) {
        return RandomStringUtils.randomAlphabetic(randomStringLength);
    }

    public static List<String> getRandomStringSet(int randomStringSetLength, int randomStringLength) {
        List<String> randomStringSet = new ArrayList<String>();
        String randomString = "";
        for (int indice = 0; indice < randomStringSetLength; indice++) {
            randomString = getRandomString(randomStringLength);
            if (!(contain(randomStringSet, randomString))) {
                randomStringSet.add(randomString);
            }
        }
        return randomStringSet;
    }

    public static boolean contain(List<String> randomStringSet, String randomString) {
        ListIterator<String> iterator = randomStringSet.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().equalsIgnoreCase(randomString)) {
                return true;
            }
        }
        return false;
    }

    public static BufferedWriter getDataSetFile(String CsvFileName) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(
                    new FileWriter(
                    new File("./" + CsvFileName + ".csv"), false));
            bw.append("firstQID, secondQID \n");
        } catch (IOException ex) {
            Logger.getLogger(DataSetHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bw;
    }
}
//}
