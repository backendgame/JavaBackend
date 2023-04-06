package begame.onehit.web.table;

import java.io.IOException;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.config.CMD_ONEHIT;
import database.BaseTableData;
import database.table.DBGameTable_UserData;

public class Onehit_Table155_Config_WriteKey_Set extends BaseOnehitTable_Validate_TableInfo {

	public Onehit_Table155_Config_WriteKey_Set() {
		super(CMD_ONEHIT.BBWeb_Table_Config_WriteKey);
	}

	@Override protected MessageSending onHeaderInfo(DBGameTable_UserData databaseUserData, MessageReceiving messageReceiving) throws IOException {
//		long writeKey = messageReceiving.readLong();
//		if(messageReceiving.validate()==false)
//			return mgVariableInvalid;
//		////////////////////////////////////////////////////////////////
//		databaseUserData.setLong(BaseTableData.Offset_WriteKey, writeKey);
//		////////////////////////////////////////////////////////////////
//		MessageSending messageSending=new MessageSending(cmd,CaseCheck.HOPLE);
//		messageSending.writeLong(writeKey);
//		return messageSending;
		byte[] writeKey = messageReceiving.readByteArray(8);
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		////////////////////////////////////////////////////////////////
		databaseUserData.writeBytes(BaseTableData.Offset_WriteKey, writeKey);
		////////////////////////////////////////////////////////////////
		return mgOK;
	}
}
