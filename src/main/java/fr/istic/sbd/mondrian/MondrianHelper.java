package fr.istic.sbd.mondrian;

import java.util.*;

public class MondrianHelper
{
    private enum dimension {
        first, second
    };
    
    private enum side{
        left, right
    };
    
    private static List<EquivalenceClass> equivalenceClassList = new ArrayList<EquivalenceClass>();
    
    public static void display()
    {
        for(EquivalenceClass eq : equivalenceClassList)
            System.out.println(eq.toString());
    }
    
    public static List<EquivalenceClass> mondrian(List<Data> dataSet, int k)
    {   System.out.println("** mondrian **");
        DataSetHelper.toString(dataSet);
        dimension dim;
        SortedMap<Integer, Integer> fq;
        int median = 0;
        List<Data> leftPartition, rigthPartition;
        
        if(!allowable(dataSet, k))
            return getEquivalenceClass(dataSet);
        else
        {
            dim = chooseDimension(dataSet);
            System.out.println("dim "+ dim);
            fq = frequencySet(dataSet, dim);
            median = findMedian(fq);
            leftPartition = getLeftOrRigthPartition(dataSet, dim, median, side.left);
            rigthPartition = getLeftOrRigthPartition(dataSet, dim, median, side.right);
            System.out.println("** left **");
            DataSetHelper.toString(leftPartition);
            System.out.println("** rigth **");
            DataSetHelper.toString(rigthPartition);
            mondrian(leftPartition, k);
            mondrian(rigthPartition, k);
        }
        return null;
    }
    
    public static List<Data> getLeftOrRigthPartition(List<Data> dataSet, dimension dim, int splitVal, side s)
    {
        List<Data> partition = new ArrayList<Data>();
        Data data = null;
        for (Iterator<Data> it = dataSet.iterator(); it.hasNext();)
        {
            data = it.next();
            switch(dim)
            {
                case first:
                        if(data.getQid().getFirstQid() > splitVal && s == side.right)
                            partition.add(data);
                        if(data.getQid().getFirstQid() <= splitVal && s == side.left)
                            partition.add(data);
                    break;
                case second:
                        if(data.getQid().getSecondQid() > splitVal && s == side.right)
                            partition.add(data);
                        if(data.getQid().getSecondQid() <= splitVal && s == side.left)
                            partition.add(data);
                    break;
            }
        }
        return partition;
    }
    
    public static boolean allowable(List<Data> dataSet, int k)
    {
       return (dataSet.size() >= 2*k);
        /*List<Integer> qidValues = null;
        switch(dim)
        {
            case first:
                    qidValues = getFirstQidValues(dataSet);
                break;
            case second:
                    qidValues = getSecondQidValues(dataSet);
                break;
        }
        Collections.sort(qidValues);
        int size = qidValues.size();
        return ((qidValues.subList(0, (size/2)).size() >= k) && (qidValues.subList((size/2), size).size() >= k));*/ 
    }
    
    public static int findMedian(SortedMap<Integer, Integer> frequencySet)
    {
        List<Integer> cumulateValues = new ArrayList<Integer>();
        Collection<Integer> values = (Collection<Integer>)frequencySet.values();
        Iterator<Integer> it = values.iterator();
        int size = 0;
        Integer cumulateVal;
        while(it.hasNext())
        {
            size += it.next().intValue();
            cumulateValues.add(size);
        }
        
        Set<Integer> keySet = frequencySet.keySet();
        Iterator<Integer> keySetIterator = keySet.iterator();
        int median = 0;
        int index = 0;
        if(size % 2 == 0) //si pair
        {
            for(Integer val : cumulateValues)
            {
                if(val.intValue() <= (size / 2))
                {
                    index++;
                    System.out.println("index "+index);
                }
            }
            for(int j = 1; j <= (index+1); j++){
                System.out.println("j** "+j);
                if(keySetIterator.hasNext())
                {
                    if(j >= index){
                        median += keySetIterator.next().intValue();
                    }
                    else
                        keySetIterator.next();
                }
            }
            median =  (median / 2);
        }   
        if(size % 2 != 0) // si impair
        {
            for(Integer val : cumulateValues)
            {
                if(val.intValue() <= ((size + 1) / 2)){
                    index++;
                    System.out.println("index "+index);
                }
            }
            for(int j = 1; j <= index; j++)
            {
                if(keySetIterator.hasNext())
                {
                    if(j == index)
                        median = keySetIterator.next().intValue();
                    else
                        keySetIterator.next();
                }
            }
        }
        System.out.println("median** "+median);
        return median;
    }
    
    
    public static SortedMap<Integer, Integer> frequencySet(List<Data> dataSet, dimension dim) {
        List<Integer> selectedDimensionValues = null;

        switch (dim) {
            case first:
                selectedDimensionValues = getFirstQidValues(dataSet);
                break;
            case second:
                selectedDimensionValues = getSecondQidValues(dataSet);
                break;
        }

        ListIterator<Integer> iterator = selectedDimensionValues.listIterator();
        SortedMap<Integer, Integer> histogram = new TreeMap<Integer, Integer>();
        Integer value = null;
        int counter = 0;
        do {
            iterator = selectedDimensionValues.listIterator();
            while (iterator.hasNext()) {
                if (counter == 0) {
                    value = iterator.next();
                    counter++;
                    iterator.remove();
                } else {
                    if (value.intValue() == iterator.next().intValue()) {
                        counter++;
                        iterator.remove();
                    }
                }
            }
            histogram.put(value, counter);
            counter = 0;
        } while (selectedDimensionValues.size() > 0);
        return histogram;
    }

    public static dimension chooseDimension(List<Data> currentPartition)
    {
        List<Integer> firstQidValues = getFirstQidValues(currentPartition);
        List<Integer> secondQidValues = getSecondQidValues(currentPartition);
        int x = ((int) Collections.max(firstQidValues) - ((int) Collections.min(firstQidValues)));
        int y = ((int) Collections.max(secondQidValues) - ((int) Collections.min(secondQidValues)));
        if ((x - y) >= 0) {
            return dimension.first;
        } else {
            return dimension.second;
        }
    }
    
    public static List<EquivalenceClass> getEquivalenceClass(List<Data> dataSet)
    {
        System.out.println("** getEquivalenceClass **");
        DataSetHelper.toString(dataSet);
        List<Integer> firstQidValues = getFirstQidValues(dataSet);
        List<Integer> secondQidValues = getSecondQidValues(dataSet);
        System.out.println("firstQidValues "+firstQidValues.size()+" secondQidValues "+secondQidValues.size());
        
        int maxFirstQidValues = ((int)Collections.max(firstQidValues));
       
        int minFirstQidValues = (int)Collections.min(firstQidValues);
       
        
        int maxSecondQidValues = (int)Collections.max(secondQidValues);
        int minSecondQidValues = (int)Collections.min(secondQidValues);
        equivalenceClassList.add(new EquivalenceClass(new Range(minFirstQidValues, 
                maxFirstQidValues), new Range(minSecondQidValues, maxSecondQidValues)));
        return equivalenceClassList;
    }
    
    public static List<Integer> getFirstQidValues(List<Data> dataSet)
    {
        List<Integer> firstQidValues = new ArrayList<Integer>();
        for (Data data : dataSet) {
            firstQidValues.add(data.getQid().getFirstQid());
        }
        return firstQidValues;
    }

    public static List<Integer> getSecondQidValues(List<Data> dataSet)
    {
        List<Integer> secondQidValues = new ArrayList<Integer>();
        for (Data data : dataSet) {
            secondQidValues.add(data.getQid().getSecondQid());
        }
        return secondQidValues;
    }
}
