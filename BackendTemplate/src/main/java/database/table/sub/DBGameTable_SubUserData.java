package database.table.sub;

import java.io.IOException;

import bgcore.core.MessageSending;
import database.BaseTableData;
import database.DescribeTable;
import database.SubTable;
import database.table.DBString;

public class DBGameTable_SubUserData extends BaseTableData {
	public static final int OFFSET_StartSubUserData		= 7730;
	
	public static final long OFFSET_Type				=60;//byte
	public static final long OFFSET_DescribeTables	 	= 61;//ID → short → List DescribeTables
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public DBGameTable_SubUserData(short _tableId, short _subTableId) throws IOException {
		super(_tableId,SubTable.getSubFile(_tableId, _subTableId));
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void updateDescribeTable(DescribeTable[] list, DBString dbString) throws IOException {
		rf.seek(OFFSET_StartSubUserData);
		rf.writeByte(1);
		updateDescribeTable(OFFSET_DescribeTables, list, OFFSET_StartSubUserData + 1, dbString);
	}
	public DescribeTable[] getDescribeTable(DBString dbString) throws IOException {return getDescribeTables(OFFSET_DescribeTables, OFFSET_StartSubUserData + 1, dbString);}
	
	public void writeDescribeTable(MessageSending messageSending,DBString dbString) throws IOException {
		DescribeTable[] listDescribeTables = getDescribeTable(dbString);
		if(listDescribeTables==null)
			messageSending.writeShort((short) 0);
		else {
			short numberDescribeTables = (short) listDescribeTables.length;
			messageSending.writeShort(numberDescribeTables);
			for(short i=0;i<numberDescribeTables;i++)
				listDescribeTables[i].writeMessage(messageSending);
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private byte[] dataDefault=null;
	private byte[] dataTransfer=null;
	public byte[] readDataByUserId(long userId) throws IOException {
		if(dataTransfer==null)
			dataTransfer=new byte[1+dataLength];
		
		rf.seek(OFFSET_StartSubUserData + userId*(1+dataLength));
		rf.read(dataTransfer);
		
		if(dataTransfer[0]==0) {//Chưa initDeafule value
			if(dataDefault==null) {
				dataDefault=new byte[1+dataLength];
			
				rf.seek(OFFSET_StartSubUserData);
				rf.read(dataDefault);
			}
			
			rf.seek(OFFSET_StartSubUserData + userId*(1+dataLength));
			rf.write(dataDefault);
			return dataDefault;
		}else
			return dataTransfer;
	}
	
	@Override public long countRow() throws IOException {return (rf.length() - OFFSET_StartSubUserData)/(1+dataLength);}
}
