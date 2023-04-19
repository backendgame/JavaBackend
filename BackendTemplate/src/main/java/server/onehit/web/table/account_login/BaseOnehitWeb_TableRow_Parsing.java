package server.onehit.web.table.account_login;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.server.BackendSession;
import backendgame.com.database.DBDescribe;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import server.config.CaseCheck;
import server.onehit.BaseOnehit_AiO;
import server.onehit.BinaryToken;

public abstract class BaseOnehitWeb_TableRow_Parsing extends BaseOnehit_AiO {

	public BaseOnehitWeb_TableRow_Parsing(short command_id) {super(command_id);}

	public abstract long[] onGetUserId(DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData, MessageReceiving messageReceiving) throws Exception;
	
	@Override public MessageSending onMessage(BackendSession session, MessageReceiving messageReceiving) {
		BinaryToken richardToken = new BinaryToken();
		if (richardToken.decode(messageReceiving.readString()) == false)
			return mgTokenError;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (currentTimeMillis > richardToken.timeOut)
			return mgExpired;
		short tableId = messageReceiving.readShort();
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(tableId<0 || richardToken.adminId<1)// Validate TableId và UserId
			return mgInvalid;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		MessageSending mgResult=null;
		DBGame_AccountLogin databaseAccount = null;
		DBGame_UserData databaseUserData=null;
		try {
			databaseAccount = new DBGame_AccountLogin(tableId);
			databaseUserData = new DBGame_UserData(tableId);
			
			if(System.currentTimeMillis()>databaseUserData.timeAvaiable)//Hết hạn
				mgResult=mgTimeout;
			else if(databaseUserData.countRow()<2)
				mgResult=mgPlayerNotFound;
			else if(richardToken.adminId==databaseUserData.adminId) {
				long[] listUserId = onGetUserId(databaseAccount, databaseUserData, messageReceiving);
				if(listUserId==null || listUserId.length==0) {
					mgResult = mgValueNull;	
				}else {
					int numberUserId = listUserId.length;
					MessageSending bigMessageSending=new MessageSending(cmd,CaseCheck.HOPLE);
					bigMessageSending.writeLong(databaseUserData.countRow());
					if(databaseUserData.getNumberColumn()==0) {
						bigMessageSending.writeInt(0);
						bigMessageSending.writeInt(numberUserId);
						for(short i=0;i<numberUserId;i++)
							databaseAccount.writeInfoByOffset(bigMessageSending, databaseUserData.getOffsetOfCredential(listUserId[i]));
					}else {
						DBDescribe[] listDescribeTables = databaseUserData.getDescribes();
						int numberDescribeTables = listDescribeTables.length;
						bigMessageSending.writeInt(numberDescribeTables);
						for(short i=0;i<numberDescribeTables;i++) 
							listDescribeTables[i].writeMessage(bigMessageSending);
						
						bigMessageSending.writeInt(numberUserId);
						for(short i=0;i<numberUserId;i++) {
							databaseAccount.writeInfoByOffset(bigMessageSending, databaseUserData.getOffsetOfCredential(listUserId[i]));
							databaseUserData.writeParsingRow(listUserId[i], listDescribeTables, numberDescribeTables, bigMessageSending);
						}
					}
					mgResult=bigMessageSending;
				}
			}else
				mgResult = mgPlayerWrong;
		} catch (Exception e) {
			e.printStackTrace();
			mgResult = new MessageSending(cmd, CaseCheck.EXCEPTION);
			mgResult.writeString(getStringException(e));
		}
		
		if(databaseUserData!=null)databaseUserData.close();
		if(databaseAccount!=null)databaseAccount.close();
		return mgResult;
	}
}
