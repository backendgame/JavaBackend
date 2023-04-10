package begame.onehit.web.table.account_login;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.database.entity.DBDescribe;
import begame.config.CMD_ONEHIT;
import begame.config.CaseCheck;
import begame.onehit.web.BaseOnehitWeb;
import database_game.DatabaseId;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;

public class OnehitWeb_TableRow299_Insert_Account extends BaseOnehitWeb {

	public OnehitWeb_TableRow299_Insert_Account() {
		super(CMD_ONEHIT.BBWeb_Row_Insert_Account);
	}
	
	@Override public MessageSending onProcessDatabase(DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData, MessageReceiving messageReceiving) throws Exception {
		byte databaseId = messageReceiving.readByte();
		String credential = messageReceiving.readString();
		String password = (databaseId==DatabaseId.SystemAccount || databaseId==DatabaseId.EmailCode)?messageReceiving.readString():"";
		////////////////////////////////////////////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return mgInvalid;
		////////////////////////////////////////////////////////////////////////////////////////////////
		long userId = databaseAccount.insertAccount(credential, databaseId, password, databaseUserData);
		
        if (userId < 0)
            return mgExist;
        
        MessageSending mgResult = new MessageSending(cmd,CaseCheck.HOPLE);
        mgResult.writeLong(userId);
        if(databaseUserData.getNumberColumn()==0) {
            mgResult.writeInt(0);
            databaseAccount.writeInfoByOffset(mgResult, databaseUserData.getOffsetOfCredential(userId));
        }else {
            DBDescribe[] listDescribeTables = databaseUserData.getDescribes();
            int numberDescribeTables = listDescribeTables.length;
            mgResult.writeInt(numberDescribeTables);
            for(int i=0;i<numberDescribeTables;i++) 
                listDescribeTables[i].writeMessage(mgResult);
            
            databaseAccount.writeInfoByOffset(mgResult, databaseUserData.getOffsetOfCredential(userId));
            databaseUserData.writeParsingRow(userId, listDescribeTables, numberDescribeTables, mgResult);
        }
        return mgResult;
    }	
}
