package backendgame.com.database.indexing;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DBIndexType_Long extends DBBaseType{
	
	public DBIndexType_Long(RandomAccessFile _rfData, Object object) {
		rfData=_rfData;
		long v =  (long) object;
		data=new byte[8];
		data[0] = (byte)(v >>> 56);
		data[1] = (byte)(v >>> 48);
		data[2] = (byte)(v >>> 40);
		data[3] = (byte)(v >>> 32);
		data[4] = (byte)(v >>> 24);
		data[5] = (byte)(v >>> 16);
		data[6] = (byte)(v >>>  8);
		data[7] = (byte)(v >>>  0);
		dataCompare=new byte[8];
	}
	
	public DBIndexType_Long(RandomAccessFile _rfData) {
		rfData=_rfData;
	}

	@Override public byte compareQuerry() throws IOException {
		rfData.read(dataCompare);
		if(data[0]==dataCompare[0])
			if(data[1]==dataCompare[1])
				if(data[2]==dataCompare[2])
					if(data[3]==dataCompare[3])
						if(data[4]==dataCompare[4])
							if(data[5]==dataCompare[5])
								if(data[6]==dataCompare[6])
									if(data[7]==dataCompare[7])
										return 0;
									else
										return (byte) (data[7]>dataCompare[7]?1:-1);
								else
									return (byte) (data[6]>dataCompare[6]?1:-1);
							else
								return (byte) (data[5]>dataCompare[5]?1:-1);
						else
							return (byte) (data[4]>dataCompare[4]?1:-1);
					else
						return (byte) (data[3]>dataCompare[3]?1:-1);
				else
					return (byte) (data[2]>dataCompare[2]?1:-1);
			else
				return (byte) (data[1]>dataCompare[1]?1:-1);
		else
			return (byte) (data[0]>dataCompare[0]?1:-1);
	}

	@Override public void writeData(Object object) throws IOException {
		rfData.writeLong((long) object);		
	}

	@Override
	public Object readData() throws IOException {
		return rfData.readLong();
	}

}
