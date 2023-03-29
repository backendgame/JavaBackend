package backendgame.onehit.web.table.account_data;

import backendgame.config.CMD_ONEHIT;
import backendgame.onehit.BaseOnehitWeb;
import backendgame.onehit.BinaryToken;
import bgcore.core.MessageReceiving;
import bgcore.core.MessageSending;
import database.table.DBGameTable_AccountLogin;
import database.table.DBGameTable_UserData;
import database.table.DBString;

public class OnehitWeb_TableRow290_Update_AccountStatus extends BaseOnehitWeb {

	public OnehitWeb_TableRow290_Update_AccountStatus() {
		super(CMD_ONEHIT.BBWeb_Row_Update_AccountStatus);
	}
	
	@Override public MessageSending onProcessDatabase(DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, BinaryToken richardToken, MessageReceiving messageReceiving) throws Exception {
		long userId = messageReceiving.readLong();
		byte status = messageReceiving.readByte();
		////////////////////////////////////////////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return mgInvalid;
		if(userId<0)
			return mgPlayerInvalidData;
		////////////////////////////////////////////////////////////////////////////////////////////////
		databaseUserData.rf.seek(DBGameTable_UserData.LENGTH_HEADER + userId*(9+databaseUserData.dataLength));
		databaseUserData.rf.writeByte(status);
		return mgOK;
	}	
}
