package backendgame.onehit.web.table.sub.tile_primarykey;

import bgcore.core.MessageReceiving;
import bgcore.core.MessageSending;
import database.table.DBGameTable_AccountLogin;
import database.table.DBGameTable_UserData;
import database.table.DBString;
import database.table.sub.DBGameTable_SubUserData;

public abstract class BaseOnehit_SubTable_FullData extends BaseOnehit_SubTable {

	public BaseOnehit_SubTable_FullData(short command_id) {super(command_id);}

	public abstract long[] onGetUserId(DBGameTable_SubUserData subTable, DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, MessageReceiving messageReceiving) throws Exception;

	@Override
	public MessageSending onProcess(DBGameTable_SubUserData subTable, DBGameTable_AccountLogin databaseAccount, DBGameTable_UserData databaseUserData, DBString dbString, MessageReceiving messageReceiving) throws Exception {
		long[] listUserId = onGetUserId(subTable, databaseAccount, databaseUserData, dbString, messageReceiving);
		if(listUserId==null || listUserId.length==0) {
			return mgValueNull;	
		}else {
			short numberUserId = (short) listUserId.length;
			
			MessageSending bigMessageSending=new MessageSending((short) 0);
			databaseUserData.writeDescribeTable(bigMessageSending, dbString);
			subTable.writeDescribeTable(bigMessageSending, dbString);
			
			bigMessageSending.writeShort(numberUserId);
			for(short i=0;i<numberUserId;i++) {
				databaseAccount.writeInfoByOffset(bigMessageSending, listUserId[i]);
				bigMessageSending.writeCopyData(databaseUserData.readDataByUserId(listUserId[i]));
				bigMessageSending.writeCopyData(subTable.readDataByUserId(listUserId[i]));
			}
			return bigMessageSending;
		}
	}
	
	
	
	
//	@Override public MessageSending onMessage(BaseOnehit_Session session, MessageReceiving messageReceiving) {
//		BinaryToken binaryToken = new BinaryToken();
//		if (binaryToken.decode(messageReceiving.readString()) == false)
//			return mgTokenError;
//		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		if (OneHitProcessing.currentTimeMillis > binaryToken.timeOut)
//			return mgExpired;
//		short tableId = messageReceiving.readShort();
//		short subId = messageReceiving.readShort();
//		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		if(tableId<0 || subId<0 || binaryToken.adminId<0)// Validate TableId và UserId
//			return mgInvalid;
//		if(new File(PATH.DATABASE_FOLDER+"/"+tableId).exists()==false)
//			return mgNotExist;
//		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//		MessageSending mgResult;
//		
//		DBGameTable_AccountLogin databaseAccount = null;
//		DBGameTable_UserData databaseUserData=null;
//		DBGameTable_SubUserData subTable=null;
//		DBString dbString = null;
//		
//		try {
//			subTable=new DBGameTable_SubUserData(tableId, subId);
//			databaseAccount = new DBGameTable_AccountLogin(tableId);
//			databaseUserData = new DBGameTable_UserData(tableId);
//			dbString = new DBString(tableId);
//			
//			if(System.currentTimeMillis() > subTable.timeAvaiable)
//				mgResult = mgExpired;
//			else if(binaryToken.adminId != subTable.adminId)
//				mgResult = mgPlayerWrong;
//			else {
//				
//				long[] listUserId = onGetUserId(subTable, databaseAccount, databaseUserData, dbString, messageReceiving);
//				if(listUserId==null || listUserId.length==0) {
//					mgResult = mgValueNull;	
//				}else {
//					short numberUserId = (short) listUserId.length;
//					
//					BigMessageSending bigMessageSending=new BigMessageSending();
//					databaseUserData.writeDescribeTable(bigMessageSending, dbString);
//					subTable.writeDescribeTable(bigMessageSending, dbString);
//					
//					byte[] dataMainTable = new byte[databaseUserData.dataLength+1];
//					byte[] dataSubTable = new byte[subTable.dataLength+1];
//					bigMessageSending.writeshort(numberUserId);
//					for(short i=0;i<numberUserId;i++) {
//						databaseUserData.readDataByUserId(listUserId[i], dataMainTable);
//						subTable.readDataByUserId(listUserId[i], dataSubTable);
//						
//						databaseAccount.writeInfoByOffset(bigMessageSending, listUserId[i]);
//						bigMessageSending.writeCopyData(dataMainTable);
//						bigMessageSending.writeCopyData(dataSubTable);
//					}
//					mgResult=bigMessageSending.toMessage();
//				}
//				
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			mgResult = new MessageSending(cmd, CaseCheck.EXCEPTION);//Tại vì có write thêm data
//			mgResult.writeString(Lib.getStringException(e));
//		}
//		
//		if(dbString!=null)dbString.close();
//		if(subTable != null)subTable.close();
//		if(databaseUserData!=null)databaseUserData.close();
//		if(databaseAccount!=null)databaseAccount.close();
//		return mgResult;
//	}
	




}
