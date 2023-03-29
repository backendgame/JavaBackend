package gameonline.rest.database.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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
	
	public static long getLength(byte dataType) throws Exception {
		switch (dataType) {
			case BOOLEAN:return 1;
			case BYTE:return 1;
			case STATUS:return 1;
			case PERMISSION:return 1;
			case AVARTAR:return 1;
			
			case SHORT:return 2;
			
			case INTEGER:return 4;
			case FLOAT:return 4;

			case LONG:return 8;
			case DOUBLE:return 8;
			case STRING:return 8;
			case TIMEMILI:return 8;
			case USER_ID:return 8;
			
			case BINARY:throw new Exception("User Defind BINARY size");
			case LIST:throw new Exception("User Defind LIST size");
			default:throw new Exception("DataType not Exist");
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////
	public byte type;
	public short size;
	//////////////////////////////////////////////////////////////////////////////////////
	private static void showAllStatic() throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = DataType.class.getDeclaredFields();
		for(Field f:fields)
			if(Modifier.isStatic(f.getModifiers()))
				System.out.println(f.getName() + " = " + f.get(null));
		
	}
	private static void showAllPublic() throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = DataType.class.getFields();
		for(Field f:fields)
//			if(Modifier.isPublic(f.getModifiers()))
			if(Modifier.isStatic(f.getModifiers())==false)
				System.out.println(f.getName() + " = " + f.getType());
		
	}
	//////////////////////////////////////////////////////////////////////////////////////
	public static void ShowAll() {
		try {
			showAllStatic();
			System.out.println("*************************");
			showAllPublic();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
