package database_game;

import begame.onehit.web.dto.DTO_Create_Table;

public class DatabaseManager {
	private DatabaseManager() {
		
	}
	
	
	
	public DTO_Create_Table createTable(long adminId) {
		
		return null;
	}
	
	
	private static DatabaseManager instance=null;
	public static DatabaseManager gI() {
		if(instance==null) {
			instance=new DatabaseManager();
		}
		return instance;
	}

}
