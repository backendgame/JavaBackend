package begame.onehit.web.table.account_login;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.server.BaseBackEnd_Session;
import backendgame.com.database.DBString;
import begame.config.CaseCheck;
import begame.config.PATH;
import begame.onehit.BaseOnehit_AiO;
import begame.onehit.BinaryToken;
import begame.onehit.web.dto.DTO_Describe;
import database_game.table.DBGameTable_AccountLogin;
import database_game.table.DBGameTable_UserData;

public abstract class BaseOnehitWeb_TableRow_Parsing extends BaseOnehit_AiO {

	public BaseOnehitWeb_TableRow_Parsing(short command_id) {super(command_id);}

	public abstract long[] onGetUserId(DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, MessageReceiving messageReceiving) throws Exception;
	
	@Override public MessageSending onMessage(BaseBackEnd_Session session, MessageReceiving messageReceiving) {
		BinaryToken richardToken = new BinaryToken();
		if (richardToken.decode(messageReceiving.readString()) == false)
			return mgTokenError;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (currentTimeMillis > richardToken.timeOut)
			return mgExpired;
		short tableId = messageReceiving.readShort();
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(tableId<0 || richardToken.adminId<1)// Validate TableId và UserId
			return mgInvalid;
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
				long[] listUserId = onGetUserId(databaseAccount, databaseUserData, dbString, messageReceiving);
				if(listUserId==null || listUserId.length==0) {
					mgResult = mgValueNull;	
				}else {
					short numberUserId = (short) listUserId.length;
					MessageSending bigMessageSending=new MessageSending(cmd,CaseCheck.HOPLE);
					bigMessageSending.writeLong(databaseUserData.countRow());
					if(databaseUserData.getDataLength()==0) {
						bigMessageSending.writeShort((short) 0);//list Describes = 0
						bigMessageSending.writeShort(numberUserId);
						for(short i=0;i<numberUserId;i++) {
							databaseAccount.writeInfoByOffset(bigMessageSending, databaseUserData.getCredentialOffset(listUserId[i]));
							databaseUserData.writeParsingRow(listUserId[i], (short) 0, null, bigMessageSending, dbString);
						}
					}else {
						DTO_Describe[] listDescribeTables = databaseUserData.getDescribeTables(dbString);
						short numberDescribeTables = (short) listDescribeTables.length;
						bigMessageSending.writeShort(numberDescribeTables);
						for(short i=0;i<numberDescribeTables;i++) 
							listDescribeTables[i].writeMessage(bigMessageSending);
						
						bigMessageSending.writeShort(numberUserId);
						for(short i=0;i<numberUserId;i++) {
							databaseAccount.writeInfoByOffset(bigMessageSending, databaseUserData.getCredentialOffset(listUserId[i]));
							databaseUserData.writeParsingRow(listUserId[i], numberDescribeTables, listDescribeTables, bigMessageSending, dbString);
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
		if(dbString!=null)dbString.close();
		return mgResult;
	}
}