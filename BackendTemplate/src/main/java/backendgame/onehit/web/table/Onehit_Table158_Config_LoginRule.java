package backendgame.onehit.web.table;

import java.io.IOException;

import backendgame.config.CMD_ONEHIT;
import bgcore.core.MessageReceiving;
import bgcore.core.MessageSending;
import database.table.DBGameTable_UserData;

public class Onehit_Table158_Config_LoginRule extends BaseOnehitTable_Validate_TableInfo {

	public Onehit_Table158_Config_LoginRule() {
		super(CMD_ONEHIT.BBWeb_Table_Config_LoginRule);
	}

	@Override protected MessageSending onHeaderInfo(DBGameTable_UserData databaseUserData, MessageReceiving messageReceiving) throws IOException {
		if(messageReceiving.avaiable()!=7)
			return mgInvalid;
		////////////////////////////////////////////////////////////////
		databaseUserData.rf.seek(DBGameTable_UserData.Offset_Permission_Device);
		databaseUserData.rf.write(messageReceiving.readByteArray(7));
		////////////////////////////////////////////////////////////////
		return mgOK;
	}
	
	

}
