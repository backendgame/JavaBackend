package begame.onehit.web.table;

import java.io.IOException;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import begame.config.CMD_ONEHIT;
import database_game.table.DBGame_AccountLogin;

public class Onehit_Table157_Config_TokenLifeTime extends BaseOnehitTable_Validate_TableInfo {

	public Onehit_Table157_Config_TokenLifeTime() {
		super(CMD_ONEHIT.BBWeb_Table_Config_TokenLifeTime);
	}
	
	@Override
	protected MessageSending onDatabaseAccount(DBGame_AccountLogin databaseAccount, MessageReceiving messageReceiving) throws IOException {
		long tokenLifeTime = messageReceiving.readLong();
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		////////////////////////////////////////////////////////////////
		databaseAccount.writeLong(DBGame_AccountLogin.Offset_Token_LifeTime, tokenLifeTime);
		////////////////////////////////////////////////////////////////
		return mgOK;
	}
}
