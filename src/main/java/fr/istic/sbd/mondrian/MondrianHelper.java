package fr.istic.sbd.mondrian;

import java.util.*;

public class MondrianHelper
{
    private static int instance = 0;
    private static int numberOfDivision = 0;
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
    { 
        dimension dim;
        SortedMap<Integer, Integer> fq;
        int median = 0;
        
        if(!allowable(dataSet, k))
           return getEquivalenceClass(dataSet);
        else
        {
            dim = chooseDimension(dataSet);
            fq = frequencySet(dataSet, dim);
            median = findMedian(fq);
            
            mondrian(getLeftOrRigthPartition(dataSet, dim, median, side.left), k);
            mondrian(getLeftOrRigthPartition(dataSet, dim, median, side.right), k);
        }
        return equivalenceClassList;
    }
    
    /**
     * 
     * @param dataSet
     * @param dim
     * @param splitVal
     * @param s
     * 
     * renvoie tout n-uplet de la partition courante 'dataSet' dont la valeur
     * sur la dimension 'dim' choisie est inferieure ou égale à la médiane 'splitVal'
     */
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
    
    /**
     * ici on repond à la question pourront-on faire une decoupe sur un jeu de donnée
     * avec au moins k données de part et d'autre des partitions resultantes
     * @param dataSet
     * @param k
     * @return boolean
     */
    public static boolean allowable(List<Data> dataSet, int k)
    {
       return (dataSet.size() >= 2*k);
     }
    
    /*
     * retoune la médiane de l'histogramme des n-uplets sur la dimension choisie
     */
    public static int findMedian(SortedMap<Integer, Integer> frequencySet)
    {
        List<Integer> cumulateValues = new ArrayList<Integer>();
        Collection<Integer> values = (Collection<Integer>)frequencySet.values();
        Iterator<Integer> it = values.iterator();
        int size = 0;
        
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
                    
                }
            }
            for(int j = 1; j <= (index+1); j++){
                
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
        numberOfDivision++;
        return median;
    }
    
   /**
    * 
    * renvoie le nombre de divisions effectués
    */
    public static int getNumberOfDivision(){
        return numberOfDivision;
    }
    /*
     * cette fonction retourne l'histogramme des n-uplets sur la dimension choisie
     */
    public static SortedMap<Integer, Integer> frequencySet(List<Data> dataSet/*partion actuelle*/, dimension dim/*dimension choisie*/) {
        List<Integer> selectedDimensionValues = null;
        //si le jeu de données est vide on retourne null
        if(dataSet.isEmpty())
            return null;
        //si la dimension choisie est 'first', on recupere une liste des valeurs du premier quasi-identifiant
        //si la dimension choisie est 'second', on recupere une liste des valeurs du deuxieme quasi-identifiant
        switch (dim) 
        {
            case first:
                selectedDimensionValues = getFirstQidValues(dataSet);
                break;
            case second:
                selectedDimensionValues = getSecondQidValues(dataSet);
                break;
        }
        //on definit un iterateur sur notre liste 'selectedDimensionValues' d'entiers 
        ListIterator<Integer> iterator = null;
        //Initialisation de l'histogramme des n-uplets sur la dimension choisie
        //qui est un ensemble de couples (cle, valeur)
        //avec cle representant une valeur de la dimension choisie et
        //valeur pour occurence de cette cle
        SortedMap<Integer, Integer> histogram = new TreeMap<Integer, Integer>();
        Integer value = null;
        //represente les occurences d'une valeur presente dans notre
        //liste 'selectedDimensionValues' d'entier
        int counter = 0;
        do 
        {
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
        return histogram;//l'histogramme des n-uplets sur la dimension choisie
    }
    /*
     * retourne la dimension choisie
     */
    public static dimension chooseDimension(List<Data> currentPartition)
    {
        //retourne une liste des valeurs du premier quasi-identifiant 
        List<Integer> firstQidValues = getFirstQidValues(currentPartition);
        //retourne une liste des valeurs du premier quasi-identifiant 
        List<Integer> secondQidValues = getSecondQidValues(currentPartition);
        //on fait la difference entre le maximum et le minimum de la liste
        //des valeurs du premier quasi-identifiant
        int x = ((int) Collections.max(firstQidValues) - ((int) Collections.min(firstQidValues)));
        //on fait la difference entre le maximum et le minimum de la liste
        //des valeurs du second quasi-identifiant
        int y = ((int) Collections.max(secondQidValues) - ((int) Collections.min(secondQidValues)));
        if ((x - y) >= 0) {
            return dimension.first; //ici la dimension 'first' choisie
        } else {
            return dimension.second;//ici la dimension 'second' choisie
        }
    }
    
    /**
     * ajoute une classe d'équivalence à la liste de classes d'equivalence.
     * Notons que le jeu de données passé en paramètre à cette fonction ne peut
     * être decoupé avec au moins k element de part et d'autre des partitions resultantes
     * @param dataSet
     * @return 
     */
    public static List<EquivalenceClass> getEquivalenceClass(List<Data> dataSet)
    {
        List<Integer> firstQidValues = null;
        List<Integer> secondQidValues = null;
        //si notre jeu de données est non vide.
        if(dataSet.size() > 0)
        {   
            // on recupere l'ensemble des valeurs du premier quasi-identifiant
            firstQidValues = getFirstQidValues(dataSet);
            // on recupere l'ensemble des valeurs du deuxieme quasi-identifiant
            secondQidValues = getSecondQidValues(dataSet);
            //on recupere le maximum des données du premier quasi-identifiant
            int maxFirstQidValues = ((int)Collections.max(firstQidValues));
            //on recupere le minimum des données du premier quasi-identifiant
            int minFirstQidValues = (int)Collections.min(firstQidValues);
            //on recupere le maximum des données du deuxieme quasi-identifiant
            int maxSecondQidValues = (int)Collections.max(secondQidValues);
            //on recupere le minimum des données du deuxieme quasi-identifiant
            int minSecondQidValues = (int)Collections.min(secondQidValues);
            //on parcourt la partition passée en paramètre pour récuperer 
            //les 'sensitives data' et au même moment créer une classe
            //d'équivalence et l'ajoutée à notre liste de classes d'équivalence
            for(Data data : dataSet)
            {
                
              equivalenceClassList.add
                        (
                        new EquivalenceClass(
                        new Range(minFirstQidValues, maxFirstQidValues), 
                        new Range(minSecondQidValues, maxSecondQidValues),
                        data.getStr())
                        );
            }
        }
        return equivalenceClassList;
    }
    
    //retourne une liste des valeurs du premier quasi-identifiant
    public static List<Integer> getFirstQidValues(List<Data> dataSet)
    {
        List<Integer> firstQidValues = new ArrayList<Integer>();
        for (Data data : dataSet)
            firstQidValues.add(data.getQid().getFirstQid());
        return firstQidValues;
    }
    
    //retourne une liste des valeurs du second quasi-identifiant
    public static List<Integer> getSecondQidValues(List<Data> dataSet)
    {
        List<Integer> secondQidValues = new ArrayList<Integer>();
        for (Data data : dataSet) {
            secondQidValues.add(data.getQid().getSecondQid());
        }
        return secondQidValues;
    }
}
