package backendgame.com.database.indexing;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DBIndexType_Byte extends DBBaseType{
	
	private byte value;
	public DBIndexType_Byte(RandomAccessFile _rfData, Object object) {
		rfData=_rfData;
		value=(byte) object;
	}
	
	public DBIndexType_Byte(RandomAccessFile _rfData) {
		rfData=_rfData;
	}

	@Override public byte compareQuerry() throws IOException {
		return (byte) (value - rfData.readByte());
	}

	@Override public void writeData(Object object) throws IOException {
		rfData.writeByte((byte) object);
	}

	@Override public Object readData() throws IOException {
		return rfData.readByte();
	}

}
