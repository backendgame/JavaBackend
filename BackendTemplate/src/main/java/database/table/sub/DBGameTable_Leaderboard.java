package database.table.sub;

import java.io.IOException;

import bgcore.core.MessageSending;
import database.BaseTableData;
import database.DataType;
import database.SubTable;
import database.table.DBString;

public class DBGameTable_Leaderboard extends BaseTableData{
//	public static final long OFFSET_Type				=60;//byte
	public static final long OFFSET_DataType			=61;//short
	public static final long OFFSET_ColumnName			=62;//byte
	public static final long OFFSET_InDexMin			=70;//short
	public static final long OFFSET_StartLeaderboard	=72;//short
	///////////////////////////////////////////////////////////////////////////////////////numberTop=dataLength
	public byte dataType;
	public byte size;
	public DBGameTable_Leaderboard(short _tableId, short _subTableId) throws IOException {
		super(_tableId,SubTable.getSubFile(_tableId, _subTableId));
		rf.seek(OFFSET_DataType);
		dataType = rf.readByte();
		switch (dataType) {
			case DataType.BYTE:		size=1;break;
			case DataType.SHORT:	size=2;break;
			case DataType.INTEGER:	size=4;break;
			case DataType.LONG:		size=8;break;
			
			case DataType.FLOAT:	size=4;break;
			case DataType.DOUBLE:	size=8;break;
			default:throw new IOException("DataType error : "+dataType);
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////
	public String getColumnName(DBString dbString) throws IOException {rf.seek(OFFSET_ColumnName);return dbString.getStringByLocation(rf.readLong());}
	///////////////////////////////////////////////////////////////////////////////////////
	public long getUserId(short index) throws IOException {
		rf.seek(OFFSET_StartLeaderboard+index*(8+size));
		return rf.readLong();
	}
//	public long getValue(long index) throws IOException {
//		rf.seek(OFFSET_StartLeaderboard+index*(8+size)+8);
//		return rf.readLong();
//	}
	public Object getValue(short index) throws IOException {
		rf.seek(OFFSET_StartLeaderboard+index*(8+size)+8);
		switch (dataType) {
			case DataType.BYTE:		return rf.readByte();
			case DataType.SHORT:	return rf.readShort();
			case DataType.INTEGER:	return rf.readInt();
			case DataType.LONG:		return rf.readLong();
			
			case DataType.FLOAT:	return rf.readFloat();
			case DataType.DOUBLE:	return rf.readDouble();
			default:throw new IOException("DataType error : "+dataType);
		}
	}
	public void writeValue(MessageSending messageSending, short index) throws IOException {
		rf.seek(OFFSET_StartLeaderboard+index*(8+size)+8);
		switch (dataType) {
			case DataType.BYTE:		messageSending.writeByte(rf.readByte());break;
			case DataType.SHORT:	messageSending.writeShort(rf.readShort());break;
			case DataType.INTEGER:	messageSending.writeInt(rf.readInt());break;
			case DataType.LONG:		messageSending.writeLong(rf.readLong());break;
			
			case DataType.FLOAT:	messageSending.writeFloat(rf.readFloat());break;
			case DataType.DOUBLE:	messageSending.writeDouble(rf.readDouble());break;
			default:throw new IOException("DataType error : "+dataType);
		}
	}
	
	
	
	public void update(long index, long userId, Object value) throws IOException {
		rf.seek(OFFSET_StartLeaderboard+index*(8+size));
		rf.writeLong(userId);
		switch (dataType) {
			case DataType.BYTE:		rf.writeByte((byte) value);break;
			case DataType.SHORT:	rf.writeShort((short) value);break;
			case DataType.INTEGER:	rf.writeInt((int) value);break;
			case DataType.LONG:		rf.writeLong((long) value);break;
			
			case DataType.FLOAT:	rf.writeFloat((float) value);break;
			case DataType.DOUBLE:	rf.writeDouble((double) value);break;
			default:throw new IOException("DataType error : "+dataType);
		}
	}
	
	@Override public long countRow() throws IOException {return dataLength;}
}
