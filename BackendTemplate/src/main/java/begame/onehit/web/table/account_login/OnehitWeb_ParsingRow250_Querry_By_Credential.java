package begame.onehit.web.table.account_login;

import backendgame.com.core.MessageReceiving;
import begame.config.CMD_ONEHIT;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;

public class OnehitWeb_ParsingRow250_Querry_By_Credential extends BaseOnehitWeb_TableRow_Parsing {

	public OnehitWeb_ParsingRow250_Querry_By_Credential() {
		super(CMD_ONEHIT.BBWeb_ParsingRow_Querry_By_Credential);
	}

    @Override public long[] onGetUserId(DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData, MessageReceiving messageReceiving) throws Exception {
        String credential = messageReceiving.readString();
        if(messageReceiving.validate()==false || credential.equals(""))
            return null;
        return databaseAccount.querryUserId(credential);
    }
	

 

	
}
