package begame.onehit.web.table.account_login;

import backendgame.com.core.MessageReceiving;
import begame.config.CMD_ONEHIT;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;

public class OnehitWeb_ParsingRow251_Querry_By_ListUserId extends BaseOnehitWeb_TableRow_Parsing {

	public OnehitWeb_ParsingRow251_Querry_By_ListUserId() {
		super(CMD_ONEHIT.BBWeb_ParsingRow_Querry_By_ListUserId);
	}

	@Override public long[] onGetUserId(DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData, MessageReceiving messageReceiving) throws Exception {
		short numberUser = messageReceiving.readShort();
		long[] listUserId = new long[numberUser];
		for(short i=0;i<numberUser;i++)
			listUserId[i] = messageReceiving.readLong();
		////////////////////////////////////////////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return null;
		////////////////////////////////////////////////////////////////////////////////////////////////
		return listUserId;
	}




//	@Override protected MessageSending onProcess(DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, DataType[] listDataType, MessageReceiving messageReceiving) {
//		short numberUser = messageReceiving.readShort();
//		long[] listUserId = new long[numberUser];
//		for(short i=0;i<numberUser;i++)
//			listUserId[i] = messageReceiving.readLong();
//		////////////////////////////////////////////////////////////////////////////////////////////////
//		if(messageReceiving.validate()==false)
//			return mgVariableInvalid;
//		////////////////////////////////////////////////////////////////////////////////////////////////
//		short count=0;
//		long offset;
//		long userId;
//		
//		BigMessageSending messageSending=new BigMessageSending(cmd,CaseCheck.HOPLE);
//		messageSending.writeshort(count);
//		for(short i=0;i<numberUser;i++)
//			try {
//				userId = listUserId[i];
//				offset=databaseUserData.getLocationAccountLogin(userId);
//				
//				databaseAccount.writeInfoByOffset(messageSending, offset);
//				databaseUserData.writeParsingByUserId(dbString, messageSending, listDataType, userId);
//				
//				count++;
//			} catch (IOException e) {
//				e.printStackTrace();
//				return getExceptionMessage(e);
//			}
//		messageSending.editSHORT(1, count);
//		return messageSending.toMessage();
//	}

	
}
