package begame.onehit.web.table.account_login;

import backendgame.com.core.MessageReceiving;
import begame.config.CMD_ONEHIT;
import database_game.table.DBGame_AccountLogin;
import database_game.table.DBGame_UserData;

public class OnehitWeb_ParsingRow253_Querry_By_LatestCreate extends BaseOnehitWeb_TableRow_Parsing {

	public OnehitWeb_ParsingRow253_Querry_By_LatestCreate() {
		super(CMD_ONEHIT.BBWeb_ParsingRow_Querry_By_LatestCreate);
	}

	@Override public long[] onGetUserId(DBGame_AccountLogin databaseAccount, DBGame_UserData databaseUserData, MessageReceiving messageReceiving) throws Exception {
		short numberUser = messageReceiving.readShort();
		////////////////////////////////////////////////////////////////////////////////////////////////
		if(messageReceiving.validate()==false)
			return null;
		////////////////////////////////////////////////////////////////////////////////////////////////
		long maxUserId = databaseUserData.countRow() - 1;
		
		if(numberUser>maxUserId)
			numberUser=(short) maxUserId;
		
		long[] listUserId = new long[numberUser];
		for(short i=0;i<numberUser;i++)
			listUserId[i] = maxUserId - i;
		
		return listUserId;
	}

//	@Override
//	protected MessageSending onProcess(DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, DataType[] listDataType, MessageReceiving messageReceiving) {
//		short numberUser = messageReceiving.readShort();
//		////////////////////////////////////////////////////////////////////////////////////////////////
//		if(messageReceiving.validate()==false)
//			return null;
//		//////////////////////////////////////////////////////////////////////////////////74//////////////
//		short rowDatabase;
//		try {rowDatabase = (short) databaseUserData.getUserIdForInsert();} catch (IOException e1) {e1.printStackTrace();return new MessageSending(cmd,CaseCheck.DATABASE_ERROR_QUERRY);}
//		if(numberUser>=rowDatabase)
//			numberUser=(short) (rowDatabase-1);
//		
//		short count=0;
//		long offset;
//		long userId;
//		
//		BigMessageSending messageSending=new BigMessageSending(cmd,CaseCheck.HOPLE);
//		messageSending.writeshort(count);
//		for(short i=0;i<numberUser;i++)
//			try {
//				userId = rowDatabase-i-1;
//				offset=databaseUserData.getLocationAccountLogin(userId);
//				
//				databaseAccount.writeInfoByOffset(messageSending, offset);
//				databaseUserData.writeParsingByUserId(dbString, messageSending, listDataType, userId);
//				count++;
//			} catch (IOException e) {
//				e.printStackTrace();
//				MessageSending mg=new MessageSending(cmd,CaseCheck.HOPLE);
//				mg.writeString(getStringException(e));
//				return mg;
//			}
//		messageSending.editSHORT(1, count);
//		return messageSending.toMessage();		
//	}
	
	
//	@Override protected MessageSending onProcess(DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, MessageReceiving messageReceiving){
//		short numberUser = messageReceiving.readShort();
//		////////////////////////////////////////////////////////////////////////////////////////////////
//		if(messageReceiving.validate()==false)
//			return null;
//		////////////////////////////////////////////////////////////////////////////////////////////////
//		short rowDatabase;
//		try {rowDatabase = (short) databaseUserData.getUserIdForInsert();} catch (IOException e1) {e1.printStackTrace();return new MessageSending(cmd,CaseCheck.DATABASE_ERROR_QUERRY);}
//		if(numberUser>=rowDatabase)
//			numberUser=(short) (rowDatabase-1);
//		
//		
//		short count=0;
//		long location;
//		
//		MessageSending messageSending=new MessageSending(cmd,CaseCheck.HOPLE);
//		messageSending.writeshort(count);
//		for(short i=0;i<numberUser;i++)
//			try {
//				location=databaseUserData.getLocationAccountLogin(rowDatabase-i-1);
//				messageSending.writeCopyData(databaseAccount.getDataByLocation(location));
//				count++;
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		messageSending.editSHORT(1, count);
//		return messageSending;
//	}

}
