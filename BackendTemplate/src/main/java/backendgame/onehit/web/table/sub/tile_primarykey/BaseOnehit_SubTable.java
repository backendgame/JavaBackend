package backendgame.onehit.web.table.sub.tile_primarykey;

import java.io.File;

import backendgame.config.CaseCheck;
import backendgame.config.PATH;
import backendgame.onehit.BaseOnehit_AiO;
import backendgame.onehit.BinaryToken;
import bgcore.core.MessageReceiving;
import bgcore.core.MessageSending;
import bgcore.core.OneHitProcessing;
import bgcore.core.server.BaseBackEnd_Session;
import database.SubTable;
import database.table.DBGameTable_AccountLogin;
import database.table.DBGameTable_UserData;
import database.table.DBString;
import database.table.sub.DBGameTable_SubUserData;

public abstract class BaseOnehit_SubTable extends BaseOnehit_AiO {

	public BaseOnehit_SubTable(short command_id) {super(command_id);}

	public abstract MessageSending onProcess(DBGameTable_SubUserData subTable, DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, MessageReceiving messageReceiving) throws Exception;
	
	@Override public MessageSending onMessage(BaseBackEnd_Session session, MessageReceiving messageReceiving) {
		BinaryToken binaryToken = new BinaryToken();
		if (binaryToken.decode(messageReceiving.readString()) == false)
			return mgTokenError;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (OneHitProcessing.currentTimeMillis > binaryToken.timeOut)
			return mgExpired;
		short tableId = messageReceiving.readShort();
		short subId = messageReceiving.readShort();
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(tableId<0 || subId<0 || binaryToken.adminId<1)// Validate TableId và UserId
			return mgInvalid;
		if(new File(PATH.DATABASE_FOLDER+"/"+tableId).exists()==false)
			return mgNotExist;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		MessageSending mgResult;
		DBGameTable_AccountLogin databaseAccount = null;
		DBGameTable_UserData databaseUserData=null;
		DBGameTable_SubUserData subTable=null;
		DBString dbString = null;
		try {
			subTable=new DBGameTable_SubUserData(tableId, subId);
			databaseAccount = new DBGameTable_AccountLogin(tableId);
			databaseUserData = new DBGameTable_UserData(tableId);
			dbString = new DBString(tableId);
			if(System.currentTimeMillis() > subTable.timeAvaiable)
				mgResult = mgExpired;
			else if(binaryToken.adminId != subTable.adminId)
				mgResult = mgPlayerWrong;
			else if(subTable.getType() != SubTable.SubType_SubUserData)
				mgResult = mgDatabaseNotExist;
			else 
				mgResult = onProcess(subTable, databaseAccount, databaseUserData, dbString, messageReceiving);
		} catch (Exception e) {
			e.printStackTrace();
			mgResult = new MessageSending(cmd, CaseCheck.EXCEPTION);//Tại vì có write thêm data
			mgResult.writeString(getStringException(e));
		}
		
		if(dbString!=null)dbString.close();
		if(subTable != null)subTable.close();
		if(databaseUserData!=null)databaseUserData.close();
		if(databaseAccount!=null)databaseAccount.close();
		return mgResult;
	}
	




}
