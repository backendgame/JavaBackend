package server.onehit.web.table.account_login;

import backendgame.com.core.MessageReceiving;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import server.config.CMD_ONEHIT;

public class OnehitWeb_ParsingRow252_Querry_By_UserIdRange extends BaseOnehitWeb_TableRow_Parsing {

	public OnehitWeb_ParsingRow252_Querry_By_UserIdRange() {
		super(CMD_ONEHIT.BBWeb_ParsingRow_Querry_By_UserIdRange);
	}

	@Override public long[] onGetUserId(DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData, MessageReceiving messageReceiving) throws Exception {
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
