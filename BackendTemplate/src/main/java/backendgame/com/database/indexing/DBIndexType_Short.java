package backendgame.com.database.indexing;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DBIndexType_Short extends DBBaseType{
	
	public DBIndexType_Short(RandomAccessFile _rfData, Object object) {
		rfData=_rfData;
		short value = (short) object;
		data=new byte[2];
		data[0] = (byte) (value>>>8);
		data[1]	= (byte) value;
		dataCompare=new byte[2];
	}
	
	public DBIndexType_Short(RandomAccessFile _rfData) {
		rfData=_rfData;
	}

	@Override public byte compareQuerry() throws IOException {
		rfData.read(dataCompare);
		if(data[0]==dataCompare[0])
			if(data[1]==dataCompare[1])
				return 0;
			else
				return (byte) (data[1]>dataCompare[1]?1:-1);
		else
			return (byte) (data[0]>dataCompare[0]?1:-1);
	}

	@Override
	public void writeData(Object object) throws IOException {
		rfData.writeShort((short) object);
	}

	@Override public Object readData() throws IOException {
		return rfData.readShort();
	}

}
