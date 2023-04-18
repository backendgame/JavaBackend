package backendgame.com.database.operator;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DBOperator_Short extends DBOperator{
	private RandomAccessFile rfData;
	public DBOperator_Short(RandomAccessFile _rfData) {
		rfData=_rfData;
	}
	
	public short process(short offset, byte operator, short value) throws IOException {
		switch (operator) {
			case Addition:return add(offset, value);
			case AddPositive:return addPositive(offset, value);
			case AddInBound:return add_InBound(offset, value);
			case Multiplication: return multiplication(offset, value);
			case Division: return division(offset, value);
			case Modulus: return modulus(offset, value);
			case AND: return and(offset, value);
			case OR: return or(offset, value);
			case NOT: return not(offset, value);
			case XOR: return xor(offset, value);
			case NAND: return nand(offset, value);
			case LeftShift: return leftShift(offset, value);
			case RightShift: return rightShift(offset, value);
			default:
				break;
		}
		return 0;
	}

	public short add(short offset, short value) throws IOException {
		rfData.seek(offset);
		value = (short) (value + rfData.readShort());

		rfData.seek(offset);
		rfData.writeShort(value);
		return value;
	}
	public short addPositive(short offset, short value) throws IOException {
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


	public short add_InBound(short offset, short value) throws IOException {
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
	public short multiplication(short offset, short value) throws IOException {
		rfData.seek(offset);
		short oldValue = rfData.readShort();
		short result = (short) (oldValue * value);

		if(oldValue>0 && value>0 && result<=0)
			result=Short.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Short.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeShort(result);
		return result;
	}
	public short division(short offset, short value) throws IOException {
		rfData.seek(offset);
		short oldValue = rfData.readShort();
		short result = (short) (oldValue / value);

		if(oldValue>0 && value>0 && result<=0)
			result=Short.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Short.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeShort(result);
		return result;
	}
	public short modulus(short offset, short value) throws IOException {
		rfData.seek(offset);
		short oldValue = rfData.readShort();
		short result = (short) (oldValue % value);

		if(oldValue>0 && value>0 && result<=0)
			result=Short.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Short.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeShort(result);
		return result;
	}

	public short and(short offset, short value) throws IOException {
		rfData.seek(offset);
		short oldValue = rfData.readShort();
		short result = (short) (oldValue & value);

		if(oldValue>0 && value>0 && result<=0)
			result=Short.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Short.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeLong(result);
		return result;
	}
	public short or(short offset, short value) throws IOException {
		rfData.seek(offset);
		short oldValue = rfData.readShort();
		short result = (short) (oldValue | value);

		if(oldValue>0 && value>0 && result<=0)
			result=Short.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Short.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeLong(result);
		return result;
	}
	public short not(short offset, short value) throws IOException {
		rfData.seek(offset);
		short result = (short) (~value);

		if(value>0 && result<=0)
			result=Short.MAX_VALUE;

		if(value<0 && result>=0)
			result=Short.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeLong(result);
		return result;
	}
	public short xor(short offset, short value) throws IOException {
		rfData.seek(offset);
		short oldValue = rfData.readShort();
		short result = (short) (oldValue ^ value);

		if(oldValue>0 && value>0 && result<=0)
			result=Short.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Short.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeLong(result);
		return result;
	}
	public short nand(short offset, short value) throws IOException {
		rfData.seek(offset);
		short oldValue = rfData.readShort();
		short result = (short) (~(oldValue & value));

		if(oldValue>0 && value>0 && result<=0)
			result=Short.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Short.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeLong(result);
		return result;
	}
	public short leftShift(short offset, short value) throws IOException {
		rfData.seek(offset);
		short oldValue = rfData.readShort();
		short result = (short) (oldValue << value);

		if(oldValue>0 && value>0 && result<=0)
			result=Short.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Short.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeLong(result);
		return result;
	}
	public short rightShift(short offset, short value) throws IOException {
		rfData.seek(offset);
		short oldValue = rfData.readShort();
		short result = (short) (oldValue >> value);

		if(oldValue>0 && value>0 && result<=0)
			result=Short.MAX_VALUE;

		if(oldValue<0 && value<0 && result>=0)
			result=Short.MIN_VALUE;

		rfData.seek(offset);
		rfData.writeLong(result);
		return result;
	}
}
