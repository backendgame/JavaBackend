package server.onehit.web.table;

import java.io.IOException;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import database_game.table.DBGame_AccountLogin;
import server.config.CMD_ONEHIT;

public class Onehit_Table159_Config_MailService extends BaseOnehitTable_Validate_TableInfo {

	public Onehit_Table159_Config_MailService() {
		super(CMD_ONEHIT.BBWeb_Table_Config_MailService);
	}

	@Override
	protected MessageSending onDatabaseAccount(DBGame_AccountLogin databaseAccount, MessageReceiving messageReceiving) throws IOException {
		if(messageReceiving.avaiable()==0 || messageReceiving.avaiable()>256)
			return mgVariableInvalid;
		databaseAccount.writeData(DBGame_AccountLogin.Offset_Email, messageReceiving.getEndByte());
		return mgOK;
	}
}
