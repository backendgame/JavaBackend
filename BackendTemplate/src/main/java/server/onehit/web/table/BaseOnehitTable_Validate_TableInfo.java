package server.onehit.web.table;

import java.io.File;
import java.io.IOException;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.OneHitProcessing;
import backendgame.com.core.server.BackendSession;
import database_game.table.DBGame_AccountLogin;
import server.config.CaseCheck;
import server.config.PATH;
import server.onehit.BaseOnehit_AiO;
import server.onehit.BinaryToken;

public abstract class BaseOnehitTable_Validate_TableInfo extends BaseOnehit_AiO {

	public BaseOnehitTable_Validate_TableInfo(short command_id) {
		super(command_id);
	}

	protected abstract MessageSending onDatabaseAccount(DBGame_AccountLogin databaseAccount, MessageReceiving messageReceiving) throws IOException;
	
	@Override
	public MessageSending onMessage(BackendSession session, MessageReceiving messageReceiving) {
		BinaryToken binaryToken = new BinaryToken();
		if (binaryToken.decode(messageReceiving.readString()) == false)
			return mgTokenError;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (OneHitProcessing.currentTimeMillis > binaryToken.timeOut)
			return mgExpired;
		short tableId = messageReceiving.readShort();
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(tableId<0 || binaryToken.adminId<1)// Validate TableId và UserId
			return mgInvalid;
		if(new File(PATH.DATABASE_FOLDER+"/"+tableId).exists()==false)
			return mgDatabaseNotExist;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		MessageSending mgResult;
		DBGame_AccountLogin databaseUserData=null;
		try {
			databaseUserData=new DBGame_AccountLogin(tableId);
			mgResult = onDatabaseAccount(databaseUserData, messageReceiving);
		} catch (Exception e) {
			e.printStackTrace();
			mgResult = new MessageSending(cmd, CaseCheck.EXCEPTION);//Tại vì có write thêm data
			mgResult.writeString(getStringException(e));
		}
		if (databaseUserData != null)
			databaseUserData.close();

		return mgResult;
	}
}
