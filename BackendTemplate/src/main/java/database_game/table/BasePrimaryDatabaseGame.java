package database_game.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class BasePrimaryDatabaseGame extends BaseDatabaseGame {
	public RandomAccessFile rfBTree;
	
	
	
	
	public long countRow() throws IOException {return rfBTree.length()/8;}
	public long getOffsetByIndex(long id) throws IOException {rfBTree.seek(id*8);return rfBTree.readLong();}
	
	public void close() {if(rfBTree!=null)try {rfBTree.close();rfBTree=null;} catch (IOException e) {e.printStackTrace();}if(rfData!=null)try {rfData.close();rfData=null;} catch (IOException e) {e.printStackTrace();}}
	public abstract void deleteFile();

}
