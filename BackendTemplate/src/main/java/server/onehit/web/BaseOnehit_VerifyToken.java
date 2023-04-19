package server.onehit.web;

import java.io.File;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.OneHitProcessing;
import backendgame.com.core.server.BackendSession;
import server.config.PATH;
import server.onehit.BaseOnehit_AiO;
import server.onehit.BinaryToken;

public abstract class BaseOnehit_VerifyToken extends BaseOnehit_AiO {

	public BaseOnehit_VerifyToken(short command_id) {super(command_id);}

	
	protected abstract MessageSending onPassToken(BackendSession session, MessageReceiving messageReceiving, short tableId);
	
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
		return onPassToken(session, messageReceiving, tableId);
	}	
	
}
