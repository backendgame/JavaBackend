package database_game.table;

public abstract class BaseDatabaseGame {
	public static final long Offset_TimeAvaiable		 	= 0;
	public static final long Offset_AdminId					= 8;//UserId-DynamoDB
	public static final long Offset_Permission	 			= 16;//1 byte
	public static final long Offset_LogoutId	 			= 17;//1 byte
	public static final long Offset_AccessKey			 	= 18;
	public static final long Offset_ReadKey				 	= 26;
	public static final long Offset_WriteKey			 	= 34;
	
	public static final long Offset_TimeCreate		 		= 42;
	public static final long Offset_Version					= 50;
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public long timeAvaiable;				//0
	public long adminId;					//8
	public byte permission;					//16
	public byte logoutId;					//17
	public long accessKey;					//18
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String INDEX_EXTENSION = ".index";
	public short tableId;
	public String path;
	
	

	
	public abstract void close();
	public abstract void deleteFile();
}
