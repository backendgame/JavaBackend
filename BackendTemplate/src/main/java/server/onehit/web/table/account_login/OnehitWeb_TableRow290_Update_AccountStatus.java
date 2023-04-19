package server.onehit.web.table.account_login;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import server.config.CMD_ONEHIT;
import server.onehit.web.BaseOnehitWeb;

public class OnehitWeb_TableRow290_Update_AccountStatus extends BaseOnehitWeb {

	public OnehitWeb_TableRow290_Update_AccountStatus() {
		super(CMD_ONEHIT.BBWeb_Row_Update_AccountStatus);
	}
	
	@Override public MessageSending onProcessDatabase(DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData, MessageReceiving messageReceiving) throws Exception {
		long userId = messageReceiving.readLong();
		byte status = messageReceiving.readByte();
		////////////////////////////////////////////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return mgInvalid;
		if(userId<0)
			return mgPlayerInvalidData;
		////////////////////////////////////////////////////////////////////////////////////////////////
		databaseAccount.updateStatus(databaseUserData.getOffsetOfCredential(userId),status);
		return mgOK;
	}	
}
