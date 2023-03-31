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
import database.SubTable;
import database.table.DBGameTable_AccountLogin;
import database.table.DBGameTable_UserData;
import database.table.DBString;

public class OnehitWeb_SubTable305_Create_Tile_Binary extends BaseOnehitWeb {

	public OnehitWeb_SubTable305_Create_Tile_Binary() {super(CMD_ONEHIT.BBWeb_SubTable_Create_Tile_Binary);}

	@Override public MessageSending onProcessDatabase(DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, BinaryToken richardToken, MessageReceiving messageReceiving) throws Exception {
		////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		////////////////////////////////////////////////////////
		MessageSending mgResult=null;
		RandomAccessFile rf=null;
		try {
			short subTable = databaseUserData.countSubTable();
			File dir = new File(SubTable.getDirectory(databaseUserData.tableId, subTable));if(dir.exists()==false)dir.mkdir();
			rf = new RandomAccessFile(SubTable.getSubFile(databaseUserData.tableId, subTable), "rw");
			rf.write(databaseUserData.read(0, (int) SubTable.HEADER_LENGTH));
			rf.writeByte(SubTable.SubType_Tile_Binary);
			
			rf.seek(BaseTableData.Offset_TimeCreate);
			rf.writeLong(currentTimeMillis);
			
			databaseUserData.updateCountSubTable(subTable+1);
			///////////////////////////////////////////////////////////////////////////////////////////////////////
			new File(SubTable.getDesFile(databaseUserData.tableId, subTable)).createNewFile();

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
