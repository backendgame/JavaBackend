package database;

public class DataType {
	public static final byte BOOLEAN	= 1;
	public static final byte BYTE 		= 10;
	public static final byte STATUS		= 11;
	public static final byte PERMISSION	= 12;
	public static final byte AVARTAR	= 13;
	
	public static final byte SHORT 		= 20;
	
	public static final byte INTEGER 	= 40;
	public static final byte FLOAT 		= 41;
	
	public static final byte LONG 		= 80;
	public static final byte DOUBLE		= 81;
	public static final byte STRING		= 82;
	public static final byte TIMEMILI	= 83;
	public static final byte USER_ID	= 84;
	
	public static final byte BINARY		= 100;
	public static final byte LIST		= 101;
	//////////////////////////////////////////////////////////////////////////////////////
	public byte type;
	public short size;
	//////////////////////////////////////////////////////////////////////////////////////
	public DataType(byte _type, short _size) {
		type = _type;
		size = _size;
	}
	
}
