package database.table;

import java.io.IOException;
import java.io.RandomAccessFile;

import backendgame.config.PATH;
import bgcore.core.MessageSending;
import database.BaseTableData;
import database.DataType;
import database.DescribeTable;

public class DBGameTable_UserData extends BaseTableData{
	public static final int LENGTH_HEADER		= 8192;
	
	public static final long OFFSET_CountSubTable			= 60;
	public static final long Offset_Token_LifeTime			= 62;
	
	public static final long Offset_Tracking_AllTime 		= 70;//16 byte
	public static final long Offset_Tracking_Yesterday 		= 86;//20 byte
	public static final long Offset_Tracking_ThisMonth 		= 106;//20 byte
	
	public static final long Offset_Permission_Device 			= 126;
	public static final long Offset_Permission_SystemAccount 	= 127;
	public static final long Offset_Permission_Facebook 		= 128;
	public static final long Offset_Permission_Google 			= 129;
	public static final long Offset_Permission_AdsId 			= 130;
	public static final long Offset_Permission_Apple 			= 131;
	public static final long Offset_Permission_EmailCode		= 132;
	
	
	public static final long Offset_Email			 		= 256;
	
	public static final long Offset_DescribeTables	 		= 523;//ID → short → List DescribeTables
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	public DBGameTable_UserData(short _tableId,byte[] _header,String s) throws IOException {
//		tableId=_tableId;
//		try {
//			rf = new RandomAccessFile(s, "rw");
//			rf.write(_header);
//			rf.seek(LENGTH_HEADER-1);
//			rf.writeByte(0);
//			rf.seek(0);
//			timeAvaiable=rf.readLong();
//			adminId=rf.readLong();
//			permission=rf.readByte();
//			logoutId=rf.readByte();
//			dataLength=rf.readShort();
//			accessKey=rf.readLong();
//		}catch (Exception e) {
//			e.printStackTrace();
//			if(rf!=null)
//				try {rf.close();} catch (IOException ee) {ee.printStackTrace();}
//			throw e;
//		}
//	}
	public DBGameTable_UserData(short _tableId) throws IOException {
		super(_tableId,PATH.DATABASE_FOLDER+"/"+_tableId+"/"+PATH.UserData);
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public short countSubTable() throws IOException {rf.seek(OFFSET_CountSubTable);return rf.readShort();}
	public void updateCountSubTable(int countSubTable) throws IOException {rf.seek(OFFSET_CountSubTable);rf.writeShort(countSubTable);}
	/*1 byte đầu là byte đầu của data default là 1 để đồng bộ init data*/
	public void updateDescribeTable(DescribeTable[] list, DBString dbString) throws IOException {
		updateDescribeTable(Offset_DescribeTables, list, LENGTH_HEADER + 1, dbString);
		rf.seek(LENGTH_HEADER);
		rf.writeByte(1);
		rf.seek(LENGTH_HEADER+1+dataLength);
		rf.writeLong(0);
	}
	public short countNumberDescribeTables() throws IOException {rf.seek(Offset_DescribeTables+2);return rf.readShort();}
	public DescribeTable[] getDescribeTables(DBString dbString) throws IOException {return getDescribeTables(Offset_DescribeTables,LENGTH_HEADER+1, dbString);}
	public DescribeTable get1DescribeTable(short indexDescribeTable,DBString dbString) throws IOException {return get1DescribeTable(indexDescribeTable,Offset_DescribeTables,LENGTH_HEADER+1, dbString);}
	
	public void writeDescribeTable(MessageSending messageSending,DBString dbString) throws IOException {
		DescribeTable[] listDescribeTables = getDescribeTables(dbString);
		if(listDescribeTables==null)
			messageSending.writeShort((short) 0);
		else {
			short numberDescribeTables = (short) listDescribeTables.length;
			messageSending.writeShort(numberDescribeTables);
			for(short i=0;i<numberDescribeTables;i++) {
				listDescribeTables[i].writeMessage(messageSending);
			}
		}
	}
	
	public byte getStatus(long userId) throws IOException {rf.seek(LENGTH_HEADER + userId*(9+dataLength));return rf.readByte();}
	public void writeParsingRow(long userId, short numberDescribeTables,DescribeTable[] listDescribeTables, MessageSending messageSending, DBString dbString) throws IOException {
		rf.seek(LENGTH_HEADER + userId*(9+dataLength));
		messageSending.writeByte(rf.readByte());//Permission
		
		for(short i=0;i<numberDescribeTables;i++)
			switch (listDescribeTables[i].Type) {
				case DataType.BOOLEAN:
					messageSending.writeBoolean(rf.readBoolean());
					break;
				case DataType.BYTE:
				case DataType.STATUS:
				case DataType.PERMISSION:
				case DataType.AVARTAR:
					messageSending.writeByte(rf.readByte());
					break;
		
				case DataType.SHORT:
					messageSending.writeShort(rf.readShort());
					break;
					
				case DataType.INTEGER:
					messageSending.writeInt(rf.readInt());
					break;
				case DataType.FLOAT:
					messageSending.writeFloat(rf.readFloat());
					break;
					
				case DataType.LONG:
					messageSending.writeLong(rf.readLong());
					break;
				case DataType.DOUBLE:
					messageSending.writeDouble(rf.readDouble());
					break;
				case DataType.STRING:
					messageSending.writeString(dbString.getStringByLocation(rf.readLong()));
					break;
				case DataType.TIMEMILI:
					messageSending.writeLong(rf.readLong());
					break;
				case DataType.USER_ID:
					messageSending.writeLong(rf.readLong());
					break;
					
				case DataType.LIST:
				case DataType.BINARY:
					byte[] tmp = new byte[listDescribeTables[i].Size];
					rf.read(tmp);
					
					messageSending.writeShort(listDescribeTables[i].Size);
					messageSending.writeCopyData(tmp);
					break;
				default:break;
			}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public byte[] dataTransfer=null;
	public byte[] readDataByUserId(long userId) throws IOException {
		if(dataTransfer==null)
			dataTransfer=new byte[1+dataLength];
		
		rf.seek(LENGTH_HEADER + userId*(9+dataLength));
		rf.read(dataTransfer);
		
		return dataTransfer;
	}

	public void seekToData(long userId) throws IOException {rf.seek(LENGTH_HEADER + userId*(9+dataLength));}
	@Override public long countRow() throws IOException {return (rf.length() - LENGTH_HEADER) / (9 + dataLength);}
	
	public void insert(long credentialOffset) throws IOException {
		if(dataLength==0) {
			rf.seek(LENGTH_HEADER + countRow()*9);//UserId mới = countRow();
			rf.writeByte(1);
			rf.writeLong(credentialOffset);
		}else {
			readDataByUserId(0);//init dataTransfer
			
			rf.seek(LENGTH_HEADER + countRow()*(9+dataLength));
			rf.write(dataTransfer);
			rf.writeLong(credentialOffset);
		}
	}
	
	public long getCredentialOffset(long userId) throws IOException {
		rf.seek(LENGTH_HEADER + userId*(9+dataLength) + 1 + dataLength);
		return rf.readLong();
	}
	
	public void cloneWithData(RandomAccessFile rfClone, DescribeTable[] newDescribeTables, DBString dbString) throws IOException {
		//Header
		rfClone.seek(0);
		rfClone.write(read(0, (int) LENGTH_HEADER));
		
		//DescribeTable
		short tmpLength=dataLength;
		RandomAccessFile tmpRF = rf;
		rf=rfClone;
		updateDescribeTable(Offset_DescribeTables, newDescribeTables, DBGameTable_UserData.LENGTH_HEADER + 1, dbString);//Mượn tạm class để lưu DescribeTable
		short newDataLength = dataLength;
		dataLength = tmpLength;
		rf = tmpRF;
		
		//Copy data
		boolean flagProcess;
		DescribeTable newDes;
		byte[] data=new byte[8192];
		long numberRow = countRow();
		long offsetStartData = DBGameTable_UserData.LENGTH_HEADER;
		DescribeTable[] oldDescribeTables = getDescribeTables(dbString);
		short size;
		for(long i=1;i<numberRow;i++) {
			rf.seek(offsetStartData + i*(9+dataLength));//1 byte đầu là Permission
			rfClone.seek(offsetStartData + i*(9+newDataLength));
			rfClone.writeByte(rf.readByte());
			
			rf.seek(offsetStartData + i*(9+dataLength) + 1+dataLength);//1 byte đầu là Permission
			rfClone.seek(offsetStartData + i*(9+newDataLength) + 1+newDataLength);
			rfClone.writeLong(rf.readLong());
			
			for(short j=0;j<newDescribeTables.length;j++) {
				flagProcess = false;
				newDes = newDescribeTables[j];
				if(oldDescribeTables!=null && oldDescribeTables.length>0)
					for(DescribeTable oldDes:oldDescribeTables)
						if(oldDes.isSame(newDes)) {
							size = oldDes.Size<newDes.Size?oldDes.Size:newDes.Size;
							
							rf.seek(offsetStartData + i*(9+dataLength) + 1 + oldDes.Offset);
							rf.read(data, 0, size);
							
							rfClone.seek(offsetStartData + i*(9 + newDataLength) + 1 + newDes.Offset);
							rfClone.write(data, 0, size);
							flagProcess=true;
							break;
						}
				
				if(flagProcess==false) {
					rfClone.seek(offsetStartData + i*(9 + newDataLength) + 1 + newDes.Offset);
					newDes.writeDefaultValue(rfClone, dbString);
				}
			}
			
		}
	}
}
