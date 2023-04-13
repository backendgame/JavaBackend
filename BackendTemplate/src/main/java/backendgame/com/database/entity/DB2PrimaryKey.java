package backendgame.com.database.entity;

public class DB2PrimaryKey {
	public Object hashKey;
	public Object rangeKey;
	public void trace() {
		System.out.println("hashKey("+hashKey+")	rangeKey("+rangeKey+")");
	}
}
