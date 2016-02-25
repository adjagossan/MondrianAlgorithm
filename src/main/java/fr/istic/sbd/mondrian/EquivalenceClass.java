package fr.istic.sbd.mondrian;


public class EquivalenceClass {
	private Range firstQid;
	private Range secondQid;

    public EquivalenceClass(Range firstQid, Range secondQid) {
        this.firstQid = firstQid;
        this.secondQid = secondQid;
    }
        
        public EquivalenceClass(){
            
        }
        
        public String toString(){
            return "<["+firstQid.getMin()+","+firstQid.getMax()+"], ["+secondQid.getMin()+","+secondQid.getMax()+"]>";
        }
}
