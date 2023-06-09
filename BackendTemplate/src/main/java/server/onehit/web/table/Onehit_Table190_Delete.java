package server.onehit.web.table;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.server.BackendSession;
import server.config.CMD_ONEHIT;
import server.onehit.web.BaseOnehit_VerifyToken;

public class Onehit_Table190_Delete extends BaseOnehit_VerifyToken {

	public Onehit_Table190_Delete() {
		super(CMD_ONEHIT.BBWeb_Table_Delete);
	}

	
//	public MessageSending onMessage(BaseBackEnd_Session session, MessageReceiving messageReceiving) {
//		BinaryToken binaryToken = new BinaryToken();
//		if (binaryToken.decode(messageReceiving.readString()) == false)
//			return mgTokenError;
//		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		if (currentTimeMillis > binaryToken.timeOut)
//			return mgExpired;
//		short tableId = messageReceiving.readShort();
//		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		if(tableId<0 || binaryToken.adminId<1)// Validate TableId và UserId
//			return mgInvalid;
//		if(new File(PATH.DATABASE_FOLDER+"/"+tableId).exists()==false)
//			return mgNotExist;
//		if(binaryToken.adminId != managerTable.getUserId(tableId))
//			return mgPlayerWrong;
//		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		
//		managerTable.deleteFolder(new File(PATH.DATABASE_FOLDER+"/"+tableId));
//		return mgOK;
//	}


	@Override protected MessageSending onPassToken(BackendSession session, MessageReceiving messageReceiving, short tableId) {
		if(managerTable.deleteDatabase(tableId))
			return mgOK;
		else
			return mgDatabaseNotExist;
	}
}
