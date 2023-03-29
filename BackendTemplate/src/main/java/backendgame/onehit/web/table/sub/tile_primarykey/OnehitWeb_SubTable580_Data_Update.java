package backendgame.onehit.web.table.sub.tile_primarykey;

import backendgame.config.CMD_ONEHIT;
import bgcore.core.MessageReceiving;
import bgcore.core.MessageSending;
import database.table.DBGameTable_AccountLogin;
import database.table.DBGameTable_UserData;
import database.table.DBString;
import database.table.sub.DBGameTable_SubUserData;

public class OnehitWeb_SubTable580_Data_Update extends BaseOnehit_SubTable {

	public OnehitWeb_SubTable580_Data_Update() {super(CMD_ONEHIT.BBWeb_SubTable_Data_Update);}

	@Override public MessageSending onProcess(DBGameTable_SubUserData subTable, DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, MessageReceiving messageReceiving) throws Exception {

		return null;
	}

}
