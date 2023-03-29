package database;

import java.io.IOException;
import java.io.RandomAccessFile;

import bgcore.core.MessageReceiving;
import bgcore.core.MessageSending;
import database.table.DBString;

public class DescribeTable {//==>29
	public static final short BEGIN_OFFSET= 8;
	public static final short BEGIN_PASSWORD= 16;
	public static final short LENGTH_DESCRIBE = 21;//FieldName + Offset + Password + Type + Size → Value nằm ở UserId=0
	
	public String ColumnName;	//8
	public long Offset;			//8 : biến local này giúp xác định vị trí của DefaultValue trên Database. Không dùng để gởi Onehit
	public short ColumnId;		//2
	public byte Type;			//1
	public short Size;			//2
	public Object DefaultValue;	//8
	
	public DescribeTable() {}
	
	
	public boolean isSame(DescribeTable describeTable) {
		return ColumnId==describeTable.ColumnId && Type==describeTable.Type;
	}
	
	
	public DescribeTable(RandomAccessFile rf, DBString dbString) throws IOException {
		ColumnName = dbString.getStringByLocation(rf.readLong());
		Offset = rf.readLong();
		ColumnId = rf.readShort();
		Type = rf.readByte();
		Size = rf.readShort();
	}
	
	public void writeDescribeTable(RandomAccessFile rf, DBString dbString) throws IOException {
		rf.writeLong(dbString.getStringId(ColumnName));
		rf.writeLong(Offset);
		rf.writeShort(ColumnId);
		rf.writeByte(Type);
		rf.writeShort(Size);
	}
	
	public void readDefault(RandomAccessFile rf, DBString dbString) throws IOException{
		switch (Type) {
			case DataType.BOOLEAN:
				DefaultValue = rf.readBoolean();
				break;
			case DataType.BYTE:
			case DataType.STATUS:
			case DataType.PERMISSION:
			case DataType.AVARTAR:
				DefaultValue = rf.readByte();
				break;
	
			case DataType.SHORT:
				DefaultValue = rf.readShort();
				break;
				
			case DataType.INTEGER:
				DefaultValue = rf.readInt();
				break;
			case DataType.FLOAT:
				DefaultValue = rf.readFloat();
				break;
				
			case DataType.LONG:
				DefaultValue = rf.readLong();
				break;
			case DataType.DOUBLE:
				DefaultValue = rf.readDouble();
				break;
			case DataType.STRING:
				DefaultValue = dbString.getStringByLocation(rf.readLong());
				
				break;
			case DataType.TIMEMILI:
				DefaultValue = rf.readLong();
				break;
			case DataType.USER_ID:
				DefaultValue = rf.readLong();
				break;
				
			case DataType.LIST:
			case DataType.BINARY:
				byte[] _data=new byte[Size];
				rf.read(_data);
				DefaultValue=_data;
				break;
			default:break;
		}
	}
	public void writeDefaultValue(RandomAccessFile rf, DBString dbString) throws IOException{
		switch (Type) {
			case DataType.BOOLEAN:
				rf.writeBoolean((boolean) DefaultValue);
				break;
			case DataType.BYTE:
			case DataType.STATUS:
			case DataType.PERMISSION:
			case DataType.AVARTAR:
				rf.writeByte((byte)DefaultValue);
				break;
	
			case DataType.SHORT:
				rf.writeShort((short)DefaultValue);
				break;
				
			case DataType.INTEGER:
				rf.writeInt((int)DefaultValue);
				break;
			case DataType.FLOAT:
				rf.writeFloat((float) DefaultValue);
				break;
				
			case DataType.LONG:
				rf.writeLong((long) DefaultValue);
				break;
			case DataType.DOUBLE:
				rf.writeDouble((double) DefaultValue);
				break;
			case DataType.STRING:
				rf.writeLong(dbString.getStringId((String) DefaultValue));
				break;
			case DataType.TIMEMILI:
				rf.writeLong((long) DefaultValue);
				break;
			case DataType.USER_ID:
				rf.writeLong((long) DefaultValue);
				break;
				
			case DataType.LIST:
			case DataType.BINARY:
				if(DefaultValue==null)
					rf.write(new byte[Size]);
				else
					rf.write((byte[]) DefaultValue);
				break;
			default:break;
		}
	}
	
	
	public void writeMessage(MessageSending messageSending) {
		messageSending.writeString(ColumnName);
		messageSending.writeShort(ColumnId);
		messageSending.writeByte(Type);
		
		switch (Type) {
			case DataType.BOOLEAN:
				messageSending.writeBoolean((boolean) DefaultValue);
				break;
			case DataType.BYTE:
			case DataType.STATUS:
			case DataType.PERMISSION:
			case DataType.AVARTAR:
				messageSending.writeByte((byte) DefaultValue);
				break;
	
			case DataType.SHORT:
				messageSending.writeShort((short) DefaultValue);
				break;
				
			case DataType.INTEGER:
				messageSending.writeInt((int) DefaultValue);
				break;
			case DataType.FLOAT:
				messageSending.writeFloat((float) DefaultValue);
				break;
				
			case DataType.LONG:
				messageSending.writeLong((long) DefaultValue);
				break;
			case DataType.DOUBLE:
				messageSending.writeDouble((double) DefaultValue);
				break;
			case DataType.STRING:
				messageSending.writeString((String) DefaultValue);
				break;
			case DataType.TIMEMILI:
				messageSending.writeLong((long) DefaultValue);
				break;
			case DataType.USER_ID:
				messageSending.writeLong((long) DefaultValue);
				break;
				
			case DataType.LIST:
			case DataType.BINARY:
				messageSending.writeShort(Size);
				messageSending.writeCopyData((byte[]) DefaultValue);
				break;
			default:break;
		}
	}
	
	public void readMessage(MessageReceiving messageReceiving) {
		ColumnName = messageReceiving.readString();
		ColumnId=messageReceiving.readShort();
		Type=messageReceiving.readByte();
		
		switch (Type) {
			case DataType.BOOLEAN:
				Size=1;
				DefaultValue = messageReceiving.readBoolean();
				break;
			case DataType.BYTE:
			case DataType.STATUS:
			case DataType.PERMISSION:
			case DataType.AVARTAR:
				Size=1;
				DefaultValue = messageReceiving.readByte();
				break;
	
			case DataType.SHORT:
				Size=2;
				DefaultValue = messageReceiving.readShort();
				break;
				
			case DataType.INTEGER:
				Size=4;
				DefaultValue = messageReceiving.readInt();
				break;
			case DataType.FLOAT:
				Size=4;
				DefaultValue = messageReceiving.readFloat();
				break;
				
			case DataType.LONG:
				Size=8;
				DefaultValue = messageReceiving.readLong();
				break;
			case DataType.DOUBLE:
				Size=8;
				DefaultValue = messageReceiving.readDouble();
				break;
			case DataType.STRING:
				Size=8;
				DefaultValue = messageReceiving.readString();
				break;
			case DataType.TIMEMILI:
				Size=8;
				DefaultValue = messageReceiving.readLong();
				break;
			case DataType.USER_ID:
				Size=8;
				DefaultValue = messageReceiving.readLong();
				break;
				
			case DataType.LIST:
			case DataType.BINARY:
				Size=messageReceiving.readShort();
				DefaultValue=messageReceiving.readByteArray(Size);
				break;
			default:break;
		}
	}
	
	public void trace() {
		trace("");
	}
	public void trace(String s) {
		switch (Type) {
			case DataType.BOOLEAN:System.out.println(s + "BOOLEAN("+Size+")	" + ColumnName + "	" + ColumnId + "	" + Offset + "	" + DefaultValue);break;
			case DataType.BYTE:System.out.println(s + "BYTE("+Size+")	" + ColumnName + "	" + ColumnId + "	" + Offset + "	" + DefaultValue);break;
			case DataType.STATUS:System.out.println(s + "STATUS("+Size+")	" + ColumnName + "	" + ColumnId + "	" + Offset + "	" + DefaultValue);break;
			case DataType.PERMISSION:System.out.println(s + "PERMISSION("+Size+")	" + ColumnName + "	" + ColumnId + "	" + Offset + "	" + DefaultValue);break;
			case DataType.AVARTAR:System.out.println(s + "AVARTAR("+Size+")	" + ColumnName + "	" + ColumnId + "	" + Offset + "	" + DefaultValue);break;
	
			case DataType.SHORT:System.out.println(s + "SHORT("+Size+")	" + ColumnName + "	" + ColumnId + "	" + Offset + "	" + DefaultValue);break;
				
			case DataType.INTEGER:System.out.println(s + "INTEGER("+Size+")	" + ColumnName + "	" + ColumnId + "	" + Offset + "	" + DefaultValue);break;
			case DataType.FLOAT:System.out.println(s + "FLOAT("+Size+")	" + ColumnName + "	" + ColumnId + "	" + Offset + "	" + DefaultValue);break;
				
			case DataType.LONG:System.out.println(s + "LONG("+Size+")	" + ColumnName + "	" + ColumnId + "	" + Offset + "	" + DefaultValue);break;
			case DataType.DOUBLE:System.out.println(s + "DOUBLE("+Size+")	" + ColumnName + "	" + ColumnId + "	" + Offset + "	" + DefaultValue);break;
			case DataType.STRING:System.out.println(s + "STRING("+Size+")	" + ColumnName + "	" + ColumnId + "	" + Offset + "	" + DefaultValue);break;
			case DataType.TIMEMILI:System.out.println(s + "TIMEMILI("+Size+")	" + ColumnName + "	" + ColumnId + "	" + Offset + "	" + DefaultValue);break;
			case DataType.USER_ID:System.out.println(s + "USER_ID("+Size+")	" + ColumnName + "	" + ColumnId + "	" + Offset + "	" + DefaultValue);break;
				
			case DataType.LIST:System.out.println(s + "LIST("+Size+")	" + ColumnName + "	" + ColumnId + "	" + Offset + "	" + DefaultValue);break;
			case DataType.BINARY:System.out.println(s + "BINARY("+Size+")	" + ColumnName + "	" + ColumnId + "	" + Offset + "	" + DefaultValue);break;
			default:break;
		}
	}	
}
