package backendgame.onehit.web.table;

import java.io.File;

import backendgame.config.CMD_ONEHIT;
import backendgame.config.PATH;
import backendgame.onehit.BaseOnehit_AiO;
import backendgame.onehit.BinaryToken;
import bgcore.core.MessageReceiving;
import bgcore.core.MessageSending;
import bgcore.core.server.BaseBackEnd_Session;

public class Onehit_Table190_Delete extends BaseOnehit_AiO {

	public Onehit_Table190_Delete() {
		super(CMD_ONEHIT.BBWeb_Table_Delete);
	}

	
	public MessageSending onMessage(BaseBackEnd_Session session, MessageReceiving messageReceiving) {
		BinaryToken binaryToken = new BinaryToken();
		if (binaryToken.decode(messageReceiving.readString()) == false)
			return mgTokenError;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (currentTimeMillis > binaryToken.timeOut)
			return mgExpired;
		short tableId = messageReceiving.readShort();
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(tableId<0 || binaryToken.adminId<1)// Validate TableId và UserId
			return mgInvalid;
		if(new File(PATH.DATABASE_FOLDER+"/"+tableId).exists()==false)
			return mgNotExist;
		if(binaryToken.adminId != managerTable.getUserId(tableId))
			return mgPlayerWrong;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		managerTable.deleteFolder(new File(PATH.DATABASE_FOLDER+"/"+tableId));
		return mgOK;
	}
}
