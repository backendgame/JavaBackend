package backendgame.onehit.web.table.sub.tile_primarykey;

import backendgame.config.CMD_ONEHIT;
import bgcore.core.MessageReceiving;
import database.table.DBGameTable_AccountLogin;
import database.table.DBGameTable_UserData;
import database.table.DBString;
import database.table.sub.DBGameTable_SubUserData;

public class OnehitWeb_SubTable521_SimpleQuerry_Range extends BaseOnehit_SubTable_SimpleData {

	public OnehitWeb_SubTable521_SimpleQuerry_Range() {super(CMD_ONEHIT.BBWeb_SubTable_Querry_Range);}

	@Override public long[] onGetUserId(DBGameTable_SubUserData subTable, DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, MessageReceiving messageReceiving) throws Exception {
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
