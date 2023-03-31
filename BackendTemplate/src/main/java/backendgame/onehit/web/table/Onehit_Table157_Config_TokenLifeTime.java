package backendgame.onehit.web.table;

import java.io.IOException;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.config.CMD_ONEHIT;
import database.table.DBGameTable_UserData;

public class Onehit_Table157_Config_TokenLifeTime extends BaseOnehitTable_Validate_TableInfo {

	public Onehit_Table157_Config_TokenLifeTime() {
		super(CMD_ONEHIT.BBWeb_Table_Config_TokenLifeTime);
	}
	
	@Override protected MessageSending onHeaderInfo(DBGameTable_UserData databaseUserData, MessageReceiving messageReceiving) throws IOException {
		long tokenLifeTime = messageReceiving.readLong();
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		////////////////////////////////////////////////////////////////
		databaseUserData.setLong(DBGameTable_UserData.Offset_Token_LifeTime, tokenLifeTime);
		////////////////////////////////////////////////////////////////
		return mgOK;
	}
}
