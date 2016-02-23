package fr.istic.sbd.mondrian;

public class Data
{
	private int id;
	private QID qid;
	private String str;

	public Data(int id, QID qid, String str)
	{
		this.id = id;
		this.qid = qid;
		this.str = str;
	}
	
	public String toString()
	{
		return this.id+" "+this.qid.getFirstQid()+" "+this.qid.getSecondQid()+" "+this.str;
	}
}

