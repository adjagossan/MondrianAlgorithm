package fr.istic.sbd.mondrian;
import java.util.*;
import org.apache.commons.lang3.RandomStringUtils;

public class LaunchMe {
	
	private static List<Data> dataSet = new ArrayList<Data>();
	private static Data data;
	private final static int  rangeLengthMax = 2;
	private final static int randomStringLength = 4;
	
	public static void main(String[] args) 
	{       
		toString(DataSetHelper.createDataSet(6, new int[]{3,10}, new int[]{15,20}, 4));		
	}
	
	public static void toString(List<Data> dataSet){
		for(Data data : dataSet)
			System.out.println(data.toString());
	}
	/*
	private static List<Data> createDataSet(int dataSetLength, int[] rangeForFirstQid, int[] rangeForLastQid, int randomStringSetLength)
	{
		int randomNumberForFirstQid;
		int randomNumberForLastQid;
		List<String> randomStringSet = getRandomStringSet(randomStringSetLength, randomStringLength);
		String randomStringForSensitiveData;
		QID qid;
                
		for (int IdRowDataSet = 1; IdRowDataSet <= dataSetLength; IdRowDataSet++)
		{
			randomNumberForFirstQid = getRandomNumberInRange(rangeForFirstQid);
			randomNumberForLastQid = getRandomNumberInRange(rangeForLastQid);
			qid = new QID(randomNumberForFirstQid, randomNumberForLastQid);
			randomStringForSensitiveData = randomStringSet.get(getRandomNumberInRange(new int[]{1,randomStringSetLength}) - 1);
			data = new Data(IdRowDataSet, qid, randomStringForSensitiveData);
			dataSet.add(data);
		}
		return dataSet;
	}

	private static int getRandomNumberInRange(int[] range)
	{
		if(range.length < rangeLengthMax)
			throw new IllegalArgumentException("length of range must be equals to 2");
		if (range[0]>= range[1])
			throw new IllegalArgumentException("max must be greater than min");
		Random r = new Random();
		return r.nextInt((range[1] - range[0]) + 1) + range[0];
	}
	
	private static String getRandomString(int randomStringLength){
		return RandomStringUtils.randomAlphabetic(randomStringLength);
	}
	
	private static List<String> getRandomStringSet(int randomStringSetLength, int randomStringLength)
	{
		List<String> randomStringSet = new ArrayList<String>();
		String randomString = "";
		for(int indice =0; indice <randomStringSetLength; indice++)
		{
			randomString = getRandomString(randomStringLength);
			if(!(contain(randomStringSet, randomString)))
				randomStringSet.add(randomString);
		}
		return randomStringSet;
	}
	
	private static boolean contain(List<String> randomStringSet, String randomString)
	{
		ListIterator<String> iterator = randomStringSet.listIterator();
		while(iterator.hasNext())
		{
			if(iterator.next().equalsIgnoreCase(randomString))
				return true;
		}
		return false;
	}
	
	*/
}
