package backendgame.com.database.indexing;

import java.io.IOException;
import java.io.RandomAccessFile;

public abstract class DBBaseType {
	public RandomAccessFile rfData;
	
	public byte[] data,dataCompare;
	
	public abstract Object readData() throws IOException;
	public abstract void writeData(Object object) throws IOException;
	public abstract byte compareQuerry() throws IOException;
}
