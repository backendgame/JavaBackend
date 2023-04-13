package backendgame.com.database.operator;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DBOperator_Boolean extends DBOperator{

	
	
	private RandomAccessFile rfData;
	public DBOperator_Boolean(RandomAccessFile _rfData) {
		rfData=_rfData;
	}
	public boolean process(long offsetData, byte operator, boolean value) throws IOException {
		rfData.seek(offsetData);
		switch (operator) {
			case NOT:
				value = !rfData.readBoolean();
				rfData.seek(offsetData);
				rfData.writeBoolean(value);
				return value;
				
			default:
				break;
		}
		return true;
	}
}
