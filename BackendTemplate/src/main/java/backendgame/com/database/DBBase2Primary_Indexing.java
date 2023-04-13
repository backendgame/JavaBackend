package backendgame.com.database;

import java.io.IOException;
import java.io.RandomAccessFile;

import backendgame.com.database.entity.DB2PrimaryKey;
import backendgame.com.database.indexing.DBBaseType;
import backendgame.com.database.indexing.DBIndexType_Byte;
import backendgame.com.database.indexing.DBIndexType_ByteArray;
import backendgame.com.database.indexing.DBIndexType_Double;
import backendgame.com.database.indexing.DBIndexType_Float;
import backendgame.com.database.indexing.DBIndexType_Integer;
import backendgame.com.database.indexing.DBIndexType_Long;
import backendgame.com.database.indexing.DBIndexType_Short;
import backendgame.com.database.indexing.DBIndexType_String;

public class DBBase2Primary_Indexing extends DBBase_Indexing {

	public DBBase2Primary_Indexing() {}
	public DBBase2Primary_Indexing(RandomAccessFile _rfData,RandomAccessFile _rfBTree) {rfData=_rfData;rfBTree=_rfBTree;}
	
	
	private DBBaseType hashKey,rangeKey;
	public long querryOffset(Object t,Object k) throws IOException {
		long index=querryIndex(t,k);
		if(index==-1)
			return -1;
		rfBTree.seek(index*8);
		return rfBTree.readLong();
	}
	public long querryIndex(Object t,Object k) throws IOException {
		hashKey=getType(t);
		rangeKey=getType(k);
		if(rfBTree.length()==0)
			return -1;
		return querry_index();
	}
	
	
	private void seekToIndex(long index) throws IOException {
		rfBTree.seek(index*8);
		rfData.seek(rfBTree.readLong());
	}
	public long[] querryOffset(Object t) throws IOException {
		long[] result=querryIndex(t);
		if(result!=null) {
			int length = result.length;
			for(int i=0;i<length;i++) {
				rfBTree.seek(result[i]*8);
				result[i]=rfBTree.readLong();
			}
		}
		return result;
	}
	public long[] querryIndex(Object t) throws IOException {
		DBBase1Primary_Indexing querry = new DBBase1Primary_Indexing(rfData,rfBTree);
		long btree = querry.querryIndex(t);
		
		if(btree==-1)
			return null;
		
		long left = btree;
		while(left-1>-1) {
			seekToIndex(left-1);
			querry.compareQuerry();
			if(querry.compare==0)
				left--;
			else
				break;
		}
		
		long countAccount = rfBTree.length()/8;
		long right = btree;

		while(right+1<countAccount) {
			seekToIndex(right+1);
			querry.compareQuerry();
			if(querry.compare==0)
				right++;
			else
				break;
		}
		
		int size = (int) (right-left+1);
		long[] result = new long[size];
		for(int i=0;i<size;i++)
			result[i] = left+i;
		
		return result;
	}
	
	
	
	
	
	
	
	
	@Override public void compareQuerry() throws IOException {
		compare=hashKey.compareQuerry();
		if(compare==0)
			if(rfData.readBoolean())////////////////////////////////////////////////////
				if(rangeKey==null)
					compare=1;
				else
					compare=rangeKey.compareQuerry();
			else
				compare = (byte) (rangeKey==null?0:-1);
	}

	public long insertNewRow(Object t,Object k) throws IOException {
		if(querryOffset(t,k)!=-1)
			return -1;
		
		long offset = rfData.length();
		indexing(offset);
		rfData.seek(offset);
		hashKey.writeData(t);
		if(rangeKey==null)
			rfData.writeBoolean(false);/////////////////////////////////////////////////
		else {
			rfData.writeBoolean(true);//////////////////////////////////////////////////
			rangeKey.writeData(k);
		}
		return offset;
	}
	
//	public DoubleString readKey(long offset, Class<?> _hashKey, Class<?> _rangeKey) throws IOException {
//		
//	}
	
	protected DBBaseType getTypeByClass(Class<?> object) throws IOException {
		if(object == Byte.class)
			return new DBIndexType_Byte(rfData);
		if(object == Short.class)
			return new DBIndexType_Short(rfData);
		
		if(object == Integer.class)
			return new DBIndexType_Integer(rfData);
		if(object == Float.class)
			return new DBIndexType_Float(rfData);
		
		if(object == Long.class)
			return new DBIndexType_Long(rfData);
		if(object == Double.class)
			return new DBIndexType_Double(rfData);
		
		if(object == String.class)
			return new DBIndexType_String(rfData);
		
		if(object == byte[].class)
			return new DBIndexType_ByteArray(rfData);
		
		throw new IOException("Error type("+object.getClass().getTypeName()+") : "+object);
	}
	
	public DB2PrimaryKey getKey(long offset, Class<?> _hashKey, Class<?> _rangeKey) throws IOException {
		DB2PrimaryKey doubleString=new DB2PrimaryKey();
		rfData.seek(offset);
		doubleString.hashKey = getTypeByClass(_hashKey).readData();
		if(rfData.readBoolean())
			doubleString.rangeKey = getTypeByClass(_rangeKey).readData();
		return doubleString;
	}
}
