package begame.onehit.web.table;

import java.io.IOException;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.config.CMD_ONEHIT;
import database.BaseTableData;
import database.table.DBGameTable_UserData;

public class Onehit_Table153_Config_ReadKey_Set extends BaseOnehitTable_Validate_TableInfo {

	public Onehit_Table153_Config_ReadKey_Set() {
		super(CMD_ONEHIT.BBWeb_Table_Config_ReadKey);
	}
	
	@Override protected MessageSending onHeaderInfo(DBGameTable_UserData databaseUserData, MessageReceiving messageReceiving) throws IOException {
//		long readKey = messageReceiving.readLong();
//		if(messageReceiving.validate()==false)
//			return mgVariableInvalid;
//		////////////////////////////////////////////////////////////////
//		databaseUserData.setLong(BaseTableData.Offset_ReadKey, readKey);
//		////////////////////////////////////////////////////////////////
//		MessageSending messageSending=new MessageSending(cmd,CaseCheck.HOPLE);
//		messageSending.writeLong(readKey);
//		return messageSending;
		byte[] readKey = messageReceiving.readByteArray(8);
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		////////////////////////////////////////////////////////////////
		databaseUserData.writeBytes(BaseTableData.Offset_ReadKey, readKey);
		////////////////////////////////////////////////////////////////
		return mgOK;
	}

}
