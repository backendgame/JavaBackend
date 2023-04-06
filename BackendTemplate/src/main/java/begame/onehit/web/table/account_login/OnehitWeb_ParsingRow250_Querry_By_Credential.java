package begame.onehit.web.table.account_login;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.server.BaseBackEnd_Session;
import backendgame.com.database.DBString;
import begame.config.CMD_ONEHIT;
import begame.config.CaseCheck;
import begame.config.PATH;
import begame.onehit.BaseOnehit_AiO;
import begame.onehit.BinaryToken;
import database.DescribeTable;
import database_game.table.DBGameTable_AccountLogin;
import database_game.table.DBGameTable_UserData;

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
		DBString dbString = null;
		try {
			databaseAccount = new DBGameTable_AccountLogin(tableId);
			databaseUserData = new DBGameTable_UserData(tableId);
			dbString = new DBString(PATH.dbStringName(tableId));
			
			if(System.currentTimeMillis()>databaseUserData.timeAvaiable)//Hết hạn
				mgResult=mgTimeout;
			else if(databaseUserData.countRow()<2)
				mgResult=mgPlayerNotFound;
			else if(richardToken.adminId==databaseUserData.adminId) {
				long[] listOffset = databaseAccount.getOffset(credential);
				if(listOffset==null || listOffset.length==0) {
					mgResult = mgValueNull;	
				}else {
					long userId;
					short numberUserId = (short) listOffset.length;
					MessageSending bigMessageSending=new MessageSending(cmd,CaseCheck.HOPLE);
					if(databaseUserData.getDataLength()==0) {
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
