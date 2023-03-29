package database.operators;

import java.io.IOException;
import java.io.RandomAccessFile;

public class BB_Operators_Boolean {
	public static final byte Set				= 1;
	public static final byte AdditiveInverse	= 2;
	public static final byte LogicalNegation	= 3;
	
	public static final byte BitwiseNOT			= 20;
	public static final byte BitwiseAND			= 21;
	public static final byte BitwiseOR			= 22;
	public static final byte BitwiseXOR			= 23;
	public static final byte BitwiseLeftShift	= 24;
	public static final byte BitwiseRightShift	= 25;
	
	public static final byte Modulo				= 40;
	
	public static final byte Division			= 60;
	
	public static final byte Multiplication		= 80;
	
	public static final byte Add				= 100;
	public static final byte ADD_POSITIVE		= 101;
	public static final byte ADD_NEGATIVE		= 102;
	public static final byte ADD_MIN			= 103;
	public static final byte ADD_MAX			= 104;
	public static final byte ADD_MIN_MAX		= 105;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void process(RandomAccessFile rf, long offset, BB_Operator operator) throws IOException {
		switch (operator.Operator) {
			case Set:set(rf, offset, operator);break;
//			case AdditiveInverse:additiveInverse(rf, offset, messageReceiving, messageSending);break;
//			case LogicalNegation:logicalNegation(rf, offset, messageReceiving, messageSending);break;
//			
//			case BitwiseNOT:bitwiseNOT(rf, offset, messageReceiving, messageSending);break;
//			case BitwiseAND:bitwiseAND(rf, offset, messageReceiving, messageSending);break;
//			case BitwiseOR:bitwiseOR(rf, offset, messageReceiving, messageSending);break;
//			case BitwiseXOR:bitwiseXOR(rf, offset, messageReceiving, messageSending);break;
//			case BitwiseLeftShift:bitwiseLeftShift(rf, offset, messageReceiving, messageSending);break;
//			case BitwiseRightShift:bitwiseRightShift(rf, offset, messageReceiving, messageSending);break;
//			
//			case Modulo:modulo(rf, offset, messageReceiving, messageSending);break;
//			
//			case Division:division(rf, offset, messageReceiving, messageSending);break;
//			
//			case Multiplication:multiplication(rf, offset, messageReceiving, messageSending);break;
			
//			case Add:add(rf, offset, messageReceiving, messageSending);break;
			default:throw new IOException("DataType Operators Error!");
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public void set(RandomAccessFile rf, long offset, BB_Operator operator) throws IOException {
		rf.seek(offset);
		rf.writeBoolean((boolean) operator.Value);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private BB_Operators_Boolean() {}
	private static BB_Operators_Boolean instance=null;
	public static final BB_Operators_Boolean gI() {
		if(instance==null) {
			instance = new BB_Operators_Boolean();
		}
		return instance;
	}
}
