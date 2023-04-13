package backendgame.com.database.entity;

public class DB2Primary_Credential {
	public String credential;
	public byte databaseId;
	public void trace() {
		System.out.println("Credential("+credential+")	databaseId("+databaseId+")");
	}
}
