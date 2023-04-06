package begame.onehit.web;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.server.BaseBackEnd_Session;
import begame.onehit.BaseOnehit_AiO;

public abstract class BaseOnehitWeb extends BaseOnehit_AiO {

	public BaseOnehitWeb(short command_id) {super(command_id);}
	
//	public abstract MessageSending onProcessDatabase(DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, BinaryToken richardToken, MessageReceiving messageReceiving) throws Exception;
	
	@Override public MessageSending onMessage(BaseBackEnd_Session session, MessageReceiving messageReceiving) {
//		BinaryToken richardToken = new BinaryToken();
//		if (richardToken.decode(messageReceiving.readString()) == false)
//			return mgTokenError;
//		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		if (OneHitProcessing.currentTimeMillis > richardToken.timeOut)
//			return mgExpired;
//		short tableId = messageReceiving.readShort();
//		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		if(tableId<0 || richardToken.adminId<1)// Validate TableId và UserId
//			return mgInvalid;
//		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		MessageSending mgResult=null;
//		DBGameTable_AccountLogin databaseAccount = null;
//		DBGameTable_UserData databaseUserData=null;
//		DBString dbString = null;
//		try {
//			databaseAccount = new DBGameTable_AccountLogin(tableId);
//			databaseUserData = new DBGameTable_UserData(tableId);
//			dbString = new DBString(tableId);
//			
//			if(System.currentTimeMillis()>databaseUserData.timeAvaiable)//Hết hạn
//				mgResult=mgTimeout;
//			else if(richardToken.adminId==databaseUserData.adminId) {
//				mgResult = onProcessDatabase(databaseAccount, databaseUserData, dbString, richardToken, messageReceiving);
//			}else
//				mgResult = mgPlayerWrong;
//		} catch (Exception e) {
//			e.printStackTrace();
//			mgResult = new MessageSending(cmd, CaseCheck.EXCEPTION);
//			mgResult.writeString(getStringException(e));
//		}
//		
//		if(dbString!=null)
//			dbString.close();
//		if(databaseUserData!=null)
//			databaseUserData.close();
//		if(databaseAccount!=null)
//			databaseAccount.close();
//		return mgResult;
		return null;
	}
}
