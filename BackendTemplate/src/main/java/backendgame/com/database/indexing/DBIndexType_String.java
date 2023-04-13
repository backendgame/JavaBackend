package backendgame.com.database.indexing;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DBIndexType_String extends DBBaseType{
	private short strLength;
	private short lengthCompare;
	public DBIndexType_String(RandomAccessFile _rfData, Object object) {
		rfData=_rfData;
		String str=(String) object;
		data=str.getBytes();
		strLength=(short) data.length;
		dataCompare=new byte[strLength];
	}
	
	public DBIndexType_String(RandomAccessFile _rfData) {
		rfData=_rfData;
	}

	@Override public byte compareQuerry() throws IOException {
		lengthCompare=rfData.readShort();
		if(strLength==lengthCompare) {
			rfData.read(dataCompare);
			
			for(short i=0;i<strLength;i++)
				if(data[i]!=dataCompare[i])
					return (byte) (data[i]>dataCompare[i]?1:-1);
			return 0;
		}else
			return (byte) (strLength>lengthCompare?1:-1);
	}

	@Override
	public void writeData(Object object) throws IOException {
		rfData.writeUTF((String) object);		
	}

	@Override
	public Object readData() throws IOException {
		return rfData.readUTF();
	}

}
