package begame.onehit.web.table;

import java.io.IOException;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import begame.config.CMD_ONEHIT;
import database_game.table.DBGame_AccountLogin;

public class Onehit_Table158_Config_LoginRule extends BaseOnehitTable_Validate_TableInfo {

	public Onehit_Table158_Config_LoginRule() {
		super(CMD_ONEHIT.BBWeb_Table_Config_LoginRule);
	}


	@Override protected MessageSending onDatabaseAccount(DBGame_AccountLogin databaseAccount, MessageReceiving messageReceiving) throws IOException {
		byte[] loginRule = messageReceiving.readSpecialArray_WithoutLength(7);
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		
		databaseAccount.writeData(DBGame_AccountLogin.Offset_Permission_Device, loginRule);
		return mgOK;
	}
}
