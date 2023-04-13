package database_game;

import backendgame.com.database.DBDescribe;
import begame.onehit.web.dto.DTO_Create_Table;

public class DatabaseManager {
	public Object[] listLock;
	private DatabaseManager() {
		listLock=new Object[65536];
	}
	
	public void writeLongToDataFile(short tableId, long offset,long value) {
		
	}
	public void writeToDataFile(short tableId, long offset, byte[] data) {
		
	}
	
	public DTO_Create_Table createTable(long adminId, DBDescribe[] listDescribes, long tokenLifeTime) {
		
		return null;
	}
	
	
	private static DatabaseManager instance=null;
	public static DatabaseManager gI() {
		if(instance==null) {
			instance=new DatabaseManager();
		}
		return instance;
	}

	public boolean deleteDatabase(short tableId) {
		return false;
	}

}
