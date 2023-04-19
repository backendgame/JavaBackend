package server.onehit.web;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.OneHitProcessing;
import backendgame.com.core.server.BackendSession;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;
import server.config.CaseCheck;
import server.onehit.BaseOnehit_AiO;
import server.onehit.BinaryToken;

public abstract class BaseOnehitWeb extends BaseOnehit_AiO {

	public BaseOnehitWeb(short command_id) {super(command_id);}
	
	public abstract MessageSending onProcessDatabase(DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData, MessageReceiving messageReceiving) throws Exception;
	
	@Override public MessageSending onMessage(BackendSession session, MessageReceiving messageReceiving) {
		BinaryToken richardToken = new BinaryToken();
		if (richardToken.decode(messageReceiving.readString()) == false)
			return mgTokenError;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (OneHitProcessing.currentTimeMillis > richardToken.timeOut)
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
			else if(richardToken.adminId==databaseUserData.adminId) {
				mgResult = onProcessDatabase(databaseAccount, databaseUserData, messageReceiving);
			}else
				mgResult = mgPlayerWrong;
		} catch (Exception e) {
			e.printStackTrace();
			mgResult = new MessageSending(cmd, CaseCheck.EXCEPTION);
			mgResult.writeString(getStringException(e));
		}
		
		if(databaseUserData!=null)
			databaseUserData.close();
		if(databaseAccount!=null)
			databaseAccount.close();
		return mgResult;
	}
}
