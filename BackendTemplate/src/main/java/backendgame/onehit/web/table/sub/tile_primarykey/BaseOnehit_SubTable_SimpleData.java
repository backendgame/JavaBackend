package backendgame.onehit.web.table.sub.tile_primarykey;

import bgcore.core.MessageReceiving;
import bgcore.core.MessageSending;
import database.table.DBGameTable_AccountLogin;
import database.table.DBGameTable_UserData;
import database.table.DBString;
import database.table.sub.DBGameTable_SubUserData;

public abstract class BaseOnehit_SubTable_SimpleData extends BaseOnehit_SubTable {

	public BaseOnehit_SubTable_SimpleData(short command_id) {super(command_id);}

	public abstract long[] onGetUserId(DBGameTable_SubUserData subTable, DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, MessageReceiving messageReceiving) throws Exception;

	@Override
	public MessageSending onProcess(DBGameTable_SubUserData subTable, DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, MessageReceiving messageReceiving) throws Exception {
		long[] listUserId = onGetUserId(subTable, databaseAccount, databaseUserData, dbString, messageReceiving);
		if(listUserId==null || listUserId.length==0) {
			return mgValueNull;	
		}else {
			short numberUserId = (short) listUserId.length;
			
			MessageSending bigMessageSending=new MessageSending((short) 0);
			subTable.writeDescribeTable(bigMessageSending, dbString);
			
			bigMessageSending.writeShort(numberUserId);
			for(short i=0;i<numberUserId;i++) {
				databaseAccount.writeInfoByOffset(bigMessageSending, listUserId[i]);
				bigMessageSending.writeCopyData(subTable.readDataByUserId(listUserId[i]));
			}
			return bigMessageSending;
		}
	}
	
	
	




}
