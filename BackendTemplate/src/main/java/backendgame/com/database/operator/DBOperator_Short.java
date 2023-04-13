package backendgame.com.database.operator;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DBOperator_Short extends DBOperator{
	

	
	
	private RandomAccessFile rfData;
	public DBOperator_Short(RandomAccessFile _rfData) {
		rfData=_rfData;
	}
	
	public short process(long offset, byte operator, short value) throws IOException {
		switch (operator) {
			case Addition:return add(offset, value);
			
			case AddPositive:return addPositive(offset, value);
				
			case AddInBound:return add_InBound(offset, value);
			
			default:
				break;
		}
		
		
		
	    return 0;
	}
	
	public short add(long offset, short value) throws IOException {
		rfData.seek(offset);
		value += rfData.readShort();
		
		rfData.seek(offset);
		rfData.writeShort(value);
		return value;
	}
	public short addPositive(long offset, short value) throws IOException {
		rfData.seek(offset);
		short oldValue = rfData.readShort();
		
		short result = (short) (oldValue + value);
		if(result<0)
			if(oldValue>0 && value>0)
				result=Short.MAX_VALUE;
			else
				result=0;
		
		rfData.seek(offset);
		rfData.writeShort(result);
		return result;
	}
	
	
	public short add_InBound(long offset, short value) throws IOException {
		rfData.seek(offset);
		short oldValue = rfData.readShort();
		short result = (short) (oldValue + value);
		
		if(oldValue>0 && value>0 && result<=0)
			result=Short.MAX_VALUE;
		
		if(oldValue<0 && value<0 && result>=0)
			result=Short.MIN_VALUE;
		
		rfData.seek(offset);
		rfData.writeShort(result);
		return result;
	}
}
