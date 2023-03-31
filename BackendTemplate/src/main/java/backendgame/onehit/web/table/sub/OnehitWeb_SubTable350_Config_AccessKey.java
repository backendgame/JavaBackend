package backendgame.onehit.web.table.sub;

import java.io.IOException;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.config.CMD_ONEHIT;
import database.BaseTableData;

public class OnehitWeb_SubTable350_Config_AccessKey extends BaseOnehit_SubTable {

	public OnehitWeb_SubTable350_Config_AccessKey() {
		super(CMD_ONEHIT.BBWeb_SubTable_Config_AccessKey);
	}

	@Override protected MessageSending onProcessSubTable(BaseTableData databaseUserData, MessageReceiving messageReceiving) throws IOException {
		byte[] accessKey = messageReceiving.readByteArray(8);
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		////////////////////////////////////////////////////////////////
		databaseUserData.writeBytes(BaseTableData.Offset_AccessKey, accessKey);
		////////////////////////////////////////////////////////////////
		return mgOK;
	}
}
