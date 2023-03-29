package backendgame.onehit.web.table;

import java.io.IOException;

import backendgame.config.CMD_ONEHIT;
import bgcore.core.MessageReceiving;
import bgcore.core.MessageSending;
import database.BaseTableData;
import database.table.DBGameTable_UserData;

public class Onehit_Table151_Config_AccessKey_Set extends BaseOnehitTable_Validate_TableInfo {

	public Onehit_Table151_Config_AccessKey_Set() {
		super(CMD_ONEHIT.BBWeb_Table_Config_AccessKey);
	}

	@Override protected MessageSending onHeaderInfo(DBGameTable_UserData databaseUserData, MessageReceiving messageReceiving) throws IOException {
		byte[] accessKey = messageReceiving.readByteArray(8);
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		////////////////////////////////////////////////////////////////
		databaseUserData.writeBytes(BaseTableData.Offset_AccessKey, accessKey);
		////////////////////////////////////////////////////////////////
		return mgOK;
	}

}
