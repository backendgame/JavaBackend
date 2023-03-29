package backendgame.onehit.web.table.sub.tile_primarykey;

import backendgame.config.CMD_ONEHIT;
import bgcore.core.MessageReceiving;
import database.table.DBGameTable_AccountLogin;
import database.table.DBGameTable_UserData;
import database.table.DBString;
import database.table.sub.DBGameTable_SubUserData;

public class OnehitWeb_SubTable540_FullQuerry_Index extends BaseOnehit_SubTable_FullData {

	public OnehitWeb_SubTable540_FullQuerry_Index() {super(CMD_ONEHIT.BBWeb_SubTable_FullQuerry_Index);}

	@Override public long[] onGetUserId(DBGameTable_SubUserData subTable, DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, MessageReceiving messageReceiving) throws Exception {
		short numberUser = messageReceiving.readShort();
		long[] listUserId = new long[numberUser];
		for(short i=0;i<numberUser;i++)
			listUserId[i] = messageReceiving.readLong();
		////////////////////////////////////////////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return null;
		////////////////////////////////////////////////////////////////////////////////////////////////
		return listUserId;
	}

	
}
