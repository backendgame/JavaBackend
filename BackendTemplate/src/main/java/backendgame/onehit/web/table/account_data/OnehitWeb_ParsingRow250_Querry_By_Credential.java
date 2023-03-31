package backendgame.onehit.web.table.account_data;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.server.BaseBackEnd_Session;
import backendgame.config.CMD_ONEHIT;
import backendgame.config.CaseCheck;
import backendgame.onehit.BaseOnehit_AiO;
import backendgame.onehit.BinaryToken;
import database.DescribeTable;
import database.table.DBGameTable_AccountLogin;
import database.table.DBGameTable_UserData;
import database.table.DBString;
import database.table.Querry_AccountLogin;

public class OnehitWeb_ParsingRow250_Querry_By_Credential extends BaseOnehit_AiO {

	public OnehitWeb_ParsingRow250_Querry_By_Credential() {
		super(CMD_ONEHIT.BBWeb_ParsingRow_Querry_By_Credential);
	}

	@Override public MessageSending onMessage(BaseBackEnd_Session session, MessageReceiving messageReceiving) {
		BinaryToken richardToken = new BinaryToken();
		if (richardToken.decode(messageReceiving.readString()) == false)
			return mgTokenError;
		if (currentTimeMillis > richardToken.timeOut)
			return mgExpired;
		short tableId = messageReceiving.readShort();
		String credential = messageReceiving.readString();
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(tableId<0 || richardToken.adminId<1)// Validate TableId và UserId
			return mgInvalid;
		if(messageReceiving.validate()==false || credential.equals(""))
			return mgVariableInvalid;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		MessageSending mgResult=null;
		DBGameTable_AccountLogin databaseAccount = null;
		DBGameTable_UserData databaseUserData=null;
		Querry_AccountLogin querry = null;
		DBString dbString = null;
		try {
			databaseAccount = new DBGameTable_AccountLogin(tableId);
			databaseUserData = new DBGameTable_UserData(tableId);
			querry = new Querry_AccountLogin(tableId);
			dbString = new DBString(tableId);
			
			if(System.currentTimeMillis()>databaseUserData.timeAvaiable)//Hết hạn
				mgResult=mgTimeout;
			else if(databaseUserData.countRow()<2)
				mgResult=mgPlayerNotFound;
			else if(richardToken.adminId==databaseUserData.adminId) {
				long[] listOffset = querry.getOffset(credential);
				if(listOffset==null || listOffset.length==0) {
					mgResult = mgValueNull;	
				}else {
					long userId;
					short numberUserId = (short) listOffset.length;
					MessageSending bigMessageSending=new MessageSending(cmd,CaseCheck.HOPLE);
					if(databaseUserData.dataLength==0) {
						bigMessageSending.writeShort((short) 0);
						bigMessageSending.writeShort(numberUserId);
						for(short i=0;i<numberUserId;i++) {
							userId = databaseAccount.getUserId(listOffset[i]);
							databaseAccount.writeInfoByOffset(bigMessageSending, listOffset[i]);
							databaseUserData.writeParsingRow(userId, (short) 0, null, bigMessageSending, dbString);
						}
					}else {
						DescribeTable[] listDescribeTables = databaseUserData.getDescribeTables(dbString);
						short numberDescribeTables = (short) listDescribeTables.length;
						bigMessageSending.writeShort(numberDescribeTables);
						for(short i=0;i<numberDescribeTables;i++) 
							listDescribeTables[i].writeMessage(bigMessageSending);
						
						bigMessageSending.writeShort(numberUserId);
						for(short i=0;i<numberUserId;i++) {
							userId = databaseAccount.getUserId(listOffset[i]);
							databaseAccount.writeInfoByOffset(bigMessageSending, listOffset[i]);
							databaseUserData.writeParsingRow(userId, numberDescribeTables, listDescribeTables, bigMessageSending, dbString);
						}
					}
					mgResult=bigMessageSending;
				}
			}else
				mgResult = mgPlayerWrong;
		} catch (Exception e) {
			e.printStackTrace();
			mgResult = new MessageSending(cmd, CaseCheck.EXCEPTION);
			mgResult.writeString(getStringException(e));
		}
		
		if(databaseUserData!=null)databaseUserData.close();
		if(databaseAccount!=null)databaseAccount.close();
		if(querry!=null)querry.close();
		if(dbString!=null)dbString.close();
		return mgResult;
	}

	
}
