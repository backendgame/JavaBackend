package backendgame.com.database;

import java.io.IOException;
import java.io.RandomAccessFile;

import backendgame.com.database.indexing.DBBaseType;

public class DBBase1Primary_Indexing extends DBBase_Indexing {
	public DBBase1Primary_Indexing() {
	
	}
	public DBBase1Primary_Indexing(RandomAccessFile _rfData,RandomAccessFile _rfBTree) {
		rfData=_rfData;
		rfBTree=_rfBTree;
	}
	
	
	private DBBaseType primaryKey;
	public long querryIndex(Object t) throws IOException {
		primaryKey=getType(t);
		if(rfBTree.length()==0)
			return -1;
		return querry_index();
	}
	public long querryOffset(Object t) throws IOException {
		long index=querryIndex(t);
		if(index==-1)
			return -1;
		rfBTree.seek(index*8);
		return rfBTree.readLong();
	}
	
	
	@Override public void compareQuerry() throws IOException {
		compare=primaryKey.compareQuerry();
	}


	public long insertNewRow(Object t) throws IOException {
		if(querryIndex(t)!=-1)
			return -1;
		long offset = rfData.length();
		indexing(offset);
		rfData.seek(offset);
		primaryKey.writeData(t);
		return offset;
	}

}
