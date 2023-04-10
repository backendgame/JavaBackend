package begame.onehit.web.table.account_login;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.database.entity.DB_WriteDatabase;
import begame.config.CMD_ONEHIT;
import begame.onehit.web.BaseOnehitWeb;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;

public class OnehitWeb_ParsingRow285_Update_UserData extends BaseOnehitWeb {

	public OnehitWeb_ParsingRow285_Update_UserData() {
		super(CMD_ONEHIT.BBWeb_ParsingRow_Update_UserData);
	}

	@Override public MessageSending onProcessDatabase(DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData, MessageReceiving messageReceiving) throws Exception {
	    DB_WriteDatabase operator;
		int numberProcess = messageReceiving.readInt();
		DB_WriteDatabase[] listOperators=new DB_WriteDatabase[numberProcess];
		for(int i=0;i<numberProcess;i++) {
			operator=new DB_WriteDatabase();
			operator.readMessage(messageReceiving);
			listOperators[i]=operator;
			
			//Fix dataOperator
			if(databaseUserData.validateData(operator)==false)
			    return mgInvalid;
		}
		////////////////////////////////////////////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		////////////////////////////////////////////////////////////////////////////////////////////////
		
		for(int i=0;i<numberProcess;i++)
			databaseUserData.writeData(listOperators[i]);
		
		return mgOK;
	}

	
	
}
