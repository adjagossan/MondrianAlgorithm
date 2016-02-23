package fr.istic.sbd.mondrian;


public class QID {
	private int firstQid;
	private int secondQid;
	
	public QID(int firstQid, int secondQid){
		this.firstQid = firstQid;
		this.secondQid = secondQid;
	}

	public int getFirstQid() {
		return firstQid;
	}

	public void setFirstQid(int firstQid) {
		this.firstQid = firstQid;
	}

	public int getSecondQid() {
		return secondQid;
	}

	public void setSecondQid(int secondQid) {
		this.secondQid = secondQid;
	}
}
