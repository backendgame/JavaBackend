package backendgame.onehit.web.table.sub;

import java.io.IOException;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.config.CMD_ONEHIT;
import database.BaseTableData;

public class OnehitWeb_SubTable352_Config_WriteKey extends BaseOnehit_SubTable {

	public OnehitWeb_SubTable352_Config_WriteKey() {
		super(CMD_ONEHIT.BBWeb_SubTable_Config_WriteKey);
	}

	@Override protected MessageSending onProcessSubTable(BaseTableData databaseUserData, MessageReceiving messageReceiving) throws IOException {
		byte[] writeKey = messageReceiving.readByteArray(8);
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		////////////////////////////////////////////////////////////////
		databaseUserData.writeBytes(BaseTableData.Offset_WriteKey, writeKey);
		////////////////////////////////////////////////////////////////
		return mgOK;
	}
}
