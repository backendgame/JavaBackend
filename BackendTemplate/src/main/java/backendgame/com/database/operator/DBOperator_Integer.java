package backendgame.com.database.operator;

import java.io.RandomAccessFile;

public class DBOperator_Integer extends DBOperator{
	public RandomAccessFile rfData;
	public DBOperator_Integer(RandomAccessFile _rfData) {
		rfData=_rfData;
	}
	public int process(long offsetData, byte operator, int object) {
		return 0;
	}
}
