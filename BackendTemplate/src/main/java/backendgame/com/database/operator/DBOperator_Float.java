package backendgame.com.database.operator;

import java.io.RandomAccessFile;

public class DBOperator_Float extends DBOperator{
	public RandomAccessFile rfData;
	public DBOperator_Float(RandomAccessFile _rfData) {
		rfData=_rfData;
	}
	public float process(long offsetData, byte operator, float object) {
		return 0;
	}
}
