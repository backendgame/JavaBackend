package backendgame.com.database.indexing;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DBIndexType_ByteArray extends DBBaseType{
	public int length,lengthCompare;
	
	public DBIndexType_ByteArray(RandomAccessFile _rfData, Object object) {
		rfData=_rfData;
		String str=(String) object;
		data=str.getBytes();
		length=data.length;
		dataCompare=new byte[length];
	}
	
	public DBIndexType_ByteArray(RandomAccessFile _rfData) {
		rfData=_rfData;
	}

	@Override public byte compareQuerry() throws IOException {
		lengthCompare=rfData.readInt();
		if(length==lengthCompare) {
			rfData.read(dataCompare);
			
			for(int i=0;i<length;i++)
				if(data[i]!=dataCompare[i])
					return (byte) (data[i]>dataCompare[i]?1:-1);
			return 0;
		}else
			return (byte) (length>lengthCompare?1:-1);
	}

	@Override
	public void writeData(Object object) throws IOException {
		rfData.write((byte[])object);		
	}

	@Override
	public Object readData() throws IOException {
		length=rfData.readInt();
		if(length>0) {
			byte[] result=new byte[length];
			rfData.read(result);
			return result;
		}else
			return null;
	}

}
