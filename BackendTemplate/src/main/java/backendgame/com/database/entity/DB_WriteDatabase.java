package backendgame.com.database.entity;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;

public class DB_WriteDatabase extends DB_ReadDatabase{
    public String columnName;
    public int indexDescribe;
    public byte operator;
//    public byte Type;
    public int size;
//    public Object value;
    
    
    public void writeMessage(MessageSending messageSending) {
        messageSending.writeString(columnName);
        messageSending.writeInt(indexDescribe);
        messageSending.writeByte(operator);
        messageSending.writeByte(Type);
        messageSending.writeInt(size);
        
		if(0<Type && Type<10)
			messageSending.writeBoolean((boolean) value);
		else if(9<Type && Type<20)
			messageSending.writeByte((byte) value);
		else if(19<Type && Type<40)
			messageSending.writeShort((short) value);
		else if(39<Type && Type<60)
			messageSending.writeInt((int) value);
		else if(59<Type && Type<80)
			messageSending.writeFloat((float) value);
		else if(79<Type && Type<90)
			messageSending.writeLong((long) value);
		else if(89<Type && Type<100)
			messageSending.writeDouble((double) value);
		else if(Type==DBDefine_DataType.ByteArray || Type==DBDefine_DataType.LIST)
			messageSending.writeByteArray((byte[]) value);
		else if(Type==DBDefine_DataType.STRING)
			messageSending.writeString((String) value);
		else if(Type==DBDefine_DataType.IPV6) {
            if(value!=null && ((byte[])value).length==16)
            	messageSending.writeSpecialArray_WithoutLength((byte[])value);
            else
            	messageSending.writeSpecialArray_WithoutLength(new byte[16]);
		}else
			System.err.println("DB_WriteDatabase error → writeMessage ColumnName("+columnName+")	: " + DBDefine_DataType.getTypeName(Type));
    }
    
    public void readMessage(MessageReceiving messageReceiving) {
        columnName=messageReceiving.readString();
        indexDescribe=messageReceiving.readInt();
        operator=messageReceiving.readByte();
        Type=messageReceiving.readByte();
        size=messageReceiving.readInt();
        
		if(0<Type && Type<10)
			value = messageReceiving.readBoolean();
		else if(9<Type && Type<20)
			value = messageReceiving.readByte();
		else if(19<Type && Type<40)
			value = messageReceiving.readShort();
		else if(39<Type && Type<60)
			value = messageReceiving.readInt();
		else if(59<Type && Type<80)
			value = messageReceiving.readFloat();
		else if(79<Type && Type<90)
			value = messageReceiving.readLong();
		else if(89<Type && Type<100)
			value = messageReceiving.readDouble();
		else if(Type==DBDefine_DataType.ByteArray)
			value = messageReceiving.readByteArray();
		else if(Type==DBDefine_DataType.LIST)
			value = messageReceiving.readByteArray();
		else if(Type==DBDefine_DataType.STRING)
			value = messageReceiving.readString();
		else if(Type==DBDefine_DataType.IPV6)
			value = messageReceiving.readSpecialArray_WithoutLength(16);
		else
			System.err.println("DB_WriteDatabase error → readMessage(MessageReceiving messageReceiving)	ColumnName("+columnName+")	: " + DBDefine_DataType.getTypeName(Type));
    }
}
