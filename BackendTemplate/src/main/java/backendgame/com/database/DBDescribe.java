package backendgame.com.database;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;

public class DBDescribe {
	public int OffsetRow;			//4 : biến local này giúp xác định vị trí của DefaultValue trên Database.
	public int ViewId;				//4
	public byte State;				//1
	public byte Permission;			//1
	public int Size;				//4
	public byte Type;				//1
	public String ColumnName;		//Max
	protected byte[] DefaultData;		//8
	
	
	public void loadDefaultData(byte[] data) {
	    if(data==null) {
	        DefaultData = new byte[4];
	        return;
	    }
	    
	    int dataLength = data.length;
	    DefaultData = new byte[dataLength+4];
	    for(int i=0;i<dataLength;i++)
	        DefaultData[i+4]=data[i];
        DefaultData[0] = (byte)(dataLength>>>24);
        DefaultData[1] = (byte)(dataLength>>>16);
        DefaultData[2] = (byte)(dataLength>>>8);
        DefaultData[3] = (byte)dataLength;
	}
    public void loadDefaultData(double value) {
        long vDouble = Double.doubleToLongBits(value);
        DefaultData = new byte[8];
        DefaultData[0] = (byte) (vDouble >>> 56);
        DefaultData[1] = (byte) (vDouble >>> 48);
        DefaultData[2] = (byte) (vDouble >>> 40);
        DefaultData[3] = (byte) (vDouble >>> 32);
        DefaultData[4] = (byte) (vDouble >>> 24);
        DefaultData[5] = (byte) (vDouble >>> 16);
        DefaultData[6] = (byte) (vDouble >>> 8);
        DefaultData[7] = (byte) (vDouble >>> 0);
    }
    public void loadDefaultData(long value) {
        DefaultData = new byte[8];
        DefaultData[0] = (byte) (value >>> 56);
        DefaultData[1] = (byte) (value >>> 48);
        DefaultData[2] = (byte) (value >>> 40);
        DefaultData[3] = (byte) (value >>> 32);
        DefaultData[4] = (byte) (value >>> 24);
        DefaultData[5] = (byte) (value >>> 16);
        DefaultData[6] = (byte) (value >>> 8);
        DefaultData[7] = (byte) (value >>> 0);
    }
	public void loadDefaultData(float value) {
	    int vFloat = Float.floatToIntBits(value);
        DefaultData = new byte[4];
        DefaultData[0] = (byte)(vFloat>>>24);
        DefaultData[1] = (byte)(vFloat>>>16);
        DefaultData[2] = (byte)(vFloat>>>8);
        DefaultData[3] = (byte)vFloat;
    }
	public void loadDefaultData(int value) {
	    DefaultData = new byte[4];
	    DefaultData[0] = (byte)(value>>>24);
	    DefaultData[1] = (byte)(value>>>16);
	    DefaultData[2] = (byte)(value>>>8);
	    DefaultData[3] = (byte)value;
	}
	public void loadDefaultData(short value) {
	    DefaultData = new byte[2];
	    DefaultData[0] = (byte) (value>>>8);
	    DefaultData[1] = (byte) value;
	}
	public void loadDefaultData(byte value) {DefaultData = new byte[1];DefaultData[0] = value;}
	public void loadDefaultData(boolean value) {DefaultData = new byte[1];DefaultData[0] = (byte) (value?1:0);}
	public void fixSize() {if(Size<DefaultData.length)Size=DefaultData.length;}
	public void loadDefaultData(String value) {
		byte[] tmp=value.getBytes();
		int strLength = tmp.length;
		DefaultData=new byte[strLength+2];
		for(int i=0;i<strLength;i++)
			DefaultData[i+2]=tmp[i];
		DefaultData[0]	= (byte) (strLength>>>8);
		DefaultData[1]	= (byte) strLength;		
		if(Size<DefaultData.length)
			Size=DefaultData.length;
//        if(value==null){
//            DefaultData=new byte[2];
//            return;
//        }
//        int stringLenth = value.length();
//        int j = 0;
//        int k;
//        for (int n = 0; n < stringLenth; n++) {
//            k = value.charAt(n);
//            if ((k >= 1) && (k <= 127)) {
//                j++;
//            } else if (k > 2047) {
//                j += 3;
//            } else {
//                j += 2;
//            }
//        }
//        DefaultData = new byte[j * 2 + 2];
//        DefaultData[0] = ((byte)(j >>> 8 & 0xFF));
//        DefaultData[1] = ((byte)(j >>> 0 & 0xFF));
//        int count=2;
//        int i1 = 0;
//        for (i1 = 0; i1 < stringLenth; i1++) {
//            k = value.charAt(i1);
//            if ((k < 1) || (k > 127)) {
//                break;
//            }
//            DefaultData[(count++)] = ((byte) k);
//        }
//        while (i1 < stringLenth) {
//            k = value.charAt(i1);
//            if ((k >= 1) && (k <= 127)) {
//                DefaultData[(count++)] = ((byte) k);
//            } else if (k > 2047) {
//                DefaultData[(count++)] = ((byte) (0xE0 | k >> 12 & 0xF));
//                DefaultData[(count++)] = ((byte) (0x80 | k >> 6 & 0x3F));
//                DefaultData[(count++)] = ((byte) (0x80 | k >> 0 & 0x3F));
//            } else {
//                DefaultData[(count++)] = ((byte) (0xC0 | k >> 6 & 0x1F));
//                DefaultData[(count++)] = ((byte) (0x80 | k >> 0 & 0x3F));
//            }
//            i1++;
//        }
    }
	
	public void writeMessage(MessageSending messageSending) {
		messageSending.writeInt(OffsetRow);
		messageSending.writeInt(ViewId);
		messageSending.writeByte(State);
		messageSending.writeByte(Permission);
		messageSending.writeInt(Size);
		messageSending.writeByte(Type);
		messageSending.writeString(ColumnName);
		messageSending.writeByteArray(DefaultData);
	}
	public void readMessage(MessageReceiving messageReceiving) {
		OffsetRow=messageReceiving.readInt();
		ViewId=messageReceiving.readInt();
		State=messageReceiving.readByte();
		Permission=messageReceiving.readByte();
		Size=messageReceiving.readInt();
		Type=messageReceiving.readByte();
		ColumnName=messageReceiving.readString();
		DefaultData=messageReceiving.readByteArray();
	}
	
	public Object getDefaultData() {
        switch (Type) {
            case DBDefine_DataType.BOOLEAN:return DefaultData[0]!=0;
            
            case DBDefine_DataType.BYTE:
            case DBDefine_DataType.STATUS:
            case DBDefine_DataType.PERMISSION:
            case DBDefine_DataType.AVARTAR:
            	return DefaultData[0];
            
            case DBDefine_DataType.SHORT:
        		return toShort();
            
            case DBDefine_DataType.FLOAT:return Float.intBitsToFloat(toInt());
            case DBDefine_DataType.IPV4:
            case DBDefine_DataType.INTEGER:return toInt();
            
            case DBDefine_DataType.DOUBLE:Double.longBitsToDouble(toLong());
            case DBDefine_DataType.USER_ID:
            case DBDefine_DataType.TIMEMILI:
            case DBDefine_DataType.LONG:return toLong();
            
            case DBDefine_DataType.LIST:
            case DBDefine_DataType.ByteArray:
            	int size=DefaultData.length-4;
            	if(size==0)
            		return null;
            	byte[] arr=new byte[size];
            	for(int i=0;i<size;i++)
            		arr[i]=DefaultData[i+4];
            	return arr;
            
            case DBDefine_DataType.STRING:
            	if(DefaultData.length==2)
            		return "";
            	else {
            		int _length=DefaultData.length-2;
            		byte[] tmp=new byte[_length];
            		for(short i=0;i<_length;i++)
            			tmp[i]=DefaultData[i+2];
            		return new String(tmp,StandardCharsets.UTF_8);
            	}
            	
            case DBDefine_DataType.IPV6:return DefaultData;
            default:return "TYPE_NULL("+Type+")";
        }
    }
	
	private long toLong() {
		long l0 = DefaultData[0] & 0xFF;
		long l1 = DefaultData[1] & 0xFF;
		long l2 = DefaultData[2] & 0xFF;
		long l3 = DefaultData[3] & 0xFF;
		long l4 = DefaultData[4] & 0xFF;
		long l5 = DefaultData[5] & 0xFF;
		long l6 = DefaultData[6] & 0xFF;
		long l7 = DefaultData[7] & 0xFF;
		//currentReading+=8;
		
		long r0 = l0 << 56;
		long r1 = l1 << 48;
		long r2 = l2 << 40;
		long r3 = l3 << 32;
		long r4 = l4 << 24;
		long r5 = l5 << 16;
		long r6 = l6 << 8;
		long r7 = l7;
		return r0 + r1 + r2 + r3 + r4 + r5 + r6 + r7;
	}
	private int toInt() {
		int ch1 = DefaultData[0] & 0xFF;
		int ch2 = DefaultData[1] & 0xFF;
		int ch3 = DefaultData[2] & 0xFF;
		int ch4 = DefaultData[3] & 0xFF;
		return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
	}
	private short toShort() {
		int ch1 = DefaultData[0] & 0xFF;
		int ch2 = DefaultData[1] & 0xFF;
		return (short)((ch1 << 8) + (ch2 << 0));
	}
	public void trace() {
		trace("");
	}
	public void trace(String s) {
		if(Type==DBDefine_DataType.ByteArray || Type==DBDefine_DataType.LIST)
			System.out.println(s + ColumnName+"("+Size+")	" + DBDefine_DataType.getTypeName(Type)+"	OffsetRow("+OffsetRow+")	ViewId("+ViewId+")	State("+State+")	Permission("+Permission+")	:" + Arrays.toString((byte[])getDefaultData())+"	-->"+ Arrays.toString(DefaultData));
		else
			System.out.println(s + ColumnName+"("+Size+")	" + DBDefine_DataType.getTypeName(Type)+"	OffsetRow("+OffsetRow+")	ViewId("+ViewId+")	State("+State+")	Permission("+Permission+")	:" + getDefaultData());
	}
}
