package backendgame.com.database.table;

import java.io.IOException;
import java.io.RandomAccessFile;

import backendgame.com.database.DBProcess_Describe;

public abstract class BaseDBTable {
	public RandomAccessFile rfData;
	public RandomAccessFile rfBTree;
	public String path;
	
	
	public DBProcess_Describe des;
	public long offsetDescribe;
	public void initDes() {if(des==null)des=new DBProcess_Describe(rfData, offsetDescribe);}
	
	public long countRow() throws IOException {return rfBTree.length()/8;}
	public long getOffsetByIndex(long id) throws IOException {rfBTree.seek(id*8);return rfBTree.readLong();}
	
	public void close() {if(rfBTree!=null)try {rfBTree.close();rfBTree=null;} catch (IOException e) {e.printStackTrace();}if(rfData!=null)try {rfData.close();rfData=null;} catch (IOException e) {e.printStackTrace();}}
	public abstract void deleteFile();
}
