package backendgame.onehit.web.table.sub;

import java.io.File;
import java.io.RandomAccessFile;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.config.CMD_ONEHIT;
import backendgame.config.CaseCheck;
import backendgame.onehit.BaseOnehitWeb;
import backendgame.onehit.BinaryToken;
import database.BaseTableData;
import database.DataType;
import database.SubTable;
import database.table.DBGameTable_AccountLogin;
import database.table.DBGameTable_UserData;
import database.table.DBString;
import database.table.sub.DBGameTable_Leaderboard;

public class OnehitWeb_SubTable300_Create_Leaderboard extends BaseOnehitWeb {

	public OnehitWeb_SubTable300_Create_Leaderboard() {super(CMD_ONEHIT.BBWeb_SubTable_Create_Leaderboard);}

	@Override public MessageSending onProcessDatabase(DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, BinaryToken richardToken, MessageReceiving messageReceiving) throws Exception {
		short numberTop=messageReceiving.readShort();
		byte dataType = messageReceiving.readByte();
		String columnName = messageReceiving.readString();
		////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return mgInvalid;
		
		byte size = 0;
		switch (dataType) {
			case DataType.BYTE:		size=1;break;
			case DataType.SHORT:	size=2;break;
			case DataType.INTEGER:	size=4;break;
			case DataType.LONG:		size=8;break;
			
			case DataType.FLOAT:	size=4;break;
			case DataType.DOUBLE:	size=8;break;
			default:return mgVariableInvalid;
		}
		////////////////////////////////////////////////////////
		MessageSending mgResult=null;
		RandomAccessFile rf=null;
		try {
			short subTable = databaseUserData.countSubTable();
			File dir = new File(SubTable.getDirectory(databaseUserData.tableId, subTable));if(dir.exists()==false)dir.mkdir();
			rf = new RandomAccessFile(SubTable.getSubFile(databaseUserData.tableId, subTable), "rw");
			rf.write(databaseUserData.read(0, (int) SubTable.HEADER_LENGTH));
			rf.writeByte(SubTable.SubType_Leaderboard);//60
			rf.writeByte(dataType);//61
			rf.writeLong(dbString.getStringId(columnName));//62
			rf.writeShort(0);//70 IndexMin
			rf.setLength(DBGameTable_Leaderboard.OFFSET_StartLeaderboard+numberTop*(8 + size));//LengthFile = SubTable.HEADER_LENGTH + 1b(SubType) + numberTop*(userId+Value);
			
			rf.seek(BaseTableData.Offset_DataLength);
			rf.writeShort(numberTop);
			
			rf.seek(BaseTableData.Offset_TimeCreate);
			rf.writeLong(currentTimeMillis);
			
			databaseUserData.updateCountSubTable(subTable+1);

			MessageSending messageSending=new MessageSending(cmd,CaseCheck.HOPLE);
			messageSending.writeShort(subTable);
			mgResult = messageSending;
		}catch (Exception e) {
			e.printStackTrace();
			mgResult=new MessageSending(cmd, CaseCheck.EXCEPTION);
			mgResult.writeString(getStringException(e));
		}
		
		if(rf!=null)
			rf.close();
		return mgResult;
	}
	
}
