package begame.onehit.web.table;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.server.BaseBackEnd_Session;
import begame.config.CMD_ONEHIT;
import begame.onehit.web.BaseOnehit_VerifyToken;
import database_game.table.BaseDatabaseGame;

public class Onehit_Table151_Config_AccessKey_Set extends BaseOnehit_VerifyToken {

	public Onehit_Table151_Config_AccessKey_Set() {
		super(CMD_ONEHIT.BBWeb_Table_Config_AccessKey);
	}

	@Override protected MessageSending onPassToken(BaseBackEnd_Session session, MessageReceiving messageReceiving, short tableId) {
		long accessKey = messageReceiving.readLong();
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		managerTable.writeLongToDataFile(tableId, BaseDatabaseGame.Offset_AccessKey, accessKey);
		return mgOK;
	}

}
