package server.config;

public class PATH {
	public static final String DATABASE_FOLDER = "./BackendGame";
	
	public static final String UserData = "User.data";
	public static final String StringData = "String.data";
	public static final String StringIndex = "String.index";
	
	
	
//	public static final String SubTable = "SubTable";
	
	public static String dbStringName(short tableId) {return PATH.DATABASE_FOLDER+"/"+tableId+"/string";}
}
