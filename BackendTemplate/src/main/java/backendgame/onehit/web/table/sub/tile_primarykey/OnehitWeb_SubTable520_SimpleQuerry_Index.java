package backendgame.onehit.web.table.sub.tile_primarykey;

import backendgame.com.core.MessageReceiving;
import backendgame.config.CMD_ONEHIT;
import database.table.DBGameTable_AccountLogin;
import database.table.DBGameTable_UserData;
import database.table.DBString;
import database.table.sub.DBGameTable_SubUserData;

public class OnehitWeb_SubTable520_SimpleQuerry_Index extends BaseOnehit_SubTable_SimpleData {

	public OnehitWeb_SubTable520_SimpleQuerry_Index() {super(CMD_ONEHIT.BBWeb_SubTable_Querry_Index);}

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
