package backendgame.com.database;

import java.io.IOException;
import java.io.RandomAccessFile;

import backendgame.com.database.indexing.DBBaseType;
import backendgame.com.database.indexing.DBIndexType_Byte;
import backendgame.com.database.indexing.DBIndexType_ByteArray;
import backendgame.com.database.indexing.DBIndexType_Double;
import backendgame.com.database.indexing.DBIndexType_Float;
import backendgame.com.database.indexing.DBIndexType_Integer;
import backendgame.com.database.indexing.DBIndexType_Long;
import backendgame.com.database.indexing.DBIndexType_Short;
import backendgame.com.database.indexing.DBIndexType_String;

public abstract class DBBase_Indexing {
	public RandomAccessFile rfData;
	public RandomAccessFile rfBTree;
	

	protected byte compare;
	protected long l;
	
	public DBBase_Indexing() {}
	public DBBase_Indexing(RandomAccessFile _rfData,RandomAccessFile _rfBTree) {
		rfData=_rfData;
		rfBTree=_rfBTree;
	}
	
	protected DBBaseType getType(Object object) throws IOException {
		if(object instanceof Byte)
			return new DBIndexType_Byte(rfData, object);
		if(object instanceof Short)
			return new DBIndexType_Short(rfData, object);
		
		if(object instanceof Integer)
			return new DBIndexType_Integer(rfData, object);
		if(object instanceof Float)
			return new DBIndexType_Float(rfData, object);
		
		if(object instanceof Long)
			return new DBIndexType_Long(rfData, object);
		if(object instanceof Double)
			return new DBIndexType_Double(rfData, object);
		
		if(object instanceof String)
			return new DBIndexType_String(rfData, object);
		
		if(object instanceof byte[])
			return new DBIndexType_ByteArray(rfData, object);
		
		throw new IOException("Error type("+object.getClass().getTypeName()+") : "+object);
	}
	
//	public void removeIndex(long offsetOfData,byte[] dataQuerry) throws IOException{
//		
//	}
//	
//	
	public void indexing(long offsetPrimary) throws IOException {//Phải querry trước khi index để có biến l
		long lengBTree = rfBTree.length();
		if(lengBTree==0) {
			rfBTree.seek(0);
			rfBTree.writeLong(offsetPrimary);
			return;
		}
		
		byte[] _data;
		if(compare<0) {//l luôn được kiểm tra cuối cùng
			if(l*8==lengBTree) {
				rfBTree.seek(lengBTree);
				rfBTree.writeLong(offsetPrimary);
			}else {
				_data=new byte[(int) (lengBTree - l*8)];
				rfBTree.seek(l*8);
				rfBTree.read(_data);
				
				rfBTree.seek(l*8);
				rfBTree.writeLong(offsetPrimary);
				rfBTree.write(_data);
			}
		}else {//S nằm bên phải l
			l++;//Dịch qua phải 1 vị trí
			if(l*8==lengBTree) {//lúc khởi tạo thì r = length/8 -1
				rfBTree.seek(lengBTree);
				rfBTree.writeLong(offsetPrimary);
			}else {
				_data=new byte[(int) (lengBTree - l*8)];
				rfBTree.seek(l*8);
				rfBTree.read(_data);
				
				rfBTree.seek(l*8);
				rfBTree.writeLong(offsetPrimary);
				rfBTree.write(_data);
			}
		}
	}
	

	
	protected long querry_index() throws IOException {
		if(rfBTree.length()==0)
			return -1;
		
		l = 0;
		long r = rfBTree.length()/8 - 1;//r là nơi sẽ indexData mới
		long m;
		while (l < r) {
			m = (l + r) / 2;
			
			rfBTree.seek(m*8);
			rfData.seek(rfBTree.readLong());
			compareQuerry();
			if(compare==0)
				return m;
			
			if(compare>0)
				l = m + 1;
			else
				r = m - 1;
		}
		
		rfBTree.seek(l*8);
		rfData.seek(rfBTree.readLong());
		compareQuerry();
		if(compare==0)
			return l;

		return -1;
	}
	
	public abstract void compareQuerry() throws IOException;
}
