package backendgame.com.database.indexing;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DBIndexType_Integer extends DBBaseType{
	
	public DBIndexType_Integer(RandomAccessFile _rfData, Object object) {
		rfData=_rfData;
		int value = (int) object;
		data=new byte[4];
		data[0]=(byte)(value>>>24);
		data[1]=(byte)(value>>>16);
		data[2]=(byte)(value>>>8);
		data[3]=(byte)value;
		dataCompare=new byte[4];
	}
	
	public DBIndexType_Integer(RandomAccessFile _rfData) {
		rfData=_rfData;
	}

	@Override public byte compareQuerry() throws IOException {
		rfData.read(dataCompare);
		if(data[0]==dataCompare[0])
			if(data[1]==dataCompare[1])
				if(data[2]==dataCompare[2])
					if(data[3]==dataCompare[3])
						return 0;
					else
						return (byte) (data[3]>dataCompare[3]?1:-1);
				else
					return (byte) (data[2]>dataCompare[2]?1:-1);
			else
				return (byte) (data[1]>dataCompare[1]?1:-1);
		else
			return (byte) (data[0]>dataCompare[0]?1:-1);
	}

	@Override
	public void writeData(Object object) throws IOException {
		rfData.writeInt((int) object);
	}

	@Override
	public Object readData() throws IOException {
		return rfData.readInt();
	}

}
