package fr.istic.sbd.mondrian;

public class Data
{
	private int id;
	private QID qid;
	private String str;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public QID getQid() {
        return qid;
    }

    public void setQid(QID qid) {
        this.qid = qid;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

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

