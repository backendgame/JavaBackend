package backendgame.onehit.web.table.sub.leaderboard;

import backendgame.config.CMD_ONEHIT;
import backendgame.config.CaseCheck;
import bgcore.core.MessageReceiving;
import bgcore.core.MessageSending;
import database.table.DBString;
import database.table.sub.DBGameTable_Leaderboard;

public class OnehitWeb_Leaderboard420_Querry_Id extends BaseOnehit_Leaderboard {

	public OnehitWeb_Leaderboard420_Querry_Id() {super(CMD_ONEHIT.BBWeb_Leaderboard_Querry_Id);}

	@Override
	public MessageSending onProcess(DBGameTable_Leaderboard leaderboard, MessageReceiving messageReceiving, DBString dbString) throws Exception {
		if(messageReceiving.validate()==false)
			return mgVariableInvalid;
		
		byte[] data = leaderboard.read(DBGameTable_Leaderboard.OFFSET_StartLeaderboard, leaderboard.dataLength*(8+leaderboard.size));
		
		MessageSending messageSending = new MessageSending(cmd, CaseCheck.HOPLE);
		messageSending.writeShort(leaderboard.dataLength);
		messageSending.writeByte(leaderboard.dataType);
		messageSending.writeString(leaderboard.getColumnName(dbString));
		messageSending.writeCopyData(data);
		
//		for(short i=0;i<leaderboard.dataLength;i++)
//			System.out.println(leaderboard.getUserId(i)+"	-	"+leaderboard.getValue(i));
		
		return messageSending;
	}


}
