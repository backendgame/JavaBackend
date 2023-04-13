package backendgame.com.database.table;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import backendgame.com.database.DBBase2Primary_Indexing;

public abstract class DBTable_2Primary extends BaseDBTable{
	public static final String DATA_EXTENSION = ".d2ps";
	public static final String INDEX_EXTENSION = ".d2pi";
	public static final String ROWID_EXTENSION = ".d2pr";
	public DBTable_2Primary(String _path) throws FileNotFoundException {
		path=_path;
		rfData = new RandomAccessFile(_path+DATA_EXTENSION, "rw");
		rfBTree = new RandomAccessFile(_path+INDEX_EXTENSION, "rw");
	}
	
	
	public DBBase2Primary_Indexing btree;
	public void initBtree() {if(btree==null)btree=new DBBase2Primary_Indexing(rfData,rfBTree);}
	public long querryOffset(long rowId) {return 0;}
	public long[] querryOffset(long[] rowId) {return null;}
	public long querryRowId(String credential, byte databaseId) {
		
		
		
		
		
		return 0;
	}
	public long[] querryRowId(String credential) {return null;}
	public long insert(Object credential, Object databaseId) throws IOException {
		initBtree();
		return btree.insertNewRow(credential, databaseId);
	}

	public long querryOffset(Object credential, Object databaseId) throws IOException {
		initBtree();
		return btree.querryOffset(credential, databaseId);
	}
	
	public long[] querryOffset(Object credential) {
		return null;
	}
}
