package database.operators;

import gameonline.rest.database.model.DataType;
import richard.MessageReceiving;
import richard.MessageSending;

public class BB_Operator {
    public long userId;
    public short ColumnIndex;
    public short ColumnId;
    public byte Type;
    public short Size;
    public byte Operator;
    public Object Value;
    
    public void writeMessage(MessageSending messageSending) {
        messageSending.writeLong(userId);
        messageSending.writeshort(ColumnIndex);
        messageSending.writeshort(ColumnId);
        messageSending.writeByte(Operator);
        messageSending.writeByte(Type);
        switch (Type) {
            case DataType.BOOLEAN:
                messageSending.writeBoolean(Boolean.parseBoolean(Value.toString()));
                break;
            case DataType.BYTE:
            case DataType.STATUS:
            case DataType.PERMISSION:
            case DataType.AVARTAR:
                messageSending.writeByte(Byte.parseByte(Value.toString()));
                break;
                
            case DataType.SHORT:
                messageSending.writeshort(Short.parseShort(Value.toString()));
                break;
                
            case DataType.INTEGER:
                messageSending.writeInt(Integer.parseInt(Value.toString()));
                break;
            case DataType.FLOAT:
                messageSending.writeFloat(Float.parseFloat(Value.toString()));
                break;
                
            case DataType.LONG:
                messageSending.writeLong(Long.parseLong(Value.toString()));
                break;
            case DataType.DOUBLE:
                messageSending.writeDouble(Double.parseDouble(Value.toString()));
                break;
            case DataType.STRING:
                messageSending.writeString((String) Value);
                break;
            case DataType.TIMEMILI:
            	messageSending.writeLong(Long.parseLong(Value.toString()));
                break;
            case DataType.USER_ID:
            	messageSending.writeLong(Long.parseLong(Value.toString()));
                break;
                
            case DataType.LIST:
            case DataType.BINARY:
                messageSending.writeshort(Size);
                messageSending.writeCopyData((byte[]) Value);
                break;
            default:break;
        }
    }
    
    public void readMessageOperator(MessageReceiving messageReceiving) {
        userId=messageReceiving.readLong();
        ColumnIndex=messageReceiving.readShort();
        ColumnId=messageReceiving.readShort();
        Operator=messageReceiving.readByte();
        Type=messageReceiving.readByte();
        switch (Type) {
            case DataType.BOOLEAN:
                Size=1;
                Value = messageReceiving.readBoolean();
                break;
            case DataType.BYTE:
            case DataType.STATUS:
            case DataType.PERMISSION:
            case DataType.AVARTAR:
                Size=1;
                Value = messageReceiving.readByte();
                break;
                
            case DataType.SHORT:
                Size=2;
                Value = messageReceiving.readShort();
                break;
                
            case DataType.INTEGER:
                Size=4;
                Value = messageReceiving.readInt();
                break;
            case DataType.FLOAT:
                Size=4;
                Value = messageReceiving.readFloat();
                break;
                
            case DataType.LONG:
                Size=8;
                Value = messageReceiving.readLong();
                break;
            case DataType.DOUBLE:
                Size=8;
                Value = messageReceiving.readDouble();
                break;
            case DataType.STRING:
                Size=8;
                Value = messageReceiving.readString();
                break;
            case DataType.TIMEMILI:
                Size=8;
                Value = messageReceiving.readLong();
                break;
            case DataType.USER_ID:
                Size=8;
                Value = messageReceiving.readLong();
                break;
                
            case DataType.LIST:
            case DataType.BINARY:
                Size = messageReceiving.readShort();
                Value = messageReceiving.readArrayByte(Size);
                break;
            default:break;
        }
    }
    
    public boolean validate() {
        return userId>0 && ColumnIndex>-1;
    }
}
