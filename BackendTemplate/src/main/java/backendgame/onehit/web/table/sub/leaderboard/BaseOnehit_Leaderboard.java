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
import database.SubTable;
import database.table.DBString;
import database.table.sub.DBGameTable_Leaderboard;

public abstract class BaseOnehit_Leaderboard extends BaseOnehit_AiO {

	public BaseOnehit_Leaderboard(short command_id) {super(command_id);}

	public abstract MessageSending onProcess(DBGameTable_Leaderboard leaderboard, MessageReceiving messageReceiving, DBString dbString) throws Exception;
	
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
		DBGameTable_Leaderboard leaderboard=null;
		DBString dbString = null;
		try {
			leaderboard=new DBGameTable_Leaderboard(tableId, subId);
			dbString=new DBString(tableId);
			if(System.currentTimeMillis() > leaderboard.timeAvaiable)
				mgResult = mgExpired;
			else if(binaryToken.adminId != leaderboard.adminId)
				mgResult = mgPlayerWrong;
			else if(leaderboard.getType() != SubTable.SubType_Leaderboard)
				mgResult = mgDatabaseNotExist;
			else 
				mgResult = onProcess(leaderboard, messageReceiving, dbString);
		} catch (Exception e) {
			e.printStackTrace();
			mgResult = new MessageSending(cmd, CaseCheck.EXCEPTION);//Tại vì có write thêm data
			mgResult.writeString(getStringException(e));
		}
		if(leaderboard != null)leaderboard.close();
		if(dbString != null)dbString.close();
		return mgResult;
	}
	
	
}
