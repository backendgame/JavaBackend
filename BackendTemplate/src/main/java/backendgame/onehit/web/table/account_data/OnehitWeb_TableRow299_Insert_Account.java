package backendgame.onehit.web.table.account_data;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.config.CMD_ONEHIT;
import backendgame.config.CaseCheck;
import backendgame.onehit.BaseOnehitWeb;
import backendgame.onehit.BinaryToken;
import database.DatabaseId;
import database.DescribeTable;
import database.table.DBGameTable_AccountLogin;
import database.table.DBGameTable_UserData;
import database.table.DBString;

public class OnehitWeb_TableRow299_Insert_Account extends BaseOnehitWeb {

	public OnehitWeb_TableRow299_Insert_Account() {
		super(CMD_ONEHIT.BBWeb_Row_Insert_Account);
	}
	
	@Override public MessageSending onProcessDatabase(DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, BinaryToken richardToken, MessageReceiving messageReceiving) throws Exception {
		byte databaseId = messageReceiving.readByte();
		String credential = messageReceiving.readString();
		String password = (databaseId==DatabaseId.SystemAccount || databaseId==DatabaseId.EmailCode)?messageReceiving.readString():"";
		////////////////////////////////////////////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return mgInvalid;
		////////////////////////////////////////////////////////////////////////////////////////////////
		long passwordLocation = dbString.getStringId(password);
		long userId = databaseAccount.insertRow(credential, databaseId, passwordLocation, databaseUserData);

		if(userId==-1)
			return mgExist;
		if(userId>0) {
			MessageSending mgResult = new MessageSending(cmd,CaseCheck.HOPLE);
			mgResult.writeLong(userId);
			
			if(databaseUserData.dataLength==0) {
				mgResult.writeShort((short) 0);
				databaseAccount.writeInfoByOffset(mgResult, databaseUserData.getCredentialOffset(userId));
			}else {
				DescribeTable[] listDescribeTables = databaseUserData.getDescribeTables(dbString);
				short numberDescribeTables = (short) listDescribeTables.length;
				mgResult.writeShort(numberDescribeTables);
				for(short i=0;i<numberDescribeTables;i++) 
					listDescribeTables[i].writeMessage(mgResult);
				
				databaseAccount.writeInfoByOffset(mgResult, databaseUserData.getCredentialOffset(userId));
				databaseUserData.writeParsingRow(userId, numberDescribeTables, listDescribeTables, mgResult, dbString);
			}
			return mgResult;
		}else
			return mgPlayerInvalidData;
	}	
}
