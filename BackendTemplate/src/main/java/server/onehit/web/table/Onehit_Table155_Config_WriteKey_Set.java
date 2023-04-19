package server.onehit.web.table;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.server.BackendSession;
import database_game.table.BaseDatabaseGame;
import server.config.CMD_ONEHIT;
import server.onehit.web.BaseOnehit_VerifyToken;

public class Onehit_Table155_Config_WriteKey_Set extends BaseOnehit_VerifyToken {

	public Onehit_Table155_Config_WriteKey_Set() {
		super(CMD_ONEHIT.BBWeb_Table_Config_WriteKey);
	}
	
	@Override protected MessageSending onPassToken(BackendSession session, MessageReceiving messageReceiving, short tableId) {
		long writeKey = messageReceiving.readLong();
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		managerTable.writeLongToDataFile(tableId, BaseDatabaseGame.Offset_WriteKey, writeKey);
		return mgOK;
	}
}
