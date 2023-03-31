package backendgame.onehit.web.table.sub.leaderboard;

import backendgame.com.core.MessageReceiving;
import backendgame.com.core.MessageSending;
import backendgame.config.CMD_ONEHIT;
import backendgame.config.CaseCheck;
import database.BaseTableData;
import database.table.DBString;
import database.table.sub.DBGameTable_Leaderboard;

public class OnehitWeb_Leaderboard490_LoadConfig extends BaseOnehit_Leaderboard {

	public OnehitWeb_Leaderboard490_LoadConfig() {super(CMD_ONEHIT.BBWeb_Leaderboard_LoadConfig);}

	@Override public MessageSending onProcess(DBGameTable_Leaderboard leaderboard, MessageReceiving messageReceiving, DBString dbString) throws Exception {
		MessageSending messageSending=new MessageSending(cmd,CaseCheck.HOPLE);
		
		leaderboard.rf.seek(BaseTableData.Offset_DataLength);
		messageSending.writeShort(leaderboard.rf.readShort());
		
		leaderboard.rf.seek(BaseTableData.Offset_AccessKey);
		messageSending.writeLong(leaderboard.rf.readLong());
		
		leaderboard.rf.seek(BaseTableData.Offset_ReadKey);
		messageSending.writeLong(leaderboard.rf.readLong());
		
		leaderboard.rf.seek(BaseTableData.Offset_WriteKey);
		messageSending.writeLong(leaderboard.rf.readLong());
		
		leaderboard.rf.seek(BaseTableData.Offset_TimeCreate);
		messageSending.writeLong(leaderboard.rf.readLong());
		
		leaderboard.rf.seek(BaseTableData.Offset_Version);
		messageSending.writeLong(leaderboard.rf.readLong());
		
		leaderboard.rf.seek(DBGameTable_Leaderboard.OFFSET_DataType);
		messageSending.writeByte(leaderboard.rf.readByte());
		
		messageSending.writeString(leaderboard.getColumnName(dbString));
		return messageSending;
	}



}
