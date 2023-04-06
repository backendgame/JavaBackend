package begame.onehit.web.table;

import java.io.IOException;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.config.CMD_ONEHIT;
import database.table.DBGameTable_UserData;

public class Onehit_Table159_Config_MailService extends BaseOnehitTable_Validate_TableInfo {

	public Onehit_Table159_Config_MailService() {
		super(CMD_ONEHIT.BBWeb_Table_Config_MailService);
	}

	@Override protected MessageSending onHeaderInfo(DBGameTable_UserData databaseUserData, MessageReceiving messageReceiving) throws IOException {
		int length = messageReceiving.avaiable();
		if(length==0)
			return mgVariableInvalid;
		
		if(length > DBGameTable_UserData.Offset_DescribeTables - DBGameTable_UserData.Offset_Email)
			return mgOutOfRange;
		
		databaseUserData.rf.seek(DBGameTable_UserData.Offset_Email);
		databaseUserData.rf.write(messageReceiving.readByteArray(length));
		
		return mgOK;
	}

}
