package fr.istic.sbd.mondrian;


public class EquivalenceClass {
	private Range firstQid;
	private Range secondQid;
        private String sensitiveData;

    public EquivalenceClass(Range firstQid, Range secondQid, String sensitiveData)
    {
        this.firstQid = firstQid;
        this.secondQid = secondQid;
        this.sensitiveData = sensitiveData;
    }
        
    public EquivalenceClass(){
            
    }
        
    public String toString(){
           return "<["+firstQid.getMin()+","+firstQid.getMax()
                    +"], ["+secondQid.getMin()+","+secondQid.getMax()+"]> "
                    +sensitiveData;
    }
}
