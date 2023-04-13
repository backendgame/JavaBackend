package backendgame.com.database.operator;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DBOperator_Byte extends DBOperator{
	public static final byte SET = 1;
	
	public static final byte ADD = 10;
	
	public static final byte AddPositive = 20;
	
	public static final byte AddInBound = 50;
	
	
	private RandomAccessFile rfData;
	public DBOperator_Byte(RandomAccessFile _rfData) {
		rfData=_rfData;
	}
	
	public byte process(long offset, byte operator, byte value) throws IOException {
		switch (operator) {
			case SET:
				rfData.seek(offset);
				rfData.writeByte(value);
				return value;

			case ADD:return add(offset, value);
			
			case AddPositive:return addPositive(offset, value);
				
			case AddInBound:return add_InBound(offset, value);
			
			default:
				break;
		}
		
		
		
	    return 0;
	}
	
	public byte add(long offset, byte value) throws IOException {
		rfData.seek(offset);
		value = (byte) (value + rfData.readByte());
		
		rfData.seek(offset);
		rfData.writeByte(value);
		return value;
	}
	public byte addPositive(long offset, byte value) throws IOException {
		rfData.seek(offset);
		byte oldValue = rfData.readByte();
		byte result = (byte) (oldValue + value);
		if(result<0)
			if(oldValue>0 && value>0)
				result=Byte.MAX_VALUE;
			else
				result=0;
		
		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}
	
	
	public byte add_InBound(long offset, byte value) throws IOException {
		rfData.seek(offset);
		byte oldValue = rfData.readByte();
		byte result = (byte) (oldValue + value);
		
		if(oldValue>0 && value>0 && result<=0)
			result=Byte.MAX_VALUE;
		
		if(oldValue<0 && value<0 && result>=0)
			result=Byte.MIN_VALUE;
		
		rfData.seek(offset);
		rfData.writeByte(result);
		return result;
	}
}
