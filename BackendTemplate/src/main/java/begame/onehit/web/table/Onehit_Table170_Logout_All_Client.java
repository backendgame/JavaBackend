package begame.onehit.web.table;

import java.io.IOException;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import begame.config.CMD_ONEHIT;
import database_game.table.DBGame_AccountLogin;

public class Onehit_Table170_Logout_All_Client extends BaseOnehitTable_Validate_TableInfo {

	public Onehit_Table170_Logout_All_Client() {
		super(CMD_ONEHIT.BBWeb_Table_Logout_All_Client);
	}


	@Override protected MessageSending onDatabaseAccount(DBGame_AccountLogin databaseAccount, MessageReceiving messageReceiving) throws IOException {
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		//////////////////////////////////////////////////////////////////////////////////
		databaseAccount.rfData.seek(DBGame_AccountLogin.Offset_LogoutId);
		byte logoutId = databaseAccount.rfData.readByte();
		databaseAccount.rfData.seek(DBGame_AccountLogin.Offset_LogoutId);
		databaseAccount.rfData.writeByte(logoutId+1);
		return mgOK;
	}

}
