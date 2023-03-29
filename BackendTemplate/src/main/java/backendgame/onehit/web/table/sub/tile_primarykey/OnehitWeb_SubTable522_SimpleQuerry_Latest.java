package backendgame.onehit.web.table.sub.tile_primarykey;

import backendgame.config.CMD_ONEHIT;
import bgcore.core.MessageReceiving;
import database.table.DBGameTable_AccountLogin;
import database.table.DBGameTable_UserData;
import database.table.DBString;
import database.table.sub.DBGameTable_SubUserData;

public class OnehitWeb_SubTable522_SimpleQuerry_Latest extends BaseOnehit_SubTable_SimpleData {

	public OnehitWeb_SubTable522_SimpleQuerry_Latest() {super(CMD_ONEHIT.BBWeb_SubTable_Querry_Latest);}

	@Override public long[] onGetUserId(DBGameTable_SubUserData subTable, DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, MessageReceiving messageReceiving) throws Exception {
		short numberUser = messageReceiving.readShort();
		////////////////////////////////////////////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return null;
		////////////////////////////////////////////////////////////////////////////////////////////////
		long maxUserId = databaseUserData.countRow() - 1;
		
		if(numberUser>maxUserId)
			numberUser=(short) maxUserId;
		
		long[] listUserId = new long[numberUser];
		for(short i=0;i<numberUser;i++)
			listUserId[i] = maxUserId - i;
		
		return listUserId;
	}

}