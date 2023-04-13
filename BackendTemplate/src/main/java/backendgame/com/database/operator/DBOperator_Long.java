package backendgame.com.database.operator;

import java.io.RandomAccessFile;

public class DBOperator_Long extends DBOperator{
	public RandomAccessFile rfData;
	public DBOperator_Long(RandomAccessFile _rfData) {
		rfData=_rfData;
	}
	public long process(long offsetData, byte operator, long object) {
		return 0;
	}
}
