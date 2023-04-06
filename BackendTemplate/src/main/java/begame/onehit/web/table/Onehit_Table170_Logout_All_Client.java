package begame.onehit.web.table;

import java.io.IOException;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.config.CMD_ONEHIT;
import database.BaseTableData;
import database.table.DBGameTable_UserData;

public class Onehit_Table170_Logout_All_Client extends BaseOnehitTable_Validate_TableInfo {

	public Onehit_Table170_Logout_All_Client() {
		super(CMD_ONEHIT.BBWeb_Table_Logout_All_Client);
	}

	@Override protected MessageSending onHeaderInfo(DBGameTable_UserData databaseUserData, MessageReceiving messageReceiving) throws IOException {
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		//////////////////////////////////////////////////////////////////////////////////
		databaseUserData.rf.seek(BaseTableData.Offset_LogoutId);
		byte logoutId = databaseUserData.rf.readByte();
		databaseUserData.rf.seek(BaseTableData.Offset_LogoutId);
		databaseUserData.rf.writeByte(logoutId+1);
		return mgOK;
	}

}
