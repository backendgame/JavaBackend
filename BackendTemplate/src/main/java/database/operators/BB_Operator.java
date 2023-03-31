package database.operators;

import java.io.IOException;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import database.DataType;
import database.DescribeTable;

public class BB_Operator {
	public long userId;
	public short index;
	public short ColumnId;
	public byte Operator;
	public byte Type;
	public short Size;
	public Object Value;
	
	public void trace() {
		System.out.println("userId("+userId+")	index("+index+")	ColumnId("+ColumnId+")	Operator("+Operator+")	Type("+Type+")	Size("+Size+")	Value("+Value+")");
	}
	
	public long verifyToGetOffset(DescribeTable describeTable) throws IOException {
		if(ColumnId==describeTable.ColumnId && Type==describeTable.Type && Size<=describeTable.Size)
			return describeTable.Offset;
		else
			return -1;
	}
	
	public void writeMessage(MessageSending messageSending) {
		messageSending.writeLong(userId);
		messageSending.writeShort(index);
		messageSending.writeShort(ColumnId);
		messageSending.writeByte(Operator);
		messageSending.writeByte(Type);
		switch (Type) {
			case DataType.BOOLEAN:
				messageSending.writeBoolean((boolean) Value);
				break;
			case DataType.BYTE:
			case DataType.STATUS:
			case DataType.PERMISSION:
			case DataType.AVARTAR:
				messageSending.writeByte((byte) Value);
				break;
				
			case DataType.SHORT:
				messageSending.writeShort((short) Value);
				break;
				
			case DataType.INTEGER:
				messageSending.writeInt((int) Value);
				break;
			case DataType.FLOAT:
				messageSending.writeFloat((float) Value);
				break;
				
			case DataType.LONG:
				messageSending.writeLong((long) Value);
				break;
			case DataType.DOUBLE:
				messageSending.writeDouble((double) Value);
				break;
			case DataType.STRING:
				messageSending.writeString((String) Value);
				break;
			case DataType.TIMEMILI:
				messageSending.writeLong((long) Value);
				break;
			case DataType.USER_ID:
				messageSending.writeLong((long) Value);
				break;
				
			case DataType.LIST:
			case DataType.BINARY:
				messageSending.writeShort(Size);
				messageSending.writeCopyData((byte[]) Value);
				break;
			default:break;
		}
	}
	
	public void readMessageOperator(MessageReceiving messageReceiving) {
		userId=messageReceiving.readLong();
		index=messageReceiving.readShort();
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
				Value = messageReceiving.readByteArray(Size);
				break;
			default:break;
		}
	}
	
	public boolean validate() {
		return userId>0 && index>-1 && ColumnId!=0;
	}
}
