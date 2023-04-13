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
        switch (Type) {
            case DBDefine_DataType.BOOLEAN:
                messageSending.writeBoolean((boolean) value);break;

            case DBDefine_DataType.BYTE:
            case DBDefine_DataType.STATUS:
            case DBDefine_DataType.PERMISSION:
            case DBDefine_DataType.AVARTAR:
                messageSending.writeByte((byte) value);break;

            case DBDefine_DataType.SHORT:
                messageSending.writeShort((short) value);break;

            case DBDefine_DataType.FLOAT:
                messageSending.writeFloat((float) value);break;
            case DBDefine_DataType.IPV4:
            case DBDefine_DataType.INTEGER:
                messageSending.writeInt((int) value);break;

            case DBDefine_DataType.DOUBLE:
                messageSending.writeDouble((double) value);break;

            case DBDefine_DataType.USER_ID:
            case DBDefine_DataType.TIMEMILI:
            case DBDefine_DataType.LONG:
                messageSending.writeLong((long) value);break;

            case DBDefine_DataType.ByteArray:
                messageSending.writeByteArray((byte[]) value);break;
            case DBDefine_DataType.LIST:
                messageSending.writeByteArray((byte[]) value);break;
            case DBDefine_DataType.STRING:
                messageSending.writeString((String) value);break;
            case DBDefine_DataType.IPV6:
                messageSending.writeByteArray((byte[]) value);break;

            default:
                System.err.println("Type error "+this.getClass().getSimpleName()+" → writeMessage "+columnName+"(col:"+indexDescribe+") : " + Type);
        }
    }
    
    public void readMessage(MessageReceiving messageReceiving) {
        columnName=messageReceiving.readString();
        indexDescribe=messageReceiving.readInt();
        operator=messageReceiving.readByte();
        Type=messageReceiving.readByte();
        size=messageReceiving.readInt();
        switch (Type) {
            case DBDefine_DataType.BOOLEAN:value = messageReceiving.readBoolean();break;

            case DBDefine_DataType.BYTE:
            case DBDefine_DataType.STATUS:
            case DBDefine_DataType.PERMISSION:
            case DBDefine_DataType.AVARTAR:value = messageReceiving.readByte();break;

            case DBDefine_DataType.SHORT:value = messageReceiving.readShort();break;

            case DBDefine_DataType.FLOAT:value = messageReceiving.readFloat();break;
            
            case DBDefine_DataType.IPV4:
            case DBDefine_DataType.INTEGER:value = messageReceiving.readInt();break;

            case DBDefine_DataType.DOUBLE:value = messageReceiving.readDouble();break;

            case DBDefine_DataType.USER_ID:
            case DBDefine_DataType.TIMEMILI:
            case DBDefine_DataType.LONG:value = messageReceiving.readLong();break;

            case DBDefine_DataType.STRING:value = messageReceiving.readString();break;
            
            case DBDefine_DataType.ByteArray:
            case DBDefine_DataType.LIST:value = messageReceiving.readByteArray();break;
            
            case DBDefine_DataType.IPV6:value = messageReceiving.readSpecialArray_WithoutLength(16);break;
            default:System.err.println("Database error DBProcess_Describe → process " + DBDefine_DataType.getTypeName(Type));break;
        }
    }
}
