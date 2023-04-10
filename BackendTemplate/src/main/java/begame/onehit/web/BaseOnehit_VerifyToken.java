package begame.onehit.web;

import java.io.File;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.OneHitProcessing;
import backendgame.com.core.server.BaseBackEnd_Session;
import begame.config.PATH;
import begame.onehit.BaseOnehit_AiO;
import begame.onehit.BinaryToken;

public abstract class BaseOnehit_VerifyToken extends BaseOnehit_AiO {

	public BaseOnehit_VerifyToken(short command_id) {super(command_id);}

	
	protected abstract MessageSending onPassToken(BaseBackEnd_Session session, MessageReceiving messageReceiving, short tableId);
	
	@Override
	public MessageSending onMessage(BaseBackEnd_Session session, MessageReceiving messageReceiving) {
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
