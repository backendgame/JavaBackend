package database;

import backendgame.config.PATH;

public class SubTable {
	public static final long HEADER_LENGTH = 60;
	
	public static final byte SubType_SubUserData = 1;
	
	public static final byte SubType_Leaderboard = 20;
	
	public static final byte SubType_Tile_Binary = 50;
	public static final byte SubType_Tile_Row = 51;
	
	
	////////////////////////////////////////////////////////////////////////////////////////
	public static String getDirectory(short _tableId, short _subTableId) {
		return PATH.DATABASE_FOLDER + "/" + _tableId + "/" + _subTableId;
	}
	public static String getSubFile(short _tableId, short _subTableId) {
		return PATH.DATABASE_FOLDER + "/" + _tableId + "/" + _subTableId + "/Data";
	}
	public static String getDesFile(short _tableId, short _subTableId) {
		return PATH.DATABASE_FOLDER + "/" + _tableId + "/" + _subTableId + "/Data.des";
	}
	////////////////////////////////////////////////////////////////////////////////////////
}
