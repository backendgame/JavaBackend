package backendgame.onehit.web.table.sub;

import java.io.IOException;

import backendgame.config.CMD_ONEHIT;
import bgcore.core.MessageReceiving;
import bgcore.core.MessageSending;
import database.BaseTableData;

public class OnehitWeb_SubTable351_Config_ReadKey extends BaseOnehit_SubTable {

	public OnehitWeb_SubTable351_Config_ReadKey() {
		super(CMD_ONEHIT.BBWeb_SubTable_Config_ReadKey);
	}

	@Override protected MessageSending onProcessSubTable(BaseTableData databaseUserData, MessageReceiving messageReceiving) throws IOException {
		byte[] readKey = messageReceiving.readByteArray(8);
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		////////////////////////////////////////////////////////////////
		databaseUserData.writeBytes(BaseTableData.Offset_ReadKey, readKey);
		////////////////////////////////////////////////////////////////
		return mgOK;
	}
}
