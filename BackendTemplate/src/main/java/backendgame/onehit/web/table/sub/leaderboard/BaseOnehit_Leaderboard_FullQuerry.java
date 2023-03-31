package backendgame.onehit.web.table.sub.leaderboard;

import java.io.File;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.com.core.OneHitProcessing;
import backendgame.com.core.server.BaseBackEnd_Session;
import backendgame.config.CaseCheck;
import backendgame.config.PATH;
import backendgame.onehit.BaseOnehit_AiO;
import backendgame.onehit.BinaryToken;
import database.DescribeTable;
import database.SubTable;
import database.table.DBGameTable_AccountLogin;
import database.table.DBGameTable_UserData;
import database.table.DBString;
import database.table.sub.DBGameTable_Leaderboard;

public abstract class BaseOnehit_Leaderboard_FullQuerry extends BaseOnehit_AiO {

	public BaseOnehit_Leaderboard_FullQuerry(short command_id) {super(command_id);}

	public abstract short[] onGetIndex(DBGameTable_Leaderboard leaderboard, MessageReceiving messageReceiving) throws Exception;
	
	@Override public MessageSending onMessage(BaseBackEnd_Session session, MessageReceiving messageReceiving) {
		BinaryToken binaryToken = new BinaryToken();
		if (binaryToken.decode(messageReceiving.readString()) == false)
			return mgTokenError;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (OneHitProcessing.currentTimeMillis > binaryToken.timeOut)
			return mgExpired;
		short tableId = messageReceiving.readShort();
		short subId = messageReceiving.readShort();
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(tableId<0 || subId<0 || binaryToken.adminId<1)// Validate TableId và UserId
			return mgInvalid;
		if(new File(PATH.DATABASE_FOLDER+"/"+tableId).exists()==false)
			return mgNotExist;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		MessageSending mgResult;
		DBGameTable_AccountLogin databaseAccount = null;
		DBGameTable_UserData databaseUserData=null;
		DBGameTable_Leaderboard leaderboard=null;
		DBString dbString = null;
		try {
			leaderboard=new DBGameTable_Leaderboard(tableId, subId);
			databaseAccount = new DBGameTable_AccountLogin(tableId);
			databaseUserData = new DBGameTable_UserData(tableId);
			dbString = new DBString(tableId);
			if(System.currentTimeMillis() > leaderboard.timeAvaiable)
				mgResult = mgExpired;
			else if(binaryToken.adminId != leaderboard.adminId)
				mgResult = mgPlayerWrong;
			else if(leaderboard.getType() != SubTable.SubType_Leaderboard)
				mgResult = mgDatabaseNotExist;
			else {
				short[] listIndex = onGetIndex(leaderboard, messageReceiving);
				if(listIndex==null)
					mgResult = mgValueNull;
				else {
					long userId;
					short numberIndex = (short) listIndex.length;
					
					MessageSending bigMessageSending=new MessageSending((short) 0);
					bigMessageSending.writeByte(CaseCheck.HOPLE);
					bigMessageSending.writeShort(leaderboard.dataLength);
					bigMessageSending.writeByte(leaderboard.dataType);
					bigMessageSending.writeString(leaderboard.getColumnName(dbString));
					
					if(databaseUserData.dataLength==0) {
						bigMessageSending.writeShort((short) 0);//numberDescribeTables
						bigMessageSending.writeShort(numberIndex);
						for(short i=0;i<numberIndex;i++) {
							userId = leaderboard.getUserId(listIndex[i]);
							
							bigMessageSending.writeShort(listIndex[i]);
							databaseAccount.writeInfoByOffset(bigMessageSending, databaseUserData.getCredentialOffset(userId));
							leaderboard.writeValue(bigMessageSending, listIndex[i]);
							bigMessageSending.writeByte(databaseUserData.getStatus(userId));
							bigMessageSending.writeLong(userId);//Use for Verify
						}
					}else {
						DescribeTable[] listDescribeTables = databaseUserData.getDescribeTables(dbString);
						short numberDescribeTables = (short) listDescribeTables.length;
						
						bigMessageSending.writeShort(numberDescribeTables);
						for(short i=0;i<numberDescribeTables;i++) {
							listDescribeTables[i].writeMessage(bigMessageSending);
						}
						
						bigMessageSending.writeShort(numberIndex);
						for(short i=0;i<numberIndex;i++) {
							userId = leaderboard.getUserId(listIndex[i]);
							
							bigMessageSending.writeShort(listIndex[i]);
							databaseAccount.writeInfoByOffset(bigMessageSending, databaseUserData.getCredentialOffset(userId));
							leaderboard.writeValue(bigMessageSending, listIndex[i]);
							databaseUserData.writeParsingRow(userId, numberDescribeTables, listDescribeTables, bigMessageSending, dbString);
							bigMessageSending.writeLong(userId);//Use for Verify
						}
					}
					
					mgResult = bigMessageSending;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			mgResult = new MessageSending(cmd, CaseCheck.EXCEPTION);//Tại vì có write thêm data
			mgResult.writeString(getStringException(e));
		}
		if(dbString!=null)dbString.close();
		if(leaderboard != null)leaderboard.close();
		if(databaseUserData!=null)databaseUserData.close();
		if(databaseAccount!=null)databaseAccount.close();
		return mgResult;
	}
	
	
}
