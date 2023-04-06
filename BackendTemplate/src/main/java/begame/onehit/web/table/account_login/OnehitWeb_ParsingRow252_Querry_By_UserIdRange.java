package begame.onehit.web.table.account_login;

import backendgame.com.core.MessageReceiving;
import backendgame.config.CMD_ONEHIT;
import database.table.DBGameTable_AccountLogin;
import database.table.DBGameTable_UserData;
import database.table.DBString;

public class OnehitWeb_ParsingRow252_Querry_By_UserIdRange extends BaseOnehitWeb_TableRow_Parsing {

	public OnehitWeb_ParsingRow252_Querry_By_UserIdRange() {
		super(CMD_ONEHIT.BBWeb_ParsingRow_Querry_By_UserIdRange);
	}

	@Override public long[] onGetUserId(DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, MessageReceiving messageReceiving) throws Exception {
		long userIdBegin = messageReceiving.readLong();
		long userIdEnd = messageReceiving.readLong();
		////////////////////////////////////////////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return null;
		////////////////////////////////////////////////////////////////////////////////////////////////
		long countRow = databaseUserData.countRow();

		if(userIdBegin<0)
			userIdBegin=0;
		if(userIdBegin>countRow-1)
			userIdBegin=countRow-1;
		
		if(userIdEnd<0)
			userIdEnd=0;
		if(userIdEnd>countRow-1)
			userIdEnd=countRow-1;
		////////////////////////////////////////////////////////////////////////////////////////////////
		return rangeToArray(userIdBegin, userIdEnd);
	}
}
