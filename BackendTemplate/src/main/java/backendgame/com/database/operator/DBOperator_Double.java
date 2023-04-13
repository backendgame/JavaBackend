package backendgame.com.database.operator;

import java.io.RandomAccessFile;

public class DBOperator_Double extends DBOperator{
	public RandomAccessFile rfData;
	public DBOperator_Double(RandomAccessFile _rfData) {
		rfData=_rfData;
	}
	public double process(long offsetData, byte operator, double object) {
		return 0;
	}
}
